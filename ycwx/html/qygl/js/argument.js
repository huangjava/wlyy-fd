$(function() {
	var Request = new Object();
	Request = GetRequest();
	var teamCode = Request["teamCode"];
	var teamName = Request["teamName"];
	var orgName = Request["orgName"];
	var orgCode = Request["orgCode"];
	// 判断是否隐藏“我已认真阅读并同意该协议”的按钮
	var readonly = Request["readonly"];
	// 如果是签约邀请，则获取patientCode及inviCode参数
	var patientCode = Request["patientCode"] || "";// 被邀请的患者code
	
	if(readonly!==undefined) {
		$('#agree_btn').hide();
	}
	$('#agree_btn').on('click',function() {
		getReqPromise("patient/sign/sendApplication",{teamCode:teamCode,teamName:teamName, orgCode:orgCode, orgName:orgName, patientCode:patientCode}).then(function(data) {
			location.href = "../../qygl/html/sign-success.html";
		})

	});
})
