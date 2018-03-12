$(function(){
	$.ajax({  
        type: "get",  
        url: "/app/getAppDetail",
        data: {'id':location.search.substr(4)},
        dataType: 'json',  
        contentType: "application/x-www-form-urlencoded",  
        success: function(msg){ 
        	var html = '<div class="module"><div class="module_l" >App名称</div><div class="module_c"><input class="input_2" type="text" value ="'+msg.name+'" readonly></div><div class="clearfloat"></div></div>';
        	if(msg.platform == 1){
        		html +='<div class="module"><div class="module_l" >设备</div><div class="module_c"><input class="input_2" type="text" value ="iPhone" readonly></div><div class="clearfloat"></div></div>';
        		html +='<div class="module"><div class="module_l" >iTunes ID</div><div class="module_c"><input class="input_2" type="text" value ="'+msg.fullName+'" readonly></div><div class="clearfloat"></div></div>';
        		html +='<div class="module"><div class="module_l" >BundleID</div><div class="module_c"><input class="input_2" type="text" value ="'+msg.packageName+'" readonly></div><div class="clearfloat"></div></div>';
        	}else{
        		html +='<div class="module"><div class="module_l" >设备</div><div class="module_c"><input class="input_2" type="text" value ="Android" readonly></div><div class="clearfloat"></div></div>';
        		/*html +='<div class="module"><div class="module_l" >APP全名</div><div class="module_c"><input class="input_2" type="text" value ="'+msg.fullName+'" readonly></div><div class="clearfloat"></div></div>';*/
        		html +='<div class="module"><div class="module_l" >APP包名</div><div class="module_c"><input class="input_2" type="text" value ="'+msg.packageName+'" readonly></div><div class="clearfloat"></div></div>';
        		html +='<div class="module"><div class="module_l" >下载地址</div><div class="module_c"><input class="input_2" type="text" value ="'+msg.url+'" readonly></div><div class="clearfloat"></div></div>';
        	} 
        	html +='<div class="module"><div class="module_l">App类型</div>';
        	if(msg.tags.length  > 0){
        		html +='<div class="module_c_info " style="width:276px">';
	        	for(var i=0; i < msg.tags.length; i++){
	        		html +='<div class="module_c"><input class="input_5 cancel_sy" value="'+msg.tags[i].tag+'" readonly style="margin-top:15px;margin-bottom:10px"/></div>';
	        	}
	        	html +='</div>';
        	}
        	html +='<div class="clearfloat"></div></div>';      
        	$('.content_m_info').html(html);
        }
	});
});