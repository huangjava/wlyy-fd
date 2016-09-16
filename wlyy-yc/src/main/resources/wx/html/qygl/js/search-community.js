var d = dialog({contentType:'load', skin:'bk-popup'}).show();
saveAgentPage("../../qygl/html/search-community.html");
var Request = GetRequest(),
	userAgent = Request["userAgent"],
	town = Request["town"],
	teamName = Request["teamName"];
if(Request["openid"]){
	openId = decodeURIComponent(Request["openid"]);
	window.localStorage.setItem("openid", openId);
}
if(Request["r"]){
	random = Request["r"];
	window.localStorage.setItem("random", random);
}
if (userAgent!=null) {
	var userStr = $.parseJSON(decodeURIComponent(userAgent));
	wxSaveUserAgent(userStr.uid, userStr.openid, userStr.token);
}
var $communityView = $('#community_view'),
	$communityList = $('#community_list'),
	$noResultWrap = $('#no_result_wrap');


	//getReqPromise("patient/family/isAssign",{patient:"1"}).then(function(data) {
	//	// TODO 是否已分拣(是否有网格信息)
	//	var sign = data.data;
	//	if(sign==1) {
	//		//已分拣+已签约
	//		window.location.href = "../html/doctor-homepage.html?teamCode="+teamCode;
	//	} else if(sign == 0){
	//		//已分拣+未签约
	//		window.location.href = "../html/search-team.html"
	//	}else{
	//		//未分拣
	//		window.location.href = "../html/address-new.html"
	//	}
    //
    //
	//}).catch(function(e) {
	//	console && console.error(e)
	//});


// 搜索框
$('.searchbar').searchBar();

getReqPromise("patient/sign/getSignStatus",{patientCode:"1",openId:openId,random:random}).then(function(data) {
	if (data.loginUrl) {
		window.location.href = data.loginUrl;
		return;
	}
	// TODO 示例数据
	var sign = data.data.signStatus;
	if (sign==1) {
	
		dialog({
				content: '您已签约！',
				cancelValue: '我知道了',
				cancel: function () {
					wx.closeWindow();
				}
		}).showModal();
		d.close();
	}
	
	
}).catch(function(e) {
	console && console.error(e)
});

getReqPromise("patient/hospital/getHospitalByTownCode",{town:town,openId:openId,random:random}).then(function(data) {
	// TODO 示例数据
	if (data.loginUrl) {
		window.location.href = data.loginUrl;
		return;
	}
	if(data && data.list.length) {
		$noResultWrap.hide();
		var html = template("community_li_tmpl", data);
		$communityList.html(html);
	} else {
		$noResultWrap.show();
	}
	
	d.close();
	
}).catch(function(e) {
	console && console.error(e)
});