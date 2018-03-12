var resId = getQueryVariable('resId');
var userEmail = getQueryVariable('userEmail');

var displayChart;
var displayData = new Array();

$(function(){
	 $('#userEmail').val(userEmail);
	 $('.date-picker').datepicker({
	        language : "zh-CN",
	        autoclose : true,
	        todayHighlight : true,
	        clearBtn : true
	    })
	 $("#sdate").datepicker().on("changeDate", function(e) {
	        $("#edate").datepicker("setStartDate", $("#sdate").datepicker("getDate"));
	    });
	 $("#edate").datepicker().on("changeDate", function(e) {
	        $("#sdate").datepicker("setEndDate", $("#edate").datepicker("getDate"));
	    });
	 initChart();
	 $("#returnList").unbind().bind("click", function() {   
			javascript:location.href='resourceList.html';  
		});
	 $(".query_btn").click(function(){
			getDataList(); 
	});
});

function initChart(){
	Highcharts.setOptions({
		global : {
			useUTC : false,
		}
	});
	displayChart = new Highcharts.Chart({
		chart : {
			renderTo : 'container',
			type : 'line'
		},
		title : {
			text : '数据统计',
			x : -20,
			style:{
                fontFamily:"Microsoft YaHei"
              }
		// center
		},
		exporting: {
			enabled : false ,
		},
	    xAxis: {
	    	type: 'datetime',  
            labels: {  
                //step: 1,   
                formatter: function () {  
                    return Highcharts.dateFormat('%Y-%m-%d', this.value);  
                }  
            },
	        categories: [],
	    },
//		subtitle : {
//			text : 'Source: ffanad.com',
//			x : -20
//		},
		yAxis : {
			title : {
				text : '次数'
			},
			plotLines : [ {
				value : 0,
				width : 1,
				color : '#808080'
			} ]
		},
		tooltip : {
			xDateFormat: '%Y-%m-%d',
			valueDecimals:2
		},
		legend : {
			layout : 'vertical',
			align : 'right',
			verticalAlign : 'middle',
			borderWidth : 0,
			enabled:false,
		},
		series : [ 
		{
			name : '请求',
			data : []
		}, {
			name : '展示',
			data : []
		}, {
			name : '点击',
			data : []
		}, {
			name : '填充率',
			data : []
		}, {
			name : '点击率',
			data : []
		}, {
			name : '收入',
			data : []
		}, ]
	});
}

function displayFilterChanged(selectedValue){
	for(var index=0;index<displayChart.series.length;index++){
		if(index == selectedValue){
			displayChart.series[index].setVisible(true,false);
		}else{
			displayChart.series[index].setVisible(false,false);
		}
	}
	var newTitleText = {text: '次数',};
	if(selectedValue > 4){
		newTitleText = {text: '元',};
	}else if(selectedValue > 2){
		newTitleText = {text: '百分比％',};
	}
	displayChart.yAxis[0].setTitle(newTitleText,true);
}

function displayFilterChanged_old(displaySelect){
	var data = displayData[displaySelect.value];
	displayChart.series[0].name=displaySelect.options[displaySelect.selectedIndex].text;
	if(typeof(data) != undefined && data!=null){
		displayChart.series[0].setData(data,true);
	}
}


function getQueryVariable(variable) {
	var query = window.location.search.substring(1);
	var vars = query.split("&");
	for (var i = 0; i < vars.length; i++) {
		var pair = vars[i].split("=");
		if (pair[0] == variable) {
			return pair[1];
		}
	}
}

function validateNumber(oNum){
	if (!oNum) {
		return false;
	}
	var strP = /^[0-9]*[1-9][0-9]*$/;
	if (!strP.test(oNum)) {
		return false;
	}
	try {
		if (parseInt(oNum) != oNum) {
			return false;
		}
	} catch (ex) {
		return false;
	}
	return true;
}

function dateTimeIsNOK() {
	var tmpStart = $('#sdate').val();
	var tmpEnd = $('#edate').val();
	return tmpStart == "" || tmpEnd == ""
			|| Date.parse(tmpStart) > Date.parse(tmpEnd);
} 

function checkMyForm(){
	if(resId == null || !validateNumber(resId)){
		alert("资源id必须是数字");
		return false;
	}
	if(dateTimeIsNOK()){
		alert("请选择开始和结束时间，并且开始时间不能大于结束时间。");
		return false;
	}
	return true;
	
}

