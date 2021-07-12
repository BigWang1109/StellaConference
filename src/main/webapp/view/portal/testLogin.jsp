<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2019-11-11
  Time: 11:51
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
    <title></title>
  <jsp:include flush="true" page="/view/common/resource1.jsp"></jsp:include>
  <script type="text/javascript">
    function login(){
      var account = $('[name=account]').val();
      var password = $('[name=password]').val();
      $.ajax({
      url : '<%=path%>/portal/checkLogin/'+account+'/'+password+'',
      type : 'post',
      success:function(data){
      if(data.flag == "success"){
      alert("success");
      window.location.href = "<%=path%>/portal/main";
      }else{
      alert("对不起，您无权登录");
      return;
      }
      }
      })
      <%--$.post('<%=path%>/portal/checkLogin/'+account+'/'+password+'',--%>
              <%--function(data){--%>
                <%--if(data.flag=='success'){--%>
                  <%--window.location.href = "<%=path%>/portal/main";--%>
                <%--}else{--%>
                  <%--alert("对不起，您无权登录");--%>
                <%--}--%>
              <%--}--%>
      <%--);--%>
    }
  </script>
</head>
<body>
<input type="text" name="account" placeholder="请输入用户名" class="account">
<input type="password" name="password" placeholder="请输入密码" class="password">
<button class="sign-button" onclick="login()">登录</button>
</body>
</html>
