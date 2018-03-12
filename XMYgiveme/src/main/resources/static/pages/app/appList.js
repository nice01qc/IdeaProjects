var items_per_page = 20; 
var current_page = 1;
var pageTotal = 0;
var trThis;
function getDataList(){ 
	var urlstr = "/app/list";
	if($('#platform').val() != '')
		urlstr += "?platform="+$('#platform').val();
    $.ajax({  
        type: "get",  
        url: urlstr,  
        data: {'name':$('#name').val(),'starttime':$('#registerStartTime').val(),'endtime':$('#registerEndTime').val(),
        	'email':$('#email').val(),'appKey':$('#appKey').val(),'pageNo':current_page,'pageSize':items_per_page},  
        dataType: 'json',  
        contentType: "application/x-www-form-urlencoded",  
        success: function(msg){
            var time; 
            var html = '';
            $('#appList').html(html);
            $.each(msg.rows,function(i,n){  
            	if(i%2 == 0){
            		html +='<tr class="tr-b">';
            	}else 
            		html +='<tr class="tr-a">';
            	if(msg.rows[i].name.length  >20){
            		var name = msg.rows[i].name;
            		msg.rows[i].name =msg.rows[i].name.substring(0,17)+'...'
            		html +='<td class="td-d" title='+formatTitleString(name)+'>'+msg.rows[i].name+'</td>';
            	}else
            		html +='<td class="td-d">'+msg.rows[i].name+'</td>';
            	
            	html +='<td class="td-d" style="width:190px; word-wrap: break-word; word-break: break-all;">'+msg.rows[i].appKey+'</td>';
            	
            	html +='<td class="td-d" style="width:190px; word-wrap: break-word; word-break: break-all;">'+msg.rows[i].appSecret+'</td>';
            	if(msg.rows[i].platform == 1)
            		html +='<td class="td-d">iPhone</td>';
            	else 
            		html +='<td class="td-d">Android</td>';
            	if(msg.rows[i].showTimes ==null)
            		html +='<td class="td-a">0</td>';
            	else
            		html +='<td class="td-a">'+msg.rows[i].showTimes+'</td>';
            	if(msg.rows[i].clickTimes ==null)
            		html +='<td class="td-a">0</td>';
            	else
            		html +='<td class="td-a">'+msg.rows[i].clickTimes+'</td>';
            	html +='<td class="td-a">'+msg.rows[i].clickRate+'</td>';
            	html +='<td class="td-f">'+new Date(msg.rows[i].createTime).format("yyyy-MM-dd hh:mm")+'</td>';
            	var adstatus = msg.rows[i].status;
            	if(adstatus == 0) adstatus = '待审核';
            	else if(adstatus == 1) adstatus = '未激活';
            	else if(adstatus == 2) adstatus = '未通过';
            	else if(adstatus == 3) adstatus = '通过';
            	html +='<td class="td-g">'+adstatus+'</td>';
            	html +='<td class="td-d">'+msg.rows[i].email+'</td>';
            	html +='<td class="td-d"  ><a href="javascript:void(0)" onclick="openTr('+msg.rows[i].id+')">详情</a></td>';
            	html +='<td class="td-f">';
            	if(adstatus != '未通过'){
            		if(adstatus == '待审核')
            			html +='<button class="button_8" onclick="successTr(this,'+msg.rows[i].id+')">通过</button>';
            		html +=' <button class="button_8" onclick="showAudit(this,'+msg.rows[i].id+')">驳回</button>';
            	}
            	html +="</td></tr>";
            }); 
            if(html == ''){
            	html ='<tr class="tr-a" ><td colspan =12>没有找到匹配的记录</td></tr>'
            	$("#Pagination").hide();		
            }else
            	$("#Pagination").show();	
            $('#appList').html(html);
            if(msg.total == 0)
            	$('.page-info').text('0 / 0');
            else{
            	pageTotal = Math.ceil(msg.total/items_per_page);
            	 $('.page-info').text(current_page+' / '+pageTotal);
            }
            $("#current_page").val('');
            updatePageBtnStatus();
        }  
    });  
} 

