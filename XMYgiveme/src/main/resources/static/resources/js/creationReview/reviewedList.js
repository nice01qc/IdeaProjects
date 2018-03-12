/**
 * 待审核页面
 * created by zhanghua54
 * 2016.10.27
 */

;(function(window, document, $) {
    'use strict';

    var reviewListUrl = '/adxReviewManager/getAlreadyReviewList',
        getAllDspUrl = '/adxReviewManager/getAllDsp',
        reviewUrl = '/adxReviewManager/operateReview';

    var passedText = '<p><span class="glyphicon glyphicon-ok"></span>审核通过</p>',
        retreatedText = '<p><span class="glyphicon glyphicon-remove"></span>审核退回</p>';

    var retreatReasonForm = $('#retreat-reason-form');

    var retreatReason = {
        '广告主体问题': ['行业不能投放', '品类不能投放', '品牌不能投放', '单品不能投放'],
        '图片问题': ['过于裸露', '图片模糊', '无法正常打开'],
        '着陆页问题': ['过于裸露', '无法正常打开'],
        '其他问题': ['其他']
    };

    var topBanner = '<div class="table-top-banner">' +
        '<div class="check-all"><input type="checkbox" id="select-all-checkbox"> 全选 </div>' +
        '<button class="btn btn-default" id="batch-pass-btn" type="button"><span class="glyphicon glyphicon-ok"></span>批量通过</button>' +
        '<button class="btn btn-default" id="batch-retreat-btn" type="button"><span class="glyphicon glyphicon-remove"></span>批量退回</button>' +
        '<div class="pull-right">查询到<span class="total-count-no">--</span>条已审核</div></div>';
    
    var reviewListCols = [{
        title: '图片展示',
        name: 'name',
        render: function(v, item, i) {
            return '<div style="height: 100%;">' +
                '<p style="font-weight: bold;font-size: 16px;text-align: left;margin-top: 10px;">' + item.dspName +
                '<span style="margin: 0 10px;font-weight: normal;font-size: 14px;">' + item.updateTime + '更新</span></p>' +
                '<img width="350px" style="margin-bottom: 15px;" src="' + item.copyUrl + '"></div>';
        }
    }, {
        title: '链接展示',
        name: 'links',
        render: function(v, item, i) {
            var photoUrlHtml = '';
            var landingPageUrlHtml = '';
            var showDetectUrlHtml = '';
            var clickDetectUrlHtml = '';
            if (item.url !== '' && item.url !== null) {
                photoUrlHtml += '<p style="font-weight: bold;font-size: 16px;text-align: left;">图片链接：</p><p class="url-ele">' + item.url + '</p>';
            }
            if (item.landingPageUrl !== '' && item.landingPageUrl !== null) {
                landingPageUrlHtml += '<p style="font-weight: bold;font-size: 16px;text-align: left;">点击跳转链接：</p><p class="url-ele">' + item.landingPageUrl + '</p>';
            }
            if (item.showDetectUrlList && item.showDetectUrlList.length > 0) {
                showDetectUrlHtml += '<p style="font-weight: bold;font-size: 16px;text-align: left;">曝光监测链接：</p>';
                $.each(item.showDetectUrlList, function(i, v) {
                    showDetectUrlHtml += '<p class="url-ele">' + v + '</p>';
                });
            }
            if (item.clickDetectUrlList && item.clickDetectUrlList.length > 0) {
                clickDetectUrlHtml += '<p style="font-weight: bold;font-size: 16px;text-align: left;">点击监测链接：</p>';
                $.each(item.clickDetectUrlList, function(i, v) {
                    clickDetectUrlHtml += '<p class="url-ele">' + v + '</p>';
                });
            }
            return '<div style="width: 400px;height: 100%;padding: 5px 0;">' +
                photoUrlHtml + landingPageUrlHtml + showDetectUrlHtml + clickDetectUrlHtml +
                '</div>';
        }
    }, {
        title: '操作',
        render: function(v, item, i) {
            var actionHtml = '';
            if (item.reviewStatus == 1) {
                actionHtml += passedText;
            } else {
                actionHtml += '<p><button class="btn btn-default btn-block pass-btn" data-loading-text="稍候..." data-id="' + item.id + '" type="button"><span class="glyphicon glyphicon-ok"></span>通过</button></p>';
            }
            if (item.reviewStatus == 2) {
                actionHtml += retreatedText;
            } else {
                actionHtml += '<p><button class="btn btn-default btn-block retreat-btn" data-loading-text="稍候..." data-id="' + item.id + '" type="button"><span class="glyphicon glyphicon-remove"></span>退回</button></p>';
            }
            return actionHtml;
        }
    }];

    var reviewListComponent = $('#creationListGrid').TableWithSearchComponent({
        url: reviewListUrl,
        cols: reviewListCols,
        items: 'result',
        paginationTarget: $('#creationListPagination'),
        searchBtn: $('#creationListSearchBtn'),
        showTopBanner: true,
        topBannerTemplate: topBanner,
        showThead: false,
        showCheckbox: true,
        searchParamHandler: searchHandler,
        successHandler: girdLoadSuccessHandler
    });

    $('#batch-pass-btn').click(function() {
        var _this = $(this);
        reviewListComponent.getSelectedData(function(selectedData) {
            if (selectedData.data.length > 0) {
                batchReview(_this, { status: 1 }, selectedData, 'pass');
            } else {
                alert('请勾选想要批量通过的数据');
            }
        });
    });
    $('#batch-retreat-btn').click(function() {
        var _this = $(this);
        reviewListComponent.getSelectedData(function(selectedData) {
            if (selectedData.data.length > 0) {
                $('#retreat-reason').modal('show');
                $('#submit-reason-btn').on('click', function() {
                    var _submitBtn = $(this);
                    submitRetreatReason(function(params) {
                        batchReview(_this, params, selectedData, 'retreat', _submitBtn);
                        $('#retreat-reason').modal('hide');
                        $('#submit-reason-btn').off('click');
                    });
                });
            } else {
                alert('请勾选想要批量退回的数据');
            }
        });
    });

    $('.date-range-picker').daterangepicker();

    $('.date-select-check').on('change', function(e) {
        var _this = $(this);
        var value = _this.val();
        var targetInput = _this.parent().find('.date-range-picker');
        if (value === 'd') {
            targetInput.removeClass('hide');
        } else {
            targetInput.addClass('hide');
        }
        targetInput.val('');
    });

    $.get(getAllDspUrl, function(data) {
        if (data.status === 200) {
            if (data.result && data.result.length > 0) {
                var options = '<option value="">全部</option>';
                $.each(data.result, function(i, v) {
                    options += '<option value="' + v + '">' + v + '</option>';
                });
                $('#dsp-select').html(options);
            }
        }
    });

    //获取搜索参数
    function searchHandler() {
        var reviewStatus = $('#review-status-select').val();
        var hasReviewTime = $('#review-time-select').val() === '' ? '0' : '1';
        var reviewTimeStr = $('#review-time-input').val();
        var reviewTimeStart = '', reviewTimeEnd = '';
        if (reviewTimeStr.indexOf(' - ') !== -1) {
            reviewTimeStr = reviewTimeStr.split(' - ');
            reviewTimeStart = reviewTimeStr[0];
            reviewTimeEnd = reviewTimeStr[1];
        }
        var hasUpdateTime = $('#update-time-select').val() === '' ? '0' : '1';
        var updateTimeStr = $('#update-time-input').val();
        var updateTimeStart = '', updateTimeEnd = '';
        if (updateTimeStr.indexOf(' - ') !== -1) {
            updateTimeStr = updateTimeStr.split(' - ');
            updateTimeStart = updateTimeStr[0];
            updateTimeEnd = updateTimeStr[1];
        }
        var hasThrowTime = $('#throw-time-select').val() === '' ? '0' : '1';
        var throwTimeStr = $('#throw-time-input').val();
        var throwTimeStart = '', throwTimeEnd = '';
        if (throwTimeStr.indexOf(' - ') !== -1) {
            throwTimeStr = throwTimeStr.split(' - ');
            throwTimeStart = throwTimeStr[0];
            throwTimeEnd = throwTimeStr[1];
        }
        var dspName = $('#dsp-select').val();
        var url = $('#url-input').val();
        var jumpUrl = $('#jump-url').val();

        return {
            dspName: dspName,
            reviewStatus: reviewStatus,
            hasReviewTime: hasReviewTime,
            reviewTimeStart: reviewTimeStart,
            reviewTimeEnd: reviewTimeEnd,
            hasUpdateTime: hasUpdateTime,
            updateTimeStart: updateTimeStart,
            updateTimeEnd: updateTimeEnd,
            hasThrowTime: hasThrowTime,
            throwTimeStart: throwTimeStart,
            throwTimeEnd: throwTimeEnd,
            url: url,
            landingPageUrl: jumpUrl
        }
    }

    //列表数据加载成功的回调函数
    function girdLoadSuccessHandler() {
        $('.pass-btn').on('click', passSingleHandler);
        $('.retreat-btn').on('click', retreatSingleHandler);
        //完整链接 按钮
        var urlEles = $('#creationListGrid').find('.url-ele').toArray();
        $.each(urlEles, function(i, v) {
            var clientHeight = v.clientHeight;
            if (clientHeight > 20) {
                v.style.paddingRight = '85px';
                v.style.height = '20px';
                var viewAllBtn = '<button type="button" class="btn btn-default view-all-url-btn"><span class="glyphicon glyphicon-link text-info"></span>完整链接</button>';
                $(v).append(viewAllBtn);
            }
        });
        var viewAllUrlBtns = $('#creationListGrid').find('.view-all-url-btn');
        viewAllUrlBtns.on('click', function() {
            var _this = $(this);
            var urlTarget = _this.parent();
            urlTarget.toggleClass('view-all');
            if  (urlTarget[0].classList.contains('view-all')) {
                _this.parent()[0].style.height = 'auto';
                _this.html('<span class="glyphicon glyphicon-link text-info"></span>关闭链接');
            } else {
                _this.parent()[0].style.height = '20px';
                _this.html('<span class="glyphicon glyphicon-link text-info"></span>完整链接');
            }
        });
    }

    function passSingleHandler() {
        var _this = $(this);
        var thisId = _this.attr('data-id');
        reviewAction(_this, {
            idString: [thisId].join(','),
            status: 1
        }, function() {
            if (_this.parent().parent().find('.retreat-btn').length === 0) {
                _this.parent().parent().find('p:last-child').html('<button class="btn btn-default btn-block retreat-btn" data-loading-text="稍候..." data-id="' + thisId + '" type="button"><span class="glyphicon glyphicon-remove"></span>退回</button>');
                _this.parent().parent().find('.retreat-btn').on('click', retreatSingleHandler);
            }
            _this.parent().html(passedText);
        });
    }

    function retreatSingleHandler() {
        var _this = $(this);
        var thisId = $(this).attr('data-id');
        $('#retreat-reason').modal('show');
        $('#submit-reason-btn').on('click', function() {
            var _submitBtn = $(this);
            submitRetreatReason(function(params) {
                _submitBtn.button('loading');
                params.idString = thisId;
                reviewAction(_this, params, function() {
                    if (_this.parent().parent().find('.pass-btn').length === 0) {
                        _this.parent().parent().find('p:first-child').html('<button class="btn btn-default btn-block pass-btn" data-loading-text="稍候..." data-id="' + thisId + '" type="button"><span class="glyphicon glyphicon-ok"></span>通过</button>');
                        _this.parent().parent().find('.pass-btn').on('click', passSingleHandler);
                    }
                    _this.parent().html(retreatedText);
                    $('#retreat-reason').modal('hide');
                    $('#submit-reason-btn').off('click');
                }, _submitBtn);
            });
        });
    }

    function batchReview(btnEle, params, selectedData, passOrRetreat, submitBtn) {
        var idList = [];
        $.each(selectedData.data, function(i, v) {
            idList.push(v.id);
        });
        params.idString = idList.join(',');
        reviewAction(btnEle, params, function() {
            $.each(selectedData.rows, function(i, v) {
                var _v = passOrRetreat === 'pass' ? $(v).find('.pass-btn') : $(v).find('.retreat-btn');
                var actionTd = _v.parent().parent();
                var thisId = _v.attr('data-id');
                if (passOrRetreat === 'pass') {
                    if (actionTd.find('.retreat-btn').length === 0) {
                        actionTd.find('p:last-child').html('<button class="btn btn-default btn-block retreat-btn" data-loading-text="稍候..." data-id="' + thisId + '" type="button"><span class="glyphicon glyphicon-remove"></span>退回</button>');
                        actionTd.find('.retreat-btn').on('click', retreatSingleHandler);
                    }
                    _v.parent().html(passedText);
                } else {
                    if (actionTd.find('.pass-btn').length === 0) {
                        actionTd.find('p:first-child').html('<button class="btn btn-default btn-block pass-btn" data-loading-text="稍候..." data-id="' + thisId + '" type="button"><span class="glyphicon glyphicon-ok"></span>通过</button>');
                        actionTd.find('.pass-btn').on('click', passSingleHandler);
                    }
                    _v.parent().html(retreatedText);
                }
            });
        }, submitBtn, selectedData.rows);
    }

    function submitRetreatReason(handler) {
        retreatReasonForm.find('select').trigger('blur');
        if (retreatReasonForm.find('.has-error').length > 0) {
            return false;
        }
        var questionClass = $('#question-class').val();
        var question = $('#question-detail').val();
        var noAdoptMsg = $('#no-adopt-msg').val();
        handler({
            status: 2,
            questionClass: questionClass,
            noAdoptMsg: noAdoptMsg,
            question: question
        });
    }

    function reviewAction(_this, params, successHandler, submitBtn, batchRows) {
        var btn;
        if (batchRows) {
            btn = batchRows.map(function(v) {
                return $(v).find('button[data-loading-text]');
            });
            $.each(btn, function(i, v) {
                v.button('loading');
            });
        } else {
            btn = _this.parent().parent().find('button').button('loading');
        }
        var thisId = _this.attr('data-id');
        $.post(reviewUrl, params, function(data) {
            if (data.status === 200) {
                if (typeof successHandler === 'function') successHandler();
            }
            if (data.message) alert(data.message);
        }, 'json').complete(function() {
            if (batchRows) {
                $.each(btn, function(i, v) {
                    v.button('reset');
                });
            } else {
                btn.button('reset');
            }
            if (submitBtn) submitBtn.button('reset');
        });
    }

    $('#question-class').on('change', function(e) {
        var value = e.target.value;
        var reasons = retreatReason[value];
        var reasonOptions = '';
        if (reasons && reasons.length) {
            $.each(reasons, function(i, v) {
                reasonOptions += '<option value="' + v + '">' + v + '</option>';
            });
        } else {
            reasonOptions = '<option value="">请选择问题分类</option>';
        }
        $('#question-detail').html(reasonOptions).trigger('change');
    });
    //用户在输入或粘贴时，会提示用户还有多少可以输入，超出长度的部分不会显示在输入框中
    $('#no-adopt-msg').on('paste input', function(e) {
        var _this = $(this);
        var maxLength = Number(_this.attr('maxLength'));
        var valueLength = _this.val().length;
        var leftLength = valueLength > maxLength ? 0 : maxLength - valueLength;
        _this.parent().find('.input-tip').text(leftLength);
    });

    //校验下拉框必选
    retreatReasonForm.find('select[required=required]').on('change blur', function(e) {
        var target = e.target,
            targetVlue = target.value,
            targetClass = target.classList;
        var tipEle = $(target).parent().parent().find('.tip');
        if (targetVlue === '') {
            targetClass.add('has-error');
            tipEle.removeClass('hide');
        } else {
            targetClass.remove('has-error');
            tipEle.addClass('hide');
        }
    });

    $('#retreat-reason').on('hidden.bs.modal', function() {
        retreatReasonForm[0].reset();
        $('#question-detail').html('<option value="">请选择问题分类</option>');
        var maxLength = $('#no-adopt-msg').attr('maxLength');
        retreatReasonForm.find('.input-tip').text(maxLength);
        retreatReasonForm.find('select').removeClass('has-error');
        retreatReasonForm.find('.tip').addClass('hide');
    });

}(window, document, window.jQuery));
