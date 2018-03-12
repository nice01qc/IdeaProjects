var items_per_page = 20; 
var current_page = 1;
var pageTotal = 0;
var trThis;

var resLocationType = {
	"0": "手机横幅",
	"1": "手机全屏",
	"2": "手机插屏",
	"3": "手机开机画面",
	"4": "定制轮播",
	"5": "云POS闲时",
	"6": "云POS忙时",
	"7": "Beacon插屏",
	"8": "智能储物柜",
	"9": "H5",
	"10":"信息流广告"
};

function validateNumber(oNum){
	if (!oNum) {
		return false;
	}
	var strP = /^[0-9]*[1-9][0-9]*$/;
	if (!strP.test(oNum)) {
		return false;
	}
	try {
		if (parseInt(oNum) != oNum) {
			return false;
		}
	} catch (ex) {
		return false;
	}
	return true;
}

function dateTimeIsNOK(){
	return Date.parse($('#sdate').val()) > Date.parse($('#edate').val());
} 

function checkMyForm(){
	var tmpCreationId = $('#creationId').val();
	if(tmpCreationId!="" && !validateNumber(tmpCreationId)){
		alert("请输入数字");
		document.getElementById("creationId").focus();
		return false;
	}
	if(dateTimeIsNOK()){
		alert("开始时间不能大于结束时间。");
		return false;
	}
	return true;
}


function getDataList(isManual){
	if(isManual==1){
		if(!checkMyForm()){
			return
		}
	}
	var urlstr = "/creation/list?pageIndex="+current_page+"&pageSize="+items_per_page;
	var startTime = $('#sdate').val();
	if(startTime == null || startTime==""){
		startTime = null;
	}else{
		startTime=new Date($('#sdate').val());
		startTime.setHours(0);//将小时设为0
		startTime = startTime.getTime();
	}
	var endTime = $('#edate').val();
	if(endTime==null || endTime==""){
		endTime=null;
	}else{
		endTime=new Date($('#edate').val());
		endTime.setHours(0);
		endTime = endTime.getTime()+24*60*60*1000-1;//结束时间为23:59:59:999
	}
	$.ajax({
		method : "post",
		url : urlstr,
		contentType : 'application/json',
		data : JSON.stringify({
			'id' : $('#creationId').val(),
			'creationName' : $('#creationName').val(),
			'resourceLocationType' : $('#resPositionType').val(),
			'auditStatus' : $('#auditStatus').val(),
			'startTime' : startTime,
			'endTime' : endTime
		}),
        success: function(msg){
        	if(msg.status != 200){
        		$('.page-info').text('1 / 1');
        	    $('#creationList').html('<tr class="tr-a" ><td colspan =11>查询出错</td></tr>');
                return ;
        	}
            var html = '';
            var start_index = (current_page-1) * items_per_page;
            $('#creationList').html(html);
            $.each(msg.result.rows,function(rowIndex,curCreationItem){
            	var index = start_index+rowIndex;
            	if(rowIndex%2 == 0){
            		html +='<tr class="tr-b">';
            	}else{
            		html +='<tr class="tr-a">';
            	}
            	html +='<td class="td-e">'+(index+1)+'</td>';
            	html +='<td class="td-d">'+curCreationItem.id+'</td>';
            	html +='<td class="td-d">'+curCreationItem.creationName+'</td>';
            	var resLoctionType = curCreationItem.resourceLocationType;
            	if(null!=resLoctionType && resLoctionType!=""){
            		resLoctionType = resLocationType[resLoctionType];
            	}
            	html +='<td class="td-d">'+resLoctionType+'</td>';
            	
            	var time = new Date(curCreationItem.createTime);
        		var month = time.getMonth() + 1 < 10 ? "0" + (time.getMonth() + 1) : time.getMonth() + 1;
        		var day = time.getDate() < 10 ? "0" + time.getDate() : time.getDate();
        		var hours=time.getHours() < 10 ? "0" + time.getHours():time.getHours();
        	    var minutes=time.getMinutes() < 10 ? "0" +time.getMinutes():time.getMinutes();
        	    
            	html +='<td class="td-f">'+time.getFullYear() + '-' + month + '-' + day +' ' + hours + ':' + minutes + '</td>';
            	var tmpStatus="空闲中";
            	if(curCreationItem.status==1){
            		tmpStatus="使用中"
            	}
            	html +='<td class="td-f">'+tmpStatus+'</td>';
            	
            	if(curCreationItem.creationPicList.length > 0){
            		html +='<td class="td-f"><img class="img-preview" onclick="showBigPicList(this);" src="'+curCreationItem.creationPicList[0].picUrl+'"/></td>';
            	}
            	else{
            		html +='<td class="td-f"/>';	
            	}
            	html +='<td class="td-d">'+curCreationItem.accountEmail+'</td>';
            	var adstatus = curCreationItem.auditStatus;
            	var adstatus_msg = "未知";
            	if(adstatus == 1){
            		adstatus_msg="正常";
                }else if(adstatus == 2){
                	adstatus_msg="未通过";
            	}else if(adstatus == 0){
            		adstatus_msg="待审核";
            	}
            	html +='<td class="td-d">'+adstatus_msg+'</td>';
            	//html +='<td class="td-d"> <a href="./creationDetail.html?id='+curCreationItem.id+'">详情>></a></td>'
            	html +='<td class="td-d"> <a href="javascript:;" resourcetype="' + curCreationItem.resourceLocationType + '" class="viewAtt">详情>></a></td>';
            	html +='<td class="td-f">';
            	if(adstatus_msg != '未通过'){
            		if(adstatus_msg == '待审核'){
            			html +='<button class="button_8" onclick="successTr(this,'+curCreationItem.id+', ' + curCreationItem.resourceLocationType + ')">通过</button>';
            		}
            		html +=' <button class="button_8" onclick="showAudit(this,'+curCreationItem.id+', ' + curCreationItem.resourceLocationType + ')">驳回</button>';
            	}
            	html +="</td></tr>";
            });
            if(html == ''){
            	html ='<tr class="tr-a" ><td colspan =11>无查询结果</td></tr>';
            	$("#Pagination").hide();
            } else {
            	$("#Pagination").show();
            }
            $('#creationList').html(html);
            
          //注册事件
        	$('#creationList').find("a.viewAtt").unbind().bind("click",function(){ 	
        		var id = $(this).parent().parent().find('td').eq(1).text();
				var isH5 = $(this).attr('resourcetype') == 9 ? 1 : 0;
        		window.location.href='creationDetail.html?id=' + id + '&isH5=' + isH5;
        	});
        	
            if(msg.result.total == 0){
            	$('.page-info').text('1 / 1');
            }
            else{
            	pageTotal = Math.ceil(msg.result.total/items_per_page);
            	 $('.page-info').text(current_page+' / '+pageTotal);
            }
            $("#current_page").val('');
            updatePageBtnStatus();
        }
    });  
} 


