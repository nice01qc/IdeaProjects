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
    }, {
        name : "广告客户",
        value : 2
    } ]
}, {
    name : "DMP",
    value : 4,
    roles : [ {
        name : "一般用户",
        value : 1
    }]
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

    $("#registerEndTime").datepicker().on("changeDate", function(e) {
        $("#registerStartTime").datepicker("setEndDate", $("#registerEndTime").datepicker("getDate"));
    });

    $('#resetPasswordModal').on('hidden.bs.modal', function(e) {
        $("#newPassword").val("").css("border-color", "").next("span").empty();
        $("#passwordConfirm").val("").css("border-color", "").next("span").empty();
    });

    $('#editProfileModal').on('hidden.bs.modal', function(e) {
        $("#editProfileForm").find("#username").val("").css("border-color", "").next("span").empty();
        $("#editProfileForm").find("#phone").val("").css("border-color", "").next("span").empty();
        $("#editProfileForm").find("#company").val("").css("border-color", "").next("span").empty();
    });

    $('#adjustProfitShareModal').on('hidden.bs.modal', function(e) {
        $("#profitShare").val("").css("border-color", "")
        $("#profitShareSpan").empty();
    });
    
    $("#table").on('reset-view.bs.table', function(){
    	var options = $('#table').bootstrapTable('getOptions');
		if(options.pageNumber == 1) {
			$('.pagination li.page-pre a').addClass('unused');
		}
		if(options.pageNumber == options.totalPages) {
			$('.pagination li.page-next a').addClass('unused');
		}
    });
});

function doSearch() {
	var options = $('#table').bootstrapTable('getOptions');
	if(options.pageNumber != 1) {
		$("#table").bootstrapTable('destroy');
		tableInit();
	}else{
	    $('#table').bootstrapTable('refresh', {
	    	query : {
	        	username : $("#username").val(),
	            phone : $("#phone").val(),
	            accountEmail : $("#accountEmail").val(),
	            freezeState : $("#freezeState").val(),
	            userType : $("#userType").val(),
	            userRoles : $("#userRoles").val(),
	            registerStartTime : $("#registerStartTime").val(),
	            registerEndTime : $("#registerEndTime").val()
	        }
	    });
    }
}

/**
 * table初始化
 * @returns
 */
function tableInit () {
   $('#table').bootstrapTable({
	   toggle:'table',
	   pagination:true,
	   url:"/account/list",
	   sidePagination:"server",
	   method:'post',
	   pageSize:20,
	   escape:true,
	   classes:'table  table-striped table-no-bordered',
	   cache:false,
	   paginationPreText:"<i  class='glyphicon glyphicon-triangle-left'/>",
	   paginationNextText:"<i  class='glyphicon glyphicon-triangle-right'/>",
	   queryParams:function(params){
		   var temp={
				   order:params.order,
				   limit: params.limit, //页面大小
				   offset: params.offset, //页码
				   username : $("#username").val(),
		           phone : $("#phone").val(),
		           accountEmail : $("#accountEmail").val(),
		           freezeState : $("#freezeState").val(),
		           userType : $("#userType").val(),
		           userRoles : $("#userRoles").val(),
		           registerStartTime : $("#registerStartTime").val(),
		           registerEndTime : $("#registerEndTime").val()
	           }
		   return temp;
	   }
   });
}
function cellStyle(value, row, index) {
    return {
      css: {"word-break": "normal"}
    };
  }

/**
 * 格式化日期
 * 
 * @param value
 * @param row
 * @param index
 * @returns
 */
function dateFmt(value, row, index) {
    return new Date(value).format("yyyy-MM-dd hh:mm")
}

/**
 * 格式化冻结状态
 * 
 * @param value
 * @param row
 * @param index
 * @returns {String}
 */
function statusFmt(value, row, index) {
    if (value == 1) {
        return "<p class='text-danger'>冻结</p>"
    } else {
        return "<p class='text-success'>正常</p>"
    }
}

