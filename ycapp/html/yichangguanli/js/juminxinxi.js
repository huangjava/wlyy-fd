
mui.plusReady(function() {
	getPatientInfo();
});

/**
 * 获取居民信息
 */
function getPatientInfo(){
//	plus.nativeUI.showWaiting();
//	sendPost("doctor/baseinfo", {},
//		function(res) {
//			if(res && res.msg) {
//				plus.nativeUI.toast(res.msg);
//			} else {
//				plus.nativeUI.toast("数据加载失败");
//			}
//			plus.nativeUI.closeWaiting();
//		}, 
//		function(res) {
//			if(res.status == 200) {
//				$("body").append(template("detail_info_tmpl", res.data));
//			} else {
//				mui.toast(res.msg);
//			}
//			plus.nativeUI.closeWaiting();
//		});

	//使用静态数据
	var data = {
		name: "张三",
		sex: "1",
		photo: "",
		age: "25",
		activeTime: "2015-01-01~2017-01-01",
		tag: "高血压、糖尿病",
		idCard: "350581199002052852",
		mobile: "15656565656",
		tel: "0592-7651545",
		address: "厦门市湖里区吕岭路泰和花园120号6栋-203室",
		linkerTel: "0592-7651545"
		
	}
	$("body").append(template("detail_info_tmpl", data));
	
	$('.arrow-right').parent().on('tap', function(){
		openModifyWindow($(this).attr('data-title') , {type: 1, val: $(this).find(".c-list-value").html()});
	})
}
