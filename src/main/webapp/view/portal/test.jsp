<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2019-12-19
  Time: 11:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <style type="text/css">
      .container{
        display: block;
        width: 100%;
        height: 800px;
        border: 0;
      }
      .container .main{
        height: 400px;
        width: 100%;
      }
      .app_group{
        text-align: center;
        padding-top: 50px;
      }
      .app{
        padding: 15px;
        display: inline-block;
        text-align: center;
        margin: 0 14px 28px;
        border-radius: 6px;
        cursor: pointer;
        vertical-align: top;
        background-position: 50% 10px;
        background-repeat: no-repeat;
        width: 80px;
        height: 80px;
        box-shadow: 0 1px 5px 0 rgba(0,0,0,.05);
        background-color: #00438a;
        /*color: #00438a;*/
      }
      .app_icon{
        margin: 32px auto 0;
        background-size: auto 100%;
        width: 72px;
        min-width: 72px;
        height: 72px;
        background-color: transparent;
        background-repeat: no-repeat;
        background-position: 50% 50%;
        vertical-align: middle;
      }
      .app p {
        min-width: 142px;
        font-size: 1.1em;
        opacity: .7;
        margin: 5px auto 0;
        line-height: 1.25em;
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
      }
      body{
        background-color: #e8ebeb;
      }
      a{
        text-decoration:none;
        color: white;
      }
    </style>
</head>
<body>
<div class="container">
  <div class="app_group">
    <div class = 'app' onclick="open_OA()">
      <div class="app_icon">
        <a href="http://10.0.25.11" target="_blank">中国泛海</a>
      </div>
    </div>
    </br>
    <div class = 'app' onclick="open_fhkg_mail()">
      <div class="app_icon">
        <a href="http://mail.fhkg.com" target="_blank">民生信托</a>
      </div>
    </div>
    <div class = 'app' onclick="open_report()">
      <div class="app_icon">
        <a href="http://10.0.125.7:8008" target="_blank">民生证券</a>
      </div>
    </div>
    <div class = 'app' onclick="open_webreport()">
      <div class="app_icon">
        <a href="http://10.0.125.7:8008" target="_blank">亚太财险</a>
      </div>
    </div>
    </br>
    <div class = 'app' onclick="open_T6()">
      <div class="app_icon">
        <a href="http://10.0.125.7:8008" target="_blank">泛海三江</a>
      </div>
    </div>
    <div class = 'app' onclick="open_hr()">
      <div class="app_icon">
        <a href="http://10.0.125.7:8008" target="_blank">泛海物业</a>
      </div>
    </div>
    <div class = 'app' onclick="open_webreport2()">
      <div class="app_icon">
        <a href="http://10.0.125.7:8008" target="_blank">境外</a>
      </div>
    </div>
  </div>
</div>

</body>
</html>
