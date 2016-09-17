//正式环境（厦门i健康）
//var server = "http://www.xmtyw.cn/wlyy/";
//var appId = "wxad04e9c4c5255acf";
//var urlName = "wlyy";
//var weixinUrl = "www.xmtyw.cn";
//var agentName = "wlyyAgent";
//var agentName1 = "wlyyAgent1";
//var OpenidAgent = "OpenidAgent";
//var judgeAgent = "judgeAgent";
//var pageName = "pageurl";
//测试环境（健康之路当阳）
//var server = "http://weixin.xmtyw.cn/wlyy/";
//var appId = "wxd03f859efdf0873d";
//var urlName = "wlyy";
//var weixinUrl = "weixin.xmtyw.cn";
//var agentName = "wlyyAgent_test";
//var agentName1 = "wlyyAgent_test1";
//var OpenidAgent = "OpenidAgent_test";
//var judgeAgent = "judgeAgent_test";
//var pageName = "pageurl_test";

// 测试环境（健康之路宜昌）
//var server = "http://weixin.xmtyw.cn/wlyy/";
//var server = "http://ehr.yihu.com/yichang/fd/";
var server = "http://localhost:9111/fd/";

var appId = "wxd03f859efdf0873d";
var urlName = "wlyy";
var weixinUrl = "weixin.xmtyw.cn";
var agentName = "wlyyAgent_test";
var agentName1 = "wlyyAgent_test1";
var OpenidAgent = "OpenidAgent_test";
var judgeAgent = "judgeAgent_test";
var pageName = "pageurl_test";
var openId = window.localStorage.getItem("openid");
var random = window.localStorage.getItem("random");
//保存userAgent
function wxSaveUserAgent(uid, openid, token) {
	if(window.localStorage.getItem(agentName)){
		window.localStorage.removeItem(agentName);
	}
	var userAgent = "{\"uid\":\"" + uid + "\",\"openid\":\"" + openid + "\",\"token\":\"" + token + "\"}";
	window.localStorage.setItem(agentName, userAgent);
	return userAgent;
}

//保存userAgent1
function wxSaveUserAgent1(uid, name, photo) {
	if(window.localStorage.getItem(agentName1)){
		window.localStorage.removeItem(agentName1);
	}
	var userAgent = "{\"uid\":" + uid + ",\"name\":\"" + name + "\",\"photo\":\"" + photo + "\"}";
	window.localStorage.setItem(agentName1, userAgent);
	return userAgent;
}
//保存openid
function saveAgentOpenid(openid){
	if(window.localStorage.getItem(OpenidAgent)){
		window.localStorage.removeItem(OpenidAgent);
	}
	var agentOpenid = "{\"openid\":\"" + openid + "\"}";
	window.localStorage.setItem(OpenidAgent, agentOpenid);
}
//保存页面跳转值
function saveAgentJudge(judge){
	if(window.localStorage.getItem(judgeAgent)){
		window.localStorage.removeItem(judgeAgent);
	}
	var agentJudge = "{\"judge\":" + judge + "}";
	window.localStorage.setItem(judgeAgent, agentJudge);
}
//保存跳转页面到agent
function saveAgentPage(pageurl){
	if(window.localStorage.getItem(pageName)){
		window.localStorage.removeItem(pageName);
	}
	var agentPage = "{\"pageurl\":\"" + pageurl + "\"}";
	window.localStorage.setItem(pageName, agentPage);
}
//清空所有的agent
function clearAgent(){
	if(window.localStorage.getItem(agentName)){
		window.localStorage.removeItem(agentName);
	}
	if(window.localStorage.getItem(agentName1)){
		window.localStorage.removeItem(agentName1);
	}	
//	if(window.localStorage.getItem(OpenidAgent)){
//		window.localStorage.removeItem(OpenidAgent);
//	}
}

//获取链接上的参数
function GetRequest() {  
   var url = location.search; //获取url中"?"符后的字串
   var theRequest = new Object();
   if (url.indexOf("?") != -1) {
      var str = url.substr(1);
      strs = str.split("&");
      for(var i = 0; i < strs.length; i ++) {
         theRequest[strs[i].split("=")[0]]=(strs[i].split("=")[1]);
      }
   }
   return theRequest;
}
//判断用户是否登录
function checkUserAgent(){	
	var userAgent = window.localStorage.getItem(agentName);
    if(!userAgent){  
    	var agentOpenid = window.localStorage.getItem(OpenidAgent);
    	if(!agentOpenid){
    		var Request = new Object();
			Request = GetRequest(); 
			var code = Request["code"];
			if(code == "" || code == null || code == undefined){
				d.close();
				dialog({contentType:'tipsbox', skin:'bk-popup' , content:'授权失败'}).show();
			}
			else{
				d.show();
				var data = {};
				data.code = code;
				sendPost('weixin/getOpenidByCode', data, 'json', 'post', openidFailed, openidSuccess);
			}  
    	}
    	else{
    		var jsonstr = $.parseJSON(agentOpenid);
		    var openid = jsonstr.openid;
		    window.location.href = server + "wx/html/home/html/login.html?type=" + pagetype + "&openid=" + openid;
    	}    	  	
    }
    else{
		try{
			queryInit();
		}catch(e){
			
		}
	}
}

//成功
function openidSuccess(res) {
	if (res.status == 200) {
		var openid = res.openid;
		saveAgentOpenid(openid);
		window.location.href = server + "wx/html/home/html/login.html?type=" + pagetype + "&openid=" + openid;		
	} else {
		openidFailed(res);
	}
}

//失败
function openidFailed(res) {
	d.close();
	if (res && res.msg) {
		dialog({contentType:'tipsbox', skin:'bk-popup' , content:res.msg}).show();      
	} else {
		dialog({contentType:'tipsbox', skin:'bk-popup' , content:'获取认证信息失败'}).show(); 
	}
}

//判断用户是否有签约
function checkSign(type){	
	d.show();
	var userAgent = window.localStorage.getItem(agentName);
	if(userAgent){
		var data = {};
		$.ajax(server + "patient/is_sign", {
			data: data,
			type: 'POST',
			dataType: 'json',
			beforeSend: function(request) {
	            request.setRequestHeader("userAgent", userAgent);
	        },
			error: function(res) {
				d.close();
				if(res.status == 999 || res.status == 998 || res.status == 997){
					loginUrl(res.status);
					return;
				}
				dialog({contentType:'tipsbox', skin:'bk-popup' , content:'校验失败'}).show();
			},
			success: function(res) {
				d.close();
				if(res.status == 999 || res.status == 998 || res.status == 997){
					loginUrl(res.status);
				}
				else if (res.status == 200) {					
					var is_sign = res.data;//0未签约，1已签约
					if(is_sign == 0){						
						dialog({
				            title: '提示',
				            content: '您还未签约,请先签约,谢谢！',
				            okValue:'签约',
				            ok: function (){				            		
				            	window.location.href = server + "wx/html/ssgg/html/choose-region.html";
				            },
				            cancelValue: '不了',
				            cancel: function () {
								return;
				            }
				    	}).showModal();
					}else{
						if(type == 1){
							window.location.href = server + "wx/html/zxwz/html/teachers-consult-commit.html";
						}
					}
				} else {
					dialog({contentType:'tipsbox', skin:'bk-popup' , content:res.msg}).show();
				}
			}
		});	
	}
	else{
		dialog({contentType:'tipsbox', skin:'bk-popup' , content:'用户信息错误'}).show();
	}
}
