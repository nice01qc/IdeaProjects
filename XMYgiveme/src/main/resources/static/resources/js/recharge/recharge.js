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

});

function doSearch() {
    $('#table').bootstrapTable('refresh', {
        query : {
        	company : $("#company").val(),
        	accountEmail : $("#accountEmail").val()
        }
    });
}

function statusFmt(value, row, index) {
	if (value == 1) {
        return "<p class='text-danger'>冻结</p>"
    } else {
        return "<p class='text-success'>正常</p>"
    }
}

function operationFmt(value, row, index) {
    var freeze = "";
    var freezeClass = "button_in_op";
    if (row.freezeState == 1) {
        freeze = "解除冻结";
        freezeClass = "button_in_op_red"
    } else {
        freeze = "冻结"
    }

    var html ='<button type="button" class="button_in_op "  onclick="showModal(' + value +',\''+row.accountEmail
        + '\',\'resetPasswordModal\')" >充值</button>';

    return html;
}

function showModal(accountId,accountEmail, modalId) {
    $("#" + modalId).find("#accountId").val(accountId);
    $("#" + modalId).find("#accountEmail").val(accountEmail);
    $("#newPassword").val("");
    $("#passwordConfirm").val("");
    $("#newPasswordSpan").html("");
    $("#passwordConfirmSpan").html("");
    $('#' + modalId).modal('toggle');
}

function toRestPassword(event) {	
    var accountId = $("#restPasswordForm").find("#accountId").val();
    var accountEmail = $("#restPasswordForm").find("#accountEmail").val();
    var newPassword = $("#newPassword").val();
    var passwordConfirm = $("#passwordConfirm").val();
    
    if (parseFloat(newPassword) < 0) {
        $("#newPassword").css("border-color", "red")
        $("#newPasswordSpan").text("充值金额需>0");
        return;
    }
   // var reNum = /^\d+(\.\d{2})?$/;
     var reNum = /^([0-9][0-9]*)+(.[0-9]{1,2})?$/;
    if (!reNum.test(newPassword)) {
        $("#newPassword").css("border-color", "red")
        $("#newPasswordSpan").text("充值金额需为数字");
        return;
    }
    
    if (parseFloat(passwordConfirm)< 0) {
        $("#passwordConfirm").css("border-color", "red")
        $("#passwordConfirmSpan").text("充值金额需>0");
        return;
    }
   // var reNum = /^\d+(\.\d{2})?$/;
    var reNum = /^([0-9][0-9]*)+(.[0-9]{1,2})?$/;
    if (!reNum.test(passwordConfirm)) {
        $("#passwordConfirm").css("border-color", "red")
        $("#passwordConfirmSpan").text("充值金额需为数字");
        return;
    }
    
    $("#newPassword").css("border-color", "").next("span").empty();
    $("#passwordConfirm").css("border-color", "").next("span").empty();
    $(event.target).prop("disabled", true).html("提交中...");
   
    //$("#newPassword").val("");
    //$("#passwordConfirm").val("");
    
    $.ajax({
        url : "/recharge/rechargeInc",
        method : 'post',
        contentType : 'application/json',
		data : JSON.stringify({
			    accountId: accountId,
	  		    accountEmail:accountEmail,
	  			invoiceAmount: newPassword,
	  		    proceedsAmount: passwordConfirm
		}), 
        success : function(result) {
        	if (result.status == 200) {
                $('#resetPasswordModal').modal('toggle');
                $(event.target).prop("disabled", false).html("确定");
                doSearch()
            } else {
                alert("充值失败")
            }
        }
    });
}