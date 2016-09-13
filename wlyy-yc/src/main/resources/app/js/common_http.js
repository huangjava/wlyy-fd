//切换发布版本 true：正式版，连接生产环境；false：测试版，连接测试环境。
//var publish_version = true;
var publish_version = false;
var curr_app_version = 3; //app当前内定版本号
//接口服务器
var server;
var server1;
var appId;
// IM服务器
/*
var im_login;
var im_logout;
var im_send_group;
var im_get_group_msg;
*/

//随访计划
var sf_serverip = "";

if(publish_version == true) {
	appId = "wxad04e9c4c5255acf";
	//接口服务器
	server1 = server = "http%3a%2f%2fwww.xmtyw.cn%2fwlyy%2f";
	//随访计划
	sf_serverip = "http://120.41.252.108:8881/";

} else {
	appId = "wxd03f859efdf0873d";
	server1 = "http%3a%2f%2fweixin.xmtyw.cn%2fwlyy%2f";
	server = "http://localhost:9111/fd/"
	//	alert("注意：这是测试版！");
	//接口服务器
//		server = "http://weixin.xmtyw.cn/wlyy/";//卫计委的测试服务器
	
 //  server = "http%3a%2f%2f192.168.131.6%3a8080%2fwlyy%2f"; 
//	server = "http://172.19.103.87:9090/wlyy/";//公司内部服务器的地址
//	server = "http://192.168.131.6:8080/";//伟达
//	server = "http://192.168.131.116:80/";//荣荣
//	server = "http://192.168.131.118:8080/";//聪亮
//   server = "http://192.168.131.120:8080/fd/";//yww
//	server = "http://27.154.56.142:9090/wlyy/";//公司内部服务器的地址的外网映射
 
	//随访计划
//	sf_serverip = "http://172.19.103.85:8881/";//公司内部服务器的地址
	sf_serverip = "http://27.154.56.142:8881/";//公司内部服务器的地址的外网映射
	//请求格式实例
	//http://172.19.103.85:8881/index.php?m=FvDoctor&c=index&id=1&uid=D20160322000002&imei=869515022138389&token=D20160322000002869515022138389&platform=2
}

var isLoginOut;

function sendGet(url, params, custError, custSuccess){
	sendPost(url, params, custError, custSuccess, "GET");
}


/**
 * 统一请求ajax发送方法
 * url 请求地址：例如：patient/health_index/add
 * params 请求参数
 * dataType 数据类型：json等
 * reqType 请求方式:get 或 post
 * error 请求失败处理方法
 * success 请求成功处理方法
 */
function sendPost(url, params, custError, custSuccess,dataType) {
	
	if(dataType==undefined){
		dataType="POST"
	}
	if(isLoginOut) {
		return
	}
	//发送ajax请求
	console.log(server);
	$.ajax(server + url, {
		//	mui.ajax( url, {
		data: params || {},
		dataType: 'json',
		//crossDomain: true,
		type: dataType,
		timeout: 1000000,
		error: custError || function(xht, type, throwErr) {
			plus.nativeUI.closeWaiting();
			var random = Math.random();
			isLoginOut = window.localStorage.isLoginOut;
			if(isLoginOut) {
				return;
			}

			var tip = ""
			if(type == "timeout") {
				window.localStorage.isLoginOut = random;
				if(window.localStorage.isLoginOut != random) {
					return;
				}
				tip = "连接超时，请重新登录";
			}
			if(type == "abort") {
				window.localStorage.isLoginOut = random;
				if(window.localStorage.isLoginOut != random) {
					return;
				}
				tip = "网络异常，请检查您的网络设置";
			}

			if(type == "error" || type == "parsererror" || type == "null") {
				window.localStorage.isLoginOut = random;
				if(window.localStorage.isLoginOut != random) {
					return;
				}
				tip = "网络错误，请重新登录";
			}
			showConfirm(tip);
		},
		success: function(res) {
			var random = Math.random();
			isLoginOut = window.localStorage.isLoginOut;
			//				console.error(isLoginOut);
			if(isLoginOut) {

				return;
			}
			var tip = "";
			if(res.status == 999) {
				window.localStorage.isLoginOut = random;
				if(window.localStorage.isLoginOut != random) {
					return;
				}
				tip = "此账号已在别处登录，请重新登录";
				showConfirm(tip);
				return;
			} else if(res.status == 998) {
				window.localStorage.isLoginOut = random;
				if(window.localStorage.isLoginOut != random) {
					return;
				}
				tip = "登录超时，请重新登录";
				showConfirm(tip);
				return;
			} else if(res.status == 997) {
				window.localStorage.isLoginOut = random;
				if(window.localStorage.isLoginOut != random) {
					return;
				}
				tip = "此账号未登录，请先登录";
				showConfirm(tip);
				return;
			}
			custSuccess(res);
		},
		beforeSend: function (xhr) {
			xhr.setRequestHeader("userAgent", plus.storage.getItem("userAgent"));
			//xhr.setRequestHeader("Access-Control-Allow-Origin", plus.storage.getItem("userAgent"));
		},
		headers: {
			"Access-Control-Allow-Origin":"*",
			"Access-Control-Allow-Headers":"X-Requested-With"
		}
	});

}

/*
 * 选择退出登录
 */
function exit2Login() {
//	var loginWv = plus.webview.getWebviewById("login");
//	console.error(loginWv);
//	if(loginWv) {
//		loginWv.show("auto");
//	} else {
		mui.openWindow('../../login/html/login.html', 'login', {
			extras: {
				exit: "exit"
			},
			createNew: true
		});
//	}

	mui.later(function() {
		plus.webview.currentWebview().close("none");
	}, 1000)
}
/*
 * 弹出框
 */
