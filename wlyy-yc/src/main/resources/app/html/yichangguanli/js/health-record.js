var switchery = null;
var date = new Date();
var year = date.getFullYear();
var begindate;
var enddate;
var page = 0;
var pagesize = 10;
var pagetype = 5
var returnChary = 0;
var charType = 1;

$(function() {
	$('div[tabindex="-1"]').remove()
	bindEvents();
	//开关控件
	var changeCheckbox = document.querySelector('#jobStatusSwitch');
	var swiper = new Swiper('.xuetang-container', {
		pagination: '.xuetangcharts',
		paginationClickable: false
	});
	switchery = new Switchery(changeCheckbox, {
		disabled: false
	});

	changeCheckbox.onchange = function() {
		if (changeCheckbox.checked) //勾选
		{
			$(".dt-date-condition .dt-custom-content").show();
		} else { //未勾选
			$(".dt-date-condition .dt-custom-content").hide();
		}
	};
	begindate = year + "-" + getDateBefore(6).substring(5, 10);
	enddate = year + "-" + getDateBefore(0).substring(5, 10);
	//初始化日期范围
	var labelDate = getDateBefore(6).substring(5, 10) + "~" + getDateBefore(0).substring(5, 10);
	$(".lbl-date").html(labelDate);
	$(".lab-startDate").val(getDateBefore(6));
	$(".lab-endDate").val(getDateBefore(0));
	var opt1 = {
		preset: 'date',
		theme: 'ios',
		lang: 'zh',
		minDate: new Date(1900, 01, 01),
		onSelect:function(val){
			$(".lab-startDate").val(val.replace(/\//g,"-"));
		}
	};
	var opt2 = {
		preset: 'date',
		theme: 'ios',
		lang: 'zh',
		minDate: new Date(1900, 01, 01),
		onSelect:function(val){
			$(".lab-endDate").val(val.replace(/\//g,"-"));
		}
	};
	$(".lab-startDate").mobiscroll(opt1);
	$(".lab-endDate").mobiscroll(opt2);
	pageInit();
	
	function queryData() {
		//var self = plus.webview.currentWebview();
		//var patientId = self.patientId;
        var	Request = GetRequest();
        var	patientId = Request["patientId"];
		var params = {};
		params.patientId=patientId;
		params.type = charType; 
		params.page = page;
		params.pagesize = pagesize;
		params.begin = begindate+" 00:00:00";
		params.end = enddate+" 23:59:59";
		sendPost("doctor/health_index/list",params,null,
			function(res){
				if(res.status == 200){
					showListXue(res.list);
				}
			});
	}
	queryData();
//	queryListByType(charType, page, pagesize, queryListSuccesss, begindate, enddate);

})


//绑定事件
function bindEvents() {

    document.getElementById('mui-action-back').addEventListener('tap', function() {
        closeWindow();
//                    history.go(-1);
//                    history.back();
    });

	//标签切换
	$(".pt-tab li").click(function() {
		var id = $(this).attr("id");
		$(".pt-tab li").removeClass("hit");
		$(this).addClass("hit");
		if (id == "tizhengLi") {
			$(".xuetang").click();
			$("#tizhengList").show();
			$("#patinetsList").hide();
		} else {
			$("#tizhengList").hide();
			$("#patinetsList").show();
			$(".yundong").click();
		}
	});

	//点击标题事件
	$(".m-menu-link").on("click", ".row .col-content", function() {
		var activeItem = $(this).find(".item-icon");
		$(".m-menu-link").find(".item-icon").removeClass("active");
		$(this).find(".item-icon").addClass("active");
		$(".curve-box").hide();
		$(".c-table").hide();
		if (activeItem.hasClass("xuetang")) { //点击血糖
			$(".curve-box.div-xuetang").show();
			$(".table-xuetang").show();
			charType = 1;
			pageInit();
			//$("#qushi").click();
			queryListByType(charType, page, pagesize, queryListSuccesss, begindate, enddate);

		} else if (activeItem.hasClass("xueya")) { //点击血压
			$(".curve-box.div-xueya").show();
			$(".table-xueya").show();
			charType = 2;
			pageInit()
			//$("#qushi").click();
			queryListByType(charType, page, pagesize, queryListSuccesss1, begindate, enddate);
		} else if (activeItem.hasClass("tizhong")) { //点击体重
			$(".curve-box.div-tizhong").show();
			$(".table-tizhong").show();
			charType = 3;
			pageInit();
			$("#qushi").click();
			queryListByType(charType, page, pagesize, queryListSuccesss2, begindate, enddate);
		} else if (activeItem.hasClass("yaowei")) { //点击腰围
			$(".curve-box.div-yaowei").show();
			$(".table-yaowei").show();
			charType = 4;
			pageInit();
			$("#qushi").click();
			queryListByType(charType, page, pagesize, queryListSuccesss3, begindate, enddate);
		} else if (activeItem.hasClass("yundong")) { //点击运动
			charType = 5;
			$(".table-yundong").show();
			pageInit();
			querySportList(page, pagesize, begindate, enddate);
		} else if (activeItem.hasClass("yongyao")) { //点击用药
			charType = 6;
			$(".table-yongyao").show();
			pageInit();
			queryYongyaoList(page, pagesize, begindate, enddate);
		} else if (activeItem.hasClass("yinshi")) { //点击饮食
			charType = 7;
			pageInit();
			$(".table-yinshi").show();
			queryYinshiList(page, pagesize, begindate, enddate);
		}
	});

	//点击趋势图/列表事件
	$(".div-btn-group").on("click", ".c-btn", function() {
		//		var id = $(this).attr("id");
		//		$(".div-btn-group").find(".c-btn").removeClass("active");
		//		$(this).addClass("active");
		//		if (id == "qushi") { //显示趋势图
		//			$(".div-charts").show();
		//			$(".div-list").hide();
		//		} else { //显示列表
		//			$(".div-list").show();
		//			$(".div-charts").hide();
		//		}

	});


	//点击遮罩事件
	$(".modal-overlay").on("click", function() {
		if ($(".dt-date-condition").css("display") == "none") {
			$(".div-dialog-content").hide();
			$(".div-add-btn").removeClass("active");
			//alert("data")
		} else {
			$(".dt-date-condition").hide();
		}
		$(".modal-overlay").removeClass("modal-overlay-visible");

	});

	//时间选择框
	$(".div-date").on("click", function() {
		$(".dt-date-condition").show();
		$('body').append($('<div class="modal-overlay modal-overlay-visible" ></div>'))
//		$(".modal-overlay").addClass("modal-overlay-visible");
		$(".dt-date-condition dt").removeClass("del");
		var labdateItem = "";
		var activeId = $(".pt-tab .hit").attr("id");
		if (activeId == "tizhengLi") { //体征特诊
			labdateItem = $("#tizhengList").find(".lbl-date").attr("data-id");
		} else { //保健记录
			labdateItem = $("#patinetsList").find(".lbl-date").attr("data-id");
		}
		$(".dt-date-condition dt[data-id=" + labdateItem + "]").find(".icon").addClass("del");
		$(".dt-date-condition dt[data-id=" + labdateItem + "]").trigger("click");
		if (begindate) {
			$(".lab-startDate").val(begindate);
			$(".lab-endDate").val(enddate);
		}
	});

	//取消事件
	$(".quxiao").on("click", function() {
		$(".modal-overlay").trigger("click");
	});

	//时间选择框-取消按钮事件
	$("#cancle-btn").on("click", function() {
		$(".dt-date-condition").hide();
		$(".modal-overlay").removeClass("modal-overlay-visible");
	});

	//时间选择框-确定按钮事件
	$("#confirm-btn").on("click", function() {
		var selectOp = $(".dt-date-condition .del");
		var resultDate = "";
		var dataId = "";
		if (selectOp.length > 0) { //选中最近一周/最近一月
			var index = selectOp.closest("dt").index(); //0是最近一周，1是最近一月
			if (index == 0) {
				begindate = year + "-" + getDateBefore(6).substring(5, 10);
				enddate = year + "-" + getDateBefore(0).substring(5, 10);
				resultDate = getDateBefore(6).substring(5, 10) + "~" + getDateBefore(0).substring(5, 10);
				$(".lab-startDate").val(getDateBefore(6));
				$(".lab-endDate").val(getDateBefore(0));
				dataId = "1";
			} else {
				begindate = year + "-" + getDateBefore(29).substring(5, 10);
				enddate = year + "-" + getDateBefore(0).substring(5, 10);
				resultDate = getDateBefore(29).substring(5, 10) + "~" + getDateBefore(0).substring(5, 10);
				$(".lab-startDate").val(getDateBefore(29));
				$(".lab-endDate").val(getDateBefore(0));
				dataId = "2";
			}
		} else { //选中自定义
			var date1 = new Date($(".lab-startDate").val());
			var date2 = new Date($(".lab-endDate").val());
			if (Date.parse(date1) > Date.parse(date2)) {
				dialog({
					contentType: 'tipsbox',
					skin: 'bk-popup',
					content: '开始时间不能大于结束时间'
				}).show();
				return false;
			}
			resultDate = $(".lab-startDate").val().substring(5, 10) + "~" + $(".lab-endDate").val().substring(5, 10);
			begindate = year + "-" + $(".lab-startDate").val().substring(5, 10);
			enddate = year + "-" + $(".lab-endDate").val().substring(5, 10);
			dataId = "3";
		}
		var labdateItem = "";
		var activeId = $(".pt-tab .hit").attr("id");
		if (activeId == "tizhengLi") { //体征特诊
			$("#tizhengList").find(".lbl-date").html(resultDate).attr("data-id", dataId);
		} else { //保健记录
			$("#patinetsList").find(".lbl-date").html(resultDate).attr("data-id", dataId);
		}
		pageInit();
//		if (charType <= 4) {} 
		if(charType == 1){
			queryListByType(charType, page, pagesize, queryListSuccesss, begindate, enddate);
		}else if(charType == 2){
			queryListByType(charType, page, pagesize, queryListSuccesss1, begindate, enddate);
		}
		
		else if (charType == 5) {
			querySportList(page, pagesize, begindate, enddate);
		} else if (charType == 6) {
			queryYongyaoList(page, pagesize, begindate, enddate);
		} else if (charType == 7) {
			queryYinshiList(page, pagesize, begindate, enddate);
		}
		$("#cancle-btn").trigger("click");
	});

	//切换查询条件
	$(".dt-date-condition").on("click", ".tit", function() {
		var activeId = $(this).attr("data-id");
		$(".dt-date-condition .icon").removeClass("del");
		if (activeId == "1" || activeId == "2") { //最近一周或最近一月
			$(this).find(".icon").addClass("del");
			switchery.element.checked = false
			switchery.setPosition();
			$(".dt-date-condition .dt-custom-content").hide();
		} else {
			if (!switchery.element.checked) {
				$(".dt-date-condition dt").eq(0).trigger("click");
				return false;
			}
			switchery.element.checked = true
			switchery.setPosition();
			$(".dt-date-condition .dt-custom-content").show();
		}
	});


	$("#view_more1").on("click", function() {
		queryList1(page, pagesize);
	});

	$("#view_more2").on("click", function() {
		queryList2(page, pagesize);
	});

	$("#view_more3").on("click", function() {
		queryList3(page, pagesize);
	});

	$("#view_more4").on("click", function() {
		queryList4(page, pagesize);
	});


}

//echar数组
var charTypeJson = {
	1: ['早餐前血糖', '早餐后血糖', '午餐前血糖', '午餐后血糖', '晚餐前血糖', '晚餐后血糖', '睡前血糖'], //血糖
	2: ['收缩压', '舒张压'], //血压
	3: ['体重'], //体重
	4: ['腰围'] //腰围
}

function pageInit() {
	page = 0;
	pagesize = 10;
	if (charType == 1) {
		$(".xuetang-more").show();
		$(".xueya-more").hide();
		$(".tizhong-more").hide();
		$(".yaowei-more").hide();
		$("#view_more").html("点击查看更多");
		$("#item").html("");
	} else if (charType == 2) {
		$(".xuetang-more").hide();
		$(".xueya-more").show();
		$(".tizhong-more").hide();
		$(".yaowei-more").hide();
		$("#view_more1").html("点击查看更多");
		$("#item1").html("");
	} else if (charType == 3) {
		$(".xuetang-more").hide();
		$(".xueya-more").hide();
		$(".tizhong-more").show();
		$(".yaowei-more").hide();
		$("#view_more2").html("点击查看更多");
		$("#item2").html("");
	} else if (charType == 4) {
		$(".xuetang-more").hide();
		$(".xueya-more").hide();
		$(".tizhong-more").hide();
		$(".yaowei-more").show();
		$("#view_more3").html("点击查看更多");
		$("#item3").html("");
	} else if (charType == 5) {
		$(".sport-more").show();
		$(".yongyao-more").hide();
		$(".yinshi-more").hide();
		$("#view_more_sport").html("点击查看更多");
		$("#sport_list").html("");
	} else if (charType == 6) {
		$(".sport-more").hide();
		$(".yongyao-more").show();
		$(".yinshi-more").hide();
		$("#view_more_yongyao").html("点击查看更多");
		$("#drug_list").html("");
	} else if (charType == 7) {
		$(".sport-more").hide();
		$(".yongyao-more").hide();
		$(".yinshi-more").show();
		$("#view_more_yinshi").html("点击查看更多");
		$("#diet_list").html("");
	}

}