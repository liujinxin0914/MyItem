(function(doc, win) {
    var docEl = doc.documentElement,
        resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
        recalc = function() {
            var clientWidth = docEl.clientWidth;
            if (!clientWidth) return;    
            docEl.style.fontSize = 14 * (clientWidth / 375) + 'px';//设置根元素font-size
        };
         /*
            600px
            20 * 600/320  -- >  [2 -- 3] 放大范围
            200/320 -- > [0.5 -- 0.1] 缩小
        */
        if (!doc.addEventListener) return;
        win.addEventListener(resizeEvt, recalc, false);// DOMContentLoaded火狐特有的事件，DOM加载完后触发
        doc.addEventListener('DOMContentLoaded', recalc, false);//当dom加载完成时，或者 屏幕垂直、水平方向有改变进行html的根元素计算
})(document, window);