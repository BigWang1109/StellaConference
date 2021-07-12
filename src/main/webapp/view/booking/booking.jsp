<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2017-8-14
  Time: 9:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
  String path = request.getContextPath();
%>
<html>
<head>
    <title>会议室预定</title>
    <jsp:include flush="true" page="/view/common/dingdingResource.jsp"></jsp:include>
    <jsp:include flush="true" page="/view/common/resource1.jsp"></jsp:include>
    <jsp:include flush="true" page="/view/common/bookingResource.jsp"></jsp:include>
    <%--<jsp:include flush="true" page="/view/common/menuResource.jsp"></jsp:include>--%>
    <script type="text/javascript">
//        dd.ready(function(){
//
//        });
//        function pickdate(){
//            dd.biz.util.datepicker({
//                "format":"yyyy-MM-dd"
//            })
//        }
        $(document).ready(function(){
            //初始化日期
            initDate();
            //初始化楼层
            initFloor();
            //初始化监听
            initListen();
        });
        //初始化当天日期
        function initDate(){
            var date = new Date();
            var day = date.getDate();
            if(date.getMonth()<10){
                var month = "0"+(date.getMonth()+1);
            }
            if(date.getDate()<10){
                day = "0"+date.getDate();
            }
            var today = date.getFullYear() + "-"+month+"-"+day;
            var today = today.toString();
            $('.pickdate').val(today);
        };
        //初始化楼层默认21层
        function initFloor(){
            $('.pickfloor option:first').attr('selected','selected');
            var floor = $('.pickfloor option:first').val();
            initRoom(floor);
        };
        function initRoom(floor){
            //1.先获取会议室是否正常
            //2.默认选中第一个正常的会议室
            //3.获取日期，及会议室Id，查询会议室使用情况
            var date = $('.pickdate').val();
            var regionId ;
            $('.region-conston').empty();
            <%--$.post('<%=path%>/booking/checkRegion/'+floor+'',--%>
                   <%--function (data){--%>
                        <%--for(var i=0;i<data.length;i++){--%>
                            <%--var classname = data[i].flag==true?'fault':'normal';--%>
                            <%--$('.region-conston').append('<div id="'+data[i].regionId+'" class="'+classname+'" onclick="roomChange(this)">'+data[i].regionName+'</div>');--%>
                        <%--}--%>
                       <%--for(var i=0;i<data.length;i++){--%>
                           <%--var classname = data[i].flag==true?'fault':'normal';--%>
                           <%--if(classname=='normal'){--%>
                               <%--$('#'+data[i].regionId+'').addClass("choosen");--%>
                               <%--regionId = data[i].regionId;--%>
                               <%--break;--%>
                           <%--}--%>
                       <%--}--%>
                       <%--if(regionId!=null){--%>
                           <%--loadDual();--%>
                           <%--initDual(date,regionId);--%>
                       <%--}else{--%>
                           <%--alert('本层会议室均故障，请选择其他楼层');--%>
                       <%--}--%>
                   <%--}--%>
            <%--);--%>
            $.ajax({
                url : '<%=path%>/booking/checkRegion/'+floor+'',
                type : 'post',
                success : function(data){
                    for(var i=0;i<data.length;i++){
                        var classname = data[i].flag==true?'fault':'normal';
                        $('.region-conston').append('<div id="'+data[i].regionId+'" class="'+classname+'" onclick="roomChange(this)">'+data[i].regionName+'</div>');
                    }
                    for(var i=0;i<data.length;i++){
                        var classname = data[i].flag==true?'fault':'normal';
                        if(classname=='normal'){
                            $('#'+data[i].regionId+'').addClass("choosen");
                            regionId = data[i].regionId;
                            break;
                        }
                    }
                    if(regionId!=null){
                        loadDual();
                        initDual(date,regionId);
                    }else{
                        alert('本层会议室均故障，请选择其他楼层');
                    }
                }
            });
        };
        function loadDual(){
            $('.region-dual').empty();
            <%--$.post('<%=path%>/booking/loadDual',--%>
                    <%--function(data){--%>
                        <%--for(var i=0;i<data.length;i++){--%>
                            <%--$('.region-dual').append('<div id="'+data[i].dualId+'" onclick="dualChange(this)" >'+data[i].dualName+'</div>');--%>
                        <%--}--%>
                    <%--}--%>
            <%--);--%>
            $.ajax({
                url : '<%=path%>/booking/loadDual',
                type : 'post',
                success : function(data){
                    for(var i=0;i<data.length;i++){
                        $('.region-dual').append('<div id="'+data[i].dualId+'" onclick="dualChange(this)" >'+data[i].dualName+'</div>');
                    }
                }
            })
        };
        function initDual(date,regionId){
            <%--$.post('<%=path%>/booking/initDual/'+date+'/'+regionId+'',--%>
                    <%--function(data){--%>
                        <%--$('.region-dual').children().removeClass("reserved");--%>
                        <%--$('.region-dual').children().removeClass("choosen");--%>
                        <%--if(data.length>0){--%>
                            <%--for(var i=0;i<data.length;i++){--%>
                                <%--$('#'+data[i].bookingDual+'').addClass("reserved");--%>
                            <%--}--%>
                        <%--}else{--%>
                            <%--$('.region-dual').children().removeClass("reserved");--%>
                        <%--}--%>
                    <%--}--%>
            <%--);--%>
            $.ajax({
                url : '<%=path%>/booking/initDual/'+date+'/'+regionId+'',
                type : 'post',
                success : function(data){
                    $('#'+regionId+'').siblings().removeClass("choosen");
                    $('.region-dual').children().removeClass("reserved");
                    $('.region-dual').children().removeClass("choosen");
                    if(data.length>0){
                        for(var i=0;i<data.length;i++){
                            $('#'+data[i].bookingDual+'').addClass("reserved");
                        }
                    }else{
                        $('.region-dual').children().removeClass("reserved");
                    }
                }
            })
        };
        function initListen(){
            dateChange();
            floorChange();
        };
        //日期选择监听
        function dateChange(){
            $('.pickdate').change(function(){
                var floor = $('.pickfloor').children('option:selected').val();
                initRoom(floor);
            })
        };
        //楼层选择监听
        function floorChange(){
            $('.pickfloor').change(function(){
                var floor = $(this).children('option:selected').val();
                initRoom(floor);
            })
        };
        //会议室点击事件
        function roomChange(obj){
            var date = $('.pickdate').val();
            if(($('#'+obj.id+'').attr('class')) != 'fault'){
                $('#'+obj.id+'').addClass("choosen");
                $('#'+obj.id+'').siblings().removeClass("choosen");
            }
            initDual(date,obj.id);
        };
        //时间段点击事件
        function dualChange(obj){
            if($('#'+obj.id+'').attr('class') != 'reserved'){
                $('#'+obj.id+'').toggleClass("choosen");
            }
        };
        function checkBooking(dualArray,regionTitle,jsonObj){
//            alert('chexkBooking');
            if(dualArray.length==0){
                alert('请选择会议时间');
                return false;
            }
            if(regionTitle=='' || regionTitle == null){
                alert('请输入会议主题');
                return false;
            }
            return true;
        };
        function submitBooking(){
            var jsonObj = [];
            var entity = {};
            var date = $('.pickdate').val();
            var floor = $('.pickfloor').children('option:selected').val();
            var regionId = $('.region-conston').children('.choosen').attr('id');
            var dualArray = [];
            $('.region-dual').children('.choosen').each(function(){
                    dualArray.push($(this).attr('id'));
            });
            var regionTitle = $('.inputtheme').val();
            var isManager = $('.isManager').prop('checked')==true?0:1;
            entity.bookingDate = date;
//            entity.floor = floor;
            entity.regionId = regionId;
            entity.bookingDual = dualArray.join(",");
            entity.bookingTitle = regionTitle;
            entity.isManager = isManager;
            jsonObj.push(entity);
            jsonObj = JSON.stringify(jsonObj);
            if(!checkBooking(dualArray,regionTitle,jsonObj)){
                return;
            }else{
                $.ajax({
                    <%--url : '<%=path%>/booking/booking',--%>
                    url : '<%=path%>/booking/checkBookingRegion',
                    data:{
                        jsonList : jsonObj
                    },
                    type:'post',
                    dataType:'json',
                    success:function(v){
                        if(v.flag == "success"){
                            alert("预订成功");
                            reset(date,floor,regionId);
                        }else if(v.flag == 'error'){
                            alert("预定失败");
                        }else{
//                            alert("预定失败");
                            alert("预定失败"+ v.flag+"已被预定，请刷新页面重试");
                        }
                    },error:function(){
                        alert('error');
                    }
                });
            }

        };
        function reset(date,floor,regionId){
            $('.pickdate').val(date);
            $('.pickfloor').attr('value',floor);
            $('#'+regionId+'').addClass("choosen");
            $('#'+regionId+'').siblings().removeClass("choosen");
            $('.inputtheme').val("");
            $('.isManager').removeAttr("checked");
            initDual(date,regionId);
        };
    </script>
