<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2017-8-22
  Time: 9:29
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
    <title>我的预定</title>
    <jsp:include flush="true" page="/view/common/menuResource.jsp"></jsp:include>
    <jsp:include flush="true" page="/view/common/resource1.jsp"></jsp:include>
    <style type="text/css">
        .messTitle{
            padding: 10px;
            color: #666666;
            /*color:#3b4043;*/
            text-decoration: none;
            height: 25px;
            font-size: 16px;
        }
    </style>
    <script type="text/javascript">
      $(document).ready(function(){
          $(".addButton").show();
      });
      function cancelBooking(bookingId){
          $.ajax({
              url:'<%=path%>/booking/cancelBooking/'+bookingId+'',
              type:'post',
              success:function(data){
                if(data.flag == "success"){
                    window.location.reload();
                    alert('归还成功');
                }else{
                    alert('归还失败');
                }
              }
          })
      };
      function showDialog(bookingId,flag){
          var _topWindow;
          _topWindow = window.top.$('#topWindow');
          if (_topWindow.length <= 0){
              _topWindow = window.top.$('<div id="topWindow"/>').appendTo(window.top.document.body);
          }
          var btn;
          if(flag == '0'){
              btn = [
                  {
                      text: '归还',
                      handler: function () {
                          $.ajax({
                              url:'<%=path%>/booking/cancelBooking/'+bookingId+'',
                              type:'post',
                              success:function(data){
                                  if(data.flag == "success"){
                                      alert('归还成功');
                                      _topWindow.dialog('close');
                                      window.location.reload();
                                  }else{
                                      alert('归还失败');
                                  }
                              }
                          })
                      }
                  },
                  {
                      text: '返回',
                      handler: function () {
                          _topWindow.dialog('close');
                      }
                  }];
          }else{
              btn = [
                  {
                      text: '返回',
                      handler: function () {
                          _topWindow.dialog('close');
                      }
                  }];
          }
          _topWindow.dialog({
              title: '预定信息',
              href: '<%=path%>/booking/getBookingDetail/'+bookingId+'' ,
              width: 300,
              height: 400,
              collapsible: false,
              minimizable: false,
              maximizable: false,
              resizable: false,
              cache: false,
              modal: true,
              closed: false,
              onClose: function () {
                  _topWindow.window('destroy');
              },
              buttons: btn
          });
      };
    </script>
</head>
<body>
<table style="width: 100%">
  <div>
    <c:forEach var="booking" items="${bookingList}">
        <tr>
            <td><a class="messTitle ${booking.flag}" onclick="showDialog('${booking.bookingId}','${booking.flag}')">${booking.bookingTitle}</a></td>
            <td rowspan="2">
                <c:if test="${booking.flag eq '0'}">
                    <button style="height:35px;width: 75px;background-color:lightseagreen;border:1px solid lightseagreen;border-radius: 4px;color:#eee;font-size: 16px;" onclick="cancelBooking('${booking.bookingId}')">归还</button>
                </c:if><c:if test="${booking.flag eq '1'}">
                    <button style="height:35px;width: 75px;background-color:grey;border: 1px solid grey;border-radius: 4px;color:#eee;font-size: 16px;">已使用</button>
                </c:if><c:if test="${booking.flag eq '2'}">
                    <button style="height:35px;width: 75px;background-color:grey;border: 1px solid grey;border-radius: 4px;color:#eee;font-size: 16px;">已归还</button>
                </c:if>
            </td>
        </tr>
        <tr>
            <td><a class="messTitle" onclick="showDialog('${booking.bookingId}','${booking.flag}')">(<fmt:formatDate value="${booking.bookingDate}" pattern="yyyy-MM-dd"/>)</a></td>
        </tr>
        <tr><td><DIV style="BORDER-TOP: #00686b 1px dashed; OVERFLOW: hidden; HEIGHT: 1px"></DIV></td>
            <td><DIV style="BORDER-TOP: #00686b 1px dashed; OVERFLOW: hidden; HEIGHT: 1px"></DIV></td>
        </tr>
    </c:forEach>
  </div>
</table>
<div data-role="widget" data-widget="nav4" class="nav4">
    <nav>
        <div id="nav4_ul" class="nav_4">
            <ul class="box">
                <li class="addButton">
                    <a href="<%=path%>/view/booking/booking.jsp" class=""><span>预约会议室</span></a>
                </li>
            </ul>
        </div>
    </nav>
</div>
</body>
</html>
