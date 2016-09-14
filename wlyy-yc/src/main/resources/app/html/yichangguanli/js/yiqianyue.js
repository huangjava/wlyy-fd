//var patiUrl = "doctor/patient_group/patients"; //原有   20160905 yww 测试改
var patiUrl = "doctor/patient_group/signedPatients";
var patients; 		//缓存所有居民信息，为查询准备
var openedGroup = {}; //打开的分组，为了切换tab能保持分组状态
mui.plusReady(function() {
    $('.mui-action-back').on('tap', function(){
        alert(1);
        window.history.go(-1);
        return false;
    })
	plus.nativeUI.showWaiting();
	searchPatients(); //请求所有居民
});

/**
 * 获取所有居民
 */
function searchPatients(){
	var orgId = plus.storage.getItem("orgId");
	var userId = plus.storage.getItem("userId");
	var params = {orgId:orgId,userId:userId};
	sendPost(patiUrl, params, null, handleSuccess); 
}

/*
 * 请求成功
 */
function handleSuccess(res) {

	if (res.status == 200) {
		patients = res;
		if (res.data.length == 0) {
			$(".my-work").css("display", "none");
			$(".select-group").addClass("c-hide");
			$(".link").addClass("c-hide");
			$("#wushuju").removeClass("c-hide");
			plus.nativeUI.closeWaiting();
			return;
		}
		dealTmpl(res);
	}
}

/*
 * 拼接模板
 */
function dealTmpl(res) {
	template.helper("isOpen", function(code, nameSearch, patients) {
		if(nameSearch==1 && patients && patients.length>0)
			return true;
		return openedGroup[code]==1;
	});
	
	//template.helper("setSex", function(s) {
	//	if (s == 1) {
	//		return "男"
	//	} else if (s == 2) {
	//		return "女"
	//	}
	//});

	template.helper("setPhoto", function(s) {
		if (s == "男") {
			return "../../../images/p-male.png";
		} else if (s == "女") {
			return "../../../images/p-female.png";
		} else {
			return "../../../images/p-default.png";
		}
	});
	template.helper("setJson", function(j) {
		return JSON.stringify(j);
	});
	//template.helper("setDisease", function(d) {
	//	switch (d) {
	//		case 0:
	//			return "健康";
	//			break;
	//		case 1:
	//			return "高血压";
	//			break;
	//		case 2:
	//			return "糖尿病";
	//			break;
	//	}
	//});
	//template.helper("setSignType", function(t) {
	//	if (t == 1) {
	//		return "三师";
	//	} else if (t == 2) {
	//		return "家庭";
	//	}
	//});
	var cont = template("pati_list_tmpl", res);
	$("#pati_list").html(cont);
	groupClick();
	plus.nativeUI.closeWaiting();
}

/**
 * 初始化列表点击事件
 */
var groupClick = function() {
	
	/* 
	 * 展开居民列表  
	 */
	$(".patient-type").on('click', function() {
		var $this = $(this);
		if ($this.parent('.patient-list').hasClass("current")) {
			delete openedGroup[$this.attr('data-group')];
			$this.find('.arrow').removeClass('open');
			$this.next(".n-list").hide().parent('.patient-list').removeClass("current");
			$this.siblings().find('.n-list').hide();
			$this.siblings().find('.patient-list').removeClass('current');
			$this.siblings().find('.arrow').removeClass('open');
		} else {
			openedGroup[$this.attr('data-group')] = 1;
			$this.find('.arrow').addClass('open');
			$this.next(".n-list").show().parent('.patient-list').addClass("current");
		};
	})

	/* 
	 * 跳转到剧名详细信息
	 */
	function toDetail() {
		var patientId = $(this).attr("data-id");


        mui.openWindow({
            url: 'health-record.html?patientId='+ patientId,
            id: 'health-record.html',
            extras: {
                title: '居民首页'
            }
        });

		//openWindowWithSub("yiqianyue", "health-record.html", "居民首页",{patientId:patientId},"health-record");
	}
	mui("#pati_list").on("tap", "li", toDetail);
	mui("#search_pati_list").on("tap", "li", toDetail);
};

/*
 * 搜索居民
 */
var searchVal;
$("#search_name").click(function() {
	var val = $("input[data-form=clear]").val().trim();
	if(val == searchVal)
		return;
		
	if (val.length == 0) {
		$('#pati_list').show();
		$('#search_pati_list').hide();
		return;
	}
	
	$('#pati_list').hide();
	var patientDoms = $('li[data-name*="'+ val +'"]', $('#pati_list')).clone(true, true);
	$('#search_pati_list').empty().append($('<ul class="n-list"></ul>').append(patientDoms)).show();
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
window.addEventListener("updateyiqianyue", refresh);