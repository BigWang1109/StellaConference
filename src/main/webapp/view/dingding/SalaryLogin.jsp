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
  <title>薪资对比</title>
  <jsp:include flush="true" page="/view/common/jqueryResource.jsp"></jsp:include>
  <script type="text/javascript" src="<%=path%>/includes/scripts/common/zepto.min.js"></script>
  <script type="text/javascript" src="<%=path%>/includes/scripts/common/vue.min.js"></script>
  <script type="text/javascript" src="<%=path%>/includes/scripts/dingding/dingtalk.open.js"></script>
  <script type="text/javascript">
    <%--var _config = <%= com.wxx.conference.controller.dingding.AuthHelper.getConfig(request) %>;--%>
  </script>
  <script type="text/javascript">
    dd.ready(function() {
      dd.runtime.permission.requestAuthCode({
        corpId : '${corpId}',
        onSuccess : function(info) {
          window.location.href = '<%=path%>/salary/SalarySearchEnterDD/'+info.code+'?showmenu=false';
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
