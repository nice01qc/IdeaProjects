//全局变量
var dspCode, beginDate, endDate;

var rateFormater = function(value, row, index) {
	return value + "%";
}

var queryParams = function(params) {
//	params.dspCode = $("#promoteDsp").val();
//	params.beginDate = $("#dateRange").val().split(" - ")[0];
//	params.endDate = $("#dateRange").val().split(" - ")[1];
	params.dspCode = dspCode;
	params.beginDate = beginDate;
	params.endDate = endDate;
	return params;
}

var updateSearchTips = function() {
	var selectedDates = $("#dateRange").val().split(" - ");
	var dateRange = selectedDates[0].replace(/-/g, '.') + ' - ' + selectedDates[1].replace(/-/g, '.');
	var selectedDSP = $("#promoteDsp option:selected").text();
	if (selectedDSP == "全部") {
		selectedDSP = "全部DSP";
	}
	$("#searchTips").text(selectedDSP + "在" + dateRange + "时段的投放情况：");
}

var skipToStatment = function() {
	localStorage.setItem("crmClickedMenuId",39);
}

var showSummary = function() {
	var dspCode = $("#promoteDsp").val();
	var startDateStr = $("#dateRange").val().split(" - ")[0];
	var endDateStr = $("#dateRange").val().split(" - ")[1];
	$.ajax({
        type : "GET",
        url : "/promoteAnalysis/summary",
        data : {
        	dspCode: dspCode,
        	beginDate: startDateStr,
        	endDate: endDateStr
        },
        dataType : "json",
        contentType : "application/json",
        success : function(data) {
            if(data && data.status==200) {
            	$("#displayCount").text(data.result.displayCount);
            	$("#clickCount").text(data.result.clickCount);
            	$("#clickRate").text(data.result.clickRate + "%");
            	$("#cpmAmount").text(data.result.cpmAmount);
            	$("#cpcAmount").text(data.result.cpcAmount);
            	$("#amount").text(data.result.amount);
            } else {
            	alert("查询出错：" + data.message);
            }
        }
    });
}

var showDspStatTable = function() {
	$('#dspStatTable').bootstrapTable({
		url: "/promoteAnalysis/details/dsp",
		queryParams: queryParams,
		responseHandler: function(res) {
			return res.result;
		},
		sortName: 'displayCount',
		sortOrder: 'desc',
		columns: [
		{
			field: 'dspName',
			title: '投放来源',
			align: 'center'
		}, 
		{
			field: 'displayCount',
			title: '展示量(次)',
			align: 'center',
			sortable: true
		},
		{
			field: 'clickCount',
			title: '点击量(次)',
			align: 'center',
			sortable: true
		},
		{
			field: 'clickRate',
			title: '点击率',
			align: 'center',
			formatter: rateFormater,
			sortable: true
		},
		{
			field: 'cpmAmount',
			title: '千次展示成本(元)',
			align: 'center',
			sortable: true
		},
		{
			field: 'cpcAmount',
			title: '平均点击成本(元)',
			align: 'center',
			sortable: true
		},
		{
			field: 'amount',
			title: '消耗金额(元)',
			align: 'center',
			sortable: true
		},
		{
			field: 'dspCode',
			title: '对账单',
			align: 'center',
			formatter : function(value, row, index) {
				var startDate = $("#dateRange").val()
						.split(" - ")[0];
				var endDate = $("#dateRange").val()
						.split(" - ")[1];
				var targetUrl = "/pages/fiananceManage/tpDspBillCheck.html?dspCode="
						+ value
						+ "&startDate="
						+ startDate
						+ "&endDate="
						+ endDate;
				return '<a onclick="skipToStatment()" href="' + targetUrl + '"><span class="glyphicon glyphicon-list"></span></a>';
			}
		}
		]
	});
}

