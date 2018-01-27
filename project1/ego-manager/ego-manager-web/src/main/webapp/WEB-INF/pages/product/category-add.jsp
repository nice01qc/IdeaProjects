<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
  <%@ include file="../head.jsp" %>
  
  <link href="${ctx }/Public/css/fileinput.min.css" rel="stylesheet">
  </head>
  <body style="background-color:#ecf0f5;">
<div class="wrapper">
    <div class="breadcrumbs" id="breadcrumbs">
	<ol class="breadcrumb">
	<li><a href="javascript:void();"><i class="fa fa-home"></i>&nbsp;&nbsp;后台首页</a></li>
	        
	        <li><a href="javascript:void();">商品管理</a></li>    
	        <li><a href="javascript:void();">添加修改分类</a></li>          
	</ol>
</div>

		<section class="content">
            <div class="row">
                <div class="col-sm-12">
                    <div class="box">
                        <div class="box-header">
                            <h3 class="box-title">增加分类</h3>
			                <div class="pull-right">
			                	<a href="javascript:history.go(-1)" data-toggle="tooltip" title="" class="btn btn-default" data-original-title="返回"><i class="fa fa-reply"></i></a>
			                	<a href="javascript:;" class="btn btn-default" data-url="http://www.ego.cn/Doc/Index/article/id/1006/developer/user.html" onclick="get_help(this)"><i class="fa fa-question-circle"></i> 帮助</a>
			                </div>
                        </div>
 
                        <!-- /.box-header -->
                        <form action="" method="post" class="form-horizontal" id="category_form">
                        <div class="box-body">                         
                                <div class="form-group">
                                     <label class="col-sm-2 control-label">分类名称</label>
                                     <div class="col-sm-6">
                                        <input type="text" placeholder="名称" class="form-control large" name="name" value="">
                                        <span class="help-inline" style="color:#F00; display:none;" id="err_name"></span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-2">手机分类名称</label>
                                    <div class="col-sm-6">
                                        <input type="text" placeholder="手机分类名称" class="form-control large" name="mobileName" value="">
                                        <span class="help-inline" style="color:#F00; display:none;" id="err_mobile_name"></span>
                                    </div>
                                </div> 
                                <div class="form-group">
                                    <label0 class="control-label col-sm-2">上级分类</label0>
                                    
                                    <input type="hidden" name="parentId" id="parentId" value="0"/>
                                    <input type="hidden" name="level" id="level" value="1"/>
                                    <input type="text" name="image" id="image" value=""/>
                                    
                                    <div class="col-sm-3">
                                        <select name="parent_id_1" id="parent_id_1" onchange="getCategory(this.value,'parent_id_2','2');" class="small form-control">
	                                        <option value="0">顶级分类</option>
	                                        <c:forEach var="gc" items="${gcList }">
	                                        	<option value="${gc.id }">${gc.name }</option>
	                                        </c:forEach>
										</select>
                                    </div>                                    
                                    <div class="col-sm-3">
                                      <select name="parent_id_2" id="parent_id_2" onchange="setParentId(this.value,'3')" class="small form-control">
                                        <option value="0">请选择商品分类</option>
                                      </select>  
                                    </div>                                      
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-2">导航显示</label>
									
                                    <div class="col-sm-10">
                                        <label> 
                                            <input checked="checked" type="radio" name="isShow" value="1"> 是
                                                <input type="radio" name="isShow" value="0"> 否
                                                                                                                                                                                
                                        </label>
                                    </div>
                                </div>
				<div class="form-group">
                                    <label class="control-label col-sm-2">分类分组:</label>
									
                                    <div class="col-sm-1">
                                      <select name="catGroup" id="catGroup" class="form-control">
                                        <option value="0">0</option>                                        
                                        <option value='1' >1</option>"
                                        <option value='2' >2</option>"
                                        <option value='3' >3</option>"
                                        <option value='4' >4</option>"
                                        <option value='5' >5</option>"
                                        <option value='6' >6</option>"
                                        <option value='7' >7</option>"
                                        <option value='8' >8</option>"
                                        <option value='9' >9</option>"
                                        <option value='10' >10</option>"
                                        <option value='11' >11</option>"
                                        <option value='12' >12</option>"
                                        <option value='13' >13</option>"
                                        <option value='14' >14</option>"
                                        <option value='15' >15</option>"
                                        <option value='16' >16</option>"
                                        <option value='17' >17</option>"
                                        <option value='18' >18</option>"
                                        <option value='19' >19</option>"
                                        <option value='20' >20</option>"
                                      </select>                                        
                                    </div>                                    
                                </div>   
                             
				  <div class="form-group">
	                                    <label class="control-label col-sm-2">分类展示图片</label>

                                    <div class="col-sm-10">
                                    	<form enctype="multipart/form-data">
						                    <input id="file-product-category" class="file" name="file" type="file" data-min-file-count="1">
						                    <br>
						                </form>
                                    </div>
                                </div>                                
                               <div class="form-group">
                                    <label class="control-label col-sm-2">显示排序</label>
                                    <div class="col-sm-1">
                                        <input type="text" placeholder="50" class="form-control large" name="sort_order" value=""/>
                                        <span class="help-inline" style="color:#F00; display:none;" id="err_sort_order"></span>
                                    </div>
                                </div>
								<div class="form-group">
                                    <label class="control-label col-sm-2">分佣比例</label>
                                    <div class="col-sm-1">
                                        <input type="text" placeholder="50" class="form-control large" name="commission_rate" id="commission_rate" value="0" onpaste="this.value=this.value.replace(/[^\d.]/g,'')" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')"/>
                                    </div>
                                    <div class="col-sm-1" style="margin-top: 6px;margin-left: -20px;">
                                        <span>%</span> 
                                    </div>
                                </div>                                								                                                               
                        </div>
                        <div class="box-footer">                        	
                            <input type="hidden" name="id" value="">                           
                        	<button type="reset" class="btn btn-primary pull-left"><i class="icon-ok"></i>重填  </button>                       	                 
                            <button type="button" onclick="submitCategory()" class="btn btn-primary pull-right"><i class="icon-ok"></i>提交  </button>
                        </div> 
                    </div>
                </div>
            </div>
        </section>
