document.addEventListener( "plusready",  function()
{
    var _BARCODE = 'rtcplugin',
		B = window.plus.bridge;
    var rtcplugin = 
    {
        init : function (eventCallback) 
        {
						var success = typeof eventCallback !== 'function' ? null : function(args) 
						{
							eventCallback(args);
						};
						var fail = function(code) 
						{
							alert(code);
						};
						var callbackID = B.callbackId(success, fail);
			
						return B.exec(_BARCODE, "init", [callbackID]);
        },
        
        login : function (ip, port, userid, password) 
        {                                	
            return B.execSync(_BARCODE, "login", [ip, port, userid, password]);
        },
        
        logout : function () 
        {                                	
            return B.execSync(_BARCODE, "logout");
        },
        
        inOnline : function () 
        {                                	
            return B.execSync(_BARCODE, "inOnline");
        },
        
        makeCall : function (peerUserId) 
        {                                	
            return B.execSync(_BARCODE, "makeCall", [peerUserId]);
        }
    };
    window.plus.rtcplugin = rtcplugin;
    
		init(onCallback);
		login('121.40.189.42', '8080', 'patient', '');
		
}, true );

function init(eventCallback)
{
		var ret = plus.rtcplugin.init(eventCallback);
		if (ret == "false") {
			alert("初始化音视频库失败！");
		}
}

function login(ip, port, userid, password)
{
    var ret = plus.rtcplugin.login(ip, port, userid, password);
		if (ret == "false") {
			//alert("调用登录接口失败！");
		}
}

function logout()
{
    var ret = plus.rtcplugin.logout();
		if (ret == "false") {
			alert("调用注销接口失败！");
		}
}

function inOnline()
{
    return plus.rtcplugin.inOnline();
}

function makeCall(peerUserId)
{
    var ret = plus.rtcplugin.makeCall(peerUserId);
		if (ret == "false") {
			alert("调用呼叫接口失败！");
		}
}

function onCallback(result) {
	var event = result[0];
	if (event == "onlogin") {
		//alert('登录成功');
	}
	else if (event == "onlogout") {
		//alert('进入候诊室失败，请检查网络！');
	}
};
