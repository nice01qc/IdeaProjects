var appKey = ""; //联盟用户自建应用对应的appKey
var timeInterval = 1;  //时间间隔
//获取日期字符串
function GetDateStr(AddDayCount) {
    var dd = new Date();
    dd.setDate(dd.getDate() + AddDayCount);//获取AddDayCount天后的日期
    var y = dd.getFullYear();
    var m = (dd.getMonth() + 1) < 10 ? "0" + (dd.getMonth() + 1) : (dd.getMonth() + 1);//获取当前月份的日期，不足10补0
    var d = dd.getDate() < 10 ? "0" + dd.getDate() : dd.getDate(); //获取当前几号，不足10补0
    return y + "-" + m + "-" + d;
}

$(document).ready(function () {
    //日期控件
    $('.date-picker').datepicker({
        language: "zh-CN",
        autoclose: true,
        todayHighlight: true,
        clearBtn: true,
        endDate: new Date(GetDateStr(-1))
    })
    $("#startDate").datepicker().on("changeDate", function (e) {
        $("#endDate").datepicker("setStartDate", $("#startDate").datepicker("getDate"));
        $("#endDate").datepicker("setEndDate", new Date(GetDateStr(-1)));
    });
    $("#endDate").datepicker().on("changeDate", function (e) {
        $("#startDate").datepicker("setEndDate", $("#endDate").datepicker("getDate") == null ? new Date(GetDateStr(-1)) : $("#endDate").datepicker("getDate"));
    });

    //查询按钮事件
    $("#query_btn").click(function () {
        //下拉维度置为请求维度
        $('#displayfilter').val("0");
        getDataList();
        //每分钟调用一次查询后台数据
        //setInterval(getDataList, 60000);
    });

    selectChange();
    initChart();

    //进入页面查询一次,默认为最近一周时间
    $("#startDate").val(GetDateStr(-8));
    $("#endDate").val(GetDateStr(-1));
    getDataList();
});
var displayChart;
//图标
function initChart() {
    displayChart = new Highcharts.Chart({
        chart: {
            renderTo: 'container',
            type: 'spline'
        },
        lang: {
            noData: "查无数据"
        },
        noData: {
            style: {
                fontWeight: 'bold',
                fontSize: '15px',
                color: '#303030'
            }
        },
        title: {
            text: '数据统计',
            x: -20,
            style: {
                fontFamily: "Microsoft YaHei"
            }

        },
        xAxis: {
            type: 'datetime',
            labels: {},
            categories: [],
        },
        yAxis: {
            title: {
                text: '次数'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#00b7ff'
            }]
        },
        tooltip: {
            xDateFormat: '%Y-%m-%d',
            valueDecimals: 2
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0,
            enabled: false,
        },
        series: [

            {
                name: '请求',
                data: [],
                color: '#00b7ff'
            }, {
                name: '展示',
                data: [],
                color: '#00b7ff'
            }, {
                name: '点击',
                data: [],
                color: '#00b7ff'
            }, {
                name: '填充率',
                data: [],
                color: '#00b7ff'
            }, {
                name: '点击率',
                data: [],
                color: '#00b7ff'
            }, {
                name: '收入',
                data: [],
                color: '#00b7ff'
            },]
    });
}

//参数下拉列表选择
function displayFilterChanged(selectedValue) {
    for (var index = 0; index < displayChart.series.length; index++) {
        if (index == selectedValue) {
            displayChart.series[index].setVisible(true, false);
        } else {
            displayChart.series[index].setVisible(false, false);
        }
    }
    var newTitleText = {text: '次数',};
    //选择收入
    if (selectedValue > 4) {
        newTitleText = {text: '元',};
    }//选择填充率、点击率
    else if (selectedValue > 2) {
        newTitleText = {text: '百分比％',};
    }
    displayChart.yAxis[0].setTitle(newTitleText, true);
}

