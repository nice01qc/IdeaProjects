var resId = getQueryVariable('resId');
$(document).ready(function(){	
	var resourceId=18;
	//默认起止日期
	$('#beginDate').text(GetDateStr(-3));
    $('#endDate').text(GetDateStr(-1));
    
	var globalUserRole="";
	var sT="";  //二级标题
	var tT="";  //三级标题
	var appKey="";  //自建应用对应的appKey
	var timeScope = "-3";  	//起止日期间隔,默认是最近3天
	var beginDate = $('#beginDate').text();  //开始日期
	var endDate = $('#endDate').text();      //结束日期

	$("#returnList").unbind().bind("click", function() {   
		javascript:location.href='resourceList.html';  
	});
    //初始化图表
    initChart();
  
	//日期下拉列表点击
	$('#timeDropList>li').click(function(){
		
		timeScope = $(this).val();

		//其他下拉列表变为为选择
		$(this).parent().children('li').removeClass().addClass("");
		
		//选中的日期下拉列表变为被选中
        $(this).removeClass().addClass("active");
		
		//开始日期重置
       
        $('#beginDate').text(GetDateStr($(this).val()));
        beginDate = $('#beginDate').text();
        
        //日期下拉列表选中后查询后台数据
        getDataList(resId,beginDate,endDate,timeScope); 
        
	})
	
	//默认初始化查询数据
	 getDataList(resId,beginDate,endDate,timeScope); 
});

//获取日期字符串
function GetDateStr(AddDayCount) 
{ 
    var dd = new Date(); 
    dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期 
    var y = dd.getFullYear(); 
    var m = (dd.getMonth()+1)<10?"0"+(dd.getMonth()+1):(dd.getMonth()+1);//获取当前月份的日期，不足10补0
    var d = dd.getDate()<10?"0"+dd.getDate():dd.getDate(); //获取当前几号，不足10补0
    return y+"-"+m+"-"+d; 
}

