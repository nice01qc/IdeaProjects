$(function () {
    $("#searchBtn").click(function () {
        doSearch()
    });

    // table重画时触发事件，进行前后翻页按钮可用性判断
    $("#table").on('reset-view.bs.table', function () {
        var options = $(this).bootstrapTable('getOptions');
        if (options.pageNumber == 1) {
            $('.pagination li.page-pre a').addClass('unused');
        }
        if (options.pageNumber == options.totalPages) {
            $('.pagination li.page-next a').addClass('unused');
        }
    });
});

function doSearch() {
    $('#table').bootstrapTable('refresh', {
        query: {
            company: $("#company").val(),
            accountEmail: $("#accountEmail").val()
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
    var html = '<button type="button" class="button_in_op "  onclick="showModal(' + value + ',\'' + row.accountEmail
        + '\',\'resetPasswordModal\')" >充值</button>';

    return html;
}

function showModal(accountId, accountEmail, modalId) {
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
    var accountEmail = $("#restPasswordForm #accountEmail").val();
    var balance = $("#restPasswordForm").find("#balance").val();
    var newPassword = $("#newPassword").val();
    var passwordConfirm = $("#passwordConfirm").val();

    var reNum = /^[1-9]\d{0,8}$/;

    if (parseFloat(newPassword) < 0) {
        $("#newPassword").css("border-color", "red");
        $("#newPasswordSpan").text("充值金额需为正整数");
        return;
    } else if (!reNum.test(newPassword)) {
        $("#newPassword").css("border-color", "red");
        $("#newPasswordSpan").text("输入格式不正确，请输入整数");
        return;
    } else {
        $("#newPassword").css("border-color", "#ccc");
        $("#newPasswordSpan").text("");
    }

    if (parseFloat(passwordConfirm) < 0) {
        $("#passwordConfirm").css("border-color", "red");
        $("#passwordConfirmSpan").text("充值金额需为正整数");
        return;
    } else if (!reNum.test(passwordConfirm)) {
        $("#passwordConfirm").css("border-color", "red");
        $("#passwordConfirmSpan").text("输入格式不正确，请输入整数");
        return;
    } else {
        $("#passwordConfirm").css("border-color", "#ccc");
        $("#passwordConfirmSpan").text("");
    }

    $("#newPassword").css("border-color", "").next("span").empty();
    $("#passwordConfirm").css("border-color", "").next("span").empty();
    $(event.target).prop("disabled", true).html("提交中...");

    $.ajax({
        url: "/recharge/rechargeInc",
        method: 'post',
        data: {
            accountId: accountId,
            accountEmail: accountEmail,
            invoiceAmount: newPassword,
            proceedsAmount: passwordConfirm
        },
        success: function (result) {
            $(event.target).prop("disabled", false).html("确定");
            if (result.status == 200) {
                $("#newPassword").val("");
                $("#passwordConfirm").val("");
                $('#resetPasswordModal').modal('toggle');
                doSearch()
            } else {
                if (result.message != '') {
                    alert(result.message)
                } else {
                    alert("充值失败")
                }
            }
        },
        error: function (result) {
            $(event.target).prop("disabled", false).html("确定");
            if (result && result.responseJSON && result.responseJSON.data && result.responseJSON.data.content) {
                alert(result.responseJSON.data.content);
            }
        }
    });
}
