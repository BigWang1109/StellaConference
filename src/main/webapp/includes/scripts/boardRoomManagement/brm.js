/**
 * Created by thinkpad on 2017-9-21.
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
        'biz.util.openLink',
        'biz.util.chosen']
});
dd.ready(function() {

    function test(data){
        dd.device.notification.alert({
            message: data.key,
            title: "提示",//可传空
            buttonName: "收到",
            onSuccess : function() {
                //onSuccess将在点击button之后回调
                /*回调*/
            },
            onFail : function(err) {}
        });
    };
});
