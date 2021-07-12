<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2021-4-28
  Time: 14:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
  String path = request.getContextPath();
%>
<html>
<head>
    <title>查询结果</title>
    <jsp:include flush="true" page="/view/common/resource1.jsp"></jsp:include>
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
  <style type="text/css">
    body{
      background-color: aliceblue;
    }
    #searchResult{
      text-align: center;
      height:800px;
      /*width:300px;*/
      overflow:auto;
      margin: 0 auto;
    }
    #searchResult > p{
      color: white;
    }
    #searchResultTable{
      font-family: verdana,arial,sans-serif;
      font-size:11px;
      color:#333333;
      /*border-width: 1px;*/
      /*border-color: #999999;*/
      /*border-collapse: collapse;*/
      /*border: none;*/
      /*/!*border-bottom: 1px;*!/*/
      /*border-bottom: dashed 1px #00BFFF;*/
      text-align: center;
      margin: 0 auto;
      width: 100%;
    }
    #searchResultTable tr{
      /*background-color:#d4e3e5;*/
      /*border-bottom: 1px;*/
      /*border-bottom: dashed 1px #00BFFF;*/
    }

    #searchResultTable td{
      border-width: 1px;
      border-bottom: dashed 1px #888888;
      padding: 8px;
      /*border-style: solid;*/
      /*border-color: #a9c6c9;*/
      /*width: 25%;*/
      height: 15px;
      /*font-size: 10px;*/
      word-wrap:break-word;word-break:break-all;
    }
    .psnname{
      width: 30%;
      font-size: 15px;
      font-weight: bold;
    }
    .corpname{
      width: 60%;
      font-size: 10px;
    }
    #searchTitleTable tr{
      background-color:#00abc7;
      color: white;
      font-weight: bold;
    }
    #searchTitleTable td{
      border-width: 1px;
      padding: 8px;
      border-style: solid;
      border-color: #a9c6c9;
      /*width: 25%;*/
      font-size: 15px;
      height: 15px;;
    }
    #searchResultTable a{
      text-decoration: none;
      color: black;
    }
  </style>
</head>
<body>
<div id="searchResult" class="result">
  <table id="searchResultTable">
    <tr>
    <td class="psnname">员工姓名</td>
    <td class="corpname">就职单位</td>
    </tr>
    <c:forEach items="${psnList}" var="psn">
      <tr id='${psn.PSNCODE}' class="psncode">
        <td class="psnname"><a href="<%=path%>/CV/userSearchByCode/${psn.PSNNAME}">${psn.PSNNAME}</a></td>
        <td class="corpname"><a href="<%=path%>/CV/userSearchByCode/${psn.CORPNAME}">${psn.CORPNAME}</a></td>
      </tr>
    </c:forEach>
  </table>
</div>
</body>
</html>
