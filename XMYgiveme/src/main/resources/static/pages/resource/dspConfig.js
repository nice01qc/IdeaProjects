// var items_per_page = 20;
// var current_page = 1;
// var pageTotal = 0;
// var resourceCode;
// var currentRows;
var currentAdxDsp;
var currentPageNumber = 1;

function parseLocalResourceCode() {
    var resourceCodeStr = '{"resApptype":[{"columnCode":"app_type","columnDesc":"飞凡iOS","columnValue":"1","id":6,"position":1,"remark":"投放应用","status":0,"tableCode":"common"},{"columnCode":"app_type","columnDesc":"飞凡安卓","columnValue":"2","id":7,"position":2,"remark":"投放应用","status":0,"tableCode":"common"},{"columnCode":"app_type","columnDesc":"iOS","columnValue":"6","id":8,"position":3,"remark":"投放应用","status":0,"tableCode":"common"},{"columnCode":"app_type","columnDesc":"安卓","columnValue":"7","id":9,"position":4,"remark":"投放应用","status":0,"tableCode":"common"},{"columnCode":"app_type","columnDesc":"智能储物柜","columnValue":"5","id":118,"position":5,"remark":"投放应用","status":0,"tableCode":"common"}],"resBidtype":[{"columnCode":"res_bidtype","columnDesc":"RTB","columnValue":"0","id":97,"position":1,"remark":"议价类型","status":0,"tableCode":"resource_info"},{"columnCode":"res_bidtype","columnDesc":"独占","columnValue":"1","id":98,"position":2,"remark":"议价类型","status":0,"tableCode":"resource_info"}],"resPositiontype":[{"columnCode":"res_positiontype","columnDesc":"手机横幅","columnValue":"0","id":10,"position":1,"remark":"资源位类型","status":0,"tableCode":"resource_info"},{"columnCode":"res_positiontype","columnDesc":"手机全屏","columnValue":"1","id":11,"position":2,"remark":"资源位类型","status":0,"tableCode":"resource_info"},{"columnCode":"res_positiontype","columnDesc":"手机插屏","columnValue":"2","id":12,"position":3,"remark":"资源位类型","status":0,"tableCode":"resource_info"},{"columnCode":"res_positiontype","columnDesc":"手机开机画面","columnValue":"3","id":13,"position":4,"remark":"资源位类型","status":0,"tableCode":"resource_info"},{"columnCode":"res_positiontype","columnDesc":"定制轮播","columnValue":"4","id":14,"position":5,"remark":"资源位类型","status":0,"tableCode":"resource_info"},{"columnCode":"res_positiontype","columnDesc":"云POS闲时","columnValue":"5","id":15,"position":6,"remark":"资源位类型","status":0,"tableCode":"resource_info"},{"columnCode":"res_positiontype","columnDesc":"云POS忙时","columnValue":"6","id":16,"position":7,"remark":"资源位类型","status":0,"tableCode":"resource_info"},{"columnCode":"res_positiontype","columnDesc":"Beacon插屏","columnValue":"7","id":120,"position":8,"remark":"资源位类型","status":1,"tableCode":"resource_info"},{"columnCode":"res_positiontype","columnDesc":"智能储物柜","columnValue":"8","id":119,"position":9,"remark":"资源位类型","status":0,"tableCode":"resource_info"}],"resTerminaltype":[{"columnCode":"terminal_type","columnDesc":"APP","columnValue":"0","id":0,"position":1,"remark":"投放终端","status":0,"tableCode":"common"},{"columnCode":"terminal_type","columnDesc":"飞凡M站","columnValue":"1","id":3,"position":2,"remark":"投放终端","status":0,"tableCode":"common"},{"columnCode":"terminal_type","columnDesc":"Beacon","columnValue":"2","id":4,"position":3,"remark":"投放终端","status":0,"tableCode":"common"},{"columnCode":"terminal_type","columnDesc":"云POS","columnValue":"3","id":5,"position":4,"remark":"投放终端","status":0,"tableCode":"common"},{"columnCode":"terminal_type","columnDesc":"智能储物柜","columnValue":"4","id":117,"position":5,"remark":"投放终端","status":0,"tableCode":"common"}],"resUsertype":[{"columnCode":"resource_type","columnDesc":"定制","columnValue":"0","id":1,"position":1,"remark":"投放资源","status":0,"tableCode":"common"},{"columnCode":"resource_type","columnDesc":"联盟","columnValue":"1","id":2,"position":2,"remark":"投放资源","status":0,"tableCode":"common"}]}';
    resourceCode = $.parseJSON(resourceCodeStr);
}

