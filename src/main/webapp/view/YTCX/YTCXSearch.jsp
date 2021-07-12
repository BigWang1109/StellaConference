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
      <%--height: 100%;--%>
      <%--width: 100%;--%>
      <%--background: url("<%=path%>/includes/images/NC/bg.jpg");--%>
      <%--background-size: cover;--%>
      <%--position: absolute;--%>
      <%--overflow: hidden;--%>
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
      height: 36px;
      padding-left: 10px;
      padding-right: 10px;
      margin-bottom: 10px;
      margin-top: 6px;
      min-width: 70%;
      display: block;
      box-sizing: border-box;
      float: left;
      /*border-radius: 5px;*/
      /*background-repeat:no-repeat;*/
      /*background-position: right;*/
      border: 0.5px solid #666666;
      border-radius: 1px;
    }
    .search .btn{
      background-color:#00abc7;
      border: 0;
      color: white;
      height: 30px!important;
      min-width:28%;
      display: block!important;
      float: left;
      padding-top: 6px;
      border-radius: 5px;
    }
    #searchResult{
      text-align: center;
      height:500px;
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
      text-align: center;
      margin: 0 auto;
      width: 100%;
    }
    #searchResultTable td{
      border-width: 1px;
      border-bottom: dashed 1px #888888;
      padding: 8px;
      height: 15px;
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
      width: 100%;
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
      top: 20%;
      left: 38%;
      margin-left: -74px;
      margin-top: -14px;
      padding: 10px 10px 10px 50px;
      text-align: left;
      line-height: 27px;
      font-weight: bold;
      position: absolute;
      z-index: 2001;
    }
    .pageButton{
      text-align: center;
      text-decoration: none;
    }
  </style>
  <script type="text/javascript">
    var checkType;
    var myPageIndex = 0;
    var authcode ;
    $(document).ready(function () {
      authcode = $('#authCode').val();
      $(".psncode").on('click', function(){
        userSearchById();
      });
//      window.onpopstate = function(event){
//        alert("触发");
////        if(event.state){
//////          document.getElementById("haorooms").innerHTML = event.state.html;
//////          document.title = event.state.pageTitle;
////          alert("触发")
////        }
//      };
      window.history.pushState('forward', null, '#');
      document.onkeydown=function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if(e && e.keyCode == 13){
          cusSearch();
        }
      };
    });

    function createInvigilatorNew(data){
      var userId = $('#authCode').val();
      clearSearch();
      createTable();
      if(data.length ==0){
        $('#searchResult').append('<p style="color: #FF6A6A;font-size: 20px;font-weight: bold;">搜索无结果</p>');
      }else{
        $('#searchTitleTable').append('' +
        '<tr>' +
        '<td class="psnname">员工姓名</td>' +
        '<td class="corpname">就职单位</td>' +
        '</tr>');
        for(var i=0;i<data.length;i++){
          $('#searchResultTable').append('' +
          '<tr id='+data[i].PSNCODE+' class="psncode">' +
          '<td class="psnname"><a href="<%=path%>/YTCX/userSearchByCodeNew/'+data[i].PSNCODE+'/' + userId + '">'+data[i].PSNNAME+'</a></td>' +
          '<td class="corpname"><a href="<%=path%>/YTCX/userSearchByCodeNew/'+data[i].PSNCODE+'/' + userId + '">'+data[i].CORPNAME+'</a></td>' +
          '</tr>');
        }
      }
    }
    function userSearchById(PSNCODE){
      window.location.href = '<%=path%>/YTCX/userSearchByCodeNew/'+PSNCODE;
    }
    function cusSearch(){
      var checkValue = $('[name=phoneNumber]').val();
      checkValue = encodeURI(encodeURI(checkValue));
      if(checkValue == ''){
        $('#searchResult').empty();
        $('#searchTitle').empty();
        $('#searchResult').append('<p>请输入关键字!</p>');
      }else{
        var ajaxbg = $("#background,#progressBar");
        $.ajax({
          <%--url : '<%=path%>/YTCX/CVSearchByPage/'+checkValue+"/"+myPageIndex,--%>
          url : '<%=path%>/YTCX/CVSearchByPageTest/'+checkValue+"/"+myPageIndex+"/"+authcode,
          <%--url : '<%=path%>/YTCX/CVSearchByPageNew/'+checkValue+"/"+myPageIndex+"/"+authcode,--%>
          type : 'post',
          beforeSend:function(){
            ajaxbg.show();
          },
          success : function(data){
            ajaxbg.hide();
            createInvigilatorNew(data);
            myPageIndex = 0;
            $("#pageIndex").text(myPageIndex+1);
            $("#prePage").css("visibility","hidden");
            if(data.length < 100){
              $("#nextPage").css("visibility","hidden");
            }else{
              $("#nextPage").css("visibility","visible");
            };

          }
        })
//        }
      }
    }
    function checkTel(){
      var res = 0;
      var checkValue = $('[name=phoneNumber]').val();
      var regEx = new RegExp("[ _`~!@#$%^&()\\-+=|{}':;'\\[\\].<>/?~！@#￥%……&（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t");
      if(regEx.test(checkValue)){
        clearSearch();
        createTable();
        var str = "电话号码中含有特殊符号:"+regEx.exec(checkValue)+"，请重新输入!";
        $('#searchResult').append('<p style="color: #FF6A6A;font-size: 20px;font-weight: bold;">'+str+'</p>');
        res = 1;
      } else{
        var tels = checkValue.split(",");
        $.each(tels,function(index,j){
          if(j.length != 11){
            clearSearch();
            createTable();
            var str = "电话号码:"+j+"长度不合规，请重新输入!";
            $('#searchResult').append('<p style="color: #FF6A6A;font-size: 20px;font-weight: bold;">'+str+'</p>');
            res = 2;
          }
        })
      }
      return res;
    }
    function resetSearch(){
      $("#searchbox").val("");
      clearSearch();
      createTable();
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
    }
    function pk(pkList){

    }
    function updateIndex(flag){
      if(flag == 0){
        if(myPageIndex >0 ){
          myPageIndex--;
          $("#nextPage").css("visibility","visible");
        }
      }else if(flag == 1){
        myPageIndex++;
        $("#prePage").css("visibility","visible");
      }
      cusSearchByPage(myPageIndex);
    }
    function cusSearchByPage(myPageIndex){
      var checkValue = $('[name=phoneNumber]').val();
      checkValue = encodeURI(encodeURI(checkValue));
      if(checkValue == ''){
        $('#searchResult').empty();
        $('#searchTitle').empty();
        $('#searchResult').append('<p>请输入姓名!</p>');
      }else{
        var ajaxbg = $("#background,#progressBar");
        $.ajax({
          <%--url : '<%=path%>/YTCX/CVSearchByPage/'+checkValue+"/"+myPageIndex,--%>
          url : '<%=path%>/YTCX/CVSearchByPageTest/'+checkValue+"/"+myPageIndex+"/"+authcode,
          <%--url : '<%=path%>/YTCX/CVSearchByPageNew/'+checkValue+"/"+myPageIndex+"/"+authcode,--%>
          type : 'post',
          beforeSend:function(){
            ajaxbg.show();
          },
          success : function(data){
            ajaxbg.hide();
            createInvigilatorNew(data);
            if(data.length < 100){
              $("#pageIndex").text(myPageIndex+1);
              $("#nextPage").css("visibility","hidden");
            }else{
              $("#pageIndex").text(myPageIndex+1);
            };
            var t = $("#pageIndex").text();
            if(t == 1){
              $("#prePage").css("visibility","hidden");
            };
          }
        })
      }
    };

  </script>
