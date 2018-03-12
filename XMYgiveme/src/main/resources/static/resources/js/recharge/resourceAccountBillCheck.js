$(document).ready(function () {
    var date = new Date;
    var month = date.getMonth() + 1;
    if (month < 10) {
        month = '0' + month;
    }
    $("#years").val(date.getFullYear());
    $("#month").val(month);
    $('#userRole').on('change', function () {
        var userRole = $(this).val();
        if (userRole == '1') {
            $('#accountEmailDiv').show();
        } else {
            $('#accountEmailDiv').hide();
        }
    });
    doSearch();

});

$(function () {
    $("#searchBtn").click(function () {
        doSearch();
    });
});

function doSearch() {
    var years = $("#years").val();
    var month = $("#month").val();
    var accountMail = $("#accountEmail").val();
    var userRole = $('#userRole').val();
    $('#table').bootstrapTable('showLoading');
    $.ajax({
        type: "get",
        url: "/finance/resource?accountDate=" + years + month + "&accountMail=" + accountMail + "&userRole=" + userRole,
        success: function (data) {
            html = "<table data-toggle='table' data-classes='table  table-striped table-no-bordered'  id='table' ></table>";
            $("#table-div").html(html);
            $("#table").attr("data-pagination-pre-text", "<i  class='glyphicon glyphicon-triangle-left'/>");
            $("#table").attr("data-pagination-next-text", "<i  class='glyphicon glyphicon-triangle-right'/>");
            $('#table').bootstrapTable({
                pagination: true,
                sidePagination: 'client',
                pageNumber: 1,
                pageSize: 20,
                showHeader: true,
                checkboxHeader: true,
                columns: [{
                    width: "94px",
                    field: 'accountMail',
                    title: '登录邮箱'
                }, {
                    width: "94px",
                    field: 'company',
                    title: '公司名称'
                }, {
                    width: "94px",
                    field: 'displayCount',
                    title: '总曝光<br/>（次）'
                }, {
                    width: "94px",
                    field: 'clickCount',
                    title: '总点击<br/>（次）'
                }, {
                    width: "94px",
                    field: 'ctr',
                    title: 'CTR'
                },
                //     {
                //     width: "94px",
                //     field: 'cpmDisplayCount',
                //     title: 'CPM展示数<br/>（次）'
                // },
                //     {
                //     width: "94px",
                //     field: 'cpmAmount',
                //     title: 'CPM收入<br/>（元）'
                // }, {
                //     width: "94px",
                //     field: 'cpcClickCount',
                //     title: 'CPC点击数<br/>（次）'
                // }, 
                    {
                    width: "94px",
                    field: 'cpcAmount',
                    title: 'CPC收入<br/>（元）'
                }, {
                    width: "94px",
                    field: 'occupyAmount',
                    title: '独占收入<br/>（元）'
                }, {
                    width: "94px",
                    field: 'amount',
                    title: '总收入<br/>（元）'
                }, {
                    width: "94px",
                    formatter: 'operationFmt',
                    field: 'payStatus',
                    title: '操作'
                }],
                onResetView: function () {
                    var options = $('#table').bootstrapTable('getOptions');
                    if (options.pageNumber == undefined) {
                        $('.pagination li.page-pre a').addClass('unused');
                        return;
                    }
                    if (options.pageNumber == 1) {
                        $('.pagination li.page-pre a').addClass('unused');
                    }
                    if (options.pageNumber == options.totalPages) {
                        $('.pagination li.page-next a').addClass('unused');
                    }
                },
                data: data.result ? data.result : []
            });
            $('#table').bootstrapTable('hideLoading');
        }
    });
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
    var years = $("#years").val();
    var month = $("#month").val();
    var payStatusClass = "button_in_op";
    var disabled = "";
    if (row.payStatus == 1) {
        payStatusClass = "button_in_op_gray"
        disabled = "disabled='disabled' style='background-color: gray;'"
    }
    html = ' <button type="button" class="button_in_op" onclick="Details(\'' + years + month + '\',\'' + row.accountMail + '\',\'' + row.company + '\',\'' + row.accountId + '\',\'' + $('#userRole').val() + '\')">详情</button><br/><button type="button" '
        + '"  onclick="statusToggle(\'' + years + month + '\',\'' + row.accountId + '\',\'' + row.company + '\',' + row.payStatus + ',event)" ' + disabled + ' class="' + payStatusClass + ' ">已打款</button>';

    return html;
}

/**
 * 详情
 *
 * @param accountId
 */
function Details(accountDate, accountMail, company, accountId, userRole) {
    window.location.href = "/pages/fiananceManage/resourceAccountBillCheckDetail.html?accountDate=" + accountDate + "&accountMail=" + accountMail + "&company=" + company + "&accountId=" + accountId + "&userRole=" + userRole;
}

/**
 * 显示状态修改提示
 *
 * @param accountId
 */
function statusToggle(accountDate, accountId, company, payStatus, event) {
    $(event.target).confirmation({
        placement: "left",
        html: "html",
        title: '<h4><i class="ace-icon fa fa-bolt red"></i> 已向“' + company + '”打款吗？</h4>',
        singleton: true,
        btnOkLabel: "确定",
        btnCancelLabel: "取消",
        onConfirm: function () {
            $.ajax({
                url: "/finance/paymentStatus?accountDate=" + accountDate + "&accountId=" + accountId + "&payStatus=2",
                method: 'get',
                success: function (RestResult) {
                    if (RestResult.status == 200) {
                        doSearch()
                    } else {
                        alert(RestResult.message != null ? RestResult.message : "修改失败")
                    }
                }
            });
        }
    }).confirmation('show')
}
