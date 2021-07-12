<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2017-8-23
  Time: 16:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  String path = request.getContextPath();
%>
<html>
<head>
    <title>预定信息</title>
    <style type="text/css">
      .container{
        text-align: center;

      }
      .title{
        text-align: center;;
      }
    </style>
    <script type="text/javascript">
      $(document).ready(function(){
//        countTable();
      });

    </script>
</head>
<body>
  <div class="container">
    <%--<a>${bookingRegion.bookingId}</a>--%>
    <%--<a>${bookingRegion.bookingDate}</a>--%>
      <h3>会议主题：${bookingRegion.bookingTitle}</h3>
      <h3>会议日期：<fmt:formatDate value="${bookingRegion.bookingDate}" pattern="yyyy-MM-dd"/></h3>
      <h3>会议时间：${bookingRegion.bookingDual}</h3>
      <%--<h3 class="dualTable">${bookingRegion.bookingDual}</h3>--%>
    <%--${bookingRegion.isManager}--%>
      <h3>会议地点：${bookingRegion.bookingRegionId}${bookingRegion.regionId}</h3>
  </div>
</body>
</html>
