<%--
  Created by IntelliJ IDEA.
  User: nice01qc
  Date: 2018/4/16
  Time: 15:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <script type="text/javascript" >

        alert("begin");
        var xhr = new XMLHttpRequest();
        xhr.open('POST','http://localhost:8080${pageContext.request.contextPath}/json/testRequestBody',true);
        xhr.setRequestHeader('Content-Type','application/json');
        xhr.send(JSON.stringify({id:23,name:"jake"}));
        xhr.onload = function () {
            var responseText = xhr.responseText;
            console.log(responseText);
        }



    </script>


</body>
</html>
