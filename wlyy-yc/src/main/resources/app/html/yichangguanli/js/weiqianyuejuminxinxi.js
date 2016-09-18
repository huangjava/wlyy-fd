
$(function() {
	parent.document.title="居民资料";
	getPatientInfo();
});

/**
 * 获取居民信息
 */
function getPatientInfo(){
	plus.nativeUI.showWaiting();
	//var self = plus.webview.currentWebview();
	//var patientId = self.patientId;

    var	Request = GetRequest();
    var	patientId = Request["patientId"];
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
                //clickEvent();
			} else {
				mui.toast(res.msg);
			}
			plus.nativeUI.closeWaiting();
		}
    );
}
function clickEvent() {

    /*document.getElementById('mui-action-back').addEventListener('tap', function () {
        var	Request = GetRequest();
        var	patientId = Request["patientId"];
        var supPage = Request["supPage"];
        closeWindow();
        if(supPage){
            openWindow(supPage+'.html?patientId='+patientId);
        }
    });*/
}