var showDailyTable = function() {
	$('#dailyStatTable').bootstrapTable({
		url: "/promoteAnalysis/details/daily",
		queryParams: queryParams,
		sidePagination: 'client',
		pagination: true,
		paginationLoop: false,
		paginationPreText: '<i class="glyphicon glyphicon-triangle-left"></i>',
		paginationNextText: '<i class="glyphicon glyphicon-triangle-right"></i>',
		responseHandler: function(res) {
			return res.result;
		},
		onResetView: function() {
			var options = $('#dailyStatTable').bootstrapTable('getOptions');
			if(options.pageNumber == 1) {
				$('.dailyStatTable .pagination li.page-pre a').addClass('unused');
			}
			if(options.pageNumber == options.totalPages) {
				$('.dailyStatTable .pagination li.page-next a').addClass('unused');
			}
		},
		sortName: 'date',
		sortOrder: 'desc',
		columns: [
		{
			field: 'date',
			title: '时间(日)',
			align: 'center',
			sortable: true,
			formatter: function(value, row, index) {
				return new Date(value).format('yyyy.MM.dd');
			}
		}, 
		{
			field: 'displayCount',
			title: '展示量(次)',
			align: 'center',
			sortable: true
		},
		{
			field: 'clickCount',
			title: '点击量(次)',
			align: 'center',
			sortable: true
		},
		{
			field: 'clickRate',
			title: '点击率',
			align: 'center',
			sortable: true,
			formatter: rateFormater
		},
		{
			field: 'cpmAmount',
			title: '千次展示成本(元)',
			align: 'center',
			sortable: true
		},
		{
			field: 'cpcAmount',
			title: '平均点击成本(元)',
			align: 'center',
			sortable: true
		},
		{
			field: 'amount',
			title: '消耗金额(元)',
			align: 'center',
			sortable: true
		}
		]
	});
}

var showResStatTable = function() {
	$('#resStatTable').bootstrapTable({
		url: "/promoteAnalysis/details/resource",
		queryParams: queryParams,
		sidePagination: 'client',
		pagination: true,
		paginationLoop: false,
		paginationPreText: '<i class="glyphicon glyphicon-triangle-left"></i>',
		paginationNextText: '<i class="glyphicon glyphicon-triangle-right"></i>',
		responseHandler: function(res) {
			return res.result;
		},
		onResetView: function() {
			var options = $('#resStatTable').bootstrapTable('getOptions');
			if(options.pageNumber == 1) {
				$('.resStatTable .pagination li.page-pre a').addClass('unused');
			}
			if(options.pageNumber == options.totalPages) {
				$('.resStatTable .pagination li.page-next a').addClass('unused');
			}
		},
		sortName: 'displayCount',
		sortOrder: 'desc',
		columns: [
		{
			field: 'resourceName',
			title: '广告位',
			align: 'center'
		}, 
		{
			field: 'displayCount',
			title: '展示量(次)',
			align: 'center',
			sortable: true
		},
		{
			field: 'clickCount',
			title: '点击量(次)',
			align: 'center',
			sortable: true
		},
		{
			field: 'clickRate',
			title: '点击率',
			align: 'center',
			formatter: rateFormater,
			sortable: true
		},
		{
			field: 'cpmAmount',
			title: '千次展示成本(元)',
			align: 'center',
			sortable: true
		},
		{
			field: 'cpcAmount',
			title: '平均点击成本(元)',
			align: 'center',
			sortable: true
		},
		{
			field: 'amount',
			title: '消耗金额(元)',
			align: 'center',
			sortable: true
		}
		]
	});
}

var showTerminalStatTable = function() {
	$('#terminalStatTable').bootstrapTable({
		url: "/promoteAnalysis/details/terminal",
		queryParams: queryParams,
		responseHandler: function(res) {
			return res.result;
		},
		columns: [
		{
			field: 'terminalName',
			title: '终端',
			align: 'center'
		}, 
		{
			field: 'displayCount',
			title: '展示量(次)',
			align: 'center'
		},
		{
			field: 'clickCount',
			title: '点击量(次)',
			align: 'center'
		},
		{
			field: 'clickRate',
			title: '点击率',
			align: 'center',
			formatter: rateFormater
		},
		{
			field: 'cpmAmount',
			title: '千次展示成本(元)',
			align: 'center'
		},
		{
			field: 'cpcAmount',
			title: '平均点击成本(元)',
			align: 'center'
		},
		{
			field: 'amount',
			title: '消耗金额(元)',
			align: 'center'
		}
		]
	});
}

var showCityStatTable = function() {
	$('#cityStatTable').bootstrapTable({
		url: "/promoteAnalysis/details/city",
		queryParams: queryParams,
		sidePagination: 'client',
		pagination: true,
		paginationLoop: false,
		paginationPreText: '<i class="glyphicon glyphicon-triangle-left"></i>',
		paginationNextText: '<i class="glyphicon glyphicon-triangle-right"></i>',
		responseHandler: function(res) {
			return res.result;
		},
		onResetView: function() {
			var options = $('#cityStatTable').bootstrapTable('getOptions');
			if(options.pageNumber == 1) {
				$('.cityStatTable .pagination li.page-pre a').addClass('unused');
			}
			if(options.pageNumber == options.totalPages) {
				$('.cityStatTable .pagination li.page-next a').addClass('unused');
			}
		},
		sortName: 'displayCount',
		sortOrder: 'desc',
		columns: [
		{
			field: 'city',
			title: '城市',
			align: 'center'
		}, 
		{
			field: 'displayCount',
			title: '展示量(次)',
			align: 'center',
			sortable: true
		},
		{
			field: 'clickCount',
			title: '点击量(次)',
			align: 'center',
			sortable: true
		},
		{
			field: 'clickRate',
			title: '点击率',
			align: 'center',
			formatter: rateFormater,
			sortable: true
		},
		{
			field: 'cpmAmount',
			title: '千次展示成本(元)',
			align: 'center',
			sortable: true
		},
		{
			field: 'cpcAmount',
			title: '平均点击成本(元)',
			align: 'center',
			sortable: true
		},
		{
			field: 'amount',
			title: '消耗金额(元)',
			align: 'center',
			sortable: true
		}
		]
	});
}

