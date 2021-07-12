<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2020-7-3
  Time: 14:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
%>
<html lang="zh-CN">
<head>
  <%--<meta charset="UTF-8">--%>
  <meta charset="UTF-8" name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
  <title>客户跟进信息查询</title>
  <jsp:include flush="true" page="/view/common/resource1.jsp"></jsp:include>

  <style>
    .testcontent{
      color: #666666;
      text-decoration: none;
    }
    body{
      /*height: 100%;*/
      width: 100%;
      background: url("<%=path%>/includes/images/NC/bg.jpg");
      background-size: cover;
      position: absolute;
      overflow: hidden;
    }
    .datagrid-cell, .datagrid-cell-group, .datagrid-header-rownumber, .datagrid-cell-rownumber
    {
      text-overflow: ellipsis;
    }
    .result-body{
      /*padding-top: 120px;*/
    }
  </style>

  <script type="text/javascript">
    var corp;
    $(document).ready(function () {
      corp = $('#corp').val();
      $('#grid').datagrid({
        striped: true,
        fitColumns: true,
        resizable: true,
        singleSelect: false,
        url: '<%=path%>/nc/loadCustomerInfo/'+corp,
        idField: 'vpreferredtel',
//                sortName: 'dproceedingdate',
//                sortOrder: 'asc',
        remoteSort: false,
        pagination: true,
        pageNumber: 1,
        pageSize: 10,
        pageList: [10, 20, 50, 100],
        nowrap: false,
//                fit:true,
        loadMsg: constant.Loading,
        columns: [[
//                    { field: 'vpreferredtel', hidden : true},
//                    { field: 'ck', checkbox: true},
          { field: 'index', title: '序号', width: '5%',
            formatter: function (value, row, index) {
              return index+1;
            },align:'center'
          },
//          { field: 'vpreferredtel', title: '手机号', width: '10%' ,align:'left',
//            formatter:function(value, row, index){
//              return value;
//            }
//          },
          { field: 'vcname', title: '客户名称', width: '10%' ,align:'left',
            formatter:function(value, row, index){
//                            var content ='';
//                            if(value != undefined && value != '' && value != null){
//                                var abValue = value + '';
//                                content = '<a style="text-decoration:none;color:#666666" href="javascript:void(0);"  title="' + abValue + '" class="easyui-tooltip">' + abValue + '</a>';
//                            }
//                            return content;
              return value;
            }
          },
//
//          { field: 'vcode', title: '项目编码', width: '5%' ,align:'left',
//            formatter:function(value, row, index){
//              return value;
//            }
//          },
//          { field: 'vname', title: '项目名称', width: '15%' ,align:'left',sortable: true,
//            formatter:function(value, row, index){
////                            var content ='';
////                            if(value != undefined && value != '' && value != null){
////                                var abValue = value + '';
////                                content = '<a style="text-decoration:none;color:#666666" href="javascript:void(0);"  title="' + abValue + '" class="easyui-tooltip">' + abValue + '</a>';
////                            }
////                            return content;
//              return value;
//            }
//          },
//          { field: 'fcustype', title: '客户类型', width: '5%' ,align:'center',
//            formatter:function(value, row, index){
//              return value;
//            }
//          },
          { field: 'contactdate', title: '最近跟踪日期', width: '7%',
            formatter:function(value, row, index){
              return value;
            },align:'center'
          },
          { field: 'dmakedate', title: '跟踪单日期', width: '7%',
            formatter:function(value, row, index){
              return value;
            },align:'center'
          },
//          {
//            field: 'dproceedingdate', title: '事件日期', width: '7%',/* sortable: true,*/
//            formatter: function (value, row, index) {
//              return value;
//            },align:'center'
//          },
//          { field: 'fproceedingtype', title: '事件类型', width: '5%' ,align:'center',
//            formatter:function(value, row, index){
//              return value;
//            }
//          },
          { field: 'vproceedingdesc', title: '事件描述', width: '25%' ,align:'left',
            formatter:function(value, row, index){
//                            var content ='';
//                            if(value != undefined && value != '' && value != null){
//                                var abValue = value + '';
//                                content = '<a style="text-decoration:none;color:#666666" href="javascript:void(0);"  title="' + abValue + '" class="easyui-tooltip">' + abValue + '</a>';
//                            }
//                            return content;
              return value;
            }
          }
        ]],
        onBeforeLoad: function (param) {

          attachParams(param);

        },
        onLoadSuccess: function (data) {
//                    var rowData = data.rows;
//
//                    alert(rowData);
//                    if(rowData.size() > 10){
//                        $('.result-body').css("height:200px;");
//                    }
          var options = $('#grid').datagrid("getPager").data("pagination").options;
          var size = options.pageSize;
//                    if(size > 10){
//                        $('.result-body').css("height:200px;");
//                    }
//                    $.each(rowData, function(index, row){
//                        if (row.LoginName == 'admin') {
//                            $("#grid").datagrid("checkRow", index);
//                            $("#grid").datagrid('getPanel').find("input[type='checkbox']")[index + 1].disabled = true;
//                        }
//                    });
//                    $("#grid").datagrid("resize",{
//                        height: ($(window).height())
//                    });
        }
      });
      loadCombobox();
      changeBG();
      $(window).resize(function () {
        setTimeout(function () {
          $('#grid').datagrid('resize');
        }, 500);
      });
    });

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

    function reloadGrid() {
      $('#grid').datagrid('reload');
      reloadCombobox();
      return false;
    };
    function reloadCombobox(){
      var vpreferredtel = $("#vpreferredtel").val();
      var vnameSelected = $("#vname").combobox('getValues');
      var fcustypeSelected = $("#fcustype").combobox('getValues');
      var fproceedingtypeSelected = $("#fproceedingtype").combobox('getValues');
      var vnameCount = 0;
      $("#vname").combobox({
        url:'<%=path%>/nc/initializeOption/vname/'+vpreferredtel+'/'+corp,
        method:'post',
        valueField:'vname',
        textField:'vname',
        value:0,
        editable : true,
        multiple:true,
//                panelHeight:'150px',
        onLoadSuccess:function(data){
          vnameCount = data.length;
          if(vnameSelected != null){
            $("#vname").combobox('setValues',vnameSelected);
          }
        },
        onShowPanel:function(){
          if(vnameCount < 5){
            $(this).combobox('panel').height("auto");
          }else{
            $(this).combobox('panel').height(150);
          }
        }
      });
      $("#fcustype").combobox({
        url:'<%=path%>/nc/initializeOption/fcustype/'+vpreferredtel+'/'+corp,
        method:'post',
        valueField:'fcustype',
        textField:'fcustype',
        value:0,
        editable : true,
        multiple:true,
        onLoadSuccess:function(){
          $("#fcustype").combobox('setValues',fcustypeSelected);
        },
        panelHeight:'auto'
      });
      $("#fproceedingtype").combobox({
        url:'<%=path%>/nc/initializeOption/fproceedingtype/'+vpreferredtel+'/'+corp,
        method:'post',
        valueField:'fproceedingtype',
        textField:'fproceedingtype',
        value:0,
        editable : true,
        multiple:true,
        onLoadSuccess:function(){
          $("#fproceedingtype").combobox('setValues',fproceedingtypeSelected);
        },
        panelHeight:'auto'
      });
    }
    function loadCombobox(){
      $("#vname").combobox({
        url:'<%=path%>/nc/initializeOption/vname/'+corp,
        method:'post',
        valueField:'vname',
        textField:'vname',
        value:0,
        editable : true,
        multiple:true,
//                panelHeight:'150px',
        onLoadSuccess:function(data){
          vnameCount = data.length;
        },
        onShowPanel:function(){
          if(vnameCount < 5){
            $(this).combobox('panel').height("auto");
          }else{
            $(this).combobox('panel').height(150);
          }
        }
      });
      $("#fcustype").combobox({
        url:'<%=path%>/nc/initializeOption/fcustype/'+corp,
        method:'post',
        valueField:'fcustype',
        textField:'fcustype',
        value:0,
        editable : true,
        multiple:true,
        panelHeight:'auto'
      });
      $("#fproceedingtype").combobox({
        url:'<%=path%>/nc/initializeOption/fproceedingtype/'+corp,
        method:'post',
        valueField:'fproceedingtype',
        textField:'fproceedingtype',
        value:0,
        editable : true,
        multiple:true,
        panelHeight:'auto'
      });
    }
    function exportExcel(){
      $('#grid').datagrid('toExcel','dg.xls');
    }
    function changeBG(){
      if(corp == 'wh'){
        $("body").css({
          "background":"url('<%=path%>/includes/images/NC/bg.jpg')",
          "height":"100%",
          "width":"100%",
          "background-size":"cover",
          "position":"absolute",
          "overflow-y":"hidden"
//                    "overflow":"hidden"
        });
      }else if(corp == 'bj'){
        $("body").css({
          "background":"url('<%=path%>/includes/images/NC/bj.jpg')",
          "height":"100%",
          "width":"100%",
          "background-size":"cover",
          "position":"absolute",
          "overflow-y":"hidden"
//                    "overflow":"hidden"
        });
      }

    }
  </script>
