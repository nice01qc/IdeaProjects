var promoteId = null;
var days = null;
var timeInterval = 1; // 时间间隔
var pageSize = 20;
$(function() {
    // 初始化折线图
    init();
    // 初始化页面数据
    initPageData();
    //table重画时触发
    $("#table").on('reset-view.bs.table', function(){
    	var options = $('#table').bootstrapTable('getOptions');
		if(options.pageNumber == 1) {
			$('.pagination li.page-pre a').addClass('unused');
		}
		if(options.pageNumber == options.totalPages) {
			$('.pagination li.page-next a').addClass('unused');
		}
    });
    // 当table翻页是触发
    $('#table').on('page-change.bs.table', function(number, size) {
        $('#table').bootstrapTable('showLoading');
        var data = {
            limit : pageSize,
            offset : (size - 1) * pageSize
        }
        if (promoteId != null)
            data.promoteId = promoteId;
        if (days != null)
            data.days = days;
        $.ajax({
            type : "POST",
            url : "/user/datacenter/detail",
            data : JSON.stringify(data),
            dataType : "json",
            contentType : "application/json",
            success : function(data) {
                $('#table').bootstrapTable('load', {
                    rows : data.result.adDaySummaryDataPage.rows,
                    total : data.result.adDaySummaryDataPage.total
                });
                $('#table').bootstrapTable('hideLoading');
            }
        });
    });
    // 时间段tab点击触发
    $('div[ role="days" ]').click(function() {
    	$("#selectoption").val(0);
        promoteId = null;
        $(this).addClass("MER-ch-active").siblings('div[ role="days" ]').removeClass("MER-ch-active");
        days = $(this).attr("days");
        if (days == "3") {
            timeInterval = 1;
        } else if (days == "7") {
            timeInterval = 2;
        } else if (days == "30") {
            timeInterval = 7;
        } else if (days == "90") {
            timeInterval = 30;
        } else if (days == "1") {
            timeInterval = 30;
        }
        loadChartAndTableData({
            days : days,
            promoteId : promoteId
        });
        $("div[promote='true']").removeClass("par-two").addClass("par-one");
        // $("div[promote='true']").first().click()
    });
    // 点击量select更改后触发
    $("#selectoption").change(function() {
        displayFilterChanged($(this).val())
    });

    // 跳转账户信息页面
    $('img#goAccount').click(function() {
        window.location.href = "accountInfo.html";
    });

    $("#export").click(
            function() {
                window.location.href = "/user/datacenter/export?promoteId=" + (promoteId == null ? "" : promoteId)
                        + "&days=" + (days == null ? 3 : days);
            })
});

function initPageData() {
    $.ajax("/user/datacenter/init").success(function(data) {
        if (data.status == 200) {
            document.getElementById("count").innerHTML = data.result.adAccountData.adConsume.toFixed2();
            document.getElementById("money-re-two").innerHTML = data.result.adAccountData.adBalance.toFixed2();
            var adSummaryData = data.result.adSummaryData;
            document.getElementById("displayfreq").innerHTML = adSummaryData.showNum;
            document.getElementById("clickfreq").innerHTML = adSummaryData.clickNum;
            document.getElementById("clickrate").innerHTML = adSummaryData.clickRate.toFixed2() + "%";
            document.getElementById("cpmAmount").innerHTML = parseFloat(adSummaryData.cpm).toFixed2();
            document.getElementById("cpcAmount").innerHTML = parseFloat(adSummaryData.cpc).toFixed2();
            for (var i = 0; i < data.result.adData.length; i++) {
                var promteid = data.result.adData[i].promoteId;
                var promotename = data.result.adData[i].promoteName;
                var html = "<div id='" + promteid + "' class='par-one' promote='true'>" + promotename + "</div>"
                $("#addactivities").append(html);
            }
            // 初始化加载数据
            chartLoadData(data);
            if (data.result.adDaySummaryDataPage.rows == null || data.result.adDaySummaryDataPage.rows.length == 0) {
                $(".bootstrap-table").hide();
                $("#export_div").hide();
            } else {
                $(".bootstrap-table").show()
                $("#export_div").show();
            }
            $('#table').bootstrapTable('showLoading');

            $('#table').bootstrapTable('load', {
                rows : data.result.adDaySummaryDataPage.rows,
                total : data.result.adDaySummaryDataPage.total
            });
            $('#table').bootstrapTable('hideLoading');
            // 给投放div绑定点击事件
            $("div[promote='true']").on("click", function() {
                $("#selectoption").val(0)
                event.stopPropagation();
                $(this).removeClass("par-one").addClass("par-two");
                promoteId = $(this).attr("id");
                var countType = $("#selectoption").val();
                var datas = {};
                datas.days = (days == null ? 3 : days);
                datas.countType = countType;
                datas.promoteId = promoteId;
                loadChartAndTableData(datas)
                $(this).siblings("div[promote='true']").removeClass("par-two").addClass("par-one");

            });
            // $("div[promote='true']").first().click()
        }
    });
}

