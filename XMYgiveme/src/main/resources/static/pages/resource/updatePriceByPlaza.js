var resId = getQueryVariable('resId');
var resAreaOrient = 2; //按照广场
var resName = decodeURI(getQueryVariable('resName'));

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

function validatePrice(oNum) {
    if (!oNum) {
        return false;
    }
    var strP = /^\d+(\.\d{0,2})?$/;
    if (!strP.test(oNum)) {
        return false;
    }
    try {
        if (parseFloat(oNum) != oNum) {
            return false;
        }
    } catch (ex) {
        return false;
    }
    return oNum <= 9999999999999.99;
}

function getDataList() {
    var urlstr = "/resources/monopolyprice?resId=" + resId + "&resAreaOrient="
        + resAreaOrient;
    $.ajax({
        type: "get",
        url: urlstr,
        data: {
            'resId': resId,
            'resAreaOrient': resAreaOrient
        },
        dataType: 'json',
        contentType: "application/x-www-form-urlencoded",
        success: function (msg) {
            var html = '';
            $('#resourcePriceList').html(html);
            var lastRegionId = 0;
            var lastProvinceId = 0;
            var lastCityId = 0;
            var newCityIndex = 0;
            $.each(msg.result.priceList, function (index, currentPriceItem) {
                originalPriceItems.push(currentPriceItem);
                var regionId = currentPriceItem.regionId;
                var regionName = currentPriceItem.regionName;
                var provinceId = currentPriceItem.provinceId;
                var provinceName = currentPriceItem.provinceName;
                var cityId = currentPriceItem.cityId;
                var cityName = currentPriceItem.cityName;
                var plazaId = currentPriceItem.plazaId;
                var plazaName = currentPriceItem.plazaName;
                var priceId = currentPriceItem.priceId;
                var price = currentPriceItem.priceStr;

                html += '<tr class="tr-b">';
                if (lastRegionId != regionId) {
                    lastRegionId = regionId;
                    html += '<td class="td-a">' + regionName + '</td>';
                } else {
                    html += '<td class="td-a"></td>';
                }
                if (lastProvinceId != provinceId) {
                    newProvinceIndex.push(newCityIndex);
                    lastProvinceId = provinceId;
                    html += '<td class="td-a">' + provinceName + '</td>';
                } else {
                    html += '<td class="td-a"></td>';
                }
                if (lastCityId != cityId) {
                    lastCityId = cityId;
                    html += '<td class="td-a">' + cityName + '</td>';
                } else {
                    html += '<td class="td-a"></td>';
                }
                html += '<td class="td-a">' + plazaName + '</td>';
                html += '<td class="td-b td_pos"><input class="input_1 monopolize" id="input_price_' + newCityIndex + '" onfocus="showSpan(this,' + provinceId + ',' + newCityIndex + ')" value="' + price + '" ' + (currentPriceItem.throwable == 'N' ? 'disabled' : '') + '/></td>';

                //TODO 设置是否可投方，需要该功能的时候再打开  BY liujie136
                // var text = currentPriceItem.throwable == 'N' ? '可投' : '不可投';
                // var checked = currentPriceItem.throwable == 'N' ? "" : "checked";
                // var htmlThrowable = '<label class="main_span_4" id="lab_throwable_' + index + '" for="input_throwable_' + index + '">' + text + '</label><input id="input_throwable_' + index + '" type="checkbox" style="display: none" ' + checked + ' />';
                // html += '<td class="td-a">' + htmlThrowable + '</td>';
                html += "</tr>";
                newCityIndex++;
            });
            $('#resourcePriceList').html(html);

            //TODO 设置是否可投方，需要该功能的时候再打开  BY liujie136
            // $("input[id*='input_throwable_']").change(function () {
            //     $(this).checked = !$(this).prop("checked");
            //     var text = $(this).prop("checked") ? '不可投' : '可投';
            //     var index = $(this).attr("id").substr(16, $(this).attr("id").length - 16);
            //     if ($(this).prop("checked")) {
            //         $('#input_price_' + index).removeAttr("disabled");
            //     } else {
            //         $('#input_price_' + index).attr("disabled", "disabled");
            //     }
            //     $("#lab_throwable_" + index).html(text);
            // });
        }
    });
}

