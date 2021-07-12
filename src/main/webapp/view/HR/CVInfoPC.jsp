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
      width: 25%;
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
      background-color: whitesmoke;
    }
    .cvtitle{
      background-color: #336666;
      color: whitesmoke;
      font-size: 15px;
    }
    a{
      text-decoration: none;
      color: grey;
    }
    #printBtn{
      border: 0;
      color: white;
      height: 36px;
      width:100px;
      /*background-color:#BEBEBE;*/
      background-color:#00abc7;
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
    function hideAndShow(classname,barname,bar){
      $("."+classname+"").toggle();
//        if($("."+classname+"").is(':hidden')){
//          $("#"+bar+"").html(barname+"(隐藏)");
//        }else{
//          $("#"+bar+"").html(barname+"(展开)");
//        }
    }
    function backToMain(){
      window.history.back();
    }

    function testPrint(){
      $("#printBtn").hide();
      $(".fileitem").hide();
      window.print();
      $("#printBtn").show();
      $(".fileitem").show();
    }

    function viewBeforeCheck(ATTACHMENT_NAME,PK_ATTACHMENT){
      var type = ATTACHMENT_NAME.substr(ATTACHMENT_NAME.lastIndexOf("."),ATTACHMENT_NAME.length);
//      if(type!=".pdf" && type!=".doc"){
//        alert("该文件暂不支持在线预览，请下载后查看");
//        $('#'+PK_ATTACHMENT).attr("href","javascript:void(0)");
//      }
    }
  </script>
