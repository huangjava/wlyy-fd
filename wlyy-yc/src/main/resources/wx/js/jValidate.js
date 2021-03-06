(function($){
    /**
     * Define global and singleton object jValidation
     */
    var jValidation = $.jValidation || {};
    var Tips = $.Tips || {};

    /**
     * Define class ajax.Result for ajax validation
     * @returns {jValidation.ajax.Result}
     */
    jValidation.ajax = {
        Result:function(){}
    };

    jValidation.ajax.Result.prototype = {
        result : undefined,

        errorMsg:undefined,

        errorCode:undefined,

        setErrorMsg:function(errorMsg){
            this.errorMsg = errorMsg;
        },

        setErrorCode:function(errorCode){
            this.errorCode = errorCode;
        },

        setResult:function(result){
            this.result = result;
        }
    };

    jValidation.ValidationDefaultOptions = function(){};
    jValidation.ValidationDefaultOptions.prototype = {
        //是否监听form的submit事件
        onSubmit : true,
        //是否监听form的reset事件
        onReset : true,
        //表单验证时停留在第一个验证的地方,不继续验证下去
        stopOnFirst : false,
        //是否实时检查数据的合法性
        immediate : false,
        //是否出错时将光标指针移到出错的输入框上
        focusOnError : false,
        //是否使用input的title属性作为出错时的提示信息
        useTitles : false,
        //Form验证时的回调函数,可以修改最终的返回结果
        onFormValidate : function(result, form) {return result;},
        //某个input验证时的回调函数
        onElementValidate : function(result, elm) {},
        //用于ajax验证回调
        onElementValidateForAjax:function(elm){}
    };

    //是否开启tips错误提示
    jValidation.Tips = {
        useTips:true,
        showArrow:true,
        position:'right'
    };

    jValidation.ValidatorDefaultOptions = function(){};

    jValidation.ValidatorDefaultOptions.prototype = {
        ignoreEmptyValue : true, //是否忽略空值
        depends : [] //相关依赖项
    };

    /**
     * Define jValidation util obj
     */
    jValidation.util = {
        rclass : /[\n\t]/g,
        hasClass : function(dom, className)
        {
            //Codes below come form jQuery-1.4.4 jQuery.fn.hasClass().
            var className = " " + className + " ";
            return dom.className && ((" " + dom.className + " ").replace(this.rclass, " ").indexOf( className ) > -1);
        },


        stop: function(event) {
            if (event.preventDefault) {
                event.preventDefault();
                event.stopPropagation();
            } else {
                event.returnValue = false;
                event.cancelBubble = true;
            }
        },

        getElementLeft: function (element){
            var actualLeft = element.offsetLeft;
            var current = element.offsetParent;
            while (current !== null){
                actualLeft += current.offsetLeft;
                current = current.offsetParent;
            }
            return actualLeft;
        },

        getElementTop: function (element){
            var actualTop = element.offsetTop;
            var current = element.offsetParent;
            while (current !== null){
                actualTop += current.offsetTop;
                current = current.offsetParent;
            }
            return actualTop;
        },


        toArrayByArgus:function(arguments){
            // return a array
            return Array.prototype.slice.call(arguments);
        },
        visible: function(element) {
            return $(element).css("display") != 'none';
        },

        isVisible : function(elm) {
            while(elm && elm.tagName != 'BODY') {
                if(!this.visible(elm)) return false;
                elm = elm.parentNode;
            }
            return true;
        },

        getInputValue : function(elm) {
            var jElm = $(elm);
            return jElm.val();
        },

        // 通过classname传递的参数必须通过'-'分隔各个参数
        // 返回值包含一个参数singleArgument,例:validate-pattern-/[a-c]/gi,singleArgument值为/[a-c]/gi
        getArgumentsByClassName : function(prefix,className) {
            if(!className || !prefix)
                return [];
            var pattern = new RegExp(prefix+'-(\\S+)');
            var matchs = className.match(pattern);
            if(!matchs)
                return [];
            var results = [];
            results.singleArgument = matchs[1];
            var args =  matchs[1].split('-');
            for(var i = 0; i < args.length; i++) {
                if(args[i] == '') {
                    if(i+1 < args.length) args[i+1] = '-'+args[i+1];
                }else{
                    results.push(args[i]);
                }
            }
            return results;
        },

        getFormElements: function(jForm){
            return jForm.find(":text,:password,:radio.validate-one-required,:checkbox[class*=checkbox-required],select,textarea,.j-radio-wrapper,.j-checkbox-wrapper,[data-type=comboSelect]");
        },
        format : function(str,args) {
            args = args || [];
            jValidation.util.assert(args.constructor == Array,"jValidation.util.format() arguement 'args' must is Array");
            var result = str;
            for (var i = 0; i < args.length; i++){
                result = result.replace(/%s/, args[i]);
            }
            return result;
        },
        assert : function(condition,message) {
            var errorMessage = message || ("assert failed error,condition="+condition);
            if (!condition) {
                alert(errorMessage);
                throw new Error(errorMessage);
            }else {
                return condition;
            }
        },

        isDate : function(v,dateFormat,elm,isdateinput) {
            //TODO CYC 201604158 isdateinput是否日期控件验证
            var MONTH = "MM";
            var DAY = "dd";
            var YEAR = "yyyy";
            var regex = '^'+dateFormat.replace(YEAR,'\\d{4}').replace(MONTH,'\\d{2}').replace(DAY,'\\d{2}')+'$';
            //TODO CYC 201604158
            if(isdateinput=="1"){
                var dateF2="yyyyMMdd";
                var regex1= '^'+dateF2.replace(YEAR,'\\d{4}').replace(MONTH,'\\d{2}').replace(DAY,'\\d{2}')+'$';
                if(new RegExp(regex1).test(v)){
                    v= v.substr(0,4)+"-"+v.substr(4,2)+"-"+v.substr(6,2)
                    $(elm).val(v)
                }
            }
            if(!new RegExp(regex).test(v)) return false;

            var year = v.substr(dateFormat.indexOf(YEAR),4);
            var month = v.substr(dateFormat.indexOf(MONTH),2);
            var day = v.substr(dateFormat.indexOf(DAY),2);

            var d = new Date(jValidation.util.format('%s/%s/%s',[year,month,day]));
            return ( parseInt(month, 10) == (1+d.getMonth()) ) &&
                (parseInt(day, 10) == d.getDate()) &&
                (parseInt(year, 10) == d.getFullYear() );
        },

        getElmID : function(elm) {
            var reg = /[\[\]\.\{\}]/g;
            // TODO [代码片段001][修正被校验控件没有id和name时，出现的抛错问题][yezehua]
            if(!$(elm).attr("id") && !$(elm).attr("name")) return "";
            // TODO [代码片段001][修正被校验控件没有id和name时，出现的抛错问题][yezehua] 结束
            return $(elm).attr("id") ? $(elm).attr("id") : $(elm).attr("name").replace(reg,"");
        },
        getLanguage : function() {
            var lang = null;
            if (typeof navigator.userLanguage == 'undefined')
                lang = navigator.language.toLowerCase();
            else
                lang = navigator.userLanguage.toLowerCase();
            return lang;
        },
        getMessageSource : function() {
            var lang = jValidation.util.getLanguage();
            var messageSource = jValidation.Validator.messageSource['zh-cn'];
            if(jValidation.Validator.messageSource[lang]) {
                messageSource = jValidation.Validator.messageSource[lang];
            }

            var results = {};
            for(var i = 0; i < messageSource.length; i++) {
                results[messageSource[i][0]] = messageSource[i][1];
            }
            return results;
        },
        getI18nMsg : function(key) {
            return jValidation.util.getMessageSource()[key];
        }
    };

    /**
     * Define class Validator
     */

    jValidation.Validator = function(className,test,options){
        this.init(className,test,options);
    };

    //define Validator messageSource
    jValidation.Validator.messageSource = {};

    jValidation.Validator.messageSource['zh-cn'] = [
        ['validation-failed' , '验证失败.'],
        ['required' , '该项目为必添项，不允许为空！'],
        ['validate-number' , '请输入有效的数字.'],
        ['validate-digits' , '请输入数字.'],
        ['validate-positive-integer' , '请输入正整数'],
        ['validate-alpha' , '请输入英文字母.'],
        ['validate-alphanum' , '请输入英文字母或是数字,其它字符是不允许的.'],
        ['validate-email' , '请输入有效的邮件地址,如 username@example.com.'],
        ['validate-url' , '请输入有效的URL地址.'],
        ['validate-currency-dollar' , 'Please enter a valid $ amount. For example $100.00 .'],
        ['validate-one-required' , '在前面选项至少选择一个.'],
        ['validate-integer' , '请输入正确的整数'],
        ['validate-pattern' , '输入的值不匹配'],
        ['validate-ip','请输入正确的IP地址'],
        ['min-value' , '最小值为%s'],
        ['max-value' , '最大值为%s'],
        ['min-length' , '最小长度为%s,当前长度为%s.'],
        ['max-length', '最大长度为%s,当前长度为%s.'],
        ['int-range' , '输入值应该为 %s 至 %s 的整数'],
        ['float-range' , '输入值应该为 %s 至 %s 的数字'],
        ['length-range' , '输入值的长度应该在 %s 至 %s 之间,当前长度为%s'],
        ['equals','两次输入不一致,请重新输入！'],
        ['less-than','请输入小于前面的值'],
        ['less-than-equal','请输入小于或等于前面的值'],
        ['great-than','请输入大于前面的值'],
        ['great-than-equal','请输入大于或等于前面的值'],
        //['validate-date' , '请输入有效的日期,格式为 %s. 例如:%s.'],
        /*TODO 20160415 cyc 格式省略*/
        ['validate-date' , '请输入有效的日期,例如:2006-03-12'],
        ['validate-selection' , '请选择.'],
        ['validate-file' , function(v,elm,args,metadata) {
            return jValidation.util.format("文件类型应该为[%s]其中之一",[args.join(',')]);
        }],
        ['validate-id-number','请输入合法的身份证号码'],
        ['validate-chinese','请输入中文'],
        ['validate-phone','请输入正确的电话号码,如:010-29392929,当前长度为%s.'],
        ['validate-mobile-phone','请输入正确的手机号码,当前长度为%s.'],
        ['validate-zip','请输入有效的邮政编码'],
        ['validate-qq','请输入有效的QQ号码.'],
        ['validate-devid','设备输入ID有误'],
        ['validate-space','不能输入空格！'],
        ['checkbox-required','至少勾选1个'],
        ['validate-money','请输入正确的数值,最高保留2位小数'],
        ['validate-contact-way','请输入有效的电话号码或手机号码'],
        ['validate-org-length','请选择到医院一级！'],
        ['validate-org-code','请输入正确的组织代码格式，如:46650460-X'],
        ['validate-special-char','不允许输入 \' 符号']

    ];

    jValidation.Validator.prototype = {
        //init method
        init: function(className,test,options){
            this.options = $.extend(new jValidation.ValidatorDefaultOptions(), options || {});
            this._test = test ? test : function(v,elm){ return true ;};
            this._error = jValidation.util.getI18nMsg(className) ? jValidation.util.getI18nMsg(className) : jValidation.util.getI18nMsg('validation-failed');
            this.className = className;
        },

        _dependsTest : function(v,elm) {
            if(this.options.depends && this.options.depends.length > 0) {
                var dependsResult = $A(this.options.depends).all(function(depend){
                    return jValidation.Validation.get(depend).test(v,elm);
                });
                return dependsResult;
            }
            return true;
        },


        testAndGetError : function(v, elm,useTitle) {
            var dependsError = this.testAndGetDependsError(v,elm);
            if(dependsError) return dependsError;

            if(!elm) elm = {};
            var isEmpty = (this.options.ignoreEmptyValue && ((v == null) || (v.length == 0)));
            var result = isEmpty || this._test(v,elm,jValidation.util.getArgumentsByClassName(this.className,elm.className),this);
            if(!result) return this.error(v,elm,useTitle);
            return null;
        },
        testAndGetDependsError : function(v,elm) {
            var depends = this.options.depends;
            if(depends && depends.length > 0) {
                for(var i = 0; i < depends.length; i++) {
                    var dependsError = jValidation.Validation.get(depends[i]).testAndGetError(v,elm);
                    if(dependsError) return dependsError;
                }
            }
            return null;
        },

        /*
         * 返回验证错误提示信息
         */
        error : function(v,elm,useTitle) {
            var args  = jValidation.util.getArgumentsByClassName(this.className,elm.className);
            var error = this._error;
            if(typeof error == 'string') {
                if(v) args.push(v.length);
                error = jValidation.util.format(this._error,args);
            }else if(typeof error == 'function') {
                error = error(v,elm,args,this);
            }else {
                alert('property "_error" must type of string or function,current type:'+typeof error+" current className:"+this.className);
            }
            //判断是否使用了useTitle
            useTitle = jValidation.util.hasClass(elm,'useTitle');
            if(useTitle){
                if(elm){
                    var titleName = this.className+"-title";
                    if(elm.getAttribute(titleName)){
                        var titleErrorMsg = elm.getAttribute(titleName);
                        return titleErrorMsg;
                    }
                }
            }
            return error;
        }
    };


    /**
     * Define Class and its constructor: Validation
     */
    jValidation.Validation = function(jForm,options){
        this.init(jForm,options);
        return this;
    };

    jValidation.Validation.prototype = {
        init: function(jForm,options){
            this.options = $.extend(true,new jValidation.ValidationDefaultOptions(),options);
            this.jForm = jForm;
            if(this.options.immediate) {
                var useTitles = this.options.useTitles;
                var callback = this.options.onElementValidate;
                var ajaxCallback = this.options.onElementValidateForAjax;
                var elements =jValidation.util.getFormElements(jForm);
                elements.each(function(){
                    $(this).bind('blur', function(event){

                        jValidation.Validation.validateElement(event.target || event.srcElement, {
                            useTitle: useTitles,
                            onElementValidate: callback,
                            onElementValidateForAjax:ajaxCallback
                        });
                    });

                    if($(this).prop("tagName").toLowerCase() == 'select'){
                        $(this).bind('change', function(event){
                            jValidation.Validation.reset(event.target || event.srcElement);
                        });
                    }else if(this.type && (this.type.toLowerCase() == 'checkbox' || this.type.toLowerCase() == 'radio')){
                        var that = this;
                        $(this).parent().find(":input").each(function(){
                            $(this).click(function(){
                                jValidation.Validation.resetCheckbox(that);
                            });
                        });
                    }
                    else{
                        $(this).bind('keydown', function(event){
                            jValidation.Validation.reset(event.target || event.srcElement);
                        });
                    }
                });
            }
        },

        onSubmit :  function(ev){
            if(!this.validate()) jValidation.util.stop(ev);
        },

        validate : function() {
            //TODO
            var result = true;
            var useTitles = this.options.useTitles;
            var callback = this.options.onElementValidate;
            var ajaxCallback = this.options.onElementValidateForAjax;
            //解决弹出div验证问题，弹出关闭后jForm对象可能改变，采用id重新获取
            if(this.jForm.attr("id")){
                var id =this.jForm.attr("id");
                this.jForm = $("#"+id);
            }
            var jElements =jValidation.util.getFormElements(this.jForm);
            //stop validator on fires err element
            if(this.options.stopOnFirst) {
                for(var i = 0; i < jElements.size(); i++) {
                    var elm = jElements[i];
                    result = jValidation.Validation.validateElement(elm,{useTitle : useTitles, onElementValidate : callback,onElementValidateForAjax:ajaxCallback});
                    if(!result) break;
                }
            } else {
                for(var i = 0; i < jElements.size(); i++) {
                    var elm = jElements[i];
                    if(!jValidation.Validation.validateElement(elm,{useTitle : useTitles, onElementValidate : callback,onElementValidateForAjax:ajaxCallback})) {
                        result = false;
                    }
                }
            }
            // focusOnError
            if(!result && this.options.focusOnError) {
                var first = null;
                jElements.each(function(){
                    if(jValidation.util.hasClass(this,"validation-failed")){
                        first = this;
                        return false;
                    }
                });
                if(first.select) first.select();
                first.focus();
            }
            return this.options.onFormValidate(result, this.jForm);
        },

        reset : function() {
            var jElements =jValidation.util.getFormElements(this.jForm);
            jElements.each(function(){
                jValidation.Validation.reset(this);
            });
        }
    };

    /**
     * Define Class Validation static method
     */
    $.extend(true,jValidation.Validation,{
        validateElement : function(elm, options)
        {
            options = $.extend({useTitle : false,
                onElementValidate : function(result, elm) {}},options || {});

            if(elm.className){
                var cn = elm.className.split(/\s+/);
                //TODO
                for(var i = 0; i < cn.length; i++) {
                    var cssName = cn[i];
                    //for ajax validation
                    if(cssName == 'ajax'){
                        var result = options.onElementValidateForAjax(elm);
                        if(result instanceof jValidation.ajax.Result){
                            if(!result.result){
                                var errorMsg = result.errorMsg;
                                jValidation.Validation.showErrorMsg(cssName,elm,errorMsg);
                                return false;
                            }
                        }
                        // for general validation
                    }else{
                        var test = this.test(cssName,elm,options.useTitle);
                        options.onElementValidate(test, cssName);
                        if(test) {
                            jValidation.Validation.reset(elm);
                        } else {
                            return false;
                        }
                    }
                }
            }
            //is show successed info
            //去除绿色钩钩
            //jValidation.Validation.showSuccessMsg("successed",elm);
            return true;
        },
        test : function(cssName, elm, useTitle) {
            var Validation = jValidation.Validation;
            var v = this.get(cssName);
            var errorMsg = null;
            if($(elm).hasClass('force-validate') || jValidation.util.isVisible(elm))
                errorMsg = v.testAndGetError(jValidation.util.getInputValue(elm),elm,useTitle);
            if(errorMsg) {
                Validation.showErrorMsg(cssName,elm,errorMsg);
                return false;
            } else {
                Validation.hideErrorMsg(cssName,elm);
                return true;
            }
        },

        showErrorMsg : function(name,elm,errorMsg) {
            this.reset(elm);
            //change to jquery object
            var jElm = $(elm);
            if(jValidation.Tips.useTips){
                Tips.tip($(elm),{content:errorMsg,position:jValidation.Tips.position,showArrow:jValidation.Tips.showArrow});
                if(elm.type && (elm.type.toLowerCase() == 'radio' || elm.type.toLowerCase() == 'checkbox')){
                    jElm.parent().hasClass("validation-failed-span")?"":jElm.wrap('<span class="validation-failed-span"></span>');

                }
            }else{
                var prop = jValidation.Validation._getAdviceProp(name);
                var advice = jValidation.Validation.getAdvice(name, elm);
                if(!jElm.attr(prop)) {
                    //Judgment is assign advice position, if not then create a advice
                    if(advice.size()==0) {
                        advice = jValidation.Validation.newErrorMsgAdvice(name,elm,errorMsg);
                        //chage to jquery object
                        advice = $(advice);
                    }
                }


                if(advice && !jValidation.util.isVisible(advice[0])) {
                    advice.css("display",'');
                }
                advice.html(errorMsg);
                jElm.attr(prop,true);
            }
            var $wrap = jElm.closest('.j-text-wrapper');
            if($wrap.length) {
                $wrap.removeClass('validation-passed');
                $wrap.addClass('validation-failed');
            } else {
                jElm.removeClass('validation-passed');
                jElm.addClass('validation-failed');
            }
        },

        showSuccessMsg : function(name,elm) {
            this.reset(elm);
            var jElm = $(elm);
            //var prop = jValidation.Validation._getAdviceProp(name);
            var advice = jValidation.Validation.getAdvice(name, elm);
            //if(!jElm.attr(prop)) {
            //判断是否已经自动指定advice位置了,如果没有则创建
            if(advice.size() == 0) {
                advice = jValidation.Validation.newSuccessMsgAdvice(name,elm);
                advice = $(advice);
                //自定义了提示信息位置的情况
            }else{
                advice.removeClass("validation-advice");
                advice.addClass("validation-success-advice");
                advice.html("");
                advice.show();
            }
            //}
            //jElm.attr(prop,true);
            //jElm.addClass('validation-passed');
            //jElm.removeClass('validation-failed');
            var $wrap = jElm.closest('.j-text-wrapper');
            if($wrap.length) {
                $wrap.addClass('validation-passed');
                $wrap.removeClass('validation-failed');
            } else {
                jElm.addClass('validation-passed');
                jElm.removeClass('validation-failed');
            }
        },

        newErrorMsgAdvice : function(name,elm,errorMsg) {
            if(name.indexOf(".")!=-1){
                name = name.split(".")[1];
            }
            var advice = '<div class="validation-advice" id="advice-' + name + '-' + jValidation.util.getElmID(elm) +'" style="display:none">' + errorMsg + '</div>';
            switch (elm.type && elm.type.toLowerCase()) {
                case 'checkbox':
                case 'radio':
                    $(elm).after(advice);
                    break;
                default:
                    $(elm).after(advice);
            }
            advice = $('#advice-' + name + '-' + jValidation.util.getElmID(elm));
            return advice;
        },

        newSuccessMsgAdvice : function(name,elm) {
            var advice = '<span class="validation-success-advice glyphicon glyphicon-ok crm-success-color"  id="advice-' + name + '-' + jValidation.util.getElmID(elm) +'"></span>';
            switch (elm.type && elm.type.toLowerCase()) {
                case 'checkbox':
                case 'radio':
                    var p = elm.parentNode;
                    if(p) {
                        $(elm).before(advice);
                    } else {
                        $(elm).after(advice);
                    }
                    break;
                case 'input':
                    $(elm).after(advice);
                default:
                    $(elm).after(advice);
            }
            advice = $('#advice-' + name + '-' + jValidation.util.getElmID(elm));
            return advice;
        },

        hideErrorMsg : function(name,elm) {
            var prop = jValidation.Validation._getAdviceProp(name);
            var advice = jValidation.Validation.getAdvice(name, elm);
            var jElm = $(elm);
            if(advice && jElm.attr(prop)) {
                advice.hide();
            }
            jElm.attr(prop,false);
            //jElm.removeClass('validation-failed');
            //jElm.addClass('validation-passed');
            var $wrap = jElm.closest('.j-text-wrapper');
            if($wrap.length) {
                $wrap.addClass('validation-passed');
                $wrap.removeClass('validation-failed');
            } else {
                jElm.addClass('validation-passed');
                jElm.removeClass('validation-failed');
            }
        },
        reset : function(elm) {
            var jElm = $(elm);
            if(jValidation.Tips.useTips){
                if($(elm).data("jtip")){
                    $(elm).unbind("mouseover");
                    $(elm).data("jtip").tipWrap.remove();
                    $(elm).parent().find(".validation-success-advice").remove();
                }
            }else{
                var className = jElm.attr("class");
                var cn = className.split(/\s+/);
                for(var i = 0; i < cn.length; i++) {
                    var value = cn[i];
                    var prop = jValidation.Validation._getAdviceProp(value);
                    if(jElm.attr(prop)) {
                        var advice = jValidation.Validation.getAdvice(value, elm);
                        advice.hide();
                        jElm.attr(prop,'');
                    }
                }
            }

            var $wrap = jElm.closest('.j-text-wrapper');
            if($wrap.length) {
                $wrap.removeClass('validation-passed');
                $wrap.removeClass('validation-failed');
            } else {
                jElm.removeClass('validation-passed');
                jElm.removeClass('validation-failed');
            }
        },

        resetCheckbox:function(elm){
            this.reset(elm);
            $("#check-advice-" + jValidation.util.getElmID(elm)).remove();
            if(elm.type && (elm.type.toLowerCase() == 'radio' || elm.type.toLowerCase() == 'checkbox')){
                if($(elm).parent().hasClass("validation-failed-span")){
                    $(elm).unwrap();
                }

            }

        },

        _getAdviceProp : function(validatorName) {
            return '__advice'+validatorName;
        },

        getAdvice : function(name, elm) {
            //judge is exist a custom advice
            var advice = $('#advice-'+name+'-'+jValidation.util.getElmID(elm));
            if(advice.size()>0){
                return advice;
            }else{
                return $('#advice-'+jValidation.util.getElmID(elm));
            }
        },

        get : function(cssName) {
            var Validation = jValidation.Validation;
            var resultMethodName = null;
            for(var methodName in Validation.methods) {
                if(cssName == methodName) {
                    resultMethodName = methodName;
                    break;
                }
                if(cssName.indexOf(methodName) >= 0) {
                    resultMethodName = methodName;
                }
            }
            return Validation.methods[resultMethodName] ? Validation.methods[resultMethodName] : new jValidation.Validator();
        },
        add : function(className, test, options) {
            var nv = {};
            var testFun = test;
            if(test instanceof RegExp){
                testFun = function(v,elm,args,metadata){ return test.test(v);};
            }
            nv[className] = new jValidation.Validator(className, testFun, options);
            $.extend(true,jValidation.Validation.methods,nv);
        },
        addAllThese : function(validators) {
            for(var i = 0; i < validators.length; i++) {
                var value = validators[i];
                jValidation.Validation.add(value[0], value[1], (value.length > 2 ? value[2] : {}));
            }
        },
        methods : {},

        validations : {}


    });

    $.extend({ jValidation: jValidation });
})(jQuery);


