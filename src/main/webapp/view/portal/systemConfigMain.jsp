<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2019-11-15
  Time: 16:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
%>
<html>
<head>
    <title>单点登录系统管理</title>
    <jsp:include flush="true" page="/view/common/resource1.jsp"></jsp:include>
    <script type="text/javascript">
      $(document).ready(function () {
        $('#grid').datagrid({
          striped: false,
          fitColumns: true,
          resizable: true,
          singleSelect: false,
          url: '<%=path%>/portal/loadSystem',
          idField: 'ID',
          sortName: 'CODE',
          sortOrder: 'asc',
          remoteSort: true,
          pagination: true,
          pageNumber: 1,
          pageSize: 10,
          pageList: [10, 20, 50, 100],
          nowrap: false,
//        loadMsg: constant.Loading,
          columns: [[
            { field: 'id', hidden : true},
            { field: 'ck', checkbox: true},
            { field: 'index', title: '序号', width: '5%',
              formatter: function (value, row, index) {
                return index+1;
              },align:'center'
            },
            { field: 'name', title: '系统名称', align: 'center',width: '60%',
              formatter: function (value, row, index) {
//              return '<a href="#" class="testcontent" onclick="addTextInput(\''+ row.taskId + '\', 0)">'+value+'</a>';
                return value;
              }
            },
            { field: 'url', title: '操作', align: 'center',width: '30%',
              formatter: function (value, row, index) {
//                return '<a href="#" class="testcontent" onclick="systemAuth(\''+row.id+'\')">系统设置</a>|<a href="#" class="testcontent">授权设置</a>';
                return '<a href="#" class="testcontent" onclick="systemAuth(\''+ row.id + '\')">授权设置</a>';
              }
            }
          ]]
//        onBeforeLoad: function (param) {
//
//          attachParams(param);
//
//        },
//        onLoadSuccess: function (data) {
//          var rowData = data.rows;
//          $.each(rowData, function(index, row){
//            if (row.LoginName == 'admin') {
//              $("#grid").datagrid("checkRow", index);
//              $("#grid").datagrid('getPanel').find("input[type='checkbox']")[index + 1].disabled = true;
//            }
//          });
//        }
        });
      });
      function systemAuth(id){
        alert("123");
        var _topWindow;
        _topWindow = window.top.$('#topWindow');
        if (_topWindow.length <= 0){
          _topWindow = window.top.$('<div id="topWindow"/>').appendTo(window.top.document.body);
        }

        _topWindow.dialog({
          title: '授权设置',
          href: '<%=path%>/portal/getAuthUnit/'+id,
          width: 1000,
          height: 500,
          collapsible: false,
          minimizable: false,
          maximizable: false,
          resizable: false,
          cache: false,
          modal: true,
          closed: false,
          onClose: function () {
            _topWindow.window('destroy');
          }
        });
      };
    </script>
</head>
<body>
<div class="system_list">
  <div class="easyui-panel" title="单点登录系统列表" style="width: 100%;" data-options="region:'center'" >
    <form id = "form-search">
      <table class="table-search">
        <div style="padding-top: 5px;padding-left: 10px;padding-bottom: 5px;" >
          <span >系统名称：</span>
          <input class="easyui-textbox" name="code"  data-options="validType: 'length[1,2000]'">
          <span style="padding-left: 10px;">&nbsp;</span>
          <a class="easyui-linkbutton easyui-linkbutton-primary" href="javascript:void(0);" onclick="getMemberByDepartment('-1013665952206207158')">查询</a>
        </div>
      </table>
    </form>
    <div style="padding-top: 0px;padding-left: 6px;margin-bottom: 0px;padding-right:6px;">
      <table id="grid"></table>
    </div>
    <div style="text-align:center; padding-bottom:5px;">
      <a href="javascript:void(0)" style="margin-top: 5px;"align="center" class="easyui-linkbutton easyui-linkbutton-primary" onClick="systemAuth('1')">添加系统</a>
      <a href="javascript:void(0)" style="margin-top: 5px;" class="easyui-linkbutton easyui-linkbutton-primary" onClick="deleteTasks()">批量删除</a>
      <%--<a href="javascript:void(0)" style="margin-top: 5px;"align="center" class="easyui-linkbutton easyui-linkbutton-primary" onClick="addTextInput('-1',3)">自动分配IP</a>--%>
    </div>
  </div>
</div>

</body>
</html>
