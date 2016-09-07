// 页面载入显示提示“加载中” 




$(function() {
	var Request = new Object();
		Request = GetRequest();
		var doctorCode = Request["doctorCode"];
		
	
	getReqPromise("patient/hospital/getDoctorInfo",{doctorCode:doctorCode})

	// 数据成功返回，处理页面展示
	.then(function(data) {
		// TODO 后台返回 data 示例数据
		// 如果返回的列表为空则显示“抱歉，暂未找到符合条件的结果”
		if(!data) return ;
		if(!data.data.url){
			document.getElementById("photo").src = "../../../images/d-male.png";
		}else{
			document.getElementById("photo").src = data.data.url;
		}
		document.getElementById("expertise").innerHTML = data.data.expertise;
		document.getElementById("introduce").innerHTML = data.data.introduce;
		
	})
	
	// 捕捉过程中产生的异常
	.catch(function(e) {
		console && console.error(e);
	});
})
	