$(function() {
})

function reLogin(){
	var Request = GetRequest(),
		userAgent = Request["userAgent"];
	var agentPage = window.localStorage.getItem(pageName);
	var jsonstr = $.parseJSON(agentPage);
	var pageUrl = jsonstr.pageurl;
	if (userAgent) {
		var userStr = $.parseJSON(decodeURIComponent(userAgent));
		getReqPromise("login/wechat",{openId:userStr.openid})
			// 数据成功返回，处理页面展示
			.then(function(data) {
				var userStr = $.parseJSON(decodeURIComponent(data.userAgent));
				wxSaveUserAgent(userStr.uid, userStr.openid, userStr.token);
				window.location.href = pageUrl;
			});
	}
}
