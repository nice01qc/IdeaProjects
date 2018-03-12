var PFUNC = {};
var items_per_page = 20;
var current_page = 1;
var pageTotal = 0;
var trThis;
var resourceCode;

function parseLocalResourceCode() {
    var resourceCodeStr = '{"resApptype":[
    {
        "columnCode":"app_type",
        "columnDesc":"飞凡iOS",
        "columnValue":"1",
        "id":6,
        "position":1,
        "remark":"投放应用",
        "status":0,"tableCode":"common"
    },
    {
        "columnCode":"app_type",
        "columnDesc":"飞凡安卓",
        "columnValue":"2",
        "id":7,
        "position":2,
        "remark":"投放应用",
        "status":0,
        "tableCode":"common"
    },
     {
        "columnCode":"app_type","columnDesc":"iOS",
        "columnValue":"6",
        "id":8,
        "position":3,
        "remark":"投放应用",
        "status":0,"tableCode":"common"
    },
    {
        "columnCode":"app_type",
        "columnDesc":"安卓",
        "columnValue":"7",
        "id":9,
        "position":4,
        "remark":"投放应用",
        "status":0,
        "tableCode":"common"
    },
    {
        "columnCode":"app_type",
        "columnDesc":"智能储物柜",
        "columnValue":"5",
        "id":118,
        "position":5,
        "remark":"投放应用",
        "status":0,
        "tableCode":"common"
    }],
    
    "resBidtype":[
        {
            "columnCode":"res_bidtype",
            "columnDesc":"RTB",
            "columnValue":"0",
            "id":97,
            "position":1,
            "remark":"议价类型",
            "status":0,
            "tableCode":"resource_info"
        },{
            "columnCode":"res_bidtype",
            "columnDesc":"CPT",
            "columnValue":"1",
            "id":98,
            "position":2,
            "remark":"议价类型",
            "status":0,
            "tableCode":"resource_info"}],
      
      "resPositiontype":[
        {
            "columnCode":"res_positiontype",
            "columnDesc":"手机横幅",
            "columnValue":"0",
            "id":10,
            "position":1,
            "remark":"资源位类型",
            "status":0,
            "tableCode":"resource_info"
        },{
            "columnCode":"res_positiontype",
            "columnDesc":"手机全屏",
            "columnValue":"1",
            "id":11,
            "position":2,
            "remark":"资源位类型",
            "status":0,
            "tableCode":"resource_info"
        },{
            "columnCode":"res_positiontype",
            "columnDesc":"手机插屏",
            "columnValue":"2",
            "id":12,
            "position":3,
            "remark":"资源位类型",
            "status":0,
            "tableCode":"resource_info"
        },{
            "columnCode":"res_positiontype",
            "columnDesc":"手机开机画面",
            "columnValue":"3",
            "id":13,
            "position":4,
            "remark":"资源位类型",
            "status":0,
            "tableCode":"resource_info"
        },{
            "columnCode":"res_positiontype",
            "columnDesc":"定制轮播",
            "columnValue":"4",
            "id":14,
            "position":5,
            "remark":"资源位类型",
            "status":0,

            "tableCode":"resource_info"
        },{
            "columnCode":"res_positiontype",
            "columnDesc":"云POS闲时",
            "columnValue":"5",
            "id":15,
            "position":6,
            "remark":"资源位类型","status":0,"tableCode":"resource_info"
        },{
            "columnCode":"res_positiontype",
            "columnDesc":"云POS忙时",
            "columnValue":"6",
            "id":16,
            "position":7,
            "remark":"资源位类型",
            "status":0,
            "tableCode":"resource_info"
        },{
            "columnCode":"res_positiontype","columnDesc":"Beacon插屏",
            "columnValue":"7",
            "id":120,
            "position":8,
            "remark":"资源位类型",
            "status":1,
            "tableCode":"resource_info"
        },{
            "columnCode":"res_positiontype",
            "columnDesc":"智能储物柜",
            "columnValue":"8",
            "id":119,
            "position":9,
            "remark":"资源位类型",
            "status":0,
            "tableCode":"resource_info"
        }],
        


        "resTerminaltype":[
            {
                "columnCode":"terminal_type",
                "columnDesc":"APP",
                "columnValue":"0",
                "id":0,
                "position":1,
                "remark":"投放终端",
                "status":0,"tableCode":"common"
            },{
                "columnCode":"terminal_type",
                "columnDesc":"飞凡M站",
                "columnValue":"1",
                "id":3,
                "position":2,
                "remark":"投放终端",
                "status":0,
                "tableCode":"common"
            },{
                "columnCode":"terminal_type",
                "columnDesc":"Beacon",
                "columnValue":"2",
                "id":4,
                "position":3,
                "remark":"投放终端",
                "status":0,
                "tableCode":"common"
            },{
                "columnCode":"terminal_type",
                "columnDesc":"云POS",
                "columnValue":"3",
                "id":5,
                "position":4,
                "remark":"投放终端",
                "status":0,
                "tableCode":"common"
            },{
                "columnCode":"terminal_type",
                "columnDesc":"智能储物柜",
                "columnValue":"4",
                "id":117,
                "position":5,
                "remark":"投放终端",
                "status":0,"tableCode":"common"}],
            
            
            "resUsertype":[
                {
                    "columnCode":"resource_type",
                    "columnDesc":"定制",
                    "columnValue":"0",
                    "id":1,
                    "position":1,
                    "remark":"投放资源",
                    "status":0,
                    "tableCode":"common"
                },{
                    "columnCode":"resource_type",
                    "columnDesc":"联盟",
                    "columnValue":"1",
                    "id":2,
                    "position":2,
                    "remark":"投放资源",
                    "status":0,
                    "tableCode":"common"
                }]}';
    resourceCode = $.parseJSON(resourceCodeStr);
    var resTypeOfFeed = {columnCode:"res_positiontype",columnDesc:"信息流广告",columnValue:"10",remark:"资源位类型",tableCode:"resource_info"};
    resourceCode.resPositiontype.push(resTypeOfFeed);
}

