<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<div id="s_tbar">
	<div class="s_hd">
		<c:if test="${!empty currentUser }">
			<div class="tbar_lft">${currentUser.userName }，欢迎来到EGO商城！<a href="${ctx }/user/logout">退出</a></a></div>
		</c:if>
		<c:if test="${empty currentUser }">
			<div class="tbar_lft">欢迎来到EGO商城！<a href="#">请登录</a> | <a href="#">免费注册</a></div>
		</c:if>
		<div class="tbar_rgt">
			<ul>
				<li class="first"><a href="#">我的订单</a></li>
				<li><a href="#">我的EGO商城</a></li>
				<li><a href="#">帮助中心</a></li>
				<li><a href="#">联系客服</a></li>
				<li><a href="#">加入收藏</a></li>
				<li class="s_tel_str">服务热线：</li>
				<li class="s_tel">400-009-1906</li>
			</ul>
		</div>
	</div>
</div>