function showConfirm(content) {
	mui.confirm(content, "提示", ["确定"], function() {
		//exit2Login();
		plus.storage.removeItem("isLoginOut");
	});
}

function sendPostNoAsync(url, params, error, success) {
	//发送ajax请求
	mui.ajax(server + url, {
		//	mui.ajax( url, {
		data: params || {},
		async: false,
		dataType: 'json',
		//crossDomain: true,
		type: "POST",
		timeout: 10000,
		error: error || function(xht, type, throwErr) {},
		success: success
	});

}

/*
 * 获取时间
 */
function getCurrDate(flag) {
	var weekStr = ["周日", "周一", "周二", "周三", "周四", "周五", "周六"];
	var d = new Date();
	var year = d.getFullYear();
	var month = d.getMonth() + 1;
	var day = d.getDate();
	var week = d.getDay();
	var h = d.getHours();
	var mins = d.getMinutes();
	var s = d.getSeconds();
	if(month < 10) month = "0" + month;
	if(day < 10) day = "0" + day;
	if(h < 10) h = "0" + h;
	if(mins < 10) mins = "0" + mins;
	if(s < 10) s = "0" + s;

	if(flag == "date") {
		return year + "-" + month + "-" + day;
	} else if(flag == "time") {
		return h + ":" + mins + ":" + s;
	} else if(flag == "week") {
		return year + "-" + month + "-" + day + " " + weekStr[week];
	}
	return year + "-" + month + "-" + day + " " + h + ":" + mins + ":" + s;
}

function getSysDate(plus) {
	var d = new Date();
	var year = d.getFullYear();
	var month = d.getMonth() + 1;
	var day = d.getDate();
	//				var week = d.getDay();
	var h = d.getHours();
	var mins = d.getMinutes();
	var s = d.getSeconds();
	if(month < 10) month = "0" + month;
	if(day < 10) month = "0" + day;
	if(h < 10) h = "0" + h;
	if(mins < 10) mins = "0" + mins;
	if(s < 10) s = "0" + s;
	if(!plus) {
		return year + "-" + month + "-" + day + "" + h + ":" + mins + ":" + s;
	} else {
		return(year + 1) + "-" + month + "-" + day + "" + h + ":" + mins + ":" + s;
	}
}

function getSysDatePlus(plus) {
	var now = new Date();
	var d = new Date(now.getTime() + plus * 24 * 3600 * 1000);
	var year = d.getFullYear();
	var month = d.getMonth() + 1;
	var day = d.getDate();
	//	alert(year+"  "+month+"  "+day);
	//	var h = d.getHours();
	//	var mins = d.getMinutes();
	//	var s = d.getSeconds();
	if(month < 10) month = "0" + month;
	if(day < 10) day = "0" + day;
	//	if (h < 10) h = "0" + h;
	//	if (mins < 10) mins = "0" + mins;
	//	if (s < 10) s = "0" + s;
	return year + "-" + month + "-" + day
}

/**
 * 图片批量上传
 * @param {Object} images 图片数组
 * @param {Function} callBackHandler 上传结果处理方法
 */
//function uploadImages(images, callBackHandler) {
//	var task = plus.uploader.createUpload(server + '/upload/image', {
//		method: "POST"
//	}, callBackHandler);
//	//	task.addData("client", "HelloH5+");
//	//	task.addData("uid", getUid());
//	for (var i = 0; i < images.length; i++) {
//		var image = images[i];
//		task.addFile(image, {});
//	}
//	task.start();
//}

/**
 * 群组消息推送
 * @param {Object} group_id 群组ID
 * @param {Object} from_id  发送者ID
 * @param {Object} content  消息内容
 */
//function group_sendMsg(group_id,from_id,content)
//{
//	mui.ajax(group_sendmsg, {
//		data: {group_id:group_uid,content:content,from_id:from_id},
//		dataType: 'json',
//		type: "POST",
//		timeout: 10000,
//		error: function(xht, type, throwErr) {
//
//		},
//		success: function(result){
//			
//		}
//	});
//}

function getsuifangUrl() {
	var userAgent = plus.storage.getItem("userAgent");
	userAgentJson = JSON.parse(userAgent);
	var id = userAgentJson.id;
	var uid = userAgentJson.uid;
	var imei = userAgentJson.imei;
	var token = userAgentJson.token;
	var platform = 2;
	var openid = "123456";

	var sf_url = sf_serverip + "index.php?m=FvDoctor&c=index" + "&id=" + id + "&uid=" + uid + "&imei=" + imei + "&token=" + token + "&platform=" + platform + "&openid=" + openid;
	//		console.error(sf_url);
	return sf_url;
}

/** 
 * 用于把用utf16编码的字符转换成实体字符，以供后台存储 
 * @param  {string} str 将要转换的字符串，其中含有utf16字符将被自动检出 
 * @return {string}     转换后的字符串，utf16字符将被转换成&#xxxx;形式的实体字符 
 */
function utf16toEntities(str) {
	var patt = /[\ud800-\udbff][\udc00-\udfff]/g; // 检测utf16字符正则  
	str = str.replace(patt, function(char) {
		var H, L, code;
		if(char.length === 2) {
			H = char.charCodeAt(0); // 取出高位  
			L = char.charCodeAt(1); // 取出低位  
			code = (H - 0xD800) * 0x400 + 0x10000 + L - 0xDC00; // 转换算法  
			return "&#" + code + ";";
		} else {
			return char;
		}
	});
	return str;
}