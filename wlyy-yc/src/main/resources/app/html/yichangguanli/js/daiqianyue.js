var tab = 0;
var patients; 		//缓存所有居民信息，为查询准备
var myTeamDom = $('.my-team');
$(function() {
	plus.nativeUI.showWaiting();
	searchPatients(); //请求所有居民
	groupClick();
});

/**
 * 获取所有居民
 */
function searchPatients(){
	var orgId = plus.storage.getItem("orgId");
	var userId = plus.storage.getItem("userId");
	var params = {orgId:orgId,userId:userId}
//	var patiUrl = "doctor/patient_group/patients"; // 原url 20160905 yww测试改
	//var patiUrl = "doctor/login";
	var signingPatiUrl = "doctor/patient_group/signingPatients"//待签约
	var noSigningPatiUrl = "doctor/patient_group/noSigningPatients"//未签约
	//待签约
	sendPost(signingPatiUrl,params, null, function(res){
		handleSuccess(res, 0);
	});
	//未签约
	sendPost(noSigningPatiUrl,params, null, function(res){
		handleSuccess(res, 1);
	});
}

/*
 * 请求成功
 */
function handleSuccess(res, tab) {

	if (res.status == 200) {
		console.log("******"+JSON.stringify(res));
		if(tab==0)
			res = {
				tab: 0,
				data:res.data
			}
        else
        	res = {
        		tab: 1,
          		data:res.data
			}
		patients = res;
		if (res.data.length == 0) {
			$('.view-more', myTeamDom).eq(tab).hide();
			$('.nodata', myTeamDom).eq(tab).show();
			plus.nativeUI.closeWaiting();
			return;
		}
		dealTmpl(res, tab);
	}
}

/*
 * 拼接模板
 */
function dealTmpl(res, tab) {
	
	template.helper("setSex", function(s) {
		if (s == 1) {
			return "男"
		} else if (s == 2) {
			return "女"
		}
	});

	template.helper("setPhoto", function(s) {
		if (s == 1) {
			return "../../../images/p-default.png";
		} else if (s == 2) {
			return "../../../images/p-female.png";
		} else {
			return "../../../images/p-default.png";
		}
	});
	template.helper("setJson", function(j) {
		return JSON.stringify(j);
	});
	template.helper("setDisease", function(d) {
		switch (d) {
			case 0:
				return "健康";
				break;
			case 1:
				return "高血压";
				break;
			case 2:
				return "糖尿病";
				break;
		}
	});
	template.helper("setSignType", function(t) {
		if (t == 1) {
			return "三师";
		} else if (t == 2) {
			return "家庭";
		}
	});
	
	var cont = template("pati_list_tmpl", res);
	$(".d-list", myTeamDom).eq(tab).html(cont);
	plus.nativeUI.closeWaiting();
}

/**
 * 初始化列表点击事件
 */
var groupClick = function() {
	
	mui(".c-lab-mor").on("tap", "li", function(){
		$(".c-lab-mor li.curr").removeClass("curr").siblings().addClass("curr");
		tab = $(".c-lab-mor li.curr").index();
		$('li.l-1', myTeamDom).eq(tab).show().siblings().hide();
	})
	
	/* 
	 * 跳转到居民详细信息
	 */
//	$(".my-team").on("tap", "li[class*='tab-']", function() {
//		openWindowWithSub("weiqianyuejuminxinxi", "weiqianyuejuminxinxi.html", "居民资料", {});
//	});
	
	/* 
	 * 签约审核
	 */
	$(".my-team").on("tap", "li[class*='tab-']", function() {
		var patientId = $(this).attr("data-id");
        openWindow("qianyueshenhe.html?patientId="+patientId)

		//openWindowWithSub("daiqianyue", "qianyueshenhe.html", "签约审核", {patientId:patientId},"qianyueshenhe");
	});
};

/*
 * 搜索居民
 */
var searchVal, searchTab;
$("#search_name").click(function() {
	var val = $("input[data-form=clear]").val().trim();
	if(val == searchVal && searchTab == tab)
		return;
	searchTab = tab;
	if (val.length == 0) {
		$('.l-1', myTeamDom).eq(tab).show();
		$('.l-2', myTeamDom).hide();
		return;
	}
	
	var patientDoms = $('li[data-name*="'+ val +'"]', $('.l-1', myTeamDom).eq(tab)).clone(true, true);
	$('.l-1', myTeamDom).eq(tab).hide();
	$('.l-2', myTeamDom).empty().append($('<ul class="n-list l-list" ></ul>').append(patientDoms)).parent().show();
});

//显示选中的分组
function showGroup(type) {
	if(type){
		openedGroup = {};
		openedGroup[type] = 1;
	} 
}

function refresh(e) {
	searchPatients();
}

/**
 * 自定义刷新事件
 */
window.addEventListener("updatedaiqianyue", refresh);