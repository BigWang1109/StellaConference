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
  <title>判客查询</title>
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
      min-width: 100%;
      display: block;
      box-sizing: border-box;
      /*background-repeat:no-repeat;*/
      /*background-position: right;*/
      /*border: 0.5px solid #666666;*/
    }
    .search .btn{
      background-color:#00abc7;
      border: 0;
      color: white;
      height: 30px!important;
      min-width:35%;
      display: block!important;
      float: left;
      padding-top: 12px;
      border-radius: 15px;
    }
    #resetInput{
      background-color:#BEBEBE;
    }
    .search .btn:hover{
      background-color: #0052A3;
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
    .resTitle{
      font-size: 20px;
      font-weight: bold;
    }
    .type_chose{
      text-align: center;
      font-size: 10px;
      padding-top: 5px;
      /*color: white;*/
      height: 5px;
      /*padding-right: 17px;*/
      padding-bottom: 55px;
      font-weight: bold;
      background-color: aliceblue;
    }
    .type_chose > label > input{
      font-size: 10px;
      /*min-width:46%;*/
      /*display: block!important;*/
      /*float: left;*/
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
      width: 100%;
    }
    #searchResultTable tr{
      background-color:#d4e3e5;
      /*background-color:expression('#CC6600,#6699CC'.split(',')[rowIndex%4]);*/
      /*background-color:expression((this.sectionRowIndex % 4 == 0)?"#f00":"#ccc");*/
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
      width: 20%;
      height: 15px;
      font-size: 10px;
      word-wrap:break-word;word-break:break-all;
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
      width: 20%;
      font-size: 10px;
      height: 15px;;
    }
    #searchTitle{
      width: 100%;
      margin: 0 auto;
    }
  </style>
  <script type="text/javascript">
    var corp;
    var checkType;
    $(document).ready(function () {
      corp = $('#corp').val();
      $("#onoffswitch").on('click', function(){
        clickSwitch();
      });
      clickSwitch();
//      changeBG();
    });
    function clickSwitch(){
      if ($("#onoffswitch").is(':checked')) {
        checkType = 'tel';
        $("#maintitle").text("-<判客查询-模糊查询>-");
        console.log("模糊查询");
      } else {
        checkType = 'tel_match';
        $("#maintitle").text("-<判客查询-精确查询>-");
        console.log("精确查询");
      }
    }
    function createInvigilatorNew(data){
      clearSearch();
      createTable();
      if(data.length ==0){
        $('#searchResult').append('<p style="color: #FF6A6A;font-size: 20px;font-weight: bold;">无该手机号信息</p>');
      }else{
        for(var i=0;i<data.length;i++){
          $('#searchResultTable').append('' +
          '<tr><td colspan="4" style="background-color: #00686b;height: 20px;"></td></tr>'+
          '<tr>' +
          '<td>登记电话</td><td colspan="3" style="text-align: left">'+data[i].vpreferredtel+'</td></tr>' +
          '<tr><td>客户名称</td><td>'+data[i].vcname+'</td><td>项目编码</td><td>'+data[i].vcode+'</td></tr>' +
          '<tr><td>项目名称</td><td colspan="3" style="text-align: left">'+data[i].vname+'</td></tr>' +
          '<tr><td>最近跟进日期</td><td>'+data[i].contactdate+'</td>' +
//          '<td>'+(data[i].isFollowed==true?'是':'否')+'</td>' +
          '<td>签约状态</td><td>'+data[i].fcustype+'</td>' +
          '</tr>' +
          '');
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
<body>
<div class="top">
  <div class="title">

  </div>
</div>
<div class="body">
  <div class="search" style="text-align: center;">
    <p id="maintitle" style="text-align: center;margin-bottom: 10px;font-size: 30px;">判客查询</p>
    <input name="phoneNumber" id="searchbox" placeholder="请输入11位手机号,多个手机号用英文逗号分隔">
    <a class="btn" id="searchInput" onclick="cusSearch()">查询</a>
    <a class="btn" id="resetInput" onclick="resetSearch()">重置</a>

  </div>
  <div class="type_chose" id="type_chose">
    <div class="testswitch">
      <input class="testswitch-checkbox" id="onoffswitch" type="checkbox">
      <label class="testswitch-label" for="onoffswitch">
        <span class="testswitch-inner" data-on="模糊查询" data-off="精确查询"></span>
        <span class="testswitch-switch"></span>
      </label>
    </div>
  </div>
    <div id="searchTitle">
      <table id="searchTitleTable"></table>
    </div>
  <div id="searchResult" class="result">
    <table id="searchResultTable"></table>
  </div>
</div>
<input id="corp" type="hidden" value="${corp}"/>
</body>
</html>
