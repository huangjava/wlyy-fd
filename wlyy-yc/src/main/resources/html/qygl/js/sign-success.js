$(function() {
	var num = 5;
	var intervalId=setInterval(function(){
		num--;
		$timerNum = $('#timer_num');
		if (num>0)
		$timerNum[0].innerHTML="("+num+")";
		wx.closeWindow();
	},1000);
})
