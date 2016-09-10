
var d = dialog({
	contentType: 'load',
	skin: 'bk-popup'
}).show();

var d1 = dialog({
	contentType: 'load',
	skin: 'bk-popup',
	content: '提交中...'
});
var doctor = "";
var isQr = "";
var waitSign;

var level="";
// 链接中是否有openid
var openidInLink = false;
// 存储链接中携带的invilogcode参数，用来判断是否是从推送消息进入处理“签约邀请”(暂时无法实现此方式)
var signInvitationInLink;
var inviCode;
// 通过推送消息方式进入，查询得到的被邀请对象的code
var patientCode;
var userAgent;
var openid;
var invitationState;
var pagetype = 18;
var loginParams = "";

$(function() {
	
	userAgent = window.localStorage.getItem(agentName);
	
	Request = GetRequest();
	openid = Request["openid"];
	code = Request["code"];
	var currentSigner = "";// 点击邀请的推送链接携带的签约者
	var handerAccount = "";// 点击邀请的推送链接携带的处理者的账号
	isQr = Request["isQr"];
    level = Request["level"];
	openidInLink = (openid!==undefined)?true:false;
	loginParams = Request["loginParams"];
	
	if(loginParams) {
		loginParams = JSON.parse(loginParams);
	}
	
	var Request = new Object();
	Request = GetRequest();
	invitationState = Request["state"];

	
	signInvitationInLink = Request["invilogcode"];
	
	//shenzaixin 修改，增加openid的获取
		if(!openidInLink && code != undefined && code != "") {
			
			sendPost("weixin/getOpenidByCode",{"code":code},'JSON','GET',
						function(res){
							//queryFailed(res);
							handleBusiness();
						},function(res){
							if(res.status==200){
								openid = res.openid;
			    				openidInLink = openid!==undefined?true:false;
							}
							handleBusiness();
			});
		}
		else{
			handleBusiness();
		}
	
});

function handleBusiness(){
	
	if(invitationState.indexOf("__")>-1) {
		doctor = invitationState.split("__")[0];
	} else {
		doctor = invitationState;
	}
	if(!userAgent) {
		
		if(!openid) 
		{
			if(invitationState){
				$('#doctor_qrcode').find("img").attr('src',server+'qrcode/doctor_img?doctor='+doctor); 
				$('#doctor_qrcode').show();
			}
			
		} 
		
		data = { doctor: doctor };
		sendPost('family_contract/homepage/homepage', data, 'json', 'post', queryFailed, querySuccess);
		
	}else{
		
		var userJson = JSON.parse(userAgent);
		var patient = userJson.uid;
		// 获取微信授权
		closeWindow();
		// 如果invitationState含有“-”，则表示是从消息推送中跳转过来
		if(invitationState) {
			var codes = invitationState.split("__");
			if(codes &&codes.length == 4) {
				doctor = codes[0];
				inviCode = codes[1];
				currentSigner = codes[2];
				handerAccount = codes[3];
				if(currentSigner != patient) {
					dialog({
						content:'对不起，您已更换账号，需重新登录<span style="color:#333">'+handerAccount+'</span>账号后，方可处理该邀请。',
						okValue:'我知道了',
						ok: function() {
							wx.closeWindow();
						}
					}).showModal();
					
					return ;
				}
	
				signInvitationInLink = true;
			}
			
		}
		//checkUserAgent();
		queryInit();
		bindEvents();
	}
	
}


function bindEvents(){
	// 点击“+”号的控制
	$('.btn-main').on('click',function() {
		$(this).hide();
		$('#btnMenu').show();
		$("#overlay_pop").show();
		$("#overlay_pop").css("background-color","transparent");
		$("body,html").css("overflow-y","hidden");
	});
	//点击遮罩事件
	$("#overlay_pop").on('click',function() {
		$(this).hide();
		$('#btnMenu').hide();
		$('.btn-main').show();
		$('#introduce_guide').hide();
		$('#introduce_text').hide();
		$("body,html").css("overflow-y","initial");
	});	
	$('#btnMenu').on('click','.btn-item',function () {
		var type = $(this).attr('data-type');
		$('#btnMenu').hide();
		$('.btn-main').show();
		$("#overlay_pop").hide();
		$("body,html").css("overflow-y","hidden");
		switch(type) {
			case 'break': 
				// 点击“解约”控制
				overSign();
				
				break;
			case 'introduce':
			 	//window.scrollTo(0,0);
				
				// 点击“推荐”控制
				$('#overlay_pop').show();
				$("#overlay_pop").css("background-color","rgba(0,0,0,0.7)");
				$('#introduce_guide').show();
				$('#introduce_text').show();
				break;
			case 'consultation':
				// 点击“推荐”控制 （其实这里已经要跳转了，画面不用做什么显示控制）
				location.href = "../../yszx/html/doctor-consultation.html";
				break;
			default:
				// 点击“取消”的控制
				$("body,html").css("overflow-y","initial");
		}
	});
}

