var d = dialog({contentType:'load', skin:'bk-popup'}).show();

var Request = GetRequest();
var hospital = Request["hospital"];
// TODO 示例参数接受，设置默认值
var code = Request["code"] ;
var name = decodeURIComponent(Request["name"]||"");
var openid = "";
var state = Request["state"];
var stateArray = "";

var pagetype = 17;
//checkUserAgent();

if(state){
	state = decodeURIComponent(state);
	stateArray = state.split(":");
	
	if(stateArray && stateArray.length == 2){
		hospital = stateArray[0];
		name = stateArray[1];
	}
}

//var userAgent = window.localStorage.getItem(agentName);

var $teamView = $('#team_view'),
	$teamList = $('#team_list'),
	$noResultWrap = $('#no_result_wrap');

var lastId = 0;
// 搜索框
$('.searchbar').searchBar();

var reqPromise = function(url,data) {
	 
	 return new Promise(function(resolve, reject) {
		sendPost(url, data, "json", "post",
		  	function queryFailed (req) {
				dialog({contentType:'tipsbox', skin:'bk-popup' , content:'加载失败'}).show();
				// TODO 开启示例数据
				resolve({});
			}
	  		, function success(req) {
				resolve(req);
	  	});
	});
},

// 获取列表的最后一个数据对象的id字段值（用于医生列表的分页请求参数）
getLastItemId = function(list) {
	var lastObj;
	if(list&&list.length) {
		lastObj = list[list.length-1];
	}
	return lastObj?lastObj.id:lastId;
},
// 更新分页上拉加载的提示文本
updatePullUpText= function(scroller,list) {
	scroller.on('refresh',function() {
		var $wrap = $(scroller.wrapper),
		$pullupLabel = $wrap.find('.pullUpLabel');
		if(!list || !list.length) {
			$pullupLabel.text('没有更多');
		} else {
			$pullupLabel.text('上拉加载更多');
		}
	});
},
getReqPromises = function() {
	
	return Promise.all(_.map([{url:"hosptail/doctor_list",data:{hospital:hospital,id:lastId,pageSize:15}}],
	function(param){
		return reqPromise(param.url,param.data);
	}));
};

//if(userAgent) {
//	userAgent = JSON.parse(userAgent);
//} 

$teamView.find('.community-name').text(name);
getReqPromises().then(function(datas) {
	//openid = datas[0] && datas[0].openid;
	var data = datas[0];
	// TODO 示例数据
	data = {"msg":"获取医院医生列表成功！","list":[{"code":"D2016080002","job_name":" 全科医师","introduce":"我是全科医生","name":"大米全科1","dept_name":"","photo":"http://172.19.103.85:8882/res/images/2016/08/12/20160812170142_901.jpg","id":1262,"expertise":"我是全科医生","hospital_name":"嘉莲社区医疗服务中心"},{"code":"D2016080005","job_name":" 全科医师","introduce":"我是全科医生","name":"大米全科2","dept_name":"","photo":"","id":1271,"expertise":"我是全科医生","hospital_name":"嘉莲社区医疗服务中心"},{"code":"D2016080225","job_name":" 全科医师","introduce":"我是全科医生","name":"谭仁祝(全科)","dept_name":"","photo":"","id":1274,"expertise":"我是全科医生","hospital_name":"嘉莲社区医疗服务中心"},{"code":"D2010080225","job_name":" 全科医师","introduce":"我是全科医生","name":"谭仁祝(全科1)","dept_name":"","photo":"","id":1276,"expertise":"我是全科医生","hospital_name":"嘉莲社区医疗服务中心"}],"status":200};
	lastId = getLastItemId(data.list);
	if(data && data.list.length) {
		$noResultWrap.hide();
		var html = template("team_li_tmpl", data);
		$teamList.html(html);
	} else {
		$noResultWrap.show();
		d.close();
		return ;
	}
	
	var teamListScroller = $teamView.initScroll({pullDown: false,pullUpAction: function() {
		reqPromise("hosptail/doctor_list", {hospital:hospital,id:lastId,pageSize:15}).then(function(data) {
			// TODO 示例数据
			// data = {"msg":"获取医院医生列表成功！","list":[{"code":"D2016080002","job_name":" 全科医师","introduce":"我是全科医生","name":"大米全科1","dept_name":"","photo":"http://172.19.103.85:8882/res/images/2016/08/12/20160812170142_901.jpg","id":1262,"expertise":"我是全科医生","hospital_name":"嘉莲社区医疗服务中心"},{"code":"D2016080005","job_name":" 全科医师","introduce":"我是全科医生","name":"大米全科2","dept_name":"","photo":"","id":1271,"expertise":"我是全科医生","hospital_name":"嘉莲社区医疗服务中心"},{"code":"D2016080225","job_name":" 全科医师","introduce":"我是全科医生","name":"谭仁祝(全科)","dept_name":"","photo":"","id":1274,"expertise":"我是全科医生","hospital_name":"嘉莲社区医疗服务中心"},{"code":"D2010080225","job_name":" 全科医师","introduce":"我是全科医生","name":"谭仁祝(全科1)","dept_name":"","photo":"","id":1276,"expertise":"我是全科医生","hospital_name":"嘉莲社区医疗服务中心"}],"status":200};
			lastId = getLastItemId(data.list);
			var html = template("team_li_tmpl", data);
			$teamList.append(html);
			updatePullUpText(teamListScroller,data.list);
			teamListScroller.refresh();
		})
	}});
	d.close();
})
.then(function() {
	
	$teamList.on('click','li',function() {
		//checkUserAgent();
		var doctor = $(this).attr('data-code');
		window.location.href = "../../ssgg/html/doctor-homepage-new.html?state="+doctor+'&code='+code;
	});
	
}).catch(function(e) {
	console && console.error(e)
})