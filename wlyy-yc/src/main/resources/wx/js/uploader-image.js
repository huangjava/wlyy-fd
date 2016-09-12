var server="http://demo.dcloud.net.cn/helloh5/uploader/upload.php";
var files=[];
// 上传文件
function upload(){
	if(files.length<=0){
		plus.nativeUI.alert("没有添加上传文件！");
		return;
	}
	outSet("开始上传：")
	var wt=plus.nativeUI.showWaiting();
	var task=plus.uploader.createUpload(server,
		{method:"POST"},
		function(t,status){ //上传完成
			if(status==200){
				outLine("上传成功："+t.responseText);
				plus.storage.setItem("uploader",t.responseText);
				var w=plus.webview.create("uploader_ret.html","uploader_ret.html",{scrollIndicator:'none',scalable:false});
				w.addEventListener("loaded",function(){
					wt.close();
					w.show("slide-in-right",300);
				},false);
			}else{
				outLine("上传失败："+status);
				wt.close();
			}
		}
	);
	task.addData("client","HelloH5+");
	task.addData("uid",getUid());
	for(var i=0;i<files.length;i++){
		var f=files[i];
		task.addFile(f.path,{key:f.name});
	}
	task.start();
}
// 拍照添加文件
function appendByCamera(){
	plus.camera.getCamera().captureImage(function(p){
		appendFile(p);
	});	
}
// 从相册添加文件
function appendByGallery(){
	plus.gallery.pick(function(p){
        appendFile(p);
    });
}

// 从相册中选择多张图片 
function galleryImgs(){
	// 从相册中选择图片
	console.log("从相册中选择多张图片:");
    plus.gallery.pick( function(e){
    	for(var i in e.files){
	    	appendFile(e.files[i]);
    	}
    }, function ( e ) {
    	console.log( "取消选择图片" );
    },{filter:"image",multiple:true});
}

// 添加文件
var index=1;
function appendFile(p){
	var fe=document.getElementById("files");
	var li=document.createElement("li");
	var n=p.substr(p.lastIndexOf('/')+1);
	li.innerText=n;
	fe.appendChild(li);
	files.push({name:"uploadkey"+index,path:p});
	index++;
	empty.style.display="none";
	
	//预览
	var imgList = document.getElementById("img_list");
	var img = document.createElement("img");
	img.src = p;
	imgList.appendChild(img);
	img.width = 50;
	img.height = 50;
}
// 产生一个随机数
function getUid(){
	return Math.floor(Math.random()*100000000+10000000).toString();
}