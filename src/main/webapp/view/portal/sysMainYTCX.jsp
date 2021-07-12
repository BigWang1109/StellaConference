<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2021-6-3
  Time: 13:54
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
%>
<HTML>
<HEAD>
  <TITLE> 简历查询系统管理后台 </TITLE>
  <%--<meta http-equiv="content-type" content="text/html; charset=UTF-8">--%>
  <%--<link href="<%=path%>/includes/styles/zTree/YTCX.css" rel="stylesheet">--%>
  <%--<link href="<%=path%>/includes/styles/zTree/zTreeStyle/zTreeStyle.css" rel="stylesheet">--%>
  <link href="<%=path%>/includes/styles/portal/sysMainYTCX.css" rel="stylesheet">
  <%--<script type="text/javascript" src="<%=path%>/includes/scripts/zTree/jquery-1.4.4.min.js"></script>--%>
  <%--<script type="text/javascript" src="<%=path%>/includes/scripts/zTree/jquery.ztree.core.js"></script>--%>
  <%--<script type="text/javascript" src="<%=path%>/includes/scripts/zTree/jquery.ztree.excheck.js"></script>--%>
  <link href="<%=path%>/includes/jquery-easyui-1.4.4/themes/bootstrap/easyui.css" rel="stylesheet">
  <link href="<%=path%>/includes/jquery-easyui-1.4.4/themes/icon.css" rel="stylesheet">
  <%--<script type="text/javascript" src="<%=path%>/includes/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>--%>
  <%--<script type="text/javascript" src="<%=path%>/includes/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>--%>
  <jsp:include flush="true" page="/view/common/resource1.jsp"></jsp:include>
  <SCRIPT LANGUAGE="JavaScript">
    var setting = {
//      callback:{
//        onClick:getNodeByClick
//      },
      data:{
        simpleData:{
          enable:true,
          idKey:"id",
          pIdKey:"pId",
          rootPId:0
        }
      },
      check:{
        autoCheckTrigger:false,
        chkboxType:{"Y":"ps","N":"ps"},
        chkStyle:"checkbox",
        enable:true,
        nocheckInherit:false,
        chkDisabledInherit:false,
        radioType:"levle"
      }
    };
    $(document).ready(function(){
      initRoleUserList();
    });
    function logout(){
      window.location.href = "<%=path%>/portal/login";
    }
    function initRoleUserList(){
      $('#grid').datagrid({
        striped: false,
        fitColumns: true,
        resizable: true,
        singleSelect: false,
        url: '<%=path%>/YTCX/getRoleUserList',
        idField: 'psncode',
        remoteSort: true,
        pagination: true,
        pageNumber: 1,
        pageSize: 10,
        pageList: [10, 20, 50, 100],
        nowrap: false,
        loadMsg: constant.Loading,
        columns: [[
          { field: 'id', hidden : true},
          { field: 'ck', checkbox: true},
          { field: 'index', title: '序号', width: '10%',
            formatter: function (value, row, index) {
              return index+1;
            },align:'center'
          },
          { field: 'psncode', title: '员工编号', width: '30%' ,align:'center',
            formatter:function(value, row, index){
              return value;
            }
          },
          { field: 'psnname', title: '姓名', align: 'center',width: '30%',
            formatter: function (value, row, index) {
              return value;
            }
          },
          { field: 'type', title: '操作', align: 'center',width: '30%',
            formatter: function (value, row, index) {
              return '<a href="#" onclick="openOrgTreeDialog(\''+ row.psncode + '\')">设置权限</a> ';
            }
          }
        ]],
        onBeforeLoad: function (param) {

              attachParams(param);

          }

      });
    }
    function peopleBatchAuth(){
      var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
      var checkedNodes = treeObj.getCheckedNodes();
//        for(var i=0;i<checkedNodes.length;i++){
//            alert(checkedNodes[i].id);
//        }
    }
    function openOrgTreeDialog(psncode){
      var _topWindow;
      _topWindow = window.top.$('#topWindow');
      if (_topWindow.length <= 0){
        _topWindow = window.top.$('<div id="topWindow"/>').appendTo(window.top.document.body);
      }
      var btn;
        btn = [{
            text: '保存',
            handler: function () {
                var zTreeObj = window.top.$.fn.zTree.getZTreeObj("newTreeDemo");
                var checkedNodes = zTreeObj.getCheckedNodes(true);
//                checkedNodes = JSON.stringify(checkedNodes);
                var nodesArr = new Array()
                for(var i=0;i<checkedNodes.length;i++){
//                    alert(checkedNodes[i].id);
                    nodesArr.push(checkedNodes[i].id);
                }
//                alert(checkedNodes)
                $.ajax({
                    url : '<%=path%>/YTCX/updateRoleList',
                    data:{
                        'nodesArr':nodesArr,
                        'psncode':psncode
                    },
                    type : 'post',
                    success : function(data){
                        alert("保存成功")
                        _topWindow.dialog('close');
                        $('#grid').datagrid('reload');
                    }
                })
            }
        }];
      _topWindow.dialog({
        title: '权限设置',
        href: '<%=path%>/YTCX/getRoleList/'+psncode ,
        width: '500px',
        height: '550px',
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
    function addUser(){
        var _topWindow;
        _topWindow = window.top.$('#topWindow');
        if (_topWindow.length <= 0){
            _topWindow = window.top.$('<div id="topWindow"/>').appendTo(window.top.document.body);
        }
        var btn;
        btn = [{
            text: '确认添加',
            handler: function () {
                var codes = new Array();
                window.top.$("#searchResultTable").find("tr").each(function () {
                    var tdArr = $(this).children();
                    var psncode = tdArr.eq(0).find('input').val().toString();
                    var type = tdArr.eq(0).find('input').is(':checked');
                    if (type) {
                        if(!checkCodeExists(psncode)){
                            codes.push(psncode);
                        }
                    }
                });
                if(codes.length > 0){
                    $.ajax({
                        url: '<%=path%>/YTCX/addUser',
                        data: {
                            'codes': codes
                        },
                        type: 'post',
                        success: function () {
                            alert("添加成功")
                            _topWindow.dialog('close');
                            $('#grid').datagrid('reload');
                        }
                    })
                }else{
                    alert("请先选择人员");
                }
            }
        }];
        _topWindow.dialog({
            title: '添加用户',
            href: '<%=path%>/YTCX/turnToAddUser' ,
            width: '500px',
            height: '550px',
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
    function delUser(){
        var codes = new Array();
        var rows = $('#grid').datagrid('getSelections');
        if(rows.length > 0){
            for(var i=0; i<rows.length; i++){
                codes.push(rows[i].psncode);
            }
            $.ajax({
                url: '<%=path%>/YTCX/delUser',
                data: {
                    'codes': codes
                },
                type: 'post',
                success: function () {
                    alert("删除成功")
                    $('#grid').datagrid('reload');
                }
            })
        }else{
            alert("请选择要删除的人员");
        }
    };
    function checkCodeExists(code){
//        alert(Object.prototype.toString.apply(code))
        var existsCodes = new Array();
        var rows = $('#grid').datagrid('getRows');
        for(var i=0; i<rows.length; i++){
            existsCodes.push(rows[i].psncode.toString());
//            alert(existsCodes[i])
        }
        return existsCodes.includes(code)
//        if(existsCodes.includes(code)){
//            return true;
//        }else{
//            return false;
//        }

    }
    function reloadGrid() {
        $('#grid').datagrid('reload');
        return false;
    };
    function resetSearch(){
        $('#psncode').textbox("setValue","");
        $('#psnname').textbox("setValue","");
        $('#grid').datagrid('reload');
    };
    function attachParams(param) {
        var jsonObj = $('#form-search').serializeArray();
        var PageCount = {};
        PageCount.name = 'PageCount';
        PageCount.value = $('#grid').datagrid('getPager').data('pagination').options.total;
        jsonObj.push(PageCount);

        var QueryKey = {};
        QueryKey.name = 'QueryKey';
        QueryKey.value = $('#grid').datagrid('getData').QueryKey;
        jsonObj.push(QueryKey);

        var jsonStr = $.toJSON(jsonObj);
        param.query = jsonStr;

    };
  </SCRIPT>
</HEAD>
<BODY>
<div class="container">
  <div class="main">
    <div class="main_body">
      <div class="mian_table" >
        <div class="easyui-panel" title="人员列表" style="width: 100%;" data-options="region:'center'" >
          <form id = "form-search">
            <table class="table-search">
              <div style="padding-top: 5px;padding-left: 10px;padding-bottom: 5px;" >
                <span >员工编号：</span>
                <input class="easyui-textbox" name="psncode" id="psncode" data-options="validType: 'length[1,2000]'">
                <span >姓名：</span>
                <input class="easyui-textbox" name="psnname" id="psnname"  data-options="validType: 'length[1,2000]'">
                <span style="padding-left: 10px;">&nbsp;</span>
                <a class="easyui-linkbutton easyui-linkbutton-primary" href="javascript:void(0);" onclick="reloadGrid()">查询</a>
                <a class="easyui-linkbutton easyui-linkbutton-primary" href="javascript:void(0);" onclick="resetSearch()">重置</a>
              </div>
            </table>
          </form>
          <div style="padding-top: 0px;padding-left: 6px;margin-bottom: 0px;padding-right:6px;">
            <table id="grid" style="height:310px;"></table>
          </div>
          <div style="text-align:center; padding-bottom:5px;">
            <a href="javascript:void(0)" style="margin-top: 5px;"align="center" class="easyui-linkbutton easyui-linkbutton-primary" onClick="addUser()">添加用户</a>
            <a href="javascript:void(0)" style="margin-top: 5px;" class="easyui-linkbutton easyui-linkbutton-primary" onClick="delUser()">删除用户</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</BODY>
</HTML>
