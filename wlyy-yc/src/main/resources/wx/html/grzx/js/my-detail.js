var d = dialog({contentType:'load', skin:'bk-popup'});
saveAgentPage("../../grzx/html/my-detail.html");
var teamName,orgName,teamCode;
var Request = GetRequest();
if(Request["openid"]){
	openId = decodeURIComponent(Request["openid"]);
	window.localStorage.setItem("openid", openId);
}
if(Request["r"]){
	random = Request["r"];
	window.localStorage.setItem("random", random);
}

$(function() {	
	queryInit();
});	

function queryInit(){
	d.show();
    //查询用户信息
	query();
	getSignStatus();
}

//跳转到家庭签约信息
function changeJtqy(sign) {
	if(sign == 1) {
		window.location.href = "../../qygl/html/doctor-homepage.html?teamCode="+teamCode+"&orgName="+orgName;
	} else if (sign == 0) {
		window.location.href = "../../qygl/html/search-community.html"
	}
}

//查询列表
function query() {
	var data={openId:openId,random:random};
	sendPost('patient/family/baseinfo', data, 'json', 'post', queryFailed, querySuccess);
}

function queryFailed(res) {
	d.close();
	if (res && res.msg) {
		dialog({contentType:'tipsbox', skin:'bk-popup' , content:res.msg}).show();      
	} else {
		dialog({contentType:'tipsbox', skin:'bk-popup' , content:'初始化失败'}).show(); 
	}
}

function querySuccess(res) {
	if (res.status == 200) {
		if (res.loginUrl) {
			window.location.href = res.loginUrl;
			return;
		}
		setValue(res.data);	
		d.close();
	} else {
		queryFailed(res);
	}
}

//查询列表
function getSignStatus() {
	var data={openId:openId,random:random};
	sendPost('/patient/sign/getSignStatus', data, 'json', 'post', getFailed, getSuccess);
}

function getFailed(res) {
	d.close();
	if (res && res.msg) {
		dialog({contentType:'tipsbox', skin:'bk-popup' , content:res.msg}).show();
	} else {
		dialog({contentType:'tipsbox', skin:'bk-popup' , content:'初始化失败'}).show();
	}
}

function getSuccess(res) {
	if (res.status == 200) {
		if (res.loginUrl) {
			window.location.href = res.loginUrl;
			return;
		}
		teamName = res.data.teamName;
		orgName = res.data.orgName;
		teamCode = res.data.teamCode;
		var sign = Number(res.data.signStatus);//签约状态
		var html = "";
		if(sign == 0){
			html =   '<div onclick="changeJtqy(false)">未签约，去签约</div>';
		}else if(sign == 1){
			html =  '<div onclick="changeJtqy(true)">'+teamName+'</div>';
		}
		document.getElementById("divSign").innerHTML = html;
	} else {
		getFailed(res);
	}
}

//初始化页面数据
function setValue(data){
	var photo = data.photo;
	var name = data.name;
	var mobile = data.mobile;
	var idcard = data.idCard;
	var address = data.address;

	if(!photo){
		photo = "../../../images/p-default.png";
	}	
	if(!name) name = "";
	if(!mobile) mobile = "";
	if(!idcard) idcard = "";
	if(!address) address = "";
	
	document.getElementById("photo").src = photo;
	document.getElementById("name").value = name;			
	document.getElementById("mobile").innerHTML = mobile;
	document.getElementById("idcard").innerHTML = idcard;
	document.getElementById("address").value = address;
}
