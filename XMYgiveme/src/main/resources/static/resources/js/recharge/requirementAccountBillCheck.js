$(document).ready(function () {
    var date = new Date;
    var month = date.getMonth() + 1;
    if (month < 10) {
        month = '0' + month;
    }
    $("#years").val(date.getFullYear());
    $("#month").val(month);
    doSearch()
});


$(function () {
    $("#searchBtn").click(function () {
        doSearch();
    });
});

function doSearch() {
    var years = $("#years").val();
    var month = $("#month").val();
    var accountMail = $("#accountEmail").val()
    $('#table').bootstrapTable('showLoading');
    $.ajax({
        type: "get",
        url: "/finance/requirement?accountDate=" + years + month + "&accountMail=" + accountMail,
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
                    field: 'effectivePromoteCount',
                    title: '有效投放数'
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
                    {
                    width: "94px",
                    field: 'cpmAmount',
                    title: 'CPM花费<br/>（元）'
                }, {
                    width: "94px",
                    field: 'cpcClickCount',
                    title: 'CPC点击数<br/>（次）'
                }, {
                    width: "94px",
                    field: 'cpcAmount',
                    title: 'CPC花费<br/>（元）'
                }, {
                    width: "94px",
                    field: 'occupyAmount',
                    title: '独占花费<br/>（元）'
                }, {
                    width: "94px",
                    field: 'amount',
                    title: '总花费<br/>（元）'
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
    html = ' <button type="button" class="button_in_op" onclick="Details(\'' + years + month + '\',\'' + row.accountMail + '\',\'' + row.company + '\',\'' + row.accountId + '\')">详情</button>';
    return html;
}

/**
 * 显示状态修改提示
 *
 * @param accountId
 */
function Details(accountDate, accountMail, company, accountId) {
    window.location.href = "/pages/fiananceManage/requirementAccountBillCheckDetails.html?accountDate=" + accountDate + "&accountMail=" + accountMail + "&company=" + company + "&accountId=" + accountId;
}
