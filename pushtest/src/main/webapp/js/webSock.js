    var websocket = null;
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://118.25.1.128:8080/pushtest/websocket");
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
        if (event.data.match(/(.jpeg)$|(.png)$|(.jpg)$/g)) {
            setImg(event.data);
        }else{
            setMessageInnerHTML(event.data);    // 此处处理此处代码
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
    //关闭WebSocket连接
    function closeWebSocket() {
        websocket.close();
    }

    //发送消息
    function sendRoom() {
        var message = document.getElementById('room').value;
        websocket.send(message);
        alert("ok get room, you can receive message ! or the room is reseted !");
    }

    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
        var message = document.getElementById('message');
        var div = document.createElement("div");
        var textNode = document.createTextNode(innerHTML);
        div.append(textNode);
        div.ondblclick=function(){
            this.parentNode.removeChild(this);
        }
        message.append(div);
    }

    // 将图片加入imgdir 必须完整的格式，且以“-”加数据结尾
    function setImg(imgdir){
        var imgArea = document.getElementById('imgArea');
        var img = document.createElement("img");
        img.src = imgdir;
        img.ondblclick=function(){
            this.parentNode.removeChild(this);
        }
        imgArea.append(img);
    }