saveAgentPage("../../qygl/html/start-sign.html");
var d1 = dialog({contentType:'load', skin:'bk-popup'});
var d = dialog({contentType:'load', skin:'bk-popup', content:'提交签约信息，请稍后...'});
var Request = new Object();
Request = GetRequest();
var teamCode = Request["teamCode"];
var teamName = Request["teamName"];
var orgName = Request["orgName"];
var orgCode = Request["orgCode"];
var code = Request["code"];
if(code){
	closeWindow();
}
var patientCode = Request["patientCode"];
if(!patientCode || patientCode=="undefined") {
	patientCode = "";
}
$(function(){
	if (decodeURI(teamName)) {
		document.getElementById("teamName").innerText = decodeURI(teamName);
	}
	if (decodeURI(orgName)) {
		document.getElementById("orgName").innerText = decodeURI(orgName);
	}
	query();

	//$("#idCard").on("blur", function() {
	//	var value = $(this).val();
	//	validateIdcard(value);
	//});

	$('#agree_sign_btn').click(function(e) {
		$(this).toggleClass("checked");
	});

});

function toXieYiShu(){
	location.href = "../../qygl/html/argument.html";
}

//查询用户信息
function query() {
	d1.show();
	var data={};
	sendPost('patient/family/baseinfo', data, 'json', 'post', queryFailed, querySuccess);
}

function queryFailed(res) {
	d1.close();
	if (res && res.msg) {
		dialog({contentType:'tipsbox', skin:'bk-popup' , content:res.msg}).show();
	} else {
		dialog({contentType:'tipsbox', skin:'bk-popup' , content:'用户信息获取失败'}).show();
	}
}

function querySuccess(res) {
	if (res.loginUrl) {
		window.location.href = res.loginUrl;
		return;
	}
	if (res.status == 200) {
		var data = res.data;
		var name = data.name;
		var mobile = data.mobile;
		var idCard = data.idCard;
		var idCardAll = data.idCardAll;
		var address = data.address;
		if(!name) name = "";
		if(!mobile) mobile = "";
		if(!address) address = "";
		if(!idCard) idCard = "";
		document.getElementById("name").value = name;
		document.getElementById("mobile").value = mobile;
		document.getElementById("address").value = address;
		document.getElementById("idCard").value = idCard;
		$(".userName").html(name);
		$('#idCard').attr('data-idCard',idCardAll);
		d1.close();
	} else {
		queryFailed(res);
	}
}

//验证信息
function validate(data) {
	if (data.name == "") {
		dialog({contentType:'tipsbox', skin:'bk-popup' , content:'请输入您的姓名'}).show();
		return false;
	}
	if (data.address == "") {
		dialog({contentType:'tipsbox', skin:'bk-popup' , content:'家庭地址填写!'}).show();
		return false;
	}
//		if (data.idCard != "" && !isIdcard(data.idCard)) {
//		    dialog({contentType:'tipsbox', skin:'bk-popup' , content:'身份证号格式不正确'}).show();
//			return false;
//		}
//		if (data.mobile != "" && !isphone(data.mobile)) {
//		    dialog({contentType:'tipsbox', skin:'bk-popup' , content:'手机号格式不正确'}).show();
//			return false;
//		}
	return true;
}

function validateIdcard(v){
	if ( v == "" ||( v != "" && !isIdcard(v))) {
		dialog({contentType:'tipsbox', skin:'bk-popup' , content:'身份证号格式不正确'}).show();
		return false;
	}
}

function validatePhone(v){
	if(v != "" && !isphone(v)){
		dialog({contentType:'tipsbox', skin:'bk-popup' , content:'手机号格式不正确'}).show();
		document.getElementById("mobile").focus();
		return false;
	}
	return true;
}


//提交签约
function submitSign(){
	var isAgree = $("#agree_sign_btn").hasClass("checked");
	if(!isAgree){
		dialog({contentType:'tipsbox', skin:'bk-popup' , content:'请选择同意签约协议'}).show();
		return;
	}
	var data = {};
	data.name = $("#name").val();
	data.address = $("#address").val();
	data.mobile = $("#mobile").val();
	//data.idCard = $("#idCard").val();
	data.idCard = $('#idCard').attr('data-idCard');
	if(validate(data)){
		d.showModal();
		//加密设置:获取公钥
//			var encryURL = server + "login/public_key";
//			var key = RSAUtils.getKeyFromServer(encryURL);
		//拼请求内容
//			data.idCard = RSAUtils.encryStr(key, data.idCard);
		data.streetCode = "123456";
		data.stateCode = "01";

		data.teamCode = teamCode;
		data.teamName = "";
		data.hospital = "";
		data.orgName = "";
		data.patientCode = patientCode;
		debugger
		getReqPromise("patient/sign/sendApplication",{contactPhone:data.mobile, patientName:data.name,teamCode:teamCode }).then(function(data) {
			if (data.loginUrl) {
				window.location.href = data.loginUrl;
				return;
			}
			location.href = "../../qygl/html/sign-success.html";
		})
	}
}

//失败
function operateFailed(res) {
	if (res.loginUrl) {
		window.location.href = res.loginUrl;
		return;
	}
	d.close();
	if (res && res.msg) {
		dialog({contentType:'tipsbox', skin:'bk-popup' , content:res.msg}).show();
	} else {
		dialog({contentType:'tipsbox', skin:'bk-popup' , content:'提交失败'}).show();
	}
	return;
}

//function operateSuccesss(res) {
//	if (res.status == 200) {
//		d.close();
//		//TODO 签约成功操作
//		getReqPromise("patient/sign/sendApplication",{teamCode:teamCode,teamName:teamName, orgCode:orgCode, orgName:orgName, patientCode:patientCode,openId:openId,random:random}).then(function(data) {
//			if (data.loginUrl) {
//				window.location.href = data.loginUrl;
//				return;
//			}
//			location.href = "../../qygl/html/sign-success.html";
//		})
////			window.location.href = "doctor-home-page.html?doctor=" + doctor;
//	} else {
//		//非200则为失败
//		operateFailed(res);
//	}
//}


function closeWindow() {
	var Request = new Object();
	Request = GetRequest();
	var code = Request["code"];
	//从后台那边获取签名等信息
	var params = {};
	params.pageUrl = window.location.href;
	$.ajax(server + "weixin/getSign", {
		data: params,
		dataType: "json",
		type: "post",
		success: function(res) {
			if (res.loginUrl) {
				window.location.href = res.loginUrl;
				return;
			}
			if(res.status == 200) {
				var t = res.data.timestamp;
				var noncestr = res.data.noncestr;
				var signature = res.data.signature;
				wx.config({
					//debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
					appId: appId, // 必填，公众号的唯一标识
					timestamp: t, // 必填，生成签名的时间戳
					nonceStr: noncestr, // 必填，生成签名的随机串
					signature: signature, // 必填，签名，见附录1
					jsApiList: [
						'closeWindow'
					] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
				});
			}
		}
	});
}