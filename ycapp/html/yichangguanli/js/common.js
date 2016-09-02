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
