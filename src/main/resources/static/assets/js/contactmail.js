let mailValid = new PnJValidation(document.mailFrm,
    ['fromAddress', 'toAddress', 'name', 'content'],
);

let loadingDisplay = new DisplayAnimation(document.querySelector("#loading"));

mailValid.setSubmitAction(() => {
    loadingDisplay.show();
    let frm = mailValid.getForm();
    XMLAjax(frm.action, frm.method, new FormData(frm), (data) => {
        let objData = JSON.parse(data);
        if (objData.result) {
            alert("메일 발송이 완료 되었습니다.");
            frm.reset();
            loadingDisplay.fadeOut(500);
        }
        else {
            alert("메일 발송에 실패 했습니다.");
        }
    }, () => {
        alert("메일 발송 페이 호출에 문제가 있습니다.");
    })
})