/**
 * 格式化操作列
 * 
 * @param value
 * @param row
 * @param index
 * @returns {String}
 */
function operationFmt(value, row, index) {
    var freeze = "";
    var freezeClass = "button_in_op";
    if (row.freezeState == 1) {
        freeze = "解除冻结";
        freezeClass = "button_in_op_red"
    } else {
        freeze = "冻结"
    }

    var html = '<button type="button" class="' + freezeClass + ' "  onclick="statusToggle(' + value + ', '
            + row.freezeState + ',event)">' + freeze
            + '</button><button type="button" class="button_in_op "  onclick="showModal(' + value
            + ',\'resetPasswordModal\')" >重置密码</button><button type="button" class="button_in_op " onclick="showModal('
            + value + ',\'editProfileModal\')">编辑信息</button>'
    if (row.userType == 2 && row.userRoles == 1) {
        html += ' <button type="button" class="button_in_op " onclick="showModal(' + value
                + ',\'adjustProfitShareModal\')">调整分成</button>';
    }

    return html;
}

/**
 * 格式化用户类别和角色
 * 
 * @param value
 * @param row
 * @param index
 * @returns {String}
 */
function typeFmt(value, row, index) {
    var html = "";
    for (var i = 0; i < sysAndRole.length; i++) {
        if (sysAndRole[i].value == value) {
            html = sysAndRole[i].name;
            $.each(sysAndRole[i].roles, function(j, m) {
                if (row.userRoles == m.value) {
                    html += " - " + m.name;
                }
            });
        }
    }

    return html;
}

/**
 * 格式化分成百分百
 * 
 * @param value
 * @param row
 * @param index
 * @returns {String}
 */
function profitShareFmt(value, row, index) {
    if (row.userType == 2 && row.userRoles == 1) {
        return (value * 100).toFixed(2) + "%";
    }
}

/**
 * 显示状态修改提示
 * 
 * @param accountId
 */
function statusToggle(accountId, freezeState, event) {
    var freeze = "";
    if (freezeState == 1) {
        freeze = "解除冻结"
    } else {
        freeze = "冻结"
    }
    $(event.target).confirmation({
        placement : "left",
        html : "html",
        title : '<h4><i class="ace-icon fa fa-bolt red"></i> 确定' + freeze + '吗？</h4>',
        singleton : true,
        btnOkLabel : "确定",
        btnCancelLabel : "取消",
        onConfirm : function() {
            $.ajax({
                url : "/account/" + accountId,
                method : 'PUT',
                contentType : 'application/json',
                data : JSON.stringify({
                    accountId : accountId,
                    freezeState : freezeState == 1 ? 2 : 1
                }),
                success : function(result) {
                    if (result.status == 200) {
                        doSearch()
                    } else {
                        alert(result.message!=null?result.message:"修改失败")
                    }
                }
            });
        }
    }).confirmation('show')
}

/**
 * 显示模态窗口
 * 
 * @param accountId
 * @param modalId
 */
function showModal(accountId, modalId) {
    $("#" + modalId).find("#accountId").val(accountId);
    $('#' + modalId).modal('toggle')
    if ("editProfileModal" == modalId) {
        $.get("/account/" + accountId, function(data) {
            $("#editProfileForm").find("#username").val(data.result.username);
            $("#editProfileForm").find("#phone").val(data.result.phone);
            $("#editProfileForm").find("#company").val(data.result.company);
        });
    } else if ("adjustProfitShareModal" == modalId) {
        $.get("/account/" + accountId, function(data) {
            $("#adjustProfitShareForm").find("#profitShare").val((data.result.profitShare * 100).toFixed(2));
        });
    }
}

/**
 * 执行密码重置
 * 
 * @param event
 */
