function queryParams(params){
		params.adminEmail=$("#adminEmail").val();
		params.accountEmail= $("#accountEmail").val();
		params.startDate=$("#startDate").val();
		params.endDate=$("#endDate").val();
		params.pageNo=params.pageNumber;
		return params;
}

$(function () {

    $('.date-picker').datepicker({
        language : "zh-CN",
        autoclose : true,
        todayHighlight : true,
        clearBtn : true
    })

    $("#searchBtn").click(function () {
    	$('#table').bootstrapTable('refresh', {
			query : queryParams
		});
    });

    $("#startDate").datepicker().on("changeDate", function ( e ) {
        $("#endDate").datepicker("setStartDate", $("#startDate").datepicker("getDate"));
    });

    $("#endDate").datepicker().on("changeDate", function ( e ) {
        $("#startDate").datepicker("setEndDate", $("#endDate").datepicker("getDate"));
    });

    $("#table").on('reset-view.bs.table', function(){
    	var options = $('#table').bootstrapTable('getOptions');
		if(options.pageNumber == 1) {
			$('.pagination li.page-pre a').addClass('unused');
		}
		if(options.pageNumber == options.totalPages) {
			$('.pagination li.page-next a').addClass('unused');
		}
    });
});
function cellStyle ( value, row, index ) {
    return {
        css : {
            "word-break" : "normal"
        }
    };
}

/**
 * 格式化日期
 * 
 * @param value
 * @param row
 * @param index
 * @returns
 */
function dateFmt ( value, row, index ) {
    return new Date(value).format("yyyy-MM-dd hh:mm")
}

/**
 * 格式化分成百分百
 * 
 * @param value
 * @param row
 * @param index
 * @returns {String}
 */
function profitShareFmt ( value, row, index ) {
    return (value * 100).toFixed(2) + "%";
}
