saveAgentPage("../../tzzb/html/health-record.html");
var Request = GetRequest();
if(Request["openid"]){
	openId = decodeURIComponent(Request["openid"]);
	window.localStorage.setItem("openid", openId);
}
if(Request["r"]){
	random = Request["r"];
	window.localStorage.setItem("random", random);
}
  var switchery = null;
  var date = new Date();
  var year = date.getFullYear();
  var begindate;
  var enddate;
  var page=0;
	var pagesize = 10;
	Request = GetRequest();
	var pagetype = 5
	var openid = Request["openid"];
	var returnChary = 0 ;
  returnChary = Request["charType"];
  var charType = 1;
  var scroller1 = null;
$(function(){
	  //checkUserAgent();
	//var userAgent = window.localStorage.getItem(agentName);
//		if(!userAgent){
//			window.localStorage.setItem(pageName,"{\"pageurl\":\""+window.location.href+"\"}");
//			window.location.href = "../../home/html/login.html?type=0&openid=" + openid;	
//		}	
		bindEvents();	
		scroller1 = new IScrollPullUpDown('wrapper',{
			probeType:2,
			bounceTime: 250,
			bounceEasing: 'quadratic',
			mouseWheel:false,
			scrollbars:true,
			fadeScrollbars:true,
			interactiveScrollbars:false,
			click:true,
			},null,null);
     	//开关控件
       var changeCheckbox = document.querySelector('#jobStatusSwitch');
       var swiper = new Swiper('.xuetang-container', {
					pagination: '.xuetangcharts',
					paginationClickable: false
			 });
       switchery = new Switchery(changeCheckbox, { disabled: false});
     
       changeCheckbox.onchange = function() {
            if(changeCheckbox.checked)//勾选
            {
             	$(".dt-date-condition .dt-custom-content").show();  
            }
            else{//未勾选
               $(".dt-date-condition .dt-custom-content").hide();  
            }
       };
        begindate = year+"-"+getDateBefore(6).substring(5,10);
        enddate  = year+"-"+getDateBefore(0).substring(5,10);
        //初始化日期范围
        var labelDate = getDateBefore(6).substring(5,10)+"~"+getDateBefore(0).substring(5,10);
      $(".lbl-date").html(labelDate);
      $(".lab-startDate").val(getDateBefore(6));
	    $(".lab-endDate").val(getDateBefore(0));
	    var opt1 = {
				preset: 'date',
				theme: 'ios',
				lang: 'zh',
				minDate: new Date(1900, 01, 01)
			};
		$(".lab-startDate").mobiscroll(opt1);
		$(".lab-endDate").mobiscroll(opt1);
		pageInit();
	 	if(!returnChary){
	 		queryChat(begindate,enddate,queryListSuccesss);
	 	}else{
	 		setTimeout(
	 			function(){
	 				changePage(returnChary);
	 			},20) 		
	 	}
	 	
	 	
	});
    function changePage(pageType){
    	switch (pageType) {
					case "1":
						$("#xuetang_btn").click();
						break;
					case "2":
						$("#xueya_btn").click();
						break;
					case "3":
						$(".tizhong").click();
						break;
					case "4":
						$(".yaowei").click();
						break;
					case "5":
						$("#healthLi").click();
						break;
					case "6":
						$("#healthLi").click();
						$(".yongyao").click();			
						break;
					case "7":
						$("#healthLi").click();
						$(".yinshi").click();
						break;
				}	
    }
    
    //绑定事件
    function bindEvents() {
    	//标签切换
		$(".div-list").show();
      $(".pt-tab li").click(function(){
			var id = $(this).attr("id");
			$(".pt-tab li").removeClass("hit");
 			$(this).addClass("hit");
			if(id=="tizhengLi"){
				$("#xuetang_btn").click();
				$("#tizhengList").show();
				$("#patinetsList").hide();
			}else{
				$("#tizhengList").hide();
				$("#patinetsList").show();
				$(".yundong").click();
			}
		});	
		
		//点击标题事件
		$(".div-btn-group").on("click",".c-btn-full",function(){
			var activeItem = $(this).find(".item-icon");
			$(this).removeClass("active");
			$(this).addClass("active");
			$(".curve-box").hide();
			$(".c-table").hide();
			if($(this).hasClass("xuetang")){//点击血糖
				$(".curve-box.div-xuetang").show();
				$(".table-xuetang").show();
				charType=1;
				pageInit(); 
			  $("#qushi").click();
			  queryChat(begindate,enddate,queryListSuccesss);
//			  queryListByType(charType,page, pagesize, queryListSuccesss,begindate,enddate);
				
			}else if($(this).hasClass("xueya")){//点击血压
				$(".curve-box.div-xueya").show();
				$(".table-xueya").show();
				charType=2;
				pageInit()		
				$("#qushi").click();
				queryChat(begindate,enddate,queryListSuccesss1);
//				queryListByType(charType,page, pagesize, queryListSuccesss1,begindate,enddate);		
			}else if(activeItem.hasClass("tizhong")){//点击体重
				$(".curve-box.div-tizhong").show();
				$(".table-tizhong").show();			
				charType=3;
				pageInit();
				$("#qushi").click();
				queryChat(begindate,enddate,queryListSuccesss2);
//				queryListByType(charType,page, pagesize, queryListSuccesss2,begindate,enddate);			
			}else if(activeItem.hasClass("yaowei")){//点击腰围
				$(".curve-box.div-yaowei").show();
				$(".table-yaowei").show();
				charType=4;
				pageInit();
				$("#qushi").click();
				queryChat(begindate,enddate,queryListSuccesss3);
//				queryListByType(charType,page, pagesize, queryListSuccesss3,begindate,enddate);			
			}else if(activeItem.hasClass("yundong")){//点击运动
				charType=5;
				$(".table-yundong").show();
				pageInit();
				querySportList(page,pagesize,begindate,enddate);
			}else if(activeItem.hasClass("yongyao")){//点击用药
				charType=6;
				$(".table-yongyao").show();
				pageInit();
				queryYongyaoList(page,pagesize,begindate,enddate);
			}else if(activeItem.hasClass("yinshi")){//点击饮食
				charType=7;
				pageInit();
				$(".table-yinshi").show();
				queryYinshiList(page,pagesize,begindate,enddate);
			}
		});
		
		//点击趋势图/列表事件
		$(".div-btn-group").on("click",".c-btn",function(){
			var id = $(this).attr("id");
			$(".div-btn-group").find(".c-btn").removeClass("active");
			$(this).addClass("active");
			if(id=="qushi"){//显示趋势图
				$(".div-charts").show();
				$(".div-list").hide();
			}else{//显示列表
				$(".div-list").show();
				$(".div-charts").hide();
				setTimeout(function () {
					scroller1.refresh();
				}, 1000);	
			}
		
		});
		
		//点击新增事件
    	$(".div-add-btn").click(function(){
    		$(".modal-overlay").css("background","transparent");
    		$("#footer").css("z-index",4).hide();
    		var activeItem = $(".pt-tab li.hit").attr("id");
    		if(activeItem=="tizhengLi"){
    			if($(this).hasClass("active")){
					$(".div-dialog-content.div-tizhengtezheng").hide();
				}else{
					$(".div-dialog-content.div-tizhengtezheng").show();
				}
    		}else{
    			if($(this).hasClass("active")){
					$(".div-dialog-content.div-baojianjilu").hide();
				}else{
					$(".div-dialog-content.div-baojianjilu").show();
				}
    		}
    		
    		if($(this).hasClass("active")){
				$(".modal-overlay").removeClass("modal-overlay-visible");
				$(this).removeClass("active");
			}else{
				$(this).addClass("active");
				$(".modal-overlay").addClass("modal-overlay-visible");
			}
    		
    		
    	});
    	
			//点击遮罩事件
			$(".modal-overlay").on("click",function(){
				$("#footer").show();
				if($(".dt-date-condition").css("display")=="none"){
					$(".div-dialog-content").hide();
					$(".div-add-btn").removeClass("active");
				}else{
					$(".dt-date-condition").hide();
				}
				$(".modal-overlay").removeClass("modal-overlay-visible");
				
		   });
		   
		   //时间选择框
		   $(".div-date").on("click",function(){
		   		$("#footer").css("z-index",3);
		   		$(".dt-date-condition").show();
		   		$(".modal-overlay").css("background","rgba(0, 0, 0, 0.4)");
		   		$(".modal-overlay").addClass("modal-overlay-visible");
		   		$(".dt-date-condition dt").removeClass("del");
		   		var labdateItem = "";
		   		var activeId = $(".pt-tab .hit").attr("id");
		   		if(activeId=="tizhengLi"){//体征特诊
		   			labdateItem = $("#tizhengList").find(".lbl-date").attr("data-id");
		   		}else{//保健记录
		   			labdateItem = $("#patinetsList").find(".lbl-date").attr("data-id");
		   		}
			    $(".dt-date-condition dt[data-id="+labdateItem+"]").find(".icon").addClass("del");
			     $(".dt-date-condition dt[data-id="+labdateItem+"]").trigger("click");
			   if(begindate){
				   $(".lab-startDate").val(begindate);
		   	 	 $(".lab-endDate").val(enddate);
	   	 		}
		   });
		   
		   //取消事件
		   	$(".quxiao").on("click",function(){
		   			$(".modal-overlay").trigger("click");
		   	 });
		   
		   //时间选择框-取消按钮事件
		   $("#cancle-btn").on("click",function(){
			   	$(".dt-date-condition").hide();
			   	$(".modal-overlay").removeClass("modal-overlay-visible");
		   	});
		   	
		   	//时间选择框-确定按钮事件
		   	$("#confirm-btn").on("click",function(){
	   			var selectOp = $(".dt-date-condition .del");
	   			var resultDate = "";
	   			var dataId = "";
	   			if(selectOp.length>0){//选中最近一周/最近一月
	   				var index = selectOp.closest("dt").index();//0是最近一周，1是最近一月
	   				if(index==0){
	   				  begindate = year+"-"+getDateBefore(6).substring(5,10);
       				enddate  = year+"-"+getDateBefore(0).substring(5,10);
	   					resultDate = getDateBefore(6).substring(5,10)+"~"+getDateBefore(0).substring(5,10);
	   					$(".lab-startDate").val(getDateBefore(6));
	   					$(".lab-endDate").val(getDateBefore(0));
	   					dataId = "1";
	   				}else{
	   					begindate = year+"-"+getDateBefore(29).substring(5,10);
       				enddate  = year+"-"+getDateBefore(0).substring(5,10);
	   					resultDate = getDateBefore(29).substring(5,10)+"~"+getDateBefore(0).substring(5,10);
	   					$(".lab-startDate").val(getDateBefore(29));
	   					$(".lab-endDate").val(getDateBefore(0));
	   					dataId = "2";
	   				}
	   			}else{//选中自定义
 					var date1=new Date($(".lab-startDate").val());
					var date2=new Date($(".lab-endDate").val());
	   				if(Date.parse(date1)>Date.parse(date2)){
	   					dialog({contentType:'tipsbox',bottom:true, skin:'bk-popup' , content:'开始时间不能大于结束时间'}).show();
	   					return false;
	   				}
	   				resultDate = $(".lab-startDate").val().substring(5,10)+"~"+$(".lab-endDate").val().substring(5,10);
	   				begindate = year+"-"+$(".lab-startDate").val().substring(5,10);
       			enddate  = year+"-"+$(".lab-endDate").val().substring(5,10);
	   				dataId = "3";
	   			}
	   			var labdateItem = "";
		   		var activeId = $(".pt-tab .hit").attr("id");
		   		if(activeId=="tizhengLi"){//体征特诊
		   			$("#tizhengList").find(".lbl-date").html(resultDate).attr("data-id",dataId);
		   		}else{//保健记录
		   			$("#patinetsList").find(".lbl-date").html(resultDate).attr("data-id",dataId);
		   		}
		   		pageInit();
		   		if(charType==1){
		   			 queryChat(begindate,enddate,queryListSuccesss);
		   		}else if(charType==2){
		   			 queryChat(begindate,enddate,queryListSuccesss1);
		   		}else if(charType==3){
		   		  queryChat(begindate,enddate,queryListSuccesss2);
		   		}else if(charType==4){
		   			 queryChat(begindate,enddate,queryListSuccesss3);
		   		}else if(charType==5){
		   			querySportList(page,pagesize,begindate,enddate);
		   		}else if(charType==6){
				  	queryYongyaoList(page,pagesize,begindate,enddate);
		   		}
		   		else if(charType==7){
		   			queryYinshiList(page,pagesize,begindate,enddate);
		   		}
	   			 $("#cancle-btn").trigger("click");
		   	});
		   	
		   	//切换查询条件
		   $(".dt-date-condition").on("click",".tit",function(){
	   			var activeId = $(this).attr("data-id");
	   			$(".dt-date-condition .icon").removeClass("del");
	   			if(activeId=="1" || activeId=="2"){//最近一周或最近一月
	   				$(this).find(".icon").addClass("del");
	   				switchery.element.checked = false
	   				switchery.setPosition();
	   				$(".dt-date-condition .dt-custom-content").hide();  
	   			}else{
	   				if(!switchery.element.checked){
	   					$(".dt-date-condition dt").eq(0).trigger("click"); 
	   					return false;
	   				}
	   				switchery.element.checked = true
	   				switchery.setPosition();
	   				$(".dt-date-condition .dt-custom-content").show();  
	   			}
		   	});
		   	
		   //新增血糖
		   $(".addxuetang").on("click",function(){
		   		$(this).addClass("active");
		   		window.open('health-index-add-bloodsugar.html');
		   	});
			
			//新增血压
		   $(".addxueya").on("click",function(){
		   		$(this).addClass("active");
		   		window.open('health-index-add-bloodpressure.html');
		   	});
		   	
		   	//新增体重
		   $(".addtizhong").on("click",function(){
		   		$(this).addClass("active");
		   		window.open('health-index-add-weight.html');
		   	});
		   	
		   	//新增腰围
		   $(".addyaowei").on("click",function(){
		   		$(this).addClass("active");
		   		window.open('health-index-add-waistline.html');
		   	});
		   	
		   	//新增设备管理
		   $(".addshebei").on("click",function(){
		   		$(this).addClass("active");
		   		window.open('../../wdsb/html/my-equipments.html');
		   	});
		   	
		   	//新增运动
		   $(".addyundong").on("click",function(){
		   		$(this).addClass("active");
		   		window.open('add-sport-record.html');
		   	});
		   	
		   	//新增用药
		   $(".addyongyao").on("click",function(){
		   		$(this).addClass("active");
		   		window.open('add-drug-record.html');
		   	});
		   	
		   	//新增饮食
		   $(".addyinshi").on("click",function(){
		   		$(this).addClass("active");
		   		window.open('add-diet-record.html');
		   	});
		   	   
		   	$("#view_more1").on("click", function() {    
          	queryList1(page,pagesize);
    		});
    		
    		$("#view_more2").on("click", function() {    
        	queryList2(page,pagesize);
    		});
    		
    		$("#view_more3").on("click", function() {  
        	queryList3(page,pagesize);
    		});
    		
    		$("#view_more4").on("click", function() {  
        	queryList4(page,pagesize);
    		});
    		$("#view_more_sport").on("click", function() {  
        	querySportList(page,pagesize,begindate,enddate);
    		});
    		$("#view_more_yongyao").on("click", function() {  
        	queryYongyaoList(page,pagesize,begindate,enddate);
    		});
    		$("#view_more_yinshi").on("click", function() {  
        	queryYinshiList(page,pagesize,begindate,enddate);
    		});
		   	
		   	   
		   	   
    }
   
	
	//echar数组