</div>
<script src="${ctx }/Public/js/fileinput.js" type="text/javascript"></script>
<script src="${ctx }/Public/js/fileinput_locale_zh.js" type="text/javascript"></script>
<script>
	//初始设置 language指定语言 uploadUrl指定文件上传的后台地址 allowedPreviewTypes允许上传文件的类型
    $('#file-product-category').fileinput({
        language: 'zh',
        uploadUrl: '${ctx}/fileUpload/save',
        allowedPreviewTypes : ['image', 'html', 'text', 'video', 'audio', 'flash']
    });
	//上传文件失败后 调用的方法（回调函数）
    $('#file-product-category').on('fileuploaderror', function(event, data, previewId, index) {
        var form = data.form, files = data.files, extra = data.extra,
                response = data.response, reader = data.reader;
        console.log(data);
        console.log('File upload error');
    });
	//文件错误 比如文件类型错误 会调用此函数（回调函数）
    $('#file-product-category').on('fileerror', function(event, data) {
        console.log(data.id);
        console.log(data.index);
        console.log(data.file);
        console.log(data.reader);
        console.log(data.files);
    });
	//文件上传成功之后 回调的函数
    $('#file-product-category').on('fileuploaded', function(event, data, previewId, index) {
        var form = data.form, files = data.files, extra = data.extra,
                response = data.response, reader = data.reader;
        $("#image").val("http://"+data.response.fileUrl);
        console.log('File uploaded triggered');
    });
</script>
 <script type="text/javascript">
 	
 	//根据pid 获取子分类 
 	function getCategory(parentId,nextNode,level){
 		
 		$("#parentId").val(parentId);
 		if(0==parentId){
 			$("#level").val(1);
 		}else{
 			$("#level").val(level);
 		}
 		
 		//ajax 从后台加载 子分类
 		$.ajax({
 			url:"${ctx}/product/category/getByPid",
 			type:"POST",
 			data:{"parentId":parentId},
 			dataType:"JSON",
 			success:function(result){
 				var htmlStr='<option value="0">请选择商品分类</option>';
 				for(i=0;i<result.length;i++){
 					htmlStr+='<option value="'+result[i].id+'">'+result[i].name+'</option>';
 				}
 				
 				$("#"+nextNode).html(htmlStr);
 			},
 			error:function(){
 				alert("获取子分类失败");
 			}
 		})
 	}
 	//设置隐藏域中 parentId值
 	function setParentId(parentId,level){
 		if(0==parentId){
 			$("#parentId").val($("#parent_id_1").val());
 			$("#level").val(2);
 		}else{
 			$("#parentId").val(parentId);
 	 		$("#level").val(level);
 		}
 	}
 	
 	function submitCategory(){
 		//ajax 从后台加载 子分类
 		$.ajax({
 			url:"${ctx}/product/category/save",
 			type:"POST",
 			data:$("#category_form").serialize(),
 			dataType:"JSON",
 			success:function(result){
 				if(200==result.code){
 					layer.alert("保存成功");
 				}
 			},
 			error:function(){
 				layer.alert("保存失败");
 			}
 		})
 	}
 	
 
    function delfunc(obj){
    	layer.confirm('确认删除？', {
    		  btn: ['确定','取消'] //按钮
    		}, function(){
   				$.ajax({
   					type : 'post',
   					url : $(obj).attr('data-url'),
   					data : {act:'del',del_id:$(obj).attr('data-id')},
   					dataType : 'json',
   					success : function(data){
   						if(data==1){
   							layer.msg('操作成功', {icon: 1});
   							$(obj).parent().parent().remove();
   						}else{
   							layer.msg(data, {icon: 2,time: 2000});
   						}
   						layer.closeAll();
   					}
   				})
    		}, function(index){
    			layer.close(index);
    			return false;// 取消
    		}
    	);
    }
    
    //全选
    function selectAll(name,obj){
    	$('input[name*='+name+']').prop('checked', $(obj).checked);
    }   
    
    function get_help(obj){
        layer.open({
            type: 2,
            title: '帮助手册',
            shadeClose: true,
            shade: 0.3,
            area: ['90%', '90%'],
            content: $(obj).attr('data-url'), 
        });
    }
    
    function delAll(obj,name){
    	var a = [];
    	$('input[name*='+name+']').each(function(i,o){
    		if($(o).is(':checked')){
    			a.push($(o).val());
    		}
    	})
    	if(a.length == 0){
    		layer.alert('请选择删除项', {icon: 2});
    		return;
    	}
    	layer.confirm('确认删除？', {btn: ['确定','取消'] }, function(){
    			$.ajax({
    				type : 'get',
    				url : $(obj).attr('data-url'),
    				data : {act:'del',del_id:a},
    				dataType : 'json',
    				success : function(data){
    					if(data == 1){
    						layer.msg('操作成功', {icon: 1});
    						$('input[name*='+name+']').each(function(i,o){
    							if($(o).is(':checked')){
    								$(o).parent().parent().remove();
    							}
    						})
    					}else{
    						layer.msg(data, {icon: 2,time: 2000});
    					}
    					layer.closeAll();
    				}
    			})
    		}, function(index){
    			layer.close(index);
    			return false;// 取消
    		}
    	);	
    }
    </script> 
   
</body>
</html>