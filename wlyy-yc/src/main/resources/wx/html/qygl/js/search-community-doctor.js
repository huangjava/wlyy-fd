saveAgentPage("../../qygl/html/search-community-doctor.html");
// TODO 社区列表示例数据
var communitiesData = {"msg":"查询成功","list":[{"code":"3502050100","name":"海沧区嵩屿街道社区卫生服务中心"},{"code":"3502050101","name":"海沧社区卫生服务站"},{"code":"3502050200","name":"石塘社区卫生服务中心"},{"code":"3502050300","name":"东孚卫生院"},{"code":"3502050301","name":"天竺社区卫生服务站"},{"code":"3502050302","name":"国营厦门第一农场社区卫生服务站"},{"code":"3502050400","name":"新阳社区卫生服务中心"},{"code":"0a11148d-5b04-11e6-8344-fa163e8aee56","name":"厦门市海沧医院","photo":""}],"status":200};
// TODO 医生列表示例数据
var doctorsData = {"msg":"获取医院医生列表成功！","list":[{"code":"D2016080002","job_name":" 全科医师","introduce":"我是全科医生","name":"大米全科1","dept_name":"","photo":"http://172.19.103.85:8882/res/images/2016/08/12/20160812170142_901.jpg","id":1262,"expertise":"我是全科医生","hospital_name":"嘉莲社区医疗服务中心"},{"code":"D2016080005","job_name":" 全科医师","introduce":"我是全科医生","name":"大米全科2","dept_name":"","photo":"","id":1271,"expertise":"我是全科医生","hospital_name":"嘉莲社区医疗服务中心"},{"code":"D2016080225","job_name":" 全科医师","introduce":"我是全科医生","name":"谭仁祝(全科)","dept_name":"","photo":"","id":1274,"expertise":"我是全科医生","hospital_name":"嘉莲社区医疗服务中心"},{"code":"D2010080225","job_name":" 全科医师","introduce":"我是全科医生","name":"谭仁祝(全科1)","dept_name":"","photo":"","id":1276,"expertise":"我是全科医生","hospital_name":"嘉莲社区医疗服务中心"}],"status":200};

var userAgent = window.localStorage.getItem(agentName);
if(userAgent) {
	userAgent = JSON.parse(userAgent);
} 
// 请求URL参数,urls[0]:社区列表，urls[1]:医生列表
var urls = ["patient/hosptail/hospital_list","patient/hosptail/doctor_list"];

	// 搜索框 
var $searchbar = $('.searchbar'),
	// 搜索输入框
	$searchbarInput = $('.searchbar input'),
	// 搜索取消按钮
	$searchCancelBtn = $('.searchbar-cancel'),
	// 搜索框下面悬浮的搜索提示
	$searchSuggest = $('#search_suggest_text'),
	// 搜索结果展示容器
	$searchtResult = $('#search_result'),
	// 社区搜索结果容器
	$communityWrapper = $('#community_wrapper'),
	// 医生搜索结果容器
	$doctorWrapper = $('#doctor_wrapper'),
	// 查看更多社区结果容器
	$moreCommunityWrapper = $('#more_community_warp'),
	// 查看更多医生结果容器
	$moreDoctorWrapper = $('#more_doctor_warp'),
	// 搜索无结果时显示
	$noResultWrap = $('#no_result_wrap');

	// 列表分页相关，存储搜索结果最后一条记录的id
var lastCommunityId = 0,
	lastDoctorId = 0;
	
	// 滚动条实例
var moreCommunityScroller = null,
    moreDoctorScroller = null;

var isFromHistory = false;

