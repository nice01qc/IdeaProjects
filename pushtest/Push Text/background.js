
var room = "123";
chrome.runtime.onMessage.addListener(function (request, sender, callback) {
    //alert(request.selText);
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
    
});

function semdImg(){
  chrome.tabs.captureVisibleTab(null, {
      format : "jpeg",
      quality : 50
  }, function(data) {
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'http://118.25.1.128:8080/pushtest//img', true);

    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send("img="+encodeURIComponent(data) + "&room=" + encodeURIComponent(room));

    });
}


function semdtext(text){
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'http://118.25.1.128:8080/pushtest/text', true);

    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send("text="+encodeURIComponent(text) + "&room=" + encodeURIComponent(room));
}