</head>
<body>
<div class="top">
  <div class="title">

  </div>
</div>
<div id="mycontent">
  <div class="search" style="text-align: center;">
    <p id="maintitle" style="text-align: center;margin-bottom: 10px;font-size: 12px;">您当前可查看${count}条简历</p>
    <form action="javascript:;">
      <input name="phoneNumber" id="searchbox" placeholder="请输入关键字" type="search">
    </form>
    <a class="btn" id="searchInput" onclick="cusSearch()">查询</a>
      <div id="progressBar" class="progressBar" style="display: none; ">数据加载中，请稍等...</div>
  </div>
    <div id="resultSize" style="padding-top: 30px;text-align: center"></div>
    <div id="searchTitle">
      <table id="searchTitleTable"></table>
    </div>
  <div id="searchResult" class="result">
    <table id="searchResultTable"></table>
  </div>
    <div style="text-align: center;">
      <a id="prePage" class="pageButton" href="#" onclick="updateIndex(0)" style="visibility: hidden"><span style="padding-right: 50px;"><</span></a>第<span id="pageIndex">1</span>页
      <a id="nextPage" class="pageButton" href="#" onclick="updateIndex(1)" style="visibility: hidden"><span style="padding-left: 50px;" >></span></a>
    </div>
</div>
<input id="authCode" type="hidden" value="${authCode}"/>
<%--<input id="token" type="hidden" value="${token}"/>--%>
</body>
</html>
