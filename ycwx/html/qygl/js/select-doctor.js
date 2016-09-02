var d = dialog({contentType:'load', skin:'bk-popup'}).show();
var Request = GetRequest(),
// 如果从签约首页进入时携带existedSanShi参数，表明该用户已经签约了三师全科医生，
// 根据该字段存在弹出提示是否与该医生签约家庭医生
existedSanShi = Request["existedSanShi"],
doctor = Request["doctor"],
doctorName = Request["doctorName"];

var userAgent = window.localStorage.getItem(agentName);
var $areaView = $('#area_view'),
	$areaList = $('#area_list');

// 搜索框
$('.searchbar').searchBar();

var pagetype = 16;

$(function(){
	checkUserAgent();	
})

var reqPromise = function(url,data) {
	 return new Promise(function(resolve, reject) {
		sendPost(url, data, "json", "post",
		  	function queryFailed () {
				dialog({contentType:'tipsbox', skin:'bk-popup' , content:'加载失败'}).show();
				// TODO 开启示例数据
				//resolve({ });
			}
	  		, function success(req) {
				resolve(req);
	  	});
	});
},
handleData = function(list) {
	if(!list) return [];
	// 签约状态对应的文本
	var signStatus = ["待签约","已签约","待解约"],
	groups = _.chain(list).map(function(o) {
		o.signStatusText = signStatus[o.signStatus] || "";
		o.dateText = (function() {
			switch(o.signStatus) {
				case 0: return "申请时间："+o.sqDate
				case 1: return "签约时效："+o.qyDate+' ~ '+o.endDate
				case 2: return "提交时间："+o.qyDate
			}
		})();
		return o;
	}).groupBy(function(o) {
		return o.signType;
	}).value();
	
	return groups;
};
if(userAgent) {
	userAgent = JSON.parse(userAgent);
} 
  		
reqPromise("patient/family_contract/getSignMessage",{patientCode:userAgent.uid})
 .then(function(data) {
 	var groups = handleData(data.list),
	familyList = groups[2] || [],
	sanshiList = groups[1] || [];
	return {
		familyDoctor: familyList[0],
		sanShiDoctor: (function() {
			return _.find(sanshiList,function(obj) {
				return obj.level==2;
			});
		})()
	}
 }).then(function(doctors) {
 	if(doctors.familyDoctor) {
 		window.location.href = server + "wx/html/ssgg/html/doctor-homepage-new.html?state="+doctors.familyDoctor.code+"&openid="+userAgent.openid;
 	} else if(existedSanShi || doctors.sanShiDoctor) {
 		var name = decodeURIComponent(doctorName||doctors.sanShiDoctor.name);
		var content = '您已存在三师签约，签约医生为'+ name +'医生，继续与'+name+'医生签约家庭医生服务吗？';
		dialog({
	        content: content,
	        okValue:'确定',
	        ok: function (){			            		
	        	window.location.href = server + "wx/html/ssgg/html/doctor-homepage-new.html?state="+(doctor||doctors.sanShiDoctor.code)+"&openid="+userAgent.openid;
	        }
		}).showModal();
		return false;
 	} 
 }).then(function(flag) {
 	if(flag!==false) {
 		// TODO 目前项目只有厦门，所以city设置为350200
		reqPromise("/patient/hosptail/getTownByCityCode",{city:'350200'}).then(function(data) {
		
			// TODO 示例数据
			// data = {"msg":"查询成功","list":[{"code":"350203","name":"思明区"},{"code":"350205","name":"海沧区"},{"code":"350206","name":"湖里区"},{"code":"350211","name":"集美区"},{"code":"350212","name":"同安区"},{"code":"350213","name":"翔安区"}],"status":200}
			var html = template("area_li_tmpl", data);
			$areaList.html(html);
			d.close();
		}).catch(function(e) {
			console && console.error(e)
		})
 	}
 }).catch(function(e) {
	console && console.error(e)
});
 