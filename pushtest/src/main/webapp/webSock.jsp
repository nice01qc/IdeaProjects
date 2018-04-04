<%--
  Created by IntelliJ IDEA.
  User: nice01qc
  Date: 2018/4/4
  Time: 21:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>WebSocket</title>
    <link href="css/webSock.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <div id="textArea">
    	输入接收消息房间号：<br/><input id="room" type="text"/>
    	<button onclick="sendRoom()">发送消息</button><hr/>
    	<button onclick="closeWebSocket()">关闭WebSocket连接</button><hr/>
    	<div id="message"></div>
    </div>
    <div id="imgArea"></div>

</body>

<script src="js/webSock.js"></script>
</html>
