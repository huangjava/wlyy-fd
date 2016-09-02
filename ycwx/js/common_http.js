/**
 * 统一请求ajax发送方法
 * url 请求地址：例如：patient/health_index/add
 * params 请求参数
 * dataType 数据类型：json等
 * reqType 请求方式:get 或 post
 * error 请求失败处理方法
 * success 请求成功处理方法
 */
function sendPost(url, params, dataType, reqType, error, success) {
	var userAgent = window.localStorage.getItem(agentName);
	//发送ajax请求
	$.ajax(server + url, {
		data: params,
		dataType: dataType,
		beforeSend: function(request) {
			request.setRequestHeader("userAgent", userAgent);
		},
		type: reqType,
		error: function(res) {
			if(res.status == 999 || res.status == 998 || res.status == 997){
				loginUrl(res.status);
				return;
			}
			error(res);
		},
		success: function(res) {
			if(res.status == 999 || res.status == 998 || res.status == 997){
				loginUrl(res.status);
				return;
			}
			success(res);
		}
	});
}

//重新登陆
function loginUrl(status){
	var pageurl = window.location.href;

	saveAgentPage(pageurl);
	var content = "";
	if(status == 999){
		content = "此账号已在别处登录，需要请重新登录";
	}else if(status == 998){
		content = "登录超时，需要请重新登录";
	}else if(status == 997){
		content = "此账号未登录，需要请重新登录";
	}
	var openid = "";
	var userAgent = window.localStorage.getItem(agentName);
	if(userAgent){
		var jsonstr = $.parseJSON(userAgent);
		openid = jsonstr.openid;
	}
    clearAgent();
	dialog({
	  	title:'提示',
	  	skin:"ui-dialog ax-popup pror", 
	  	content: content, 
	  	ok: function (){
	  		window.location.href = server + "wx/html/home/html/login.html?type=0&openid=" + openid;
	  	}
	}).showModal();
}

function getReqPromise(url, data) {
	return new Promise(function(resolve, reject) {
		sendPost(url, data, "json", "post",
		  	function queryFailed (res) {
				dialog({contentType:'tipsbox', skin:'bk-popup' , content:'加载失败'}).show();
				// TODO 开启示例数据
				resolve({});
			}
	  		, function success(res) {
				resolve(res);
	  	});
	});
}

/*
 * reqs: 请求的参数数组，格式：[{url:'a/xxx',data:{...}},{url:'b/xxx',data:{...}}]
 * */
function getReqPromises(reqs) {
	if(!reqs || !reqs.length) {
		return new Promise(function(resolve, reject) {
			resolve([]);
		});
	}
	return Promise.all(_.map(reqs,function(param){
		return reqPromise(param.url,param.data);
	}));
};