function successTr(nowTr, id, resourceType){
	if(confirm('您确定要审核通过这个创意吗 ？')){
		var submitUrl = resourceType == 9? '/creation/auditH5LandingPage' : '/creation/auditresult';
		$.ajax({
			method: "post",
			url: submitUrl,
			contentType : 'application/json',
			data : JSON.stringify({ 'id':id,'auditStatus':'1','auditReason':''}),
			success: function(msg){
				if ((resourceType == 9 && msg.status === 200) || (resourceType != 9 && msg.status==200 && msg.result && msg.result.auditStatus == 1)) {
					//更新状态列
					$(nowTr).parent().parent().children('td').eq(8).html('正常');
					$(nowTr).parent().html('<button class="button_8" onclick="showAudit(this,'+id+',' + resourceType + ')">驳回</button>');
				} else {
					if (resourceType == 9) {
						alert(msg.message);
					} else {
						alert("更新审核信息失败，请刷新页面");
					}
				}
			},
			error: function() {
				alert('审核失败');
			}
		});
	}
}

function submitAudit(){
	var creationInfo = $("#auditId").val().split(',');
	var id= creationInfo[0], resourceType = creationInfo[1];
	var submitUrl = resourceType == 9 ? '/creation/auditH5LandingPage' : '/creation/auditresult';
	var auditComment=$('#reason').val();
	if(auditComment == ''){
		alert('驳回理由不能为空，请输入内容!');
	}else if(auditComment.length > 100){
		alert('请不要超过100个字符');
	}else{ 
		$.ajax({
			method: "post",
			url: submitUrl,
			contentType : 'application/json',
			data : JSON.stringify({ 'id':id,'auditStatus':'2','auditReason':auditComment}),
			success : function(msg) {
				if ((resourceType == 9 && msg.status === 200) || (resourceType != 9 && msg.status == 200 && msg.result && msg.result.auditStatus == 2)) {
					// 更新状态列
					$(trThis).parent().parent().children('td').eq(8).html('未通过');
					$(trThis).parent().html('');
					closeAudit();
				} else {
					if (resourceType == 9) {
						alert(msg.message);
					} else {
						alert("更新审核信息失败，请刷新页面");
					}
				}
	        },
			error: function() {
				alert('审核失败');
			}
		});
	}
}
//大图查看
function showBigPicList(e) { 
	$("#showPic").attr('src', e.src);
	$("#bigPicList").show();	
}
//关闭大图
function closePicList(){ 
	$("#bigPicList").hide();
}

$(function(){
	getDataList(0); 
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
				getDataList(1);
			}
    	}	
	}); 
	$(".next-page").click(function(){
		if(current_page + 1 <= pageTotal){
			current_page = current_page + 1;
			scrollToTableHead();
			getDataList(1); 
		}
	});
	
	$(".pre-page").click(function(){
		if(current_page - 1 > 0){
			current_page = current_page - 1;
			scrollToTableHead();
			getDataList(1); 
		}	
	});
	
	$(".button_use").click(function(){
		current_page = 1;
		getDataList(1); 
	});
	 $('.date-picker').datepicker({
		language : "zh-CN",
		autoclose : true,
		todayHighlight : true,
		clearBtn : true
	 });
	 $("#sdate").datepicker().on("changeDate", function(e) {
	        $("#edate").datepicker("setStartDate", $("#sdate").datepicker("getDate"));
	    });
	 $("#edate").datepicker().on("changeDate", function(e) {
	        $("#sdate").datepicker("setEndDate", $("#edate").datepicker("getDate"));
	    });
});

function showAudit(nowTr, id, resourceType) {
	$('#reason').val('');
	$("#auditId").val(id + ',' + resourceType);
	trThis = nowTr;
	$("#resourceAudit").show();
}

function closeAudit() { 
	$("#auditId").val('');
	$("#resourceAudit").hide();	
}

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
