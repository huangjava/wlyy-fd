
mui.plusReady(function() {
	getPatientInfo();
});

/**
 * 获取居民信息
 */
function getPatientInfo(){
	plus.nativeUI.showWaiting();
	var self = plus.webview.currentWebview();
	var patientId = self.patientId;
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
				$("body").append(template("detail_info_tmpl", res.data));
			} else {
				mui.toast(res.msg);
			}
			plus.nativeUI.closeWaiting();
		});

}
