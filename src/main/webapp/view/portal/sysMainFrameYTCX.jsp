<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2021-6-3
  Time: 13:26
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
%>
<HTML>
<HEAD>
  <TITLE> 简历查询系统 </TITLE>
  <%--<meta http-equiv="content-type" content="text/html; charset=UTF-8">--%>
  <link href="<%=path%>/includes/styles/zTree/YTCX.css" rel="stylesheet">
  <link href="<%=path%>/includes/styles/zTree/zTreeStyle/zTreeStyle.css" rel="stylesheet">
  <link href="<%=path%>/includes/styles/portal/systemMainFrame.css" rel="stylesheet">
  <script type="text/javascript" src="<%=path%>/includes/scripts/zTree/jquery-1.4.4.min.js"></script>
  <script type="text/javascript" src="<%=path%>/includes/scripts/zTree/jquery.ztree.core.js"></script>
  <script type="text/javascript" src="<%=path%>/includes/scripts/zTree/jquery.ztree.excheck.js"></script>
  <link href="<%=path%>/includes/jquery-easyui-1.4.4/themes/bootstrap/easyui.css" rel="stylesheet">
  <link href="<%=path%>/includes/jquery-easyui-1.4.4/themes/icon.css" rel="stylesheet">
  <script type="text/javascript" src="<%=path%>/includes/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
  <script type="text/javascript" src="<%=path%>/includes/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>
  <%--<jsp:include flush="true" page="/view/common/resource1.jsp"></jsp:include>--%>
  <SCRIPT LANGUAGE="JavaScript">
    var zTreeObj;
    // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
    var setting = {
      callback:{
        onClick:getNodeByClick
      }

    };
    // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
    var zNodes = [
      {id:"0",name:"权限管理", open:true}
//      {id:"2",name:"管理员设置", open:true}
    ];
    var sNodes = [
      {id:"0",name:"权限管理", open:true},
      {id:"1",name:"日志查询", open:true}
    ];
    $(document).ready(function(){
      var iden = $('#iden').val();
//      alert(iden);
      if(iden == 'admin'){
        zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
      }else if(iden == 'system'){
        zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, sNodes);
      }
    });
    function getNodeByClick(event,treeId,treeNode){
      var id = treeNode.id;
      if(id == 0){
        $("#fmb").empty();
        var targetHtml = '<iframe src="<%=path%>/YTCX/authorityManage" width="100%" height="100%" frameborder="1" scrolling="no"></iframe>';
        $("#fmb").append(targetHtml);
      }else if(id == 1){
        $("#fmb").empty();
        var targetHtml = '<iframe src="<%=path%>/YTCX/turnToLogQuery" width="100%" height="100%" frameborder="1" scrolling="no"></iframe>';
        $("#fmb").append(targetHtml);
      }
    }
    function logout(){
      window.location.href = "<%=path%>/YTCX/login";
    }
  </SCRIPT>
</HEAD>
<BODY>
<div class="frame_container">
  <div class="frame_header">
    <div class="frame_header-left">
      <img class="frame_logo" src="<%=path%>/includes/images/portal/logo/fhkg_logo.png"><span class="frame_title">简历查询系统管理后台</span>
    </div>
    <div class="frame_header-right">
      <span class="frame_account"> ${orgMember.NAME}</span>
      <button class="frame_quit_btn" onclick="logout()">退出</button>
    </div>
  </div>
  <div class="frame_main">
    <div class="frame_left_menu">
      <div>
        <ul id="treeDemo" class="ztree"></ul>
      </div>
    </div>
    <div class="frame_main_body" id="fmb">
      <iframe src="<%=path%>/YTCX/authorityManage" width="100%" height="100%" frameborder="1" scrolling="no"></iframe>
    </div>
  </div>
</div>
<input id="iden" type="hidden" value="${iden}"/>
</BODY>
</HTML>
