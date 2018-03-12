var currentPageNumber = 1;

$(document).ready(function () {
    $('#dateRange').daterangepicker(null, function (start, end, label) {
        console.log(start.toISOString(), end.toISOString(), label);
    });

    var defaultEndDate = new Date();
    var defaultStartDate = ffanadUtils.plusDays(defaultEndDate, -30);
    try {
        if (ffanadUtils.getQueryParameters("endDate")) {
            var endDateStr = ffanadUtils.getQueryParameters("endDate");
            if (new Date(endDateStr) <= new Date())
                defaultEndDate = new Date(endDateStr);
        }
        if (ffanadUtils.getQueryParameters("startDate")) {
            var startDateStr = ffanadUtils.getQueryParameters("startDate");
            if (new Date(startDateStr) <= defaultEndDate)
                defaultStartDate = new Date(startDateStr);
        }
        if (ffanadUtils.getQueryParameters("dspCode")) {
            var dspCode = ffanadUtils.getQueryParameters("dspCode");
            $("#promoteDsp").val(dspCode);
            $("#dsp_name_label").text($("#promoteDsp").find("option:selected").text());
        }
    } catch (ex) {
        console.log("日期格式错误");
    }
    $("#dateRange").val(defaultStartDate.format('yyyy-MM-dd') + " - " + defaultEndDate.format('yyyy-MM-dd'));
    $('#dateRange').data('daterangepicker').setStartDate(defaultStartDate.format('yyyy-MM-dd'));
    $('#dateRange').data('daterangepicker').setEndDate(defaultEndDate.format('yyyy-MM-dd'));
    $("#spDateRange").text(defaultStartDate.format('yyyy.MM.dd') + " - " + defaultEndDate.format('yyyy.MM.dd'));

    function summaryParams(params) {
        params.dsp = $("#promoteDsp").val();
        params.beginDate = $("#dateRange").val().split(" - ")[0];
        params.endDate = $("#dateRange").val().split(" - ")[1];
        return params;
    }

    $('#tSummary').bootstrapTable({
        url: '/finance/dsp/billCheck/summary',
        dataType: "json",
        "queryParamsType": "",
        striped: true,
        queryParams: summaryParams,
        columns: [
            {
                field: 'dateRange',
                title: '投放时段',
                align: "center",
                valign: "middle"
            },
            {
                field: 'requestCount',
                title: '请求数(次)',
                align: "center",
                valign: "middle"
            },
            {
                field: 'effectiveRequestCount',
                title: '有效请求数(次)',
                align: "center",
                valign: "middle"
            },
            {
                field: 'displayCount',
                title: '展示量(次)',
                align: "center",
                valign: "middle"
            },
            {
                field: 'clickCount',
                title: '点击量(次)',
                align: "center",
                valign: "middle"
            },
            {
                field: 'amountStr',
                title: '消耗金额(元)',
                align: "center",
                valign: "middle"
            },
            {
                field: 'balance',
                title: '当前余额(元)',
                align: "center",
                valign: "middle"
            }
        ],
        responseHandler: function (sourceData) {
            if (sourceData.status == 200 && sourceData.result != null) {
                return new Array(sourceData.result);
            } else {
                return new Array();
            }
        }
    });

    function pageParams(params) {
        currentPageNumber = params.pageNumber;
        return {
            pageNum: params.pageNumber,
            pageSize: params.pageSize,
            dsp: $("#promoteDsp").val(),
            beginDate: $("#dateRange").val().split(" - ")[0],
            endDate: $("#dateRange").val().split(" - ")[1]
        };
    };

    $('#tDetail').bootstrapTable({
        url: '/finance/dsp/billCheck/page',
        pagination: true,
        paginationLoop: false,
        paginationPreText: '<i class="glyphicon glyphicon-triangle-left"></i>',
        paginationNextText: '<i class="glyphicon glyphicon-triangle-right"></i>',
        sidePagination: 'server',
        dataType: "json",
        queryParamsType: "",
        striped: true,
        pageNumber: 1,
        pageSize: 10,
        queryParams: pageParams,
        onResetView: function () {
            var options = $('#tDetail').bootstrapTable('getOptions');
            if (options.pageNumber == 1) {
                $('.pagination li.page-pre a').addClass('unused');
            }
            if (options.pageNumber == options.totalPages) {
                $('.pagination li.page-next a').addClass('unused');
            }
        },
        columns: [
            {
                field: 'promoteId',
                title: '投放ID',
                align: "center",
                valign: "middle"
            },
            {
                field: 'allocateTime',
                title: '投放时间',
                align: "center",
                valign: "middle"
            },
            {
                field: 'resName',
                title: '广告位',
                align: "center",
                valign: "middle"
            },
            {
                field: 'osStr',
                title: '投放终端',
                align: "center",
                valign: "middle"
            },
            {
                field: 'cityStr',
                title: '投放城市',
                align: "center",
                valign: "middle"
            },
            {
                field: 'displayCount',
                title: '展示量(次)',
                align: "center",
                valign: "middle"
            },
            {
                field: 'clickCount',
                title: '点击量(次)',
                align: "center",
                valign: "middle"
            },
            {
                field: 'settleTypeStr',
                title: '计费方式',
                align: "center",
                valign: "middle"
            },
            {
                field: 'amountStr',
                title: '计费单价(元)',
                align: "center",
                valign: "middle"
            },
            {
                field: 'amountStr',
                title: '消耗金额(元)',
                align: "center",
                valign: "middle"
            },
            {
                field: 'balance',
                title: '余额(元)',
                align: "center",
                valign: "middle"
            }
        ],
        responseHandler: function (sourceData) {
            if (sourceData.status == 200 && sourceData.result != null) {
                $("#spTotalDesc").text("，共 " + sourceData.result.total + " 条记录");
                return sourceData.result;
            } else {
                $("#spTotalDesc").text("，共 0 条记录");
                return {total: 0, rows: []};
            }
        }
    });

    $("#btnSearch").click(function () {
        $("#dsp_name_label").text($("#promoteDsp :selected").text());
        var beginDate = $("#dateRange").val().split(" - ")[0];
        var endDate = $("#dateRange").val().split(" - ")[1];
        $("#spDateRange").text(beginDate.replace(new RegExp(/(-)/g), '.') + " - " + endDate.replace(new RegExp(/(-)/g), '.'));
        $('#tSummary').bootstrapTable('refresh', summaryParams);
        if (currentPageNumber == 1) {
            $("#tDetail").bootstrapTable('refresh', pageParams);
        } else {
            $('#tDetail').bootstrapTable('selectPage', 1);
        }
    });

    $("#download").click(function () {
        var downloadUrl = "/finance/dsp/billCheck/export";
        downloadUrl = downloadUrl + "?dsp=" + $("#promoteDsp").val();
        downloadUrl = downloadUrl + "&beginDate=" + $("#dateRange").val().split(" - ")[0];
        downloadUrl = downloadUrl + "&endDate=" + $("#dateRange").val().split(" - ")[1];
        window.location.href = downloadUrl;
    })
});
