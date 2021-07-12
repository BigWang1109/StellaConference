<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2020-8-14
  Time: 13:29
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
  <title>员工履历表</title>
  <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
  <jsp:include flush="true" page="/view/common/resource1.jsp"></jsp:include>
  <jsp:include flush="true" page="/view/common/exportPDF.jsp"></jsp:include>
  <style type="text/css">
    body{
      font-size: 20px;
    }
    #cvtable{
      border-collapse: collapse;
      margin: 0 auto;
      text-align: center;
      width: 100%;
    }
    #cvtable td{
      padding: 3px;
      border: 1px solid #cad9ea;
      /*color: #666;*/
      height: 30px;
      word-wrap:break-word;word-break:break-all;
    }
    .cvname{
      color: whitesmoke;
      font-size: 13px;
      width: 15%;
      background-color: #999999;
    }
    .cvvalue{
      color: grey;
      font-size: 13px;
      width: 30%;
    }
    .cvtitle{
      background-color: #336666;
      color: whitesmoke;
      font-size: 15px;
    }
  </style>
  <script type="text/javascript">
    function gengerateQRCode(PSNCODE){
      $.ajax({
        url : '<%=path%>/CV/gengerateQRCode/'+PSNCODE,
        type : 'post',
        beforeSend:function(){
//            ajaxbg.show();
        },
        success : function(data){
//            ajaxbg.hide();
//            createInvigilatorNew(data);
          createQRCode(PSNCODE);
        }
      })
    };
    function createQRCode(PSNCODE){
      $("#qrcodeArea").append('' +
      '<img id="qrcode" src="<%=path%>/CV/getQRCode/'+PSNCODE+'" />');
    };
  </script>
