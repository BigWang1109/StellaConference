<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2021-4-27
  Time: 14:25
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
    <title>简历查询-条件查询</title>
    <jsp:include flush="true" page="/view/common/resource1.jsp"></jsp:include>
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
    <style type="text/css">
      #show_select_condition_area{
        /*height: 80px;*/
        overflow: scroll;
        white-space: nowrap;
        width: 100%;
        box-sizing: border-box;
        background-color: whitesmoke;
      }
      #condition_selected_List{
        display: inline-block;
      }
      .condition_selected{
        /*width: 80px;*/
        height: 30px;
        color: black;
        float: left;
        margin: 5px;
        font-size: 20px;
        background-color: white;
        /*display: inline-block;*/
      }
      .delBtn{
        width:18px;
        float: right;
        margin: 6px 5px 6px 15px;
      }
      .condition{
        /*width: 80px;*/
        height: 30px;
        background-color: whitesmoke;
        color: black;
        float: left;
        margin: 5px;
        padding: 3px 2px;
        font-size: 20px;
        text-align: center;
      }
      .conditionList{
        display: inline-block;
      }
      .btn_area .btn{
        background-color: #5182BB;
        border: 0;
        color: white;
        height: 30px !important;
        width: 96%;
        display: block ;
        text-align: center;
        border-radius: 5px;
        font-size: 15px;
        position: fixed;
        bottom: 20px;
        text-decoration: none;
        padding-top:10px;
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
      /*#searchTitleTable tr{*/
        /*background-color:#5182BB;*/
        /*color: white;*/
        /*font-weight: bold;*/
      /*}*/
      /*#searchTitleTable td{*/
        /*border-width: 1px;*/
        /*padding: 8px;*/
        /*border-style: solid;*/
        /*border-color: #a9c6c9;*/
        /*/!*width: 25%;*!/*/
        /*font-size: 15px;*/
        /*height: 15px;;*/
      /*}*/
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
        /*border-width: 1px;*/
        /*border-color: #999999;*/
        /*border-collapse: collapse;*/
        /*border: none;*/
        /*/!*border-bottom: 1px;*!/*/
        /*border-bottom: dashed 1px #00BFFF;*/
        text-align: center;
        margin: 0 auto;
        width: 100%;
        /*height: 600px;*/
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
        background-color:#5182BB;
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
      #cvtable{
        border-collapse: collapse;
        margin: 0 auto;
        text-align: center;
        width: 100%;
      }
      #cvtable td{
        padding: 3px;
        border: 1px solid #cad9ea;
        /*color: #666;*/
        height: 30px;
        width: 25%;
        word-wrap:break-word;word-break:break-all;
      }
      .cvname{
        color: black;
        font-weight: bold;
        font-size: 13px;
        width: 15%;
        background-color: rgb(218,235,248);
      }
      .cvvalue{
        color: black;
        font-size: 13px;
        width: 30%;
      }
      .cvtitle{
        background-color: #999999;
        color: whitesmoke;
        font-weight: bold;
        font-size: 15px;
      }
      .progress {
        height: 20px;
        background: #ebebeb;
        border-left: 1px solid transparent;
        border-right: 1px solid transparent;
        border-radius: 10px;
      }
      .progress > span {
        position: relative;
        float: left;
        margin: 0 -1px;
        min-width: 30px;
        height: 18px;
        line-height: 16px;
        text-align: right;
        background: #cccccc;
        border: 1px solid;
        border-color: #bfbfbf #b3b3b3 #9e9e9e;
        border-radius: 10px;
        background-image: -webkit-linear-gradient(top, #f0f0f0 0%, #dbdbdb 70%, #cccccc 100%);
        background-image: -moz-linear-gradient(top, #f0f0f0 0%, #dbdbdb 70%, #cccccc 100%);
        background-image: -o-linear-gradient(top, #f0f0f0 0%, #dbdbdb 70%, #cccccc 100%);
        background-image: linear-gradient(to bottom, #f0f0f0 0%, #dbdbdb 70%, #cccccc 100%);
        -webkit-box-shadow: inset 0 1px rgba(255, 255, 255, 0.3), 0 1px 2px rgba(0, 0, 0, 0.2);
        box-shadow: inset 0 1px rgba(255, 255, 255, 0.3), 0 1px 2px rgba(0, 0, 0, 0.2);
      }
      .progress > span > span {
        padding: 0 8px;
        font-size: 11px;
        font-weight: bold;
        color: #404040;
        color: rgba(0, 0, 0, 0.7);
        text-shadow: 0 1px rgba(255, 255, 255, 0.4);
      }
      .progress > span:before {
        content: '';
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        z-index: 1;
        height: 18px;
        background: url("<%=path%>/includes/images/progress.png") 0 0 repeat-x;
        border-radius: 10px;
      }
      .progress .blue {
        background: rgb(218,235,248);
        border-color: #459fd6 #3094d2 #277db2;
        /*background-image: -webkit-linear-gradient(top, #aed5ed 0%, #7bbbe2 70%, #5aaadb 100%);*/
        /*background-image: -moz-linear-gradient(top, #aed5ed 0%, #7bbbe2 70%, #5aaadb 100%);*/
        /*background-image: -o-linear-gradient(top, #aed5ed 0%, #7bbbe2 70%, #5aaadb 100%);*/
        /*background-image: linear-gradient(to bottom, #aed5ed 0%, #7bbbe2 70%, #5aaadb 100%);*/
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
      #select_condition_area{
        height: 500px;
        /*display: inline-block;*/
        overflow: auto;
      }
    </style>
  <script type="text/javascript">
    var conditionSekectedList;
    var conditionArr = [];
    var sexArr = [];
    var eduArr = [];
    var zzmmArr = [];
    var ageArr = [];
    var docArr = [];
    var lanArr = [];
    var clientHeight = document.documentElement.clientHeight;
    $(document).ready(function () {
      $('#select_condition_area').css("height",clientHeight - 80);
      $("#showSearchBtn").hide();
      $('.condition').each(function(){
//        alert($(this).height())
        if($(this).width() < 100){
          $(this).css("width","100px")
        }
      })
    });
    function delCondition(obj,type,eid){
      if(type == 'sex'){
        $(obj).parent().remove()
        var removeItem = eid
        sexArr = $.grep(sexArr,function(value){
          return value != removeItem
        })
      }
      if(type == 'edu'){
        $(obj).parent().remove()
        var removeItem = eid
        eduArr = $.grep(eduArr,function(value){
          return value != removeItem
        })
      }
      if(type == 'zzmm'){
        $(obj).parent().remove()
        var removeItem = eid
        zzmmArr = $.grep(zzmmArr,function(value){
          return value != removeItem
        })
      }
      if(type == 'age'){
        $(obj).parent().remove()
        var removeItem = eid
        ageArr = $.grep(ageArr,function(value){
          return value != removeItem
        })
      }
      if(type == 'doc'){
        $(obj).parent().remove()
        var removeItem = eid
        docArr = $.grep(docArr,function(value){
          return value != removeItem
        })
      }
      if(type == 'lan'){
        $(obj).parent().remove()
        var removeItem = eid
        lanArr = $.grep(lanArr,function(value){
          return value != removeItem
        })
      }
      resizeArea();
      setUnSelectedColor(eid);
      var node = $('#searchBtn')
//      alert(node.is(':hidden'))
      if(node.is(':hidden')){
        conditionSearch();
      }
      conditionSearchCount()
    }

    function addToSelectedList(obj,type,val){
      var eid = $(obj).attr("id")
      if(type == 'sex' && $.inArray(eid,sexArr) == -1){
        sexArr.push(eid)
        $("#condition_selected_List").append('<div class="condition_selected">'+val+'<img class="delBtn" src="<%=path%>/includes/images/icon/mul.png" onclick="delCondition(this,\'sex\',\''+eid+'\')"></div>')
      }
      if(type == 'edu' && $.inArray(eid,eduArr) == -1){
        eduArr.push(eid)
        $("#condition_selected_List").append('<div class="condition_selected">'+val+'<img class="delBtn" src="<%=path%>/includes/images/icon/mul.png" onclick="delCondition(this,\'edu\',\''+eid+'\')"></div>')
      }
      if(type == 'zzmm' && $.inArray(eid,zzmmArr) == -1){
        zzmmArr.push(eid)
        $("#condition_selected_List").append('<div class="condition_selected">'+val+'<img class="delBtn" src="<%=path%>/includes/images/icon/mul.png" onclick="delCondition(this,\'zzmm\',\''+eid+'\')"></div>')
      }
      if(type == 'age' && $.inArray(eid,ageArr) == -1){
        ageArr.push(eid)
        $("#condition_selected_List").append('<div class="condition_selected">'+val+'<img class="delBtn" src="<%=path%>/includes/images/icon/mul.png" onclick="delCondition(this,\'age\',\''+eid+'\')"></div>')
      }
      if(type == 'doc' && $.inArray(eid,docArr) == -1){
        docArr.push(eid)
        $("#condition_selected_List").append('<div class="condition_selected">'+val+'<img class="delBtn" src="<%=path%>/includes/images/icon/mul.png" onclick="delCondition(this,\'doc\',\''+eid+'\')"></div>')
      }
      if(type == 'lan' && $.inArray(eid,lanArr) == -1){
        lanArr.push(eid)
        $("#condition_selected_List").append('<div class="condition_selected">'+val+'<img class="delBtn" src="<%=path%>/includes/images/icon/mul.png" onclick="delCondition(this,\'lan\',\''+eid+'\')"></div>')
      }
      resizeArea();
      setSelectedColor(obj);
      conditionSearchCount();
    }
    function setSelectedColor(obj){
      $(obj).css("background-color","#5182BB")
      $(obj).css("color","white")
    }
    function setUnSelectedColor(eid){
//      alert($("#"+eid).html())
      $("#"+eid+"").css("background-color","whitesmoke")
      $("#"+eid+"").css("color","black")
    }
    function resizeArea(){
      var show_select_area_height = $('#show_select_condition_area').height();
//      alert(show_select_area_height);
//      if(show_select_area_height > 40){
//        $('#select_condition_area').css("height",clientHeight - 80 - show_select_area_height);
//      }
      if(show_select_area_height > 0){
        $('#select_condition_area').css("height",clientHeight - 80 - show_select_area_height);
      }else{
        $('#select_condition_area').css("height",clientHeight - 80);
      }
    }
    function cusSearch(){
      conditionArr.push(sexArr)
      conditionArr.push(eduArr)
      conditionArr.push(zzmmArr)
      conditionArr.push(docArr)
      conditionArr.push(lanArr)
      $("#select_condition_area").toggle()
      if(sexArr.length > 0 || eduArr.length > 0){
        $.ajax({
          url: '<%=path%>/CV/addConditionsToCookie',
          data: {
            'sexArr': sexArr,
            'eduArr': eduArr
          },
          type: 'post',
          success: function () {
            window.location.href = "<%=path%>/CV/conditionQueryList";
          }
        });
        <%--window.location.href = "<%=path%>/CV/conditionQuery/"+conditionArr;--%>
      }else{
        alert("请先选择条件");
      }
//      $.each(sexArr, function (k,v) {
//        alert(v)
//      })
//      $.each(eduArr, function (k,v) {
//        alert(v)
//      })
    }

    function conditionSearch(){
      $("#select_condition_area").hide();
      $("#searchBtn").hide();
      $("#showSearchBtn").show();
//      alert("触发");
      conditionArr = [];
      if(sexArr.length > 0){
        var entity = {};
        entity.type = "sex";
        entity.conditions = sexArr.join(",");
        conditionArr.push(entity)
      }
      if(eduArr.length > 0){
        var entity = {};
        entity.type = "edu";
        entity.conditions = eduArr.join(",");
        conditionArr.push(entity)
      }
      if(zzmmArr.length > 0){
        var entity = {};
        entity.type = "zzmm";
        entity.conditions = zzmmArr.join(",");
        conditionArr.push(entity)
      }
      if(ageArr.length > 0){
        var entity = {};
        entity.type = "age";
        entity.conditions = ageArr.join(",");
        conditionArr.push(entity)
      }
      if(docArr.length > 0){
        var entity = {};
        entity.type = "doc";
        entity.conditions = docArr.join(",");
        conditionArr.push(entity)
      }
      if(lanArr.length > 0){
        var entity = {};
        entity.type = "lan";
        entity.conditions = lanArr.join(",");
        conditionArr.push(entity)
      }
      conditionArr = JSON.stringify(conditionArr);
      if(conditionArr.length > 0){
        var ajaxbg = $("#background,#progressBar");
        $.ajax({
          url : '<%=path%>/CV/conditionQuery',
          data:{
            'conditionArr':conditionArr
          },
          type : 'post',
          beforeSend:function(){
            ajaxbg.show();
          },
          success : function(data){
            ajaxbg.hide();
            createInvigilatorNew(data);
            resizeTableHeight();
          }
        })
      }else{
        alert("请先选择条件");
      }
    }
    function conditionSearchCount(){
//      $("#select_condition_area").hide();
//      $("#searchBtn").hide();
//      $("#showSearchBtn").show();
//      alert("触发");
      conditionArr = [];
      if(sexArr.length > 0){
        var entity = {};
        entity.type = "sex";
        entity.conditions = sexArr.join(",");
        conditionArr.push(entity)
      }
      if(eduArr.length > 0){
        var entity = {};
        entity.type = "edu";
        entity.conditions = eduArr.join(",");
        conditionArr.push(entity)
      }
      if(zzmmArr.length > 0){
        var entity = {};
        entity.type = "zzmm";
        entity.conditions = zzmmArr.join(",");
        conditionArr.push(entity)
      }
      if(ageArr.length > 0){
        var entity = {};
        entity.type = "age";
        entity.conditions = ageArr.join(",");
        conditionArr.push(entity)
      }
      if(docArr.length > 0){
        var entity = {};
        entity.type = "doc";
        entity.conditions = docArr.join(",");
        conditionArr.push(entity)
      }
      if(lanArr.length > 0){
        var entity = {};
        entity.type = "lan";
        entity.conditions = lanArr.join(",");
        conditionArr.push(entity)
      }
      conditionArr = JSON.stringify(conditionArr);
      var height = $('#show_select_condition_area').height();
      if(height > 0){
        if(conditionArr.length > 0){
//        var ajaxbg = $("#background,#progressBar");
          $.ajax({
            url : '<%=path%>/CV/conditionQueryCount',
            data:{
              'conditionArr':conditionArr
            },
            type : 'post',
            beforeSend:function(){
//            ajaxbg.show();
            },
            success : function(data){
              $('#searchBtn').text("共"+data+"人符合条件");
            }
          })
        }
      }else{
        $('#searchBtn').text("查询");
      }
    }
    function resizeTableHeight(){
      var show_select_area_height = $('#show_select_condition_area').height();
      if(show_select_area_height > 0){
        $('#searchResult').css("height",clientHeight - 120 - show_select_area_height);
      }
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
    function createInvigilatorNew(data){
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
          '<td class="psnname"><a href="#" onclick="openCVDialog(\''+data[i].PSNCODE+'\')">'+data[i].PSNNAME+'</a></td>' +
          '<td class="corpname"><a href="#" onclick="openCVDialog(\''+data[i].PSNCODE+'\')">'+data[i].CORPNAME+'</a></td>' +
          '</tr>');
        }
//        openCVDialog();
      }
    }
    function showCondition(){
      $("#searchBtn").show()
      $("#select_condition_area").show();
      $('#showSearchBtn').hide();
      clearSearch();
    }
    function openCVDialog(psncode){
      var _topWindow;
      _topWindow = window.top.$('#topWindow');
      if (_topWindow.length <= 0){
        _topWindow = window.top.$('<div id="topWindow"/>').appendTo(window.top.document.body);
      }
      var btn;
      _topWindow.dialog({
        title: '简历查看',
        href: '<%=path%>/CV/userSearchByCode/'+psncode ,
        width: '95%',
        height: '600px',
        collapsible: false,
        minimizable: false,
        maximizable: false,
        resizable: false,
        cache: false,
        modal: true,
        closed: false,
        onClose: function () {
          _topWindow.window('destroy');
//          $('#grid').datagrid('reload');
        },
        buttons: btn
      });
    }
  </script>
</head>
<body style="overflow: hidden;height: 100%;position: fixed;width: 96%;">
<div id="show_select_condition_area">
  <div id="condition_selected_List">
  </div>
</div>
<div id="progressBar" class="progressBar" style="display: none; ">数据加载中，请稍等...</div>
<div id="select_condition_area">
  <div><h2>性别</h2></div>
  <div class="conditionList">
    <%--<div id="male" class="condition" onclick="addToSelectedList(this,'sex','男')">男</div>--%>
    <%--<div id="female" class="condition" onclick="addToSelectedList(this,'sex','女')">女</div>--%>
    <c:if test="${sexList != null && fn:length(sexList) > 0}">
      <c:forEach items="${sexList}" var="sex">
        <div id="${sex.SEX}" class="condition" onclick="addToSelectedList(this,'sex','${sex.SEX}')">${sex.SEX}</div>
      </c:forEach>
    </c:if>
  </div>
  <div><h2>最高学历</h2></div>
  <div class="conditionList">
    <%--<div id="undergraduate" class="condition" onclick="addToSelectedList(this,'edu','本科')">本科</div>--%>
    <c:if test="${eduList != null && fn:length(eduList) > 0}">
      <c:forEach items="${eduList}" var="edu">
        <div id="${edu.DEGREE}" class="condition" onclick="addToSelectedList(this,'edu','${edu.DEGREE}')">${edu.DEGREE}</div>
      </c:forEach>
    </c:if>
  </div>
  <div><h2>职称</h2></div>
  <div class="conditionList">
    <div id="人力资源管理师" class="condition" onclick="addToSelectedList(this,'doc','人力资源管理师')">人力资源管理师</div>
    <div id="注册会计师" class="condition" onclick="addToSelectedList(this,'doc','注册会计师')">注册会计师</div>
    <div id="注册税务师" class="condition" onclick="addToSelectedList(this,'doc','注册税务师')">注册税务师</div>
    <div id="审计师" class="condition" onclick="addToSelectedList(this,'doc','审计师')">审计师</div>
    <div id="经济师" class="condition" onclick="addToSelectedList(this,'doc','经济师')">经济师</div>
    <div id="注册建造师" class="condition" onclick="addToSelectedList(this,'doc','注册建造师')">注册建造师</div>
    <div id="注册造价工程师" class="condition" onclick="addToSelectedList(this,'doc','注册造价工程师')">注册造价工程师</div>
    <div id="注册监理工程师" class="condition" onclick="addToSelectedList(this,'doc','注册监理工程师')">注册监理工程师</div>
    <div id="物业管理师" class="condition" onclick="addToSelectedList(this,'doc','物业管理师')">物业管理师</div>
  </div>
  <div><h2>语言能力</h2></div>
  <div class="conditionList">
    <div id="lan0" class="condition" onclick="addToSelectedList(this,'lan','英语')">英语</div>
    <div id="lan1" class="condition" onclick="addToSelectedList(this,'lan','西班牙语')">西班牙语</div>
    <div id="lan2" class="condition" onclick="addToSelectedList(this,'lan','俄语')">俄语</div>
    <div id="lan3" class="condition" onclick="addToSelectedList(this,'lan','法语')">法语</div>
    <div id="lan4" class="condition" onclick="addToSelectedList(this,'lan','德语')">德语</div>
    <div id="lan5" class="condition" onclick="addToSelectedList(this,'lan','日语')">日语</div>
  </div>
  <div><h2>政治面貌</h2></div>
  <div class="conditionList">
    <%--<div id="undergraduate" class="condition" onclick="addToSelectedList(this,'edu','本科')">本科</div>--%>
    <c:if test="${zzmmList != null && fn:length(zzmmList) > 0}">
      <c:forEach items="${zzmmList}" var="zzmm">
        <div id="${zzmm.ZZMM}" class="condition" onclick="addToSelectedList(this,'zzmm','${zzmm.ZZMM}')">${zzmm.ZZMM}</div>
      </c:forEach>
    </c:if>
  </div>
  <div><h2>年龄</h2></div>
  <div class="conditionList">
    <div id="age1" class="condition" onclick="addToSelectedList(this,'age','25岁以下')">25岁以下</div>
    <div id="age2" class="condition" onclick="addToSelectedList(this,'age','25至35岁')">25至35岁</div>
    <div id="age3" class="condition" onclick="addToSelectedList(this,'age','35至45岁')">35至45岁</div>
    <div id="age4" class="condition" onclick="addToSelectedList(this,'age','45至55岁')">45至55岁</div>
    <div id="age5" class="condition" onclick="addToSelectedList(this,'age','55岁以上')">55岁以上</div>
  </div>
</div>
<div id="condition_query_list">
  <div id="searchTitle">
    <table id="searchTitleTable"></table>
  </div>
  <div id="searchResult" class="result">
    <table id="searchResultTable"></table>
  </div>
</div>
<div class="btn_area">
  <a class="btn" id="searchBtn" onclick="conditionSearch()">查询</a>
  <a class="btn" id="showSearchBtn" onclick="showCondition()">显示全部查询条件</a>
</div>
</body>
</html>