var charTypeJson = {
	1:['早餐前血糖', '早餐后血糖', '午餐前血糖', '午餐后血糖', '晚餐前血糖', '晚餐后血糖', '睡前血糖'],//血糖
	2:['收缩压','舒张压'],//血压
	3:['体重'],//体重
	4:['腰围']//腰围
}


	
	function queryChat(date_begin, date_end,queryList) {
	if (parseFloat(date_begin.replace(/-/g, "")) > parseFloat(date_end.replace(/-/g, ""))) {
		dialog({contentType:'tipsbox',bottom:true, skin:'bk-popup' , content:'开始时间不能晚于结束时间'}).show();
		return;
	}
	//发送请求
	queryChatByType(date_begin, date_end, queryChartSuccesss,queryList);
	}
	
	/**
	 * 健康指标图表查询成功处理方法
	 */
	function queryChartSuccesss(res) {	
		if (res.status == 200) {
			d.close();
			//成功
			showChart(res.list);
		} else {
			//非200则为失败
			queryChartFailed(res);
		}
	}
	
	
	/**
	 * 健康指标图表查询失败处理方法
	 */
	function queryChartFailed(res) {
		d.close();
		if (res && res.msg) {
			dialog({contentType:'tipsbox',bottom:true, skin:'bk-popup' , content:res.msg}).show();
		} else {
			dialog({contentType:'tipsbox',bottom:true, skin:'bk-popup' , content:'加载失败'}).show();
		}
	}
	
	/**
	 * 查询健康指标图表
	 * @param {Object} type 健康指标类型（1血糖，2血压，3体重，4腰围）
	 * @param {Object} begin 记录开始时间
	 * @param {Object} end 记录结束时间
	 */
	function queryChatByType(begin, end, successFunction,queryList) {
		//拼请求内容
		var params = {openId:openId,random:random};
		params.type = charType;
		params.begin = begin+" 00:00:00";
		params.end = end+" 23:59:59";
		d.show();
		
		//发送ajax请求
		sendPost("patient/health_index/chart", params, "json", "post", queryChartFailed, function(res){
			if (res.loginUrl) {
				window.location.href = res.loginUrl;
				return;
			}
			successFunction(res);
			queryListByType(charType,page, pagesize, queryList,begindate,enddate);
		});
	}
	
	//血糖展示	
	function showChart(list) {
		var legendArray = charTypeJson[charType];
		var xAxisArray = new Array();
		var seriesArray = new Array();
	  var titleArr = ['单位(mmol/L)','单位(mmHg)','单位(kg)','单位(cm)'];
		var allDatas = new Array();
		if(charType==1){
			var data1 = new Array();
			var data2 = new Array();
			var data3 = new Array();
			var data4 = new Array();
			var data5 = new Array();
			var data6 = new Array();
			var data7 = new Array();

			for (var i = 0; i < list.length; i++) {
					var data = list[i];
					if (!data) {
						continue;
					}
					xAxisArray.push(data.date.substr(5, 5));
					data1.push(buildData(data.value1, 6.1, 3.9));
					data2.push(buildData(data.value2, 7.8, 4.4));
					data3.push(buildData(data.value3, 6.1, 3.9));
					data4.push(buildData(data.value4, 7.8, 4.4));
					data5.push(buildData(data.value5, 6.1, 3.9));
					data6.push(buildData(data.value6, 7.8, 4.4));
					data7.push(buildData(data.value7, 6.1, 3.9));
				}

				allDatas.push(data1);
				allDatas.push(data2);
				allDatas.push(data3);
				allDatas.push(data4);
				allDatas.push(data5);
				allDatas.push(data6);
				allDatas.push(data7);
		}
		if(charType==2){
			var data1 = new Array();
			var data2 = new Array();
			for (var i = 0; i < list.length; i++) {
				var data = list[i];
				if (!data) {
					continue;
				}
			xAxisArray.push(data.date.substr(5, 11));
			data1.push(buildData(data.value1, 139, 90));
			data2.push(buildData(data.value2, 89, 60));
			}
			allDatas.push(data1);
			allDatas.push(data2);
		}
		if(charType==3){
			var data1 = new Array();
			for (var i = 0; i < list.length; i++) {
				var data = list[i];
				if (!data) {
					continue;
				}
				xAxisArray.push(data.date.substr(5, 5));
				data1.push(data.value1);
			}
			allDatas.push(data1);
		}
		if(charType==4){var data1 = new Array();
		for (var i = 0; i < list.length; i++) {
				var data = list[i];
				if (!data) {
					continue;
				}
				xAxisArray.push(data.date.substr(5, 5));
				data1.push(data.value1);
			}
			allDatas.push(data1);
		}
		for (var i = 0; i < legendArray.length; i++) {
			var lineColor = (charType==2?(i==1?"CC66FF":"#5dd1d2"):"#5dd1d2");
			var series = {
				name: legendArray[i],
				type: 'line',
				symbol: 'emptyCircle',
				layerPadding: 0,
				nodePadding: 0,
				itemStyle: {
					normal: {
						borderWidth: 8,
						color: '#0ad800',
						lineStyle: { // 系列级个性化折线样式，横向渐变描边
							borderWidth: 2,
							color: lineColor,
							width: 4
						},
						nodeStyle: {
							borderWidth: 2,
							color: '#93DB70',
							borderColor: '#93DB70'
						}
					},
					emphasis: {
						label: {
							show: true
						}
					}
				},
				data: allDatas[i]
			};
			seriesArray.push(series);
		}
		// 路径配置
		//require.config({
		//	paths: {
		//		echarts: 'http://echarts.baidu.com/build/dist'
		//	}
		//});
		//// 使用
		//require(
		//	[
		//		'echarts',
		//		'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
		//	],
		//	function(ec, num) {
		//		var temp =0;
		//		if(charType==2){
		//			var myChart = require('echarts').init(document.getElementById('chart8'));
		//			var option = {
		//				tooltip: {
		//					trigger: 'axis'
		//				},
		//				grid: {
		//					x: 30,
		//					y: 20,
		//					x2: 25,
		//					y2: 20
		//				},
		//				legend: {
		//					show: false,
		//					data: legendArray
		//				},
		//				toolbox: {
		//					show: false
		//				},
		//				calculable: false,
		//				xAxis: [{
		//					type: 'category',
		//					boundaryGap: false,
		//					data: xAxisArray,
		//					axisLabel: {
		//						margin: 6
		//					}
		//				}],
		//				yAxis: [{
		//					name : titleArr[charType-1],
		//					type: 'value'
		//				}],
		//
		//				series: seriesArray
		//			};
		//			// 为echarts对象加载数据
		//			myChart.setOption(option);
		//		}
		//		else{
	//			for (var i = 0; i < seriesArray.length; i++) {
	//				// 基于准备好的dom，初始化echarts图表
	//				if(charType==1){
	//					temp = i;
	//				}
	//				if(charType==3){
	//					temp = 8;
	//				}
	//				if(charType==4){
	//					temp = i+9;
	//				}
	//				var myChart = ec.init(document.getElementById('chart' + (temp + 1)));
	////				console.log(myChart);
	//				var option = {
	//					tooltip: {
	//						trigger: 'axis'
	//					},
	//					grid: {
	//						x: 30,
	//						y: 20,
	//						x2: 25,
	//						y2: 20
	//					},
	//					legend: {
	//						show: false,
	//						data: legendArray
	//					},
	//					toolbox: {
	//						show: false
	//					},
	//					calculable: false,
	//					xAxis: [{
	//						type: 'category',
	//						boundaryGap: false,
	//						data: xAxisArray,
	//						axisLabel: {
	//							margin: 6
	//						}
	//					}],
	//					yAxis: [{
	//						name : titleArr[charType-1],
	//						type: 'value'
	//					}],
	//
	//					series: [seriesArray[i]]
	//				};
	//
	//				// 为echarts对象加载数据
	//				myChart.setOption(option);
	//			}
	//			}
	//		}
	//	);
	}
	
	
	function buildData(value, max, min) {
		if ((value > 0 && value < min) || value > max) {
			return {
				value: value,
				itemStyle: {
					normal: {
						color: '#f00'
					}
				}
			}
		}else{
			return value;
		}
	}
	

