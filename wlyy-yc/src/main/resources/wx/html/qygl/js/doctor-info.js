// 页面载入显示提示“加载中” 

var Request = GetRequest();
var teamCode = Request["teamCode"];
var orgName = decodeURIComponent(Request["orgName"]||"");
var teamName =decodeURIComponent(Request["teamName"]||""),
	orgCode = Request["orgCode"],
	doctorCode = Request["doctorCode"];


$(function() {
	saveAgentPage("../../qygl/html/doctor-info.html");
	
	getReqPromise("patient/hospital/getDoctorInfo",{doctorCode:doctorCode,openId:openId,random:random})

	// 数据成功返回，处理页面展示
	.then(function(data) {
		// TODO 后台返回 data 示例数据
		// 如果返回的列表为空则显示“抱歉，暂未找到符合条件的结果”
		if(!data) return ;
		if (data.loginUrl) {
			window.location.href = data.loginUrl;
			return;
		}
		if(!data.data.url){
			document.getElementById("photo").src = "../../../images/d-male.png";
		}else{
			document.getElementById("photo").src = data.data.url;
		}
		document.getElementById("doctorCode").innerHTML = doctorCode;
		document.getElementById("doctorName").innerHTML = data.data.doctorName;
		document.getElementById("orgCode").innerHTML = data.data.orgCode;
		document.getElementById("orgName").innerHTML = orgName;
		document.getElementById("teamCode").innerHTML = data.data.teamCode;
		document.getElementById("teamName").innerHTML = teamName;
		document.getElementById("jobName").innerHTML = data.data.jobName;
		document.getElementById("expertise").innerHTML = data.data.expertise;
		document.getElementById("introduce").innerHTML = data.data.introduce;
		
	})
	
	// 捕捉过程中产生的异常
	.catch(function(e) {
		console && console.error(e);
	});
})

var startSign = function() {
	var teamName = encodeURI(document.getElementById("teamName").innerHTML);
	var orgName = encodeURI(document.getElementById("orgName").innerHTML);
	var orgCode = document.getElementById("orgCode").innerHTML;

	window.location.href = "../../qygl/html/start-sign.html?teamCode=" + teamName + "&teamName=" + teamName + "&orgCode=" + orgCode+ "&orgName=" + orgName+'&patientCode='+"1";
}

var isSignForView = function(data) {
	if(!data) return ;
	var sign = data.data.signStatus;
	if(sign == 0) { // 未签约
		document.getElementById("btnSign").innerHTML = '<a onclick="startSign()" class="c-btn c-btn-4dcd70 c-btn-full c-btn-radius c-f18">申请签约</a>';
		$('#btnSign').show();
		$('#btnSign').closest("div").addClass("h64");
		$("#divAgree").hide();

	} else if(sign == 1) { // 已签约
		//document.getElementById("btnSign").innerHTML = '<a onclick="overSign()" class="c-btn c-btn-E0A526 c-btn-full c-btn-radius c-f18">申请解约</a>';
		$('#btnSign').hide();
		$('.btn-main').show();
		$('#btnSign').closest("div").removeClass("h64");
		$("#divAgree").show();
	} else if(sign == 2) {//待签约
		document.getElementById("btnSign").innerHTML = '<a onclick="cancelSign()" class="c-btn c-btn-E0A526 c-btn-full c-btn-radius c-f18">取消申请</a>';
		$('#btnSign').show();
		$('.btn-main').show();
		$('#btnSign').closest("div").removeClass("h64");
		$("#divAgree").show();
	}
}

getReqPromises([{url: '/patient/sign/getSignStatus',data:{}}])
	.then(function(datas) {
		if (datas[0].loginUrl) {
			window.location.href = datas[0].loginUrl;
			return;
		}

		isSignForView(datas[0]);
		// 关闭页面加载提示
	}).then(function() {

	})


// 捕捉过程中产生的异常
	.catch(function(e) {
		console && console.error(e);
	});
	