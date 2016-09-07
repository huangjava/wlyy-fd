// 页面载入显示提示“加载中” 
var d = dialog({contentType:'load', skin:'bk-popup'}).show(),
	// 团队成员列表
	$teamMemberList = $('#team_member_list'),
	// 点击显示全部
	$showAllMemberBtn = $('#show_all_member_btn');
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

var startSign = function() {
	var teamName = encodeURI(document.getElementById("teamName").innerHTML);
	var orgName = encodeURI(document.getElementById("orgName").innerHTML);
	window.location.href = "../../qygl/html/agreement.html?teamCode=" + teamName + "&teamName=" + teamName + "&orgCode=" + orgCode+ "&orgName=" + orgName+'&patientCode='+"1";
}

var showTeamInfo = function(data) {
	
	if(!data) return ;
	if(!data.data.url){
		document.getElementById("photo").src = "../../../images/d-male.png";
	}else{
		document.getElementById("photo").src = data.data.url;
	}
	document.getElementById("teamCode").innerHTML = data.data.teamCode;
	document.getElementById("teamName").innerHTML = data.data.teamName;
	document.getElementById("orgName").innerHTML = data.data.orgName;
	document.getElementById("orgCode").innerHTML = data.data.orgCode;
	document.getElementById("introduce").innerHTML = data.data.introduce;
	
};

// TODO url,data示例参数为空
getReqPromises([{url: 'patient/hospital/getTeamInfo',data:{teamCode:"1",orgCode:"1"}},
{url: 'patient/hospital/getDoctorList',data:{teamCode:"1",begin:0, end:1}},
{url: '/patient/sign/getSignStatus',data:{patientCode:"1"}}])
.then(function(datas) {
	showTeamInfo(datas[0]);
	isSignForView(datas[2]);
	if(!datas[1].list.length) {
		$noResultWrap.show();
		return ;
	}
	
	// 根据列表模板，填充数据，输出列表html代码
	var html = template("member_li_tmpl", datas[1]);
	// 将列表添加到页面
	$teamMemberList.append(html);
	// 关闭页面加载提示
	d.close();
})
//getReqPromise("patient/hospital/getDoctorList",{teamCode:"1",begin:0, end:1})
//
//// 数据成功返回，处理页面展示
//.then(function(data) {
//	// TODO 后台返回 data 示例数据
//	// 如果返回的列表为空则显示“抱歉，暂未找到符合条件的结果”
//	
//})

// 绑定页面相关事件
.then(function() {
	
	$showAllMemberBtn.click(function() {
		d.show();
		getReqPromise("patient/hospital/getDoctorList",{teamCode:"1",begin:0, end:1}).then(function(data) {
			// TODO 后台返回 data 示例数据
//			data = {"msg":"获取医院医生列表成功！","list":[{"code":"D2016080002","job_name":" 全科医师","introduce":"我是全科医生","name":"大米全科1","dept_name":"","photo":"http://172.19.103.85:8882/res/images/2016/08/12/20160812170142_901.jpg","id":1262,"expertise":"我是全科医生","hospital_name":"嘉莲社区医疗服务中心",relationship:'本人'},{"code":"D2016080005","job_name":" 全科医师","introduce":"我是全科医生","name":"大米全科2","dept_name":"","photo":"","id":1271,"expertise":"我是全科医生","hospital_name":"嘉莲社区医疗服务中心",relationship:'女儿'},{"code":"D2016080225","job_name":" 全科医师","introduce":"我是全科医生","name":"谭仁祝(全科)","dept_name":"","photo":"","id":1274,"expertise":"我是全科医生","hospital_name":"嘉莲社区医疗服务中心",relationship:'女儿'},{"code":"D2010080225","job_name":" 全科医师","introduce":"我是全科医生","name":"谭仁祝(全科1)","dept_name":"","photo":"","id":1276,"expertise":"我是全科医生","hospital_name":"嘉莲社区医疗服务中心",relationship:'女儿'}],"status":200};
			// 根据列表模板，填充数据，输出列表html代码
			var html = template("member_li_tmpl", data);
			// 将列表添加到页面
			$teamMemberList.append(html);
			d.close();
			$showAllMemberBtn.css({
				display: 'none'
			});
		})
	});
})

// 捕捉过程中产生的异常
.catch(function(e) {
	console && console.error(e);
});
	