var getReqPromise = function(url, data) {
	return new Promise(function(resolve, reject) {
		sendPost(url, data, "json", "post",
		  	function queryFailed (req) {
				dialog({contentType:'tipsbox', skin:'bk-popup' , content:'加载失败'}).show();
				// TODO 开启示例数据
				resolve({});
			}
	  		, function success(req) {
				if (req.loginUrl) {
					window.location.href = req.loginUrl;
					return;
				}
				resolve(req);
	  	});
	});
},
getSearchPromise = function(data){

	return Promise.all(_.map(urls,function(url){
		return getReqPromise(url, data);
	})).then(function(data) {
		if (data.loginUrl) {
			window.location.href = data.loginUrl;
			return;
		}
		return {
			// TODO 示例数据
			communities: communitiesData.list || data[0].list,
			doctors: doctorsData.list || data[1].list
//			communities: data[0].list,
//			doctors: data[1].list
		};
	});
},
// 控制搜索关键字悬浮提示的显示
showSearchSuggest = function(text) {
	var suggestText = '搜索“'+text+'”';
	// 如果text不为空，则显示；否则隐藏
	if(text&&text.trim().length) {
			$searchSuggest.text(suggestText);
			$searchSuggest.show();
	} else {
		$searchSuggest.text('');
		$searchSuggest.hide();
	}
},
// 显示搜索结果的社区列表（限制显示的数量最多为3）
showCommunityList = function(data) {

	if(data && data.length) {
		var html = template("community_li_tmpl", {list: data.slice(0,3)}),
		$communityList = $('#community_list');
		$communityList.html(html);
		// 搜索结果小于等于3个时，隐藏“查看更多”
		if(data.length<=3) {
			$communityWrapper.find('.more-result').hide();
		} else {
			$communityWrapper.find('.more-result').show();
		}
		$communityWrapper.show();
	} else {
		$communityWrapper.hide();
	}
},
// 显示搜索结果的医生列表（限制显示的数量最多为3）
showDoctorList = function(data) {
	if(data && data.length) {
		var html = template("doctor_li_tmpl", {list: data.slice(0,3)}),
		$communityList = $('#doctor_list');
		$communityList.html(html);
		// 搜索结果小于等于3个时，隐藏“查看更多”
		if(data.length<=3) {
			$doctorWrapper.find('.more-result').hide();
		} else {
			$doctorWrapper.find('.more-result').show();
		}
		$doctorWrapper.show();
	} else {
		$doctorWrapper.hide();
	}
},
// 显示更多社区列表
showMoreCommunityList = function(data,isAppend) {
	if(data && data.length) {
		var html = template("community_li_tmpl", {list: data}),
		$moreCommunityList = $('#more_community_list');
		isAppend?$moreCommunityList.append(html):$moreCommunityList.html(html);
		$moreCommunityWrapper.show();
	} else {
		// 如果不是分页加载，也就是首屏时，若查无数据则隐藏
		!isAppend && $moreCommunityWrapper.hide();
	}
},
// 显示更多医生列表
showMoreDoctorList = function(data,isAppend) {
	if(data && data.length) {
		var html = template("doctor_li_tmpl", {list: data}),
		$moreDoctorList = $('#more_doctor_list');
		isAppend?$moreDoctorList.append(html):$moreDoctorList.html(html);
		$moreDoctorWrapper.show();
	} else {
		// 如果不是分页加载，也就是首屏时，若查无数据则隐藏
		!isAppend && $moreDoctorWrapper.hide();
	}
},
// 分页查询社区列表
searchCommunityByPaging = function () {
	var kw = $searchbarInput.val();
	// TODO 示例示例搜索参数
	// id: 上次搜索结果列表最后一条记录id，type固定为2，query:搜素关键字，pageSize:每页条数
	var url = urls[0],
	params = { id:0, query: kw,pageSize:15,type:2 ,openId:openId,random:random};
	getReqPromise(url,params).then(function(data){
		if (data.loginUrl) {
			window.location.href = data.loginUrl;
			return;
		}
		// TODO 示例数据
		var communities = communitiesData.list;
		//var communities = data.list;
		showMoreCommunityList(communities);
		$moreCommunityWrapper.show();
		lastCommunityId = getLastId(data);
		if(!moreCommunityScroller) {
			moreCommunityScroller = initScroller($moreCommunityWrapper,url,
			function() { // 传递分页参数
				return $.extend({},params,{id:lastCommunityId});
			},function(data) {
				// TODO 示例数据
				data = {"msg":"查询成功！","list":[{"code":"3502130500","address":"新圩镇龙新街","province":"350000","town":"350213","city":"350200","level":2,"name":"新圩中心卫生院","levelName":"社区医院","id":44},{"code":"3502130400","address":"内厝镇上塘村240号","province":"350000","town":"350213","city":"350200","level":2,"name":"内厝卫生院","levelName":"社区医院","id":43},{"code":"3502130300","address":"马巷镇民安路123号","province":"350000","town":"350213","city":"350200","level":2,"name":"马巷卫生院","levelName":"社区医院","id":42},{"code":"3502130200","address":"新店镇新兴街永兴路9号","province":"350000","town":"350213","city":"350200","level":2,"name":"新店中心卫生院","levelName":"社区医院","id":41},{"code":"3502130100","address":"大嶝街道田乾社区路口94号","province":"350000","town":"350213","city":"350200","level":2,"name":"大嶝卫生院（或）大嶝社区卫生服务中心","levelName":"社区医院","id":40},{"code":"3502120800","address":"莲花镇美埔村91号","province":"350000","town":"350212","city":"350200","level":2,"name":"莲花卫生院","levelName":"社区医院","id":39},{"code":"3502120700","address":"西柯针镇西柯村","province":"350000","town":"350212","city":"350200","level":2,"name":"同安区西柯中心卫生院","levelName":"社区医院","id":38},{"code":"3502120600","address":"新民镇乌涂村溪仔尾","province":"350000","town":"350212","city":"350200","level":2,"name":"新民卫生院","levelName":"社区医院","id":37},{"code":"3502120500","address":"汀溪镇汀溪街423号","province":"350000","town":"350212","city":"350200","level":2,"name":"汀溪卫生院","levelName":"社区医院","id":36},{"code":"3502120400","address":"五显镇垵炉村五显宫里1号","province":"350000","town":"350212","city":"350200","level":2,"name":"五显卫生院","levelName":"社区医院","id":35}],"status":200};
				showMoreCommunityList(data.list,true);
				lastCommunityId = getLastId(data) || lastCommunityId;
				
				var kw = $searchbarInput.val();
				highlightKeyword(kw);
				moreCommunityScroller.refresh();
			});
		} 
		highlightKeyword(kw);
		moreCommunityScroller.refresh();
	});
},
// 分页查询医生列表
searchDoctorByPaging = function () {
	var kw = $searchbarInput.val();
	// TODO 示例示例搜索参数
	// id: 上次搜索结果列表最后一条记录id，type固定为2，query:搜素关键字，pageSize:每页条数
	var url = urls[1],
	params = { id:0, query: kw,pageSize:15,type:2,openId:openId,random:random };
	getReqPromise(url,params).then(function(data){
		if (data.loginUrl) {
			window.location.href = data.loginUrl;
			return;
		}
		// TODO 示例数据
		var doctors = doctorsData.list;
		//var doctors = data.list;
		showMoreDoctorList(doctors);
		$moreDoctorWrapper.show();
 		lastDoctorId = getLastId(data);
		if(!moreDoctorScroller) {
			moreDoctorScroller = initScroller($moreDoctorWrapper,url,
			function() { // 传递分页参数
				return $.extend({},params,{id:lastDoctorId,openId:openId,random:random});
			},function(data) {
				// TODO 示例数据
				data = {"msg":"查询成功！","list":[{"jobName":" 全科医师","deptName":"","code":"D2010080225","sex":2,"sexName":"女","name":"谭仁祝(全科1)","photo":"","id":1276,"hospitalName":"嘉莲社区医疗服务中心","dept":"","job":"","hospital":"3502030500"},{"jobName":" 全科医师","deptName":"","code":"D2016080225","sex":2,"sexName":"女","name":"谭仁祝(全科)","photo":"","id":1274,"hospitalName":"嘉莲社区医疗服务中心","dept":"","job":"","hospital":"3502030500"},{"jobName":" 全科医师","deptName":"","code":"D2016080005","sex":2,"sexName":"女","name":"大米全科2","photo":"","id":1271,"hospitalName":"嘉莲社区医疗服务中心","dept":"","job":"","hospital":"3502030500"},{"jobName":" 全科医师","deptName":"心血管科","code":"D20160809002","sex":1,"sexName":"男","name":"孙杨(全)","photo":"","id":1264,"hospitalName":"莲前第一社区医疗服务中心","dept":"","job":"","hospital":"3502030400"},{"jobName":" 全科医师","deptName":"","code":"D2016080002","sex":1,"sexName":"男","name":"大米全科1","photo":"http://172.19.103.85:8882/res/images/2016/08/12/20160812170142_901.jpg","id":1262,"hospitalName":"嘉莲社区医疗服务中心","dept":"","job":"","hospital":"3502030500"},{"jobName":" 全科医师","deptName":"心血管科","code":"D20160802","sex":1,"sexName":"男","name":"邹林全科","photo":"","id":1244,"hospitalName":"莲前第一社区医疗服务中心","dept":"","job":"","hospital":"3502030400"},{"jobName":" 全科医师","deptName":"心血管科","code":"D2016001","sex":1,"sexName":"男","name":"陈啊咋全科","photo":"","id":1240,"hospitalName":"莲前第一社区医疗服务中心","dept":"","job":"","hospital":"3502030400"},{"jobName":"全科医师","deptName":"心血管科","code":"D20160805900002","sex":1,"sexName":"男","name":"杨炜武","photo":"http://172.19.103.85:8882/res/images/2016/08/05/20160805154335_66.png","id":1153,"hospitalName":"莲前第一社区医疗服务中心","dept":"","job":"","hospital":"3502030400"},{"jobName":"全科医师","deptName":"心血管科","code":"D20160805900001","sex":1,"sexName":"男","name":"叶泽华","photo":"","id":1152,"hospitalName":"莲前第一社区医疗服务中心","dept":"","job":"","hospital":"3502030400"},{"jobName":"副主任医师","deptName":"妇产科","code":"48833fff339111e6badcfa163e789033","sex":2,"sexName":"女","name":"丘新全","id":1137,"hospitalName":"莲前第一社区医疗服务中心","dept":"","job":"","hospital":"3502030400"}],"status":200};
				showMoreDoctorList(data.list,true);
				lastDoctorId = getLastId(data) || lastDoctorId;
				
				var kw = $searchbarInput.val();
				highlightKeyword(kw);
				moreDoctorScroller.refresh();
			});
		} 
		highlightKeyword(kw);
		moreDoctorScroller.refresh();
	});
},
// 获取分页搜索返回的最后一条记录的id
getLastId = function(data) {
	var lastObj = data.list && data.list.length && data.list[data.list.length-1];
	// 最后一条记录
	if(lastObj) {
		return lastObj.id;
	} else {
		return null
	}
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
// 关键字高亮显示
highlightKeyword = function(kw) {
	$searchTarget = $('.search-target-text');
	$searchTarget.hide();
	_.each($searchTarget,function(ele){
		var $ele = $(ele),
		text = $ele.text(),
		reg = new RegExp(kw+"(?!>)","gi"),
		html = text.replace(reg,'<em>'+kw+'</em>');
		$ele.html(html);
	});
	$searchTarget.show();
},
// 区域滚动条分页实例初始化
initScroller = function($el,url,getData,pullUpAction) {
	var scroller = $el.initScroll({pullDown: false,pullUpAction: function() {
		var data = getData();
		getReqPromise(url, data).then(function(data) {
			if(pullUpAction && $.isFunction(pullUpAction)) {
				pullUpAction(data);
				updatePullUpText(scroller,data.list);
			}
		})
	}});
	
	return scroller;
},
// 搜索框搜索执行方法
search = function () {
	// 是否是“查看更多”模式
	var isMoreMode = location.hash.indexOf('more')>=0,
	// 获取查看更多的类型(社区或者医生)
	moreType = location.hash.split('-')[1];
	if(isMoreMode) {
		// 返回最初页面无hash值时的页面状态
		history.go(-1);
	} 
		
	var kw = $searchbarInput.val().trim(),
		// 社区搜索结果列表数据
		communities = [],
		// 医生搜索结果列表数据
		doctors = [];
		// 隐藏搜索提示
		showSearchSuggest(false);
		// 搜索参数

		return kw && getSearchPromise({id:0,query: kw,type:2,pageSize:4}).then(function(data){

			communities = data.communities;
			doctors = data.doctors;　
			
			if(!communities.length && !doctors.length) {
				$noResultWrap.show();
			} else {
				$noResultWrap.hide();
				showCommunityList(communities);
				showDoctorList(doctors);
				showMoreCommunityList(false);
				showMoreDoctorList(false);
				$searchtResult.show();
				highlightKeyword(kw);
			}
		});
},
// 重置搜索结果的容器高度（为了让滚动条出现在容器内部，而不引起外部内容滚动）
resetResultWrapHeight = function() {
	var winHeight = $(window).height(),
	diff = 45,
	$wrap = $('#search_result');
	$wrap.height(winHeight-diff);
},
// 监听窗口大小变化，重置所属区列表、社区列表列表容器高度
resultWrapAutoAdapt = function() {
	resetResultWrapHeight();
	$(window).on('resize',function() {
		resetResultWrapHeight();
	});
};

resultWrapAutoAdapt();
new Promise(function(resolve, reject) {
	// 搜索框初始化
	$searchbar.searchBar();
	$searchbarInput.click();
	$searchbarInput.focus();
	resolve(true);
	
}).then(function() {
	// 注册hash值变化事件,为了判断是否点击了"查看更多",以及在点击返回时能够回到之前的查询结果页面状态
	window.onhashchange = function() {
		// 是否是“查看更多”模式
		var isMoreMode = location.hash.indexOf('more')>=0,
		// 获取查看更多的类型(社区或者医生)
		moreType = location.hash.split('-')[1];
		if(isMoreMode) {
			$searchtResult.hide();
			lastCommunityId = 0;
			lastDoctorId = 0;
			switch(moreType) {
				case 'community':
					searchCommunityByPaging();
					break;
				case 'doctor':
					searchDoctorByPaging();
					break;
			}
		} else {
			showMoreCommunityList(false);
			showMoreDoctorList(false);
			$searchtResult.show();
		}
	};
	$searchbarInput.on('focus',function() {
//		var text = $(this).val();
//		if(text.trim()) {
	
//			showSearchSuggest(text);
//		}
	}).on('input', function() {
		var text = $(this).val().trim();
		showMoreCommunityList(false);
		showMoreDoctorList(false);
		$searchtResult.hide();
		showSearchSuggest(text);	
	}).on('keydown',function(e) {
		if (e.which === 13) {
			search();
		}
	});
	
	$searchSuggest.on('click',function() {
		search();
	});
	
	// 当点击“查看更多”后hash值发生变化，同时增加了一条历史记录，所以返回之前页面需要history.go(-2)
	$searchCancelBtn.on('click',function() {
		// 是否是“查看更多”模式
		var isMoreMode = location.hash.indexOf('more')>=0;
		isMoreMode?window.history.go(-2):window.history.go(-1);
	});
	
	$('#doctor_list,#more_doctor_list').on('click','li',function() {
		var code = $(this).attr('data-code');
		window.location.href = "../../ssgg/html/doctor-homepage-new.html?state="+code+"&openid="+userAgent.openid;
	});

}).catch(function(e) {
	alert(e);
	console && console.error(e);
});
window.onpageshow = function() {
	
	var $input = $('.searchbar input[type=search]'),
	kw = $input.val().trim();
	if(kw) {
		var d = dialog({contentType:'load', skin:'bk-popup'}).show();
		var promise = search();
		if(promise) {
			promise.then(function() {
				d.close();
			});
		}
	}
}