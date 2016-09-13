$(function() {
})

function reLogin(){
	var Request = GetRequest(),
		openId = Request["openId"];
	var agentPage = window.localStorage.getItem(pageName);
	var pageUrl = "../../../html/qygl/html/search-community.html";
	if (agentPage!=null) {
		var jsonStr = $.parseJSON(agentPage);
		pageUrl = jsonStr.pageurl;
	}
	if (openId) {
		getReqPromise("login/wechat",{openId:openId})
			// 数据成功返回，处理页面展示
			.then(function(data) {
				var userStr = $.parseJSON(decodeURIComponent(data.userAgent));
				wxSaveUserAgent(userStr.uid, userStr.openid, userStr.token);
				window.location.href = pageUrl;
			});
	}
}
