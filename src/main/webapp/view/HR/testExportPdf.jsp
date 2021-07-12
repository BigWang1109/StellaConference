<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2020-8-21
  Time: 13:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
%>
<html>
<head>
    <title></title>
  <jsp:include flush="true" page="/view/common/exportPDF.jsp"></jsp:include>
  <jsp:include flush="true" page="/view/common/resource1.jsp"></jsp:include>
  <script type="text/javascript">
    function exportReportTemplet() {
      alert("enter");
      var element = $("#print");    // 这个dom元素是要导出pdf的div容器
      var w = element.width();    // 获得该容器的宽
      var h = element.height();    // 获得该容器的高
      var offsetTop = element.offset().top;    // 获得该容器到文档顶部的距离
      var offsetLeft = element.offset().left;    // 获得该容器到文档最左的距离
      var canvas = document.createElement("canvas");
      canvas.width = w * 2;    // 将画布宽&&高放大两倍
      canvas.height = h * 2;
      var context = canvas.getContext("2d");
      var scale = 2;
      context.scale(2, 2);
      //  context.translate(-offsetLeft - abs, -offsetTop);

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
        //未生成pdf的html页面高度
        var leftHeight = contentHeight;
        //页面偏移
        var position = 0;
        //a4纸的尺寸[595.28,841.89]，html页面生成的canvas在pdf中图片的宽高
        var imgWidth = 595.28;
        var imgHeight = 592.28 / contentWidth * contentHeight;

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
            pdf.addImage(pageData, 'JPEG', 0, position, imgWidth, imgHeight)
            leftHeight -= pageHeight;
            position -= 841.89;
            //避免添加空白页
            if (leftHeight > 0) {
              pdf.addPage();
            }
          }
        }
        pdf.save('销售合同.pdf');
      })

    }
    function getmes() {
      alert("enter");
      html2canvas(document.getElementById('print'),{ //此处的id为要打印的部分

        onrendered:function(canvas){
          var contentWidth = canvas.width;
          var contentHeight = canvas.height;

          //一页pdf显示html页面生成的canvas高度;
          var pageHeight = contentWidth / 592.28 * 841.89;
          //未生成pdf的html页面高度
          var leftHeight = contentHeight;
          //pdf页面偏移
          var position = 0;
          //a4纸的尺寸[595.28,841.89]，html页面生成的canvas在pdf中图片的宽高
          var imgWidth = 595.28;
          var imgHeight = 592.28/contentWidth * contentHeight;

          var pageData = canvas.toDataURL('image/jpeg', 1.0);

          var pdf = new jsPDF('', 'pt', 'a4');

          //有两个高度需要区分，一个是html页面的实际高度，和生成pdf的页面高度(841.89)
          //当内容未超过pdf一页显示的范围，无需分页
          if (leftHeight < pageHeight) {
            pdf.addImage(pageData, 'JPEG', 0, 0, imgWidth, imgHeight );
          } else {
            while(leftHeight > 0) {
              pdf.addImage(pageData, 'JPEG', 0, position, imgWidth, imgHeight);
              leftHeight -= pageHeight;
              position -= 841.89;
              //避免添加空白页
              if(leftHeight > 0) {
                pdf.addPage();
              }
            }
          }

          pdf.save('导出为PDF测试.pdf'); //
        }
      });

    }

  </script>
</head>
<body>
<button id="exportToPdf" onclick="exportReportTemplet()">导出为PDF</button>
<div id="print">
  这里是要导出为pdf中的内容
</div>
</body>
</html>
