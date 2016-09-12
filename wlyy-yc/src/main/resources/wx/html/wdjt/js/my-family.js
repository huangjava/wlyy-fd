	// 页面载入显示提示“加载中” 
var d = dialog({contentType:'load', skin:'bk-popup'}).show(),
	// 未签约成员列表容器
	$memberView = $('#member_view'),
	// 未签约成员列表
	$memberList = $('#member_list'),
	// 未签约成员列表为空时展示的内容
	$noResultWrap = $('#no_result_wrap'),
	// 添加家庭成员按钮
	$addSigningMemberBtn = $('#add_signing_member_btn');

// TODO url,data示例参数为空
getReqPromise({url:'',data: {}})

// 数据成功返回，处理页面展示
.then(function(data) {
	// TODO 后台返回 data 示例数据
	data = {"msg":"获取医院医生列表成功！","list":[{"code":"D2016080002","job_name":" 全科医师","introduce":"我是全科医生","name":"大米全科1","dept_name":"","photo":"http://172.19.103.85:8882/res/images/2016/08/12/20160812170142_901.jpg","id":1262,"expertise":"我是全科医生","hospital_name":"嘉莲社区医疗服务中心",relationship:'本人'},{"code":"D2016080005","job_name":" 全科医师","introduce":"我是全科医生","name":"大米全科2","dept_name":"","photo":"","id":1271,"expertise":"我是全科医生","hospital_name":"嘉莲社区医疗服务中心",relationship:'女儿'},{"code":"D2016080225","job_name":" 全科医师","introduce":"我是全科医生","name":"谭仁祝(全科)","dept_name":"","photo":"","id":1274,"expertise":"我是全科医生","hospital_name":"嘉莲社区医疗服务中心",relationship:'女儿'},{"code":"D2010080225","job_name":" 全科医师","introduce":"我是全科医生","name":"谭仁祝(全科1)","dept_name":"","photo":"","id":1276,"expertise":"我是全科医生","hospital_name":"嘉莲社区医疗服务中心",relationship:'女儿'}],"status":200};
	
	// 如果返回的列表为空则显示“抱歉，暂未找到符合条件的结果”
	if(!data.list.length) {
		$noResultWrap.show();
		return ;
	}
	
	// 根据列表模板，填充数据，输出列表html代码
	var html = template("member_li_tmpl", data);
	// 将列表添加到页面
	$memberList.append(html);
	
	// 关闭页面加载提示
	d.close();
})

// 绑定页面相关事件
.then(function() {
	
	$addSigningMemberBtn.click(function() {
		location.href = "";
	});
})

// 捕捉过程中产生的异常
.catch(function(e) {
	console && console.error(e);
});
	
