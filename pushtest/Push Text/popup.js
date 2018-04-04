var myroom = document.getElementById("myroom");
var mybutton = document.getElementById("mybutton");
var myshow = document.getElementById("show");

var localRootNum = localStorage.getItem("room");
if (localRootNum) {
  myshow.value = localRootNum;
}


mybutton.onclick=function(){
  
  chrome.runtime.sendMessage({
    directive: 'room',
    roomNum: myroom.value
  }, null);

  localStorage.removeItem("room");
  localStorage.setItem("room",myroom.value);
};