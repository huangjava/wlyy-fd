
mui.plusReady(function() {
	getPatientInfo();
//	$('#pati_info').on('tap', function(){
//		alert(1)
//		console.log("居民详细信息")
//		openWindowWithSub("weiqianyuejuminxinxi", "weiqianyuejuminxinxi.html", "居民资料", {});
//	})
});

/**
 * 获取居民信息
 */
function getPatientInfo(){
	plus.nativeUI.showWaiting();
	var self = plus.webview.currentWebview();
	var id = self.id;
	var params = {patientId:id};
	sendPost("doctor/patient_group/patientInfo", params,
		function(res) {
			if(res && res.msg) {
				plus.nativeUI.toast(res.msg);
			} else {
				plus.nativeUI.toast("数据加载失败");
			}
			plus.nativeUI.closeWaiting();
		}, 
		function(res) {
			if(res.status == 200) {
				$("#docInfo").append(template("detail_info_tmpl", res.data));
				$('#docInfo').on('tap','ul', function(){
					var id = $(this).attr("data-id");	
					mui.openWindow({
						url: "weiqianyuejuminxinxi.html",
						id: id,
						extras: {
							id: id
						}
					});
					
//					openWindowWithSub("weiqianyuejuminxinxi", "weiqianyuejuminxinxi.html", "居民资料", {});
				})
			} else {
				mui.toast(res.msg);
			}
			plus.nativeUI.closeWaiting();
		});

	//使用静态数据
//	var data = {
//		name: "张三3333",
//		sex: "1",
//		photo: "",
//		age: "25",
//		activeTime: "2015-01-01~2017-01-01",
//		tag: "高血压、糖尿病",
//		idCard: "350581199002052852",
//		mobile: "15656565656",
//		tel: "0592-7651545",
//		address: "厦门市湖里区吕岭路泰和花园120号6栋-203室",
//		linkerTel: "0592-7651545"
//		
//	}
//	$("#docInfo").append(template("detail_info_tmpl", data));
	
//	$('.arrow-right').parent().on('tap', function(){
//		openModifyWindow($(this).attr('data-title') , {type: 1, val: $(this).find(".c-list-value").html()});
//	})
}


