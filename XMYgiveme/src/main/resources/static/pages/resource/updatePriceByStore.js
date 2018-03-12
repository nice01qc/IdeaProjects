var resId = getQueryVariable('resId');
var resAreaOrient = 3; //按照门店
var items_per_page = 20;
var current_page = 1;
var pageTotal = 0;
var resName = decodeURI(getQueryVariable('resName'));
var xhr; //ajax请求对象

var originalPriceItems = new Array();
var newProvinceIndex = new Array();
var lastShowSpanIndex = -1;
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
    var urlstr = "/resources/" + resId + "/price";
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
                $('.dijiye').text('1 / 1');
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
                var price = curResourceItem.price == undefined ? "" : curResourceItem.priceStr;
                var storeId = curResourceItem.storeId;
                var storeName = curResourceItem.storeName;
                var storeBusinessTypeId = curResourceItem.storeBusinessTypeId;
                var storeBusinessTypeName = curResourceItem.storeBusinessTypeName == undefined ? "" : curResourceItem.storeBusinessTypeName;
                var throwable = curResourceItem.throwable == "Y" ? "" : "不可投";

                html += '<tr class="tr-b">';
                html += '<td class="td-a">' + regionName + '</td>';
                html += '<td class="td-a">' + cityName + '</td>';
                html += '<td class="td-a">' + storeBusinessTypeName + '</td>';
                html += '<td class="td-a">' + storeName + '</td>';
                if (throwable == "不可投") {
                    html += '<td class="td-b cannotPut">' + price + '</td>';
                } else {
                    html += '<td class="td-b">' + price + '</td>';
                }
                html += '<td class="td-a cannotPut">' + throwable + '</td>';
                html += "</tr>";
            });

            $('#resourcePriceList').html(html);

            if (msg.result.total == 0) {
                $('.dijiye').text('1 / 1');
            } else {
                pageTotal = Math.ceil(msg.result.total / items_per_page);
                $('.dijiye').text(current_page + ' / ' + pageTotal);
            }
            $("#current_page").val('');
        }
    });
}
//获取默认价格
function getDefaultPrice() {
    var urlstr = "/resources/" + resId;
    $.ajax({
        type: "get",
        url: urlstr,
        data: {},
        dataType: 'json',
        contentType: "application/x-www-form-urlencoded",
        success: function (msg) {
            if (msg.status == 200) {
                var queryPrice = msg.result.resReservePriceStr;
                $('#inputPrice').val(queryPrice);
                var flagQuery = msg.result.enableReservePrice == "N" ? false : true;
                $('#defaultPrice').get(0).checked = flagQuery;
                if (flagQuery) {
                    $('#inputPrice').removeAttr("disabled");
                } else {
                    $('#inputPrice').attr("disabled", "disabled");
                }
            } else {
                alert(msg.message);
            }

        }
    });
}