var initData = function() {
	dspCode = $("#promoteDsp").val();
	beginDate = $("#dateRange").val().split(" - ")[0];
	endDate = $("#dateRange").val().split(" - ")[1];

	updateSearchTips();
	showSummary();
	showDspStatTable();
	showDailyTable();
	showResStatTable();
	showTerminalStatTable();
	showCityStatTable();
}

var exportDspStat = function() {
	var downloadUrl = "/promoteAnalysis/details/dsp/export";
	downloadUrl = downloadUrl + "?dspCode=" + dspCode;
	downloadUrl = downloadUrl + "&beginDate=" + beginDate;
	downloadUrl = downloadUrl + "&endDate=" + endDate;
	window.location.href = downloadUrl;
}

var exportDailyStat = function() {
	var downloadUrl = "/promoteAnalysis/details/daily/export";
	downloadUrl = downloadUrl + "?dspCode=" + dspCode;
	downloadUrl = downloadUrl + "&beginDate=" + beginDate;
	downloadUrl = downloadUrl + "&endDate=" + endDate;
	window.location.href = downloadUrl;
}

var exportResStat = function() {
	var downloadUrl = "/promoteAnalysis/details/resource/export";
	downloadUrl = downloadUrl + "?dspCode=" + dspCode;
	downloadUrl = downloadUrl + "&beginDate=" + beginDate;
	downloadUrl = downloadUrl + "&endDate=" + endDate;
	window.location.href = downloadUrl;
}

var exportTerminalStat = function() {
	var downloadUrl = "/promoteAnalysis/details/terminal/export";
	downloadUrl = downloadUrl + "?dspCode=" + dspCode;
	downloadUrl = downloadUrl + "&beginDate=" + beginDate;
	downloadUrl = downloadUrl + "&endDate=" + endDate;
	window.location.href = downloadUrl;
}

var exportCityStat = function() {
	var downloadUrl = "/promoteAnalysis/details/city/export";
	downloadUrl = downloadUrl + "?dspCode=" + dspCode;
	downloadUrl = downloadUrl + "&beginDate=" + beginDate;
	downloadUrl = downloadUrl + "&endDate=" + endDate;
	window.location.href = downloadUrl;
}

$(document).ready(function() {
	//初始化时间条件为最近30天
	var defaultEndDate = new Date();
	var defaultStartDate = ffanadUtils.plusDays(defaultEndDate, -30);
	$("#dateRange").val(defaultStartDate.format('yyyy-MM-dd') + " - " + defaultEndDate.format('yyyy-MM-dd'));
	
	//初始化加载所有数据
	initData();
	
	//初始化时间控件
	$('#dateRange').daterangepicker();
	
	$("#resetBtn").on('click', function(){
		$("#dateRange").val(defaultStartDate.format('yyyy-MM-dd') + " - " + defaultEndDate.format('yyyy-MM-dd'));
		$('#dateRange').data('daterangepicker').setStartDate(defaultStartDate.format('yyyy-MM-dd'));
		$('#dateRange').data('daterangepicker').setEndDate(defaultEndDate.format('yyyy-MM-dd'));
		$('#promoteDsp').prop('selectedIndex', 0);
	});
	
	$("#submitBtn").on('click', function() {
		//更新查询参数
		dspCode = $("#promoteDsp").val();
		beginDate = $("#dateRange").val().split(" - ")[0];
		endDate = $("#dateRange").val().split(" - ")[1];
		
		updateSearchTips();
		showSummary();
		$("#dspStatTable").bootstrapTable("refresh", {query: queryParams});
		$("#dailyStatTable").bootstrapTable("refresh", {query: queryParams});
		$("#resStatTable").bootstrapTable("refresh", {query: queryParams});
		$("#terminalStatTable").bootstrapTable("refresh", {query: queryParams});
		$("#cityStatTable").bootstrapTable("refresh", {query: queryParams});
	});
});