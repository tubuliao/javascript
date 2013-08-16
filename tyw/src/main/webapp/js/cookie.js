function setCookie(name, value, expireday) {
      var exp = new Date();
    exp.setTime(exp.getTime() + expireday*24*60*60*1000); //设置cookie的期限
  //	document.cookie = name+"="+escape(value)+"; expires"+"="+exp.toGMTString();//创建cookie,带失效周期的
    document.cookie = name+"="+escape(value)+";path=/";//创建cookie
  }


function getCookie(name) {
    var cookieStr = document.cookie;
    if(cookieStr.length > 0) {
        var cookieArr = cookieStr.split(";"); //将cookie信息转换成数组
        for (var i=0; i<cookieArr.length; i++) {
            var cookieVal = cookieArr[i].split("="); //将每一组cookie(cookie名和值)也转换成数组
             if($.trim(cookieVal[0]) == name) {
                   return unescape(cookieVal[1]); //返回需要提取的cookie值
            }
        }
    }
}