//三级联动
function selectChange() {
    var a = $("#brand_a"),
        b = $("#brand_b"),
        c = $("#brand_c");

    //一级下拉变化
    a.change(function () {

        //先清空二级三级下拉列表
        $("#brand_b").html("");
        $("#brand_c").html("");
        appKey = "";//还原

        //一级选择“全部”
        if ($("#brand_a").val() === "") {

            $('#brand_b').append('<option value="">全部</option>');
            $('#brand_c').append('<option value="">全部</option>');
        }
        //一级选择高级用户
        else if ($("#brand_a").val() === "2") {

            $('#brand_b').append('<option value="">全部</option>');

            //获取二级下拉列表
            $.ajax({
                type: "GET",
                url: "/res/dashboard/getSecondTitle",
                data: {pKey: "common.terminal_type"},
                dataType: "json",
                async: false,
                success: function (displayTypeList) {
                    //动态循环追加下拉列表
                    $.each(displayTypeList, function (i, n) {
                        $('#brand_b').append('<option value="' + displayTypeList[i].columnValue + '">' + displayTypeList[i].columnDesc + '</option>');
                    });
                }
            });

            $('#brand_c').append('<option value="">全部</option>');
        }
        //一级选择联盟用户
        else if ($("#brand_a").val() === "1") {

            $('#brand_b').append('<option value="">全部</option>');
            $('#brand_b').append('<option value="0">APP</option>');
            $('#brand_c').append('<option value="">全部</option>');
        }
    });

    //二级下拉变化
    b.change(function () {

        //先清空二级下拉列表
        $("#brand_c").html("");
        appKey = "";//还原

        //如果一级选择高级用户，二级选择APP
        if ($("#brand_a").val() === "2" && $("#brand_b").val() === "0") {
            $('#brand_c').append('<option value="">全部</option>');
            $('#brand_c').append('<option value="0">飞凡IOS</option>');
            $('#brand_c').append('<option value="1">飞凡Android</option>');
        }
        //如果一级选择联盟用户，二级选择APP，查询自建APP
        else if ($("#brand_a").val() === "1" && $("#brand_b").val() === "0") {

            //添加“全部”下拉
            $('#brand_c').append('<option value="">全部</option>');

            //查询所有联盟用户自建的APP
            $.ajax({
                type: "GET",
                url: "/res/dashboard/getAllUnionApps",
                'async': true,
                'data': {},
                success: function (data) {
                    //动态增加联盟用户自建app的下拉列表
                    $(data).each(function (i) {
                        var app = data[i];
                        var appName = app.name;
                        var app_Key = app.appKey;
                        var opt = $('<option value="' + app_Key + '" >' + appName + '</option>');
                        $('#brand_c').append(opt);
                    });
                }
            });
        } else {
            //添加“全部”下拉
            $('#brand_c').append('<option value="">全部</option>');
        }

    });
    //动态更新appkey
    c.change(function () {
        appKey = $(this).val();
    });
}

