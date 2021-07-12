<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2021-6-8
  Time: 15:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
%>
<html>
<head>
  <title>日志查询</title>
  <jsp:include flush="true" page="/view/common/resource1.jsp"></jsp:include>
  <script type="text/javascript">
    $(document).ready(function () {
      $('#grid').datagrid({
        striped: false,
        fitColumns: true,
        resizable: true,
        singleSelect: false,
        url: '<%=path%>/YTCX/logQuery',
//        sortName: 'CODE',
//        sortOrder: 'asc',
        remoteSort: true,
        pagination: true,
        pageNumber: 1,
        pageSize: 10,
        pageList: [10, 20, 50, 100],
        nowrap: false,
        loadMsg: constant.Loading,
        columns: [[
          { field: 'index', title: '序号', width: '5%',
            formatter: function (value, row, index) {
              return index+1;
            },align:'center'
          },
          { field: 'log_psncode', title: '访问用户账号', align: 'center',width: '15%',
            formatter: function (value, row, index) {
              return value;
            }
          },
          { field: 'log_psnname', title: '访问用户名称', align: 'center',width: '15%',
            formatter: function (value, row, index) {
              return value;
            }
          },
          { field: 'log_type', title: '操作类型', align: 'center',width: '10%',
            formatter: function (value, row, index) {
              if(value == 0){
                return "登录"
              }else if(value == 1){
                return "查询"
              }else{
                return "查看"
              }
            }
          },
          { field: 'log_content', title: '操作内容', align: 'center',width: '30%',
            formatter: function (value, row, index) {
              return value;
            }
          },
          { field: 'log_time', title: '时间', align: 'center',width: '25%',
            formatter: function (value, row, index) {
              return value;
            }
          }
        ]],
        onBeforeLoad: function (param) {
          attachParams(param);
        }
      });
    });
    function reloadGrid() {
      $('#grid').datagrid('reload');
      return false;
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
    function resetSearch(){
      $('#log_psncode').textbox("setValue","");
      $('#log_psnname').textbox("setValue","");
      $('#grid').datagrid('reload');
    };
  </script>
</head>
<body>
<div class="system_list">
  <div class="easyui-panel" title="日志列表" style="width: 100%;" data-options="region:'center'" >
    <form id = "form-search">
      <table class="table-search">
        <div style="padding-top: 5px;padding-left: 10px;padding-bottom: 5px;" >
          <span >访问用户账号：</span>
          <input class="easyui-textbox" name="log_psncode" id="log_psncode" data-options="validType: 'length[1,2000]'">
          <span >访问用户名称：</span>
          <input class="easyui-textbox" name="log_psnname" id="log_psnname"  data-options="validType: 'length[1,2000]'">
          <span >操作类型：</span>
          <select class="easyui-combobox" name="log_type" id="log_type" style="width:200px;">
            <option value="">全部</option>
            <option value="0">登录</option>
            <option value="1">查询</option>
            <option value="2">查看</option>
          </select>
          <span style="padding-left: 10px;">&nbsp;</span>
          <a class="easyui-linkbutton easyui-linkbutton-primary" href="javascript:void(0);" onclick="reloadGrid()">查询</a>
        </div>
      </table>
    </form>
    <div style="padding-top: 0px;padding-left: 6px;margin-bottom: 0px;padding-right:6px;">
      <table id="grid" style="height:310px;"></table>
    </div>
    <div style="text-align:center; padding-bottom:5px;">
    </div>
  </div>
</div>

</body>
</html>
