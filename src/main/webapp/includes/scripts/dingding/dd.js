/**
 * Created by thinkpad on 2017-8-14.
 */
dd.config({                                                          //实现验证
    agentId : _config.agentId,
    corpId : _config.corpId,
    timeStamp : _config.timeStamp,
    nonceStr : _config.nonceStr,
    signature : _config.signature,
    jsApiList : [
        'runtime.info',
        'biz.contact.choose',
        'device.notification.confirm',
        'device.notification.alert',
        'device.notification.prompt',
        'biz.ding.post',
        'biz.util.openLink' ]
});
dd.ready(function() {                                               //验证成功
    dd.runtime.permission.requestAuthCode({                         //获取code码值
        corpId : _config.corpId,
        onSuccess : function(info) {
            //alert('authcode: ' + info.code);
            alert('!!!!');
            $.ajax({
                url : 'userinfo?code=' + info.code + '&corpid='     //请求后台通过code值获得userId
                + _config.corpId,
                //url:'user/getUserInfo/'+code+'',
                type : 'GET',
                success : function(data, status, xhr) {
                    //var info = JSON.parse(data);
                    alert(data);
                    //var info = data;
                    //
                    //alert(info.name);
                    //document.getElementById("userName").innerHTML = info.name;
                    //document.getElementById("userId").innerHTML = info.userid;

                },
                error : function(xhr, errorType, error) {
                    logger.e("yinyien:" + _config.corpId);
                    alert(errorType + ', ' + error);
                }
            });

        },
        onFail : function(err) {
            //alert(_config.ticket);
            alert('fail: ' + JSON.stringify(err));
        }
    });
});

dd.error(function(err) {                                             //验证失败
    document.getElementById("userName").innerHTML = "验证出错";
    alert('dd error: ' + JSON.stringify(err));
});