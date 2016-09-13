var rowData={};
var sn="";//记录sn码
var dataId = null;
var type = null;
var name = null;
var type = null;
var photoUrl = null;
var deviceId = null;
var oldSn="";//旧sn
$(function() {
	Request = GetRequest(); 
	 dataId = Request["id"];
	deviceId = Request["deviceId"];
	getDeviceInfo(deviceId);
	bindEvents();
	if(dataId){
		$(document).attr("title","编辑血糖仪");
		initData(dataId);
		$("#bang-btn").html("保存");
	}else{
		$(document).attr("title","新增血糖仪");
	}

});


function initData(dataId){
	sendPost("patient/device/patientDeviceInfo?id="+dataId,{},"JSON","POST",
		function(res){
			if(res.msg){
				dialog({contentType:'tipsbox', skin:'bk-popup' , content:res.msg}).show();
			}else{
				dialog({contentType:'tipsbox', skin:'bk-popup' , content:'设备信息初始化失败！'}).show();
			}
		},
		function(res){
			if(res.status==200){
				rowData = res.data;
				oldSn = rowData.deviceSn;
				$("#sncode").val(rowData.deviceSn);
			}else{
				dialog({contentType:'tipsbox', skin:'bk-popup' , content:'设备信息初始化失败！'}).show();
			}
		}

	)
}

//判断值是否改变过
function isValueChange(){
	var changeTagStr =  $("#sncode").val();
	if(oldSn!=changeTagStr){
		$("#bang-btn").addClass("active");
	}
}

//绑定事件
function bindEvents(){
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
	
	//保存方法
	$("#bang-btn").bind("click",function(){
		if($(this).hasClass("active")){
			var snCode =  $("#sncode").val();
			if(snCode==null||snCode==""){
				dialog({contentType:'tipsbox', skin:'bk-popup' , content:'请输入设备的SN码进行绑定！'}).show();
			}else{
				checkSnBind(snCode);
				checkSnBind(snCode,checkSuccess);
			}
		}
	});
	
	$("#sncode").on("input",function(){
		var snCode =  $(this).val();
		isValueChange();
		if(snCode!=null||snCode!=""){
			checkSnBind(snCode,null);
		}
	})
}

function checkSnBind(snCode,checkSuccess){
	var params = {};
	params.type=type;
	params.device_sn = snCode;
	sendPost("patient/device/patientDeviceIdCard",params,"JSON","POST",
		function(res){
			dialog({contentType:'tipsbox', skin:'bk-popup' , content:'设备SN校验失败！'}).show();
		},function(res){
			if(res.status==200){
				var name = "" ;
				var others = null;
				for(var j in res.data){
					name = res.data[j].name;
					others = res.data[j].others
				}
				//没被绑定
				if(name==""){
					$("#bang-btn").addClass("active");
					if(checkSuccess!=null){
						checkSuccess(snCode);
					}
				}else if(others!=null&&others==1){
					$("#bang-btn").removeClass("active");
					//被绑定而且被别人绑定
					dialog({contentType:'tipsbox', skin:'bk-popup' , content:'设备SN已经被绑定请重新输入!'}).show();
				}
				else if(others!=null&&others==0){
					$("#bang-btn").removeClass("active");
					//新增，编辑/被自己绑定
					dialog({contentType:'tipsbox', skin:'bk-popup' , content:'设备SN已经被您绑定请移步编辑界面修改!'}).show();
				}
			}
		}
	);
	
	
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
				$(".deviceName").html(name);
				$(".div-xuetangyi-img img").attr("src",photoUrl);
			}else{
				dialog({contentType:'tipsbox', skin:'bk-popup' , content:'设备信息获取失败！'}).show();
			}
		}

	)
}

function checkSuccess(snCode){
	name="血糖仪-"+name;

	var params ={};
	params.deviceId=deviceId;//设备ID
	params.deviceName=name;//设备名称
	params.deviceSn=snCode;//设备SN码
	params.categoryCode=type;//设备分类 血糖仪1 血压计 2
	params.userType="-1";//是否多用户
	if(rowData.id){
		params.id = rowData.id;
	}
	sendPost("/patient/device/savePatientDevice",{"json":JSON.stringify(params)},"JSON","post",
		function(res){
			dialog({contentType:'tipsbox', skin:'bk-popup' , content:'设备绑定失败！'}).show();	
		},
		function(res){
			dialog({contentType:'tipsbox', skin:'bk-popup' , content:'设备绑定成功！'}).show();	
			setTimeout(function(){
				window.location.href='my-equipments.html';
			},1000);
		}
	)
}


	
