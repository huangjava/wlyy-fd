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