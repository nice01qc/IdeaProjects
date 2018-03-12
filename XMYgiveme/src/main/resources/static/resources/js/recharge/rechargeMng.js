var sysAndRole = [ {
    name : "CRM",
    value : 1,
    roles : [ {
        name : "管理员",
        value : 1
    }, {
        name : "运营人员",
        value : 2
    }, {
        name : "财务人员",
        value : 3
    } ]
}, {
    name : "SSP",
    value : 2,
    roles : [ {
        name : "联盟用户",
        value : 1
    }, {
        name : "高级用户",
        value : 2
    } ]
}, {
    name : "DSP",
    value : 3,
    roles : [ {
        name : "一般用户",
        value : 1
    } ]
} ]
$(function() {

    $('.date-picker').datepicker({
        language : "zh-CN",
        autoclose : true,
        todayHighlight : true,
        clearBtn : true
    })

    $.each(sysAndRole, function(i, n) {
        $("#userType").append("<option value='" + n.value + "'>" + n.name + "</option>");
    });
    $.each(sysAndRole[0].roles, function(j, m) {
        $("#userRoles").append("<option value='" + m.value + "'>" + m.name + "</option>");
    });

    $("#userType").change(function() {
        if ($(this).val() == "") {
            $("#userRoles").empty();
            $("#userRoles").append("<option value=''>全部</option>");
            return;
        }
        for (var i = 0; i < sysAndRole.length; i++) {
            if (sysAndRole[i].value == $(this).val()) {
                $("#userRoles").empty();
                $("#userRoles").append("<option value=''>全部</option>");
                $.each(sysAndRole[i].roles, function(j, m) {
                    $("#userRoles").append("<option value='" + m.value + "'>" + m.name + "</option>");
                });
            }
        }
    });

    $("#searchBtn").click(function() {
        doSearch()
    });

    $("#registerStartTime").datepicker().on("changeDate", function(e) {
        $("#registerEndTime").datepicker("setStartDate", $("#registerStartTime").datepicker("getDate"));
    });

});

function doSearch() {
    $('#table').bootstrapTable('refresh', {
        query : {
            accountEmail : $("#accountEmail").val(),
            id : $("#id").val(),
            operator : $("#operator").val(),
            status : $("#status").val(),
            frontCreateTime : $("#registerStartTime").val(),
            backCreateTime : $("#registerEndTime").val()
        }
    });
}

function operationFmt(value, row, index) {
    var freeze = "";
    var freezeClass = "button_in_op";
    var html="";
    if (row.status == 1) {
        freeze = "撤销";
  
     html = '<button type="button" class="' + freezeClass + ' "  onclick="statusToggle(' + value + ', '+row.id+','+
            + row.invoiceAmount + ',event)">' + freeze
            + '</button>';
    }
    return html;
}

function statusToggle(accountId,id,invoiceAmount, event) {
   
    $(event.target).confirmation({
        placement : "left",
        html : "html",
        title : '<h4><i class="ace-icon fa fa-bolt red"></i> 从客户帐户余额中扣除本次充值的￥ '+invoiceAmount+'元</h4>',
        singleton : true,
        btnOkLabel : "确定",
        btnCancelLabel : "取消",
        onConfirm : function() {
            $.ajax({
                url : "/recharge/rechargeUndo",
                method : 'POST',
                contentType : 'application/json',
                data : JSON.stringify({
                    accountId : accountId,
                    id:id,
                    invoiceAmount:invoiceAmount
                }),
                success : function(result) {
                    if (result.status == 200) {
                        doSearch()
                    } else {
                        alert("修改失败")
                    }
                }
            });
        }
    }).confirmation('show')
}

function statusFmt(value, row, index) {
    if (value == 1) {
    	return "<p class='text-success'>正常</p>"
        
    } else {
    	var date=new Date(row.revokeTime).format("yyyy-MM-dd hh:mm:ss");
    	var msg="撤销人:"+row.revoker+"&#10;撤销时间:"+date;
    	return "<p class='text-dange' title='"+formatTitleString(msg)+"'>已撤销</p>";
    }
}

function dateFmt(value, row, index) {
    return new Date(value).format("yyyy-MM-dd hh:mm")
}