function popThanksDialog() {
	dialog({
        content: '<div class="c-t-left">您的签约申请已提交，请等待医生的审核。请您的亲友们也一起享受家庭医生的服务吧~</div>',
        okValue:'前往分享',
        ok: function (){				            		
        	location.href = '../../qygl/html/signing-share.html'
        },
        cancelValue: '我知道了',
        cancel: function () {
			return;
        }
	}).showModal();
}

function closeWindow() {
	var Request = new Object();
	Request = GetRequest();
	var code = Request["code"];
	//从后台那边获取签名等信息
	var params = {};
	params.pageUrl = window.location.href;
	$.ajax(server + "weixin/getSign", {
		data: params,
		dataType: "json",
		type: "post",
		success: function(res) {
			if(res.status == 200) {
				var t = res.data.timestamp;
				var noncestr = res.data.noncestr;
				var signature = res.data.signature;
				wx.config({
					//debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
					appId: appId, // 必填，公众号的唯一标识
					timestamp: t, // 必填，生成签名的时间戳
					nonceStr: noncestr, // 必填，生成签名的随机串
					signature: signature, // 必填，签名，见附录1
					jsApiList: [
							'closeWindow'
						] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
				});
			}
		}
	});
}
function popOverlay(flag) {
	var height = document.body.scrollHeight,
		$pop = $('#overlay_pop');
	
	$('#introduce_guide').hide();
	$('#introduce_text').hide();
	if(flag) {
		$pop.one('click',function() {
			$('.btn-main img').attr('src','../images/geduoxuanfu_icon.png');
			$('.btn-item').hide();
			$('.btn-main').addClass('close');
			$('#introduce_guide').hide();
			$('#introduce_text').hide();
			$pop.hide();
		});
		$pop.height(height).show();
	} else {
		$pop.hide();
	}
}

