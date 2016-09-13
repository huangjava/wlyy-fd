//切换发布版本 true：正式版，连接生产环境；false：测试版，连接测试环境。
//var publish_version = true;
var publish_version = false;

// IM服务器
var im_login;
var im_logout;
var im_send_group;
var im_get_group_msg;
var im_send_one_by_one;
var im_get_one_by_one_msg;
var im_get_new_msg_count;

if (publish_version == true) {
	// IM服务器
	im_login="http://120.41.253.95:3000/user/login.im";
	im_logout="http://120.41.253.95:3000/user/logout.im";
	im_send_group="http://120.41.253.95:3000/group/sendmsg.im";
	im_get_group_msg="http://120.41.253.95:3000/group/getmsg.im";
	im_send_one_by_one = "http://120.41.253.95:3000/p2p/sendmsg.im";
	im_get_one_by_one_msg = "http://120.41.253.95:3000/p2p/getmsg.im"
	im_get_new_msg_count="http://120.41.253.95:3000/statistic/getgroupchatinfo.im"
} else {
	
	
	// IM服务器
	im_login="http://172.19.103.76:3000/user/login.im";
	im_logout="http://172.19.103.76:3000/user/logout.im";
	im_send_group="http://172.19.103.76:3000/group/sendmsg.im";
	im_get_group_msg="http://172.19.103.76:3000/group/getmsg.im";
	im_send_one_by_one="http://172.19.103.76:3000/p2p/sendmsg.im";
	im_get_one_by_one_msg = "http://172.19.103.76:3000/p2p/getmsg.im"
	im_get_new_msg_count="http://172.19.103.76:3000/statistic/getgroupchatinfo.im"

	//172.19.103.85是公司内部服务器的地址
	//27.154.56.142是公司内部服务器的地址的外网映射
//	im_login="http://27.154.56.142:3000/user/login.im";
//	im_logout="http://27.154.56.142:3000/user/logout.im";
//	im_send_group="http://27.154.56.142:3000/group/sendmsg.im";
//	im_get_group_msg="http://27.154.56.142:3000/group/getmsg.im";
//	im_send_one_by_one="http://27.154.56.142:3000/p2p/sendmsg.im";
//	im_get_one_by_one_msg = "http://27.154.56.142:3000/p2p/getmsg.im"
//	im_get_new_msg_count="http://27.154.56.142:3000/statistic/getgroupchatinfo.im"
}

var im={
	login:function(userId,token,client_id,platform){
		$.ajax({
			type:"get",
			url:im_login,
			data:{user_id:userId,token:token,client_id:client_id,platform:platform},
			async:true,
			dataType:"json",
			success:function(data){
				//data: {errno: 0, errmsg: 'login successful'}
				console.log(JSON.stringify(data));
			},
			error:function(msg){
				
			}
		});
	},
	logout:function(userId){
		$.ajax({
			type:"get",
			url:im_logout,
			data:{user_id:userId},
			async:true,
			dataType:"json",
			success:function(data){
				//data: {errno: 0, errmsg: 'login successful'}
				console.log(JSON.stringify(data));
			},
			error:function(msg){
				
			}
		});
	},
	sendGroup:function(userId,groupId,content,type,handler){
		
		$.ajax({
			type:"get",
			url:im_send_group,
			data:{from_uid:userId,to_gid:groupId,content:content,type:type},
			async:true,
			dataType:"json",
			success:function(data){
				//data: {errno: 0, errmsg: 'login successful'}
				console.log(JSON.stringify(data));
				handler(data);
			},
			error:function(msg){
				handler(msg);
			}
		});
	},
	getGroupMsg:function(uid,groupId,start,count,handler){
		
		$.ajax({
			type:"get",
			url:im_get_group_msg,
			data:{uid:uid,gid:groupId,start:start,count:count},
			async:true,
			dataType:"json",
			success:function(data){
				//data: {errno: 0, errmsg: 'login successful'}
				console.log(JSON.stringify(data));
				handler(data);
			},
			error:function(msg){
				handler(msg);
			}
		});
	},
	sendOneByOne:function(from_uid,to_uid,content,type){		
		$.ajax({
			type:"get",
			url:im_send_one_by_one,
			data:{from_uid:from_uid,to_uid:to_uid,content:content,type:type},
			async:true,
			dataType:"json",
			success:function(data){
				//data: {errno: 0, errmsg: 'login successful'}
				console.log(JSON.stringify(data));
				handler(data);
			},
			error:function(msg){
				handler(msg);
			}
		});
	},
	getOneByOneMsg:function(uid,peer_uid,start,count){	
		$.ajax({
			type:"get",
			url:im_get_one_by_one_msg,
			data:{gid:groupId,start:start,count:count},
			async:true,
			dataType:"json",
			success:function(data){
				//data: {errno: 0, errmsg: 'login successful'}
				console.log(JSON.stringify(data));
				handler(data);
			},
			error:function(msg){
				handler(msg);
			}
		});
	},
	getgroupchatinfo:function(uid,gid,handler){
		$.ajax({
			type:"get",
			url:im_get_new_msg_count,
			data:{uid:uid,gid:gid},
			async:true,
			dataType:"json",
			success:function(data){
				handler(data);
			},
			error:function(msg){
				handler(msg);
			}
		});
		
	},//获取群组消息统计
	getP2pMsg:function(uid,peer_uid,start,count, handler){	
		$.ajax({
			type:"get",
			url:im_get_one_by_one_msg,
			data:{uid: uid, peer_uid: peer_uid, start:start, count: count},
			async:true,
			dataType:"json",
			success:function(data){
				handler(data);
			},
			error:function(msg){
				handler(msg);
			}
		});
	},
	sendP2p:function(from_uid,to_uid,content,type,handler){		
		$.ajax({
			type:"get",
			url:im_send_one_by_one,
			data:{from_uid:from_uid,to_uid:to_uid,content:content,type:type},
			async:true,
			dataType:"json",
			success:function(data){
				if(handler) handler(data);
			},
			error:function(msg){
				if(handler) handler(msg);
			}
		});
	},
};