var dd = dialog({contentType:'load', skin:'bk-popup', content:'保存中...'});
var d = dialog({contentType:'load', skin:'bk-popup'});
function getNowDate() {
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var day = date.getDate();
	var hour = date.getHours();
	var minute = date.getMinutes();
	var second = date.getSeconds();
	return year + '-' + (month < 10 ? '0' + month : month) + '-' + (day < 10 ? '0' + day : day);
}

/**
 * 获取多少天前的日期
 */
function getDateBefore(days) {
	var now = new Date();
	var date = new Date(now.getTime() - days * 24 * 3600 * 1000);
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var day = date.getDate();
	var hour = date.getHours();
	var minute = date.getMinutes();
	var second = date.getSeconds();
	return year + '-' + (month < 10 ? '0' + month : month) + '-' + (day < 10 ? '0' + day : day);
}

/**
 * 添加健康指标到服务器
 * intervene	干预标志
 * time	记录时间
 * value1 血糖/收缩压/体重/腰围
 * value2 舒张压
 * point 血糖测量点标识
 * type	健康指标类型（1血糖，2血压，3体重，4腰围）
 */		
function addHealthIndex(intervene, operTime, value1, value2, value3, value4, value5, value6, value7, type, successFunction) {
	//拼请求内容
	var params = {};
	params.intervene = intervene;
	var data = {};
	if(type==1){
		data.gi = value1+"";
		data.gi_type = value2+"";
		data.time = operTime+" 00:00:00";	
	}
	if(type==2){
		data.sys = value1+"";
		data.dia = value2+"";
		data.time = operTime;
	}
	if(type==3){
		data.weight = value1+"";
		data.time = operTime+" 00:00:00";
	}
	if(type==4){
		var data = {};
		data.waistline = value1+"";
		data.time = operTime+" 00:00:00";
	}
	params.data = JSON.stringify(data);
	params.type = type+"";
	dd.showModal();
	//发送ajax请求
	sendPost("/patient/health_index/addPatientHealthIndex", params, "json", "post", addHealthIndexFailed, successFunction);
}

/**
 * 健康指标添加失败处理方法
 */
function addHealthIndexFailed(res) {
	dd.close();
	if (res && res.msg) {
		dialog({contentType:'tipsbox',bottom:true, skin:'bk-popup' , content:res.msg}).show();
	} else {
		dialog({contentType:'tipsbox',bottom:true, skin:'bk-popup' , content:'保存失败'}).show();
	}
}

function toIntValue(value){
		if(parseInt(value) == value){
			return parseInt(value);
		}else{
			return Math.round(value*Math.pow(10, 1))/Math.pow(10, 1);
		}
}

/**
 * 血压添加成功处理方法
 */
function addBloodPressureSuccess(res) {
	if (res.status == 200) {
		dd.close();
		window.location.href='health-record.html?charType=2&'+$.now();
	} else {
		//非200则为失败
		addHealthIndexFailed(res);
	}
}

/**
 * 血糖添加成功处理方法
 */
function addBloodSugarSuccess(res) {
	if (res.status == 200) {
		//添加成功
		var record_date = document.getElementById("date").value;
		var type_str = document.getElementById("type").value;
		var val = document.getElementById("val").value;
		var type;
		switch (type_str) {
			case "空腹血糖":
				type = ".bb";
				break;
			case "早餐后血糖":
				type = ".ba";
				break;
			case "午餐前血糖":
				type = ".lb";
				break;
			case "午餐后血糖":
				type = ".la";
				break;
			case "晚餐前血糖":
				type = ".db";
				break;
			case "晚餐后血糖":
				type = ".da";
				break;
			case "睡前血糖":
				type = ".sb";
				break;
		}
		window.location.href='health-record.html?charType=1&'+$.now();
		dd.close();
	} else {
		//非200则为失败
		addHealthIndexFailed(res);
	}
}

/**
 * 腰围添加成功处理方法
 */
function addWaistlineSuccess(res) {
	if (res.status == 200) {
		dd.close();
		window.location.href='health-record.html?charType=4&'+$.now();
	} else {
		//非200则为失败
		addHealthIndexFailed(res);
	}
}

/**
 * 体重添加成功处理方法
 */
function addWeightSuccess(res) {
	if (res.status == 200) {
		dd.close();
		window.location.href='health-record.html?charType=3&'+$.now();
	} else {
		//非200则为失败
		addHealthIndexFailed(res);
	}
}

/////////////////////////////////////////////////////////////////////////////////健康指标图表数据查询///////////////////////////////////////////////////////////////////////////

/**
 * 查询健康指标图表
 * @param {Object} type 健康指标类型（1血糖，2血压，3体重，4腰围）
 * @param {Object} begin 记录开始时间
 * @param {Object} end 记录结束时间
 */
function queryChatByType(type, begin, end, successFunction) {
	//拼请求内容
	var params = {};
	params.type = type;
	params.begin = begin;
	params.end = end;
	d.show();
	//发送ajax请求
	sendPost("patient/health_index/chart", params, "json", "post", queryChartFailed, successFunction);
}

/**
 * 健康指标图表查询失败处理方法
 */
function queryChartFailed(res) {
	d.close();
	if (res && res.msg) {
		dialog({contentType:'tipsbox',bottom:true, skin:'bk-popup' , content:res.msg}).show();
	} else {
		dialog({contentType:'tipsbox',bottom:true, skin:'bk-popup' , content:'加载失败'}).show();
	}
}

/////////////////////////////////////////////////////////////////////////////////健康指标历史记录查询///////////////////////////////////////////////////////////////////////////
/**
 * 健康指标历史记录查询
 * @param {Object} type 健康指标类型（1血糖，2血压，3体重，4腰围）
 * @param {Object} page 当前分页
 * @param {Object} pagesize 分页大小
 */
function queryListByType(type, page, pagesize, successFunction,begindate,enddate) {
	//拼请求内容
	var params = {};
	params.type = type; 
	params.page = page;
	params.pagesize = pagesize;
	params.start = begindate+" 00:00:00";
	params.end = enddate+" 23:59:59";
	d.show();
	//发送ajax请求
	sendPost("patient/health_index/list", params, "json", "post", queryListFailed, successFunction);
}

/**
 * 健康指标历史记录查询失败处理方法
 */
function queryListFailed(res) {
	d.close();
	if (res && res.msg) {
		dialog({contentType:'tipsbox',bottom:true, skin:'bk-popup' , content:res.msg}).show();
	} else {
		dialog({contentType:'tipsbox',bottom:true, skin:'bk-popup' , content:'加载失败'}).show();
	}
}

/**
 * 查询健康指标的预警标准
 */
function queryWarning() {
	//拼请求内容
	var params = {};
	d.show();
	//发送ajax请求
	sendPost("patient/health_index/standard", params, "json", "post", queryListFailed, queryWarningSuccesss);
}
