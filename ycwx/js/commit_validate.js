/*判断输入是否为正确的身份证*/
function isIdcard(v)
{
    if(!(/^\d{17}(\d|x)$/i.test(v) || /^\d{15}$/i.test(v))) return false;
    var provinceCode = parseInt(v.substr(0,2));
    if((provinceCode < 11) || (provinceCode > 91)) return false;
    var forTestDate = v.length == 18 ? v : v.substr(0,6)+"19"+v.substr(6,15);
    var birthday = forTestDate.substr(6,8);
    if(!isDate(birthday,'yyyyMMdd')) return false;
    if(v.length == 18) {
        v = v.replace(/x$/i,"a");
        var verifyCode = 0;
        for(var i = 17;i >= 0;i--)
            verifyCode += (Math.pow(2,i) % 11) * parseInt(v.charAt(17 - i),11);
        if(verifyCode % 11 != 1) return false;
    }
    return true;
}
    
function isDate(v,dateFormat){
	var MONTH = "MM";
    var DAY = "dd";
    var YEAR = "yyyy";
    var regex = '^'+ dateFormat.replace(YEAR,'\\d{4}').replace(MONTH,'\\d{2}').replace(DAY,'\\d{2}')+'$';
    if(!new RegExp(regex).test(v)) return false;

    var year = v.substr(dateFormat.indexOf(YEAR),4);
    var month = v.substr(dateFormat.indexOf(MONTH),2);
    var day = v.substr(dateFormat.indexOf(DAY),2);

    var d = new Date(format('%s/%s/%s',[year,month,day]));
    return ( parseInt(month, 10) == (1+d.getMonth()) ) &&
        (parseInt(day, 10) == d.getDate()) &&
        (parseInt(year, 10) == d.getFullYear() );
}

function format(str,args) {
    args = args || [];
    var result = str;
    for (var i = 0; i < args.length; i++){
        result = result.replace(/%s/, args[i]);
    }
    return result;
} 

/*判断输入是否为合法的手机号码*/
function isphone(inputString)
{
    var partten = /^0?(13[0-9]|15[0-9]|18[0-9]|14[57]|17[0-9])[0-9]{8}$/;
    if(partten.test(inputString))
    {
        return true;
    }
    else
    {
        return false;
    }
}

/*判断输入是否为合法的医社保卡号*/
function isSsc(inputString)
{
    var partten = /^[a-zA-Z0-9]+$/;
    if(partten.test(inputString))
    {
        return true;
    }
    else
    {
        return false;
    }
}

//处理表情
function utf16toEntities(str) { 
 	var patt=/[\ud800-\udbff][\udc00-\udfff]/g; // 检测utf16字符正则 
 	str = str.replace(patt, function(char){ 
 		var H, L, code; if (char.length===2) { 
 			H = char.charCodeAt(0); // 取出高位
 			L = char.charCodeAt(1); // 取出低位 
 			code = (H - 0xD800) * 0x400 + 0x10000 + L - 0xDC00; // 转换算法 
 			return "&#" + code + ";"; 
 		} else { return char; } }); 
    return str;
}