<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2017-9-1
  Time: 10:19
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
    <title>会议室管理系统后台</title>
    <link href="<%=path%>/includes/styles/booking/adminLogin.css" rel="stylesheet">
    <script type="text/javascript">
      function login(){
        var account = $('[name=account]').val();
        var password = $('[name=password]').val();
        $.post('<%=path%>/booking/adminLogin/'+account+'/'+password+'',
          function(data){
            if(data.flag!='success'){
              alert(data.flag);
              return;
            }
          }
        );
      }
    </script>
</head>
<body>
<div class="index-main">
  <div class="index-main-body">
    <div class="index-header">
      <h2 class="subtitle">欢迎登录会议室管理系统后台</h2>
    </div>
    <div>
      <div class="view-signup">
        <form>
          <div class="group-inputs">
            <div class="name input-wrapper">
              <input type="text" name="account" placeholder="请输入用户名">
            </div>
            <div class="verification input-wrapper">
              <input type="password" name="password" placeholder="请输入密码">
            </div>
          </div>
          <div class="button-wrapper">
            <button class="sign-button" onclick="login()">登录</button>
          </div>
        </form>
        <p class="agreement-tip">
          <a href="">忘记密码</a>
        </p>
      </div>
    </div>
  </div>
</div>
</body>
</html>
