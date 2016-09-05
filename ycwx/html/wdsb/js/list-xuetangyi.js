var d = dialog({contentType:'load', skin:'bk-popup'});
var category_code = null;
$(function() {
	Request = GetRequest();
	category_code = Request["category_code"];
	var openid = Request["openid"];
	var userAgent = window.localStorage.getItem(agentName);
	if(!userAgent){
			window.localStorage.setItem(pageName,"{\"pageurl\":\""+window.location.href+"\"}");
			window.location.href = "../../home/html/login.html?type=0&openid=" + openid;
	}
	getDevices();
});
function getDevices(){
	var params = {};
	params.category_code = category_code;
	d.show();
	sendPost("/common/device/DeviceList",params,"JSON","GET",queryListFailed,queryListSuccess);
}

function queryListSuccess(res){
	d.close();	
	if(res.status==200){
		if(res.data&&res.data.length>0){
			for(var i in res.data){
				showList(res.data[i]);
			}
		}else{
			dialog({contentType:'tipsbox', skin:'bk-popup' , content:'无更多设备！'}).show();		
		}
	}else{
		queryListFailed(res);
	}
}

function showList(rowData){

	var	showDomLi = '<li class="inp'+rowData.id+'" >'+
						'<div class="deviceTitle">'+rowData.name+'</div>'+
						'<div class="div-xuetangyi-img">'+
						'<img  src="'+rowData.photo+'" ></div>'+
					'</li>';
	$(".c-list").append(showDomLi);
	$(".inp"+rowData.id+" div").click(function(){
		location.href="edit-xuetangyi.html?deviceId="+rowData.id;
	})
}



function queryListFailed(res) {
	d.close();
	if (res &&  res.msg) {
		dialog({contentType:'tipsbox', skin:'bk-popup' , content:res.msg}).show();
	} else {
		dialog({contentType:'tipsbox', skin:'bk-popup' , content:'加载设备型号列表失败'}).show();
	}
}

	
