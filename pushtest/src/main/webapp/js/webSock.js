var websocket = null;
var windowroom = "";
var imgNum = 1;
//判断当前浏览器是否支持WebSocket
if ('WebSocket' in window) {
    websocket = new WebSocket(window.sockSite + "websocket");
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
    if (event.data.match(/jpeg;base64/g)) {
        setImg(event.data);
    } else if (event.data.match(/^imgNum:[0-9]+$/g)) {
        document.getElementById("imgNum").innerHTML = event.data;
    } else if (event.data.match(/^TextNum:[0-9]+$/g)) {
        document.getElementById("TextNum").innerHTML = event.data;
    } else {
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

    if (message.match(/^[a-zA-Z0-9]+$/g)) {
        websocket.send(message);
        windowroom = message;
        alert("ok get room, you can receive message ! or the room is reseted !");
        var messagenode = document.getElementById('message');
        messagenode.innerHTML = "<div>以下消息双击可以删除,图片也可以</div>";
    } else {
        alert("房间号码无效!");
    }

}

//将消息显示在网页上
function setMessageInnerHTML(innerHTML) {
    var message = document.getElementById('message');
    var div = document.createElement("div");
    var textNode = document.createTextNode(innerHTML);
    div.append(textNode);
    div.ondblclick = function () {
        this.parentNode.removeChild(this);
    }
    message.appendChild(div);
}

// 将图片加入imgdir 必须完整的格式，且以“-”加数据结尾
function setImg(imgdir) {
    var imgArea = document.getElementById('imgArea');
    var div = document.createElement("div");

    var buttom0 = document.createElement("button");
    buttom0.type = "button";
    buttom0.innerHTML = imgNum;
    imgNum = imgNum+1;

    var buttom1 = document.createElement("button");
    buttom1.type = "button";
    buttom1.innerHTML = "查看";
    var buttom2 = document.createElement("button");
    buttom2.type = "button";
    buttom2.innerHTML = "删除";



    var img = document.createElement("img");
    img.src = imgdir;

    div.appendChild(img);
    div.appendChild(buttom0);
    div.appendChild(buttom1);
    div.appendChild(buttom2);

    div.ondblclick = function () {
        this.parentNode.removeChild(this);
    };
    buttom2.onclick=function(){
        imgArea.removeChild(div);
    };

    buttom1.onclick=function () {
        showbigimg(img.src);
    }
    img.onclick = function () {
        showbigimg(this.src);
    }
    imgArea.appendChild(div);
}

function showbigimg(imgsrc) {
    var showdiv = document.getElementById("showbigimg");
    while(showdiv.hasChildNodes()) //当div下还存在子节点时 循环继续
    {
        showdiv.removeChild(showdiv.firstChild);
    }
    showdiv.opacity = "1";
    showdiv.height = "100%";
    var img = document.createElement("img");
    img.src = imgsrc;
    showdiv.appendChild(img);
}
document.getElementById("showbigimg").onclick=function () {
    while(this.hasChildNodes()) //当div下还存在子节点时 循环继续
    {
        this.removeChild(this.firstChild);
    }
    this.opacity = "0";
    this.height="0px";
}



// 以下是提交答案的
var headinput = document.getElementById("headinput");
var headtijiao = document.getElementById("headtijiao");
headtijiao.onclick = function () {
    if (!windowroom.match(/^[a-zA-Z0-9]+$/g)){
        alert("非法房间号");
        headinput.value = "no 发送";
    } else {
        var text = headinput.value;
        var xhr = new XMLHttpRequest();
        xhr.open('POST', window.site + 'result', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.send("result=" + encodeURIComponent(text)+ "&room=" + encodeURIComponent(windowroom));

        headinput.value = "ok,已发送";
    }

}