// 签约邀请处理
function invitSignHandle() {
	sendPost('patient/family_contract/getPatientSign', {homePageDoctorCode: doctor,invitePatientCode: inviCode}, 'json', 'post', queryFailed, function(res) {
		
		var data = res.data;

		// 被邀请人是否已签约家庭医生
		var isSignedFamilyDoctor = data.type=="1" && data.sign_type != -1 && data.sign_type != 0,
		// 被邀请人是否已申请签约
		isRequetSigning = data.sign_type==0,
		// 申请签约的医生是否为该医生
		isSignedDoctor = doctor == data.sign_doctor,
		// 是否签约三师
		isSignedSanShi = data.type=="2" && data.sign_type != -1,
		// 签约的三师中全科医生是否为发出邀请的医生
		isSanShiDoctor = doctor == data.sign_doctor;
		
		patientCode = data.patient_code;
		
		// 被邀请人是否已签约家庭医生
		if(isSignedFamilyDoctor) {
			dialog({
				content: data.patient_name+'已经签约，请忽略该邀请。',
				okValue:'我知道了',
				ok: function() {
					wx.closeWindow();
				}
			}).showModal();
		} 
		// 被邀请人是否已申请签约
		else if (isRequetSigning) {
			if(isSignedDoctor){
				document.getElementById("btnSign").innerHTML = '<a onclick="cancelSign()" class="c-btn c-btn-E0A526 c-btn-full c-btn-radius c-f18">取消'+data.patient_name+'的签约申请</a>';
				$('#btnSign').show();
				$('#btnSign').closest("div").addClass("h64");
				updateBodyHeight();
			} else {
				dialog({
					content: data.patient_name+'已向其他医生申请签约，取消申请后，方可与该医生签约。',
					okValue:'我知道了',
					ok: function() {
						wx.closeWindow();
					}
				}).showModal();
			}
		}
		// 是否签约三师
		else if(isSignedSanShi) {
			// 签约的三师中全科医生是否为发出邀请的医生
			if(isSanShiDoctor) {
				document.getElementById("sign_invitation").innerHTML = 
				'<div class="c-row">'
					+'<div class="c-50 c-tac height-50 lheight-50 bc-ff9526 c-fff" onclick="ignoreSignInvitation()">忽略签约邀请</div>'
					+'<div class="c-50 c-tac height-50 lheight-50 bc-75bf00 c-fff"><a href="javascript:void(0);" onclick="agreeSignInvitation()" class="c-fff">同意与其签约</a></div>'
				+'</div>';

			} else {
				dialog({
					content: data.patient_name+'已存在三师签约，签约医生为'+data.sign_doctorName+'医生，不可与其他医生签约家庭医生。',
					okValue:'我知道了',
					ok: function() {
						wx.closeWindow();
					}
				}).showModal();
			}
		} else {

			document.getElementById("sign_invitation").innerHTML = 
				'<div class="c-row">'
					+'<div class="c-50 c-tac height-50 lheight-50 bc-ff9526 c-fff" onclick="ignoreSignInvitation()">忽略签约邀请</div>'
					+'<div class="c-50 c-tac height-50 lheight-50 bc-75bf00 c-fff"><a href="javascript:void(0);" onclick="agreeSignInvitation()" class="c-fff">同意与其签约</a></div>'
				+'</div>';
		}
		
		querySuccess({
			"status":200,
			"data":{
				"doctor": doctor,
				"jobName": data.homepage_jobName,
				"intro": data.homepage_intro,
				"name": data.homepage_doctorName,
				"sign": data.sign_type,
				"photo": data.homepage_photo,
				"dept": data.homepage_dept,
				"services": [],
				"job": data.homepage_photo,
				"hospital": data.homepage_hospital,
				"expertise": data.homepage_expertise,
				"group": data.group
			}
		});
		
	});
}

function queryInit() {
	//d.show();
	var data = {};
	var Request = new Object();
	Request = GetRequest();
	doctor = doctor || Request["state"];
	if(doctor.indexOf("__")>-1) {
		doctor = doctor.split("__")[0];
	}
	waitSign = Request["waitSign"];
	data.doctor = doctor;
	var flag = window.localStorage.getItem("popThanksDialog");
	if(!flag && waitSign) {
		window.localStorage.setItem("popThanksDialog","1");
		popThanksDialog();
	} else {
		window.localStorage.removeItem("popThanksDialog");
	}
	if(signInvitationInLink) {
		sendPost('patient/family/findPatientInviteLog',{invitePatientCode:inviCode}, 'json', 'post',function(){
			dialog({contentType:'tipsbox', skin:'bk-popup' , content:'请求失败'}).show();
		},function(res) {
			if(res.data.dealType==0) {
				invitSignHandle();
			} else {
				dialog({
					content: "该邀请已处理或失效。",
					okValue:'我知道了',
					ok: function() {
						wx.closeWindow();
					}
				}).showModal();
			}
		});
		
	}else{
		sendPost('patient/family_contract/homepage', data, 'json', 'post', queryFailed, querySuccess);
	}
}

function queryFailed(res) {
	d.close();
	if(res && res.msg) {
		dialog({
			contentType: 'tipsbox',
			skin: 'bk-popup',
			content: res.msg
		}).show();
	} else {
		dialog({
			contentType: 'tipsbox',
			skin: 'bk-popup',
			content: '加载失败'
		}).show();
	}
}