</head>
<body>
<%--<input id="backBtn" type="button" onclick="backToMain()" value="返回">--%>
<%--<input id="printButton" type="button" onclick="gengerateQRCode('${psndoc.searchVal}')" value="高亮显示">--%>
<input type="button" onclick="testPrint()" value="下载简历" id="printBtn">
<div id="qrcodeArea"></div>
<div id="print">
  <table id="cvtable" border="1px">
    <%--<tr><td class="cvtitle" colspan="4" onclick="backToMain()" style="text-align: left;background-color: #00abc7">返回</td></tr>--%>
    <%--<tr>--%>
    <%--<td class="cvtitle" colspan="4" onclick="testPrint()"--%>
    <%--style="text-align: left;background-color: #00abc7" id="printBtn">下载简历--%>
    <%--</td>--%>
    <%--</tr>--%>
    <tr>
      <td class="cvtitle" colspan="4">基本信息</td>
    </tr>
    <tr>
      <td class="cvname">员工姓名</td>
      <td class="cvvalue" colspan="2">${psndoc.PSNNAME}</td>
      <td class="cvvalue" rowspan="5"><img id="picture" src="<%=path%>/CV/getPictureByCode/${psndoc.PSNCODE}"
                                           width="345" style="width: 85px;height: 115px;"/></td>
      <%--<td class="cvname" rowspan="4"><img id="qrcode" src="<%=path%>/CV/getQRCode/${psndoc.PSNCODE}"  width="345" style="width: 100px;height: 100px;"/></td>--%>
    </tr>
    <tr>
      <td class="cvname">部门</td>
      <td class="cvvalue" colspan="2">${psndoc.DEPTNAME}</td>
    </tr>
    <tr>
      <td class="cvname">岗位</td>
      <td class="cvvalue" colspan="2">${psndoc.GW}</td>
    </tr>
    <tr>
      <td class="cvname">职务</td>
      <td class="cvvalue" colspan="2">${psndoc.ZW}</td>
    </tr>
    <tr>
      <td class="cvname">工号</td>
      <td class="cvvalue" colspan="2">${psndoc.PSNCODE}</td>
    </tr>
    <tr>
      <td class="cvname">公司名称</td>
      <td class="cvvalue" colspan="3">${psndoc.CORPNAME}</td>
    </tr>
    <tr>
      <td class="cvname">入司日期</td>
      <td class="cvvalue">${psndoc.RSDATE}</td>
      <td class="cvname">参加工作时间</td>
      <td class="cvvalue">${psndoc.WORKDATE}</td>
    </tr>
    <tr>
      <td class="cvname">出生日期</td>
      <td class="cvvalue">${psndoc.BIRTHDAY}</td>
      <td class="cvname">年龄</td>
      <td class="cvvalue">${psndoc.AGE}</td>
    </tr>
    <tr>
      <td class="cvname">婚姻状况</td>
      <td class="cvvalue">${psndoc.HY}</td>
      <td class="cvname">生育状况</td>
      <td class="cvvalue">${psndoc.SY}</td>
    </tr>
    <tr>
      <td class="cvname">性别</td>
      <td class="cvvalue">${psndoc.SEX}</td>
      <td class="cvname">民族</td>
      <td class="cvvalue">${psndoc.MZ}</td>
    </tr>
    <tr>
      <td class="cvname">学历</td>
      <td class="cvvalue">${psndoc.XL}</td>
      <td class="cvname">毕业院校</td>
      <td class="cvvalue">${psndoc.SCHOOL}</td>
    </tr>
    <tr>
      <td class="cvname">专业</td>
      <td class="cvvalue" colspan="3">${psndoc.ZY}</td>
    </tr>
    <tr>
      <td class="cvname">入党团时间</td>
      <td class="cvvalue">${psndoc.RDDATE}</td>
      <td class="cvname">政治面貌</td>
      <td class="cvvalue">${psndoc.ZZMM}</td>
    </tr>
    <tr>
      <td class="cvname">籍贯</td>
      <td class="cvvalue">${psndoc.JG}</td>
      <td class="cvname">户口</td>
      <td class="cvvalue">${psndoc.HK}</td>
    </tr>
    <tr>
      <td id="eduBar" class="cvtitle" colspan="4" onclick="hideAndShow('eduItem','学历信息','eduBar')">学历信息</td>
    </tr>
    <c:forEach items="${eduList}" var="edu">
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
    </c:forEach>
    <tr>
      <td id="workBar" class="cvtitle" colspan="4" onclick="hideAndShow('workItem','工作履历','workBar')">工作履历</td>
    </tr>
    <c:if test="${workList != null && fn:length(workList) > 0}">
      <tr class="workItem">
        <td class="cvname">工作单位</td>
        <td class="cvname">职务</td>
        <td class="cvname">开始时间</td>
        <td class="cvname">结束时间</td>
      </tr>
    </c:if>
    <c:forEach items="${workList}" var="work">
      <tr class="workItem">
        <td class="cvvalue">${work.WORKCORP}</td>
        <td class="cvvalue">${work.WORKPOST}</td>
        <td class="cvvalue">${work.BEGINDATE}</td>
        <td class="cvvalue">${work.ENDDATE}</td>
      </tr>
    </c:forEach>
    <tr>
      <td id="kpiBar" class="cvtitle" colspan="4" onclick="hideAndShow('kpiItem','绩效考核','kpiBar')">绩效考核</td>
    </tr>
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
    <tr>
      <td id="contBar" class="cvtitle" colspan="4" onclick="hideAndShow('contItem','合同信息','contBar')">合同信息</td>
    </tr>
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
    <tr>
      <td id="titleBar" class="cvtitle" colspan="4" onclick="hideAndShow('titleItem','职称、执业资格、技术等级','titleBar')">
        职称、执业资格、技术等级
      </td>
    </tr>
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
    <tr>
      <td id="trainBar" class="cvtitle" colspan="4" onclick="hideAndShow('trainitem','培训经历','trainBar')">培训经历</td>
    </tr>
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
        <tr class="fileitem">
        <td class="cvname" colspan="2">文件名称</td>
        <td class="cvname" colspan="2">操作</td>
        </tr>
      </c:if>
      <c:forEach items="${fileList}" var="file">
        <tr class="fileitem">
          <td class="cvvalue" colspan="2">
            <%--<a href="<%=path%>/CV/turnToPdfViewPC/${file.PK_ATTACHMENT}/${file.PSNCODE}">${file.ATTACHMENT_NAME}</a>--%>
            <a id="${file.PK_ATTACHMENT}" href="/pdfjs/web/viewer.html?file=<%=path%>/CV/downloadFileFormat/${file.PK_ATTACHMENT}/${file.PSNCODE}" target="_blank" onclick="viewBeforeCheck('${file.ATTACHMENT_NAME}','${file.PK_ATTACHMENT}')">${file.ATTACHMENT_NAME}</a>
          </td>
          <td class="cvvalue">
            <%--<a id="${file.PK_ATTACHMENT}" href="<%=path%>/CV/turnToPdfViewPC/${file.PK_ATTACHMENT}/${file.PSNCODE}" onclick="viewBeforeCheck('${file.ATTACHMENT_NAME}','${file.PK_ATTACHMENT}')">查看</a>--%>
            <a id="${file.PK_ATTACHMENT}" href="/pdfjs/web/viewer.html?file=<%=path%>/CV/downloadFileFormat/${file.PK_ATTACHMENT}/${file.PSNCODE}" target="_blank" onclick="viewBeforeCheck('${file.ATTACHMENT_NAME}','${file.PK_ATTACHMENT}')">查看</a>
          </td>
          <td class="cvvalue" >
            <%--<a href="<%=path%>/CV/turnToPdfViewPC/${file.PK_ATTACHMENT}/${file.PSNCODE}">下载</a>--%>
            <a id="downloadBtn" href="<%=path%>/CV/downloadFile/${file.PK_ATTACHMENT}/${file.PSNCODE}">下载</a>
          </td>
        </tr>
      </c:forEach>
  </table>