</head>
<body>
<%--<input id="printButton" type="button" onclick="gengerateQRCode('${psndoc.PSNCODE}')" value="生成二维码">--%>
<div id="qrcodeArea"></div>
<div id="print">
  <table id="cvtable" border="1px">
    <tr><td class="cvtitle" colspan="4">基本信息</td></tr>
    <tr>
      <td class="cvname">员工姓名</td><td class="cvvalue">${psndoc.PSNNAME}</td>
      <%--<td class="cvname" rowspan="4">照片</td>--%>
      <%--<td class="cvvalue" rowspan="3">${psndoc.PHOTO}</td>--%>
      <td class="cvvalue" rowspan="4"><img id="picture" src="<%=path%>/CV/getPictureByCode/${psndoc.PSNCODE}"  width="345" style="width: 85px;height: 115px;"/></td>
      <td class="cvname" rowspan="4"><img id="qrcode" src="<%=path%>/CV/getQRCode/${psndoc.PSNCODE}"  width="345" style="width: 100px;height: 100px;"/></td>
    </tr>
    <tr>
      <td class="cvname">岗位</td><td class="cvvalue">${psndoc.GW}</td>
    </tr>
    <tr>
      <td class="cvname">职务</td><td class="cvvalue">${psndoc.ZW}</td>
    </tr>
    <tr>
      <td class="cvname">工号</td><td class="cvvalue">${psndoc.PSNCODE}</td>
    </tr>
    <tr>
      <td class="cvname">公司名称</td><td class="cvvalue" colspan="3">${psndoc.CORPNAME}</td>
    </tr>
    <tr>
      <td class="cvname">入司日期</td><td class="cvvalue">${psndoc.RSDATE}</td>
      <td class="cvname">参加工作时间</td><td class="cvvalue">${psndoc.WORKDATE}</td>
    </tr>
    <tr>
      <td class="cvname">出生日期</td><td class="cvvalue">${psndoc.BIRTHDAY}</td>
      <td class="cvname">年龄</td><td class="cvvalue">${psndoc.AGE}</td>
    </tr>
    <tr>
      <td class="cvname">婚姻状况</td><td class="cvvalue">${psndoc.HY}</td>
      <td class="cvname">生育状况</td><td class="cvvalue">${psndoc.SY}</td>
    </tr>
    <tr>
      <td class="cvname">性别</td><td class="cvvalue">${psndoc.SEX}</td>
      <td class="cvname">民族</td><td class="cvvalue">${psndoc.MZ}</td>
    </tr>
    <tr>
      <td class="cvname">学历</td><td class="cvvalue">${psndoc.XL}</td>
      <td class="cvname">毕业院校</td><td class="cvvalue">${psndoc.SCHOOL}</td>
    </tr>
    <tr>
      <td class="cvname">专业</td><td class="cvvalue" colspan="3">${psndoc.ZY}</td>
    </tr>
    <tr>
      <td class="cvname">入党团时间</td><td class="cvvalue">${psndoc.RDDATE}</td>
      <td class="cvname">政治面貌</td><td class="cvvalue">${psndoc.ZZMM}</td>
    </tr>
    <tr>
      <td class="cvname">籍贯</td><td class="cvvalue">${psndoc.JG}</td>
      <td class="cvname">户口</td><td class="cvvalue">${psndoc.HK}</td>
    </tr>
    <tr><td class="cvtitle"  colspan="4">学历信息</td></tr>
    <c:forEach items="${eduList}" var="edu">
      <%--<c:forEach items="<%=path%>/CV/getWokExprienceByCode/${psndoc.PSNCODE}" var="we">--%>
      <%--<tr>--%>
      <tr><td class="cvname">学校</td><td class="cvvalue">${edu.SCHOOL}</td>
        <td class="cvname">学历</td><td class="cvvalue">${edu.EDUCATION}</td></tr>
      <tr><td class="cvname">专业</td><td class="cvvalue">${edu.MAJOR}</td>
        <td class="cvname">学位</td><td class="cvvalue">${edu.DEGREE}</td></tr>
      <tr><td class="cvname">入学日期</td><td class="cvvalue">${edu.BEGINDATE}</td>
        <td class="cvname">毕业日期</td><td class="cvvalue">${edu.ENDDATE}</td></tr>
      <%--</tr>--%>
    </c:forEach>
    <tr><td class="cvtitle"  colspan="4">工作履历</td></tr>
    <tr>
      <td class="cvname">工作单位</td>
      <td class="cvname">职务</td>
      <td class="cvname">开始时间</td>
      <td class="cvname">结束时间</td>
    </tr>
    <c:forEach items="${workList}" var="work">
      <%--<c:forEach items="<%=path%>/CV/getWokExprienceByCode/${psndoc.PSNCODE}" var="we">--%>
      <tr>
        <td class="cvvalue">${work.WORKCORP}</td>
        <td class="cvvalue">${work.WORKPOST}</td>
        <td class="cvvalue">${work.BEGINDATE}</td>
        <td class="cvvalue">${work.ENDDATE}</td>
      </tr>
    </c:forEach>
    <tr><td class="cvtitle"  colspan="4">绩效考核</td></tr>
    <tr>
      <td class="cvname">考核年度</td>
      <td class="cvname">考核等级</td>
      <td class="cvname" colspan="2">奖励政策</td>
    </tr>
    <c:forEach items="${kpiList}" var="kpi">
      <tr>
        <td class="cvvalue">${kpi.KPI_YEAR}</td>
        <td class="cvvalue">${kpi.DEGREE}</td>
        <td class="cvvalue" colspan="2">${kpi.REWARD}</td>
      </tr>
    </c:forEach>
    <tr><td class="cvtitle"  colspan="4">合同信息</td></tr>
    <tr>
      <td class="cvname">合同编号</td>
      <td class="cvname">合同类型</td>
      <td class="cvname">开始时间</td>
      <td class="cvname">结束时间</td>
    </tr>
    <c:forEach items="${contList}" var="cont">
      <tr>
        <td class="cvvalue">${cont.VCONTRACTNUM}</td>
        <td class="cvvalue">${cont.TERMNAME}</td>
        <td class="cvvalue">${cont.BEGINDATE}</td>
        <td class="cvvalue">${cont.ENDDATE}</td>
      </tr>
    </c:forEach>
    <tr><td class="cvtitle"  colspan="4">职称|执业资格|技术等级</td></tr>
    <tr>
      <td class="cvname">名称</td>
      <td class="cvname">级别</td>
      <td class="cvname">评定机构</td>
      <td class="cvname">评定日期</td>
    </tr>
    <c:forEach items="${titleList}" var="title">
      <tr>
        <td class="cvvalue">${title.P_NAME}</td>
        <td class="cvvalue">${title.DOCNAME}</td>
        <td class="cvvalue">${title.P_ORG}</td>
        <td class="cvvalue">${title.P_DATE}</td>
      </tr>
    </c:forEach>
    <tr><td class="cvtitle"  colspan="4">培训经历</td></tr>
    <tr>
      <td class="cvname">培训名称</td>
      <td class="cvname">培训类别</td>
      <td class="cvname">开始时间</td>
      <td class="cvname">结束时间</td>
    </tr>
    <c:forEach items="${trainList}" var="train">
      <tr>
        <td class="cvvalue">${train.VTRA_ACT}</td>
        <td class="cvvalue">${train.TRM_CLASS_NAMES}</td>
        <td class="cvvalue">${train.BEGINDATE}</td>
        <td class="cvvalue">${train.ENDDATE}</td>
      </tr>
    </c:forEach>

  </table>
</div>
</body>
</html>
