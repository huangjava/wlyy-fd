var rowData={};
var sn="";//记录sn码
var userType=0;
var dataId = null;
var type = null;
var name = null;
var deviceId = null;
var photoUrl = null;
var oldSn="";//旧sn
$(function() {
	Request = GetRequest(); 
	dataId = Request["id"];
	deviceId = Request["deviceId"];
	getDeviceInfo(deviceId);
	bindEvents();
	if(dataId){
		$(document).attr("title","编辑血压计");
		initData(dataId);
		$("#bang-btn").html("保存");
	}else{
		$(document).attr("title","新增血压计");
	}
	isValueChange();
});

function initData(dataId){
	sendPost("patient/device/patientDeviceInfo?id="+dataId,{},"JSON","POST",
		function(res){
			dialog({contentType:'tipsbox', skin:'bk-popup' , content:'设备信息初始化失败！'}).show();
		},
		function(res){
			if(res.status==200){
				rowData = res.data;
				$("#sncode").val(rowData.deviceSn);
				checkSnBind(rowData.deviceSn);
				userType = rowData.userType;
				oldSn = rowData.deviceSn;
				if(userType==1){
					$(".father").addClass("active");
				}
				if(userType==2){
					$(".monther").addClass("active");
				}
			}else{
				dialog({contentType:'tipsbox', skin:'bk-popup' , content:'设备信息初始化失败！'}).show();
			}
		}
		
	)
}

function getDeviceInfo(deviceId){
	sendPost("/common/device/deviceInfo?id="+deviceId,{},"JSON","POST",
		function(res){
			dialog({contentType:'tipsbox', skin:'bk-popup' , content:'设备信息获取失败！'}).show();
		},
		function(res){
			if(res.status==200){
				var rowData1 = res.data;
				type = rowData1.categoryCode;
				name = rowData1.name;
				photoUrl = rowData1.photo;
				var multiUser = JSON.parse(rowData1.multiUser);
				for(var key in multiUser){
					if(key==1){
						$(".key-one").html(multiUser[key]);
					}else{
						$(".key-two").html(multiUser[key]);
					}
				}
				$(".deviceName").html(name);
				$(".div-xueyaji-img img").attr("src",photoUrl)
				
			}else{
				dialog({contentType:'tipsbox', skin:'bk-popup' , content:'设备信息获取失败！'}).show();
			}
		}

	)
}


//绑定事件
function bindEvents(){
	//$(".c-quick-list").find(".li-key").addClass("invalid");//SN码输入错误时，则绑定快捷键不可点击
	//添加isrcoll
	var scroller1 = new IScrollPullUpDown('wrapper',{
			probeType:2,
			bounceTime: 250,
			bounceEasing: 'quadratic',
			mouseWheel:false,
			scrollbars:true,
			click:true,
			fadeScrollbars:true,
			interactiveScrollbars:false
	},null,null);
	
	//点击绑定快捷键事件
	$(".c-quick-list").on("click",".li-key",function(){
		var newDom = $(this).find(".key-img");
		var canclick = newDom.attr("data-click");
		var userName = $(this).find(".div-band-info").html();
		if(canclick==0){
			var title = '该快捷键'+userName+'注册，需解除与该快捷键的绑定关系后，方能完成绑定';
			dialog({
			    content: title,
			    cancelValue: '我知道了',
			    cancel: function () {
					return;
			    }
			}).showModal();
			return;
		}else if(canclick==-1){
			var title = '该序列号已经被您绑定，请到编辑界面修改';
			dialog({
			    content: title,
			    cancelValue: '我知道了',
			    cancel: function () {
					return;
			    }
			}).showModal();
			return;	
		}
		$(".c-quick-list").find(".li-key").find(".key-img").removeClass("active");
		userType = newDom.attr("data-type");
		$(this).find(".key-img").addClass("active");
		var snCode =  $("#sncode").val();
		if(snCode!=null&&snCode!=""){
			$("#bang-btn").addClass("active");
		}
	})
	
	//保存方法
	$("#bang-btn").bind("click",function(){
		if($(this).hasClass("active")){
			var snCode =  $("#sncode").val();
			if(snCode==null||snCode==""){
				dialog({contentType:'tipsbox', skin:'bk-popup' , content:'请输入设备的SN码进行绑定！'}).show();
			}else{
				checkSnBind(snCode,checkSuccess);
			}
		}
	})	
	
	$("#sncode").on("input",function(){
		var snCode =  $(this).val();
		isValueChange();
		if(snCode!=null||snCode!=""){
			 //userType=0;
			$(".c-quick-list").find(".li-key").find(".key-img").removeClass("active");
			$(".father").attr("data-click","1");
			$(".monther").attr("data-click","1");
			$(".div-father-band").hide();
			$(".div-monther-band").hide();
			checkSnBind(snCode);
		}
	})
}

