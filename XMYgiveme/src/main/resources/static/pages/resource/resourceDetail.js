var PFUNC = {};
var bidTypeflag = "0";//RTB
var areaOrientflag = "2";

PFUNC.initResourceDetail = function () {
    var url = window.location.href;
    var id = url.split("?")[1].split("=")[1];
    var urlstr = "/resources/" + id;
    $.ajax({
        method: "get",
        url: urlstr,
        contentType: 'application/json',
        data: {},
        success: function (msg) {
            $('#resName').val(msg.result.resName);
            //别名
            $('#resNameAlias').html(msg.result.alias);
            if (msg.result.originId) {
                $("#trOriginName").show();
                $("#spOriginName").text(msg.result.originName);
                $("#trOriginPlatform").show();
                $("#spOriginPlatform").text(msg.result.originPlatformName);
                $("#trOriginId").show();
                $("#spOriginId").text(msg.result.originId);
            } else {
                $("#trOriginName").hide();
                $("#trOriginPlatform").hide();
                $("#trOriginId").hide();
            }

            if (msg.result.originPlatform === 2) {
                $('#related-no').text('关联编号');
            }

            if (msg.result.channelName) {
                $('#channelName').text(msg.result.channelName);
            }
            //终端类型
            $.each(msg.result.resAppIds, function (i, val) {
                $("input[name=resAppIds][value=" + val + "]").prop("checked", true);
                if (val == 3) {
                    $("#resMobile").prop("checked", true);
                } else {
                    $("#resApp").prop("checked", true);
                }
            });
            if (msg.result.resBidtype == bidTypeflag) {
                $("input:radio[name='resBidtype']").first().prop("checked", true);
                $("input:radio[name='resAreaOrient']:eq(2)").parent().hide();//RTB不包含按门店投放
            } else {
                $("input:radio[name='resBidtype']").last().prop("checked", true);
            }
            //1=按城市投放 2=按广场投放 3=按门店投放
            if (msg.result.resAreaOrient == "1") {
                $("input:radio[name='resAreaOrient']:eq(1)").prop("checked", true);
            } else if (msg.result.resAreaOrient == "2") {
                $("input:radio[name='resAreaOrient']:eq(0)").prop("checked", true);
            } else {
                $("input:radio[name='resAreaOrient']:eq(2)").prop("checked", true);
            }

            var value = msg.result.resourceSpecList;
            $.each(value, function (i, val) {
                //console.log(val.resLength);
                if (i == 0) {
                    $('#resLength').val(val.resLength);
                    $('#resWidth').val(val.resWidth);
                    $('#resImgPath').attr("src", val.resImgPath);
                }
                return false;
            });
            $('#resCarouselNum').val(msg.result.resCarouselNum);
            $('#resContent').val(msg.result.resContent);
        }
    });
};

PFUNC.returnList = function () {
    $("#returnList").unbind().bind("click", function () {
        javascript:location.href = 'resourceList.html';
    });
};

//页面初始化
$(document).ready(function () {
    //初始化页面事件
    PFUNC.initResourceDetail.call(this);

    PFUNC.returnList.call(this);

});	