function resUsertypeChanged(selectedUserType) {
    if (selectedUserType != "") {
        var tmpHtml = '<option value="">全部</option>';
        if (selectedUserType == 0) {//定制用户
            tmpHtml += '<option value="0">APP</option>';
            tmpHtml += '<option value="1">飞凡M站</option>';
            tmpHtml += '<option value="2">Beacon</option>';
            tmpHtml += '<option value="3">云POS</option>';
            tmpHtml += '<option value="4">智能储物柜</option>';
        } else {//联盟用户
            tmpHtml += '<option value="0">APP</option>';
            tmpHtml += '<option value="2">Beacon</option>';
        }
        $('#resTerminaltype').html(tmpHtml);
    } else {
        $('#resTerminaltype').html('<option value="">全部</option>');
    }
    $('#resApptype').html('<option value="">全部</option>');
}

function resTerminaltypeChanged(selectedTerminalType) {
    if (selectedTerminalType != "") {
        var selectedUserType = $('#resUsertype').val();
        var tmpHtml = '<option value="">全部</option>';
        if (selectedTerminalType == 0 || selectedTerminalType == 2) {//APP或者Beacon
            if (selectedUserType == 0) {//定制用户
                tmpHtml += '<option value="1">飞凡iOS</option>';
                tmpHtml += '<option value="2">飞凡安卓</option>';
            } else {//联盟用户
                tmpHtml += '<option value="6">iOS</option>';
                tmpHtml += '<option value="7">安卓</option>';
            }
        }
        $('#resApptype').html(tmpHtml);
    } else {
        $('#resApptype').html('<option value="">全部</option>');
    }
}

function validateFrameIndex(frameIndex) {
    if (!frameIndex) {
        return false;
    }
    var strP = /^[0-9]*[1-9][0-9]*$/;
    if (!strP.test(frameIndex)) {
        return false;
    }
    return frameIndex <= 99;
}

function validateReservePrice(reservePrice) {
    if (!reservePrice) {
        return false;
    }
    var strP = /^\d+(\.\d{0,2})?$/;
    if (!strP.test(reservePrice)) {
        return false;
    }
    try {
        if (parseFloat(reservePrice) != reservePrice) {
            return false;
        }
    } catch (ex) {
        return false;
    }
    return reservePrice <= 99.99 && reservePrice >= 0.01;
}

