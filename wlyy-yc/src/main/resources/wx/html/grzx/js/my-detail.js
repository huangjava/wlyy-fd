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
}

//跳转到家庭签约信息
function changeJtqy(sign) {
	if(sign == 1) {
		window.location.href = "../../qygl/html/doctor-homepage.html?teamCode="+teamCode+"&orgName="+orgName;
	} else if (sign == 0) {
		window.location.href = "../../qygl/html/search-community.html"
	}
	//window.location.href = "../../ssgg/html/choose-region.html";
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
		if($("#ssc").val() != ""){
			$("#ssc").attr("readonly","readonly");
		}
		if($("#provinceCode").val() == ""){
			document.getElementById("province").value = "福建省";
	        document.getElementById("provinceCode").value = "350000";
		}
		if($("#cityCode").val() == ""){
			document.getElementById("city").value = "厦门市";
	        document.getElementById("cityCode").value = "350200";
		}
		d.close();
	} else {
		queryFailed(res);
	}
}
//初始化页面数据
function setValue(data){
	var photo = data.photo;
	var name = data.name;
	//var birthday = data.birthday;
	var mobile = data.mobile;
	var idcard = data.idCard;
	var ssc = data.socialSecurityCard;
	var province = data.province;
	var city = data.city;
	var area = data.town;
	var street = data.street;
	var provinceName = data.provinceCode;
	var cityName = data.cityCode;
	var areaName = data.townCode;
	var streetName = data.streetCode;
	var address = data.address;
	teamName = data.teamName;
	orgName = data.orgName;
	teamCode = data.teamCode;
	var sign = Number(data.sign);//签约状态

	if(!photo){
		photo = "../../../images/p-default.png";
	}	
	if(!name) name = "";
	//if(!birthday) birthday = "";
	if(!mobile) mobile = "";
	if(!idcard) idcard = "";
	if(!ssc) ssc = "";
	if(!province) province = "";
	if(!city) city = "";
	if(!area) area = "";
	if(!provinceName) provinceName = "";
	if(!cityName) cityName = "";
	if(!areaName) areaName = "";
	if(!street) street = "";
	if(!streetName) streetName = "";
	if(!address) address = "";
	
	document.getElementById("photo").src = photo;
	document.getElementById("name").value = name;			
	//document.getElementById("birthday").innerHTML = birthday;
	document.getElementById("mobile").innerHTML = mobile;
	document.getElementById("idcard").innerHTML = idcard;
	//document.getElementById("ssc").value = ssc;
	//document.getElementById("province").value = provinceName;
	//document.getElementById("provinceCode").value = province;
	//document.getElementById("city").value = cityName;
	//document.getElementById("cityCode").value = city;
	//document.getElementById("town").value = areaName;
	//document.getElementById("townCode").value = area;
	//document.getElementById("street").value = streetName;
	//document.getElementById("streetCode").value = street;
	document.getElementById("address").value = address;
	
	
	
	var html = "";
	if(sign == 0){
		html =   '<div onclick="changeJtqy(false)">未签约，去签约</div>';
	}else if(sign == 1){
		html =  '<div onclick="changeJtqy(true)">'+teamName+'</div>';
	}else{
		//window.location.href = "../../qygl/html/search-community.html";
	}
	document.getElementById("divSign").innerHTML = html;
	
	// 利用定时器监听页面输入变化，控制保存按钮是否可用
	checkSaveBtnStatus();
}

function checkSaveBtnStatus () {
	var values = [$('#provinceCode').val(),$('#cityCode').val(),$('#townCode').val(),$('#streetCode').val(),$('#address').val()];
	var valueStr = values.join(',');
	var timmer = setInterval(function() {
		var newValues = [$('#provinceCode').val(),$('#cityCode').val(),$('#townCode').val(),$('#streetCode').val(),$('#address').val()];
		var newValueStr = newValues.join(',');
		if(valueStr!=newValueStr) {
			clearInterval(timmer);
			timmer = null;
			$('#saveCommit').removeClass('c-btn-disable').addClass('c-btn-4dcd70');
			$('#saveCommit').on('click',function() {
				saveCommit();
			});
		}
	},300);
}
// 添加文件
function appendFile(p) {
	document.getElementById("photo").src = p;
}
//获取需要上传的图片
function getImages() {
	var images = [];
	var imgSrc = $("#photo").attr("src");
	images.push(imgSrc);
	return images;
}	
// 上传头像
var serverId = "";
function chooseImage(){
	wx.chooseImage({
	  count: 1, 
      success: function (res) {
      	appendFile(res.localIds[0]);
        uploadImage();
      }
    });
}	
//获取微信上传图片的媒体ID
function uploadImage(){
	var images = getImages();
	if (images.length == 0) {
      return;
    }
    var i = 0, length = images.length;
    serverId = "";
    function upload() {
      wx.uploadImage({
        localId: images[i],
        success: function (res) {
          i++;
          if(serverId.length == 0){
          	serverId = res.serverId;
          }
          else{
          	serverId =serverId + "," + res.serverId;
          }          
          if (i < length) {
            upload();
          }
          if(i == images.length){
          	update_photo();
          }          
        },
        fail: function (res) {
          alert(JSON.stringify(res));
        }
      });
    }
    upload();
}	
//上传图片到服务器上
function update_photo(){
	var params = {};
	params.mediaIds = serverId;	
	sendPost('patient/save', params, 'json', 'post', submitFailed, submitSuccess);
}
//上传失败
function submitFailed(res) {
	if (res && res.msg) {
		dialog({contentType:'tipsbox', skin:'bk-popup' , content:res.msg}).show();      
	} else {
		dialog({contentType:'tipsbox', skin:'bk-popup' , content:'上传失败'}).show(); 
	}
}
//上传成功
function submitSuccess(res) {
	if (res.status == 200) {
		
	} else {
		submitFailed(res);
	}
}	

