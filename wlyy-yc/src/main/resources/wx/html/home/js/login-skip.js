$(function() {
	var agentPage = window.localStorage.getItem(pageName);
	var pageUrl = "../../../html/qygl/html/search-community.html";
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

	//getReqPromise("patient/family/isAssign",{}).then(function(data) {
	//	// TODO 是否已分拣(是否有网格信息)
	//	var assign = data.data.assign;
	//	if(assign==1) {
	//		//已分拣+已签约
	//		window.location.href = "../../qygl/html/doctor-homepage.html?teamCode="+data.data.teamCode+"&orgName="+data.data.orgName;
	//	} else if(assign == 0){
	//		//已分拣+未签约
	//		window.location.href = "../../qygl/html/search-team.html?hospital="+data.data.unitCode+"&name="+data.data.unitName;
	//	}else{
	//		//未分拣
	//		window.location.href = "../../qygl/html/address-new.html";
	//	}
    //
	//}).catch(function(e) {
	//	console && console.error(e)
	//});



	window.location.href = pageUrl;

});
