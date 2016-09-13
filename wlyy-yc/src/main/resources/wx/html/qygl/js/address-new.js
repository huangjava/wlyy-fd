var d = dialog({contentType:'load', skin:'bk-popup'});
saveAgentPage("../../qygl/html/address-new.html");
var openid =null;
var userAgent = window.localStorage.getItem(agentName);
if(userAgent){
	var jsonstr = $.parseJSON(userAgent);
	openid = jsonstr.openid;
}
$(function() {
	getCity();
	checkSaveBtnStatus();
});	

//跳转到三师信息
function changeSanshi(){
	window.location.href = "../html/signing-doctors.html";
}
//跳转到家庭签约信息
function changeJtqy(signedStatus){
	if(signedStatus) {
		window.location.href = "../../ssgg/html/doctor-homepage-new.html"
	} else {
		window.location.href = "../html/signing-doctors.html"
	}
	//window.location.href = "../../ssgg/html/choose-region.html";
}

//初始化页面数据
function setValue(data){
	var city = data.city;
	var area = data.area;
	var street = data.street;
	var committee = data.committee;
	var cityName = data.cityName;
	var areaName = data.areaName;	
	var streetName = data.streetName;
	var committeeName = data.committeeName;
	var address = data.address;
	
	if(!city) city = "";
	if(!area) area = "";
	if(!cityName) cityName = "";
	if(!areaName) areaName = "";
	if(!street) street = "";
	if(!streetName) streetName = "";
	if(!committee) committee = "";
	if(!committeeName) committeeName = "";
	if(!address) address = "";
	
	document.getElementById("city").value = cityName;
	document.getElementById("cityCode").value = city;
	document.getElementById("town").value = areaName;
	document.getElementById("townCode").value = area;
	document.getElementById("street").value = streetName;
	document.getElementById("streetCode").value = street;
	document.getElementById("committee").value = committeeName;
	document.getElementById("committeeCode").value = committe;
	document.getElementById("address").value = address;
	
	
	// 利用定时器监听页面输入变化，控制保存按钮是否可用
	checkSaveBtnStatus();
}

function checkSaveBtnStatus () {
	
	var timmer = setInterval(function() {
		var city = $('#cityCode').val();
		var town = $('#townCode').val();
		var street = $('#streetCode').val();
		var committee = $('#committeeCode').val();
		var address = $('#address').val();
		if(city!=''&&town!=''&&street!=''&&committee!=''&&address!='') {
			clearInterval(timmer);
			timmer = null;
			$('#saveCommit').removeClass('c-btn-disable').addClass('c-btn-4dcd70');
			$('#saveCommit').on('click',function() {
				saveCommit();
			});
		}
	},300);
}

var oldPrivince = "";
var oldCity = "";
var oldTown = "";
var oldStreet = "";
//获取省份信息
function getCity(){
	d.show();
	var data={openId:openId,random:random};
	data.type = 1;
	data.code = "";
	sendPost('common/district', data, 'json', 'post', queryAreaFailed, queryCitySuccess);
}
//获取省市区错误
function queryAreaFailed(res) {
	d.close();
	if (res && res.msg) {
		dialog({contentType:'tipsbox',bottom:true, skin:'bk-popup' , content:res.msg}).show();      
	} else {
		dialog({contentType:'tipsbox',bottom:true, skin:'bk-popup' , content:'获取失败'}).show(); 
	}
}

function queryCitySuccess(res) {
	if (res.status == 200) {
		if (res.loginUrl) {
			window.location.href = res.loginUrl;
			return;
		}
		setCity(res.list);	
		d.close();
	} else {
		queryAreaFailed(res);
	}
}
//填充城市下拉框
function setCity(list){
	var arr_key=[];
    var arr_value=[];
    for(var i=0; i<list.length; i++){
        arr_key.push(list[i].code);
        arr_value.push(list[i].name);                      
    }
	$('#city').mobiscroll({
		theme: 'ios',
		lang: 'zh',
		formatValue: function(d) {
			return d.join(',');
		},
		customWheels: true,
		wheels: [
			[{
				keys: arr_key,
				values: arr_value
			}]
		],
		onSelect: function(valueText, inst) {
			var dd = eval("[" + valueText + "]");
			$("#city").val(dd[0].values);
			$("#cityCode").val(dd[0].keys);
			$("#town").val("");
			$("#townCode").val("");
			$("#street").val("");
			$("#streetCode").val("");
		}
	});
	$("#city").mobiscroll("show");	
}

//获取区县信息
function getTown(){
	d.show();
	var data={openId:openId,random:random};
	data.type = 2;
	data.code = $('#cityCode').val();
	oldCity = $('#cityCode').val();
	sendPost('common/district', data, 'json', 'post', queryAreaFailed, queryTownSuccess);
}

function queryTownSuccess(res) {
	if (res.status == 200) {
		if (res.loginUrl) {
			window.location.href = res.loginUrl;
			return;
		}
		setTown(res.list);	
		d.close();
	} else {
		queryAreaFailed(res);
	}
}

