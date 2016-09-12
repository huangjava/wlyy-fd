//调用
function initial() {
	showWaitDialog();
	setInterval("closeWaitDialog()",3000);
}
//显示等待框
function showWaitDialog() {
	$(".overlay,.showbox").show();
//  var h = $(document).height();
//  $(".overlay").css({ "height": h });
//  $(".showbox").css({ 'display': 'block' });
//  $(".overlay").css({ 'display': 'block', 'opacity': '0.8' });
//  $(".showbox").css({ 'margin-top': '250px', 'opacity': '1' });
}
//关闭等待框
function closeWaitDialog() {
	$(".overlay,.showbox").hide();
//  $(".showbox").css({ 'margin-top': '250px', 'opacity': '0' });
//  $(".overlay").css({ 'display': 'none', 'opacity': '0' });
//  $(".showbox").css({ 'display': 'none' });
}