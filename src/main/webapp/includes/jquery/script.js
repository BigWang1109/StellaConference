/*
 * chairui
 *
 * 2016-04-16 easyui 扩展 combotree 取消选择
 */

; (function () {

    var utils = function () {
        this.formatBoolean = function (value, trueTitle, falseTitle) {
            if (value == 'True' || value == 'true' || value == '1') {
                if('undefined' !== typeof trueTitle){
                    return trueTitle;
                }
                else{
                    return "是";
                }
            }
            else {
                if('undefined' !== typeof falseTitle){
                    return falseTitle;
                }
                else{
                    return "否";
                }
            }
        },
        this.replaceDict = function(dict, value){
            //dict = [{ "name": "title0", "value": 0 }, { "name": "title1", "value": 1}];
            var name = '';
            for(var i=0; i< dict.length; i++){
                if(dict[i].value == value){
                    name = dict[i].name;
                    break; 
                }
            }
            return name;
        },
        this.formatGTM = function(value){
            //value = Sat, 08 Feb 2014 00:00:00 GMT
            var date = new Date(value);
            var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
            var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
            return date.getFullYear() + "-" + month + "-" + day;
        },
        this.formatJsonDate = function (cellval) {
            //空值处理
            if(cellval == null || cellval == ''){
                return '';
            }

            //spring mvc 序列化 631209600000
            cellval = cellval + "";
            //.net mvc 序列化 /Date(1325696521000)/
            var intval = parseInt(cellval.replace("/Date(", "").replace(")/", ""));
            if (intval == -62135596800000) {
                return '';
            }

            var date = new Date(parseInt(cellval.replace("/Date(", "").replace(")/", ""), 10));
            var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
            var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
            return date.getFullYear() + "-" + month + "-" + currentDate;
        },
        this.openWindow = function(title, size, url, fn){
            //size: 0,1,2 对应 width:350 650 850  height:450  自定义:'200,200'

            _topWindow = window.top.$('#topWindow');
            if (_topWindow.length <= 0){
                _topWindow = window.top.$('<div id="topWindow"/>').appendTo(window.top.document.body);
            }

            var width = 350;
            var height = 450;
            if(size == 0) {
                width = 350;
                height = 300;
            }
            else if(size == 1) {
                width = 650;
                height = 450;
            }
            else if(size == 2) {
                width = 850;
                height = 450;
            }
            else if(size.indexOf(",")>-1){
                var array = size.split(",");
                width = array[0] * 1.0;
                height = array[1] * 1.0;
            }

            _topWindow.dialog({
                title: title,
                href: url,
                width: width,
                height: height,
                collapsible: false,
                minimizable: false,
                maximizable: false,
                resizable: false,
                cache: false,
                modal: true,
                closed: false,
                buttons: [{
                    text: '确定',
                    handler: function () {
                        fn();
                    }
                },
                {
                    text: '取消',
                    handler: function () {
                        _topWindow.dialog('close');
                    }
                }],
                onClose: function () {
                    _topWindow.window('destroy');
                }
            });

        }
    };

    window.utils = utils;
})();

var utils = new utils();

var _topWindow;



//对Date的扩展，将 Date 转化为指定格式的String   
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，   
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)   
// 例子：   
// (new Date()).format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423   
// (new Date()).format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18   
Date.prototype.format = function(fmt)   
{ //author: meizz   
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
};
//替换
String.prototype.replaceAll = function (oldstr, newstr) {
    return this.toString().replace(new RegExp(oldstr, "gm"), newstr);
}

/*
Usage 1: define the default prefix by using an object with the property prefix as a parameter which contains a string value; {prefix: 'id'}
Usage 2: call the function jQuery.uuid() with a string parameter p to be used as a prefix to generate a random uuid;
Usage 3: call the function jQuery.uuid() with no parameters to generate a uuid with the default prefix; defaul prefix: '' (empty string)

$.uuid();

*/
/*
Generate fragment of random numbers
*/
jQuery._uuid_default_prefix = '';
jQuery._uuidlet = function () {
    return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
};
/*
Generates random uuid
*/
jQuery.uuid = function (p) {
    if (typeof (p) == 'object' && typeof (p.prefix) == 'string') {
        jQuery._uuid_default_prefix = p.prefix;
    } else {
        p = p || jQuery._uuid_default_prefix || '';
        return (p + jQuery._uuidlet() + jQuery._uuidlet() + "-" + jQuery._uuidlet() + "-" + jQuery._uuidlet() + "-" + jQuery._uuidlet() + "-" + jQuery._uuidlet() + jQuery._uuidlet() + jQuery._uuidlet());
    };
};




jQuery.fn.outerHTML = function (s) {
    return (s) ? this.before(s).remove() : jQuery("<p>").append(this.eq(0).clone()).html();
}


/*
    bootstrap checkbox 处理

<label class="checkbox-inline">
    <input type="checkbox" id="X0126" parsley-group="hobby" value="1"> 接受杂志短信
</label>

$('input[type="checkbox"]').checkbox();
*/
jQuery.fn.checkbox = function () {
    this.each(function () {
        var checkbox = this;
        var hd_name = $(checkbox).attr('id');
        var hd_value = $(checkbox).attr('value');
        var hd_id = hd_name + '_hidden';

        //checkbox 无法序列化 $('#form').serializeArray()
        //创建同名的hidden对象
        if (hd_value == '1') {
            $(checkbox).prop("checked", true);
            $(checkbox).before('<input id="' + hd_id + '" name="' + hd_name + '" type="hidden" value="1" />');
        }
        else {
            $(checkbox).before('<input id="' + hd_id + '" name="' + hd_name + '" type="hidden" value="0" />');
            $(checkbox).prop("checked", false);

        }

        $(checkbox).parent().mouseup(function (e) {
            if ($(checkbox).prop('checked')) {
                $(checkbox).attr("value", '0');
                $('#' + hd_id).attr("value", '0');
            }
            else {
                $(checkbox).attr("value", '1');
                $('#' + hd_id).attr("value", '1');
            }
            e.stopPropagation();
            return;
        })
    })

}