//查询后台数据 
function getDataList() {
    var userRole = $('#brand_a').val();  //用户类型
    var terminal = $('#brand_b').val();  //投放终端
    var appType = $('#brand_c').val();   //投放应用
    var beginDate = $('#startDate').val(); //开始日期
    var endDate = $('#endDate').val();   //结束日期
    $.ajax({
        method: "get",
        url: "/res/dashboard/statistics",
        data: {
            'sspUserType': userRole,
            'terminal': terminal,
            'appType': userRole == 2 ? appType : "",
            'appKey': userRole == 1 ? appKey : "",
            'beginDate': beginDate,
            'endDate': endDate
        },
        success: function (statisticData) {
            if (statisticData.status != 200 && statisticData.status != 300) {
                alert("查询数据出错");
                return;
            }

            var dateArray = new Array();   //请求日期
            var requestArray = new Array();   //请求数
            var displayArray = new Array();  //展示数
            var clickArray = new Array();  //点击数
            var fillRateArray = new Array();  //填充率
            var clickRateArray = new Array();  //点击率
            var amountArray = new Array();  //收入

            if (statisticData.status == 300) {
                // alert("查无数据"); 
                //清空原有数据
                displayChart.xAxis[0].setCategories(dateArray, false);
                displayChart.series[0].setData(requestArray, false);
                displayChart.series[1].setData(displayArray, false);
                displayChart.series[2].setData(clickArray, false);
                displayChart.series[3].setData(fillRateArray, false);
                displayChart.series[4].setData(clickRateArray, false);
                displayChart.series[5].setData(amountArray, false);
                //页面合计项回显
                $('#totalRequest').html("");
                $('#totalDisplay').html("");
                $('#totalClick').html("");
                $('#totalFillRate').html("");
                $('#totalClickRate').html("");
                $('#totalAmount').html("");
                //重新绘图
                displayChart.redraw();
                return;
            }

            var totalRequest = 0;  //总请求数
            var totalDisplay = 0;  //总展示数
            var totalClick = 0;  //总点击数
            var totalFillRate = 0;  //总填充率
            var totalClickRate = 0;  //总点击率
            var totalAmount = 0;  //总收入

            var time = 1;

            //如果开始日期和结束日期都有值
            if (beginDate != "" && endDate != "") {
                var s1 = new Date(beginDate.replace(/-/g, "/"));
                var s2 = new Date(endDate.replace(/-/g, "/"));
                var days = s2.getTime() - s1.getTime();             //计算日期相差天数的毫秒数
                time = parseInt(days / (1000 * 60 * 60 * 24)) + 1;  //转化成相差的天数
            }
            else {
                time = statisticData.data.length;
            }

            //相差七天以内正常全部显示
            if (time < 7) {
                timeInterval = 1;
            }//大于7天小于30天
            else if (time < 30) {
                timeInterval = 2;
            }//大于1个月小于90天
            else if (time < 90) {
                timeInterval = 7;
            }//大于90天小于半年
            else if (time < 180) {
                timeInterval = 30;
            }//大于半年
            else {
                timeInterval = 30;
            }
            //循环遍历后台数据
            $.each(statisticData.data, function (rowIndex, curStatisticItem) {
                var tmpDate = curStatisticItem.date - (new Date().getTimezoneOffset() * 60000); //日期,js得到的是时区T16,需要减8个时区,才是东8区
                var tmpRequest = curStatisticItem.requestCount;  //请求数
                var tmpDisplay = curStatisticItem.displayCount;  //展示数
                var tmpClick = curStatisticItem.clickCount;  //点击数
                var tmpFillRate = tmpRequest == 0 ? 0 : tmpDisplay * 100 / tmpRequest;  //填充率
                var tmpClickRate = tmpDisplay == 0 ? 0 : tmpClick * 100 / tmpDisplay;  //点击率
                var tmpAmount = parseFloat(curStatisticItem.amountStr);  //收入 去掉100转换成元。
                totalRequest += tmpRequest;
                totalDisplay += tmpDisplay;
                totalClick += tmpClick;
                totalAmount += tmpAmount;

                dateArray.push(new Date(tmpDate).format("yyyy-MM-dd"));
                requestArray.push(tmpRequest);
                displayArray.push(tmpDisplay);
                clickArray.push(tmpClick);
                fillRateArray.push(tmpFillRate);
                clickRateArray.push(tmpClickRate);
                amountArray.push(tmpAmount);
            });
            totalFillRate = isNaN(totalDisplay * 100 / totalRequest) ? 0 : totalDisplay * 100 / totalRequest;
            totalClickRate = isNaN(totalClick * 100 / totalDisplay) ? 0 : totalClick * 100 / totalDisplay;

            //页面合计项回显
            $('#totalRequest').html(totalRequest);
            $('#totalDisplay').html(totalDisplay);
            $('#totalClick').html(totalClick);
            $('#totalFillRate').html(totalFillRate.toFixed(2) + "%");
            $('#totalClickRate').html(totalClickRate.toFixed(2) + "%");
            $('#totalAmount').html(totalAmount.toFixed(2));

            //Chart数据填充
            displayChart.xAxis[0].setCategories(dateArray, false);
            displayChart.xAxis[0].update({
                labels: {
                    step: timeInterval,
                    rotation: (timeInterval == 1 || timeInterval == 2) ? 0 : -45
                }
            });
            displayChart.series[0].setData(requestArray, false);
            displayChart.series[1].setData(displayArray, false);
            displayChart.series[2].setData(clickArray, false);
            displayChart.series[3].setData(fillRateArray, false);
            displayChart.series[4].setData(clickRateArray, false);
            displayChart.series[5].setData(amountArray, false);

            var selectedValue = $('#displayfilter').val();
            displayFilterChanged(selectedValue);
        }
    });
}
