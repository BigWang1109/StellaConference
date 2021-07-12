<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2020-11-26
  Time: 13:01
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
    <title>薪资对比</title>
    <meta name="viewport"
          content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
    <%--<jsp:include flush="true" page="/view/common/resource1.jsp"></jsp:include>--%>
    <jsp:include flush="true" page="/view/common/tableFreezeResource.jsp"></jsp:include>
    <style type="text/css">
        table{empty-cells:show;border-collapse:collapse;border-spacing:0}
        table{ font-size:13px;border:1px dimgrey solid;background-color:white;}
        table td{height:40px;text-align:center;border:1px dimgrey solid;background-color: white}
        body {
            font-size: 13px;
        }
        .addbtn {
            background-color: #00abc7;
            border: 0;
            color: white;
            height: 30px !important;
            width: 100%;
            display: block !important;
            text-align: center;
            padding-top: 6px;
            border-radius: 5px;
        }

        a {
            text-decoration: none;
        }

        .cvtitle {
            /*width: 10%;*/
            /*background-color: #999999;*/
            background-color: rgb(218,235,248);
            color:black;
            font-weight: bold;
            font-size: 13px;
            height:40px;
        }

        .cvvalue {
            /*width: 45%;*/
        }
        .returnBtn{
            background-color: #5182BB;
            border: 0;
            color: white;
            height: 40px !important;
            width: 96%;
            display: block !important;
            text-align: center;
            border-radius: 5px;
            font-size: 13px;
            position: fixed;
            bottom: 10px;
            padding-top: 10px;
            text-decoration: none;
        }
    </style>
    <script type="text/javascript">
        var len =
        ${fn:length(psndocList)} *
        150 + 45;
        var clientWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
        var clientHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
//        alert(clientWidth);
//        alert(clientHeight);
        var cvTitleWidth = clientWidth * 0.2;
        var cvValueWidth = clientWidth * 0.37;
        $(document).ready(function () {
//            $(".table_area").css("width", len + "px");
//            $("#resTab").css("width", len + "px");
//            $(".table_area").css("width", clientWidth+"px");
            $("#resTab").css("width", "100%");
            $(".cvtitle").css("width", cvTitleWidth +"px");
            $(".cvvalue").css("width", cvValueWidth + "px");
            $("#resTab").FrozenTable(1, 0, 1, 0);
        });
        function frozenTable() {
            var rowHead = 1, rowFoot = 0, colLeft = 0, colRight = 1;
            $("#resTab").FrozenTable(1, 1, 1);
        }
        function getViewportSize () {
            return {
                width: window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth,
                height: window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight
            };
        }

    </script>
</head>
<body>
<div id="head_area">

</div>
<div id="main_area">
    <a id="addButton" class="addbtn" href="<%=path%>/salary/SalarySearchEnter">+添加人员</a>
    <div class="table_area" style="overflow: auto;width: 100%;height: 620px;margin-top: 10px;">
        <table border="1px;" class="resTab" id="resTab">
            <tr>
                <td class="cvtitle">员工姓名</td>
                <c:forEach items="${psndocList}" var="psndoc">
                    <td class="cvvalue">
                        <a href="<%=path%>/salary/userSearchByCode/${psndoc.PSNCODE}" style="text-decoration: none;"> ${psndoc.PSNNAME}</a>
                    </td>
                </c:forEach>
            </tr>
            <tr>
                <td class="cvtitle">公司</td>
                <c:forEach items="${psndocList}" var="psndoc">
                    <td class="cvvalue" style="word-wrap: break-word">${psndoc.CORPNAME}</td>
                </c:forEach>
            </tr>
            <tr>
                <td class="cvtitle">部门</td>
                <c:forEach items="${psndocList}" var="psndoc">
                    <td class="cvvalue" style="word-wrap: break-word">${psndoc.DEPTNAME}</td>
                </c:forEach>
            </tr>
            <tr>
                <td class="cvtitle">岗位/职位</td>
                <c:forEach items="${psndocList}" var="psndoc">
                    <td class="cvvalue" style="word-wrap: break-word">${psndoc.GW}</td>
                </c:forEach>
            </tr>
            <tr>
                <td class="cvtitle">年龄</td>
                <c:forEach items="${psndocList}" var="psndoc">
                    <td class="cvvalue">${psndoc.AGE}</td>
                </c:forEach>
            </tr>
            <tr>
                <td class="cvtitle">入泛海</br>时间</td>
                <c:forEach items="${psndocList}" var="psndoc">
                    <td class="cvvalue" style="word-wrap: break-word">${psndoc.RSDATE}</td>
                </c:forEach>
            </tr>
            <tr>
                <td class="cvtitle">工龄</td>
                <c:forEach items="${psndocList}" var="psndoc">
                    <td class="cvvalue">${psndoc.GL}</td>
                </c:forEach>
            </tr>
            <%--<tr>--%>
                <%--<td class="cvtitle">性别</td>--%>
                <%--<c:forEach items="${psndocList}" var="psndoc">--%>
                    <%--<td class="cvvalue">${psndoc.SEX}</td>--%>
                <%--</c:forEach>--%>
            <%--</tr>--%>
            <tr>
                <td class="cvtitle">学历</td>
                <c:forEach items="${psndocList}" var="psndoc">
                    <td class="cvvalue">${psndoc.XL}</td>
                </c:forEach>
            </tr>
            <tr>
                <td class="cvtitle">薪级薪档</td>
                <c:forEach items="${wadocList}" var="wadoc">
                    <td class="cvvalue" style="word-wrap: break-word">${wadoc.LEV}</td>
                </c:forEach>
            </tr>
            <tr>
                <td class="cvtitle">月薪标准</td>
                <c:forEach items="${wadocList}" var="wadoc">
                    <td class="cvvalue" style="word-wrap: break-word">${wadoc.NMONEY}</td>
                </c:forEach>
            </tr>
            <tr>
                <td class="cvtitle">起薪日期</td>
                <c:forEach items="${wadocList}" var="wadoc">
                    <td class="cvvalue" style="word-wrap: break-word">${wadoc.BEGINDATE}</td>
                </c:forEach>
            </tr>
            <%--<tr>--%>
                <%--<td class="cvtitle">变动原因</td>--%>
                <%--<c:forEach items="${wadocList}" var="wadoc">--%>
                    <%--<td class="cvvalue">${wadoc.REASON}</td>--%>
                <%--</c:forEach>--%>
            <%--</tr>--%>
        </table>
    </div>
    <%--<a class="returnBtn" href="<%=path%>/salary/SalarySearchEnter">返回首页</a>--%>
</div>
</body>
</html>