var oldPrivince = "";
var oldCity = "";
var oldTown = "";
//获取省份信息
function getProvince(){
	d.show();
	var data={};
	data.type = 1;
	data.code = "";
	sendPost('common/district', data, 'json', 'post', queryAreaFailed, queryProvinceSuccess);
}
//获取省市区错误
function queryAreaFailed(res) {
	d.close();
	if (res && res.msg) {
		dialog({contentType:'tipsbox', skin:'bk-popup' , content:res.msg}).show();      
	} else {
		dialog({contentType:'tipsbox', skin:'bk-popup' , content:'获取失败'}).show(); 
	}
}

function queryProvinceSuccess(res) {
	if (res.status == 200) {
		setProvince(res.list);	
		d.close();
	} else {
		queryAreaFailed(res);
	}
}
//填充省份下拉框
function setProvince(list){
	var arr_key=[];
    var arr_value=[];
    for(var i=0; i<list.length; i++){
        arr_key.push(list[i].code);
        arr_value.push(list[i].name);                      
    }
	$('#province').mobiscroll({
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
			$('#province').val(dd[0].values);
			$('#provinceCode').val(dd[0].keys);
			$("#city").val("");
			$("#cityCode").val("");
			$("#town").val("");
			$("#townCode").val("");
			$("#street").val("");
			$("#streetCode").val("");
		}
	});
	$("#province").mobiscroll("show");	
}

//获取城市信息
function getCity(){
	d.show();
	var data={};
	data.type = 2;
	data.code = $('#provinceCode').val();
	oldPrivince = $('#provinceCode').val();
	sendPost('common/district', data, 'json', 'post', queryAreaFailed, queryCitySuccess);
}

function queryCitySuccess(res) {
	if (res.status == 200) {
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
		onBeforeShow: function (inst) {
			var curProvince = $('#provinceCode').val();
	        if(curProvince != oldPrivince){
	        	getCity();
	        }
	    },
		onSelect: function(valueText, inst) {
			var dd = eval("[" + valueText + "]");
			$('#city').val(dd[0].values);
			$('#cityCode').val(dd[0].keys);
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
	var data={};
	data.type = 3;
	data.code = $('#cityCode').val();
	oldCity = $('#cityCode').val();
	sendPost('common/district', data, 'json', 'post', queryAreaFailed, queryTownSuccess);
}

function queryTownSuccess(res) {
	if (res.status == 200) {
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
	var data={};
	data.type = 4;
	data.code = $('#townCode').val();
	oldTown = $('#townCode').val();
	sendPost('common/district', data, 'json', 'post', queryAreaFailed, queryStreetSuccess);
}

function queryStreetSuccess(res) {
	if (res.status == 200) {
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


var dd = dialog({contentType:'load', skin:'bk-popup', content:'保存中...'});
//保存用户信息
function saveCommit(){
	dd.showModal();
	var params = {};
	params.name = $("#name").val();	
	params.ssc = $("#ssc").val();	
	params.province = $("#provinceCode").val();
	params.city = $("#cityCode").val();
	params.town = $("#townCode").val();
	params.street = $("#streetCode").val();
	params.address = $("#address").val();
	sendPost('patient/save', params, 'json', 'post', submitFailed1, submitSuccess1);
}

function submitFailed1(res) {
	dd.close();
	if (res && res.msg) {
		dialog({contentType:'tipsbox', skin:'bk-popup' , content:res.msg}).show();      
	} else {
		dialog({contentType:'tipsbox', skin:'bk-popup' , content:'保存失败'}).show(); 
	}
}

function submitSuccess1(res) {
	if (res.status == 200) {
		if($("#ssc").val() != ""){
			$("#ssc").attr("readonly","readonly");
		}
		dd.close();
		dialog({contentType:'tipsbox', skin:'bk-popup' , content:'保存成功'}).show();
		window.scrollTo(0,0);
		//window.location.reload();
	} else {
		submitFailed1(res);
	}
}
//查看图片
function viewImg(dom) {
	var $img = $(dom);
	var thissrc = $img.attr("src");
	var mWid = $(window).width();
	var mHei = $(window).height();
	var nHtml = '<div class="delimgpop"><div class="del-img-box"><div class="del-img-con"><img class="del-pop-img" src="' + thissrc + '" style="max-width:' + mWid + 'px; max-height:' + mHei + 'px;"></div></div></div>';
	$("body").append(nHtml);
	$(".delimgpop").click(function() {
		$(this).remove()
	});
};
