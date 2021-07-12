<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2021-6-9
  Time: 11:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <title>文件加密</title>
  <jsp:include flush="true" page="/view/common/resource.jsp"></jsp:include>
</head>
<body>
<script type="text/javascript">
  function getQuestionIDs(){
    alert("添加水印成功，请尽快下载文件！")
  }
</script>
<form id="form-add">
  <table style="width: 99%;" align="center" class="table-input">
    <%--<tr><input type="text" id="wmtext" placeholder="请输入水印文字"></tr>--%>
    <tr>
      <%--<td style="width: 100px;font-size:13px;" align="right" valign="top">上传文件：</td>--%>
      <td>
        <%--<a href="#">上传</a>--%>
        <jsp:include flush="true" page="/view/common/uploadfile.jsp">
          <jsp:param name="foreignid" value="123"/>
          <jsp:param name="category" value="fhkg"/>
          <jsp:param name="multiple" value="true"/>
          <jsp:param name="allowedExtensions" value="['pdf']"/>
          <jsp:param name="sizeLimit" value="52400000"/>
          <jsp:param name="list" value="${list}"/>
          <jsp:param name="wmtext" value="${wmtext}"/>
          <jsp:param name="isShowTime" value="${isShowTime}"/>
          <jsp:param name="transparency" value="${transparency}"/>
          <jsp:param name="wFontSize" value="${wFontSize}"/>
          <jsp:param name="onComplete" value="getQuestionIDs"/>
        </jsp:include>
      </td>
    </tr>
  </table>
</form>
</body>
</html>