</div>
<script type="text/javascript">
  function exportReportTemplet() {
    var element = $("#print");    // 这个dom元素是要导出pdf的div容器
    var w = element.width();    // 获得该容器的宽
    var h = element.height();    // 获得该容器的高
    alert("w:"+w);
    alert("h:"+h);
    var offsetTop = element.offset().top;    // 获得该容器到文档顶部的距离
    var offsetLeft = element.offset().left;    // 获得该容器到文档最左的距离
    var canvas = document.createElement("canvas");
    canvas.width = w * 2;    // 将画布宽&&高放大两倍
    canvas.height = h * 2;
    var context = canvas.getContext("2d");
    var scale = 2;
    context.scale(2, 2);
//        context.translate(-offsetLeft - abs, -offsetTop);

    var opts = {
      scale: scale,
      canvas: canvas,
      width: w,
      height: h,
      useCORS: true,
      background: '#FFF'
    }

    html2canvas(element, opts).then(function (canvas) {
      allowTaint: false;
      taintTest: false;
      var contentWidth = canvas.width;
      var contentHeight = canvas.height;
      //一页pdf显示html页面生成的canvas高度;
      var pageHeight = contentWidth / 592.28 * 841.89;
//        var pageHeight = contentWidth / 592.28;
      //未生成pdf的html页面高度
      var leftHeight = contentHeight;
      //页面偏移
      var position = 0;
      //a4纸的尺寸[595.28,841.89]，html页面生成的canvas在pdf中图片的宽高
      var imgWidth = 595.28;
      var imgHeight = 595.28 / contentWidth * contentHeight;

      var pageData = canvas.toDataURL('image/jpeg', 1.0);
      //   var oCanvas = document.getElementById("print");
      // Canvas2Image.saveAsJPEG(oCanvas);
      var pdf = new jsPDF('', 'pt', 'a4');

      //有两个高度需要区分，一个是html页面的实际高度，和生成pdf的页面高度(841.89)
      //当内容未超过pdf一页显示的范围，无需分页
      if (leftHeight < pageHeight) {
        pdf.addImage(pageData, 'JPEG', 0, 0, imgWidth, imgHeight);
      } else {    // 分页
        while (leftHeight > 0) {
          alert("leftHeight:"+leftHeight);
          pdf.addImage(pageData, 'JPEG', 0, position, imgWidth, imgHeight)
          leftHeight -= pageHeight;
          position -= 841.89;
          //避免添加空白页
          if (leftHeight > 0) {
            pdf.addPage();
          }
        }
      }
      pdf.save('员工简历.pdf');
    })

  }
</script>
</body>
</html>