function loadChartAndTableData(datas) {
    $.ajax({
        type : 'POST',
        url : '/user/datacenter/chart',
        data : JSON.stringify(datas),
        dataType : 'json',
        contentType : "application/json",
        success : function(dataresponse) {
            // 加载chart数据
            chartLoadData(dataresponse);
            // table同步查询数据
            $('#table').bootstrapTable('showLoading');
            $.ajax({
                type : "POST",
                url : "/user/datacenter/detail",
                data : JSON.stringify({
                    limit : pageSize,
                    offset : 0,
                    days : datas.days,
                    promoteId : datas.promoteId
                }),
                dataType : "json",
                contentType : "application/json",
                success : function(data) {
                    if (data.result.adDaySummaryDataPage.rows == null
                            || data.result.adDaySummaryDataPage.rows.length == 0) {
                        $(".bootstrap-table").hide();
                        $("#export_div").hide();
                    } else {
                        $(".bootstrap-table").show();
                        $("#export_div").show();
                    }
                    $('#table').bootstrapTable('load', {
                        rows : data.result.adDaySummaryDataPage.rows,
                        total : data.result.adDaySummaryDataPage.total
                    });
                    $('#table').bootstrapTable('hideLoading');
                }
            });
        }

    });
}

function chartLoadData(dataresponse) {
    var showNumArr = new Array();// 展示量
    var clickNumArr = new Array();// 点击量
    var clickRateArr = new Array();// 点击率
    var cpmArr = new Array();// 千次展示成本
    var cpcArr = new Array();// 单次点击成本
    var timeArr = new Array();// 时间

    $.each(dataresponse.result.adDaySummaryData, function(i, n) {
        showNumArr.push(n.showNum)
        clickNumArr.push(n.clickNum)
        clickRateArr.push(n.clickRate)
        cpmArr.push(n.cpm)
        cpcArr.push(n.cpc)
        timeArr.push(n.time);
    })
    displayChart.xAxis[0].setCategories(timeArr, false);
    displayChart.xAxis[0].update({
        labels : {
            step : timeInterval,
            rotation : (timeInterval==1 || timeInterval==2)?0:-45
        }
    });
    displayChart.series[0].setData(showNumArr, false);
    displayChart.series[1].setData(clickNumArr, false);
    displayChart.series[2].setData(clickRateArr, false);
    displayChart.series[3].setData(cpmArr, false);
    displayChart.series[4].setData(cpcArr, false);
    displayFilterChanged(0);
}

// 基于准备好的dom，初始化echarts实例
var displayChart;
function init() {
    displayChart = new Highcharts.Chart({
        chart : {
            renderTo : 'containerNo',
            type : 'spline'
        },
        lang : {
            noData : "查无数据"
        },
        noData : {
            style : {
                fontWeight : 'bold',
                fontSize : '15px',
                color : '#303030'
            }
        },
        title : {
            text : '',
            x : -20,

        },
        xAxis : {
            labels : {},
            categories : [],
        },
        subtitle : {
            text : '',
            x : -20
        },
        yAxis : {
            title : {
                text : '次'
            },
            plotLines : [ {
                value : 0,
                width : 1,
                color : '#00b7ff'
            } ]
        },
        tooltip : {
            xDateFormat : '%Y-%m-%d',
            valueDecimals : 2
        },
        legend : {
            layout : 'vertical',
            align : 'right',
            verticalAlign : 'middle',
            borderWidth : 0,
            enabled : false,
        },
        series : [ {
            name : '展示量',
            data : [],
            color : '#00b7ff'
        }, {
            name : '点击量',
            data : [],
            color : '#00b7ff'
        }, {
            name : '点击率',
            data : [],
            color : '#00b7ff'
        }, {
            name : '千次展示成本',
            data : [],
            color : '#00b7ff'
        }, {
            name : '点击成本',
            data : [],
            color : '#00b7ff'
        }, ]
    });
}

function dateFmt(value, row, index) {
    return value;
}

function displayFilterChanged(selectedValue) {
    for (var index = 0; index < displayChart.series.length; index++) {
        if (index == selectedValue) {
            displayChart.series[index].setVisible(true, false);
        } else {
            displayChart.series[index].setVisible(false, false);
        }
    }
    var newTitleText = {
        text : '次',
    };
    if (selectedValue == 2) {
        newTitleText = {
            text : '%',
        };
    }// 选择填充率、点击率
    else if (selectedValue > 2) {
        newTitleText = {
            text : '元/千次',
        };
    }
    displayChart.yAxis[0].setTitle(newTitleText, true);
}