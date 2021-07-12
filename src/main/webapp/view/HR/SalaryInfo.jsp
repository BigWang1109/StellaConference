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
  <jsp:include flush="true" page="/view/common/pdfMobileResource.jsp"></jsp:include>
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
      width: 25%;
      word-wrap:break-word;word-break:break-all;
    }
    .cvname{
      color: black;
      font-weight: bold;
      font-size: 13px;
      width: 15%;
      background-color: rgb(218,235,248);
    }
    .cvvalue{
      color: black;
      font-size: 13px;
      width: 30%;
    }
    .cvtitle{
      background-color: #999999;
      color: whitesmoke;
      font-weight: bold;
      font-size: 15px;
    }
    a{
      text-decoration: none;
      color: grey;
    }
    .btn{
      background-color: #5182BB;
      border: 1px;
      border-style: solid;
      color: white;
      height: 30px !important;
      width: 100%;
      display: block !important;
      text-align: center;
      border-radius: 5px;
      font-size: 15px;
      padding-top: 10px;
      /*position: fixed;*/
      /*bottom: 10px;*/
    }
    .progress {
      height: 20px;
      background: #ebebeb;
      border-left: 1px solid transparent;
      border-right: 1px solid transparent;
      border-radius: 10px;
    }
    .progress > span {
      position: relative;
      float: left;
      margin: 0 -1px;
      min-width: 30px;
      height: 18px;
      line-height: 16px;
      text-align: right;
      background: #cccccc;
      border: 1px solid;
      border-color: #bfbfbf #b3b3b3 #9e9e9e;
      border-radius: 10px;
      background-image: -webkit-linear-gradient(top, #f0f0f0 0%, #dbdbdb 70%, #cccccc 100%);
      background-image: -moz-linear-gradient(top, #f0f0f0 0%, #dbdbdb 70%, #cccccc 100%);
      background-image: -o-linear-gradient(top, #f0f0f0 0%, #dbdbdb 70%, #cccccc 100%);
      background-image: linear-gradient(to bottom, #f0f0f0 0%, #dbdbdb 70%, #cccccc 100%);
      -webkit-box-shadow: inset 0 1px rgba(255, 255, 255, 0.3), 0 1px 2px rgba(0, 0, 0, 0.2);
      box-shadow: inset 0 1px rgba(255, 255, 255, 0.3), 0 1px 2px rgba(0, 0, 0, 0.2);
    }
    .progress > span > span {
      padding: 0 8px;
      font-size: 11px;
      font-weight: bold;
      color: #404040;
      color: rgba(0, 0, 0, 0.7);
      text-shadow: 0 1px rgba(255, 255, 255, 0.4);
    }
    .progress > span:before {
      content: '';
      position: absolute;
      top: 0;
      bottom: 0;
      left: 0;
      right: 0;
      z-index: 1;
      height: 18px;
      background: url("<%=path%>/includes/images/progress.png") 0 0 repeat-x;
      border-radius: 10px;
    }
    .progress .blue {
      background: rgb(218,235,248);
      border-color: #459fd6 #3094d2 #277db2;
      /*background-image: -webkit-linear-gradient(top, #aed5ed 0%, #7bbbe2 70%, #5aaadb 100%);*/
      /*background-image: -moz-linear-gradient(top, #aed5ed 0%, #7bbbe2 70%, #5aaadb 100%);*/
      /*background-image: -o-linear-gradient(top, #aed5ed 0%, #7bbbe2 70%, #5aaadb 100%);*/
      /*background-image: linear-gradient(to bottom, #aed5ed 0%, #7bbbe2 70%, #5aaadb 100%);*/
    }
  </style>
  <script type="text/javascript">
    function hideAndShow(classname,barname,bar){
      $("."+classname+"").toggle();
//        if($("."+classname+"").is(':hidden')){
//          $("#"+bar+"").html(barname+"(隐藏)");
//        }else{
//          $("#"+bar+"").html(barname+"(展开)");
//        }
    };
    function viewBeforeCheck(ATTACHMENT_NAME,PK_ATTACHMENT){
      var type = ATTACHMENT_NAME.substr(ATTACHMENT_NAME.lastIndexOf("."),ATTACHMENT_NAME.length);
//        if(type!=".pdf"){
//          alert("该类型文件暂不支持在线预览");
//          $('#'+PK_ATTACHMENT).attr("href","javascript:void(0)");
//        }
    }
    function addToPK(){
      var psncode = '${psndoc.PSNCODE}';
      $.ajax({
        url : '<%=path%>/salary/addToCookie/'+psncode,
        type : 'post',
        success : function(data){
//            ajaxbg.hide();
//            createInvigilatorNew(data);
          //跳转到新页面，显示已选人员，并可添加人员
          window.location.href = "<%=path%>/salary/turnToPkList"
        }
      })
    }
  </script>
</head>
<body>
<%--<input id="printButton" type="button" onclick="gengerateQRCode('${psndoc.PSNCODE}')" value="生成二维码">--%>
<%--<input id="printButton" type="button" onclick="gengerateQRCode('${psndoc.searchVal}')" value="高亮显示">--%>
<div id="qrcodeArea"></div>
<div id="print">
  <table id="cvtable" border="1px">
    <%--<tr><td colspan="4"><input class="btn" type="button" value="添加对比" onclick="addToPK()"></td></tr>--%>
    <tr><td colspan="4"><a class="btn" href="#" onclick="addToPK()">添加对比</a></td></tr>
    <tr><td class="cvtitle" colspan="4">员工履历表</td></tr>
      <tr>
        <td class="cvname">信息完整度</td>
        <td colspan="3">
          <div class="progress">
            <span class="blue" style="width: ${psndoc.percentage};"><span>${psndoc.percentage}</span></span>
          </div>
        </td>
      </tr>
    <tr><td class="cvtitle" colspan="4">基本信息</td></tr>
    <tr>
      <td class="cvname">员工姓名</td><td class="cvvalue"colspan="2">${psndoc.PSNNAME}</td>
      <%--<td class="cvname" rowspan="4">照片</td>--%>
      <%--<td class="cvvalue" rowspan="3">${psndoc.PHOTO}</td>--%>
      <%--<td class="cvname" rowspan="4">照片</td>--%>
      <td class="cvvalue" rowspan="6"><img id="picture" src="<%=path%>/CV/getPictureByCode/${psndoc.PSNCODE}"  width="345" style="width: 85px;height: 115px;"/></td>
      <%--<td class="cvvalue" rowspan="4"><img id="picture" src=""  width="345" style="width: 85px;height: 115px;"/></td>--%>
      <%--<td class="cvname" rowspan="4"><img id="qrcode" src="<%=path%>/CV/getQRCode/${psndoc.PSNCODE}"  width="345" style="width: 100px;height: 100px;"/></td>--%>
    </tr>
    <tr>
      <td class="cvname">部门</td><td class="cvvalue" colspan="2">${psndoc.DEPTNAME}</td>
    </tr>
    <tr>
      <td class="cvname">岗位</td><td class="cvvalue"colspan="2">${psndoc.GW}</td>
    </tr>
    <tr>
      <td class="cvname">职务</td><td class="cvvalue"colspan="2">${psndoc.ZW}</td>
    </tr>
    <tr>
      <td class="cvname">工号</td><td class="cvvalue" colspan="2">${psndoc.PSNCODE}</td>
    </tr>
      <tr>
        <td class="cvname">入司日期</td><td class="cvvalue" colspan="2">${psndoc.RSDATE}</td>
      </tr>
    <tr>
      <td class="cvname">公司名称</td><td class="cvvalue" colspan="3">${psndoc.CORPNAME}</td>
    </tr>
      <tr>
        <td class="cvname">出生日期</td><td class="cvvalue">${psndoc.BIRTHDAY}</td>
        <td class="cvname">年龄</td><td class="cvvalue">${psndoc.AGE}</td>
      </tr>
      <tr>
        <td class="cvname">参加工作时间</td><td class="cvvalue">${psndoc.WORKDATE}</td>
        <td class="cvname">工龄</td><td class="cvvalue">${psndoc.GL}</td>
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
    <tr><td id="eduBar" class="cvtitle"  colspan="4" onclick="hideAndShow('eduItem','学历信息','eduBar')">学历信息</td></tr>
    <c:forEach items="${eduList}" var="edu">
      <%--<c:forEach items="<%=path%>/CV/getWokExprienceByCode/${psndoc.PSNCODE}" var="we">--%>
      <%--<tr>--%>
      <tr class="eduItem">
        <td class="cvname">学校</td>
        <td class="cvvalue">${edu.SCHOOL}</td>
        <td class="cvname">学历</td>
        <td class="cvvalue">${edu.EDUCATION}</td>
      </tr>
      <tr class="eduItem">
        <td class="cvname">专业</td>
        <td class="cvvalue">${edu.MAJOR}</td>
        <td class="cvname">学位</td>
        <td class="cvvalue">${edu.DEGREE}</td>
      </tr>
      <tr class="eduItem">
        <td class="cvname">入学日期</td>
        <td class="cvvalue">${edu.BEGINDATE}</td>
        <td class="cvname">毕业日期</td>
        <td class="cvvalue">${edu.ENDDATE}</td>
      </tr>
      <%--</tr>--%>
    </c:forEach>
    <tr><td id="workBar" class="cvtitle"  colspan="4" onclick="hideAndShow('workItem','工作履历','workBar')">工作履历</td></tr>
    <c:if test="${workList != null && fn:length(workList) > 0}">
      <tr class="workItem">
        <td class="cvname">工作单位</td>
        <td class="cvname">职务</td>
        <td class="cvname">开始时间</td>
        <td class="cvname">结束时间</td>
      </tr>
    </c:if>
    <c:forEach items="${workList}" var="work">
      <%--<c:forEach items="<%=path%>/CV/getWokExprienceByCode/${psndoc.PSNCODE}" var="we">--%>
      <tr class="workItem">
        <td class="cvvalue">${work.WORKCORP}</td>
        <td class="cvvalue">${work.WORKPOST}</td>
        <td class="cvvalue">${work.BEGINDATE}</td>
        <td class="cvvalue">${work.ENDDATE}</td>
      </tr>
    </c:forEach>
      <tr><td id="wadocBar" class="cvtitle"  colspan="4" onclick="hideAndShow('wadocItem','定调资信息','wadocBar')">定调资信息</td></tr>
      <c:if test="${wadocList != null && fn:length(wadocList) > 0}">
        <tr class="wadocItem">
          <td class="cvname">薪资起始日期</td>
          <td class="cvname">薪级薪档</td>
          <td class="cvname">月薪标准</td>
          <td class="cvname">变动原因</td>
        </tr>
      </c:if>
      <c:forEach items="${wadocList}" var="wadoc">
        <%--<c:forEach items="<%=path%>/CV/getWokExprienceByCode/${psndoc.PSNCODE}" var="we">--%>
        <tr class="wadocItem">
          <td class="cvvalue">${wadoc.BEGINDATE}</td>
          <td class="cvvalue">${wadoc.LEV}</td>
          <td class="cvvalue">${wadoc.NMONEY}</td>
          <td class="cvvalue">${wadoc.REASON}</td>
        </tr>
      </c:forEach>
    <tr><td id="kpiBar" class="cvtitle"  colspan="4" onclick="hideAndShow('kpiItem','绩效考核','kpiBar')">绩效考核</td></tr>
    <c:if test="${kpiList != null && fn:length(kpiList) > 0}">
      <tr class="kpiItem">
        <td class="cvname">考核年度</td>
        <td class="cvname">考核等级</td>
        <td class="cvname" colspan="2">奖励政策</td>
      </tr>
    </c:if>
    <c:forEach items="${kpiList}" var="kpi">
      <tr class="kpiItem">
        <td class="cvvalue">${kpi.KPI_YEAR}</td>
        <td class="cvvalue">${kpi.DEGREE}</td>
        <td class="cvvalue" colspan="2">${kpi.REWARD}</td>
      </tr>
    </c:forEach>
    <tr><td id="contBar" class="cvtitle"  colspan="4" onclick="hideAndShow('contItem','合同信息','contBar')">合同信息</td></tr>
    <c:if test="${contList != null && fn:length(contList) > 0}">
      <tr class="contItem">
        <td class="cvname">合同编号</td>
        <td class="cvname">合同类型</td>
        <td class="cvname">开始时间</td>
        <td class="cvname">结束时间</td>
      </tr>
    </c:if>
    <c:forEach items="${contList}" var="cont">
      <tr class="contItem">
        <td class="cvvalue">${cont.VCONTRACTNUM}</td>
        <td class="cvvalue">${cont.TERMNAME}</td>
        <td class="cvvalue">${cont.BEGINDATE}</td>
        <td class="cvvalue">${cont.ENDDATE}</td>
      </tr>
    </c:forEach>
    <tr><td id="titleBar" class="cvtitle"  colspan="4" onclick="hideAndShow('titleItem','职称、执业资格、技术等级','titleBar')">职称、执业资格、技术等级</td></tr>
    <c:if test="${titleList != null && fn:length(titleList) > 0}">
      <tr class="titleItem">
        <td class="cvname">名称</td>
        <td class="cvname">级别</td>
        <td class="cvname">评定机构</td>
        <td class="cvname">评定日期</td>
      </tr>
    </c:if>
    <c:forEach items="${titleList}" var="title">
      <tr class="titleItem">
        <td class="cvvalue">${title.p_NAME}</td>
        <td class="cvvalue">${title.DOCNAME}</td>
        <td class="cvvalue">${title.p_ORG}</td>
        <td class="cvvalue">${title.p_DATE}</td>
      </tr>
    </c:forEach>
    <tr><td id="trainBar" class="cvtitle"  colspan="4" onclick="hideAndShow('trainitem','培训经历','trainBar')">培训经历</td></tr>
    <c:if test="${trainList != null && fn:length(trainList) > 0}">
      <tr class="trainitem">
        <td class="cvname">培训名称</td>
        <td class="cvname">培训类别</td>
        <td class="cvname">开始时间</td>
        <td class="cvname">结束时间</td>
      </tr>
    </c:if>
    <c:forEach items="${trainList}" var="train">
      <tr class="trainitem">
        <td class="cvvalue">${train.VTRA_ACT}</td>
        <td class="cvvalue">${train.TRM_CLASS_NAMES}</td>
        <td class="cvvalue">${train.BEGINDATE}</td>
        <td class="cvvalue">${train.ENDDATE}</td>
      </tr>
    </c:forEach>
    <tr><td id="fileBar" class="cvtitle" colspan="4" onclick="hideAndShow('fileitem','附件','fileBar')">附件</td></tr>
    <c:if test="${fileList != null && fn:length(fileList) > 0}">
      <%--<tr class="fileitem">--%>
      <%--<td class="cvname" colspan="3">文件名称</td>--%>
      <%--<td class="cvname" >操作</td>--%>
      <%--</tr>--%>
    </c:if>
    <c:forEach items="${fileList}" var="file">
      <tr class="fileitem">
          <%--<td class="cvvalue" colspan="3">${file.ATTACHMENT_NAME}</td>--%>
        <td class="cvvalue" colspan="4">
            <%--<a id="${file.PK_ATTACHMENT}" href="<%=path%>/CV/turnToPdfView/${file.PK_ATTACHMENT}/${file.PSNCODE}" onclick="viewBeforeCheck('${file.ATTACHMENT_NAME}','${file.PK_ATTACHMENT}')">${file.ATTACHMENT_NAME}</a>--%>
          <c:if test="${isAndroid == false}">
            <a id="${file.PK_ATTACHMENT}" href="/pdfjs/web/viewer.html?file=<%=path%>/CV/downloadFileFormat/${file.PK_ATTACHMENT}/${file.PSNCODE}" onclick="viewBeforeCheck('${file.ATTACHMENT_NAME}','${file.PK_ATTACHMENT}')">${file.ATTACHMENT_NAME}</a>
          </c:if>
          <c:if test="${isAndroid == true}">
            <a id="${file.PK_ATTACHMENT}" href="<%=path%>/CV/turnToPdfView/${file.PK_ATTACHMENT}/${file.PSNCODE}" onclick="viewBeforeCheck('${file.ATTACHMENT_NAME}','${file.PK_ATTACHMENT}')">${file.ATTACHMENT_NAME}</a>
          </c:if>
            <%--<a id="downloadBtn" href="<%=path%>/CV/downloadFile/${file.PK_ATTACHMENT}/${file.PSNCODE}">下载</a>--%>
        </td>
      </tr>
    </c:forEach>
  </table>
</div>
</body>
</html>
