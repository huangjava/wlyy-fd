$(function() {
	var agentPage = window.localStorage.getItem(pageName);
	var jsonStr = $.parseJSON(agentPage);
	var pageUrl = jsonStr.pageurl;
	var Request = GetRequest(),
		userAgent = Request["userAgent"],
		town = Request["town"],
		teamName = Request["teamName"];
	var userStr = $.parseJSON(decodeURIComponent(userAgent));
	wxSaveUserAgent(userStr.uid, userStr.openid, userStr.token);
	window.location.href = pageUrl;
});
