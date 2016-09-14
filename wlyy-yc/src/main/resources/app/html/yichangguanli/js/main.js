/*mui.init({
	keyEventBind: {
		backbutton: false  //Boolean(默认true)关闭back按键监听
	}
});*/

function loginValidation() {
	var	Request = GetRequest();
	var	userId = Request["userId"]; // userId
	var	uId = Request["userId"];     //userCode
	var	ticket = Request["ticket"];
	var	orgId = Request["orgId"];
	var	vaildTime = Request["vaildTime"];
	var	appUID = Request["appUID"];
	var	appType = Request["appType"];
	var	platform = 2, iMei = "ceshi";


	/**
	 * 请求医生基本信息
	 */
	/*sendPost("loginApp",
	{
		"userId":userId,
		"appUID":appUID,
		"orgId":orgId,
		"appType":appType,
		"vaildTime":vaildTime,
		"ticket":ticket
	},
	null, function(res) {
		if(res.status == 200) {

		} else {

		}
	}, "GET");*/

	var oUserAgent = {
		//"id": userId,
		"uid": uId,
		"imei": iMei,
		"token": ticket,
		"openid" : ""
		//"platform": platform,
	};
	var userAgent = JSON.stringify(oUserAgent);
	plus.storage.setItem("userAgent", userAgent);
	plus.storage.setItem("orgId", orgId);
	plus.storage.setItem("appUID", appUID);
	plus.storage.setItem("ticket", ticket);
	plus.storage.setItem("userId", userId);
}

$(function() {
	plus.nativeUI.showWaiting();

	loginValidation();
	$('.main iframe').height($(window).height() - 51);
    $('#subPage').height($(window).height());//子页面
    //$('#subsubPage').height($(window).height());//子页面的子页面
	window.localStorage.removeItem("isLoginOut");

	var userAgent = JSON.parse(plus.storage.getItem("userAgent"));
	var userId = userAgent.uid;
	var orgId = plus.storage.getItem("orgId");
	var client_id = plus.storage.getItem("appUID");
	var ticket = plus.storage.getItem("ticket");
	var userId = plus.storage.getItem("userId");

	/**
	 * 导航点击事件
	 */
	$('.mui-tab-item').on("tap", function () {
		$(this).addClass('mui-active').siblings().removeClass('mui-active');
		$('.main iframe').eq($(this).index()).show().siblings().hide();
	})


	/**
	 * 请求医生基本信息
	 */
	sendPost("doctor/baseinfo",
		{
			"userId": userId,
			"ticket": ticket
		},
		null, function(res) {
			if(res.status == 200) {
				plus.storage.setItem("docInfo", JSON.stringify(res.data));
                $.each($('.main iframe'), function (i, v) {
                    $(v).attr('src', $(v).attr('data-html'));
                })
			} else {
				mui.toast("获取医生信息失败");
			}
            plus.nativeUI.closeWaiting();
		});
});

function openSubPage(url){
    url += (url.indexOf('?')==-1 ? '?' : '&') +'tim='+ new Date().getTime() ;
    $('#subPage').show().attr('src', url);
}

function closeSubPage(){
    $('#subPage').hide().attr('src','');
    //$('#subPage').hide();
}
//
//function hideSubPage(){
//    $('#subPage').hide();
//}
//
//function showSubPage(){
//    $('#subPage').show();
//}
//
//function openSubSubPage(url){
//    $('#subPage').hide();
//    url += (url.indexOf('?')==-1 ? '?' : '&') +'tim='+ new Date().getTime() ;
//    $('#subSubPage').show().attr('src', url);
//}
//
//function closeSubSubPage(){
//    $('#subSubPage').hide().attr('src','');
//    $('#subPage').show();
//}