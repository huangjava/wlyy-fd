$(function() {
	var Request = new Object();
		Request = GetRequest();
		var doctor = Request["doctor"];
		var doctorName = Request["doctorName"];
		var hospitalName = Request["hospitalName"];
		// 判断是否隐藏“我已认真阅读并同意该协议”的按钮
		var readonly = Request["readonly"],
		// 如果是签约邀请，则获取patientCode及inviCode参数
		patientCode = Request["patientCode"] || "", // 被邀请的患者code
		inviCode = Request["inviCode"]||""; // 被邀请的患者与医生的邀请code
	
	if(readonly!==undefined) {
		$('#agree_btn').hide();
	}
	$('#agree_btn').on('click',function() {

		var url = 
		location.href = "../../ssgg/html/start-sign3.html?doctor=" + doctor 
		+ "&doctorName=" + doctorName + "&hospitalName=" + hospitalName+"&patientCode="+patientCode+"&inviCode="+inviCode+"&"+$.now();
	});
})
