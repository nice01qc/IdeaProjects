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
    <title>message</title>
    <link href="css/webSock.css" rel="stylesheet" type="text/css"/>
	<script src="js/para.js" ></script>
</head>
<body>

	<div id="showbigimg"></div>


    <div id="textArea">
		<div>
			提交答案：<input type="text=" id="headinput" value="">
			<button id="headtijiao">提交</button>
		</div>
    	<span>输入接收消息房间号：</span><br/><input id="room" type="text"/>
    	<button onclick="sendRoom()">确定</button>
    	<button onclick="closeWebSocket()">关闭连接</button>
    	<span id="imgNum"></span><span id="TextNum"></span><hr/>
    	<div id="message">
    		<div id="firstdiv">以下消息双击可以删除,图片也可以</div>
    	</div>
    </div>
    <div id="imgArea"></div>

<script src="js/webSock.js"></script>

</body>

</html>
