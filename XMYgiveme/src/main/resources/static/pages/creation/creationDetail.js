var PFUNC = {};
var creativePic;

var PREVIEW_PAGE_STYLE = '<style type="text/css">body,html {height:100%;font-size:100px}@media (min-width:400px) {html {font-size:125px!important}}' +
    '@media (min-width:480px) {html {font-size:150px!important}}@media (min-width:560px) {html {font-size:175px!important}}' +
    '@media (min-width:640px) {html {font-size:200px!important}} ' +
    '* {margin:0;padding:0;tap-highlight-color:rgba(0,0,0,0); -webkit-tap-highlight-color:rgba(0,0,0,0); -ms-tap-highlight-color:rgba(0,0,0,0); ' +
    '-webkit-box-sizing:border-box; -moz-box-sizing:border-box; -ms-box-sizing:border-box;box-sizing:border-box; -webkit-text-size-adjust:none; ' +
    '-moz-text-size-adjust:none; -ms-text-size-adjust:none;text-size-adjust:none}article,aside,details,figcaption,figure,footer,header,menu,nav,section {display:block;clear:both}' +
    'body {position:relative;top:0;bottom:0;left:0;right:0;max-width:720px;margin:0 auto;font:.14rem/1.5 Microsoft Yahei,sans-serif,Helvetica;color:#333;background:#fff}' +
    'h1,h2,h3,h4,h5,h6 {font-size:100%;font-weight:400}form {display:inline}ul,ol {list-style:none}img {max-width:100%;vertical-align:middle;border:0; -ms-interpolation-mode:bicubic}' +
    'li>a {display:block}a {color:#333;outline:0;text-decoration:none;}a:active {color:inherit}.ellipsis {white-space:nowrap;text-overflow:ellipsis;overflow:hidden}.main-content {padding:.19rem .12rem 0}' +
    'embed, iframe {max-width: 100%;height:auto;}</style>';

resourceLocationTypeMap = {
    "0": "手机横幅",
    "1": "手机全屏",
    "2": "手机插屏",
    "3": "手机开机画面",
    "7": "Beacon插屏",
    "4": "定制轮播",
    "5": "云POS闲时",
    "6": "云POS忙时",
    "8": "智能储物柜",
    "9": "H5",
    "10": "信息流广告"
};

PFUNC.initCreationDetail = function () {
    var urlParams = getUrlParams(window.location.href);
    var id = urlParams.id,
        isH5 = urlParams.isH5;
    if (isH5 == 1) {
        //======配置h5编辑器 start =========
        var h5Editor = new wangEditor('h5-page-content');
        h5Editor.config.menuFixed = false;
        // 阻止输出log
        wangEditor.config.printLog = false;
        h5Editor.create();
        h5Editor.disable();

        //======配置h5编辑器 end =========
        //======初始化展示h5创意组件 start =========
        $('#right-model-area').hide();
        $('#not-h5-edit-area').hide();
        $('#h5-edit-area').show();
        $('#resourceLocationType').attr('disabled', 'disabled');
        //======初始化展示h5创意组件  end  =========
        $.get('/creation/getH5LandingPage', {id: id}, function (data) {
            if (data.status === 200) {
                var result = data.result;
                if (result) {
                    $('#resourceLocationType').append('<option value="9" selected>H5</option>');
                    $('#creationName').val(result.creationName);
                    $('#h5-page-title').val(result.pageTitle);
                    h5Editor.$txt.html(result.pageBody);
                    $('#generatedLink').text(result.pageUrl);
                    $('#creatorName').text(result.userName);
                    $('#h5-preview-btn').on('click', function () {
                        var h5PageContent = '<div class="main-content">' + h5Editor.$txt.html() + '</div>';
                        var h5PreviewModal = $('#preview-modal');
                        h5PreviewModal.modal('show');
                        h5PreviewModal.find('iframe').attr('srcdoc', PREVIEW_PAGE_STYLE + h5PageContent);
                    });
                }
            } else {
                alert(data.message);
            }
        }, 'json').error(function () {
            alert('获取详情数据失败');
        });
        return;
    }
    var urlstr = "/creation/" + id;
    $.ajax({
        method: "get",
        url: urlstr,
        contentType: 'application/json',
        data: {},
        success: function (msg) {
            $('#creationName').val(msg.result.creationName);
            $('#creationLinkContent').val(msg.result.creationLinkContent);
            $('#resourceLocationType').find("option:selected").text(resourceLocationTypeMap[msg.result.resourceLocationType]);
            $('#resourceLocationType').find("option:selected").val(msg.result.resourceLocationType);
            $('#creationLinkType').find("option:selected").text(msg.result.creationLinkName);
            $('#resourceLocationType').attr("disabled", true);
            $('#creationLinkType').attr("disabled", true);
            creativePic = msg.result.creationPicList;
            showPos(msg.result);
            if (msg.result.resourceLocationType == 4) {
                var res_names;
                $.ajax({
                    url: "/resources/" + msg.result.resourceId,
                    method: 'get',
                    async: false,
                    dataType: 'text',
                    error: function () {
                        alert('加载信息错误!');
                    },
                    success: function (data) {
                        var objs = JSON.parse(data);
                        res_names = objs.result.resName;
                    }
                });
                $('.module_c_info').css('display', 'block');
                $('.module_c_info').html('');
                $('.module_c_info').html('<div>资源ID：' + msg.result.resourceId + '</div><div>资源名称：' + res_names + '</div>');
            }
        }
    });
};