function isValueChange(){
	var self = this;
	var int= setInterval(function() {
		var changeTagStr =  $("#sncode").val();
		if(oldSn!=changeTagStr){
			clearInterval(int);
			int = null;
			$("#bang-btn").addClass("active");
		}

	},300);
}

function checkSnBind(snCode,suc){
	var params = {openId:openId, random:random};
	params.type=type;
	params.device_sn = snCode;
	sendPost("patient/device/patientDeviceIdCard",params,"JSON","POST",
		function(res){
			if (res.loginUrl) {
				window.location.href = res.loginUrl;
				return;
			}
			dialog({contentType:'tipsbox', skin:'bk-popup' , content:res}).show();
			$("#bang-btn").removeClass("active");
		},function(res){
			if (res.loginUrl) {
				window.location.href = res.loginUrl;
				return;
			}
			if(res.status==200){
				for(var j in res.data){
					var data = res.data[j];
					if(data['type']==1){//爸爸键
						if(data['others']==1){//被别人绑定
							if(userType==1) userType = 0 ;
							$(".father").attr("data-click","0");
							$(".fatherbf").html("已经被"+data['name']+"绑定");
							$(".div-father-band").show();
						}else{//被自己绑定
							if(dataId==null){//新增
								$(".father").attr("data-click","-1");
								$(".monther").attr("data-click","-1");
								$(".father").addClass("active");
								$("#bang-btn").removeClass("active");
								dialog({
								    content: "该序列号已经被您绑定，请到编辑界面修改",
								    cancelValue: '我知道了',
								    cancel: function () {
										return;
								    }
								}).showModal();
							}else if(!suc){
								userType = 1;
							//选中爸爸键
								$(".father").addClass("active");
							}
						}
					}
					else if(data['type']==2){//妈妈键
						if(data['others']==1){//被别人绑定
							if(userType==2) userType = 0 ;
							$(".monther").attr("data-click","0");
							$(".montherbf").html("已经被"+data['name']+"绑定");
							$(".div-monther-band").show();		
						}
						else{//被自己绑定
							if(dataId==null){//新增
								$(".monther").attr("data-click","-1");
								$(".father").attr("data-click","-1");
								$(".monther").addClass("active");
								$("#bang-btn").removeClass("active");
								dialog({
								    content: "该序列号已经被您绑定，请到编辑界面修改",
								    cancelValue: '我知道了',
								    cancel: function () {
										return;
								    }
								}).showModal();
							}else if(!suc){
								userType = 2;
								//选中妈妈键
								$(".monther").addClass("active");				
							}
						}
					}if(suc){
						suc(snCode);
					}					
				}
				if(res.data.length==0&&suc){
					suc(snCode);
				}				
			}else{
				dialog({contentType:'tipsbox', skin:'bk-popup' , content:'设备SN校验失败！'}).show();
				$("#bang-btn").removeClass("active");
			}
		}
	);
	
	
}



function checkSuccess(snCode){
	if(userType==0){
		dialog({contentType:'tipsbox', skin:'bk-popup' , content:'请选择要绑定的快捷键'}).show();	
		return false;
	}
	name="血压计-"+name;
	var params ={};
	params.deviceId=deviceId;//设备ID
	params.deviceName=name;//设备名称
	params.deviceSn=snCode;//设备SN码
	params.categoryCode=type;//设备分类 血糖仪1   血压计 2
	params.userType=userType;//是否多用户,1.爸爸建，2.妈妈键
	if(rowData.id){
		params.id = rowData.id;
	}
	sendPost("/patient/device/savePatientDevice",{"json":JSON.stringify(params)},"JSON","post",
		function(res){
			if (res.loginUrl) {
				window.location.href = res.loginUrl;
				return;
			}
			dialog({contentType:'tipsbox', skin:'bk-popup' , content:'设备绑定失败！'}).show();	
		},
		function(res){
			if (res.loginUrl) {
				window.location.href = res.loginUrl;
				return;
			}
			if(res.status == '200'){
				dialog({contentType:'tipsbox', skin:'bk-popup' , content:'设备绑定成功！'}).show();
				setTimeout(function(){
					window.location.href='my-equipments.html';
				},1000);
			}else{
				dialog({contentType:'tipsbox', skin:'bk-popup' , content:res.msg}).show();
			}

		}
	)
}