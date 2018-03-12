var resId = getQueryVariable('resId');
var resAreaOrient = 3; //按照门店
var items_per_page = 20;
var current_page = 1;
var lastShowSpanIndex = -1;
var successNum = getQueryVariable('successNum');

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

function getDataList(pageNo) {
    var urlstr = "/resources/" + resId + "/tprice";
    $.ajax({
        type: "get",
        url: urlstr,
        data: {
            'pageSize': items_per_page,
            'pageNum': pageNo
        },
        dataType: 'json',
        contentType: "application/x-www-form-urlencoded",
        success: function (msg) {

            if (msg.status != 200) {
                $('.page-info').text('1 / 1');
                $('#resourcePriceList').html('<tr class="tr-a" ><td colspan =14>查询出错</td></tr>');
                return;
            }
            var html = '';
            $('#resourcePriceList').html(html);

            $.each(msg.result.data, function (rowIndex, curResourceItem) {

                var regionId = curResourceItem.regionId;
                var regionName = curResourceItem.regionName == undefined ? "" : curResourceItem.regionName;
                var provinceId = curResourceItem.provinceId;
                var provinceName = curResourceItem.provinceName == undefined ? "" : curResourceItem.provinceName;
                var cityId = curResourceItem.cityId;
                var cityName = curResourceItem.cityName == undefined ? "" : curResourceItem.cityName;
                var plazaId = curResourceItem.plazaId;
                var plazaName = curResourceItem.plazaName == undefined ? "" : curResourceItem.plazaName;
                var price = curResourceItem.priceStr;
                var storeId = curResourceItem.storeId;
                var storeName = curResourceItem.storeName == undefined ? "" : curResourceItem.storeName;
                var storeBusinessTypeId = curResourceItem.storeBusinessTypeId;
                var storeBusinessTypeName = curResourceItem.storeBusinessTypeName;
                var throwable = curResourceItem.throwable == "Y" ? "Y" : "N";
                var option = curResourceItem.option

                html += '<tr class="tr-b">';
                html += '<td class="td-a">' + regionName + '</td>';
                html += '<td class="td-a">' + cityName + '</td>';
                html += '<td class="td-a">' + storeBusinessTypeName + '</td>';
                html += '<td class="td-a">' + storeName + '</td>';
                if (option == "insert" || option == "update") {
                    html += '<td class="td-b cannotPut">' + price + '</td>';
                    html += '<td class="td-a cannotPut">' + throwable + '</td>';
                } else {
                    html += '<td class="td-b">' + price + '</td>';
                    html += '<td class="td-a">' + throwable + '</td>';
                }

                html += "</tr>";
            });

            $('#resourcePriceList').html(html);

            if (msg.result.total == 0) {
                $('.page-info').text('1 / 1');
            } else {
                pageTotal = Math.ceil(msg.result.total / items_per_page);
                $('.page-info').text(current_page + ' / ' + pageTotal);
            }
            $("#current_page").val('');
            updatePageBtnStatus();
        }
    });
}

$(function () {
    getDataList(1);
    $('#newNum').html(successNum.split(",")[0]);
    $('#updateNum').html(successNum.split(",")[1]);
    //翻页
    $("#pageNumClick").click(function () {
    	var goToPageNumStr = $("#current_page").val();
		$("#current_page").val("");
		if( goToPageNumStr != '' && !isNaN(goToPageNumStr)) {
			var goToPageNum = parseInt(goToPageNumStr);
			if(goToPageNum <= 0) {
				goToPageNum = 1;
			} else if(goToPageNum > pageTotal) {
				goToPageNum = pageTotal;
			}
			//判断是否跳转到当前页
			if(goToPageNum == current_page) {
				return false;
			} else {
				current_page = goToPageNum;
				scrollToTableHead();
				getDataList(current_page);
			}
    	}
    });
    $(".next-page").click(function () {
        if (current_page + 1 <= pageTotal) {
            current_page = current_page + 1;
            scrollToTableHead();
            getDataList(current_page);
        }
    });

    $(".pre-page").click(function () {
        if (current_page - 1 > 0) {
            current_page = current_page - 1;
            scrollToTableHead();
            getDataList(current_page);
        }
    });
})

function submitPrice() {
    $('#loadingModal').modal('toggle');
    var urlstr = "/resources/" + resId + "/price";
    $.ajax({
        type: "post",
        url: urlstr,
        data: {},
        dataType: 'json',
        contentType: "application/x-www-form-urlencoded",
        success: function (msg) {
            $('#loadingModal').modal('toggle');
            if (msg.status == 200) {
                window.location.href = "/pages/resource/resourceList.html";
            } else {
                alert(msg.message);
            }
        }
    });
}

/**
 * 更新翻页按钮状态
 * @returns
 */
function updatePageBtnStatus() {
	$(".pagination .next-page").removeClass("unused");
	$(".pagination .pre-page").removeClass("unused");
	if(current_page == pageTotal) {
		$(".pagination .next-page").addClass("unused");
	}
	if(current_page == 1) {
		$(".pagination .pre-page").addClass("unused");
	}
}
