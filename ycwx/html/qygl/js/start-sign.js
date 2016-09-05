 var d1 = dialog({contentType:'load', skin:'bk-popup'});
	var d = dialog({contentType:'load', skin:'bk-popup', content:'提交签约信息，请稍后...'});
	var Request = new Object();
	Request = GetRequest();
	var doctor = Request["doctor"];
	var doctorName = Request["doctorName"];
	var hospitalName = Request["hospitalName"];
	var idcard = Request["idcard"];
	// 从医生主页传递的签约邀请code
	var signInvitationCode = Request["inviCode"];
	var patientCode = Request["patientCode"];
	if(!patientCode || patientCode=="undefined") {
		patientCode = "";
	}
	$(function(){
		if (decodeURI(doctorName)) {
			document.getElementById("doctorName").innerText = decodeURI(doctorName);
		}
		if (decodeURI(hospitalName)) {
			document.getElementById("hospitalName").innerText = decodeURI(hospitalName);
		}
		if(signInvitationCode&&signInvitationCode!="undefined") {
			$('#name_label').text('代理家人');
		}
		query();

		$("#idcard").on("blur", function() {
			var value = $(this).val();
			validateIdcard(value);
		});

	})
	
	//查询用户信息
	function query() {
		d1.show();
		var data={};
		if(signInvitationCode&&signInvitationCode!="undefined") {
			sendPost('patient/getPatientByInviLogCode', {invilogCode: signInvitationCode}, 'json', 'post', queryFailed, querySuccess);
		} else {
			sendPost('patient/baseinfo', data, 'json', 'post', queryFailed, querySuccess);
		}
		
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
		if (res.status == 200) {
			var data = res.data;
			var name = data.name;
			var mobile = data.mobile;
			var idcard = data.idcard;
			var idcardAll = data.idcardAll;
			var ssc = data.ssc;
			if(!name) name = "";
			if(!mobile) mobile = "";
			if(!ssc) ssc = "";
			if(!idcard) idcard = "";
			document.getElementById("name").value = name;			
			document.getElementById("mobile").value = mobile;
			document.getElementById("ssc").value = ssc;
			document.getElementById("idcard").value = idcard;
			$('#idcard').attr('data-idcard',idcardAll);
			if(!ssc || ssc ==""){
				dialog({
					content: '对不起，未办理医保卡或在2016年6月之后办理医保卡的居民暂不能进行签约。请保证在您的帐户中（我的资料）正确录入您的医保卡号，然后再重试！',
					cancelValue: '我知道了',
					cancel: function () {
						wx.closeWindow();
					}
				}).showModal();
			}
			d1.close();
		} else {
			queryFailed(res);
		}
	}

	
	//提交签约
	function submitSign(){
		var data = {};
		data.name = $("#name").val();
		data.ssc = $("#ssc").val();
		data.mobile = $("#mobile").val();
		//data.idcard = $("#idcard").val();
		data.idcard = $('#idcard').attr('data-idcard');
		if(validate(data)){
			d.showModal();
			//加密设置:获取公钥
			var encryURL = server + "login/public_key";
			var key = RSAUtils.getKeyFromServer(encryURL);
			//拼请求内容
			data.idcard = RSAUtils.encryStr(key, data.idcard);
			data.streetCode = "123456";
			data.stateCode = "01";
			data.doctor = doctor;
			data.doctorName = "";
			data.hospital = "";
			data.hospitalName = "";
			data.patientCode = patientCode;
			sendPost("patient/family_contract/sign", data, "json", "post", operateFailed, operateSuccesss);	
		}		
	}
	
	//失败
	function operateFailed(res) {
		d.close();
		if (res && res.msg) {
			dialog({contentType:'tipsbox', skin:'bk-popup' , content:res.msg}).show();      
		} else {
			dialog({contentType:'tipsbox', skin:'bk-popup' , content:'提交失败'}).show();			
		}
		return;
	}

	function operateSuccesss(res) {
		if (res.status == 200) {
			d.close();
			window.location.href = "../../ssgg/html/doctor-homepage-new.html?waitSign=1&state=" + doctor;
//			window.location.href = "doctor-home-page.html?doctor=" + doctor;
		} else {
			//非200则为失败
			operateFailed(res);
		}
	}