<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2020-5-29
  Time: 16:26
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
<html lang="zh-CN">
<head>
  <%--<meta charset="UTF-8" name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>--%>
  <title>简历查询</title>
  <jsp:include flush="true" page="/view/common/resource1.jsp"></jsp:include>
  <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
  <style type="text/css">
    body{
      height: 100%;
      width: 100%;
      background: url("<%=path%>/includes/images/NC/bg.jpg");
      background-size: cover;
      position: absolute;
      overflow: hidden;
      background-color: aliceblue;
    }
    .pic{
      text-align: center;
      margin-bottom: 20px;
      transform:scale(0.3);
    }
    .search{
      text-align: center;
    }
    #searchbox{
      width: 50%;
      height: 36px;
      background-repeat:no-repeat;
      background-position: right;
      border: 0.5px solid #666666;
    }
    .search .btn{
      background-color:#00abc7;
      border: 0;
      color: white;
      height: 36px;
      width:100px;
    }
    #resetInput{
      background-color:#BEBEBE;
    }
    .search .btn:hover{
      background-color: #0052A3;
    }
    #searchResult{
      text-align: center;
      height:50%;
      /*width:100%;*/
      width:65%;
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
      border-collapse: collapse;
      /*border: none;*/
      /*/!*border-bottom: 1px;*!/*/
      /*border-bottom: dashed 1px #00BFFF;*/
      text-align: center;
      margin: 0 auto;
      width: 100%;
      /*width: 50%;*/
    }
    #searchResultTable tr{
      background-color:#d4e3e5;
      /*border-bottom: 1px;*/
      /*border-bottom: dashed 1px #00BFFF;*/
    }
    #searchResultTable tr:hover{
      background-color:#00BFFF;
      color: white;
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
    #searchTitleTable{
      font-family: verdana,arial,sans-serif;
      font-size:11px;
      color:#333333;
      border-width: 1px;
      border-color: #999999;
      border-collapse: collapse;
      margin: 0 auto;
      text-align: center;
      width: 100%;
      /*width:50%;*/
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
    #searchTitle{
      /*width: 100%;*/
      width: 65%;
      margin: 0 auto;
    }
    #searchResultTable a{
      text-decoration: none;
      color: black;
    }
    .progressBar {
      border: solid 2px #86A5AD;
      background: white url(<%=path%>/includes/images/progressBar_m.gif) no-repeat 10px 10px;
    }
    .progressBar {
      display: block;
      width: 50%;
      /*height: 20px;*/
      position: fixed;
      top: 35%;
      left: 28%;
      margin-left: -74px;
      margin-top: -14px;
      padding: 10px 10px 10px 50px;
      text-align: left;
      line-height: 27px;
      font-weight: bold;
      position: absolute;
      z-index: 2001;
    }
  </style>
  <script type="text/javascript">
    var corp;
    var checkType;
    $(document).ready(function () {
      corp = $('#corp').val();
//      changeBG();
      $(".psncode").on('click', function(){
        userSearchById();
      });
      document.onkeydown=function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if(e && e.keyCode == 13){
          cusSearch();
        }
      }
    });
    function createInvigilatorNew(data){
      clearSearch();
      createTable();
      if(data.length ==0){
        $('#searchResult').append('<p style="color: #FF6A6A;font-size: 20px;font-weight: bold;">搜索无结果</p>');
      }else{
        if(data.length == 100){
          $('#resultSize').append('<p>符合条件的简历过多，仅显示前100条</p>');
        }else{
          $('#resultSize').append('<p>共匹配到'+data.length+'条简历</p>');
        }
        $('#searchTitleTable').append('' +
        '<tr>' +
        '<td class="psnname">员工姓名</td>' +
        '<td class="corpname">就职单位</td>' +
        '</tr>');
        for(var i=0;i<data.length;i++){
          var psncode = data[i].PSNCODE;
          $('#searchResultTable').append('' +
//          '<tr id='+data[i].PSNCODE+' onclick='+userSearchById(data[i].PSNCODE)+'>' +
          '<tr id='+data[i].PSNCODE+' class="psncode">' +
          <%--'<td class="psnname"><a href="<%=path%>/CV/userSearchByCodePC/'+data[i].PSNCODE+'">'+data[i].PSNNAME+'</a></td>' +--%>
          <%--'<td class="corpname"><a href="<%=path%>/CV/userSearchByCodePC/'+data[i].PSNCODE+'">'+data[i].CORPNAME+'</a></td>' +--%>
          '<td class="psnname"><a href="#" onclick="ShowCVInfo(\''+data[i].PSNCODE+'\')">'+data[i].PSNNAME+'</a></td>' +
          '<td class="corpname"><a href="#" onclick="ShowCVInfo(\''+data[i].PSNCODE+'\')">'+data[i].CORPNAME+'</a></td>' +

          '</tr>');
//          $('#searchResultTable').append('' +
//          '<tr><td>' +
//          '<a onclick="userSearchById(\''+data[i].PSNCODE+'\')"><p class="psnname">'+data[i].PSNNAME+'</p><p class="corpname">'+data[i].CORPNAME+'</p></a>' +
//          '</td></tr>' +
//          '');
        }
      }
    }
    function userSearchById(PSNCODE){
      window.location.href = '<%=path%>/CV/userSearchByCode/'+PSNCODE;
    }
    function cusSearch(){
      var checkValue = $('[name=phoneNumber]').val();
      checkValue = encodeURI(encodeURI(checkValue));
      if(checkValue == ''){
        $('#searchResult').empty();
        $('#searchTitle').empty();
        $('#searchResult').append('<p>请输入姓名!</p>');
      }else{
        var corp = $('#corp').val();
        var ajaxbg = $("#background,#progressBar");
//        if(checkTel() == 0){
        <%--$.post('<%=path%>/CV/CVSearch/'+checkValue,--%>
        <%--function(data){--%>
        <%--createInvigilatorNew(data);--%>
        <%--}--%>
        <%--);--%>
        $.ajax({
          url : '<%=path%>/CV/CVSearch/'+checkValue,
          type : 'post',
          beforeSend:function(){
            ajaxbg.show();
          },
          success : function(data){
            ajaxbg.hide();
            createInvigilatorNew(data);
          }
        })
//        }
      }
    }
    function resetSearch(){
      $("#searchbox").val("");
      clearSearch();
      createTable();
      clearShowArea();
      $("#searchArea").css({
        "width":"100%"
      });
      $("#searchResult").css({
        "width":"65%"
      });
      $("#searchTitle").css({
        "width":"65%"
      });
    }
    function clearShowArea(){
      $("#showArea").empty();
    }
    function clearSearch(){
      $('#searchResult').empty();
      $('#searchTitle').empty();
      $('#resultSize').empty();
    }
    function createTable(){
      $('#searchTitle').append('<table id="searchTitleTable"></table>');
      $('#searchResult').append('<table id="searchResultTable"></table>');
    }
    function showUpdateLog(){
      $("#updateLogs").toggle();
    };
    function ShowCVInfo(PSNCODE) {
      var ajaxbg = $("#background,#progressBar");
      $("#searchArea").css({
        "width":"48%",
        "float":"left"
      });
      $("#searchResult").css({
        "width":"100%"
      });
      $("#searchTitle").css({
        "width":"100%"
      })
      $("#showArea").css({
        "width":"50%",
        "float":"left",
        "margin-top":"20px"
      });
      $("#showArea").empty();
      ajaxbg.show();
      var targetHtml = '<iframe src="<%=path%>/CV/userSearchByCodePC/'+PSNCODE+'" width="100%" height="90%" frameborder="0" scrolling="yes" onload="iframeLoaded(this)"></iframe>';
      <%--<!--var targetHtml = '<iframe src="<%=path%>/CV/userSearchByCodePC/'+PSNCODE+'" width="100%" height="90%" scrolling="yes"></iframe>';-->--%>
      $("#showArea").append(targetHtml);
    }
    function iframeLoaded(){
      var ajaxbg = $("#background,#progressBar");
      ajaxbg.hide();
    }
  </script>
