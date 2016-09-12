saveAgentPage("../../qygl/html/signing-share.html");
var Request = GetRequest();
var userAgent = window.localStorage.getItem(agentName);
if(userAgent) {
	userAgent = JSON.parse(userAgent);
} 
// 判断是否点击“我要签约”按钮跳转到“签约管理首页”
var enableSign = Request["enableSign"],
guide = Request["guide"],
from = Request["from"];//用from判断是否直接点击分享链接
if(from) {
	$('#overlay_pop').hide();
	$('#introduce_text').hide();
	$('#introduce_guide').hide();
} else {
	$('#overlay_pop').show();
	$('#introduce_text').show();
	$('#introduce_guide').show();
}
$(function() {
	$('#require_sign').on('click',function() {
		 window.scrollTo(0,document.body.scrollHeight)
	});
	$('#overlay_pop').on('touchstart',function() {
		$(this).hide();
		$('#introduce_text').hide();
		$('#introduce_guide').hide();
	});
	if(enableSign) {
		$('#require_sign').click(function() {
			//location.href = "signing-doctors.html";
		});
	}
	
	window.onscroll=function(){
		var  offsetHeight = document.body.offsetHeight,
		scrollTop = $(document.body).scrollTop(),
		height = $(window).height();
		
		if(scrollTop + height == offsetHeight) {
			$('#require_sign').hide();
		} else {
			$('#require_sign').show();
		}
	};
})
