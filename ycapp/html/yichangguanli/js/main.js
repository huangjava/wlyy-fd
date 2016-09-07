mui.init({
	keyEventBind: {
		backbutton: false  //Boolean(默认true)关闭back按键监听
	}
});

mui.plusReady(function() {
	window.localStorage.removeItem("isLoginOut");
	var self = plus.webview.currentWebview();

	var userAgent = JSON.parse(plus.storage.getItem("userAgent"));
	var userId = userAgent.uid;

	var orgId = plus.storage.getItem("orgId");
	var client_id = plus.storage.getItem("appUID");
	var ticket = plus.storage.getItem("ticket");
	var userId = plus.storage.getItem("userId");
	
	var subPages = [ "yiqianyue.html","daiqianyue.html","mine.html"];

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
});