function querySuccess(res) {
	// TODO 示例数据
//	res = {
//		"msg": "医生主页查询成功！",
//		"data": {
//			"doctor": "D20160322000002",
//			"jobName": "主任医师",
//			"intro": "慢性阻塞性肺疾病、支气管扩张等能够较为熟练地结合中医辨证与西医辨病两套方法进",
//			"name": "聂青",
//			"sign": -1,
//			"photo": "http://172.19.103.85:8882/res/images/2016/07/27/20160727010854_298.jpeg",
//			"dept": "呼吸内科",
//			"services": [],
//			"job": "",
//			"hospital": "金山社区医疗服务中心",
//			"expertise": "及实验研究工作十年余，先后参加国家级、省部级多项课题的研究，在科室积极开展的中医特色疗法如穴位注射和敷贴、中药雾化等，疗效显著，对疑难疾病的诊治能坚持自己的观点，和科室同仁一道成功抢救许多肺心病、肺性脑病和呼吸衰竭等危重患。",
//			"group": "0"
//		},
//		"status": 200
//	};
	
	if(res.status == 200) {

		var name = res.data.name;
		var jobName = res.data.jobName;
		var hospitalName = res.data.hospital;
		var deptName = res.data.dept;
		var photo = res.data.photo;
		var expertise = res.data.expertise;
		var introduce = res.data.intro;
		var sign = Number(res.data.sign);
		var group = Number(res.data.group);
		var url2 = "../../../images/d-default.png";
		if(photo != null && photo != "") {
			url2 = photo;
		}
		if(!jobName) jobName = "";
		if(!deptName) deptName = "";
		if(!expertise) expertise = "无";
		if(!introduce) introduce = "无";
		document.getElementById("photo").src = url2;
		document.getElementById("name").innerHTML = name;
		document.getElementById("jobName").innerHTML = jobName;
		document.getElementById("hospitalName").innerHTML = hospitalName;
		document.getElementById("deptName").innerHTML = deptName;
		document.getElementById("expertise").innerHTML = expertise;
		document.getElementById("introduce").innerHTML = introduce;
		document.getElementById("doctor_name").innerHTML = name;
		//动态修改title的值
        var $body = $('body');
       document.title = name+"医生诚挚为您服务";
        // hack在微信等webview中无法修改document.title的情况
        var $iframe = $('<iframe src="/favicon.ico"></iframe>');
        $iframe.on('load',function() {
            setTimeout(function() {
                $iframe.off('load').remove();
            }, 0);
        }).appendTo($body);
		
		if(group == 1) {
			$("#ul-health").show();
		} else if(group == 2) {
			$("#ul-disease").show();
		} else if(group == 3) {
			$("#ul-sixfive").show();
		} else {
			$("#ul-common").show();
		}

		if(signInvitationInLink) {
			d.close();
			return ;
		}
		if(sign == 0) { // 待签约
			document.getElementById("btnSign").innerHTML = '<a onclick="cancelSign()" class="c-btn c-btn-E0A526 c-btn-full c-btn-radius c-f18">取消申请</a>';
			$('#btnSign').show();
			$('#btnSign').closest("div").addClass("h64");
			updateBodyHeight();
			
		} else if(sign == 1) { // 已签约
			//document.getElementById("btnSign").innerHTML = '<a onclick="overSign()" class="c-btn c-btn-E0A526 c-btn-full c-btn-radius c-f18">申请解约</a>';
			$('#btnSign').hide();
			$('.btn-main').show();
			$('#btnSign').closest("div").removeClass("h64");
		} else if(sign == -1) { // 患者解约
			// 有openid显示“申请签约”按钮
			var userJson = userAgent && JSON.parse(userAgent);
			if(openidInLink || (userJson&&userJson.openid)) {
				document.getElementById("btnSign").innerHTML = '<a onclick="startSign()" class="c-btn c-btn-4dcd70 c-btn-full c-btn-radius c-f18">申请签约</a>';
				$('#btnSign').show();
				$('#btnSign').closest("div").addClass("h64");
				updateBodyHeight();
			}
			// 邀请签约时显示
//			else if(signInvitationInLink) {
//				document.getElementById("sign_invitation").innerHTML = 
//				'<div class="c-row">'
//					+'<div class="c-50 c-tac height-50 lheight-50 bc-ff9526 c-fff" onclick="ignoreSignInvitation()">忽略签约邀请</div>'
//					+'<div class="c-50 c-tac height-50 lheight-50 bc-75bf00 c-fff"><a href="javascript:void(0);" onclick="agreeSignInvitation()" class="c-fff">同意与其签约</a></div>'
//				+'</div>';
//			} 
			// 否则显示医生二维码
			else {
				if(level&&level!=2){
					$('#doctor_qrcode').hide();
				}else{
					if(doctor){
						$('#doctor_qrcode').find("img").attr('src',server+'qrcode/doctor_img?doctor='+doctor); 
					}
					$('#doctor_qrcode').show();
				}
			}
			
			$("#divAgree").hide();
		} else if(sign == 2) { // 待解约
			document.getElementById("btnSign").innerHTML = '<span class="c-f18" style="color: #E71F19;">您已申请了解约，流程审核中，请等待！！</span>';
			$('#btnSign').show();
			$('#btnSign').closest("div").addClass("h64");
			updateBodyHeight();
		} else {
			document.getElementById("btnSign").innerHTML = '<span class="c-f18" style="color: #E71F19;">医生申请与您解约，流程审核中，请等待！！</span>';
			$('#btnSign').show();
			$('#btnSign').closest("div").addClass("h64");
			updateBodyHeight();
		}
		d.close();
	} else {
		queryFailed(res);
	}
}