/**
 * init Validation validation methods
 */
(function(Validation, $){
    /**
     * Define global and singleton object jValidation
     */
    var jValidation = $.jValidation || {};
    var Tips = $.Tips || {};
    var Util = $.Util;

    Validation.addAllThese([
        ['required', function(v, elm) {
            var type = $(elm).attr("data-type");
            if(type && Util.isStrEqualsIgnorecase(type, "comboSelect")) {
                if(Util.isString(v)) {
                    var data = JSON.parse(v);
                    if(!data.keys || !data.keys.length || !data.keys.join()) {
                        return false;
                    } else {
                        return true;
                    }
                }
            }
            return !((v == null) || (v.length == 0) || /^[\s|\u3000]+$/.test(v));
        },{ignoreEmptyValue:false}],

        ['invalidchar', function(v) {
            return (/^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(v));
        }],
        ['validate-number', function(v) {
            return (!isNaN(v) && !/^\s+$/.test(v));
        }],
        ['validate-digits', function(v) {
            return !/[^\d]/.test(v);
        }],
        ['validate-alphanum', function(v) {
            return !/\W/.test(v);
        }],
        ['validate-one-required', function (v,elm) {
            var p = elm.parentNode;
            var size = $(elm).parent().find("input[type='radio']:checked").size();
            return size !=0;
        },{ignoreEmptyValue : false}],

        ['validate-digits',/^[\d]+$/],
        ['validate-positive-integer',/^[1-9]([0-9])*$/],
        ['validate-alphanum',/^[a-zA-Z0-9]+$/],
        ['validate-alpha',/^[a-zA-Z]+$/],
        ['validate-email',/\w{1,}[@][\w\-]{1,}([.]([\w\-]{1,})){1,3}$/],
        ['validate-url',/^(http|https|ftp):\/\/(([A-Z0-9][A-Z0-9_-]*)(\.[A-Z0-9][A-Z0-9_-]*)+)(:(\d+))?\/?/i],
        ['validate-currency-dollar',/^\$?\-?([1-9]{1}[0-9]{0,2}(\,[0-9]{3})*(\.[0-9]{0,2})?|[1-9]{1}\d*(\.[0-9]{0,2})?|0(\.[0-9]{0,2})?|(\.[0-9]{1,2})?)$/]
    ]);

    //custom validate start
    Validation.addAllThese([
    /**
     * Usage : equals-$otherInputId
     * Example : equals-username or equals-email etc..
     */
        ['equals', function(v,elm,args,metadata) {
            return $("#"+args[0]).val() == v;
        },{ignoreEmptyValue:false}],
        ['equals-ignore-case', function(v,elm,args,metadata) {
            return Util.isStrEqualsIgnorecase( $("#"+args[0]).val(), v);
        },{ignoreEmptyValue:false}],
    /**
     * Usage : less-than-$otherInputId
     */
        ['less-than', function(v,elm,args,metadata) {
            if(Validation.get('validate-number').test(v) && Validation.get('validate-number').test($("#"+args[0])))
                return parseFloat(v) < parseFloat($("#"+args[0]));
            return v < $("#"+args[0]);
        }],
    /**
     * Usage : less-than-equal-$otherInputId
     */
        ['less-than-equal', function(v,elm,args,metadata) {
            var val = $("#"+args[0]);
            if(Validation.get('validate-number').test(v) && Validation.get('validate-number').test(val))
                return parseFloat(v) <= parseFloat(val);
            return v < val || v == val;
        }],
    /**
     * Usage : great-than-$otherInputId
     */
        ['great-than', function(v,elm,args,metadata) {
            if(Validation.get('validate-number').test(v) && Validation.get('validate-number').test($("#"+args[0])))
                return parseFloat(v) > parseFloat($("#"+args[0]));
            return v > $("#"+args[0]);
        }],
    /**
     * Usage : great-than-equal-$otherInputId
     */
        ['great-than-equal', function(v,elm,args,metadata) {
            var val = $("#"+args[0]);
            if(Validation.get('validate-number').test(v) && Validation.get('validate-number').test(val))
                return parseFloat(v) >= parseFloat(val);
            return v > val || v == val;
        }],
        /*
         * Usage: min-length-$number
         * Example: min-length-10
         */
        ['min-length',function(v,elm,args,metadata) {
            return v.length >= parseInt(args[0]);
        }],
        /*
         * Usage: max-length-$number
         * Example: max-length-10
         */
        ['max-length',function(v,elm,args,metadata) {
            return v.length <= parseInt(args[0]);
        }],
        /*
         * Usage: validate-file-$type1-$type2-$typeX
         * Example: validate-file-png-jpg-jpeg
         */
        ['validate-file',function(v,elm,args,metadata) {
            return $("#"+args).any(function(extentionName) {
                return new RegExp('\\.'+extentionName+'$','i').test(v);
            });
        }],
        /*
         * Usage: float-range-$minValue-$maxValue
         * Example: -2.1 to 3 = float-range--2.1-3
         */
        ['float-range',function(v,elm,args,metadata) {
            return (parseFloat(v) >= parseFloat(args[0]) && parseFloat(v) <= parseFloat(args[1]));
        },{depends : ['validate-number']}],
        /*
         * Usage: int-range-$minValue-$maxValue
         * Example: -10 to 20 = int-range--10-20
         */
        ['int-range',function(v,elm,args,metadata) {
            return (parseInt(v) >= parseInt(args[0]) && parseInt(v) <= parseInt(args[1]));
        },{depends : ['validate-integer']}],
        /*
         * Usage: length-range-$minLength-$maxLength
         * Example: 10 to 20 = length-range-10-20
         */
        ['length-range',function(v,elm,args,metadata) {
            return (v.length >= parseInt(args[0]) && v.length <= parseInt(args[1]));
        }],
        /*
         * Usage: max-value-$number
         * Example: max-value-10
         */
        ['max-value',function(v,elm,args,metadata) {
            return parseFloat(v) <= parseFloat(args[0]);
        },{depends : ['validate-number']}],
        /*
         * Usage: min-value-$number
         * Example: min-value-10
         */
        ['min-value',function(v,elm,args,metadata) {
            return parseFloat(v) >= parseFloat(args[0]);
        },{depends : ['validate-number']}],
        /*
         * Usage: validate-pattern-$RegExp
         * Example: <input id='sex' class='validate-pattern-/^[fm]$/i'>
         */
        ['validate-pattern',function(v,elm,args,metadata) {
            return eval('('+args.singleArgument+'.test(v))');
        }],
        /*
         * Usage: validate-ajax-$url
         * Example: <input id='email' class='validate-ajax-http://localhost:8080/validate-email.jsp'>
         */
        ['validate-ajax',function(v,elm,args,metadata) {
            //TODO has been abolished
            return true;
        },{ignoreEmptyValue:false}],
        /*
         * Usage: validate-dwr-${service}.${method}
         * Example: <input id='email' class='validate-dwr-service.method'>
         */
        ['validate-dwr',function(v,elm,args,metadata) {
            var result = false;
            var callback = function(methodResult) {
                if(methodResult)
                    metadata._error = methodResult;
                else
                    result = true;
            };
            var call = args.singleArgument+"('"+v+"',callback)";
            DWREngine.setAsync(false);
            eval(call);
            DWREngine.setAsync(true);
            return result;
        }],
        /*
         * Usage: validate-buffalo-${service}.${method}
         * Example: <input id='email' class='validate-buffalo-service.method'>
         */
        ['validate-buffalo',function(v,elm,args,metadata) {
            var result = false;
            var callback = function(reply) {
                if(replay.getResult())
                    metadata._error = replay.getResult();
                else
                    result = true;
            };
            if(!BUFFALO_END_POINT) alert('not found "BUFFALO_END_POINT" variable');
            var buffalo = new Buffalo(BUFFALO_END_POINT,false);
            buffalo.remoteCall(args.singleArgument,v,callback);
            return result;
        }],
        /*
         * Usage: validate-date-$dateFormat or validate-date($dateFormat default is yyyy-MM-dd)
         * Example: validate-date-yyyy/MM/dd
         */
        ['validate-date', function(v,elm,args,metadata) {
            var dateFormat = args.singleArgument || 'yyyy-MM-dd';
            metadata._error = jValidation.util.format(jValidation.util.getI18nMsg(metadata.className),[dateFormat,dateFormat.replace('yyyy','2006').replace('MM','03').replace('dd','12')]);
            return jValidation.util.isDate(v,dateFormat,elm,"1");
        }],
        ['validate-selection', function(v,elm,args,metadata) {
            return elm.options ? elm.selectedIndex > 0 : !((v == null) || (v.length == 0));
        }],

    /**
     *
     *Example :checkbox-required-2
     */
        ['checkbox-required', function(v,elm,args,metadata) {

            var size = 0;
            var $wrap = $(elm).closest('.j-checkbox-wrapper');
            if($wrap.length) {
                size = $wrap.find("input[type='checkbox']:checked").size();
            } else {
                size = $(elm).parent().find("input[type='checkbox']:checked").size();
            }
            return size !=0;
        },{ignoreEmptyValue:false}],
        ['validate-integer',/^[-+]?[1-9]\d*$|^0$/],
        ['validate-ip',/^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/],

        //中国相关验证开始
        ['validate-id-number',function(v,elm,args,metadata) {
            if(!(/^\d{17}(\d|x)$/i.test(v) || /^\d{15}$/i.test(v))) return false;
            var provinceCode = parseInt(v.substr(0,2));
            if((provinceCode < 11) || (provinceCode > 91)) return false;
            var forTestDate = v.length == 18 ? v : v.substr(0,6)+"19"+v.substr(6,15);
            var birthday = forTestDate.substr(6,8);
            //TODO CYC 20160418
            //if(!jValidation.util.isDate(birthday,'yyyyMMdd')) return false;
            if(!jValidation.util.isDate(birthday,'yyyyMMdd')) return false;
            if(v.length == 18) {
                v = v.replace(/x$/i,"a");
                var verifyCode = 0;
                for(var i = 17;i >= 0;i--)
                    verifyCode += (Math.pow(2,i) % 11) * parseInt(v.charAt(17 - i),11);
                if(verifyCode % 11 != 1) return false;
            }
            return true;
        }],
        ['validate-chinese',/^[\u4e00-\u9fa5]+$/],
        ['validate-phone',/^((0[1-9]{3})?(0[12][0-9])?[-])?\d{6,8}$/],
        ['validate-mobile-phone',/^0?(13[0-9]|15[0-9]|18[0-9]|14[57]|17[0-9])[0-9]{8}$/],
        ['validate-mobile-and-phone', function (v,elm,args,metadata) {
            if(/^((0[1-9]{3})?(0[12][0-9])?[-]?)?\d{6,8}$/.test(v) || /^0?(13[0-9]|15[0-9]|18[0-9]|14[57]|17[0-9])[0-9]{8}$/.test(v))
                return true;
            return false

        }],
        ['validate-zip',/^[1-9]\d{5}$/],
        ['validate-qq',/^[1-9]\d{4,8}$/],
        ['validate-devid',/^[0-9A-Z]{6}$/],
        ['validate-space',/^[^\s]+$/],
        ['validate-money',/^[0-9]+([.]{1}[0-9]{1,2})?$/],
        ['validate-contact-way',/^((0[1-9]{3})?(0[12][0-9])?[-])?\d{6,8}|0?(13[0-9]|15[012356789]|18[012356789]|14[57])[0-9]{8}$/],
        ['validate-org-length', function (v,elm,args,metadata) {
            v = eval("("+v+")");
            var  len;
            if (Util.isStrEquals(elm.id,"location"))
                len = 1;
            else
                len = 2;

            if(v.keys[0]!="" && (v.keys.length<3 || v.keys[len]==""))
                return false;
            return true;
        }],
        ['validate-org-code', /^[0-9A-Z]{8}[-][0-9A-Z]{1}$/],
        ['validate-special-char', function (v,elm,args,metadata) {
            var pattern = new RegExp("[']");
            if (pattern.test(v))
                return false;
            return true;
        }]
    ]);

})(jQuery.jValidation.Validation,jQuery);