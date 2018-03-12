$(function () {
    $.getScript("/getMenus?_time=" + new Date().getTime(), function () {
        loadMenu();
    });

    /* 浏览器端：JQuery添加全局AJAX默认选项：complete回调函数：*/
    $(document).ajaxComplete(function (event, xhr, settings) {
        var httpStatus = xhr.status;
        if (httpStatus === 401) {
            window.location.href = '/login.html';
        }
    });
});

Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, // month
        "d+": this.getDate(), // day
        "h+": this.getHours(), // hour
        "m+": this.getMinutes(), // minute
        "s+": this.getSeconds(), // second
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
        "S": this.getMilliseconds()
    };
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
    }
    return format;
};
function loadMenu() {
    var html = "";
    $.each(menus, function (i, n) {
        html += generateMenu(n);
    });
    $("#mtree").append(html);
    $.getScript("/assets/scripts/mtree/js/mtree.js", function () {
        var menuid = localStorage.getItem("crmClickedMenuId");
        if (menuid == undefined || menuid == "undefined") {
            $("#mtree li[class*='mtree-node']:first-child").removeClass("mtree-closed").addClass(
                "mtree-active mtree-open").find("ul").css({
                "display": "block",
                "height": "auto"
            }).find("li:first-child").find("a").css("background-color", "#555");
        } else {
            var closestLi = $("a[menuid='" + menuid + "']").css("background-color", "#555").closest("li[class*='mtree-node']");
            closestLi.removeClass("mtree-closed")
                .addClass("mtree-active mtree-open").children("ul").css({
                "display": "block",
                "height": "auto"
            });
            if (closestLi.parent().closest("li[class*='mtree-node']").length > 0) {
                closestLi.parent().closest("li[class*='mtree-node']")
                    .removeClass("mtree-closed")
                    .addClass("mtree-active mtree-open").find("ul:first").css({
                    "display": "block",
                    "height": "auto"
                });
            }
        }
    });

    $("li > a").click(function () {
        //只有最小层级有可点击地址的菜单才会保存其ID值
        if ($(this).attr('href') !== '#') {
            localStorage.setItem("crmClickedMenuId", $(this).attr("menuid"));
            $(this).attr("href", $(this).attr("href") + "?_time=" + new Date().getTime())
        }
    });
}

/**
 * 用递归的方式生成多级菜单
 * @param data  菜单数据，是个数组
 * @returns {string}
 */
function generateMenu(data) {
    var html = '';
    html += '<li><a href="' + data.url + '" menuid="' + data.id + '">' + data.name + '</a>';
    if (data.children && data.children.length > 0) {
        html += "<ul>";
        $.each(data.children, function (jj, mm) {
            html += generateMenu(mm);
        });
        html += "</ul>";
    }
    html += "</li>";
    return html;
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)
        return unescape(r[2]);
    return null;
}

Number.prototype.toFixed2 = function () {
    return parseFloat(this.toString().replace(/(\.\d{2})\d+$/, "$1"));
};

function scrollToTableHead() {
    $("html, body").animate({
        scrollTop: $('table').offset().top - 75
    });
}

/**
 * 对title进行格式化，当前主要是替换了title中的空格
 * 换行: &#13; (ASCII码回车)， 回车: &#10; (ASCII码换行)，  空格: &nbsp;
 * @param title
 * @returns {string|XML|*|void}
 */
function formatTitleString(title) {
    var result = title.replace(/ /g, "&nbsp;");
    return result;
}

/**
 * 文本框只允许输入数字
 * @param event
 * @returns {boolean}
 */
function validateQty(event) {
    var key = window.event ? event.keyCode : event.which;

    if (event.keyCode == 8 || event.keyCode == 46
        || event.keyCode == 37 || event.keyCode == 39) {
        return true;
    } else if (key < 48 || key > 57) {
        return false;
    } else return true;
}

// 阿拉伯数字转换为简写汉字
function Arabia_To_SimplifiedChinese(Num) {
    for (i = Num.length - 1; i >= 0; i--) {
        Num = Num.replace(",", "")// 替换Num中的“,”
        Num = Num.replace(" ", "")// 替换Num中的空格
    }
    if (isNaN(Num)) { // 验证输入的字符是否为数字
        // alert("请检查小写金额是否正确");
        return;
    }
    // 字符处理完毕后开始转换，采用前后两部分分别转换
    part = String(Num).split(".");
    newchar = "";
    // 小数点前进行转化
    for (i = part[0].length - 1; i >= 0; i--) {
        if (part[0].length > 10) {
            // alert("位数过大，无法计算");
            return "";
        }// 若数量超过拾亿单位，提示
        tmpnewchar = ""
        perchar = part[0].charAt(i);
        switch (perchar) {
            case "0":
                tmpnewchar = "零" + tmpnewchar;
                break;
            case "1":
                tmpnewchar = "一" + tmpnewchar;
                break;
            case "2":
                tmpnewchar = "二" + tmpnewchar;
                break;
            case "3":
                tmpnewchar = "三" + tmpnewchar;
                break;
            case "4":
                tmpnewchar = "四" + tmpnewchar;
                break;
            case "5":
                tmpnewchar = "五" + tmpnewchar;
                break;
            case "6":
                tmpnewchar = "六" + tmpnewchar;
                break;
            case "7":
                tmpnewchar = "七" + tmpnewchar;
                break;
            case "8":
                tmpnewchar = "八" + tmpnewchar;
                break;
            case "9":
                tmpnewchar = "九" + tmpnewchar;
                break;
        }
        switch (part[0].length - i - 1) {
            case 0:
                tmpnewchar = tmpnewchar;
                break;
            case 1:
                if (perchar != 0) tmpnewchar = tmpnewchar + "十";
                break;
            case 2:
                if (perchar != 0) tmpnewchar = tmpnewchar + "百";
                break;
            case 3:
                if (perchar != 0) tmpnewchar = tmpnewchar + "千";
                break;
            case 4:
                tmpnewchar = tmpnewchar + "万";
                break;
            case 5:
                if (perchar != 0) tmpnewchar = tmpnewchar + "十";
                break;
            case 6:
                if (perchar != 0) tmpnewchar = tmpnewchar + "百";
                break;
            case 7:
                if (perchar != 0) tmpnewchar = tmpnewchar + "千";
                break;
            case 8:
                tmpnewchar = tmpnewchar + "亿";
                break;
            case 9:
                tmpnewchar = tmpnewchar + "十";
                break;
        }
        newchar = tmpnewchar + newchar;
    }
    // 替换所有无用汉字，直到没有此类无用的数字为止
    while (newchar.search("零零") != -1 || newchar.search("零亿") != -1 || newchar.search("亿万") != -1 || newchar.search("零万") != -1) {
        newchar = newchar.replace("零亿", "亿");
        newchar = newchar.replace("亿万", "亿");
        newchar = newchar.replace("零万", "万");
        newchar = newchar.replace("零零", "零");
    }
    // 替换以“一十”开头的，为“十”
    if (newchar.indexOf("一十") == 0) {
        newchar = newchar.substr(1);
    }
    // 替换以“零”结尾的，为“”
    if (newchar.lastIndexOf("零") == newchar.length - 1) {
        newchar = newchar.substr(0, newchar.length - 1);
    }
    return newchar;
}