function updateBodyHeight() {
	var height = $(document.body).height(); 
	$("body>.c-main").height(height-70);
}

//申请签约
function startSign() {
	if(!userAgent) {
		var params = JSON.stringify({doctor: doctor});
		window.location.href = server + "wx/html/home/html/login.html?type=" + pagetype + "&openid=" + openid+"&loginParams="+params;
	}
	
	if(isQr != false) {
		sendPost('patient/family_contract/checkOpenid', {}, 'json', 'post', queryFailed, openidSuccess2);
	} else {
		var doctorName = encodeURI(document.getElementById("name").innerHTML);
		var hospitalName = encodeURI(document.getElementById("hospitalName").innerHTML);
		window.location.href = "../../qygl/html/agreement.html?doctor=" + doctor + "&doctorName=" + doctorName + "&hospitalName=" + hospitalName+'&patientCode='+patientCode+'&inviCode='+inviCode;
	}
}

function changeAgreement() {
	window.location.href = "../../qygl/html/agreement.html?readonly=1";
}

function cancelSign() {
	dialog({
		content: '您确定继续取消和' + document.getElementById("name").innerHTML + '医生的签约吗？',
		ok: function() {
			d1.showModal();
			var data = {};
			if(patientCode) {
				data.patientCode = patientCode;
			}
			data.doctor = doctor;
			sendPost('patient/family_contract/unsign', data, 'json', 'post', submitFailed, submitSuccess);
		},
		cancel: function() {
			return;
		}
	}).showModal();
}

function overSign() {
	$("#txtInfo").hide();
	dialog({
		title: '申请解约',
		skin: "ui-dialog ax-popup pror reqest-unsign-pop",
		content: $("#xf-artd").get(0),
		ok: function() {
			var data = {};
			data.doctor = doctor;
			data.doctorName = document.getElementById("name").innerHTML;
			data.reason = $("#textReason").val();
			if(data.reason == "" || data.reason == null) {
				$("#txtInfo").show();
				return false;
			} else {
				$("#txtInfo").hide();
				d1.showModal();
				sendPost('patient/family_contract/surrender', data, 'json', 'post', submitFailed, submitSuccess2);
				$("body,html").css("overflow-y","initial");
				return;
			}
		},
		cancel: function() {
			$("body,html").css("overflow-y","initial");
			return;
		}
	}).showModal();
}

function submitFailed(res) {
	d1.close();
	if(res && res.msg) {
		dialog({
			contentType: 'tipsbox',
			skin: 'bk-popup',
			content: res.msg
		}).show();
	} else {
		dialog({
			contentType: 'tipsbox',
			skin: 'bk-popup',
			content: '操作失败'
		}).show();
	}
}

function submitSuccess(res) {
	if(res.status == 200) {
		d1.close();
		//shenzaixin 修改20160827 ，使取消签约时，页面会显示申请签约按钮，而不是二维码。
		window.location.href = "doctor-homepage-new.html?openid="+openid+"&state="+doctor;
		//location.reload();
		// window.location.href = "choose-region.html";
	} else {
		submitFailed(res);
	}
}

function submitSuccess2(res) {
	if(res.status == 200) {
		document.getElementById("btnSign").innerHTML = '<span class="c-f18" style="color: #E71F19;">您已申请了解约，流程审核中，请等待！！</span>';
		$('#btnSign').closest("div").addClass("h64");
		updateBodyHeight();
		d1.close();
		dialog({
			contentType: 'tipsbox',
			skin: 'bk-popup',
			content: '解约申请成功'
		}).show();
		window.scrollTo(0, 0);
		location.reload();
		//window.location.href = "doctor-home-page.html?doctor=" + doctor;
	} else {
		submitFailed(res);
	}
}

