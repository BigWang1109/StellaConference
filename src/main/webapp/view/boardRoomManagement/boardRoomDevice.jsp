<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2017-9-8
  Time: 9:40
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
    <title>会议室设备</title>
    <jsp:include flush="true" page="/view/common/jqueryResource.jsp"></jsp:include>
    <link href="<%=path%>/includes/styles/boardRoomManagement/boardRoomDevice.css" rel="stylesheet">
    <link href="<%=path%>/includes/styles/common/dialog.css" rel="stylesheet">
    <script type="text/javascript" src="<%=path%>/includes/scripts/common/viewport.js"></script>
    <script type="text/javascript">
        function editDevice(deviceId){
            var _topWindow;
            _topWindow = window.top.$('#topWindow');
            if (_topWindow.length <= 0){
                _topWindow = window.top.$('<div id="topWindow"/>').appendTo(window.top.document.body);
            }
            var btn;
                btn = [
                    {
                        text: '保存',
                        handler: function () {
                            var deviceName = encodeURI(encodeURI($('#deviceName').val()));
                            if(deviceId!='' && deviceId!=null){
                                $.ajax({
                                    url:'<%=path%>/brm/editDevice/'+deviceId+'/'+deviceName+'',
                                    type:'post',
                                    dataType: "json",
                                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                                    success:function(data){
                                        if(data.flag == true){
                                            _topWindow.dialog('close');
                                            window.location.reload();
                                        }else{
                                        }
                                    }
                                })
                            }
                        }
                    },
                    {
                        text: '取消',
                        handler: function () {
                            _topWindow.dialog('close');
                        }
                    }];
            _topWindow.dialog({
                title: '编辑设备',
                href: '<%=path%>/view/boardRoomManagement/deviceAdd.jsp' ,
                width:500,
                height: 210,
                collapsible: false,
                minimizable: false,
                maximizable: false,
                resizable: false,
                cache: false,
                modal: true,
                closed: false,
                onClose: function () {
                    _topWindow.window('destroy');
                },
                buttons: btn
            });
        };
        function addDevice(){
            var _topWindow;
            _topWindow = window.top.$('#topWindow');
            if (_topWindow.length <= 0){
                _topWindow = window.top.$('<div id="topWindow"/>').appendTo(window.top.document.body);
            }
            var btn;
            btn = [
                {
                    text: '保存',
                    handler: function () {
                        var deviceName = encodeURI(encodeURI($('#deviceName').val()));
                            $.ajax({
                                url:'<%=path%>/brm/addDevice/'+deviceName+'',
                                type:'post',
                                dataType: "json",
                                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                                success:function(data){
                                    if(data.flag == true){
                                        _topWindow.dialog('close');
                                        window.location.reload();
                                    }else{
                                    }
                                }
                            })
                    }
                },
                {
                    text: '取消',
                    handler: function () {
                        _topWindow.dialog('close');
                    }
                }];
            _topWindow.dialog({
                title: '添加设备',
                href: '<%=path%>/view/boardRoomManagement/deviceAdd.jsp' ,
                width:500,
                height: 210,
                collapsible: false,
                minimizable: false,
                maximizable: false,
                resizable: false,
                cache: false,
                modal: true,
                closed: false,
                onClose: function () {
                    _topWindow.window('destroy');
                },
                buttons: btn
            });
        }
        function delDevice(deviceId,deviceName){
            var _topWindow;
            _topWindow = window.top.$('#topWindow');
            if (_topWindow.length <= 0){
                _topWindow = window.top.$('<div id="topWindow"/>').appendTo(window.top.document.body);
            }
            var btn;
            btn = [
                {
                    text: '确认删除',
                    handler: function () {
                        if(deviceId!='' && deviceId!=null){
                            $.ajax({
                                url:'<%=path%>/brm/delDevice/'+deviceId+'',
                                type:'post',
                                dataType: "json",
                                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                                success:function(data){
                                    if(data.flag == true){
                                        _topWindow.dialog('close');
                                        window.location.reload();
                                    }else{
                                    }
                                }
                            })
                        }
                    }
                },
                {
                    text: '取消',
                    handler: function () {
                        _topWindow.dialog('close');
                    }
                }];
            _topWindow.dialog({
                title: '删除设备',
                <%--href: '<%=path%>/view/boardRoomManagement/deviceDelete.jsp?deviceName='+deviceName+'' ,--%>
                href: '<%=path%>/brm/convert/'+deviceId+'' ,
                width:500,
                height: 210,
                collapsible: false,
                minimizable: false,
                maximizable: false,
                resizable: false,
                cache: false,
                modal: true,
                closed: false,
                onClose: function () {
                    _topWindow.window('destroy');
                },
                buttons: btn
            });
        }
    </script>
</head>
<body>
<div class="container">
  <ul>
    <c:forEach var="device" items="${deviceList}">
      <li>
        <input type="checkbox" id="${device.deviceId}" style="zoom:300%;">
        <span>${device.deviceName}</span>
        <button class="sbutton delButton" onclick="delDevice('${device.deviceId}','${device.deviceName}')">删除</button>
        <button class="sbutton editButton" onclick="editDevice('${device.deviceId}')">编辑</button>
      </li>
    </c:forEach>
    <li><a href="#" onclick="addDevice()">添加设备</a></li>
  </ul>
  <div class="button-group">
    <button class="bbutton confirmButton">确认</button>
    <button class="bbutton cancelButton">取消</button>
  </div>
</div>
</body>
</html>