$(function () {
    $('#resName').text(resName);
    getDataList();
})

function showSpan(curInput, provinceId, index) {
    if (index != lastShowSpanIndex) {
        var spanStr = '<span id="span_' + index + '" class="main_span_4" onclick="setToWholeProvince(this,' + provinceId + ',' + index + ')">同步修改该省数据</span>';
        var element = document.getElementById("span_" + lastShowSpanIndex);
        if (element != null) {
            element.parentNode.removeChild(element);
        }
        if ($(curInput).parent()[0].childNodes.length == 1) {
            $("<br/>").appendTo($(curInput).parent());
        }
        $(spanStr).appendTo($(curInput).parent());
        lastShowSpanIndex = index;
    }
}

function setToWholeProvince(curInput, provinceId, index) {
    var newPrice = document.getElementById("input_price_" + index).value;
    if (!validatePrice(newPrice)) {
        alert("请输入数字[0~9999999999999.99]，最多保留小数点后两位");
        document.getElementById("input_price_" + index).focus();
    } else if (confirm('确定要将该价格设置为全省价格吗?')) {
        var startIndex = 0;
        var endIndex = 0;
        var found = 0;
        for (var i = 0; i < newProvinceIndex.length; i++) {
            if (newProvinceIndex[i] > index) {
                startIndex = newProvinceIndex[i - 1];
                endIndex = newProvinceIndex[i];
                found = 1;
                break;
            }
        }
        if (found == 0) {
            startIndex = newProvinceIndex[newProvinceIndex.length - 1];
            endIndex = originalPriceItems.length;
        }
        for (var tmpIndex = startIndex; tmpIndex < endIndex; tmpIndex++) {
            if (tmpIndex == index) {
                continue;
            }
            var inputElement = document.getElementById("input_price_"
                + tmpIndex);
            if (inputElement.value != newPrice) {
                inputElement.value = newPrice;
            }
        }
    }
}

function submitPrice() {
    if (confirm('您确定修改这个资源位独占价格吗?')) {
        var updatedItemList = new Array();
        var lastCityId = -1;
        var newPrice = -1;
        for (var tmpIndex = 0; tmpIndex < originalPriceItems.length; tmpIndex++) {
            newPrice = document.getElementById("input_price_" + tmpIndex).value;
            if (!validatePrice(newPrice)) {
                alert("请输入数字[0~9999999999999.99]，最多保留小数点后两位");
                document.getElementById("input_price_" + tmpIndex).focus();
                return
            }

            oldPriceItem = originalPriceItems[tmpIndex];

            //TODO 设置是否可投方，需要该功能的时候再打开  BY liujie136
            // var newThrowable = $("#input_throwable_" + tmpIndex).prop("checked") ? "Y" : "N";
            var newThrowable = oldPriceItem.throwable;
            if (newPrice != oldPriceItem.priceStr || newThrowable != oldPriceItem.throwable) {
                var object = {
                    'regionId': oldPriceItem.regionId,
                    'regionName': oldPriceItem.regionName,
                    'provinceId': oldPriceItem.provinceId,
                    'provinceName': oldPriceItem.provinceName,
                    'cityId': oldPriceItem.cityId,
                    'cityName': oldPriceItem.cityName,
                    'plazaId': oldPriceItem.plazaId,
                    'plazaName': oldPriceItem.plazaName,
                    'priceId': oldPriceItem.priceId,
                    'priceStr': newPrice,
                    'throwable': newThrowable
                };
                updatedItemList.push(object);
            }
        }
        if (updatedItemList.length > 0) {
            $.ajax({
                method: "post",
                url: '/resources/monopolyprice',
                contentType: 'application/json',
                data: JSON.stringify({
                    'resId': resId,
                    'resBidtype': 1,//独占
                    'resAreaOrient': resAreaOrient,
                    'resPriceList': updatedItemList
                }),
                success: function (msg) {
                    if (msg.status == 200) {
                        window.location.href = 'resourceList.html';
                    } else {
                        alert("更新价格信息失败，请重新提交");
                    }
                }
            });
        } else {//没有检测到修改的价格项，直接跳转
            window.location.href = 'resourceList.html';
        }
    }
}
