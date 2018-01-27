<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<script type="text/javascript" src="${ctx}/static/js/layer/layer.js"></script>
<div id="s_cart">
	<ul>
		<li class="nums">
		<a href="javascript:void(0);">购物车： <span id="s_cart_nums1">0</span>  件</a> 
		<a href="" class="btn" id="s_cart_nums2"></a></li>
		<li class="checkout"><a href="javascript:void(0);">去结算>></a></li>
	</ul>
</div>
<script type="text/javascript">
	$(function(){
		//获取购物车中的数量并展示
		getCartNum();
	});
	//获取购物车中的数量并展示
	function getCartNum(){
		$.ajax({
			url:"${ctx}/cart/getCartNum",
			type:"POST",
			dataType:"JSON",
			success:function(res){
				$("#s_cart_nums1").text(res);
				$("#cart_num").text($("#s_cart_nums1").text());
			},
			error:function(){
				alert("系统正在升级中，请稍后再试");				
			}
		});
	}
	//添加至购物车
	function addToCart(goodsId,goodsName,goodsPrice,goodsImage){
		$.ajax({
			url:"${ctx}/cart/addToCart",
			type:"POST",
			data:{"goodsId":goodsId,"goodsName":goodsName,"goodsPrice":goodsPrice,"goodsImage":goodsImage,"goodsNum":1},
			dataType:"JSON",
			success:function(res){
				if(200==res.code){
					var crrentNum = parseInt($("#s_cart_nums1").text());
					$("#s_cart_nums1").text(crrentNum+1);
					layer.msg('添加至购物车成功', {
					  offset: 't',
					  anim: 6
					});
				}
			},
			error:function(){
				alert("系统正在升级中，请稍后再试");				
			}
		});
	}
</script>