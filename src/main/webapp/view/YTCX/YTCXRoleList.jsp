<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2021-6-3
  Time: 15:52
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
    <title></title>
    <jsp:include flush="true" page="/view/common/resource1.jsp"></jsp:include>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link href="<%=path%>/includes/styles/zTree/YTCX.css" rel="stylesheet">
    <link href="<%=path%>/includes/styles/zTree/zTreeStyle/zTreeStyle.css" rel="stylesheet">
    <script type="text/javascript" src="<%=path%>/includes/scripts/zTree/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="<%=path%>/includes/scripts/zTree/jquery.ztree.core.js"></script>
</head>
<body>
<SCRIPT LANGUAGE="JavaScript">
    var newNodes = ${nodes};
    var zTreeObjNew;
    // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
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
            chkboxType:{"Y":"s","N":"s"},
            chkStyle:"checkbox",
            enable:true,
            nocheckInherit:false,
            chkDisabledInherit:false,
            radioType:"levle"
        }
    };
    // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
    var zNodesNew = newNodes;
    $(document).ready(function(){
        zTreeObjNew = $.fn.zTree.init($("#newTreeDemo"), setting, zNodesNew);
    });
</SCRIPT>
<div class="main_tree">
  <div>
    <ul id="newTreeDemo" class="ztree"></ul>
  </div>
</div>
</body>
</html>
