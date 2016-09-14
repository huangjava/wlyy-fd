
$(function() {
	getPatientInfo();
});

/**
 * 获取居民信息
 */
function getPatientInfo(){
	plus.nativeUI.showWaiting();
	//var self = plus.webview.currentWebview();
    var	Request = GetRequest();
    var	patientId = Request["patientId"];
    alert(patientId)
	var params = {patientId:patientId};
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
					var patientId = $(this).attr("data-id");	
					openWindowWithSub("juminxinxi", "weiqianyuejuminxinxi.html", "居民资料", {patientId:patientId},"weiqianyuejuminxinxi");
				})
			} else {
				mui.toast(res.msg);
			}
			plus.nativeUI.closeWaiting();
		});

}


