<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2017-8-16
  Time: 13:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
%>
<html>
<head>
  <title>泛海控股</title>
  <jsp:include flush="true" page="/view/common/resource1.jsp"></jsp:include>
  <link href="<%=path%>/includes/styles/booking/swipe.css" rel="stylesheet">
  <link href="<%=path%>/includes/styles/booking/index.css" rel="stylesheet">
  <style>
    .swipe {
      overflow: hidden;
      visibility: hidden;
      position: relative;
    }
    .swipe-wrap {
      overflow: hidden;
      position: relative;
    }
    .swipe-wrap > div {
      float:left;
      width:100%;
      position: relative;
    }
  </style>
</head>
<body>
<div class="container">
  <div id='mySwipe' style='max-width:500px;margin:0 auto' class='swipe'>
    <div class='swipe-wrap'>
      <div><img src="<%=path%>/includes/images/booking/news/notice-1.png" style="width:100%;height:230px;"></div>
      <div><img src="<%=path%>/includes/images/booking/news/notice-2.png" style="width:100%;height:230px;"></div>
    </div>
  </div>
  <div class="menuWrap bg-white">
    <div class="menuWrap-item">
      <a href="<%=path%>/view/booking/booking.jsp">预约会议室</a>
    </div>
    <div class="menuWrap-item">
      <a href="<%=path%>/view/booking/booking.jsp">我的会议</a>
    </div>
  </div>
  <div class="noticeList bg-white">
    <div class="noticeList-item">
      <div><img src="<%=path%>/includes/images/booking/news/notice-1.png" style="width:130px;height:100px;"></div><div><p></p></div>
    </div>
    <div class="noticeList-item">
      <div><img src="<%=path%>/includes/images/booking/news/notice-2.png" style="width:130px;height:100px;"></div><div><p></p></div>
    </div>
    <div class="noticeList-item">
      <div><img src="<%=path%>/includes/images/booking/news/notice-1.png" style="width:130px;height:100px;"></div><div><p></p></div>
    </div>
  </div>
  <script src='<%=path%>/includes/scripts/booking/swipe.js'></script>
  <script type="text/javascript">
    var elem = document.getElementById('mySwipe');
    window.mySwipe = Swipe(elem, {
      startSlide: 0,
      auto: 3000,
      continuous: true,
      disableScroll: true,
      stopPropagation: true,
      callback: function(index, element) {},
      transitionEnd: function(index, element) {}
    });
  </script>
</div>
</body>
</html>