function openTr(id){
	javascript:location.href='appDetail.html?id='+id;
}

function successTr(nowTr,id){
	if(confirm('您确定要审核通过这个APP吗 ？')){
		 $.ajax({  
		        type: "get",  
		        url: '/app/auditPass',  
		        data: {'id':id},  
		        dataType: 'json',  
		        contentType: "application/x-www-form-urlencoded",  
		        success: function(msg){ 
		        	if(msg.status == 200){
		        		$(nowTr).parent().parent().children('td').eq(8).html('未激活'); 
 		        	$(nowTr).parent().html('<button class="button_8" onclick="showAudit(this,'+id+')">驳回</button>'); 
		        	}else if(msg.status == 400){
		        		alert('审核失败，可能由于包名重复！');
		        	}
		        }
		 });
	}
}

function submitAudit(){
	var id= $("#auditId").val();
	if($('#reason').val() == '')
		alert('驳回理由不能为空，请输入内容!');
	else{ 
		$.ajax({  
	        type: "get",  
	        url: "/app/auditNotPass",
	        data: {'id':id,'reason':$('#reason').val()},
	        dataType: 'json',  
	        contentType: "application/x-www-form-urlencoded",  
	        success: function(msg){ 
	        	$(trThis).parent().parent().children('td').eq(8).html('未通过'); 
	        	$(trThis).parent().html('');
	        	closeAudit();
	        }
		});
	}
}

$(function(){
	 $('.date-picker').datepicker({
        language : "zh-CN",
        autoclose : true,
        todayHighlight : true,
        clearBtn : true
    })
    
	 $("#registerStartTime").datepicker().on("changeDate", function(e) {
        $("#registerEndTime").datepicker("setStartDate", $("#registerStartTime").datepicker("getDate"));
    });
	 
	 $("#registerEndTime").datepicker().on("changeDate", function(e) {
	        $("#registerStartTime").datepicker("setEndDate", $("#registerEndTime").datepicker("getDate"));
	    });
	 
	getDataList();

	$("#pageNumClick").click(function(){
		var goToPageNumStr = $("#current_page").val();
		$("#current_page").val("");
		if( goToPageNumStr != '' && !isNaN(goToPageNumStr)) {
			var goToPageNum = parseInt(goToPageNumStr);
			if(goToPageNum <= 0) {
				goToPageNum = 1;
			} else if(goToPageNum > pageTotal) {
				goToPageNum = pageTotal;
			}
			//判断是否跳转到当前页
			if(goToPageNum == current_page) {
				return false;
			} else {
				current_page = goToPageNum;
				scrollToTableHead();
				getDataList();
			}
    	}
	}); 
	
	$(".next-page").click(function () {
        if (current_page + 1 <= pageTotal) {
            current_page = current_page + 1;
            scrollToTableHead();
            getDataList();
        }
    });

    $(".pre-page").click(function () {
        if (current_page - 1 > 0) {
            current_page = current_page - 1;
            scrollToTableHead();
            getDataList();
        }
    });
	
	$("#searchBtn").click(function(){
		current_page = 1;
		getDataList(); 
	});

});

/**
 * 更新翻页按钮状态
 * @returns
 */
function updatePageBtnStatus() {
	$(".pagination .next-page").removeClass("unused");
	$(".pagination .pre-page").removeClass("unused");
	if(current_page == pageTotal) {
		$(".pagination .next-page").addClass("unused");
	}
	if(current_page == 1) {
		$(".pagination .pre-page").addClass("unused");
	}
}

function showAudit(nowTr, id) {
	$('#reason').val('');
	$("#auditId").val(id);
	trThis = nowTr;
	$("#appAudit").show();	
}

function closeAudit() { 
	$("#auditId").val('');
	$("#appAudit").hide();	
}