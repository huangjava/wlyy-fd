var openWebview = function(url) {
	mui.openWindow({
		url: url,
		id: getId(url),
		waiting:{
				autoShow:false
			}
	})
}

var getId = function(url) {
	var aStr = url.split("/");
	var aid = null;
	if (aStr.length != 0) {
		aid = aStr[aStr.length - 1].split(".");
	} else {
		aid = aStr.split(".");
	}
	//console.log(aid[0]);
	return aid[0];
}

// 重新加载Webview窗口显示的HTML页面
function reloadWebview() {
	plus.webview.currentWebview().reload(true);
}

//测试的账号
var commonUserCode = "U20160322000001";
var commonDoctorCode = "D20160322000001";
var commonUserName = "李碧玉";
var hospital="厦门中山医院";
var commonServerUrl = "http://www.xmtyw.cn";