function validateNumber(oNum) {
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

function checkMyForm() {
    var tmpResId = $('#resId').val()
    if (tmpResId != "" && !validateNumber(tmpResId)) {
        alert("请输入数字");
        document.getElementById("resId").focus();
        return false;
    }
    return true;
}

function queryParams(params) {
    currentPageNumber = (params.offset / params.limit + 1);
    return JSON.stringify({
        'resId': $('#resId').val(),
        'resName': $('#resName').val(),
        'resUserEmail': $('#resUserEmail').val(),
        'dspEnable': $('#dspEnable').val(),
        'resPositiontype': $('#resPositiontype').val(),
        'resUsertype': $('#resUsertype').val(),
        'resTerminaltype': $('#resTerminaltype').val(),
        'resApptype': $('#resApptype').val(),
        'dspId': currentAdxDsp.id,
        'pageSize': params.limit,
        'pageNumber': currentPageNumber
    });
}

function getDataList() {
    if (!checkMyForm()) {
        return
    }
    $('#tbResource').bootstrapTable({
        url: '/dspConfig/page',
        method: "POST",
        pagination: true,
        paginationLoop: false,
        paginationPreText: '<i class="glyphicon glyphicon-triangle-left"></i>',
        paginationNextText: '<i class="glyphicon glyphicon-triangle-right"></i>',
        sidePagination: 'server',
        showRefresh: false,
        dataType: "json",
        "queryParamsType": "limit",
        pageNumber: 1,
        pageSize: 20,
        queryParams: queryParams,
        responseHandler: function (res) {
            return res.result;
        },
        onResetView: function () {
            var options = $('#tbResource').bootstrapTable('getOptions');
            if (options.pageNumber == 1) {
                $('.pagination li.page-pre a').addClass('unused');
            }
            if (options.pageNumber == options.totalPages) {
                $('.pagination li.page-next a').addClass('unused');
            }
        },
        columns: [
            {
                title: '序号',
                width: 20,
                formatter: function (value, row, rowIndex) {
                    var options = $('#tbResource').bootstrapTable('getOptions');
                    (options.pageNumber - 1) * options.pageSize
                    return rowIndex + 1 + ((options.pageNumber - 1) * options.pageSize);
                }
            },
            {
                field: 'resId',
                title: '资源ID',
                width: 70,
            },
            {
                field: 'resName',
                title: '资源名称',
                width: 120
            },
            {
                title: '第三方DSP',
                width: 90,
                formatter: function (value, row, rowIndex) {
                    return currentAdxDsp.name;
                }
            },
            {
                field: 'dspEnable',
                title: '接入状态',
                width: 20,
                formatter: function (value, row, rowIndex) {
                    var valueStr = "关";
                    if (value && "Y" == value) {
                        valueStr = "开";
                    }
                    return "<span class='c_dspEnable'>" + valueStr + "</span>";
                }
            },
            {
                field: 'externalResIosId',
                title: '广告位ID-iOS',
                width: 150,
                formatter: function (value, row, rowIndex) {
                    if (!value) {
                        value = "--";
                    }
                    return "<span class='c_externalResIosId'>" + value + "</span>";
                }
            }
            ,
            {
                field: 'externalResAndroidId',
                title: '广告位ID-安卓',
                width: 150,
                formatter: function (value, row, rowIndex) {
                    if (!value) {
                        value = "--";
                    }
                    return "<span class='c_externalResAndroidId'>" + value + "</span>";
                }
            }
            ,
            {
                field: 'frameIndex',
                title: '插入位置',
                width: 100,
                formatter: function (value, row, rowIndex) {
                    var frameIndex = "--";
                    if (value && value > 0) {
                        frameIndex = "第" + value + "帧";
                    }
                    return "<span class='c_frameIndex'>" + frameIndex + "</span>";
                }
            }
            ,
            {
                field: 'reservePriceStr',
                title: '保留价(元)',
                width: 80,
                formatter: function (value, row, rowIndex) {
                    if (!value || value == '0.00') {
                        value = "--";
                    }
                    return "<span class='c_reservePrice'>" + value + "</span>";
                }
            }
            ,
            {
                title: '操作',
                formatter: function (value, row, rowIndex) {
                    return '<button class="button_8" onclick="btnOptionClick(this,' + rowIndex + ')">修改</button>';
                }
            }
        ]
    });
}

/**
 * 修改按钮的点击事件
 * @param button
 * @param rowIndex
 */
function btnOptionClick(button, rowIndex) {
    if ($(button).text() == "修改") {
        showEditView(button, rowIndex);
    } else {
        updateDspConfig(button, rowIndex);
    }
}

/**
 * 编辑视图
 * @param button
 * @param rowIndex
 */
function showEditView(button, rowIndex) {
    var currentRows = $("#tbResource").bootstrapTable("getData");
    var thisTr = $(button).parent().parent();
    var td_dspEnable = thisTr.find('.c_dspEnable');
    var td_externalResIosId = thisTr.find('.c_externalResIosId');
    var td_externalResAndroidId = thisTr.find('.c_externalResAndroidId');
    var td_frameIndex = thisTr.find('.c_frameIndex');
    var td_reservePrice = thisTr.find('.c_reservePrice');
    var dspEnableEditHtml = '<div id="s' + rowIndex + '" class="switch_btn" style="margin:0 auto" data-restype="0"><span class="on"></span><span class="off"></span><input name="key0" type="hidden" value="Y"></div>';
    var isDisabled = currentRows[rowIndex].dspEnable == "Y" ? "" : "disabled";
    var externalResIosIdEditHtml = '<div class="input_box" style="width: 100%; margin-left: 0px;"><input style="width: 120px;" maxlength="99" type="text" ' + isDisabled + ' id="eri' + rowIndex + '" /></div>';
    var externalResAndroidIdEditHtml = '<div class="input_box" style="width: 100%; margin-left: 0px;"><input style="width: 120px;" maxlength="99" type="text" ' + isDisabled + ' id="era' + rowIndex + '" /></div>';
    var frameIndexEditHtml = '<div class="input_box" style="margin: 0 auto;width: 100%;">第<input  style="width: 40px; margin-right: 5px;" type="number" min="1" max="99"  ' + isDisabled + ' id="fi' + rowIndex + '" />帧</div>';
    var reserveEditHtml = '<div class="input_box" style="width: 100%;margin-left: 0px;"><input style="width: 60px;" min="0.01" max="99.99" type="number"  ' + isDisabled + ' id="rp' + rowIndex + '" /></div>';
    td_dspEnable.html(dspEnableEditHtml);
    td_externalResIosId.html(externalResIosIdEditHtml);
    td_externalResAndroidId.html(externalResAndroidIdEditHtml);
    td_frameIndex.html(frameIndexEditHtml);
    td_reservePrice.html(reserveEditHtml);

    //初始化开关
    if (currentRows[rowIndex].dspEnable != undefined && currentRows[rowIndex].dspEnable == "Y") {
        $("#s" + rowIndex).find("input").val("Y");
        $("#s" + rowIndex).find("span").first().removeClass("on_active");
        $("#s" + rowIndex).find("span").last().removeClass("off_active");
    } else {
        $("#s" + rowIndex).find("input").val("N");
        $("#s" + rowIndex).find("span").first().addClass("on_active");
        $("#s" + rowIndex).find("span").last().addClass("off_active");
    }
    //初始化广告位ID
    if (currentRows[rowIndex].externalResIosId) {
        $("#eri" + rowIndex).val(currentRows[rowIndex].externalResIosId);
    }
    if (currentRows[rowIndex].externalResAndroidId) {
        $("#era" + rowIndex).val(currentRows[rowIndex].externalResAndroidId);
    }
    //初始化插入位置
    if (currentRows[rowIndex].frameIndex) {
        $("#fi" + rowIndex).val(currentRows[rowIndex].frameIndex);
    }
    //初始化保留价
    if (currentRows[rowIndex].reservePriceStr && currentRows[rowIndex].reservePriceStr != "0" && currentRows[rowIndex].reservePriceStr != "0.00") {
        $("#rp" + rowIndex).val(currentRows[rowIndex].reservePriceStr);
    }

    $("#s" + rowIndex).click(function () {
        var _this = $(this);
        _this.find("span").first().toggleClass("on_active");
        _this.find("span").last().toggleClass("off_active");
        if (_this.find("span").first().hasClass("on_active")) {//off
            _this.find("input").val("N");
            $("#eri" + rowIndex).attr("disabled", "disabled");
            $("#era" + rowIndex).attr("disabled", "disabled");
            $("#fi" + rowIndex).attr("disabled", "disabled");
            $("#rp" + rowIndex).attr("disabled", "disabled");
            //初始化广告位ID
            $("#eri" + rowIndex).val("");
            $("#era" + rowIndex).val("");
            //初始化插入位置
            $("#fi" + rowIndex).val("");
            //初始化保留价
            $("#rp" + rowIndex).val("");
        } else {
            _this.find("input").val("Y");
            $("#eri" + rowIndex).removeAttr("disabled");
            $("#era" + rowIndex).removeAttr("disabled");
            $("#fi" + rowIndex).removeAttr("disabled");
            $("#rp" + rowIndex).removeAttr("disabled");
            //初始化广告位ID
            if (currentRows[rowIndex].externalResIosId) {
                $("#eri" + rowIndex).val(currentRows[rowIndex].externalResIosId);
            }
            if (currentRows[rowIndex].externalResAndroidId) {
                $("#era" + rowIndex).val(currentRows[rowIndex].externalResAndroidId);
            }
            //初始化插入位置
            if (currentRows[rowIndex].frameIndex) {
                $("#fi" + rowIndex).val(currentRows[rowIndex].frameIndex);
            }
            //初始化保留价
            if (currentRows[rowIndex].reservePriceStr) {
                $("#rp" + rowIndex).val(currentRows[rowIndex].reservePriceStr);
            }
        }
    });
    $(button).text("确定");
}

/**
 * 保存
 * @param button
 * @param rowIndex
 */
function updateDspConfig(button, rowIndex) {
    var currentRows = $("#tbResource").bootstrapTable("getData");
    var dspEnable = $("#s" + rowIndex).find("input").val();
    var externalResIosId = $("#eri" + rowIndex).val();
    var externalResAndroidId = $("#era" + rowIndex).val();
    var frameIndex = $("#fi" + rowIndex).val();
    var reservePriceStr = $("#rp" + rowIndex).val();

    if (dspEnable == "Y") {
        if (frameIndex == "") {
            alert("请填写插入位置");
            return;
        } else if (!validateFrameIndex(frameIndex)) {
            alert("插入位置格式不正确");
            return;
        }
        if (reservePriceStr != "" && !validateReservePrice(reservePriceStr)) {
            alert("保留价格式不正确");
            return;
        }
    }

    if ("N" == currentRows[rowIndex].dspEnable && dspEnable == "N") {
        //没有任何的变化,不再提交
        // showReadOnlyView(button, rowIndex);
        $('#tbResource').bootstrapTable('updateRow', {index: rowIndex, row: currentRows[rowIndex]});
        return;
    }
    if (dspEnable == currentRows[rowIndex].dspEnable && externalResIosId == currentRows[rowIndex].externalResIosId && externalResAndroidId == currentRows[rowIndex].externalResAndroidId && frameIndex == currentRows[rowIndex].frameIndex && reservePriceStr == currentRows[rowIndex].reservePriceStr) {
        // showReadOnlyView(button, rowIndex);
        $('#tbResource').bootstrapTable('updateRow', {index: rowIndex, row: currentRows[rowIndex]});
        $(button).text("修改");
        return;
    }


    $(button).attr("disabled", "disabled");
    //保存设置
    $.ajax({
        method: "post",
        url: '/dspConfig',
        contentType: 'application/json',
        data: JSON.stringify({
            'resId': currentRows[rowIndex].resId,
            'dspEnable': dspEnable,
            'externalResIosId': externalResIosId,
            'externalResAndroidId': externalResAndroidId,
            'frameIndex': frameIndex,
            'reservePrice': reservePriceStr,
            'dspId': currentAdxDsp.id
        }),
        success: function (msg) {
            if (msg.status == 200) {
                currentRows[rowIndex].dspEnable = dspEnable;
                currentRows[rowIndex].externalResIosId = externalResIosId;
                currentRows[rowIndex].externalResAndroidId = externalResAndroidId;
                currentRows[rowIndex].frameIndex = frameIndex;
                currentRows[rowIndex].reservePriceStr = reservePriceStr;
                currentRows[rowIndex].dspId = currentAdxDsp.id;
                $('#tbResource').bootstrapTable('updateRow', {index: rowIndex, row: currentRows[rowIndex]});
                $(button).text("修改");
            } else {
                alert(msg.message);
            }
        },
        error: function (msg) {
            if (msg && msg.responseJSON && msg.responseJSON.message) {
                alert(msg.responseJSON.message);
            }
        },
        complete: function () {
            $(button).removeAttr("disabled");
        }
    });
}

$(function () {
    getResourceCode();

    //初始化adxDsp列表
    initAdxDsp();

    $(".button_use").click(function () {
        if (currentPageNumber == 1) {
            $('#tbResource').bootstrapTable('refresh', {query: queryParams});
        } else {
            $('#tbResource').bootstrapTable('selectPage', 1);
        }
    });
});

function getResourceCode() {
    parseLocalResourceCode();
}

function initAdxDsp() {
    $.ajax({
        method: "get",
        url: '/dspConfig/adxDsp',
        contentType: 'application/json',
        success: function (msg) {
            if (msg.status == 200) {
                var adxDsps = msg.result;
                if (!adxDsps || adxDsps.length == 0) {
                    $(".tab-body").css("text-align", "center").html("当前还没有创建第三方DSP");
                    return;
                }
                if (adxDsps.length <= 8) {
                    $("#tapAdxDsp").css("width", adxDsps.length * 120);
                } else {
                    $("#tapAdxDsp").css("overflow-x", "scroll");
                }
                // 动态增加联盟用户自建app的tab页签
                $(adxDsps).each(function (i) {
                    var adxDsp = adxDsps[i];
                    var classStr = "";
                    var styleStr = "";
                    if (i == 0) {
                        currentAdxDsp = adxDsp;
                        getDataList();
                        classStr += "active ";
                        styleStr = "border-radius: 3px 0 0 0";
                    }
                    if (i < adxDsps.length - 1) {
                        classStr += "tab_page";
                    }
                    if (i == adxDsps.length - 1) {
                        styleStr = "border-radius: 0 3px 0 0";
                    }
                    var li = $('<li role="presentation" value="' + adxDsp.id + '" class="' + classStr + '"><a href="#" style="' + styleStr + '">' + adxDsp.name + '</a></li>');
                    // 动态加标签应该在此
                    // 先处绑定事件，后append
                    li.click(function () {
                        currentAdxDsp = adxDsp;
                        // 同级其他tab标签变成未选中
                        $(this).parent().children('li').removeClass("active");

                        // 选中的三级标题tab样式调整为被选中
                        $(this).addClass("active");

                        if (currentPageNumber == 1) {
                            $('#tbResource').bootstrapTable('refresh', {query: queryParams});
                        } else {
                            $('#tbResource').bootstrapTable('selectPage', 1);
                        }

                    });
                    $('#tapAdxDsp').append(li);
                });
            } else {
                alert(msg.message);
            }
        },
        error: function (msg) {
            alert(msg.responseJSON.message);
        }
    });
}
