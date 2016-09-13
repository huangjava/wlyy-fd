// 页面标识，未登录时将重定向到登录页面，根据这个pagetype来保证登录后的跳转到该页面
saveAgentPage("../../qygl/html/signing-doctors.html");
var pagetype = -1;
var d = dialog({contentType:'load', skin:'bk-popup'});
var userAgent = window.localStorage.getItem(agentName);
var Request = GetRequest(),
uid = "",
code = Request["code"],
reqPromise = function(url,data) {
	 return new Promise(function(resolve, reject) {
		sendPost(url, data, "json", "post",
		  	function queryFailed (res) {
				dialog({contentType:'tipsbox', skin:'bk-popup' , content:'加载失败'}).show();
				// TODO 开启示例数据
				// resolve({});
			}
	  		, function success(res) {
				resolve(res);
	  	});
	});
},
// 对数据按照签约类型进行分组，并添加额外信息
handleData = function(list) {
	if(!list) return [];
	// 签约状态对应的文本
	var signStatus = ["审核中","已签约","待解约"],
	// 签约状态应用的样式类列表
	signStyleClass = ["to-signed","be-signed","to-surrender"],
	groups = _.chain(list).map(function(o) {
		o.signStatusText = signStatus[o.signStatus] || "";
		o.signStyleClass = signStyleClass[o.signStatus] || "";
		o.dateText = (function() {
			switch(o.signStatus) {
				case 0: return "申请时间："+o.sqDate
				case 1: return "时效："+o.qyDate+' ~ '+o.endDate
				case 2: return "提交时间："+o.qyDate
			}
		})();
		return o;
	}).groupBy(function(o) {
		return o.signType;
	}).value();
	
	return groups;
},
// 更新签约Card列表视图
updateCardView = function($card,data) {
	if(!data.list.length) {
		$card.hide();
		return ;
	} else {
		$card.show();
	}
	var html = template("doctor_li_tmpl", data);
	$('.signing-docotors',$card).html(html);
	$('.date-text',$card).text(data.list[0].dateText);
	$('.status-text',$card).text(data.list[0].signStatusText);
	$('.status',$card).addClass(data.list[0].signStyleClass);
},
getReqPromises = function() {
	return Promise.all(_.map([{url: "weixin/getOpenidByCode",data:{code:code}},
	{url:"patient/family_contract/getSignMessage",data:{patientCode:uid,openId:openId,random:random}}],
	function(param){
		return reqPromise(param.url,param.data);
	}));
};

//checkUserAgent();

if(userAgent) {
	d.show();
	userAgent = JSON.parse(userAgent);
	uid = userAgent.uid;
}  else {
	checkUserAgent();
}

new Promise(function(resolve, reject) {
	
  	sendPost("patient/family_contract/getSignMessage", {patientCode:uid,openId:openId,random:random}, "json", "post",
	  	function queryFailed () {
			dialog({contentType:'tipsbox', skin:'bk-popup' , content:'加载失败'}).show();
		}
  		, function success(req) {
			if (req.loginUrl) {
				window.location.href = req.loginUrl;
				return;
			}
  			// TODO 启用示例数据 
  			// resolve(req);
			if (req.status == 200) {
				resolve(req);
			} else {
				reject(Error(req.status));
			}
  		});
}).then(function(data) {
	if (data.loginUrl) {
		window.location.href = data.loginUrl;
		return;
	}
	// TODO 示例数据
	//data = []||{"msg":"获取列表成功！","list":[{"jobName":"健康管理师","code":"D2016080006","disease":1,"level":3,"endDate":"2017-08-04","sex":1,"signStatus":1,"name":"大米健管师2","photo":"","hosptialName":"嘉莲社区医疗服务中心","signType":"1","qyDate":"2016-08-04"},{"jobName":" 全科医师","code":"D2016080005","disease":1,"level":2,"endDate":"2017-08-04","sex":2,"signStatus":1,"name":"大米全科2","photo":"","hosptialName":"嘉莲社区医疗服务中心","signType":"1","qyDate":"2016-08-04"},{"jobName":"专科医师","code":"D2016080004","disease":1,"level":1,"endDate":"2017-08-04","sex":1,"signStatus":1,"name":"大米专科2","photo":"","hosptialName":"厦门大学附属第一医院思明分院","signType":"1","qyDate":"2016-08-04"},{"jobName":" 全科医师","code":"D2016080005","disease":1,"level":2,"endDate":"2017-08-15","sex":2,"signStatus":0,"name":"大米全科2","photo":"","hosptialName":"嘉莲社区医疗服务中心","signType":"2","sqDate":"2016-08-15"}],"status":200};
	// 根据签约类型signType分组，groups[1]为家庭医生， groups[2]为慢病管理
	var groups = handleData(data.list),
	familyList = groups[2] || [],
	sanshiList = groups[1] || [],
	$hasContent = $('.has-content'),
	$noContent = $('.none-content'),
	$onlySanshi = $('.only-sanshi'),
	$otherView = $('#other_view');

	// 签约类型
	var type;
	// 签约三师（也就是慢病管理）医生的level=2(表示“全科”)的医生
	var quanKeDoctor;
	// 没有任何类型的签约医生
	if(!familyList.length && !sanshiList.length) {
		$hasContent.hide();
		$noContent.show();
		$otherView.show();
		$(document.body).addClass('bc-9beaf6');
		type = 0;
	}
	// 仅有家庭签约医生
	else if(familyList.length && !sanshiList.length) {
		$hasContent.show();
		$otherView.show();
		$noContent.hide();
		$otherView.find('.btn-wrap').hide();
		type = 1;
	}
	// 仅有慢病管理签约医生
	else if(sanshiList.length && !familyList.length) {
		$hasContent.show();
		$noContent.hide();
		$otherView.show();
		type = 2;
		// 查找到全科医生，level：1专科医生，2全科医生，3健康管理师
		var quanKeDoctor = _.find(sanshiList,function(obj) {
			return obj.level==2;
		});
	} 
	// 既有慢病管理签约医生，也有家庭签约医生
	else {
		$hasContent.show();
		$otherView.hide();
		type = 3;
	}
	// 更新家庭医生签约列表
	updateCardView($('#family_card'),{list: familyList});
	// 更新慢病管理签约列表
	updateCardView($('#sanshi_card'),{list: sanshiList});
	
	// 仅有慢病管理签约医生或者没有任何签约医生时页面底部按钮显示方式控制
	if( $hasContent[0].scrollHeight+$onlySanshi[0].scrollHeight>document.body.offsetHeight) {
		$onlySanshi.css('position','relative');
	}
	
	d.close();
	
	return {type: type,quanKeDoctor: quanKeDoctor};
}).then(function(data) {
	// 签约类型 
	var type = data.type,
	// 签约的慢病管理全科医生
	quanKeDoctor = data.quanKeDoctor,
	url = "select-doctor.html";
	if(type == 2) { // 仅有慢病管理签约医生
		url += "?existedSanShi=1&doctor="+quanKeDoctor.code+"&doctorName="+quanKeDoctor.name;
	} 
	$('#sign_family_doctor_btn').click(function() {
		location.href = url 
	});
	
}).catch(function(e) {
	console && console.error(e);
});