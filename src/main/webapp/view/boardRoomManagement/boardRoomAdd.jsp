<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2017-9-6
  Time: 14:36
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
    <title>编辑会议室</title>
    <link href="<%=path%>/includes/styles/boardRoomManagement/boardRoomAdd.css" rel="stylesheet">
    <link href="<%=path%>/includes/styles/common/switch.css" rel="stylesheet">
    <script type="text/javascript" src="<%=path%>/includes/scripts/common/viewport.js"></script>
    <script>
      var _config = <%= com.wxx.conference.controller.dingding.AuthHelper.getConfig(request) %>;
    </script>
    <script type="text/javascript" src="http://g.alicdn.com/dingding/open-develop/1.6.9/dingtalk.js">
    </script>
    <script type="text/javascript" src="<%=path%>/includes/scripts/boardRoomManagement/brm.js"></script>
    <script type="text/javascript">
      function choseFloor(){
        dd.biz.util.chosen({
          source:[{"key":"20","value":20},{"key":"21","value":21},{"key":"22","value":22}],
          selectedKey:"22",
          onSuccess:function(data){
            $('#floor').html(data.key);
          },
          onFail:function(data){
          }
        })
      };
    </script>
</head>
<body>
<div class="container">
  <div class="row1 list row">
    <%--<div class="boardRoomPhoto"><span>会议室头像</span></div>--%>
    <%--<div class="boardRoomName">会议室名称</div>--%>
    <ul>
      <li><a href="">会议室头像<span>请选择</span></a></li>
      <%--<li><a href="">会议室名称</a></li>--%>
      <li><span>会议室名称</span><input type="text" placeholder="*必填-不能超过10个中文字"></li>
    </ul>
  </div>
  <div class="row2 list row">
    <%--<div class="boardRoomNum">坐席人数</div>--%>
    <%--<div class="boardRoomConfig">设备配置</div>--%>
      <ul>
        <%--<li><a href="">坐席人数</a></li>--%>
        <li><span>坐席人数</span><input type="text" placeholder="*必填-请输入最大可容纳人数"></li>
        <li><a href="<%=path%>/brm/getDeviceList">设备配置<span>请选择</span></a></li>
      </ul>
  </div>
  <div class="row3 list row">
    <%--<div class="boardRoomType">选择类别</div>--%>
    <ul>
      <li>
        <a href="">选择类别<span>请选择</span></a>
      </li>
    </ul>
  </div>
  <div class="row4 list row">
    <%--<div class="boardRoomAddr">所在地址</div>--%>
    <%--<div class="boardRoomBulidNum">所在楼号</div>--%>
    <%--<div class="boardRoomFloor">所在楼层</div>--%>
    <%--<div class="boardgRoomNum">房间号</div>--%>
      <ul>
        <li><a href="<%=path%>/view/boardRoomManagement/boardRoomLocation.jsp">所在地址<span>请选择</span></a></li>
        <%--<li><a href="">所在楼号</a></li>--%>
        <li><span>所在楼号</span><input type="text"placeholder="*选填-如A座，1号楼"></li>
        <li><a href="" onclick="choseFloor()">所在楼层<span id="floor">请选择</span></a></li>
        <%--<li><a href="">房间号</a></li>--%>
        <%--<li><span>室内位置</span><input type="textarea"  cols="30" rows="5" placeholder="请输入室内会议室具体位置"></li>--%>
        <li><span>室内位置</span><textarea cols="35" rows="3" placeholder="*请输入室内会议室具体位置"></textarea></li>
      </ul>
  </div>
  <div class="row5 list row">
    <%--<div class="isApproval">是否需要审批</div>--%>
      <ul>
        <li>
          <%--<a href="">是否需要审批</a>--%>
          <span>是否需要审批</span>
          <label class="ui-switch"><input type="checkbox" checked=""></label>
        </li>
      </ul>
  </div>
  <div class="row6">
    <%--<div class="saveButton">保存</div>--%>
    <button class="saveButton">保存</button>
  </div>
</div>
</body>
</html>
