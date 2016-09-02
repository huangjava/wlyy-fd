function back2Xiaoxi(){
	baseBack("xiaoxi.html");
}
function backxiaoxi(){
	//20160809 cyc todo 解决返回消息列表页不刷新
			var old_back = mui.back;
			mui.back = function() {
				var wobj = plus.webview.getWebviewById("xiaoxi.html");//获取全科、健康医生页面的讨论组  ID
					//html/home/html/xiaoxi.html 
				if(wobj!=null){
					wobj.reload(true);//刷新
				}
				old_back();
			}
			//cyc
}
function back2home(){
	baseBack("home2.html");
}

function back2OneByOneGongzuozu(){
	baseBack("dlz");
}

function baseBack(htmlId){
	var oldback = mui.back;
	mui.back = function() {
		var ww = plus.webview.getWebviewById(htmlId);
		if(ww){
			mui.fire(plus.webview.getWebviewById(htmlId), "refresh");			
		}
		oldback();
	}
}

function isMultiRole(){
	var l = JSON.parse(plus.storage.getItem("userRole"));
	return plus.storage.getItem('docType')!='10' && l && l.length>0;
}

function initQiehuanDom(){
	$('#qiehuan').toggle(isMultiRole());
	$('#qiehuan').on('tap', function(){
		mui.fire(plus.webview.getWebviewById("main"), "showQiehuan");
	})
}
