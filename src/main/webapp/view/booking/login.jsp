<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2017-8-22
  Time: 10:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
%>
<html>
<head>
    <title>会议室预定系统</title>
    <jsp:include flush="true" page="/view/common/resource1.jsp"></jsp:include>
    <script type="text/javascript">

    </script>
</head>
<body>
<div class="container">
  <div class="menuWrap">
    <div class="menuWrap-item left-button">
      <button onclick="booking()"><span class="buttontext">登录</span></button>
    </div>
  </div>
</div>
</body>
</html>
