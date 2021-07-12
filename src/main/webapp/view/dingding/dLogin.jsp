<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2017-8-14
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <title>简历查询</title>
    <jsp:include flush="true" page="/view/common/jqueryResource.jsp"></jsp:include>
    <%--<jsp:include flush="true" page="/view/common/resource1.jsp"></jsp:include>--%>
    <%--<script type="text/javascript">--%>
        <%--var _config;--%>
        <%--$.ajax({--%>
            <%--async: false,--%>
            <%--type:'post',--%>
<%--//            type:'get',--%>
            <%--url:'<%=path%>/dd/getConfig',--%>
            <%--cache:false,--%>
            <%--success : function(data){--%>
                <%--_config = data;--%>
            <%--},--%>
            <%--error:function(){--%>
                <%--alert('error');--%>
            <%--}--%>
        <%--});--%>
<%--//        alert(_config.corpId);--%>
    <%--</script>--%>


    <%--<script type="text/javascript" src="http://g.alicdn.com/dingding/open-develop/1.6.9/dingtalk.js">--%>
    <%--</script>--%>
    <%--<script src="https://g.alicdn.com/dingding/dingtalk-jsapi/2.10.3/dingtalk.open.js"></script>--%>
    <script type="text/javascript" src="<%=path%>/includes/scripts/common/zepto.min.js"></script>
    <script type="text/javascript" src="<%=path%>/includes/scripts/common/vue.min.js"></script>
    <script type="text/javascript" src="<%=path%>/includes/scripts/dingding/dingtalk.open.js"></script>
    <%--</script>--%>
    <%--<script type="text/javascript" src="<%=path%>/includes/scripts/common/logger.js">--%>
    <%--</script>--%>
    <%--获取code码值的js文件--%>
    <%--<script type="text/javascript" src="<%=path%>/includes/scripts/dingding/dingtalk.open.js">--%>
    <script type="text/javascript">
        <%--var _config = <%= com.wxx.conference.controller.dingding.AuthHelper.getConfig(request) %>;--%>
    </script>
    <script type="text/javascript">
//        dd.config({
//            agentId : _config.agentId,
//            corpId : _config.corpId,
//            timeStamp : _config.timeStamp,
//            nonceStr : _config.nonceStr,
//            signature : _config.signature,
//            type : 0,
//            jsApiList : [
//                'runtime.info',
//                'biz.contact.choose',
//                'device.notification.confirm',
//                'device.notification.alert',
//                'device.notification.prompt',
//                'biz.ding.post',
//                'biz.util.openLink'
//            ]
//        });
        dd.ready(function() {
            <%--alert('${corpId}');--%>
            dd.runtime.permission.requestAuthCode({
//                corpId : _config.corpId,
                corpId : '${corpId}',
                onSuccess : function(info) {
                    window.location.href = '<%=path%>/CV/CVSearchEnterDD/'+info.code+'?showmenu=false';
			        <%--alert('authcode: ' + info.code);--%>
                    <%--$.ajax({--%>
                        <%--url : '<%=path%>/DD/getUserId/'+info.code,--%>
                        <%--type : 'post',--%>
                        <%--success : function(userId){--%>
                            <%--alert(userId)--%>
                        <%--}--%>
                    <%--})--%>
                },
                onFail : function(err) {
                    alert('fail: ' + JSON.stringify(err));
                }
            });
        });
    </script>
</head>
<body>
<div style="margin: 0 auto;font-size: 30px;text-align: center;padding-top: 100px;">
    <p>系统登录中...</p>
</div>
</body>
</html>