PFUNC.returnList = function () {
    $("#returnList").unbind().bind("click", function () {
        javascript:location.href = 'creationList.html';
    });
};

//横屏竖屏查看
function controlPicShow(sign) {
    if (sign == 1) {
        $("#twoHeng").hide();
        $("#twoShu").show();
    } else {
        $("#twoHeng").show();
        $("#twoShu").hide();
    }
}

function showPos(creationDetail) {
    var resourceType = $("#resourceLocationType").val();//创意类型
    if (resourceType == 0) {
        if (true) {//修改
            $('#fileListPic').attr('src', creativePic[0].picUrl);
            $('#onePhonePic').show();
            $('#onePhonePic').attr('src', creativePic[0].picUrl);
            $('#fileList2Pic').attr('src', creativePic[1].picUrl);
            $('#fileList3Pic').attr('src', creativePic[2].picUrl);
            $("#picUrl").val(creativePic[0].picUrl);
            $("#picUrl2").val(creativePic[1].picUrl);
            $("#picUrl3").val(creativePic[2].picUrl);

            $('#picUrlId').val(creativePic[0].id);
            $('#picUrl2Id').val(creativePic[1].id);
            $('#picUrl3Id').val(creativePic[2].id);
        }
        $("#threePic").show();
        $("#twoPic").hide();
        $("#onePic").hide();
        $("#PosShow").hide();
        $("#resourceShow").hide();
        $("#twoPhone").hide();
        $("#onePhone").show();
        $("#creative_Type").show();
    } else if (resourceType == 1 || resourceType == 2 || resourceType == 3 || resourceType == 7 || resourceType == 8) {
        if (true) {//修改
            $('#fileList21Pic').attr('src', creativePic[0].picUrl);
            $("#fileList21Pic").height(420);
            $("#picUrl21").val(creativePic[0].picUrl);
            $('#picUrl21Id').val(creativePic[0].id);
            if (resourceType == 1 || resourceType == 3) {
                $("#shuChaPing").hide();
                $("#shuQuanPing").show();//隐藏手机图片，以便再次选中显示上次
                $('#shuQuanPing').attr('src', creativePic[0].picUrl);
                if (resourceType == 3) {//资源位类型-手机开机画面隐藏链接
                    $('#ideaHref').hide();//隐藏创意链接
                }
            } else if (resourceType == 2 || resourceType == 7) {
                $("#shuQuanPing").hide();
                $("#shuChaPing").show();
                $('#shuChaPing').attr('src', creativePic[0].picUrl);
            } else if (resourceType == 8) {
                //显示储物柜
                $("#twoPhone").hide();
                $("#chuWuGui").show();
                $("#chuWuGuiQuanPing").show();
                $('#chuWuGuiQuanPing').attr('src', creativePic[0].picUrl);
                $('#ideaHref').hide();
            }
        } else {
            $("#fileList21").empty();//
            $("#picUrl21").val("");
            //$("#fileList22").empty();//注掉横屏
            //$("#picUrl22").val("");//注掉横屏
            $("#shuQuanPing").hide();
            $("#shuChaPing").hide();
            $("#myPhoneHeng").hide();
        }

        $("#cancelPic").hide();//注掉横屏上传功能
        $(".content_m_phone_b").hide();//注掉横屏 添加的
        $("#threePic").hide();//
        $("#onePic").hide();
        $("#twoPic").show();
        $("#PosShow").hide();//
        $("#resourceShow").hide();
        //显示智能储物柜8
        if (resourceType != 8) {
            $("#twoPhone").show();	//
        }
        $("#onePhone").hide();
        $("#twoHeng").hide();
        $("#twoShu").show();
        $("#creative_Type").show();

        //设置图片像素
        if (resourceType == 1 || resourceType == 3) {
            $("#fourPicShow").text('640*1136');
            $("#fourPic").val('640#1136');
        } else if (resourceType == 2 || resourceType == 7) {
            $("#fourPicShow").text('480*700');
            $("#fourPic").val('480#700');
        } else if (resourceType == 8) {
            $("#fourPicShow").text('1366 * 768')
            $("#twoPicDesc").hide().after("<label id='smart'>的png、jpg格式图片</label>")
            $("#fourPic").val('1366#768');
            $("#creative_Type").hide();
        }
    } else if (resourceType == 4) {
        if (true) {//修改
            $('#fileList33Pic').attr('src', creativePic[0].picUrl);
            $("#picUrl33").val(creativePic[0].picUrl);
            $('#picUrl33Id').val(creativePic[0].id);
            $("#mySize").text(creativePic[0].picWidth + "*" + creativePic[0].picHeight);
            $("#sixPic").val(creativePic[0].picWidth + "#" + creativePic[0].picHeight);
            if (resourceType == 5) {
                $("#shuGuDingWeiZhi").show();
                $('#shuGuDingWeiZhi').attr('src', creativePic[0].picUrl);
                $("#shuLunBo").hide();
            } else {
                $("#shuLunBo").show();
                $('#shuLunBo').attr('src', creativePic[0].picUrl);
                $("#shuGuDingWeiZhi").hide();
            }
        } else {
            $("#fileList33").empty();
            $("#picUrl33").val("");
            $(".module_c_info").hide();
            $("#res_Loc_id").val("");
            $("#shuLunBo").hide();
            $("#shuGuDingWeiZhi").hide();
        }
        $("#twoPic").hide();
        $("#onePic").show();
        $("#threePic").hide();
        $("#PosShow").hide();//
        $("#resourceShow").show();
        $("#twoPhone").hide();
        $("#onePhone").show();
        $("#creative_Type").show();
    } else if (resourceType == 5 || resourceType == 6) {
        if (true) {//修改
            $('#fileList21Pic').attr('src', creativePic[0].picUrl);
            $("#fileList21Pic").height(420);
            $('#fileList22Pic').attr('src', creativePic[1] == undefined ? "" : creativePic[1].picUrl);
            $("#picUrl21").val(creativePic[0].picUrl);
            $("#picUrl22").val(creativePic[1] == undefined ? "" : creativePic[1].picUrl);
            $('#picUrl21Id').val(creativePic[0].id);
            $('#picUrl22Id').val(creativePic[1] == undefined ? "" : creativePic[1].id);
            if (resourceType == 5) {//闲时
                $("#twoPadFree").show();
                $("#padShuFree").show();
                $("#padHengFree").hide();
                $('#freePadShu').attr('src', creativePic[0].picUrl);
                $('#freePadheng').attr('src', creativePic[1].picUrl);

                $("#shuQuanPing").show();
                $('#shuQuanPing').attr('src', creativePic[0].picUrl);
                $('#myPhoneHeng').attr('src', creativePic[1].picUrl);

                $('#ideaHref').hide();//隐藏创意链接
            } else {//忙时
                $("#twoPadBusy").show();
                $("#padShuBusy").show();
                $('#busyPadShu').attr('src', creativePic[0].picUrl);
                $('#fileList22Pic').attr('src', creativePic[0].picUrl);

                //隐藏600*900px的图片
                $("#twoPicFirstTxt").hide();
                $("#twoPicFirstPic").hide();

                $('#ideaHref').hide();//隐藏创意链接
            }
        } else if (resourceType == 10) {
            $("#not-h5-edit-area").hide();
            $("#div-feed").show();
        } else {
            $("#fileList21").empty();//
            $("#picUrl21").val();
            $("#fileList22").empty();
            $("#picUrl22").val();
            $("#shuChaPing").hide();
            $("#shuQuanPing").hide();//隐藏手机图片，以便再次选中显示上次
            $("#myPhoneHeng").hide();
        }

        //横屏上传功能展示
        $("#cancelPic").show();
        $(".content_m_phone_b").show();//注掉横屏 添加的

        //获取pos类型
        ctlPos('no');

        $("#threePic").hide();
        $("#onePic").hide();
        $("#twoPic").show();
        $("#PosShow").show();
        $("#resourceShow").hide();
        $("#twoPhone").show();
        $("#onePhone").hide();
        $("#twoHeng").hide();
        $("#twoShu").hide();
        $("#creative_Type").hide();
    } else if (resourceType == 10) {
        $("#div-feed").show();
        $("#feedType").val(creationDetail.contentType);
        $("#title").val(creationDetail.title);
        if(creationDetail.contentType == 2){
            $("#subTitle").val(creationDetail.subtitle);
            $("#divSubTitle").show();
        }else {
            $("#divSubTitle").hide();
        }
        $("#advertiser").val(creationDetail.advertiser);
        $("#threePic").hide();
        $("#right-model-area").hide();
        if (creationDetail.contentType == 1) {
            $("#feedSinglePicInfo").show();
            $('#feedSinglePicImg').attr('src', creativePic[0]==undefined ? '':creativePic[0].picUrl);
        } else if (creationDetail.contentType == 2) {
            $("#feedThreePicInfo").show();
            $('#feedThreePic1Img').attr('src', creativePic[0]==undefined ? '':creativePic[0].picUrl);
            $('#feedThreePic2Img').attr('src', creativePic[1]==undefined ? '':creativePic[1].picUrl);
            $('#feedThreePic3Img').attr('src', creativePic[2]==undefined ? '':creativePic[2].picUrl);
        }
    }
}

