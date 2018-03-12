var accountDate= getPar('accountDate');
var accountId= getPar('accountId');
var accountMail= getPar('accountMail');
var company = getPar('company');


function queryParams(params){
	params.accountDate = accountDate;
	params.accountId = accountId;
	return params;
}

function queryParamsDetail(params){
	params.accountDate = accountDate;
	params.accountMail = accountMail;
	return params;
}

$(function() {
	$('#company').text(company);
});

function Export(){
	
	window.location.href="/finance/export?accountDate="+accountDate+"&accountId="+accountId+"&checkParty=1";
	/*  $.ajax({
        url : "/finance/export?accountDate="+accountDate+"&accountMail="+accountMail+"&checkParty=1",
        method : 'get',
        contentType:"application/x-www-form-urlencoded;charset=UTF-8",
        success : function(result) {
        	console.log(result);
        /*    if (result.setStatus == 200) {
            	alert("导出成功!");
            } else {
                alert("导出失败!")
            }
        }
    });*/
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
