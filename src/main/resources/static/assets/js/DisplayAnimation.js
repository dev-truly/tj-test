class DisplayAnimation {
    _el; _style; _flag;
    _oriHeight; _oriOpacity;
    constructor(target) {
        this._flag = false;
        this._el = target;
        this._style = getComputedStyle(this._el);
        this._oriHeight = this._style.height;
        this._oriOpacity = this._style.opacity;
    }

    fadeIn = (timer) => {
        if (!this._flag) {
            this.progress(timer);
            let toOpacity = this._oriOpacity;
            this.show();
            this._el.animate([{opacity: 0},{opacity: toOpacity}],
                {
                    duration: timer,
                    iterations: 1,
                    fill: "none",
                }
            );
        }
    }

    fadeOut = (timer) => {
        if (!this._flag) {
            this.progress(timer);
            this._el.animate([{opacity: this._oriOpacity}, {opacity: 0}],
                {
                    duration: timer,
                    iterations: 1,
                    fill: "none",
                }
            );
            setTimeout(() => {
                this.hide();
            }, timer);
        }
    }

    fadeToggle = (timer) => {
        if (this._el.display == "block") {
            this.fadeOut(timer);
        } else {
            this.fadeIn(timer);
        }
    }

    show = () => {
        this._el.style.display = "block";
    }

    hide = () => {
        this._el.style.display = "none";
    }

    slideDown = (timer) => {
        if (!this._flag) {
            this.progress(timer);
            let toHeight = this._oriHeight;
            this.show();
            this._el.animate([{height: 0},{height: toHeight}],
                {
                    duration: timer,
                    iterations: 1,
                    fill: "none",
                }
            );
        }
        // this._el.style.top = document.window.height;
        // this._el.style.display = "block";

    }

    slideUp = (timer) => {
        if (!this._flag) {
            this.progress(timer);
            this._el.animate([{height: 0, display: "none"}],
                {
                    duration: timer,
                    iterations: 1,
                    fill: "none",
                }
            );
            setTimeout(() => {
                this.hide();
            }, timer);
        }
    }

    slideToggle = (timer) => {
        if (this._el.display == "block") {
            this.slideUp(timer);
        }
        else {
            this.slideDown(timer);
        }
    }

    progress = (timer) => {
        this._flag = true;
        let timerInterval = setInterval(() => {
            this._flag = false;
            clearInterval(timerInterval);
        }, timer);
    }
}