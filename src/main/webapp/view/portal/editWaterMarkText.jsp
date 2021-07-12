<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2021-6-9
  Time: 15:46
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
  <script type="text/javascript" src="<%=path%>/includes/scripts/common/waterMark.js"></script>
  <style type="text/css">
    .submit-button{
      background: #0f88eb;
      border:0;
      border-radius: 3px;
      width: 100px;
      /*line-height:41px;*/
      color:#fff;
      /*font-size: 15px;*/
      height: 30px;
    }
  </style>
  <script type="text/javascript">
    function login(){
      var wmtext = $('[name=wmtext]').val();
      var isShowTime = $('#isShowTime').is(':checked');
      var transparency = $('#transparency option:selected').val();
      var wFontSize = $('#wFontSize option:selected').val();
      wmtext = encodeURI(encodeURI(wmtext));
      window.location.href = "<%=path%>/portal/addWaterMark?wmtext="+wmtext+"&isShowTime="+isShowTime+"&transparency="+transparency+"&wFontSize="+wFontSize
    }
    function previewWaterMark(){
      clearMark();
//      var userId = $('#userId').val();
      var userName = $('#wmtext').val();
      var isShowTime = $('#isShowTime').is(':checked');
      var transparency = $('#transparency option:selected').val();
      var wFontSize = $('#wFontSize option:selected').val();
      var watermark_width;
      var watermark_height = wFontSize * 3;
      wFontSize = wFontSize+"px";

      if(isShowTime){
        var now = getNowTime();
        watermark_width = (userName.toString().length + now.toString().length) * wFontSize * 1.8;
        waterMark({"watermark_txt":""+userName+now,"watermark_alpha":transparency,"watermark_fontsize":wFontSize,"watermark_width":watermark_width,"watermark_height":watermark_height})
      }else{
        watermark_width = userName.toString().length * wFontSize * 1.8;
        waterMark({"watermark_txt":""+userName+"","watermark_alpha":transparency,"watermark_fontsize":wFontSize,"watermark_width":watermark_width,"watermark_height":watermark_height})
      }
    }
  </script>
</head>
<body>
<div style="text-align: center;margin: 200px auto 10px auto">
  <input type="text" id="wmtext" name="wmtext" placeholder="请输入水印文字" style="height: 30px;width: 350px;">
  <input class="submit-button" type="button" onclick="login()" value="提交" >
  <input type="button" onclick="previewWaterMark()" value="效果预览" style="height: 30px;width: 100px;">
</div>
<div style="text-align: center;margin: 0 auto">
  <span style="font-size: 12px;">字体大小：</span>
  <select  name="wFontSize" id="wFontSize" style="width:50px;margin-right: 30px;font-size: 12px;">
    <option value="10">10</option>
    <option value="15" selected="selected">15</option>
    <option value="20">20</option>
    <option value="25">25</option>
    <option value="30">30</option>
    <option value="50">50</option>
    <%--<option value="100">100</option>--%>
  </select>
  <span style="font-size: 12px;">透明度：</span>
  <select  name="transparency" id="transparency" style="width:50px;margin-right: 30px;font-size: 12px;">
    <option value="0.1">1</option>
    <option value="0.2">2</option>
    <option value="0.3">3</option>
    <option value="0.4">4</option>
    <option value="0.5">5</option>
    <option value="0.6" selected="selected">6</option>
    <option value="0.7">7</option>
    <option value="0.8">8</option>
    <option value="0.9">9</option>
    <option value="1.0">10</option>
  </select>
  <span style="font-size: 12px;">水印是否显示时间戳：</span><input type="checkbox" value="isShowTime" id="isShowTime" checked="checked">
</div>
<div style="text-align: left;margin: 100px auto;font-size: 12px;width: 600px;height: 200px;">
  <p style="font-weight: bold">使用说明：</p>
  <p>1.录入水印文字;</p>
  <p>2.可自定义选择字体大小、透明度、是否显示时间戳;</p>
  <p>3.点击[效果预览]可在当前页面进行水印效果预览;</p>
  <p>4.确认水印内容后点击提交;</p>
  <p>5.选择附件上传，可点击[浏览上传],也可拖动文件至虚线框内上传，支持批量上传，附件大小不超过50M;</p>
  <p>6.待页面提示“添加水印成功后”，可点击对应文件进行下载;</p>
  <p>7.文件仅能下载一次，系统不保留用户上传文件;</p>
</div>
</body>
</html>
