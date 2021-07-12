<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2020-4-30
  Time: 15:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
%>
<html>
<head>
  <title>判客</title>
  <jsp:include flush="true" page="/view/common/resource1.jsp"></jsp:include>
  <script type="text/javascript">
    function check(){
      var phoneNumber = $('[name=phoneNumber]').val();
      $.post('<%=path%>/nc/checkCustomerExists/'+phoneNumber+'',
              function(data){
                if(data==1){
                  alert("号码已存在");
                }else{
                  alert("号码不存在");
                }
              }
      );
    }
    function checkNew(){
        var checkValue = $('[name=phoneNumber]').val();
        var checkType = 'tel';
        $.post('<%=path%>/nc/checkCustomerExists/'+checkValue+'/'+checkType,
                function(data){
                    alert(data)
//                    if(data==1){
//                        alert("号码已存在");
//                    }else{
//                        alert("号码不存在");
//                    }
                }
        );
    }
    function checkFollow(){
        var checkValue = $('[name=phoneNumber]').val();
        var checkType = 'tel';
        $.post('<%=path%>/nc/CheckCustomerFollow/'+checkValue+'/'+checkType,
                function(data){
                    alert(data)
//                    if(data==1){
//                        alert("号码已存在");
//                    }else{
//                        alert("号码不存在");
//                    }
                }
        );
    }
    <%--function getAll(){--%>
        <%--$.post('<%=path%>/nc/getAllCPS',--%>
                <%--function(data){--%>
                    <%--alert('success');--%>
                <%--}--%>
        <%--);--%>
    <%--}--%>
  </script>
</head>
<body>
<div class="container">
  <input type="text" name="phoneNumber" placeholder="请输入手机号" class="easyui-validatebox" required="true" >
  <div class="menuWrap">
    <div class="menuWrap-item left-button">
      <button onclick="checkNew()"><span class="buttontext">判客</span></button>
      <button onclick="checkFollow()"><span class="buttontext">查看跟进</span></button>
      <%--<button onclick="getAll()"><span class="buttontext">获取全部</span></button>--%>
    </div>
  </div>
</div>
</body>
</html>