jQuery.fn.radiolist = function () {
    var options = this.data('options');
    //单引号替换为双引号
    options = options.replace(new RegExp("'","gm"),"\"");
    options = options.replace(new RegExp("\t","gm"),"");
    options = options.replace(new RegExp("\r","gm"),"");
    options = options.replace(new RegExp("\n","gm"),"");
    options = $.parseJSON(options);

    //初始化 radio list
    var id = this.attr('id');
    for(i=0; i< options.length; i++){
        $('<label> <input type="radio" name="ui_' + id + '" class="' + id + '" data-key="' + options[i].id + '" />'+ options[i].text +' </label>').insertBefore(this);
    }

    var defaultValue = this.attr('value');
    //初始化默认值
    $("input[name='ui_" + id +"']").each(function(){
        if ($(this).data('key') == defaultValue){
            $(this).prop('checked', true);
        }
    })

    //更新选中的值
    $('.'+id).click(function(){
        $('#'+id).val($(this).data('key'));
    });
}


$.ajaxPrefilter(function (options) {
    var originalUrl = options.url;
    if (originalUrl.indexOf('?') > 0) {
        options.url = originalUrl + "&ajaxPrefilter=" + new Date().getTime();
    } else {
        options.url = originalUrl + "?ajaxPrefilter=" + new Date().getTime();
    }
});


/*
    打印相关
*/
function PageSetup(name, value) {
    var Wsh = new ActiveXObject("WScript.Shell");
    Wsh.RegWrite("HKEY_CURRENT_USER\\Software\\Microsoft\\Internet Explorer\\PageSetup\\" + name, value);
}

function openPrintWindow(url, msg) {
    var owner = null;
    owner = window.open("", "打印窗口", "height=50, width=50, top=0, left=0, menubar=no, location=no, scrollbars=no, resizable=no, status=no");
    //owner = window.open("", "打印窗口");
    owner.window.document.title = "打印窗口";
    owner.location = url + "&Key=" + $.uuid();
    setTimeout(function () { execVBPrint(owner) }, 2000);
}

function execVBPrint(owner) {
    try {
        if (document.readyState == "complete") {
            if (!$.support.leadingWhitespace) {
                try {
                    //页面设置
                    PageSetup('header', '');
                    PageSetup('footer', '');
                    PageSetup('margin_left', 0.27559);      //6     0.23622
                    PageSetup('margin_right', 0.27559);     //7     0.27559
                    PageSetup('margin_bottom', 0.27559);    //8.13  0.32000
                    PageSetup('margin_top', 0.27559);
                }
                catch (e) {
                    alert('页面设置失败，请配置浏览器安全中的 Activex 控件和插件权限。');
                }
            }

            setTimeout(function () {
                //关闭打印窗口，仅适用IE6、7、8浏览器
                try{
                    owner.opener = null;
                    owner.open('', '_self');
                    owner.close();
                    owner = null;
                }
                catch(e){
                    console.warn(e);
                }
            }, 1000);

            if (!+[1, ]) {
                //直接打印，不显示打印设置窗口，仅适用IE6、7、8浏览器
                //vbPrintPage(6, 2, 3);
                execScript(n = "vbPrintPage 6, 2, 3", "vbscript");
                //注意：vbscript 后面的js脚本不会被执行
            } else {
                //IE9及以上IE版本
                document.WebBrowser.ExecWB(6, 6);
            }
        }
        else {
            //如果页面没有加载完毕，下一秒重新尝试打印
            setTimeout(function () { execVBPrint(owner) }, 1000);
        }
    }
    catch (e) {
        console.warn(e);
    }
}



/*
     easyui 验证函数扩展
 */
try{
    $.extend($.fn.validatebox.defaults.rules, {
        minLength: {
            validator: function(value, param){
                return value.length >= param[0];
            },
            message: '请至少输入 {0} 个字符'
        },
        equals: {
            validator: function(value, param){
                return value == $(param[0]).val();
            },
            message: '验证失败'
        },
        maxThan: {
            validator: function (value, param) {
                var s = $(param[0]).datebox('getValue');
                //alert(s);
                return value >= s;
            },
            message: '验证失败'
        }
    });
}
catch (e){
    //console.warn(e);
}


try{
    //combotree 取消选择 示例
    //var t = $(this).combotree('tree');
    //var n = t.tree('getSelected');
    //if (n != null) {
    //    t.tree("unSelect", n.target);
    //}
    $.extend($.fn.tree.methods, {
        unSelect: function (jq, target) {
            return jq.each(function () {
                $(target).removeClass("tree-node-selected");
            });
        }
    });
}
catch (e){
    //console.warn(e);
}





var constant = {
    "Loading" :"正在处理中，请稍候...",
    "OnlyOneSelected":"请至少选择一行记录！",
    "ConfirmDelete":"确认要删除吗？",
    "ConfirmSubmit" : "确认要提交吗？",
    "ConfirmArchive":"确认要归档吗？",
    "FailedToSubmitOnNetwork" : "提交失败，您的网速较慢请重新尝试提交。当前状态：",
    "FailedToSubmit":"提交失败：",
    "FailedToDeleteOnNetwork":"删除失败，您的网速较慢请重新尝试删除。当前状态：",
    "FailedToDelete":"删除失败：",
    "FailedToArchiveOnNetwork":"归档失败，您的网速较慢请重新尝试归档。当前状态：",
    "FailedArchive":"归档失败："

}