//云pos类型，忙时，闲时控制
function ctlPos(judge) {
    var posT = $('#posType').val();
    if (posT == 1) {//闲时
        $("#fourPicShow").text('600*900');
        $("#fourPic").val('600#900');
        $("#fivePicShow").text('1280*700');
        $("#fivePic").val('1280#700');
    } else {//忙时
        $("#fourPicShow").text('600*900');
        $("#fourPic").val('600#900');
        $("#fivePicShow").text('520*700');
        $("#fivePic").val('520#700');
    }
}

PFUNC.onClickEvent = function () {
    $('img').unbind().bind('click', function () {
        showBigPicList(this);
    });

    $('#canelPng').unbind().bind('click', function () {
        $("#bigPicList").attr("style", "display: none;");
    });
};

function showBigPic(ids) {
    $("#showPic").attr('src', ids.src);
    $("#bigPic").show();
}

//大图查看
function showBigPicList(e) {
    $("#showPic").attr('src', e.src);
    $("#bigPicList").attr("style", "disploy:block");
}
//关闭大图
function closePicList() {
    $("#bigPicList").removeAttr("style").addClass('style', 'disploy: block');
}


//页面初始化
$(document).ready(function () {
    PFUNC.initCreationDetail.call(this);
    PFUNC.returnList.call(this);
    PFUNC.onClickEvent.call(this);
});

//pad横竖屏切换
function controlPadPicShow(sign) {
    if (sign == 1) {
        $("#padHengFree").hide();
        $("#padShuFree").show();
    } else {
        $("#padHengFree").show();
        $("#padShuFree").hide();
    }
}

function getUrlParams(argu) {
    if (argu.indexOf('?') !== -1) {
        argu = argu.split('?')[1];
    }
    var str = argu.split('#')[0];
    var result = {};
    var temp = str.split('&');
    for (var i = 0; i < temp.length; i++) {
        var temp2 = temp[i].split('=');
        result[temp2[0]] = temp2[1];
    }
    return result;
}
