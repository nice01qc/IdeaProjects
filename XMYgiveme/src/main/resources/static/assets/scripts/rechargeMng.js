var sysAndRole = [{
    name: "CRM",
    value: 1,
    roles: [{
        name: "管理员",
        value: 1
    }, {
        name: "运营人员",
        value: 2
    }, {
        name: "财务人员",
        value: 3
    }]
}, {
    name: "SSP",
    value: 2,
    roles: [{
        name: "联盟用户",
        value: 1
    }, {
        name: "高级用户",
        value: 2
    }]
}, {
    name: "DSP",
    value: 3,
    roles: [{
        name: "一般用户",
        value: 1
    }]
}]
$(function () {

    $('.date-picker').datepicker({
        language: "zh-CN",
        autoclose: true,
        todayHighlight: true,
        clearBtn: true
    })

    $.each(sysAndRole, function (i, n) {
        $("#userType").append(
            "<option value='" + n.value + "'>" + n.name + "</option>");
    });
    $.each(sysAndRole[0].roles, function (j, m) {
        $("#userRoles").append(
            "<option value='" + m.value + "'>" + m.name + "</option>");
    });

    $("#userType").change(
        function () {
            if ($(this).val() == "") {
                $("#userRoles").empty();
                $("#userRoles").append("<option value=''>全部</option>");
                return;
            }
            for (var i = 0; i < sysAndRole.length; i++) {
                if (sysAndRole[i].value == $(this).val()) {
                    $("#userRoles").empty();
                    $("#userRoles").append("<option value=''>全部</option>");
                    $.each(sysAndRole[i].roles, function (j, m) {
                        $("#userRoles").append(
                            "<option value='" + m.value + "'>" + m.name
                            + "</option>");
                    });
                }
            }
        });

    $("#searchBtn").click(function () {
        $('#charge_his_table').bootstrapTable('refresh', {
            query: queryParams
        });
    });

    $("#registerStartTime").datepicker().on(
        "changeDate",
        function (e) {
            $("#registerEndTime").datepicker("setStartDate",
                $("#registerStartTime").datepicker("getDate"));
        });

    //table重画时触发事件，进行前后翻页按钮可用性判断
    $("#charge_his_table").on('reset-view.bs.table', function () {
        var options = $(this).bootstrapTable('getOptions');
        if (options.pageNumber == 1) {
            $('.pagination li.page-pre a').addClass('unused');
        }
        if (options.pageNumber == options.totalPages) {
            $('.pagination li.page-next a').addClass('unused');
        }
    });
});

function queryParams(params) {
    params.accountEmail = $("#accountEmail").val();
    params.rechargeId = $("#id").val();
    params.operator = $("#operator").val();
    params.status = $("#status").val();
    params.rechargeStartDate = $("#registerStartTime").val();
    params.rechargeEndDate = $("#registerEndTime").val();
    params.pageNo = params.pageNumber;
    params.pageSize = params.pageSize;
    console.log(params);
    return params;
}


function operationFmt(value, row, index) {
    var freeze = "";
    var freezeClass = "button_in_op";
    var html = "";
    var btnText = "发票信息"
    if (row.status != 0 && row.status < 4) {
        freeze = "撤销";

        html = '<button type="button" class="' + freezeClass
            + ' "  onclick="statusToggle(' + row.rechargeId + ','
            + row.invoiceAmountStr + ',event)">' + freeze + '</button>';
    }
    if (row.status == 2) {
        btnText = "确认信息"
    } else if (row.status == 3) {
        btnText = "寄出发票"
    }
    if (row.status > 1) {
        html += '<button type="button" class="button_in_op" onclick="window.location.href=\'/pages/fiananceManage/invoiceDetail.html?rid='
            + row.rechargeId + '\'">' + btnText + '</button>';
    }
    return html;
}

function statusToggle(id, invoiceAmount, event) {

    $(event.target)
        .confirmation(
            {
                placement: "left",
                html: "html",
                title: '<h4><i class="ace-icon fa fa-bolt red"></i> 从客户帐户余额中扣除本次充值的￥ '
                + parseFloat(invoiceAmount).toFixed(2) + '元</h4>',
                singleton: true,
                btnOkLabel: "确定",
                btnCancelLabel: "取消",
                onConfirm: function () {
                    $.ajax({
                        url: "/recharge/rechargeUndo",
                        method: 'POST',
                        contentType: 'application/json',
                        data: JSON.stringify({
                            rechargeId: id,
                            invoiceAmount: invoiceAmount
                        }),
                        success: function (result) {
                            if (result.status == 200) {
                                $('#searchBtn').trigger('click');
                            } else if (result.message != '') {
                                alert(result.message)
                            } else {
                                alert("修改失败")
                            }
                        }
                    });
                }
            }).confirmation('show')
}

function statusFmt(value, row, index) {
    // if (value == 1) {
    // 	return "<p class='text-success'>未申请发票</p>"
    // } else if (value == 2) {
    // 	return "<p class='text-success'>已申请发票</p>"
    // } else if (value == 3) {
    // 	return "<p class='text-success'>开票中</p>"
    // } else if (value == 4) {
    // 	return "<p class='text-success'>已寄出发票</p>"
    // }
    if (row.revoker != null && row.revokerTime != null) {
        var date = new Date(row.revokerTime).format("yyyy-MM-dd hh:mm:ss");
        var msg = "撤销人:" + row.revoker + "&#10;撤销时间:" + date;
        return "<p class='text-dange' title='" + msg + "'>" + value + "</p>";
    } else {
        return value;
    }
}

function dateFmt(value, row, index) {
    return new Date(value).format("yyyy-MM-dd hh:mm")
}