//初始化图表
function initChart(){
	displayChart = new Highcharts.Chart({
		chart : {
			renderTo : 'container',
			type : 'spline'
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
		title : {
			text : '',
			x : -20,
	
		},
	    xAxis: {
	    	type: 'datetime',  
	        categories: [],
	    },
		subtitle : {
			text : '',
			x : -20
		},
		yAxis : {
			title : {
				text : '次数'
			},
			plotLines : [ {
				value : 0,
				width : 1,
				color : '#00b7ff'
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
			data : [],
			color:'#00b7ff'
		}, {
			name : '展示',
			data : [],
			color:'#00b7ff'
		}, {
			name : '点击',
			data : [],
			color:'#00b7ff'
		}, {
			name : '填充率',
			data : [],
			color:'#00b7ff'
		}, {
			name : '点击率',
			data : [],
			color:'#00b7ff'
		}, {
			name : '收入',
			data : [],
			color:'#00b7ff'
		}, ]
	});
}

//参数下拉列表选择
function displayFilterChanged(selectedValue){
	for(var index=0;index<displayChart.series.length;index++){
		if(index == selectedValue){
			displayChart.series[index].setVisible(true,false);
		}else{
			displayChart.series[index].setVisible(false,false);
		}
	}
	var newTitleText = {text: '次数',};
	//选择收入
	if(selectedValue > 4){
		newTitleText = {text: '元',};
	}//选择填充率、点击率
	else if(selectedValue > 2){
		newTitleText = {text: '百分比％',};
	}
	displayChart.yAxis[0].setTitle(newTitleText,true);
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
//查询后台资源数据
function getDataList(resId,beginDate,endDate,timeScope) {
	var beginDate=$('#beginDate').text();
	var endDate=$('#endDate').text();
	$.ajax({
		type : 'POST',
		url :"/Resource/datasearch/"+resId,
		data : {			
			'beginDate' : beginDate,
			'endDate' : endDate
		},
		success : function(statisticData) {
			if (statisticData.status != 200 && statisticData.status != 300 ) {
				alert("查询数据出错")
				return;
			}
			if (statisticData.status == 300) {
				alert("查无数据!")
				return;
			}
			var dateArray = new Array();   //请求日期
			var requestArray = new Array();   //请求数
			var displayArray = new Array();  //展示数
			var clickArray = new Array();  //点击数
			var fillRateArray = new Array();  //填充率
			var clickRateArray = new Array();  //点击率
			var amountArray = new Array();  //收入
			
			var totalRequest=0;  //总请求数
			var totalDisplay=0;  //总展示数
			var totalClick=0;  //总点击数
			var totalFillRate=0;  //总填充率
			var totalClickRate=0;  //总点击率
			var totalAmount=0;  //总收入
			
			var timeIntervalCount = 0; //时间间隔计数器
			var timeInterval; //时间间隔
			if(timeScope=="-3"){
				timeInterval = 1;
			}else if(timeScope=="-7"){
				timeInterval = 2;
			} else if(timeScope=="-30"){
				timeInterval = 7;
			} else if(timeScope=="-90"){
				timeInterval = 30;
			} else if(timeScope=="-180"){
				timeInterval = 30;
			} 
			//循环遍历后台数据
			$.each(statisticData.data, function(rowIndex, curStatisticItem) {
				var tmpDate = new Date(curStatisticItem.date).format("yyyy-MM-dd") //日期,js得到的是时区T16,需要减8个时区,才是东8区
				var tmpRequest = curStatisticItem.requestCount;  //请求数
				var tmpDisplay = curStatisticItem.displayCount;  //展示数
				var tmpClick = curStatisticItem.clickCount;  //点击数
				var tmpFillRate = isNaN(tmpDisplay*100/tmpRequest)?0:tmpDisplay*100/tmpRequest;  //填充率
				var tmpClickRate = isNaN(tmpClick*100/tmpDisplay)?0:tmpClick*100/tmpDisplay;  //点击率
				var tmpAmount = curStatisticItem.amount;  //收入
				totalRequest+=tmpRequest;
				totalDisplay+=tmpDisplay;
				totalClick+=tmpClick;
				totalAmount += tmpAmount;
				
				//根据起止日期间隔天数有选择填充图表x轴
				if((timeIntervalCount+timeInterval) % timeInterval == 0){
					dateArray.push(tmpDate);
				}else{
					dateArray.push("");
				}
				timeIntervalCount++;
				
				requestArray.push(tmpRequest);
				displayArray.push(tmpDisplay);
				clickArray.push(tmpClick);
				fillRateArray.push(tmpFillRate);
				clickRateArray.push(tmpClickRate);
				amountArray.push(tmpAmount);
			});
			totalFillRate = isNaN(totalDisplay*100/totalRequest)?0:totalDisplay*100/totalRequest;
			totalClickRate = isNaN(totalClick*100/totalDisplay)?0:totalClick*100/totalDisplay;
			
			//页面合计项回显
			$('#totalRequest').html(totalRequest);
			$('#totalDisplay').html(totalDisplay);
			$('#totalClick').html(totalClick);
			$('#totalFillRate').html(totalFillRate.toFixed(2)+"%");
			$('#totalClickRate').html(totalClickRate.toFixed(2)+"%");
			$('#totalAmount').html(totalAmount.toFixed(2));
			
			//Chart数据填充
			displayChart.xAxis[0].setCategories(dateArray,false);
			displayChart.series[0].setData(requestArray,false);
			displayChart.series[1].setData(displayArray,false);
			displayChart.series[2].setData(clickArray,false);
			displayChart.series[3].setData(fillRateArray,false);
			displayChart.series[4].setData(clickRateArray,false);
			displayChart.series[5].setData(amountArray,false);
			
			//图表下拉值重置成请求维度
			$('#displayfilter').val("0");
			displayFilterChanged("0");
		}
	});
}