function toRestPassword(event) {
    var accountId = $("#restPasswordForm").find("#accountId").val();
    var newPassword = $("#newPassword").val();
    var passwordConfirm = $("#passwordConfirm").val();
    if (newPassword.length > 16 || newPassword.length < 8) {
        $("#newPassword").css("border-color", "red").next("span").text("密码需8~16位字符");
        return;
    } else{
        $("#newPassword").css("border-color", "").next("span").empty();
    } 
    if (newPassword != passwordConfirm) {
        $("#passwordConfirm").css("border-color", "red").next("span").text("请输入相同密码");
        return;
    } else {
        $("#passwordConfirm").css("border-color", "").next("span").empty();
    }
    $(event.target).prop("disabled", true).html("提交中...")
    $.post("/account/resetPassword/" + accountId, {
        newPassword : hex_md5(newPassword)
    }, function(data) {
        if (data.status == 200) {
            $('#resetPasswordModal').modal('toggle');
            $(event.target).prop("disabled", false).html("确定")
        } else {
            alert("重置密码失败")
        }
    });
}

/**
 * 执行账户编辑
 * 
 * @param event
 */
function toEditProfile(event) {
    var accountId = $("#editProfileForm").find("#accountId").val();
    var username = $("#editProfileForm").find("#username").val();
    var phone = $("#editProfileForm").find("#phone").val();
    var company = $("#editProfileForm").find("#company").val();
    var phoneReg = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
    if (username.length > 20 || username.length < 1) {
        $("#editProfileForm").find("#username").css("border-color", "red").next("span").text("姓名需在20个字符以内，不可为空");
        return;
    } else {
        $("#editProfileForm").find("#username").css("border-color", "").next("span").empty();
    }
    if (!phoneReg.test(phone)) {
        $("#editProfileForm").find("#phone").css("border-color", "red").next("span").text("请输入有效手机号码");
        return;
    } else {
        $("#editProfileForm").find("#phone").css("border-color", "").next("span").empty();
    }
    if (company.length > 20) {
        $("#editProfileForm").find("#company").css("border-color", "red").next("span").text("公司名称需在20个字符以内");
        return;
    } else {
        $("#editProfileForm").find("#company").css("border-color", "").next("span").empty();
    }
    $(event.target).prop("disabled", true).html("提交中...")
    $.ajax({
        url : "/account/" + accountId,
        method : 'PUT',
        contentType : 'application/json',
        data : JSON.stringify({
            accountId : accountId,
            username : username,
            phone : phone,
            company : company
        }),
        success : function(result) {
            if (result.status == 200) {
                $('#editProfileModal').modal('toggle');
                $(event.target).prop("disabled", false).html("确定")
                doSearch()
            } else {
                alert("修改失败")
                $('#editProfileModal').modal('toggle');
                $(event.target).prop("disabled", false).html("确定")
            }
        }
    });
}

/**
 * 执行分成调整
 * 
 * @param event
 */
function toAdjustProfitShare(event) {
    var accountId = $("#adjustProfitShareForm").find("#accountId").val();
    var profitShare = $("#adjustProfitShareForm").find("#profitShare").val();
    if (parseFloat(profitShare) < 0 || parseFloat(profitShare) > 100) {
        $("#profitShare").css("border-color", "red")
        $("#profitShareSpan").text("分成需在0~100之间");
        return;
    }
    var reNum =  /^\d+?$/;
    if (!reNum.test(profitShare)) {
        $("#profitShare").css("border-color", "red")
        $("#profitShareSpan").text("分成只可以输入正整数");
        return;
    }
    $("#profitShare").css("border-color", "")
    $("#profitShareSpan").empty();
    $(event.target).prop("disabled", true).html("提交中...")
    profitShare = parseFloat(profitShare) / 100;
    $.ajax({
        url : "/account/adjustProfitShare/" + accountId,
        method : 'PUT',
        data : {
            newProfitshare : profitShare
        },
        success : function(result) {
            if (result.status == 200) {
                $('#adjustProfitShareModal').modal('toggle');
                $(event.target).prop("disabled", false).html("确定")
                doSearch()
            } else {
                alert("修改失败")
                $('#adjustProfitShareModal').modal('toggle');
                $(event.target).prop("disabled", false).html("确定")
            }
        }
    });

}