function getTypeDesc(typeList, typeCode) {
    var desc = "";
    $.each(typeList, function (rowIndex, currentSysCode) {
        if (typeCode == currentSysCode.columnValue) {
            desc = currentSysCode.columnDesc;
            return;
        }
    });
    return desc;
}

function resUsertypeChanged(selectedUserType) {
    if (selectedUserType != "") {
        var tmpHtml = '<option value="">全部</option>';
        if (selectedUserType == 0) {// 定制用户
            tmpHtml += '<option value="0">APP</option>';
            tmpHtml += '<option value="1">飞凡M站</option>';
            tmpHtml += '<option value="2">Beacon</option>';
            tmpHtml += '<option value="3">云POS</option>';
            tmpHtml += '<option value="4">智能储物柜</option>';
        } else {// 联盟用户
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
        if (selectedTerminalType == 0 || selectedTerminalType == 2) {// APP或者Beacon
            if (selectedUserType == 0) {// 定制用户
                tmpHtml += '<option value="1">飞凡iOS</option>';
                tmpHtml += '<option value="2">飞凡安卓</option>';
            } else {// 联盟用户
                tmpHtml += '<option value="6">iOS</option>';
                tmpHtml += '<option value="7">安卓</option>';
            }
        }
        $('#resApptype').html(tmpHtml);
    } else {
        $('#resApptype').html('<option value="">全部</option>');
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
    if (oNum.charAt(oNum.length - 1) == ".") {
        return false;
    }
    try {
        if (parseFloat(oNum) != oNum) {
            return false;
        }
    } catch (ex) {
        return false;
    }
    return oNum <= 999999.99;
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

function dateTimeIsNOK() {
    return Date.parse($('#sdate').val()) > Date.parse($('#edate').val());
}

function checkMyForm() {
    var tmpResId = $('#resId').val()
    if (tmpResId != "" && !validateNumber(tmpResId)) {
        alert("请输入数字");
        document.getElementById("resId").focus();
        return false;
    }
    if (dateTimeIsNOK()) {
        alert("开始时间不能大于结束时间。");
        return false;
    }
    return true;

}

// 'resource_type', '0', '定制', '1', '投放资源', '0'
// 'resource_type', '1', '联盟', '2', '投放资源', '0'
// 'terminal_type', '0', 'APP', '1', '投放终端', '0'
// 'terminal_type', '1', '飞凡M站', '2', '投放终端', '0'
// 'terminal_type', '2', 'Beacon', '3', '投放终端', '0'
// 'terminal_type', '3', '云POS', '4', '投放终端', '0'
// 'terminal_type', '4', '智能储物柜', '5', '投放终端', '0'
// 'app_type', '0', '飞凡iOS', '1', '投放应用', '0'
// 'app_type', '1', '飞凡安卓', '2', '投放应用', '0'
// 'app_type', '2', 'iOS', '3', '投放应用', '0'
// 'app_type', '3', '安卓', '4', '投放应用', '0'
function parseAppType(resourceType, terminalType, appType) {
    if (resourceType == 0) {// 定制类型
        if (terminalType == 0 || terminalType == 2) {
            if (appType == null || appType == undefined) {
                return "";
            }
            if (appType.indexOf(",") != -1) {
                return "飞凡APP"
            }
            return getTypeDesc(resourceCode.resApptype, appType);  // 如果是定制app，直接显示appType
            // 的名称
        } else {
            return getTypeDesc(resourceCode.resTerminaltype, terminalType);   // 如果是非定制app，显示终端名称
        }
    } else {// 联盟类型
        if (terminalType == 0 || terminalType == 2) {
            return getTypeDesc(resourceCode.resUsertype, resourceType) + getTypeDesc(resourceCode.resApptype, appType); // 如果是联盟app，显示
                                                                                                                        // resourceType+appType
                                                                                                                        // eg:联盟+ios
        } else {
            return getTypeDesc(resourceCode.resUsertype, resourceType) + getTypeDesc(resourceCode.resTerminaltype, terminalType);	// 联盟非app，
            // 显示
            // resourceType+terminalType
            // eg:联盟+m站
        }
        // 应该要增加一种判断 如果是联盟 + beacon + android/ios
    }
}

function getDataList(isManual) {
    if (isManual == 1) {
        if (!checkMyForm()) {
            return
        }
    }
    var urlstr = "/resources/list?pageIndex=" + current_page + "&pageSize=" + items_per_page;
    var startTime = $('#sdate').val();
    if (startTime == null || startTime == "") {
        startTime = null;
    } else {
        startTime = new Date($('#sdate').val());
        startTime.setHours(0);// 将小时设为0
        startTime = startTime.getTime();
    }
    var endTime = $('#edate').val();
    if (endTime == null || endTime == "") {
        endTime = null;
    } else {
        endTime = new Date($('#edate').val());
        endTime.setHours(0);
        endTime = endTime.getTime() + 24 * 60 * 60 * 1000 - 1;// 结束时间为23:59:59:999
    }
    $.ajax({
        method: "post",
        url: urlstr,
        contentType: 'application/json',
        data: JSON.stringify({
            'resId': $('#resId').val(),
            'resName': $('#resName').val(),
            'resUserEmail': $('#resUserEmail').val(),
            'resStatus': $('#resStatus').val(),
            'resPositiontype': $('#resPositiontype').val(),
            'resUsertype': $('#resUsertype').val(),
            'resTerminaltype': $('#resTerminaltype').val(),
            'resApptype': $('#resApptype').val(),
            'startTime': startTime,
            'endTime': endTime,
            'resAreaOrient': $("#resAreaOrient").val()
        }),
        success: function (msg) {
            if (msg.status != 200) {
                $('.page-info').text('1 / 1');
                $('#resourceList').html('<tr class="tr-a" ><td colspan =14>查询出错</td></tr>');
                return;
            }
            var time;
            var html = '';
            var start_index = (current_page - 1) * items_per_page;
            $('#resourceList').html(html);
            $.each(msg.result.rows, function (rowIndex, curResourceItem) {
                var index = start_index + rowIndex;
                if (rowIndex % 2 == 0) {
                    html += '<tr class="tr-b">';
                } else {
                    html += '<tr class="tr-a">';
                }
                html += '<td class="td-d">' + (index + 1) + '</td>';
                html += '<td class="td-d">' + curResourceItem.resId + '</td>';
                html += '<td class="td-d" style="    word-wrap: break-word; word-break: break-all;">' + curResourceItem.resName + '</td>';
                var appType = parseAppType(curResourceItem.resUsertype,
                    curResourceItem.resTerminaltype, curResourceItem.resApptype);
                html += '<td class="td-d">' + appType + '</td>';
                var positionType = getTypeDesc(resourceCode.resPositiontype, curResourceItem.resPositiontype);
                html += '<td class="td-a">' + positionType + '</td>';
                html += '<td class="td-d">' + curResourceItem.imageSize + '</td>';

                var bidType = getTypeDesc(resourceCode.resBidtype, curResourceItem.resBidtype);
                if (curResourceItem.resPositiontype == 4) {
                    bidType = "全部";
                }
                html += '<td class="td-d">' + bidType + '</td>';
                if (curResourceItem.resSwitch == 1) {
                    html += '<td class="td-d" > <img src="../../assets/CRM/img/on-nov.png"/></td>';
                } else {
                    html += '<td class="td-d" > <img src="../../assets/CRM/img/off-nov.png"/></td>';
                }
                var adstatus = curResourceItem.resStatus;
                var adstatus_msg = "未知"
                if (adstatus == 1) {
                    adstatus_msg = "正常";
                } else if (adstatus == 2) {
                    adstatus_msg = "未通过";
                } else if (adstatus == 0) {
                    adstatus_msg = "待审核";
                }
                html += '<td class="td-d">' + adstatus_msg + '</td>';
                time = new Date(curResourceItem.resCreatedate);
                var month = time.getMonth() + 1 < 10 ? "0" + (time.getMonth() + 1) : time.getMonth() + 1;
                var day = time.getDate() < 10 ? "0" + time.getDate() : time.getDate();
                var hours = time.getHours() < 10 ? "0" + time.getHours() : time.getHours();
                var minutes = time.getMinutes() < 10 ? "0" + time.getMinutes() : time.getMinutes();
                html += '<td class="td-f">' + time.getFullYear() + '-' + month + '-' + day + ' ' + hours + ':' + minutes + '</td>';
                html += '<td class="td-b" id="' + curResourceItem.resId + 'price">';

                var cpm = curResourceItem.resCpmStr;
                var cpc = curResourceItem.resCpcStr;
                var cpt = curResourceItem.minResCptStr;

                var date = "";
                if (adstatus == 0) {

                } else {
                    // date='<a href="./resourceStatistics.html?resId=' + curResourceItem.resId + '&userEmail=' + curResourceItem.accountEmail + '">数据</a><br/>';
                    // html +='<a
                    // href="./resourceDataStatistics.html?resId='+curResourceItem.resId+'">数据</a></td>';
                }
                if (curResourceItem.resPositiontype == 4) {// 定制轮播
                    html += "<a href='javascript:;' class='viewAtt' param='" + curResourceItem.resId + "'>详情</a><br/>";
                    html += date;
                    html += "<a href='javascript:;' onclick='openRTBprice(" + cpm + "," + cpc + "," + cpt + "," + curResourceItem.resId + "," + curResourceItem.resPositiontype + ")'>RTB定价</a><br/>";
                    if (curResourceItem.resAreaOrient == 2) {// 地域定向：广场
                        html += '<a href="./updatePriceByPlaza.html?resId=' + curResourceItem.resId + '&resAreaOrient=' + curResourceItem.resAreaOrient + '&resName=' + curResourceItem.resName + '">CPT定价</a>';
                    } else if (curResourceItem.resAreaOrient == 3) {// 地域定向:门店
                        html += '<a href="./updatePriceByStore.html?resId=' + curResourceItem.resId + '&resAreaOrient=' + curResourceItem.resAreaOrient + '&resName=' + curResourceItem.resName + '">CPT定价</a>';
                    } else {
                        html += '<a href="./updatePrice.html?resId=' + curResourceItem.resId + '&resAreaOrient=' + curResourceItem.resAreaOrient + '&resName=' + curResourceItem.resName + '">CPT定价</a>';
                    }
                } else {
                    html += date;
                    if (curResourceItem.resBidtype == 0) {
                        html += "<a href='javascript:;' onclick='openRTBprice(" + cpm + "," + cpc + "," + cpt + "," + curResourceItem.resId + "," + curResourceItem.resPositiontype + ")'>RTB定价</a><br/>";
                    } else {
                        if (curResourceItem.resAreaOrient == 2) {// 地域定向：广场
                            html += '<a href="./updatePriceByPlaza.html?resId=' + curResourceItem.resId + '&resAreaOrient=' + curResourceItem.resAreaOrient + '&resName=' + curResourceItem.resName + '">CPT定价</a>';
                        } else if (curResourceItem.resAreaOrient == 3) {// 地域定向:门店
                            html += '<a href="./updatePriceByStore.html?resId=' + curResourceItem.resId + '&resAreaOrient=' + curResourceItem.resAreaOrient + '&resName=' + curResourceItem.resName + '">CPT定价</a>';
                        } else {
                            html += '<a href="./updatePrice.html?resId=' + curResourceItem.resId + '&resAreaOrient=' + curResourceItem.resAreaOrient + '&resName=' + curResourceItem.resName + '">CPT定价</a>';
                        }
                    }
                }
                html += "</td>";

                html += '<td class="td-b" style="word-wrap:break-word;word-break:break-all;">' + curResourceItem.accountEmail + '</td>';
                html += '<td class="td-rtbprice" style="width:275px;line-height: 150%;"><span style="width:180px;" id="' + curResourceItem.resId + 'PriceSpan">';
                // 获取价格字段的文字
                html += getPriceText(cpm, cpc, cpt, curResourceItem.resId, curResourceItem.resPositiontype, curResourceItem.resBidtype);
                html += "</span>"
                html += "</td>";

                html += '<td class="td-b" style="min-width: 50px;">';
                if (adstatus_msg != '未通过') {
                    if (adstatus_msg == '待审核') {
                        html += '<button class="button_8" onclick="successTr(this,' + curResourceItem.resId + ')">通过</button>';
                    }
                    html += ' <button class="button_8" onclick="showAudit(this,' + curResourceItem.resId + ')">驳回</button>';
                }
                html += "</td></tr>";
            });
            if (html == '') {
                html = '<tr class="tr-a" ><td colspan =14>无查询结果</td></tr>';
                $("#Pagination").hide();
            } else {
                $("#Pagination").show();
            }
            $('#resourceList').html(html);

            // 注册事件
            $('#resourceList').find("a.viewAtt").unbind().bind("click", function () {
                var id = $(this).parent().parent().find('td').eq(1).text();
                javascript:location.href = 'resourceDetail.html?id=' + id;
            });

            if (msg.result.total == 0) {
                $('.page-info').text('0 / 0');
            } else {
                pageTotal = Math.ceil(msg.result.total / items_per_page);
                $('.page-info').text(current_page + ' / ' + pageTotal);
            }
            $("#current_page").val('');
            updatePageBtnStatus();
        }
    });
}

function getPriceText(cpm, cpc, cpt, resId, type, bidType) {
    var text = "";
    if (bidType == 0 || type == 4) {
        text += 'CPM=' + "<div id='res" + resId + "Cpm' style='display:inline'>" + cpm + "</div>" + '';
        text += '<br>CPC=' + "<div id='res" + resId + "Cpc'  style='display:inline'>" + cpc + "</div>" + '';
    }
    if (type == 4 || bidType == 1) {
        text += '<div>CPT最低' + cpt + '/地</div>';
    } else {
        text += "";
    }
    return text;

}
// 打开修改rtb价格弹窗
function openRTBprice(cpm, cpc, cpt, resId, positiontype) {
    var cpm1 = $("#res" + resId + "Cpm").text();
    var cpc1 = $("#res" + resId + "Cpc").text();
    $("#cpmPrice").val(cpm1);
    $("#cpcPrice").val(cpc1);
    $("#oldCpmPrice").val(cpm1);
    $("#oldCpcPrice").val(cpc1);
    $("#resIdAudit").val(resId);
    $("#oldCptPrice").val(cpt.toFixed(2));
    $("#positiontype").val(positiontype);
    $("#priceAudit").show();
}
function closeRTBprice() {
    $("#priceAudit").hide();
}
// 修改价格
function modifyPrice() {
    var cpm = $("#cpmPrice").val();
    var cpc = $("#cpcPrice").val();
    var resId = $("#resIdAudit").val();
    if (!validatePrice(cpm) || !validatePrice(cpc)) {
        alert("请输入数字［0～999999.99］，最多保留小数点后两位");
        return;
    }
    var cpmOld = $("#oldCpmPrice").val();
    var cpcOld = $("#oldCpcPrice").val();
    var cpt = $("#oldCptPrice").val();
    var positiontype = $("#positiontype").val();
    if ((cpm != cpmOld || cpc != cpcOld) && confirm('您确定修改这个资源位的RTB价格吗 ？')) {
        // 价格有更新，且用户选择修改
        $.ajax({
            method: "post",
            url: '/resources/rtbprice',
            contentType: 'application/json',
            data: JSON.stringify({'resId': resId, 'resCpm': cpm, 'resCpc': cpc}),
            success: function (msg) {
                if (msg.status == 200) {
                    // 更新状态列
                    var newCPM = msg.result.resCpmStr;
                    var newCPC = msg.result.resCpcStr;
                    var spanName = resId + "PriceSpan";
                    $("#" + spanName).html(getPriceText(newCPM, newCPC, cpt, resId, positiontype, 0));
                    closeRTBprice();
                } else {
                    alert("更新RTB价格信息失败，请刷新页面");
                    closeRTBprice();
                }
            }
        });
    } else {
        closeRTBprice();
    }
}
function openTr(id) {
    javascript:location.href = 'resourceDetail.html/id=' + id;
}

function successTr(nowTr, id) {
    if (confirm('您确定要审核通过这个资源位吗 ？')) {
        $.ajax({
            method: "post",
            url: '/resources/auditresult',
            contentType: 'application/json',
            data: JSON.stringify({'resId': id, 'resStatus': '1', 'resAuditComment': ''}),
            success: function (msg) {
                if (msg.status == 200 && msg.result.resStatus == 1) {
                    // 更新状态列
                    $(nowTr).parent().parent().children('td').eq(8).html('正常');
                    $(nowTr).parent().html('<button class="button_8" onclick="showAudit(this,' + id + ')">驳回</button>');
                } else {
                    alert("更新审核信息失败，请刷新页面");
                }
            }
        });
    }
}

function showUpdateRTBPrice(nowTr, id, cpm, cpc) {
    var modifyRTBPriceHTML = '<span>' + '<span style="width:42px">CPM=</span>' + '<input id="input_rescpm" value="' + cpm + '"/>' +
        '<br/>' + '<span style="width:42px">CPC=</span>' + '<input id="input_rescpc" value="' + cpc + '"/></span><button class="button_8" onclick="updateRTBPrice(this,' + id + ',' + cpm + ',' + cpc + ')">保存</button>'
    $(nowTr).parent().html(modifyRTBPriceHTML);
}


function updateRTBPrice(nowTr, id, cpm, cpc) {
    var updateCPM = $(nowTr).parent().find('#input_rescpm').val();
    var updateCPC = $(nowTr).parent().find('#input_rescpc').val();
    if (!validatePrice(updateCPM) || !validatePrice(updateCPC)) {
        alert("请输入数字［0～999999.99］，最多保留小数点后两位");
        return;
    }

    if ((cpm != updateCPM || cpc != updateCPC) && confirm('您确定修改这个资源位的RTB价格吗 ？')) {
        // 价格有更新，且用户选择修改
        $.ajax({
            method: "post",
            url: '/resources/rtbprice',
            contentType: 'application/json',
            data: JSON.stringify({'resId': id, 'resCpm': updateCPM, 'resCpc': updateCPC}),
            success: function (msg) {
                if (msg.status == 200) {
                    // 更新状态列
                    var newCPM = msg.result.resCpmStr;
                    var newCPC = msg.result.resCpcStr;
                    var updatedRTBPriceHTML = '<span>CPM=' + newCPM + '<br>CPC=' + newCPC
                        + '</span><img onclick="showUpdateRTBPrice(this,' + id + ',' + newCPM + ',' + newCPC + ')" src="../../assets/CRM/img/record-icon.png"/>';
                    $(nowTr).parent().html(updatedRTBPriceHTML);
                } else {
                    alert("更新RTB价格信息失败，请刷新页面");
                    // 更新失败，还是显示原来老价格
                    var updatedRTBPriceHTML = '<span>CPM=' + cpm + '<br>CPC=' + cpc
                        + '</span><img onclick="showUpdateRTBPrice(this,' + id + ',' + cpm + ',' + cpc + ')" src="../../assets/CRM/img/record-icon.png"/>';
                    $(nowTr).parent().html(updatedRTBPriceHTML);
                }
            }
        });
    } else {
        var updatedRTBPriceHTML = '<span>CPM=' + cpm + '<br>CPC=' + cpc
            + '</span><img onclick="showUpdateRTBPrice(this,' + id + ',' + cpm + ',' + cpc + ')" src="../../assets/CRM/img/record-icon.png"/>';
        $(nowTr).parent().html(updatedRTBPriceHTML);
    }
}


function submitAudit() {
    var id = $("#auditId").val();
    var auditComment = $('#reason').val();
    if (auditComment == '') {
        alert('驳回理由不能为空，请输入内容!');
    } else if (auditComment.length > 100) {
        alert('请不要超过100个字符');
    } else {
        $.ajax({
            method: "post",
            url: '/resources/auditresult',
            contentType: 'application/json',
            data: JSON.stringify({'resId': id, 'resStatus': '2', 'resAuditComment': auditComment}),
            success: function (msg) {
                if (msg.status == 200 && msg.result.resStatus == 2) {
                    // 更新状态列
                    $(trThis).parent().parent().children('td').eq(8)
                        .html('未通过');
                    $(trThis).parent().html('');
                    closeAudit();
                } else {
                    alert("更新审核信息失败，请刷新页面");
                }
            }
        });
    }
}

$(function () {
    getResourceCode();
    // 恢复选择信息
    var terminalType = $("#resTerminaltype").val();
    var appType = $("#resApptype").val();
    resUsertypeChanged($("#resUsertype").val());
    resTerminaltypeChanged(terminalType);
    $("#resTerminaltype").val(terminalType);
    $("#resApptype").val(appType);

    getDataList(0);
    $("#pageNumClick").click(function () {
        var goToPageNumStr = $("#current_page").val();
        $("#current_page").val("");
        if (goToPageNumStr != '' && !isNaN(goToPageNumStr)) {
            var goToPageNum = parseInt(goToPageNumStr);
            if (goToPageNum <= 0) {
                goToPageNum = 1;
            } else if (goToPageNum > pageTotal) {
                goToPageNum = pageTotal;
            }
            // 判断是否跳转到当前页
            if (goToPageNum == current_page) {
                return false;
            } else {
                current_page = goToPageNum;
                scrollToTableHead();
                getDataList(1);
            }
        }
    });
    $(".next-page").click(function () {
        if (current_page + 1 <= pageTotal) {
            current_page = current_page + 1;
            scrollToTableHead();
            getDataList(1);
        }
    });

    $(".pre-page").click(function () {
        if (current_page - 1 > 0) {
            current_page = current_page - 1;
            scrollToTableHead();
            getDataList(1);
        }
    });

    $(".button_use").click(function () {
        current_page = 1;
        getDataList(1);
    });
    $('.date-picker').datepicker({
        language: "zh-CN",
        autoclose: true,
        todayHighlight: true,
        clearBtn: true
    })
    $("#sdate").datepicker().on("changeDate", function (e) {
        $("#edate").datepicker("setStartDate", $("#sdate").datepicker("getDate"));
    });
    $("#edate").datepicker().on("changeDate", function (e) {
        $("#sdate").datepicker("setEndDate", $("#edate").datepicker("getDate"));
    });
// PFUNC.initResourceRegion.call(this);

});

function getResourceCode() {
    parseLocalResourceCode();
// var urlstr = "/resources/code"
// $.ajax({
// type : "get",
// url : urlstr,
// data : {
// },
// dataType : 'json',
// contentType : "application/x-www-form-urlencoded",
// success : function(msg) {//如果远端获取成功使用远端的数据
// if(msg.status == 200 && msg.result != null){
// resourceCode = msg.result;
// }
// }
// });
}

function showAudit(nowTr, id) {
    $('#reason').val('');
    $("#auditId").val(id);
    trThis = nowTr;
    $("#resourceAudit").show();
}

function closeAudit() {
    $("#auditId").val('');
    $("#resourceAudit").hide();
}

/**
 * 更新翻页按钮状态
 *
 * @returns
 */
function updatePageBtnStatus() {
    $(".pagination .next-page").removeClass("unused");
    $(".pagination .pre-page").removeClass("unused");
    if (current_page == pageTotal) {
        $(".pagination .next-page").addClass("unused");
    }
    if (current_page == 1) {
        $(".pagination .pre-page").addClass("unused");
    }
}
