<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2020-5-29
  Time: 16:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
%>
<html>
<head>
  <title>客户查询</title>
  <jsp:include flush="true" page="/view/common/resource1.jsp"></jsp:include>
  <style type="text/css">
    body{
      /*background:#EEEEEE;*/
      height: 100%;
      width: 100%;
      background: url("<%=path%>/includes/images/NC/bg.jpg");
      background-size: cover;
      position: absolute;
      overflow: hidden;
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
      width: 520px;
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
      height:400px;
      width:1200px;
      overflow:auto;
      margin: 0 auto;
    }
    /*#searchResult > table > input{*/
      /*width: 250px;*/
      /*height: 36px;*/
      /*background-repeat:no-repeat;*/
      /*background-position: right;*/
      /*border: 0.5px solid #666666;*/
      /*text-align: center;*/
      /*font-size: 15px;*/
      /*border-radius: 3px;*/
    /*}*/
    /*#searchTitle > table >input{*/
      /*width: 250px;*/
      /*height: 36px;*/
      /*background-repeat:no-repeat;*/
      /*background-position: right;*/
      /*border: 0.5px solid #666666;*/
      /*text-align: center;*/
      /*background-color:#00abc7;*/
      /*color: white;*/
      /*border-radius: 3px;*/
      /*/!*font-size: 15px;*!/*/
    /*}*/
    #searchResult > p{
      color: white;
    }
    .resTitle{
      font-size: 20px;
      font-weight: bold;
    }
    .type_chose{
      text-align: center;
      font-size: 10px;
      padding-top: 10px;
      color: white;
      height: 5px;
      padding-right: 17px;
      padding-bottom: 55px;
      font-weight: bold;
    }
    .type_chose > label > input{
      font-size: 10px;

    }
    #searchResultTable{
      font-family: verdana,arial,sans-serif;
      font-size:11px;
      color:#333333;
      border-width: 1px;
      border-color: #999999;
      border-collapse: collapse;
      text-align: center;
      margin: 0 auto;
    }
    #searchResultTable tr{
      background-color:#d4e3e5;
    }
    #searchResultTable tr:hover{
      background-color:#00BFFF;
      color: white;
    }
    #searchResultTable td{
      border-width: 1px;
      padding: 8px;
      border-style: solid;
      border-color: #a9c6c9;
      width: 150px;
      height: 45px;
      font-size: 15px;
    }
    #searchTitleTable{
      font-family: verdana,arial,sans-serif;
      font-size:15px;
      color:#333333;
      border-width: 1px;
      border-color: #999999;
      border-collapse: collapse;
      margin: 0 auto;
      text-align: center;
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
      width: 150px;
      font-size: 18px;
    }
  </style>
  <script type="text/javascript">
    var corp;
    $(document).ready(function () {
      corp = $('#corp').val();
      changeBG();
    });
    function createInvigilator(data){
      $('#searchResult').empty();
      $('#searchTitle').empty();
      if(data.length ==0){
        $('#searchTitle').append('<p>无该手机号信息</p>');
      }else{
//        $('#searchTitle').append('<p>查询结果</p>');
        $('#searchTitle').append('' +
        '<input class="resTitle" value="电话">' +
        '<input class="resTitle" value="客户名称">' +
        '<input class="resTitle" value="项目编码">' +
        '<input class="resTitle" value="项目名称">' +
        '<input class="resTitle" value="最近跟进日期">' +
        '<input class="resTitle" value="近一个月内是否有跟进">' +
        '</br>');
        if(data.length < 9){
          var obj = document.getElementById('type_chose');
          obj.style.paddingRight = '0px';
        }else{
          var obj = document.getElementById('type_chose');
          obj.style.paddingRight = '17px';
        }
        for(var i=0;i<data.length;i++){
          $('#searchResult').append('' +
          '<input class="ids"  value="'+data[i].vpreferredtel+'">' +
          '<input  class="resValue" name="invicheck" value="'+data[i].vcname+'">' +
          '<input  class="resValue" name="invicheck" value="'+data[i].vcode+'">' +
          '<input  class="resValue" name="invicheck" value="'+data[i].vname+'">' +
          '<input  class="resValue" name="invicheck" value="'+data[i].contactdate+'">' +
          '<input  class="resValue" name="invicheck" value="'+(data[i].isFollowed==true?'是':'否')+'">' +
          '</br>');
        }
      }
    }
    function createInvigilatorNew(data){
      clearSearch();
      createTable();
      if(data.length ==0){
        $('#searchResult').append('<p style="color: #FF6A6A;font-size: 20px;font-weight: bold;">无该手机号信息</p>');
      }else{
        $('#searchTitleTable').append('' +
        '<tr>' +
        '<td>登记电话</td>' +
        '<td>客户名称</td>' +
        '<td>项目编码</td>' +
        '<td>项目名称</td>' +
        '<td>最近跟进日期</td>' +
        '<td>是否一月内跟进</td>' +
        '<td>签约状态</td>' +
        '</tr>');
        if(data.length < 9){
          var obj = document.getElementById('type_chose');
          obj.style.paddingRight = '0px';
        }else{
          var obj = document.getElementById('type_chose');
          obj.style.paddingRight = '17px';
        }
        for(var i=0;i<data.length;i++){
          $('#searchResultTable').append('' +
//          '<input class="ids"  value="'+data[i].vpreferredtel+'">' +
          '<tr>' +
          '<td>'+data[i].vpreferredtel+'</td>' +
          '<td>'+data[i].vcname+'</td>' +
          '<td>'+data[i].vcode+'</td>' +
          '<td>'+data[i].vname+'</td>' +
          '<td>'+data[i].contactdate+'</td>' +
          '<td>'+(data[i].isFollowed==true?'是':'否')+'</td>' +
          '<td>'+data[i].fcustype+'</td>' +
          '</tr>');
        }
      }
    }
    function cusSearch(){
      var checkValue = $('[name=phoneNumber]').val();
      if(checkValue == ''){
        $('#searchResult').empty();
        $('#searchTitle').empty();
        $('#searchResult').append('<p>请输入手机号!</p>');
      }else{
        var checkType = $('input[name="check_type"]:checked').val();
        var corp = $('#corp').val();
        if(checkTel() == 0){
          $.post('<%=path%>/nc/searchCustomer/'+checkValue+'/'+checkType+'/'+corp,
                  function(data){
                    createInvigilatorNew(data);
                  }
          );
        }
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
    }
    function createTable(){
      $('#searchTitle').append('<table id="searchTitleTable"></table>');
      $('#searchResult').append('<table id="searchResultTable"></table>');
    }
    function changeBG(){
      if(corp == 'wh'){
        $("body").css({
          "background":"url('<%=path%>/includes/images/NC/bg.jpg')",
          "height":"100%",
          "width":"100%",
          "background-size":"cover",
          "position":"absolute",
          "overflow":"hidden"
        });
      }else if(corp == 'bj'){
        $("body").css({
          "background":"url('<%=path%>/includes/images/NC/bj.jpg')",
          "height":"100%",
          "width":"100%",
          "background-size":"cover",
          "position":"absolute",
          "overflow":"hidden"
        });
      }

    }
  </script>
</head>
<div class="top">
  <div class="title">

  </div>
</div>
<div class="body">
  <%--<div class="pic">--%>
    <%--<img class="logo" src="<%=path%>/includes/images/logo/logo.png"/>--%>
  <%--</div>--%>

  <div class="search" style="text-align: center;padding-top: 155px;">
      <p style="text-align: center;margin-bottom: 10px;font-size: 30px;color: white">判客查询</p>
      <%--<label for="search"></label>--%>
      <input name="phoneNumber" id="searchbox" placeholder="请输入11位手机号,多个手机号用英文逗号分隔">
      <%--<input class="input" type="text" name="phoneNumber" id="search" placeholder="请输入11位手机号,多个手机号用英文逗号分隔" value="查询" />--%>
      <input class="btn" type="button" value="查询" name="submit" id="searchInput" onclick="cusSearch()"/>
      <input class="btn" type="button" value="重置" name="submit" id="resetInput" onclick="resetSearch()"/>

  </div>
  <div class="type_chose" id="type_chose">
    <label><input type="radio" name="check_type" value="tel_match" checked="checked">精确匹配</label>
    <label><input type="radio" name="check_type" value="tel">模糊匹配</label>
    <div id="searchTitle">
      <table id="searchTitleTable"></table>
    </div>
  </div>
  <div id="searchResult" class="result">
    <table id="searchResultTable"></table>
  </div>
</div>
<input id="corp" type="hidden" value="${corp}"/>
</body>
</html>
