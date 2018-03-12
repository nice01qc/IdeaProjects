/**
 * DSP列表
 * created by zhanghua54
 * 2016.10.25
 */

;(function(window, document, $) {
    'use strict';

    var dspListUrl = '/adxDspManager/getDspList',
        updateTokenUrl = '/adxDspManager/updateDspToken',
        getDspInfoUrl = '/adxDspManager/getDspById',
        changeStatusUrl = '/adxDspManager/updateDspStatus';

    var dspListGrid = $('#dspListGrid');

    var actionBtns = {
        edit: '<button type="button" data-loading-text="稍候..." class="button_9 edit-btn">编辑</button>',
        updateToken: '<button type="button" data-loading-text="稍候..." class="button_9 update-token-btn">更新token</button>',
        updateTokenDisabled: '<button type="button" disabled class="button_9">更新token</button>',
        active: '<button type="button" data-loading-text="稍候..." class="button_9 active-btn">启用</button></div>',
        inactive: '<button type="button" data-loading-text="稍候..." class="button_9 inactive-btn">停用</button></div>'
    };

    var dspListCols = [{
        title: 'DSP名称',
        name: 'name'
    }, {
        title: 'DSP id',
        name: 'dspId'
    }, {
        title: 'token',
        name: 'token'
    }, {
        title: '接入方式',
        name: 'accessMode',
        render: function(v, item, i) {
            return v === '0' ? '向对方获取广告' : (v === '1' ? '实时竞价' : '');
        }
    }, {
        title: '协议价',
        name: 'cpm',
        render: function(v, item, i) {
            return ((typeof Number(v) === 'number' && v !== null) ? v : '-') + '/千次曝光';
        }
    }, {
        title: '事前审核',
        name: 'creationReview',
        render: function(v, item, i) {
            return v === 'Y' ? '是' : (v === 'N' ? '否' : '');
        }
    }, {
        title: '状态',
        name: 'status',
        render: function(v, item, i) {
            return v === 'Y' ? '启用' : (v === 'N' ? '停用' : '');
        }
    }, {
        title: '操作',
        render: operateHandler
    }];

    function operateHandler(v, item, i) {
        var actionHtml = '<div status="' + item.status + '" index="' + i + '" data-id="' + item.id + '">';
        actionHtml += actionBtns.edit;
        if (item.canUpdateToken === '1') {
            actionHtml += actionBtns.updateToken;
        } else {
            actionHtml += actionBtns.updateTokenDisabled;
        }
        if (item.status === 'Y') {
            actionHtml += actionBtns.inactive;
        } else if (item.status === 'N') {
            actionHtml += actionBtns.active;
        }
        actionHtml += '</div>';
        return actionHtml;
    }

    var dspGridComponent = dspListGrid.TableWithSearchComponent({
        url: dspListUrl,
        cols: dspListCols,
        items: 'result',
        paginationTarget: $('#dspListPagination'),
        searchBtn: $('#dspListSearchBtn'),
        searchParamHandler: searchHandler,
        successHandler: girdLoadSuccessHandler,
        refreshRowHandler: refreshRowSuccessHandler
    });

    function girdLoadSuccessHandler() {
        dspListGrid.find('.edit-btn').on('click', editHandler);
        dspListGrid.find('.update-token-btn').on('click', updateTokenHandler);
        dspListGrid.find('.active-btn').on('click', activeHandler);
        dspListGrid.find('.inactive-btn').on('click', inactiveHandler);
    }

    function refreshRowSuccessHandler(rowTr) {
        rowTr = $(rowTr);
        rowTr.find('.edit-btn').on('click', editHandler);
        rowTr.find('.update-token-btn').on('click', updateTokenHandler);
        rowTr.find('.active-btn').on('click', activeHandler);
        rowTr.find('.inactive-btn').on('click', inactiveHandler);
    }

    function activeHandler() {
        var _this = $(this);
        var $activatePopover = _this.popover({
            container: 'body',
            placement: 'top',
            trigger: 'focus',
            title: '<strong>更新状态</strong>',
            template: '<div class="popover alert-success fade" role="tooltip alert"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>',
            content: '<p>启用DSP?</p><div class="text-center"><button id="confirmActBtn" type="button" class="btn btn-success" style="margin-right: 10px;">立即启用</button><button id="cancelActBtn" type="button" class="btn btn-default">取消</button></div>',
            html: true
        });
        $activatePopover.popover('show');
        $('#confirmActBtn').on('click', function() {
            changeStatusHandler(_this, 'Y');
        });
        $('#cancelActBtn').on('click', function() {
            $activatePopover.popover('destroy');
        });
    }

    function inactiveHandler() {
        var _this = $(this);
        var $activatePopover = _this.popover({
            container: 'body',
            placement: 'top',
            trigger: 'focus',
            title: '<strong>更新状态</strong>',
            template: '<div class="popover alert-danger fade" role="tooltip alert"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>',
            content: '<p>停用DSP后对方将无法使用飞凡接口</p><div class="text-center"><button id="confirmActBtn" type="button" class="btn btn-danger" style="margin-right: 10px;">立即停用</button><button id="cancelActBtn" type="button" class="btn btn-default">取消</button></div>',
            html: true
        });
        $activatePopover.popover('show');
        $('#confirmActBtn').on('click', function() {
            changeStatusHandler(_this, 'N');
        });
        $('#cancelActBtn').on('click', function() {
            $activatePopover.popover('destroy');
        });
    }

    //编辑
    function editHandler() {
        var btn = $(this).button('loading');
        var dataId = $(this).parent().attr('data-id');
        window.location.href = './newDsp.html?id=' + dataId;
        btn.button('reset');
    }

    //更新token
    function updateTokenHandler() {
        var _this = $(this);
        var dataId = _this.parent().attr('data-id');
        var $activatePopover = _this.popover({
            container: 'body',
            placement: 'top',
            trigger: 'focus',
            title: '<strong>更新token</strong>',
            template: '<div class="popover alert-warning fade" role="tooltip alert"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>',
            content: '<p>更新token后需及时告知对方新token</p><div class="text-center"><button id="confirmActBtn" type="button" class="btn btn-warning" style="margin-right: 10px;">立即更新</button><button id="cancelActBtn" type="button" class="btn btn-default">取消</button></div>',
            html: true
        });
        $activatePopover.popover('show');
        $('#confirmActBtn').on('click', function() {
            actionHandler(_this, updateTokenUrl, {
                id: dataId,
                status: 1
            });
        });
        $('#cancelActBtn').on('click', function() {
            $activatePopover.popover('destroy');
        });
    }

    //启用 或 停用
    function changeStatusHandler(btnEle, status) {
        var dataId = btnEle.parent().attr('data-id');
        actionHandler(btnEle, changeStatusUrl, {
            id: dataId,
            status: status
        });
    }

    function actionHandler(btnEle, url, params) {
        var btn = btnEle.parent().find('button').not('[disabled]').button('loading');
        $.post(url, params, function(data) {
            if (data.status) {
                //更新数据列表
                $.get(getDspInfoUrl, { id: btnEle.parent().attr('data-id') }, function(dspIno) {
                    if (dspIno.status === 200) {
                        var rowIndex = btnEle.parent().attr('index');
                        dspGridComponent.refreshSingleRow(dspIno.result, rowIndex);
                    } else {
                        alert(dspIno.message);
                    }
                });
            }
            alert(data.message);
        }).complete(function() {
            btn.button('reset');
        });
    }

    //获取搜索参数
    function searchHandler() {
        return {
            dspName: $('#dspName-param').val(),
            accessMode: $('#accessMode-param').val(),
            status: $('#status-param').val()
        }
    }

}(window, document, $));