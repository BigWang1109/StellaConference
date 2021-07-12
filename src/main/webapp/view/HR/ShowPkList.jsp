<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2020-11-25
  Time: 17:20
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
    <title>人员对比</title>
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
    <jsp:include flush="true" page="/view/common/resource1.jsp"></jsp:include>
    <script type="text/javascript">
      $(document).ready(function () {
        $("#codeList").find("tr").each(function(){
          var tdArr = $(this).children();
          tdArr.eq(1).find('input').attr("checked", "checked");
        });
      });
      function addPeople(){
        window.location.href = "<%=path%>/salary/SalarySearchEnter"
      };
      function beginPK() {
        var codes = new Array();
        var trList = $("#codeList").children("tr")
        $("#codeList").find("tr").each(function(){
          var tdArr = $(this).children();
          var psncode = tdArr.eq(0).find('input').val()+"";
          var type = tdArr.eq(1).find('input').is(':checked');
          if(!type){
            codes.push(psncode);
          }
        });
        if(codes.length > 0){
          delCookie(codes);
        }else{
          window.location.href = "<%=path%>/salary/turnToPkFrame"
        }
      };
      function beginPKNew() {
        var codes = "";
        $("#codeList").find("tr").each(function(){
          var tdArr = $(this).children();
          var psncode = tdArr.eq(0).find('input').val()+"";
          var type = tdArr.eq(1).find('input').is(':checked');
          if(type){
            codes+=psncode+",";
          }
        });
        if(codes!=""){
          window.location.href = "<%=path%>/salary/turnToPkFrameNew?codes="+codes
        }else{
          alert("请先添加人员");
        }

      };
      function delCookie(codes){
        $.ajax({
          url : '<%=path%>/salary/delFromCookie',
          type : 'post',
          data : {
            'codes':codes
          },
          success : function(){
            window.location.href = "<%=path%>/salary/turnToPkFrame"
          }
        });
      };
      //待对比界面删除人
      function delPerson(code,obj){
//        alert($(obj).parent().parent().html())
        $(obj).parent().parent().remove();
        $.ajax({
          url : '<%=path%>/salary/delCookie/'+code,
          type : 'post',
          success : function(){
//            $(this).parent().remove();
          }
        });
      };
    </script>
    <style type="text/css">
      a{
        background-color: #00abc7;
        border: 0;
        color: white;
        height: 30px !important;
        width: 100%;
        display: block !important;
        text-align: center;
        padding-top: 6px;
        border-radius: 5px;
        text-decoration: none;
      }
      .btn{
        background-color: #5182BB;
        border: 0;
        color: white;
        height: 30px !important;
        width: 96%;
        display: block !important;
        text-align: center;
        border-radius: 5px;
        font-size: 15px;
        position: fixed;
        bottom: 20px;
      }
    </style>
</head>
<body>
  <a href="<%=path%>/salary/SalarySearchEnter">+添加人员</a>
  <table style="width: 100%;border: 1px;font-size: 20px;" id="codeList">
  <c:forEach items="${psndocList}" var="psndoc">
    <tr class="kpiItem">
      <td><input type="hidden" value="${psndoc.PSNCODE}"></td>
      <td class="cvvalue" width="20%"><input type="checkbox" checked="checked"></td>
      <td class="cvvalue" width="60%">${psndoc.PSNNAME}&nbsp;&nbsp;${psndoc.CORPNAME}&nbsp;&nbsp;${psndoc.DEPTNAME}&nbsp;&nbsp;${psndoc.GW}</td>
      <td class="cvvalue" width="20%"><a href="#" onclick="delPerson('${psndoc.PSNCODE}',this)">删除</a></td>
    </tr>
  </c:forEach>
  </table>
  <%--<a href="<%=path%>/salary/turnToPkFrame">开始对比(${fn:length(psndocList)})</a>--%>
  <%--<input class="btn" type="button" onclick="beginPK()" value="开始对比">--%>
  <a class="btn" href="#" onclick="beginPKNew()" >开始对比</a>
</body>
</html>
