$(function() {
	var agentPage = window.localStorage.getItem(pageName);
	var jsonstr = $.parseJSON(agentPage);
	var pageUrl = jsonstr.pageurl;
	
	var Request = new Object();
	Request = GetRequest();
	var userAgent = Request["userAgent"];
	var userstr = $.parseJSON(userAgent);
	wxSaveUserAgent((userstr.uid, userstr.openid, userstr.token));
	window.location.href = pageUrl;
	
})
