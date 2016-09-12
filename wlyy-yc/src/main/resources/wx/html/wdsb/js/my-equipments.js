saveAgentPage("../../wdsb/html/my-equipments.html");
var d = dialog({contentType:'load', skin:'bk-popup'}).show(),
	// 设备列表分页查询的最后一条记录的id 
	lastId = 0,
	// 没有任何结果时的显示
	$noResultWrap = $('#no_result_wrap'),
	// 设备列表视图容器，这个是用来初始化Iscroll插件时需要的容器对象
	$deviceListView = $('#device_list_view'),
	// 设备列表
	$deviceList = $('#device_list');

// 获取列表的最后一个数据对象的id字段值（用于列表的分页请求参数）
var getLastItemId = function(list) {
	var lastObj;
	if(list&&list.length) {
		lastObj = list[list.length-1];
	}
	return lastObj?lastObj.id:lastId;
},
// 更新分页上拉加载的提示文本
updatePullUpText= function(scroller,list) {
	scroller.on('refresh',function() {
		var $wrap = $(scroller.wrapper),
		$pullupLabel = $wrap.find('.pullUpLabel');
		if(!list || !list.length) {
			$pullupLabel.text('没有更多');
		} else {
			$pullupLabel.text('上拉加载更多');
		}
	});
},

//初始化左滑/右滑事件
initTouch = function (){
	//滑动事件
	$.extend({CycTounch:function(EV,X,Y){
		var valx=50;
		console.log(EV.target)
		if((Math.abs(X)>Math.abs(Y))&& Math.abs(X)>50){
			var obj=$(EV.target).closest("li");
			if(X>0){
				obj.removeClass("on")
				obj.animate({"left":"0"})
				obj.find(".c-arrow-r").animate({"right":"10px"})
				obj.find(".edit").animate({"right":"-150px"})
				obj.find(".del").animate({"right":"-75px"})
				console.log('右滑');
			}else{
				obj.addClass("on")
				obj.animate({"left":"-75px"})
				// 有编辑按钮时
				// obj.find(".c-arrow-r").animate({"right":"82px"})
				obj.find(".c-arrow-r").animate({"right":"10px"})
				obj.find(".edit").animate({"right":"0"})
				obj.find(".del").animate({"right":"-75px"})
				console.log('左滑');
			}
		}
	}});
},
// 删除设备
deleteDevice = function(code) {
	if(!code) return;
	return getReqPromise("patient/device/DeletePatientDevice",{id:code})
			.then(function(res) {
				if (res.loginUrl) {
					window.location.href = res.loginUrl;
					return;
				}
				if(res.status==200){
					location.reload();
				}else{
					dialog({contentType:'tipsbox', skin:'bk-popup' , content:'删除设备失败'}).show();
				}
			})
			.catch(function(e) {
				console && console.log(e);
			});
},

//绑定事件
bindEvents = function (){

	//绑定编辑和删除事件
	$(".c-list").on("click","li",function(){
			var code = $(this).attr("data-id"), 
				deviceId = $(this).attr("device-id"),
				type = $(this).attr("data-type");
			
			if(type==1){
				location.href="edit-xuetangyi.html?deviceId="+deviceId+"&id="+code;
			}else if(type==2){
				//location.href="edit-xueyaji.html?id="+code+"&type="+type;
				location.href="edit-xueyaji.html?deviceId="+deviceId+"&id="+code;
			}
	}).on("click","li .del",function(e){
		var code = $(this).attr("data-id");
			dialog({
			    content: '解绑设备信息，将会导致医生无法实时<br/>关注到您的体征变化。是否继续解绑？',
			    okValue:'确定',
			    ok: function (){		    	
			      	deleteDevice(code);
			    },
			    cancelValue: '取消',
			    cancel: function () {
					return;
			    }
		 	}).showModal();
		return false;
	})
	
	//新增按钮事件
	$(".div-add-btn").on("click",function(){
		$(this).hide();
		if($(this).hasClass("active")){
			$(".modal-overlay").trigger("click");
		}else{
			$(this).addClass("active");
			$(".modal-overlay").addClass("modal-overlay-visible");
			$(".div-dialog-content").show();
		}
	});
	
	//点击遮罩事件
	$(".modal-overlay").on("click",function(){
		$(".modal-overlay").removeClass("modal-overlay-visible");
		$(".div-dialog-content").hide();
		$(".div-add-btn").removeClass("active");
		$(".div-add-btn").show();
   });
   
   //取消事件
   	$(".quxiao").on("click",function(){
   		$(".modal-overlay").trigger("click");
   	 });
   
   //点击血糖仪事件
   $(".xuetangyi-icon").on("click",function(){
   		$(this).addClass("active");
   		window.location.href = "list-xuetangyi.html?category_code=1";
   	});
   	
   	 //点击血压计事件
   $(".xueyaji-icon").on("click",function(){
   		$(this).addClass("active");
   		window.location.href = "list-xueyaji.html?category_code=2";
   });
};

// 请求设备列表
getReqPromises([{url:"patient/device/PatientDeviceList",data:{id:0,pagesize:15,openId:openId,random:random}}])
//请求成功后处理
.then(function(datas) {
	// TODO 设备列表数据示例
		var data = datas[0];
		if (data.loginUrl) {
			window.location.href = data.loginUrl;
			return;
		}
	//data = {"msg":"查询成功","isFirst":true,"total":1,"list":[{"id":491,"deviceId":3,"deviceSn":"九龙江","deviceName":"血糖仪-爱奥乐G-777G","user":"250ff5e5278d415880e49643a4b9c071","categoryCode":"1","userType":"-1","userIdcard":"350628199011010029","czrq":"2016-09-05 11:27:13"}],"isLast":true,"totalPages":1,"status":200};
		lastId = getLastItemId(data.list);
	if(data && data.list.length) {
		$noResultWrap.hide();
		var html = template("device_li_tmpl", data);
		$deviceList.html(html);
	} else {
		$noResultWrap.show();
		d.close();
		return ;
	}
	
	var deviceListScroller = $deviceListView.initScroll({pullDown: false,pullUpAction: function() {
		getReqPromise("patient/device/PatientDeviceList", {id:lastId,pagesize:15,openId:openId,random:random}).then(function(data) {
			if (data.loginUrl) {
				window.location.href = data.loginUrl;
				return;
			}
			// TODO 设备列表数据示例
			//data = {"msg":"查询成功","isFirst":true,"total":1,"list":[{"id":491,"deviceId":3,"deviceSn":"九龙江","deviceName":"血糖仪-爱奥乐G-777G","user":"250ff5e5278d415880e49643a4b9c071","categoryCode":"1","userType":"-1","userIdcard":"350628199011010029","czrq":"2016-09-05 11:27:13"}],"isLast":true,"totalPages":1,"status":200};
			lastId = getLastItemId(data.list);
			
			var html = template("device_li_tmpl", data);
			$deviceList.append(html);
			updatePullUpText(deviceListScroller,data.list);
			deviceListScroller.refresh();
		})
	}});
	d.close();
})
.then(function() {
	//初始化左滑/右滑事件
	initTouch();
	// 绑定页面上相关事件
	bindEvents();
})
.catch(function(e) {
	console && console.error(e);
});

window.addEventListener('load',load, false);