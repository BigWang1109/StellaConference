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
    <title>泛海统一门户平台</title>
    <jsp:include flush="true" page="/view/common/resource1.jsp"></jsp:include>
    <link href="<%=path%>/includes/styles/booking/adminLogin.css" rel="stylesheet">
    <script type="text/javascript">
      function login(){
        var account = $('[name=account]').val();
        var password = $('[name=password]').val();
        $.ajax({
          url : '<%=path%>/portal/portalLogin/'+account+'/'+password+'',
          type : 'post',
          success:function(data){
              if(data.flag == 'success'){
                  window.location.href = "<%=path%>/portal/enterMain?token="+data.token+'&&userid='+account
              }else{
                  alert("对不起，您无权登录");
              }
          },
          error:function(){
             alert("对不起，您无权登录");
             return;
          }
        });
      }
      <%--function enter(token) {--%>
          <%--$.ajax({--%>
              <%--url: '<%=path%>/portal/main/'+00025766,--%>
              <%--type: 'post',--%>
              <%--beforeSend:function(request){--%>
                  <%--request.setRequestHeader("Authorization",token);--%>
              <%--},--%>
              <%--success:function(){--%>
                  <%--window.location.href = "<%=path%>/portal/main/"+00025766--%>
              <%--}--%>
          <%--})--%>
      <%--}--%>
    </script>
</head>
<body>
<div class="index-main">
  <div class="index-main-body">
    <div class="index-header">
      <h2 class="subtitle">欢迎使用泛海控股统一登录平台</h2>
    </div>
    <div>
      <div class="view-signup">
        <%--<form>--%>
          <div class="group-inputs">
            <div class="name input-wrapper">
              <%--<input type="text" name="account" placeholder="请输入用户名" class="account" required="true" >--%>
              <input type="text" name="account" placeholder="请输入8位员工号" class="easyui-validatebox" required="true" >
            </div>
            <div class="verification input-wrapper">
              <%--<input type="password" name="password" placeholder="请输入密码" class="password" required="true">--%>
              <input type="password" name="password" placeholder="请输入密码" class="easyui-validatebox" required="true" >
            </div>
          </div>
          <div class="button-wrapper">
            <button class="sign-button" onclick="login()">登录</button>
          </div>
        <%--</form>--%>
        <p class="agreement-tip">
          <a href="">忘记密码</a>
        </p>
      </div>
    </div>
  </div>
</div>
</body>
</html>
