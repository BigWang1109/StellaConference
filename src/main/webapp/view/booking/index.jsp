<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
%>
<html>
<head>
  <title>会议室预定系统</title>
  <link href="<%=path%>/includes/styles/booking/index.css" rel="stylesheet">
  <jsp:include flush="true" page="/view/common/resource1.jsp"></jsp:include>
  <script type="text/javascript">
    function booking(){
      location.href = "<%=path%>/view/booking/booking.jsp"
    }
    function getBookingList(){
      location.href = "<%=path%>/booking/getBookingList"
    }
  </script>
</head>
<body>
<div class="container">
  <div class="menuWrap">
    <div class="menuWrap-item left-button">
      <button onclick="booking()"><span class="buttontext">预约会议室</span></button>
    </div>
    <div class="menuWrap-item right-button">
      <button onclick="getBookingList()"><span class="buttontext">我的预约</span></button>
    </div>
  </div>
</div>
</body>
</html>