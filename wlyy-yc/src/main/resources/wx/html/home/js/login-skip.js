$(function() {
	var agentPage = window.localStorage.getItem(pageName);
	var pageUrl = "../../html/qygl/html/search-community.html"
	if (agentPage!=null) {
		var jsonStr = $.parseJSON(agentPage);
		pageUrl = jsonStr.pageurl;
	}

	var Request = GetRequest(),
		userAgent = Request["userAgent"],
		town = Request["town"],
		teamName = Request["teamName"];
	if (userAgent!=null) {
		var userStr = $.parseJSON(decodeURIComponent(userAgent));
		wxSaveUserAgent(userStr.uid, userStr.openid, userStr.token);
	}
	window.location.href = pageUrl;
});
