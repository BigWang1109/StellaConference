<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2019-11-12
  Time: 10:30
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
%>
<HTML>
<HEAD>
  <TITLE> 泛海门户 </TITLE>
  <%--<meta http-equiv="content-type" content="text/html; charset=UTF-8">--%>
  <link href="<%=path%>/includes/styles/zTree/demo.css" rel="stylesheet">
  <link href="<%=path%>/includes/styles/zTree/zTreeStyle/zTreeStyle.css" rel="stylesheet">
  <link href="<%=path%>/includes/styles/portal/systemmain.css" rel="stylesheet">
  <script type="text/javascript" src="<%=path%>/includes/scripts/zTree/jquery-1.4.4.min.js"></script>
  <script type="text/javascript" src="<%=path%>/includes/scripts/zTree/jquery.ztree.core.js"></script>
  <link href="<%=path%>/includes/jquery-easyui-1.4.4/themes/bootstrap/easyui.css" rel="stylesheet">
  <link href="<%=path%>/includes/jquery-easyui-1.4.4/themes/icon.css" rel="stylesheet">
  <script type="text/javascript" src="<%=path%>/includes/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
  <script type="text/javascript" src="<%=path%>/includes/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>
  <%--<jsp:include flush="true" page="/view/common/resource1.jsp"></jsp:include>--%>
  <SCRIPT LANGUAGE="JavaScript">
    var nodes = ${nodes};
    <%--var ztreeNode = nodes.substring(1,'${nodes}'.length-1);--%>
    function logout(){
      window.location.href = "<%=path%>/portal/login";
    }
    function getNodeByClick(event,treeId,treeNode){
      var type = treeNode.type;
      if(type == 'Department'){
        getMemberByDepartment(treeNode.id)
      }
    }
    function getMemberByDepartment(ORG_DEPARTMENT_ID){
      $('#grid').datagrid({
        striped: false,
        fitColumns: true,
        resizable: true,
        singleSelect: false,
        url: '<%=path%>/portal/loadOrgMember/'+ORG_DEPARTMENT_ID+'',
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

          { field: 'code', title: '员工编号', width: '30%' ,align:'center',
            formatter:function(value, row, index){
//              return '<a href="#" class="testcontent" onclick="addTextInput(\''+ row.taskId + '\', 1)">'+value+'</a>';
              return value;
            }
          },
          { field: 'name', title: '姓名', align: 'center',width: '30%',
            formatter: function (value, row, index) {
//              return '<a href="#" class="testcontent" onclick="addTextInput(\''+ row.taskId + '\', 0)">'+value+'</a>';
              return value;
            }
          },

          { field: 'type', title: '操作', align: 'center',width: '30%',
            formatter: function (value, row, index) {
              return '<a href="#" class="testcontent">设置权限</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="#">重置密码</a>';
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
    }
    var zTreeObj;
    // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
    var setting = {
      callback:{
        onClick:getNodeByClick
      }
    };
    // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
    var zNodes = [
      nodes
    ];
    $(document).ready(function(){
      zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);

    });
  </SCRIPT>
</HEAD>
<BODY>
<div class="container">
  <div class="header">
    <div class="header-left">
      <img class="logo" src="<%=path%>/includes/images/portal/logo/fhkg_logo.png"><span class="title">泛海控股统一登录平台管理后台</span>
    </div>
    <div class="header-right">
      <span class="account"> ${orgMember.NAME}</span>
      <button class="quit_btn" onclick="logout()">退出</button>
    </div>
  </div>
  <div class="main">
    <div class="main_tree">
      <div>
        <ul id="treeDemo" class="ztree"></ul>
      </div>
    </div>
    <div class="main_body">
      <div class="mian_table">
        <div class="easyui-panel" title="人员列表" style="width: 100%;" data-options="region:'center'" >
          <form id = "form-search">
            <table class="table-search">
              <div style="padding-top: 5px;padding-left: 10px;padding-bottom: 5px;" >
                <span >员工编号：</span>
                <input class="easyui-textbox" name="code"  data-options="validType: 'length[1,2000]'">
                <span >姓名：</span>
                <input class="easyui-textbox" name="name"  data-options="validType: 'length[1,2000]'">
                <span style="padding-left: 10px;">&nbsp;</span>
                <a class="easyui-linkbutton easyui-linkbutton-primary" href="javascript:void(0);" onclick="getMemberByDepartment('-1013665952206207158')">查询</a>
              </div>
            </table>
          </form>
          <div style="padding-top: 0px;padding-left: 6px;margin-bottom: 0px;padding-right:6px;">
            <table id="grid"></table>
          </div>
          <div style="text-align:center; padding-bottom:5px;">
            <%--<a href="javascript:void(0)" style="margin-top: 5px;"align="center" class="easyui-linkbutton easyui-linkbutton-primary" onClick="addTextInput('-1',0)">添加固定IP</a>--%>
            <%--<a href="javascript:void(0)" style="margin-top: 5px;" class="easyui-linkbutton easyui-linkbutton-primary" onClick="deleteTasks()">批量删除</a>--%>
            <%--<a href="javascript:void(0)" style="margin-top: 5px;"align="center" class="easyui-linkbutton easyui-linkbutton-primary" onClick="addTextInput('-1',3)">自动分配IP</a>--%>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</BODY>
</HTML>