function pageInit(){
	  page=0;
	  pagesize=10;
		if(charType==1){
				$(".xuetang-more").show();
				$(".xueya-more").hide();
				$(".tizhong-more").hide();
				$(".yaowei-more").hide();
				$("#view_more").html("点击查看更多");
				$("#item").html("");
		}
		else if(charType==2){
				$(".xuetang-more").hide();
				$(".xueya-more").show();
				$(".tizhong-more").hide();
				$(".yaowei-more").hide();
				$("#view_more1").html("点击查看更多");
				$("#item1").html("");
		}
		else if(charType==3){
				$(".xuetang-more").hide();
				$(".xueya-more").hide();
				$(".tizhong-more").show();
				$(".yaowei-more").hide();
				$("#view_more2").html("点击查看更多");
				$("#item2").html("");
		}
		else if(charType==4){
				$(".xuetang-more").hide();
				$(".xueya-more").hide();
				$(".tizhong-more").hide();
				$(".yaowei-more").show();
				$("#view_more3").html("点击查看更多");
				$("#item3").html("");
		}
		else if(charType==5){
				$(".sport-more").show();
				$(".yongyao-more").hide();
				$(".yinshi-more").hide();
				$("#view_more_sport").html("点击查看更多");
				$("#sport_list").html("");
		}else if(charType==6){
				$(".sport-more").hide();
				$(".yongyao-more").show();
				$(".yinshi-more").hide();
				$("#view_more_yongyao").html("点击查看更多");
				$("#drug_list").html("");
		}else if(charType==7){
				$(".sport-more").hide();
				$(".yongyao-more").hide();
				$(".yinshi-more").show();
				$("#view_more_yinshi").html("点击查看更多");
				$("#diet_list").html("");
		}
		
}
