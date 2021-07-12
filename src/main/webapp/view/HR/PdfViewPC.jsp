<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2020-9-22
  Time: 14:46
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
  <title>在线预览</title>
  <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
  <jsp:include flush="true" page="/view/common/resource1.jsp"></jsp:include>
  <jsp:include flush="true" page="/view/common/pdfMobileResource.jsp"></jsp:include>
  <style type="text/css">
    .progressBar {
      border: solid 2px #86A5AD;
      background: white url(<%=path%>/includes/images/progressBar_m.gif) no-repeat 10px 10px;
    }
    .progressBar {
      display: block;
      width: 50%;
      /*height: 20px;*/
      position: fixed;
      top: 20%;
      left: 38%;
      margin-left: -74px;
      margin-top: -14px;
      padding: 10px 10px 10px 50px;
      text-align: left;
      line-height: 27px;
      font-weight: bold;
      position: absolute;
      z-index: 2001;
    }
    #returnBtn{
      border: 0;
      color: white;
      height: 40px;
      width:85px;
      background-color:#BEBEBE;
      /*background-color:#00abc7;*/
      font-size: 15px;
    }
    #downloadBtn{
      border: 0;
      color: white;
      height: 30px;
      width:75px;
      /*background-color:#BEBEBE;*/
      background-color:#00abc7;
      display: block;
      float: left;
      padding-top: 10px;
      padding-left: 10px;
      text-decoration: none;
      font-size: 15px;
    }
  </style>
  <script type="text/javascript">
    var PK_ATTACHMENT;
    var PSNCODE;
    var ajaxbg;
    $(document).ready(function(){
      ajaxbg = $("#background,#progressBar");
      PK_ATTACHMENT = $("#PK_ATTACHMENT").val();
      PSNCODE = $("#PSNCODE").val();
      $.ajax({
        url: "<%=path%>/CV/getPdfByCode/"+PK_ATTACHMENT+"/"+PSNCODE+"",
        type: "get",
        mimeType: 'text/plain; charset=x-user-defined',//jq ajax请求文件流的方式
        beforeSend:function(){
          ajaxbg.show();
        },
        success: function (data) {
          ajaxbg.hide();
          var pdfh5 = new Pdfh5('#demo', {
            data: data
          });
        }
      });
    });
    <%--function viewPdf(){--%>
    <%--$.ajax({--%>
    <%--url: "<%=path%>/CV/getPdfByCode/'"+PK_ATTACHMENT+"'/'"+PSNCODE+"'", --%>
    <%--type: "get",--%>
    <%--mimeType: 'text/plain; charset=x-user-defined',//jq ajax请求文件流的方式--%>
    <%--success: function (data) {--%>
    <%--var pdfh5 = new Pdfh5('#demo', {--%>
    <%--data: data--%>
    <%--});--%>
    <%--}--%>
    <%--});--%>
    <%--}--%>
    function backToMain(){
      window.history.back();
    }
    function downloadFile(){
      $.ajax({
        url: "<%=path%>/CV/downloadFile/"+PK_ATTACHMENT+"/"+PSNCODE+"",
        type: "get",
        mimeType: 'text/plain; charset=x-user-defined',//jq ajax请求文件流的方式
        beforeSend:function(){
          ajaxbg.show();
        },
        success: function () {
          ajaxbg.hide();
        }
      });
    }
  </script>
</head>
<body>
<%--<input type="button" onclick="downloadFile()" value="下载附件" id="downloadBtn">--%>
<input type="button" onclick="backToMain()" value="返回简历" id="returnBtn">
<a id="downloadBtn" href="<%=path%>/CV/downloadFile/${PK_ATTACHMENT}/${PSNCODE}">下载附件</a>
<div id="demo"></div>
<input id="PK_ATTACHMENT" type="hidden" value="${PK_ATTACHMENT}"/>
<input id="PSNCODE" type="hidden" value="${PSNCODE}"/>
<div id="progressBar" class="progressBar" style="display: none; ">文件加载中，请稍等...</div>
</body>
</html>