//判断是否有三师信息
function checkTeacter() {
	var data = {};
	sendPost('patient/teachers', data, 'json', 'post', queryFailedTeacter, querySuccessTeacter);
}

function querySuccessTeacter(res) {
	if(res.status == 200) {
		d.close();
		var list = res.data.list;
		if(list.length > 0) {
			var doctorTemp = "";
			var doctorName = "";
			for(var i = 0; i < list.length; i++) {
				var data = list[i];
				if(Number(data.type) == 2) {
					doctorTemp = data.code;
					doctorName = data.name;
				}
			}
			var doctorNameTemp = document.getElementById("name").innerHTML;
			if(doctorNameTemp != doctorName) {
				dialog({
					content: '您已存在三师签约，签约医生为' + doctorName + '医生，继续与' + doctorName + '医生签约家庭医生服务吗？',
					ok: function() {
						window.location.href = "doctor-home-page.html?doctor=" + doctorTemp;
					}
				}).showModal();
			} else {
				familyDoctor();
			}
		} else {
			familyDoctor();
		}
	} else if(res.status == 1 || res.status == 2) {
		d.close();
		familyDoctor();
	} else {
		queryFailedTeacter(res);
	}
}

//成功
function openidSuccess2(res) {
	if(res.status == 200) {
		if(res.data == null || res.data == "") {
			checkTeacter();
		} else {
			queryDoctor(res);
		}
	} else {
		queryFailed(res);
	}
}

//获取家庭签约的医生信息
function queryDoctor(res) {
	var data = {};
	data.doctor = res.data;
	sendPost('patient/family_contract/homepage', data, 'json', 'post', queryFailed, querySuccess3);
}

function querySuccess3(res) {
	if(res.status == 200) {
		var doctorName = document.getElementById("name").innerHTML;
		if(res.data.name != doctorName) {
			dialog({
				content: '您已存在家庭签约，签约医生为' + res.data.name + '医生，只可以签约一个家庭医生',
				ok: function() {
					wx.closeWindow();
				}
			}).showModal();
		} else {
			familyDoctor();
		}
	} else {
		queryFailed(res);
	}
}

function familyDoctor(querystr) {
	var doctorName = encodeURI(document.getElementById("name").innerHTML);
	var hospitalName = encodeURI(document.getElementById("hospitalName").innerHTML);
	window.location.href = "../../qygl/html/agreement.html?doctor=" + doctor + "&doctorName=" + doctorName + "&hospitalName=" + hospitalName+(querystr||"")+'&patientCode='+patientCode+'&inviCode='+inviCode;
}


function queryFailedTeacter(res) {
	d.close();
	if(res && res.msg) {
		dialog({
			contentType: 'tipsbox',
			skin: 'bk-popup',
			content: res.msg
		}).show();
	} else {
		dialog({
			contentType: 'tipsbox',
			skin: 'bk-popup',
			content: '加载失败'
		}).show();
	}
}
// 忽略签约邀请
function ignoreSignInvitation() {
	sendPost('patient/family/updatePatientInviteLog', {patientInviteLogCode:inviCode,type:2}, 'json', 'post', function(){
		dialog({contentType:'tipsbox', skin:'bk-popup' , content:'请求失败'}).show();
	}, function(req) {
		if(req.status==200) {
			// 关闭页面,返回公众号
			wx.closeWindow();
		} else {
			dialog({contentType:'tipsbox', skin:'bk-popup' , content:'处理失败'}).show();
		}
	});
}
// 同意与其签约
function agreeSignInvitation() {
	sendPost('patient/family/updatePatientInviteLog', {patientInviteLogCode:inviCode,type:1}, 'json', 'post', function(){
		dialog({contentType:'tipsbox', skin:'bk-popup' , content:'请求失败'}).show();
	}, function(req) {
		if(req.status==200) {
			// 跳转到协议页面
			familyDoctor("&signInvitationCode="+inviCode+"&doctor="+doctor);
		} else {
			dialog({contentType:'tipsbox', skin:'bk-popup' , content:'处理失败'}).show();
		}
	});
}