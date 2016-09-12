/*
 * tounthcyc.js
 * 2015-05-01
 * */
function load (){
	$(document.body).on('touchstart touchmove touchend','.inp', function(e){
		touch();
	});
//		$(".inp").each(function(i, element) {
//          $(".inp:eq("+i+")")[0].addEventListener('touchstart',touch, false);
//			$(".inp:eq("+i+")")[0].addEventListener('touchmove',touch, false);
//			$(".inp:eq("+i+")")[0].addEventListener('touchend',touch, false);
//      });
		var HandStartX,HandStartY,HandEndX,HandEndY,CountX,CountY;
		function touch (event){
			//debugger
			var event = event || window.event;
			var oInp = document.getElementById("inp");
			
			switch(event.type){
				case "touchstart"://开始
					HandStartX=event.touches[0].clientX;
					HandStartY=event.touches[0].clientY;
					break;
				case "touchend":
					HandEndX=event.changedTouches[0].clientX;
					HandEndY=event.changedTouches[0].clientY;
					CountX=HandEndX-HandStartX;
					CountY=HandEndY-HandStartY;
					$.CycTounch(event,CountX,CountY)
					break;
				case "touchmove":
					event.preventDefault();
					break;
			}
		}
}