$(function () {
    $('#resName').text(resName);
    $('#defaultPrice').click(function () {
        if ($('#defaultPrice').prop('checked') == true) {
            $('#inputPrice').removeAttr("disabled");
        } else {
            $('#inputPrice').attr("disabled", "disabled")
        }
    })
    getDataList(1);
    getDefaultPrice();
    //下载模板
    /*$("#downLoadTemplate").click(function(){
     window.open("/assets/doc/资源按门店定价模板.xls");
     });*/
    //上传文件
    $('#storePriceFile').change(function () {
        ajaxFileUpload();
    });

    //根据页数设置翻页按钮位disable
    if (current_page === 1 && pageTotal === 1) {
        $('.right-sanjiao').attr("disabled", true);
        $('.right-sanjiao img').attr("src", "/resources/img/grey-right.png");
        $('.left-sanjiao').attr("disabled", true);
        $('.left-sanjiao img').attr("src", "/resources/img/grey-left.png");
    } else if (current_page === pageTotal) {
        $('.right-sanjiao').attr("disabled", true);
        $('.right-sanjiao img').attr("src", "/resources/img/grey-right.png");
        $('.left-sanjiao').attr("disabled", false);
        $('.left-sanjiao img').attr("src", "/resources/img/left.png");
    } else if (current_page === 1) {
        $('.left-sanjiao').attr("disabled", true);
        $('.left-sanjiao img').attr("src", "/resources/img/grey-left.png");
        $('.right-sanjiao').attr("disabled", false);
        $('.right-sanjiao img').attr("src", "/resources/img/right-01.png");
    } else {
        $('.right-sanjiao').attr("disabled", false);
        $('.right-sanjiao img').attr("src", "/resources/img/right-01.png");
        $('.left-sanjiao').attr("disabled", false);
        $('.left-sanjiao img').attr("src", "/resources/img/left.png");
    }

    //翻页
    $("#pageNumClick").click(function () {
        if ($("#current_page").val() != ''
            && $("#current_page").val() != current_page
            && $("#current_page").val() <= pageTotal && $("#current_page").val() > 0) {
            current_page = parseInt($("#current_page").val());
            getDataList($("#current_page").val());
        } else {
            $("#current_page").val("");
        }

        //根据页数设置翻页按钮位disable
        if (current_page === 1 && pageTotal === 1) {
            $('.right-sanjiao').attr("disabled", true);
            $('.right-sanjiao img').attr("src", "/resources/img/grey-right.png");
            $('.left-sanjiao').attr("disabled", true);
            $('.left-sanjiao img').attr("src", "/resources/img/grey-left.png");
        } else if (current_page === pageTotal) {
            $('.right-sanjiao').attr("disabled", true);
            $('.right-sanjiao img').attr("src", "/resources/img/grey-right.png");
            $('.left-sanjiao').attr("disabled", false);
            $('.left-sanjiao img').attr("src", "/resources/img/left.png");
        } else if (current_page === 1) {
            $('.left-sanjiao').attr("disabled", true);
            $('.left-sanjiao img').attr("src", "/resources/img/grey-left.png");
            $('.right-sanjiao').attr("disabled", false);
            $('.right-sanjiao img').attr("src", "/resources/img/right-01.png");
        } else {
            $('.right-sanjiao').attr("disabled", false);
            $('.right-sanjiao img').attr("src", "/resources/img/right-01.png");
            $('.left-sanjiao').attr("disabled", false);
            $('.left-sanjiao img').attr("src", "/resources/img/left.png");
        }
    });
    $(".right-sanjiao").click(function () {
        if (current_page + 1 <= pageTotal) {
            current_page = current_page + 1;
            getDataList(current_page);
        }
        //根据页数设置翻页按钮位disable
        if (current_page === 1 && pageTotal === 1) {
            $('.right-sanjiao').attr("disabled", true);
            $('.right-sanjiao img').attr("src", "/resources/img/grey-right.png");
            $('.left-sanjiao').attr("disabled", true);
            $('.left-sanjiao img').attr("src", "/resources/img/grey-left.png");
        } else if (current_page === pageTotal) {
            $('.right-sanjiao').attr("disabled", true);
            $('.right-sanjiao img').attr("src", "/resources/img/grey-right.png");
            $('.left-sanjiao').attr("disabled", false);
            $('.left-sanjiao img').attr("src", "/resources/img/left.png");
        } else if (current_page === 1) {
            $('.left-sanjiao').attr("disabled", true);
            $('.left-sanjiao img').attr("src", "/resources/img/grey-left.png");
            $('.right-sanjiao').attr("disabled", false);
            $('.right-sanjiao img').attr("src", "/resources/img/right-01.png");
        } else {
            $('.right-sanjiao').attr("disabled", false);
            $('.right-sanjiao img').attr("src", "/resources/img/right-01.png");
            $('.left-sanjiao').attr("disabled", false);
            $('.left-sanjiao img').attr("src", "/resources/img/left.png");
        }
    });

    $(".left-sanjiao").click(function () {
        if (current_page - 1 > 0) {
            current_page = current_page - 1;
            getDataList(current_page);
        }
        //根据页数设置翻页按钮位disable
        if (current_page === 1 && pageTotal === 1) {
            $('.right-sanjiao').attr("disabled", true);
            $('.right-sanjiao img').attr("src", "/resources/img/grey-right.png");
            $('.left-sanjiao').attr("disabled", true);
            $('.left-sanjiao img').attr("src", "/resources/img/grey-left.png");
        } else if (current_page === pageTotal) {
            $('.right-sanjiao').attr("disabled", true);
            $('.right-sanjiao img').attr("src", "/resources/img/grey-right.png");
            $('.left-sanjiao').attr("disabled", false);
            $('.left-sanjiao img').attr("src", "/resources/img/left.png");
        } else if (current_page === 1) {
            $('.left-sanjiao').attr("disabled", true);
            $('.left-sanjiao img').attr("src", "/resources/img/grey-left.png");
            $('.right-sanjiao').attr("disabled", false);
            $('.right-sanjiao img').attr("src", "/resources/img/right-01.png");
        } else {
            $('.right-sanjiao').attr("disabled", false);
            $('.right-sanjiao img').attr("src", "/resources/img/right-01.png");
            $('.left-sanjiao').attr("disabled", false);
            $('.left-sanjiao img').attr("src", "/resources/img/left.png");
        }
    });
})