</head>
<body>
<div class="top">
  <div class="title">
  </div>
</div>
<div class="body">
  <%--<div id="searchArea" style="width: 48%;float: left;">--%>
  <div id="searchArea" style="width: 100%;">
    <div class="search" style="text-align: center;">
      <p id="maintitle" style="text-align: center;margin-bottom: 10px;margin-top: 90px;font-size: 12px;">当前简历库中共${count}条简历</p>

      <form action="javascript:;">
        <input name="phoneNumber" id="searchbox" placeholder="请输入关键字" type="search">
        <input class="btn" type="button" value="查询" name="submit" id="searchInput" onclick="cusSearch()"/>
        <input class="btn" type="button" value="重置" name="submit" id="resetInput" onclick="resetSearch()"/>
      </form>
      <div id="progressBar" class="progressBar" style="display: none; ">数据加载中，请稍等...</div>
      <%--<div id="progressBar" class="progressBar" >数据加载中，请稍等...</div>--%>
    </div>
    <div id="resultSize" style="text-align: center"></div>
    <div id="searchTitle">
      <table id="searchTitleTable"></table>
    </div>
    <div id="searchResult" class="result">
      <table id="searchResultTable"></table>
    </div>
  </div>
  <%--<div id="showArea"style="width: 50%;float: left;margin-top: 20px;">--%>
  <div id="showArea">
    <%--<iframe src="<%=path%>/portal/authorityManage" width="100%" height="100%" frameborder="1" scrolling="no"></iframe>--%>
  </div>
</div>
</body>
</html>
