const XMLAjax = (url, method = 'GET', param = {}, callbackFunc = (response) => {}, errorFunc = () => {console.log("데이터 호출 에러");}) => {
    let data = new FormData();
    let getData = null;
    if (!param instanceof FormData) {
        getData = Object.keys(param).map(k => {data.append(k, param[k]); return `${k}=${param[k]}`}).join('&');
    }
    else {
        data = param;
        getData = new URLSearchParams(param).toString();
    }

    let xhr = new XMLHttpRequest();
    xhr.open(method, (method.toUpperCase() === 'GET') ? `${url}?${getData}` : url, true);
    //xhr.setRequestHeader("Content-Type", "multipart/form-data");
    xhr.send((method.toUpperCase() === 'GET') ? null : data);
    xhr.onload = () => {
        if (xhr.status === 200) {
            callbackFunc(xhr.response);
        }
    }
    xhr.onerror = errorFunc;
}

const JSONRequest = (url, param = {}, callbackFunc = (response) => {}, errorFunc = () => {console.log("데이터 호출 에러");}) => {
    let xhr = new XMLHttpRequest();
    const method = 'POST';
    let setData = {};
    if (param instanceof FormData) {
        param.forEach((value, key) => setData[key] = value);
    }
    else {
        setData = param;
    }
    xhr.open(method, url, true);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhr.send((method === 'GET') ? null : JSON.stringify(setData));
    xhr.onload = () => {
        if (xhr.status === 200) {
            callbackFunc(xhr.response);
        }
    }
    xhr.onerror = errorFunc;
}

const includeHtml = (element, link) => {
    if (link) {
        const xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = () => {
            if (this.readyState == 4 && this.status == 200) {
                element.innerHTML = this.responseText;
            }
        }

        xhttp.open("GET", link, true);
        xhttp.send();
    }
}

const setDateFormat = (date) => {
    let dateFormat = new Date(date);
    return `${dateFormat.getFullYear()}.${(dateFormat.getMonth() + 1).toString().padStart(2, '0')}.${dateFormat.getDate().toString().padStart(2, '0')}`;
}

const setFullDateFormat = (date) => {
    let dateFormat = new Date(date);
    return `${dateFormat.getFullYear()}-${(dateFormat.getMonth() + 1).toString().padStart(2, '0')}-${dateFormat.getDate().toString().padStart(2, '0')} ${dateFormat.getHours().toString().padStart(2, '0')}:${dateFormat.getMinutes().toString().padStart(2, '0')}:${dateFormat.getSeconds().toString().padStart(2, '0')}`;
}

const nl2br = (str) => {
    return str.replace(/\n/g, "<br />");
}