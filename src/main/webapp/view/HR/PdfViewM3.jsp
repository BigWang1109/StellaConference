<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2020-10-16
  Time: 9:07
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
  </style>
  <%--<script  src="<%=path%>/includes/scripts/M3/cmp-i18n.js"></script>--%>
  <%--<script  src="<%=path%>/includes/scripts/M3/cordova/ios/cordova.js"></script>--%>
  <%--<script  src="<%=path%>/includes/scripts/M3/cordova/cordova-plugins.js"></script>--%>
  <%--<script  src="<%=path%>/includes/scripts/M3/cmp.js"></script>--%>
  <%--<script  src="<%=path%>/includes/scripts/M3/cmp-att.js"></script>--%>
  <script>
//    cmp.att.read({
////      path: cmp.seeyonbasepath + "/rest/attachment/file/232323232",//文件的服务器地址
//      path: "E:\\QRCode\\hrfiles\\00022486\\",//文件的服务器地址
//      filename: "1002A11000000001ROFV.pdf",//附件名称
//      edit: false,  //是否可以进行修改编辑
//      success: function (result) {
//        alert("success")
//        //返回的数据格式如下：
//        alert(result);
////        result = "/storage/emulated/0/m3/files/图片.jpeg";
//
//      },
//      error: function (error) {
//        //do something
//      }
//    });
  </script>
  <script>
    function loadScript(url, callback) {
      var script = document.createElement("script")
      script.onload = function () {
        callback();
      };
      script.onerror = function () {
        console.log('文件加载失败');
      };
      script.src = url;
      document.getElementsByTagName("head")[0].appendChild(script);
      console.log('文件加载' + url);
    }
    loadScript('<%=path%>/includes/scripts/M3/cmp-i18n.js', function () {
      loadScript('<%=path%>/includes/scripts/M3/cordova/ios/cordova.js', function () {
        loadScript('<%=path%>/includes/scripts/M3/cordova/cordova-plugins.js', function () {
          loadScript('<%=path%>/includes/scripts/M3/cmp.js', function () {
            loadScript('<%=path%>/includes/scripts/M3/cmp-att.js', function () {
              cmp.ready(cmp.att.read({
                        <%--path: "<%=path%>/CV/downloadFile/1002A11000000001ROFV/00022486",--%>
                        path: "http://10.0.125.112:8080/StellaConference/CV/downloadFile/1002A11000000001ROFV/00022486",
//                        path: "/soft/module/hrfiles/00022486/1002A11000000001ROFV.pdf",//文件的服务器地址
//                        path: "E:\\QRCode\\hrfiles\\00022486\\1002A11000000001ROFV.pdf",//文件的服务器地址
                        filename: "1002A11000000001ROFV.pdf",//附件名称
                        edit: false,  //是否可以进行修改编辑
                        success: function (result) {
                          alert("success")
                          //返回的数据格式如下：
                          alert(result);

                        },
                        error: function (error) {
                          //do something
                          alert(error);
                        }
                      })
              );
            })
          })
        })
      })
    })
  </script>
</head>
<body>
<div id="demo"></div>
<input id="PK_ATTACHMENT" type="hidden" value="${PK_ATTACHMENT}"/>
<input id="PSNCODE" type="hidden" value="${PSNCODE}"/>
<div id="progressBar" class="progressBar" style="display: none; ">文件加载中，请稍等...</div>
</body>
</html>