</head>
<body>
  <div class="container">
      <%--<form id="booking-form">--%>
        <div class="header">
          <div class="header-date">
              <%--日期:<input class="pickdate" onclick="pickdate()" placeholder="请选择日期" readonly="">--%>
              日期:<input class="pickdate" type="date" placeholder="请选择日期" >
          </div>
          <div class="header-floor">
              楼层:<select class="pickfloor">
                    <option value="floor21">21层</option>
                    <option value="floor22">22层</option>
                   </select>
          </div>
        </div>
        <div class="region">
            <div class="region-module region-room">
                <div class="region-conston">
                    <%--<div>1号会议室</div>--%>
                    <%--<div>2号会议室</div>--%>
                    <%--<div>3号会议室</div>--%>
                </div>
            </div>
            <div class="region-title">会议室使用情况：</div>
            <div class="region-dual">
                <%--<div>9:00-9:30</div><div>9:30-10:00</div><div>10:00-10:30</div>--%>
                <%--<div>10:30-11:00</div><div>11:00-11:30</div><div>11:30-12:00</div>--%>
                <%--<div>13:00-13:30</div><div>13:30-14:00</div><div>14:00-14:30</div>--%>
                <%--<div>14:30-15:00</div><div>15:00-15:30</div><div>15:30-16:00</div>--%>
                <%--<div>16:00-16:30</div><div>16:30-17:00</div><div>17:00-17:30</div>--%>
            </div>
            <div class="region-theme">
                <div class="themeinput">会议主题：<input class="inputtheme" placeholder="请输入会议主题"></div>
            </div>
            <div class="region-manager">
                <div class="radio-group"> 是否有高管人员与会： <input class="isManager"  type="checkbox" value="">是</div>
            </div>
            <%--<div class="region-user">--%>
                <%--<div class="userselect">预定人：<div class="username-input"></div></div>--%>
            <%--</div>--%>
            <button type="default" class="button-Submit" onclick="submitBooking()">提交预定</button>
            <%--<div data-role="widget" data-widget="nav4" class="nav4">--%>
                <%--<nav>--%>
                    <%--<div id="nav4_ul" class="nav_4">--%>
                        <%--<ul class="box">--%>
                            <%--<li class="addButton">--%>
                                <%--<a href="<%=path%>/view/booking/booking.jsp" class="" onclick="submitBooking()"><span>提交预定</span></a>--%>
                            <%--</li>--%>
                        <%--</ul>--%>
                    <%--</div>--%>
                <%--</nav>--%>
            <%--</div>--%>
        </div>
      <%--</form>--%>
  </div>
</body>
</html>