//上传提示框
function showTip() {
    $("#upLoadTip").show();
}
//关闭提示框
function closeTip() {
    $("#upLoadTip").hide();
}

function showLoading() {
    $("#uploadDiv").hide();
    $("#uploadingDiv").show();
}

function hideLoading() {
    $("#uploadDiv").show();
    $("#uploadingDiv").hide();
}

function cancelUpload() {
    if (xhr) {
        xhr.abort();
        hideLoading();
        $("#upLoadTip").hide();
    }
}

//upload file
function ajaxFileUpload() {
    showLoading();
    var form = $("#uploadedfileForm");
    var options = {
        url: '/resources/' + resId + '/tprice?resId=' + resId,
        type: 'post',
        success: function (data) {
            if (data.status == 200) {
                var newNum = data.message;
                window.location.href = "updatePriceByStoreUploadSuccess.html?resId=" + resId + "&successNum=" + newNum;
                $("#storePriceFile").replaceWith('<input id="storePriceFile" name="storePriceFile" type="file" class="file" hidden="hidden" accept="application/vnd.ms-excel"/>');
                $('#storePriceFile').change(function () {
                    ajaxFileUpload();
                });
            } else {
                $("#storePriceFile").replaceWith('<input id="storePriceFile" name="storePriceFile" type="file" class="file" hidden="hidden" accept="application/vnd.ms-excel"/>');
                $('#storePriceFile').change(function () {
                    ajaxFileUpload();
                });
                hideLoading();
                alert(data.message);
            }
        },
        error: function (XmlHttpRequest, textStatus, errorThrown) {
            $("#storePriceFile").replaceWith('<input id="storePriceFile" name="storePriceFile" type="file" class="file" hidden="hidden" accept="application/vnd.ms-excel"/>');
            $('#storePriceFile').change(function () {
                ajaxFileUpload();
            });
            hideLoading();
            if (textStatus != 'abort') {
                alert("部分数据格式不正确，请检查后重新上传");
            }
        }
    };
    form.ajaxSubmit(options);
    xhr = form.data('jqxhr');
}
//上传excel提交
function uploadExcel() {
    return $("#storePriceFile").click();
}
//导出结果
function exportResult() {
    window.open("/resources/" + resId + "/price/excel");
}
function submitPrice() {

    var urlstr = "/resources/" + resId + "/reserveprice";
    var price = $('#inputPrice').val();
    var enablePrice = $('#defaultPrice').prop('checked') == true ? "Y" : "N";
    $.ajax({
        type: "post",
        url: urlstr,
        data: {
            'resId': resId,
            'reservePrice': price,
            'enableReservePrice': enablePrice
        },
        dataType: 'json',
        contentType: "application/x-www-form-urlencoded",
        success: function (msg) {
            window.location.href = "/pages/resource/resourceList.html";
        }
    });
}

