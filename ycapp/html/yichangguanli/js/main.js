mui.init({
	keyEventBind: {
		backbutton: false  //Boolean(默认true)关闭back按键监听
	}
});


mui.plusReady(function() {
	window.localStorage.removeItem("isLoginOut");
	var self = plus.webview.currentWebview();
	
	/*var docType = plus.storage.getItem("docType");
	if(!docType) {
		alert("获取医生类型失败");
	}*/
	var userAgent = JSON.parse(plus.storage.getItem("userAgent"));
	var userId = userAgent.uid;

	var orgId = plus.storage.getItem("orgId");
	var client_id = plus.storage.getItem("appUID");
	var ticket = plus.storage.getItem("ticket");
	var userId = plus.storage.getItem("userId");
	
	alert(ticket + userId);
	/*var token = info.token;
	var client_id = info.clientid;
	var platform = 0;
	if(plus.os.name == "Android") { //ios暂无im功能 只在安卓下才执行该方法
		platform = 1;
	}*/
	
//	loginIm(userId,token,client_id,platform); //登录Im
	
	var subPages = [ "yiqianyue.html","daiqianyue.html","mine.html"];

    //******************** 原有  20160905 yww测试改
//	var subPages = [];
//	switch(docType) {
//		case "3":
//			subPages = ["yiqianyue.html", "daiqianyue.html", "mine.html"];
//			break;
//		case "2":
//		default:
//			break;
//	}
    //*************************
	
	/**
	 * 初始化已签约、待签约、我的 页面
	 */
	var subStyles = {
		top: 0,
		bottom: "51px",
		scorllIndicator: "none"
	};
	for(var i = 0; i < subPages.length; i++) {
		var sub_wv = plus.webview.create(subPages[i], subPages[i], subStyles);
		if(i > 0) {
			sub_wv.hide();
		}
		self.append(sub_wv);
	}
    
	var activeSub = subPages[0];
	var aTab = document.querySelectorAll(".mui-tab-item");
	for(var i = 0; i < aTab.length; i++) {
		aTab[i].addEventListener("tap", function() {
			var targetSub = this.getAttribute("data-src");
			if(targetSub == activeSub) {
				return;
			}
			var xxWv = plus.webview.getWebviewById(targetSub);
			if(xxWv) {
				mui.fire(xxWv, "refresh");
				xxWv.show();
			}
			plus.webview.hide(activeSub);
			activeSub = targetSub;
		});
	}
	
	/*
	 * 退出事件
	 */
	/*var first = null;
	mui.back = function() {
		if(!first) {
			first = new Date().getTime();
			plus.nativeUI.toast("再按一次退出");
			setTimeout(function() {
				first = null;
			}, 1000);
		} else {
			if(new Date().getTime() - first < 1000) {
				plus.runtime.quit();
			}
		}
	}*/

	/**
	 * 请求医生基本信息
	 */
	sendPost("doctor/baseinfo",
		{
			"doctorId":userId,
			"ticket":ticket
		},
		null, function(res) {
		if(res.status == 200) {
			var infoStr = JSON.stringify(res.data);
			plus.storage.setItem("docInfo", infoStr);
			//TODO 测试用
			console.log(infoStr);
		} else {
			mui.toast("获取医生信息失败");
		}
	});


	/**
	 * 自定义事件格式
	 */
//	window.addEventListener("activeHuanzhe", function() {
//		var items = $(".mui-tab-item");
//		mui.trigger(items.get(1), "tap");
//		items.removeClass("mui-active");
//		mui.later(function() {
//			items.eq(1).addClass("mui-active");
//		}, 100);
//	});
	
	
	/**
	 * 返回首页
	 */
	/*window.addEventListener("mainActive", function() {
		var item = $(".mui-tab-item").get(1);
		mui.trigger(item, 'tap');
	});
		*/
	/*plus.push.addEventListener("click", function(msg) {
		console.log("click");
		var qunzuduihuaView = plus.webview.getWebviewById("dlz");
		if(qunzuduihuaView) {
			$("#red_sign").css("display", "none");
			mui.fire(qunzuduihuaView, "update");
		} else {
			$("#red_sign").css("display", "");
		}
		
		var p2pHtml = plus.webview.getWebviewById("p2p");
		if(p2pHtml) {
			mui.fire(p2pHtml, "update");
		}
	}, false);*/
	
	// 监听在线消息事件
	/*plus.push.addEventListener("receive", function(msg) {
		
		var qunzuduihuaView = plus.webview.getWebviewById("dlz");
		if(qunzuduihuaView) {
			$("#red_sign").css("display", "none");
			mui.fire(qunzuduihuaView, "update");
		} else {
			$("#red_sign").css("display", "");
		}
		
		//通知一对一聊天更新数据
		var p2pHtml = plus.webview.getWebviewById("p2p");
		if(p2pHtml) {
			mui.fire(p2pHtml, "update");
		}
	}, false);*/
	
});
/*

function loginIm(userId,token,client_id,platform){
	if(token!=null){
		im.login(userId,token,client_id,platform);	
	}else{
		var info = plus.push.getClientInfo();
		var tokenNew = info.token;
		var client_idNew = info.clientid;
		setTimeout(function(){		
			loginIm(userId,tokenNew,client_idNew,platform);
		},1000);
	}
}*/
