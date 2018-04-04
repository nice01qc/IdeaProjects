
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
      format : "png",
      quality : 100
  }, function(data) {
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'http://localhost:8080/pushtest/img', true);

    xhr.onload = function(e) {
        if (this.status == 200) {
        alert(this.response);
        }
    };
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send("img="+encodeURIComponent(data) + "&room=" + encodeURIComponent(room));

    });
}


function semdtext(text){
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'http://localhost:8080/pushtest/text', true);

    xhr.onload = function(e) {
        if (this.status == 200) {
        alert(this.response);
        }
    };
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send("text="+encodeURIComponent(text) + "&room=" + encodeURIComponent(room));
}

