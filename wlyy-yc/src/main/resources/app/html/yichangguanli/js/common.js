/**
 * 开启主从页面
 * @param {Object} id
 * @param {Object} subPage
 * @param {Object} title
 * @param {Object} extras
 * @param {Object} subId
 */
function openWindowWithSub(id, subPage, title, extras, subId){
	extras = extras || {};
	mui.openWindow({
			url: '../html/header1.html', 
			id: id,
			extras: {
				title: title, 
				subPage: subPage, 
				subId: subId,
				extras: extras
			}
		});
}

//获取链接上的参数
function GetRequest() {
	var url = location.search; //获取url中"?"符后的字串
	var theRequest = new Object();
	if (url.indexOf("?") != -1) {
		var str = url.substr(1);
		strs = str.split("&");
		for(var i = 0; i < strs.length; i ++) {
			theRequest[strs[i].split("=")[0]]=(strs[i].split("=")[1]);
		}
	}
	return theRequest;
}

/**
 * 开启修改页面
 * @param {Object} id
 * @param {Object} title
 * @param {Object} extras
 */
function openModifyWindow(title, extras){
	extras = extras || {};
	mui.openWindow({
			url: '../html/modify1.html', 
			id: 'modify1',
			extras: {
				title: title, 
				subPage: '../html/modify2.html',
				subId: 'modify2_'+ extras.type,
				extras: extras
			}
		});
}

function closeWebView(id){
	plus.webview.getWebviewById(id).close();
}

var plus = {
	waiting: undefined,
	storage: {
		removeItem: function(k) {
			window.localStorage.removeItem(k);
		},
		getItem: function (k) {
			return window.localStorage.getItem(k);
		},
		setItem: function (k, v) {
			window.localStorage.setItem(k, v);
		}
	},
	nativeUI: {
		showWaiting: function(){
			return plus.waiting = dialog({contentType:'load', skin:'bk-popup'}).show();
		},
		closeWaiting: function(){
			if(plus.waiting)
				plus.waiting.close();
		},
		toast: function (msg) {
			dialog({contentType:'tipsbox', skin:'bk-popup' , content: msg, bottom:true}).show();
		}
	}
}

function openWindow(url){
    window.top.openSubPage(url);
}

function closeWindow(){
    window.top.closeSubPage();
}

//function hideWindow(url){
//    window.top.hideSubPage(url);
//}
//
//function showWindow(){
//    window.top.showSubPage();
//}
//
//function openSubWindow(url){
//    window.top.openSubSubPage(url);
//}
//
//function closeSubWindow(){
//    window.top.closeSubSubPage();
//}