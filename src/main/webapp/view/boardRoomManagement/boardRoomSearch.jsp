<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2017-9-21
  Time: 15:13
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
    <title>查找会议室</title>
    <link href="<%=path%>/includes/styles/boardRoomManagement/boardRoomSearch.css" rel="stylesheet">
    <script type="text/javascript" src="<%=path%>/includes/scripts/common/viewport.js"></script>
</head>
<body>
  <div class="container">
    <div class="row0 row">
      <span>查找会议室</span><span>会议模版</span>
    </div>
    <div class="row1 list row">
      <ul>
        <li><a>会议时长</a></li>
        <li><a>参会人数</a></li>
      </ul>
    </div>
    <div class="row2 list row">
      <ul>
        <li><a>会议地点</a></li>
        <li><a>参会日期</a></li>
        <li><a>开始时间</a></li>
        <li><a>更多条件</a></li>
      </ul>
    </div>
    <div class="row3">
      <button class="searchButton button">查找</button>
      <button class="resetButton button">重置</button>
    </div>
  </div>
</body>
</html>