//填充城市下拉框
function setTown(list){
	var arr_key=[];
    var arr_value=[];
    for(var i=0; i<list.length; i++){
        arr_key.push(list[i].code);
        arr_value.push(list[i].name);                      
    }    
	$('#town').mobiscroll({
		theme: 'ios',
		lang: 'zh',
		formatValue: function(d) {
			return d.join(',');
		},
		customWheels: true,
		wheels: [
			[{
				keys: arr_key,
				values: arr_value
			}]
		],
		onBeforeShow: function (inst) {
			var curCity = $('#cityCode').val();
	        if(curCity != oldCity){
	        	getTown();
	        }
	    },
		onSelect: function(valueText, inst) {
			var dd = eval("[" + valueText + "]");
			$('#town').val(dd[0].values);
			$('#townCode').val(dd[0].keys);
			$("#street").val("");
			$("#streetCode").val("");
		}
	});
	$("#town").mobiscroll("show");
}
//获取街道信息
function getStreet(){
	d.show();
	var data={openId:openId,random:random};
	data.type = 3;
	data.code = $('#townCode').val();
	oldTown = $('#townCode').val();
	sendPost('common/district', data, 'json', 'post', queryAreaFailed, queryStreetSuccess);
}

function queryStreetSuccess(res) {
	if (res.status == 200) {
		if (res.loginUrl) {
			window.location.href = res.loginUrl;
			return;
		}
		setStreet(res.list);	
		d.close();
	} else {
		queryAreaFailed(res);
	}
}
//填充城市下拉框
function setStreet(list){
	var arr_key=[];
    var arr_value=[];
    for(var i=0; i<list.length; i++){
        arr_key.push(list[i].code);
        arr_value.push(list[i].name);                      
    }    
	$('#street').mobiscroll({
		theme: 'ios',
		lang: 'zh',
		formatValue: function(d) {
			return d.join(',');
		},
		customWheels: true,
		wheels: [
			[{
				keys: arr_key,
				values: arr_value
			}]
		],
		onBeforeShow: function (inst) {
			var curTown = $('#townCode').val();
	        if(curTown != oldTown){
	        	getStreet();
	        }
	    },
		onSelect: function(valueText, inst) {
			var dd = eval("[" + valueText + "]");
			$('#street').val(dd[0].values);
			$('#streetCode').val(dd[0].keys);
		}
	});
	$("#street").mobiscroll("show");
}

//获取街道信息
function getCommittee(){
	d.show();
	var data={openId:openId,random:random};
	data.type = 4;
	data.code = $('#streetCode').val();
	oldStreet = $('#streetCode').val();
	sendPost('common/district', data, 'json', 'post', queryAreaFailed, queryCommitteeSuccess);
}

function queryCommitteeSuccess(res) {
	if (res.status == 200) {
		if (res.loginUrl) {
			window.location.href = res.loginUrl;
			return;
		}
		setCommittee(res.list);	
		d.close();
	} else {
		queryAreaFailed(res);
	}
}
//填充城市下拉框
function setCommittee(list){
	var arr_key=[];
    var arr_value=[];
    for(var i=0; i<list.length; i++){
        arr_key.push(list[i].code);
        arr_value.push(list[i].name);                      
    }    
	$('#committee').mobiscroll({
		theme: 'ios',
		lang: 'zh',
		formatValue: function(d) {
			return d.join(',');
		},
		customWheels: true,
		wheels: [
			[{
				keys: arr_key,
				values: arr_value
			}]
		],
		onBeforeShow: function (inst) {
			var curStreet = $('#streetCode').val();
	        if(curStreet != oldStreet){
	        	getCommittee();
	        }
	    },
		onSelect: function(valueText, inst) {
			var dd = eval("[" + valueText + "]");
			$('#committee').val(dd[0].values);
			$('#committeeCode').val(dd[0].keys);
		}
	});
	$("#committee").mobiscroll("show");
}

var dd = dialog({contentType:'load', skin:'bk-popup', content:'保存中...'});
//保存网格信息
function saveCommit(){
	dd.showModal();
	var params = {};
	params.city = $("#cityCode").val();
	params.town = $("#townCode").val();
	params.street = $("#streetCode").val();
	params.committee = $("#committeeCode").val();
	params.address = $("#address").val();
	sendPost('patient/family/district', params, 'json', 'post', submitFailed1, submitSuccess1);
}

function submitFailed1(res) {
	dd.close();
	if (res && res.msg) {
		dialog({contentType:'tipsbox',bottom:true, skin:'bk-popup' , content:res.msg}).show();      
	} else {
		dialog({contentType:'tipsbox',bottom:true, skin:'bk-popup' , content:'自动选择机构失败'}).show(); 
	}
}

function submitSuccess1(res) {
	if (res.status == 200) {
		if (res.loginUrl) {
			window.location.href = res.loginUrl;
			return;
		}
		dd.close();
		window.location.href = "../html/search-community.html";
		window.scrollTo(0,0);
		//window.location.reload();
	} else {
		submitFailed1(res);
	}
}
