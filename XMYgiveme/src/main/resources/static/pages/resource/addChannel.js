/**
 * 添加频道
 * created by zhanghua54
 * 2016.11.17
 */

;(function(window, document, $) {
    'use strict';

    var channelGridUrl = '/channel/page',
        addChannelUrl = '/channel/save',
        delChannelUrl = '/channel/';

    var channelGridEle = $('#channel-list-grid'),
        addChannelInput = $('#channel-name-param'),
        addChannelBtn = $('#add-channel-btn');

    var CHANNEL_GRID_COLS = [{
        title: '频道名称',
        name: 'name'
    }, {
        title: '操作',
        width: 80,
        render: function(v, item, i) {
            return '<button type="button" class="del-btn" channelid="' + item.id + '" data-loading-text="处理中...">删除</button>';
        }
    }];

    var channelGrid = channelGridEle.TableWithSearchComponent({
        url: channelGridUrl,
        cols: CHANNEL_GRID_COLS,
        items: 'result',
        showIndex: true,
        paginationTarget: $('#channel-list-pagination'),
        successHandler: gridLoadSuccessHandler
    });

    function gridLoadSuccessHandler() {
        channelGridEle.find('.del-btn').on('click', function() {
            var _this = $(this);
            var channelId = _this.attr('channelid');
            var $activatePopover = _this.popover({
                container: 'body',
                placement: 'top',
                trigger: 'focus',
                title: '<strong>删除频道</strong>',
                template: '<div class="popover alert-warning fade" role="tooltip alert"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>',
                content: '<p>确认要删除该频道吗？频道删除后，下属资源位仍存在，但频道为“未归类”。</p><div class="text-center"><button id="confirmActBtn" type="button" class="btn btn-danger" style="margin-right: 10px;">确认删除</button><button id="cancelActBtn" type="button" class="btn btn-default">取消</button></div>',
                html: true
            });
            $activatePopover.popover('show');
            $('#confirmActBtn').on('click', function() {
                var btn = _this.parent().find('button').not('[disabled]').button('loading');
                $.ajax({
                    url: delChannelUrl + channelId,
                    method: 'delete',
                    success: function(data) {
                        alert(data.message);
                        channelGrid.refreshGrid();
                    },
                    complete: function() {
                        btn.button('reset');
                    }
                });
            });
            $('#cancelActBtn').on('click', function() {
                $activatePopover.popover('destroy');
            });
        });
    }

    addChannelInput.on('input', function(e) {
        var currentValue = e.target.value;
        if (currentValue.length > 0) {
            addChannelBtn.removeAttr('disabled');
        } else {
            addChannelBtn.attr('disabled', 'disabled');
        }
    });

    addChannelBtn.on('click', function() {
        var _this = $(this);
        _this.button('loading');
        var channelName = addChannelInput.val();
        var param = JSON.stringify({
            name: channelName
        });

        $.ajax({
            url: addChannelUrl,
            method: 'post',
            data: param,
            contentType: 'application/json',
            success: function(data) {
                if (data.status === 200) {
                    channelGrid.refreshGrid();
                    addChannelInput.val('');
                    _this.button('reset');
                    setTimeout(function() {
                        addChannelBtn.attr('disabled', 'disabled');
                    }, 100);
                }
                alert(data.message);
            },
            error: function() {
                _this.button('reset');
            }
        });
    });

    $('#back-btn').on('click', function() {
        var _this = $(this);
        _this.button('loading');
        window.location.href = './channelManagement.html';
    });
}(window, document, $));