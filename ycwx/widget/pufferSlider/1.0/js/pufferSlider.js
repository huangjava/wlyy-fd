+function ($) {
	$.fn.pufferSlider = function (options) {
		if(this.length == 0) return this;
		
		if(this.length > 1){
			this.each(function(){$(this).pufferSlider(options)});
			return this;
		}
		var $this=$(this),
			defaults={
				mainCell	: 'ul>li',	// 大图数据Dom节点
				autoPlay	: false,	// 自动播放
				interTime	: 3000,		// 自动运行间隔
				autoPage	: true,		// 自动分页
				effect		: false,	// 是否带循环轮播
				startBack	: null,		// 每次切换效果开始时执行函数
				endBack		: null,		// 每次切换效果结束时执行函数
				
			},
			opts 	= $.extend(defaults, options),
			$dom 	= $this.find(opts.mainCell),
			Length	= $dom.length,
			$wrap	= $pic = $em = $exit = $prev = $next = $play = null,
			picTimer	= null,
			photoTime	= false,
			picI	= 0,
			_init	= function(){
					if($wrap){
						$wrap.show();
					}else{
						$wrap=$('<div class="photo-swipe-wrap" />').appendTo('body');
						$('<div class="photo-swipe-number"><span>/'+Length+'</span></div>').appendTo($wrap);
						if(opts.autoPage){
							$em=$('<em>'+(picI+1)+'</em>').prependTo($wrap.find('.photo-swipe-number>span'));
						}
						$pic=$('<div class="photo-swipe-pic" />').appendTo($wrap);
						var photoHtml='';
						for (var i=0; i<Length; i++){
							photoHtml+='<div><p><img src="'+$dom.eq(i).data('img')+'" /></p></div>';
						}
						$pic.html(photoHtml);
						$('<div class="photo-swipe-btn" />').appendTo($wrap);
						$exit=$('<span class="exit"><i></i></span>').appendTo($wrap.find('.photo-swipe-btn')).bind('click',function(){
								$wrap.hide()
								if(picTimer){clearTimeout(picTimer);}
							});
						$play=$('<span class="play"><i></i></span>').appendTo($wrap.find('.photo-swipe-btn')).bind('click',function(){
								autoPlayFun();
							});
						$prev=$('<span class="prev"><i></i></span>').appendTo($wrap.find('.photo-swipe-btn')).bind('click',function(){
								if(picI-1>-1){
									picI--
									_photoSlider();
								}
							});
						$next=$('<span class="next"><i></i></span>').appendTo($wrap.find('.photo-swipe-btn')).bind('click',function(){
								if(picI+1<$pic.children('div').length){
									picI++
									_photoSlider();
								}
							});
						
						_photoResize();
						if(opts.autoPlay){
							autoPlayFun();	
						}
						function autoPlayFun(){
							if(photoTime){
								photoTime=false;
								$play.removeClass('on');
								clearTimeout(picTimer);
							}else{
								photoTime=true;
								$play.addClass('on');
								picTimer=setTimeout(_photoAutoSlider,opts.interTime);	
							}
						}
						$pic.on('touchstart touchmove touchend touchcancel webkitTransitionEnd', _eventPhotoHand);	
					}
					_photoSlider();
				},
				_eventPhotoHand=function(e){
					switch (e.type) {
						case 'touchmove':
							_phototouchMove(e);
							break;
						case 'touchstart':
							_phototouchStart(e);
							break;
						case 'touchcancel':
						case 'touchend':
							_phototouchEnd();
							break;
						case 'webkitTransitionEnd':
							_transitionEnd();
							break;
						case 'ortchange':
							_photoResize();
							break;
					}	
				},
				_phototouchStart=function(e) {
					op=({
						pageX:      e.originalEvent.touches[0].pageX,
						X    :      0
					});
					$pic.get(0).style.webkitTransitionDuration = '0ms';
				},
				_phototouchMove=function(e) {
					e.preventDefault();
      				e.stopPropagation();
					op.X=e.originalEvent.touches[0].pageX - op.pageX;
					$pic.get(0).style.webkitTransform = 'translate3d(' + (op.X-picI*photoW) + 'px,0,0)';				
				},
				_phototouchEnd=function() {
					var stepLength = op.X <= -100 ? Math.ceil(-op.X / photoW) : (op.X > 100) ? -Math.ceil(op.X / photoW) : 0;
					if(stepLength==1){
						if(picI<$pic.children('div').length-1){picI++}	
					}else if (stepLength==-1){
						if(picI>0){picI--}
					}
					_photoSlider();
				},
				_photoSlider=function(){
					$pic.get(0).style.cssText += '-webkit-transition:400ms;-webkit-transform:translate3d(-' + (photoW*picI) + 'px,0,0);';
					if(opts.autoPage){
						$em.text(picI+1);
					}
				},
				_photoAutoSlider=function(){
					if(picI>=Length-1){
						picI=0	
					}else{
						picI++	
					}
					_photoSlider();	
					picTimer=setTimeout(_photoAutoSlider,opts.interTime);	
				},
				_transitionEnd=function(){
					//alert(3)
				},
				_photoResize=function(){
					if($wrap){
						var w=$(window).width(), h=$(window).height(),minH=h-70;
						photoW=w;
						$pic.height(minH);
						$pic.children('div').each(function(index){
							var CSS = {
								'position'          : 'absolute',
								'height'            : minH,
								'width'             : w,
								'-webkit-transform'	: 'translate('+(index*w)+'px,0)',
								'-moz-transform'	: 'translate('+(index*w)+'px,0)',
								'-o-transform'		: 'translate('+(index*w)+'px,0)',
								'-ms-transform'		: 'translate('+(index*w)+'px,0)',
								'transform'			: 'translate('+(index*w)+'px,0)',
								'z-index'           : 900
							}
							$(this).css(CSS).find('p').width(w).height(minH)
						})
												
					}
				};
			
		$dom.on('click',function(){
			picI=$(this).index();
			_init();
		});
		
	}
	
}(jQuery);