function getDataList() {
	if (!checkMyForm()) {
		return
	}
	var urlstr = "/resources/statistics";
	var startTime = $('#sdate').val();
	if (startTime == null || startTime == "") {
		startTime = null;
	} else {
		startTime = new Date($('#sdate').val());
		startTime.setHours(0);//将小时设为0
		startTime = startTime.getTime();
	}
	var endTime = $('#edate').val();
	if (endTime == null || endTime == "") {
		endTime = null;
	} else {
		endTime = new Date($('#edate').val());
		endTime.setHours(0);
		endTime = endTime.getTime() + 24 * 60 * 60 * 1000 - 1;//结束时间为23:59:59:999
	}
	$.ajax({
		method : "post",
		url : urlstr,
		contentType : 'application/json',
		data : JSON.stringify({
			'resId' : resId,
			'startTime' : startTime,
			'endTime' : endTime
		}),
		success : function(msg) {
			if (msg.status != 200) {
				alert("查询数据出错")
				return;
			}
			
			var requestArray = new Array();
			var displayArray = new Array();
			var clickArray = new Array();
			var revenueArray = new Array();
			var displayRateArray = new Array();
			var clickRateArray = new Array();
			var requestDateArray = new Array();

			var totalRequest=0;
			var totalDisplay=0;
			var totalClick=0;
			var displayRate=0;
			var totalClickRate=0;
			var totalRevenue=0;
			displayData = [];
			$.each(msg.data, function(rowIndex, curStatisticItem) {
				var tmpRequest = curStatisticItem.requestCount;
				var tmpDisplay = curStatisticItem.displayCount;
				var tmpClick = curStatisticItem.clickCount;
				var tmpRevenue = parseFloat(curStatisticItem.amountStr);
				
				totalRequest+=tmpRequest;
				totalDisplay+=tmpDisplay;
				totalClick+=tmpClick;
				totalRevenue += tmpRevenue;
				var tmpDisplayRate = tmpDisplay*100/tmpRequest;
				if (isNaN(tmpDisplayRate)) {
					tmpDisplayRate = 0;
				}
				//tmpDisplayRate = tmpDisplayRate.toFixed(3)+"%";
				var tmpclickRate = tmpClick*100/tmpDisplay;
				if (isNaN(tmpclickRate)) {
					tmpclickRate = 0;
				}
				//tmpclickRate = tmpclickRate.toFixed(3)+"%"
				//var tmpDate = new Date(curStatisticItem.requestDate)
				//tmpDate = tmpDate.format("yyyy-MM-dd");

				requestArray.push(tmpRequest);
				displayArray.push(tmpDisplay);
				clickArray.push(tmpClick);
				revenueArray.push(tmpRevenue);
				displayRateArray.push(tmpDisplayRate);
				clickRateArray.push(tmpclickRate);
				requestDateArray.push(curStatisticItem.date);
			});
			totalDisplayRate = totalDisplay*100/totalRequest;
			if (isNaN(totalDisplayRate)) {
				totalDisplayRate = 0;
			}
			totalClickRate = totalClick*100/totalDisplay;
			if (isNaN(totalClickRate)) {
				totalClickRate = 0;
			}
			$('#totalRequest').html(totalRequest);
			$('#totalDisplay').html(totalDisplay);
			$('#totalClick').html(totalClick);
			
			$('#totalDisplayRate').html(totalDisplayRate.toFixed(2)+"%");
			$('#totalClickRate').html(totalClickRate.toFixed(2)+"%");
			
			$('#adRevenue').html(totalRevenue.toFixed(2));
			
			displayData.push(requestArray);
			displayData.push(displayArray);
			displayData.push(clickArray);
			displayData.push(displayRateArray);
			displayData.push(clickRateArray);
			displayData.push(revenueArray);
			
			displayChart.xAxis[0].setCategories(requestDateArray,false);
			displayChart.series[0].setData(requestArray,false);
			displayChart.series[1].setData(displayArray,false);
			displayChart.series[2].setData(clickArray,false);
			displayChart.series[3].setData(displayRateArray,false);
			displayChart.series[4].setData(clickRateArray,false);
			displayChart.series[5].setData(revenueArray,false);
			var selectedValue = $('#displayfilter').val();
			displayFilterChanged(selectedValue);
		}
	});
}