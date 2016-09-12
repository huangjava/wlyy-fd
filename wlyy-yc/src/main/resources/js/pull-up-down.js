$.fn.initScroll = function (opt) {
	var id = this.find('.scroll-wrapper').attr('id'),
	pullDown = opt && (opt.pullDown===false?false:true),
	pullUp = opt && (opt.pullUp===false?false:true);
	
	var scroller = new IScrollPullUpDown(id,{
			probeType:2,
			bounceTime: 250,
			bounceEasing: 'quadratic',
			mouseWheel:false,
			scrollbars:true,
			fadeScrollbars:true,
			interactiveScrollbars:false,
			click: true
	},pullDown?pullDownAction:null,pullUp?pullUpAction:null);

	//下拉刷新
	function pullDownAction(theScrollerTemp) {
		$(".pullUp").show();
		if(opt && opt.pullDownAction && $.isFunction(opt.pullDownAction)) {
			setTimeout(function () {
				opt.pullDownAction();
			}, 1000);	
		}
	}   
		
	//上拉加载数据
	function pullUpAction(theScrollerTemp) {
		if(opt && opt.pullUp===false) {
			return;
		}
		$(".pullUp").show();
		if(opt && opt.pullUpAction && $.isFunction(opt.pullUpAction)) {
			setTimeout(function () {
				opt.pullUpAction();
			}, 1000);	
		}
	}				
		
	document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
	return scroller;
}
	