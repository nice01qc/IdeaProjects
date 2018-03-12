var accountDate= getPar('accountDate');
var accountMail= getPar('accountMail');
var accountId= getPar('accountId');
var company = getPar('company');
var userRole = getPar('userRole');

function queryParams(params){
	params.accountDate = accountDate;
	params.accountId = accountId;
	params.userRole = userRole;
	return params;
}
$(function() {
	$('#company').text(company);
	$.ajax({
		type : "get",
		url : "/finance/resourceDetail?accountDate="+accountDate+"&accountId="+accountId,
		success : function(data) {
			var item;
			$.each(data.result, function(index, val){
			html = "<h4 id='"+index+"' style='margin-top: 20px;'></h4>";
			addElementDiv('content',index+'a',html);
			$.each(val, function(indexs, value){
			var iD = index+indexs;
			$('#'+index).text(value[0].appName);
			html = "<div class='table-top'>"+value[0].resourcePostion+"</div><table data-toggle='table' data-classes='table  table-striped table-no-bordered'  " +
			"id='"+iD+"'></table>";
			addElementDiv('content',iD+'b',html);
			 $('#'+iD).bootstrapTable({
			       columns: [{
			            field: 'date',
			            title: '日期'
			           }, {
			            field: 'displayCount',
			            title: '总曝光<br/>（次）'
			           },{
			            field: 'clickCount',
			            title: '总点击<br/>（次）'
			           },{
			            field: 'ctr',
			            title: 'CTR'
			           },
                       // {
			           //  field: 'cpmDisplayCount',
			           //  title: 'CPM展示数<br/>（次）'
			           // },{
			           //  field: 'cpmAmount',
			           //  title: 'CPM收入<br/>（元）'
			           // },{
			           //  field: 'cpcClickCount',
			           //  title: 'CPC点击数<br/>（次）'
			           // },
					   {
			            field: 'cpcAmount',
			            title: 'CPC收入<br/>（元）'
			           },{
			            field: 'occupyAmount',
			            title: '独占收入<br/>（元）'
			           },{
				            field: 'amount',
				            title: '总收入<br/>（元）'
				           }],
			       data:value
			      });
				});
			});
    	}
	});
});

function addElementDiv(obj,iD,html) {
	var parent = document.getElementById(obj);
	var div = document.createElement("div");
	div.setAttribute("id", iD);
	div.innerHTML = html;
	parent.appendChild(div);
}


function Export(){
	window.location.href="/finance/export?accountDate="+accountDate+"&accountId="+accountId+"&checkParty=2";

//	$.ajax({
//        url : "/finance/export?accountDate="+accountDate+"&accountMail="+accountMail+"&checkParty=2",
//        method : 'get',
//        contentType:"application/x-www-form-urlencoded;charset=UTF-8",
//        success : function(result) {
//            if (result.status == 200) {
//            	alert("导出成功!");
//            } else {
//                alert("导出失败!")
//            }
//        }
//    });
}

function getPar(par){
    //获取当前URL
    var local_url = document.location.href.toString(); 
    //获取要取得的get参数位置
    var get = local_url.indexOf(par+'=');
    if(get == -1){
        return false;   
    }   
    //截取字符串
    var get_par = local_url.slice(par.length + get + 1);    
    //判断截取后的字符串是否还有其他get参数
    var nextPar = get_par.indexOf("&");
    if(nextPar != -1){
        get_par = get_par.slice(0, nextPar);
    }
    return decodeURI(get_par);
}

function operationFmt(value, row, index) {
	if(row.payStatus == 0){
		$('.money_button').html("<button type='button'  onclick=\"statusToggle(\'"+accountDate+"\',\'"+accountId+"\'," + row.payStatus + ",event)\" class='button_in_op '>已打款</button>");
	}else{
		$('.money_button').html("<button type='button'  class='button_in_hi ' disabled='disabled'>已打款</button>");
	}
    return value ;
}

/**
 * 显示状态修改提示
 * 
 * @param accountId
 */
function statusToggle(accountDate,accountId, payStatus, event) {
    $(event.target).confirmation({
        placement : "left",
        html : "html",
        title : '<h4><i class="ace-icon fa fa-bolt red"></i> 已向“' + company + '”打款吗？</h4>',
        singleton : true,
        btnOkLabel : "确定",
        btnCancelLabel : "取消",
        onConfirm : function() {
            $.ajax({
                url : "/finance/paymentStatus?accountDate="+accountDate+"&accountId="+accountId+"&payStatus=1",
                method : 'get',
                success : function(RestResult) {
                    if (RestResult.status == 200) {
                    	$('.money_button').html("<button type='button'  class='button_in_hi ' disabled='disabled'>已打款</button>");
                    } else {
                        alert(RestResult.message!=null?RestResult.message:"修改失败")
                    }
                }
            });
        }
    }).confirmation('show')
}
