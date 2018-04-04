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
    <title>Java后端WebSocket的Tomcat实现</title>
</head>
<body>
    输入接收消息房间号：<br/><input id="text" type="text"/>
    <button onclick="sendRoom()">发送消息</button><hr/>
    <button onclick="closeWebSocket()">关闭WebSocket连接</button><hr/>
    <div id="message"></div>

</body>

<script type="text/javascript">
    var websocket = null;
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://localhost:8080/pushtest/websocket");
    }
    else {
        alert('当前浏览器 Not support websocket')
    }

    //连接发生错误的回调方法
    websocket.onerror = function () {
        setMessageInnerHTML("WebSocket连接发生错误");
    };

    //连接成功建立的回调方法
    websocket.onopen = function () {
        setMessageInnerHTML("WebSocket连接成功");
    }

    //接收到消息的回调方法
    websocket.onmessage = function (event) {
        setMessageInnerHTML(event.data);    // 此处处理此处代码
        switch (request.directive) {
            case 'room': {
                room = request.roomNum;
                alert("成功设置房间号：" + room);
                break;
            }
            case 'img': {
                semdImg();
                break;
            }
            case 'text': {
                semdtext(request.selText);
                break;
            }
        }
    }

    //连接关闭的回调方法
    websocket.onclose = function () {
        setMessageInnerHTML("WebSocket连接关闭");
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        closeWebSocket();
    }

    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
        document.getElementById('message').innerHTML += innerHTML + '<br/>';
    }

    //关闭WebSocket连接
    function closeWebSocket() {
        websocket.close();
    }

    //发送消息
    function sendRoom() {
        var message = document.getElementById('text').value;
        websocket.send(message);
    }
</script>
</html>
