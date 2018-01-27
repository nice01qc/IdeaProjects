<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>ego | 后台登陆</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.4 -->
    <link href="${ctx}/Public/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <!-- Theme style -->
    <link href="${ctx}/Public/dist/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
    <!-- iCheck -->
    <link href="${ctx}/Public/plugins/iCheck/square/blue.css" rel="stylesheet" type="text/css" />
    <style>#imgVerify{width: 120px;margin: 0 auto; text-align: center;display: block;}	</style>
    <script>    
    function detectBrowser()
    {
	    var browser = navigator.appName
	    if(navigator.userAgent.indexOf("MSIE")>0){ 
		    var b_version = navigator.appVersion
			var version = b_version.split(";");
			var trim_Version = version[1].replace(/[ ]/g,"");
		    if ((browser=="Netscape"||browser=="Microsoft Internet Explorer"))
		    {
		    	if(trim_Version == 'MSIE8.0' || trim_Version == 'MSIE7.0' || trim_Version == 'MSIE6.0'){
		    		alert('请使用IE9.0版本以上进行访问');
		    		return;
		    	}
		    }
	    }
   }
    detectBrowser();
   </script>
  </head>
  <body class="login-page">
    <div class="login-box">
      <div class="login-logo">
        <a href="#"><b>ego</b></a>
      </div>
      <div class="login-box-body">
        <p class="login-box-msg">管理后台</p>
          <form id="formlogin">
	          <div class="form-group has-feedback">
	            <input type="text" name="userName" id="username" class="form-control" placeholder="账号" />
	            <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
	          </div>
	          <div class="form-group has-feedback">
	            <input type="password" name="password" class="form-control" id="password" placeholder="密码" />
	            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
	          </div>
	          <div class="form-group">
	          	 <button type="button" onclick="userLogin()" class="btn btn-primary btn-block btn-flat" onclick="checkLogin()">立即登陆 </button>  
	          </div>
          </form>
      </div>
      
	    <div class="margin text-center">
	        <div class="copyright">
	            2014-2016 &copy; <a href="http://www.ego.cn">ego v1.3.3</a>
	            <br/>
	            <a href="http://www.ego.cn">北京506网络有限公司</a>出品
	        </div>
	    </div>
    </div><!-- /.login-box -->
    <!-- jQuery 2.1.4 -->
    <script src="${ctx}/Public/plugins/jQuery/jQuery-2.1.4.min.js" type="text/javascript"></script>
    <!-- Bootstrap 3.3.2 JS -->
    <script src="${ctx}/Public/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <!-- iCheck -->
    <script src="${ctx}/Public/plugins/iCheck/icheck.min.js" type="text/javascript"></script>
	<script src="${ctx}/Public/js/layer/layer.js"></script><!-- 弹窗js 参考文档 http://layer.layui.com/-->
    
  </body>
  <script type="text/javascript">
	function userLogin(){
		$.ajax({
			url:"${ctx}/user/login",
			type:"POST",
			data:$("#formlogin").serialize(),
			dataType:"JSON",
			success:function(res){
				if(200==res.code){
					location.href="${ctx}/index";
				}
			},
			error:function(){
				alert("系统正在升级中，请稍后再试");				
			}
		});
	}
</script>
</html>