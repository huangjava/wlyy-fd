$(function() {
})

function reLogin(){
	var agentPage = window.localStorage.getItem(pageName);
	var jsonstr = $.parseJSON(agentPage);
	var pageUrl = jsonstr.pageurl;
	getReqPromise("login/wechat/getDoctorInfo",{doctorCode:doctorCode})
		// 数据成功返回，处理页面展示
		.then(function(data) {
			wxSaveUserAgent((data.uid, data.openid, data.token));
			window.location.href = pageUrl;
		});
}
