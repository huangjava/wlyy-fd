var d = dialog({contentType:'load', skin:'bk-popup'}).show();
saveAgentPage("../../qygl/html/search-community.html");
var Request = GetRequest(),
	userAgent = Request["userAgent"],
	town = Request["town"],
	teamName = Request["teamName"];
var userStr = $.parseJSON(decodeURIComponent(userAgent));
wxSaveUserAgent(userStr.uid, userStr.openid, userStr.token);

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


getReqPromise("patient/sign/getSignStatus",{patientCode:"1"}).then(function(data) {
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

getReqPromise("patient/hospital/getHospitalByTownCode",{town:town}).then(function(data) {
	// TODO 示例数据
	//data = {"msg":"查询成功","list":[{"code":"3502050100","name":"海沧区嵩屿街道社区卫生服务中心"},{"code":"3502050101","name":"海沧社区卫生服务站"},{"code":"3502050200","name":"石塘社区卫生服务中心"},{"code":"3502050300","name":"东孚卫生院"},{"code":"3502050301","name":"天竺社区卫生服务站"},{"code":"3502050302","name":"国营厦门第一农场社区卫生服务站"},{"code":"3502050400","name":"新阳社区卫生服务中心"},{"code":"0a11148d-5b04-11e6-8344-fa163e8aee56","name":"厦门市海沧医院","photo":""}],"status":200};
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