</head>
<%--<body style="font-size: 12px;" class="body">--%>
<body>
<div class="head-bar">
  <p style="text-align: center;margin-bottom: 10px;padding-top:100px;font-size: 30px;color: white">客户跟进信息查询</p>
</div>
<div class="result-body" style="height: 500px;">
  <div class="easyui-panel" title="客户跟进信息查询" style="width: 99%;" data-options="region:'center'" >
    <form id = "form-search">
      <table class="table-search">
        <div style="padding-top: 5px;padding-left: 10px;">
          <span >手机号：</span>
          <input class="easyui-textbox" name="vpreferredtel" id = "vpreferredtel"  data-options="validType: 'length[1,2000]'">
          <span >项目名称：</span>
          <input class="easyui-combobox"
                 name="vname"
                 id="vname">
          <span >客户类型：</span>
          <input class="easyui-combobox"
                 name="fcustype"
                 id="fcustype">
          <span >事件类型：</span>
          <input class="easyui-combobox"
                 name="fproceedingtype"
                 id="fproceedingtype">
          <span style="padding-left: 10px;">&nbsp;</span>
          <a class="easyui-linkbutton easyui-linkbutton-primary" href="javascript:void(0);" onclick="reloadGrid()">查&nbsp;&nbsp;&nbsp;&nbsp;询</a>
        </div>
      </table>
    </form>
    <div style="padding-top: 0px;padding-left: 6px;margin-bottom: 0px;padding-right:6px;">
      <div id="grid" style="height: 330px;"></div>
    </div>
  </div>
</div>
<input id="corp" type = "hidden" value="${corp}">
</body>
</html>
