var self;
var oldVal, type;//type 1: 修改标签
mui.plusReady(function(){
	self = plus.webview.currentWebview();
	oldVal = self.val;
	type = self.type;
	
	if(type==1){
		var vs = oldVal.split('、');
		for(var k in vs){
			$('.type-1 li[data-val="'+ vs[k] +'"]').addClass('l-over');
		}
		
		$('.type-1 li').on('tap', function(){
			$(this).toggleClass('l-over');
		})
	} else if(type == 2 || type==3){
		$('.type-'+ type + ' textarea').val(oldVal);
	}
	
	$('.type-'+ type).show();
})

/**
 * 检验是否作了修改
 */
function checkModify(){
	if(type==1){
		var v = $('.type-1 li.l-over');
		for(var k in v){
			if(oldVal.indexOf($(v[k]).attr('data-val')) == -1)
				return true;
		}
		
		v = oldVal.split('、');
		for(var k in v){
			if(!($('.type-1 li[data-val="'+ v[k] +'"]').hasClass('l-over')))
				return true;
		}
		
		return false;
	} else if(type==2 || type==3){
		if(oldVal == $('.type-'+ type + ' textarea').val())
			return false;
		return true;
	}
}

/**
 * 处理保存事件
 */
function submit(){
	//请处理保存事件
	
	
	closeWebView("modify1");
}

window.addEventListener("submit", function(e){
	if(!e.detail.t){
		submit();
		return;
	}
	
	if(checkModify()){
		mui.confirm("您已作了修改,需要保存吗？", "提示", ["不保存", "保存"], function(e) {
			if(e.index == 0) {
				closeWebView("modify1");
			} else {
				submit();
			}
		})
	}
	else{
		closeWebView("modify1");
	}
})