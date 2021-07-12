<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2019-11-6
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
%>
<html>
<head>
    <title>泛海门户</title>
    <jsp:include flush="true" page="/view/common/resource1.jsp"></jsp:include>
    <link href="<%=path%>/includes/styles/portal/main.css" rel="stylesheet">
    <script type="text/javascript">
      var orgMember = '${orgMember}';
      function logout(){
        window.location.href = "<%=path%>/portal/login";
      }
      function open_OA(){
        window.open("http://10.0.25.11","top");
      }
      function open_fhkg_mail(){
        window.open("http://mail.fhkg.com","top");
      }
      function open_report(){
        window.open("http://10.0.125.7:8008/openreportfromv8.htm??reportId=41c54364-70ce-4df3-8430-707102fea52c&usercode=${orgMember.CODE}","top");
      }
      function open_webreport(){
        window.open("http://10.4.8.101:8080/WebReport/sso_dashboard.jsp","top");
      }
      function open_T6(){
        window.open("http://10.0.125.39/seeyon/unisso.do?method=loginSystem&sys=T6","top");
      }
      function open_hr(){
        window.open("http://10.0.26.11/hrss/access.login.d","top");
      }
      function open_webreport2(){
        window.open("http://10.4.8.101:8080/WebReport/sso_report.jsp","top");
      }
      function open_chinaoceanwide_mail(){
        window.open("http://localhost:8060/CV/CVSearchEnterPC","top");
      }
    </script>
</head>
<body>
  <input type="hidden" value="${token}" id="token"/>
  <input type="hidden" value="${userid}" id="userid"/>
  <div class="container">
    <div class="header">
        <div class="header-left">
          <img class="logo" src="<%=path%>/includes/images/portal/logo/fhkg_logo.png"><span class="title">泛海控股统一登录平台</span>
        </div>
        <div class="header-right">
          <span class="account"> ${orgMember.NAME}</span>
          <button class="quit_btn" onclick="logout()">退出</button>
        </div>
    </div>
    <div class="app_group">
      <div class = 'app' onclick="open_OA()">
        <div class="app_icon">
          <%--<a href="http://10.0.25.11" target="_blank">OA</a>--%>
          <span class="no_app_icon">OA</span>
        </div>
        <p class="app_name">OA</p>
      </div>
      <div class = 'app' onclick="open_fhkg_mail()">
        <div class="app_icon">
          <%--<a href="http://mail.fhkg.com" target="_blank">泛</a>--%>
          <span class="no_app_icon">泛</span>
        </div>
        <p class="app_name">泛海控股邮箱</p>
      </div>
      <div class = 'app' onclick="open_report()">
        <div class="app_icon">
          <%--<a href="http://10.0.125.7:8008" target="_blank">分</a>--%>
          <span class="no_app_icon">分</span>
        </div>
        <p class="app_name">分析云</p>
      </div>
      <div class = 'app' onclick="open_webreport()">
        <div class="app_icon">
          <%--<a href="http://10.0.125.7:8008" target="_blank">分</a>--%>
          <span class="no_app_icon">高</span>
        </div>
        <p class="app_name">高管驾驶舱</p>
      </div>
      <div class = 'app' onclick="open_T6()">
        <div class="app_icon">
          <%--<a href="http://10.0.125.7:8008" target="_blank">分</a>--%>
          <span class="no_app_icon">费</span>
        </div>
        <p class="app_name">费控报销</p>
      </div>
      <div class = 'app' onclick="open_hr()">
        <div class="app_icon">
          <%--<a href="http://10.0.125.7:8008" target="_blank">分</a>--%>
          <span class="no_app_icon">员</span>
        </div>
        <p class="app_name">员工自助</p>
      </div>
      <div class = 'app' onclick="open_webreport2()">
        <div class="app_icon">
          <%--<a href="http://10.0.125.7:8008" target="_blank">分</a>--%>
          <span class="no_app_icon">报</span>
        </div>
        <p class="app_name">报表统计分析</p>
      </div>
      <div class = 'app' onclick="open_chinaoceanwide_mail()">
        <div class="app_icon">
          <%--<a href="http://10.0.125.7:8008" target="_blank">分</a>--%>
          <span class="no_app_icon">中</span>
        </div>
        <p class="app_name">中国泛海邮箱</p>
      </div>
      <div class = 'app' onclick="open_chinaoceanwide_mail()">
        <div class="app_icon">
          <%--<a href="http://10.0.125.7:8008" target="_blank">分</a>--%>
          <span class="no_app_icon">简</span>
        </div>
        <p class="app_name">简历查询</p>
      </div>
    </div>
  </div>
</body>
</html>
