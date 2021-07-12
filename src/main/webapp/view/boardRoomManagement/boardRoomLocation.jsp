<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!doctype html>
<%
    String path = request.getContextPath();
%>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0, minimum-scale=1, maximum-scale=1" />
    <%--<link rel="stylesheet" href="./index.css" type="text/css">--%>
    <%--<link href="<%=path%>/includes/styles/common/map/index.css" rel="stylesheet">--%>
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <title>位置</title>
    <style>
        body{
            position: absolute;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
            margin: 0;
        }
        iframe{
            width: 100%;
            height: 100%;
        }
    </style>
</head>
<body>
<%--<iframe id="test" src="https://m.amap.com/picker/?keywords=写字楼&center=116.420186,39.907226&total=20&key=00db2461bcfe556c95b94366e9e756d9"></iframe>--%>
<iframe id="test" src="https://m.amap.com/picker/?keywords=写字楼&total=20&key=00db2461bcfe556c95b94366e9e756d9"></iframe>
<script type="text/javascript">
    (function(){
        var iframe = document.getElementById('test').contentWindow;
        document.getElementById('test').onload = function(){
//            iframe.postMessage('hello','https://m.amap.com/picker/');
            iframe.postMessage('hello','https://m.amap.com/picker/');
        };
        window.addEventListener("message", function(e){
            alert('您选择了:' + e.data.name + ',' + e.data.location)
        }, false);
    }())
</script>
<script type="text/javascript">
    var mock = {
        log: function(result) {
            window.parent.setIFrameResult('log', result);
        }
    }
    console = mock;
    window.Konsole = {
        exec: function(code) {
            code = code || '';
            try {
                var result = window.eval(code);
                window.parent.setIFrameResult('result', result);
            } catch (e) {
                window.parent.setIFrameResult('error', e);
            }
        }
    }
</script>
<script type="text/javascript">

    var mock = {
        log: function(result) {
            window.parent.setIFrameResult('log', result);
        }
    }
    console = mock;
    window.Konsole = {
        exec: function(code) {
            code = code || '';
            try {
                var result = window.eval(code);
                window.parent.setIFrameResult('result', result);
            } catch (e) {
                window.parent.setIFrameResult('error', e);
            }
        }
    }
</script>
</body>

</html>