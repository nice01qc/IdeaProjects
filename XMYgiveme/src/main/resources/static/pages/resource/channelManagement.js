/**
 * 频道管理
 * created by zhanghua54
 * 2016.11.16
 */

;(function (window, document, $) {
    'use strict';

    var channelGridUrl = '/resourceChannel/page',
        channelListUrl = '/channel',
        changeChannelUrl = '/resourceChannel/save';

    var searchForm = $('#channel-search-form'),
        channelGrid = $('#channel-list-grid');

    var ALL_CHANNEL_LIST = [];

    var channelGridCols = [{
        title: '资源ID',
        name: 'resId',
        width: 100
    }, {
        title: '资源名称',
        name: 'resName'
    }, {
        title: '频道名称',
        name: 'channelName',
        render: function (v, item, i) {
            if (item.channelId === 0) {
                v = '未归类';
            }
            return '<div class="channelNameArea" channelid="' + item.channelId + '" channelname="' + v + '">' + v + '</div>';
        }
    }, {
        title: '操作',
        width: 80,
        render: function (v, item, i) {
            return '<button type="button" class="action-btn" channelid="' + item.channelId + '" resid="' + item.resId + '" data-loading-text="处理中...">修改</button>';
        }
    }];

    $.get(channelListUrl, function (data) {
        //TODO 初始化所有频道select标签
        if (data.status === 200) {
            ALL_CHANNEL_LIST = data.result;
            //添加一个 channelId 为0，名称为“未归类”的选项
            ALL_CHANNEL_LIST.push({
                id: 0,
                name: '未归类'
            });
            $('#selected-channel-param').append(createChannelOptions());
        }
    });

    $('#add-channel-btn').on('click', function () {
        $(this).button('loading');
        window.location.href = './addChannel.html';
    });

    channelGrid.TableWithSearchComponent({
        url: channelGridUrl,
        cols: channelGridCols,
        method: "POST",
        paramJSON: true,
        items: 'result',
        paginationTarget: $('#channel-list-pagination'),
        searchBtn: $('#channel-search-btn'),
        searchParamHandler: searchHandler,
        successHandler: gridLoadSuccessHandler
    });

    function searchHandler() {
        return paramStr2Obj(decodeURIComponent(searchForm.serialize()), true);
    }

    function gridLoadSuccessHandler() {
        channelGrid.find('.action-btn').on('click', function () {
            var _this = $(this);
            var targetChannelContainer = _this.parent().parent().find('.channelNameArea');
            var channelId = targetChannelContainer.attr('channelid');
            var channelName = targetChannelContainer.attr('channelname');
            var resId = _this.attr('resid');
            //TODO 需获取所有频道列表
            console.log(111);
            if (!_this.data('editing')) {
                _this.text('确定');
                _this.addClass('btn-success');
                _this.data('editing', true);
                //用所有频道数据，来初始化一个select标签，并将当前值设为当前状态的值
                targetChannelContainer.html(createChannelSelect(channelId));
            } else {
                _this.removeClass('btn-success');
                var selectedChannelId = targetChannelContainer.find('select').val();
                _this.text('修改');
                var selectedChannelName = targetChannelContainer.find('select option:selected').text();
                if (channelId === selectedChannelId) {
                    targetChannelContainer.html(selectedChannelName);
                    _this.data('editing', false);
                    return;
                }
                _this.button('loading');
                $.post(changeChannelUrl, {
                    channelId: selectedChannelId,
                    resId: resId
                }, function (data) {
                    //成功后，需要修改'.channelNameArea'标签上的channelid属性值
                    if (data.status === 200) {
                        targetChannelContainer.html(selectedChannelName);
                        targetChannelContainer.attr('channelid', selectedChannelId);
                        targetChannelContainer.attr('channelname', selectedChannelName);
                    }
                    alert(data.message);
                }).error(function (xhr) {
                    var response = xhr.responseJSON;
                    targetChannelContainer.html(channelName);
                    targetChannelContainer.attr('channelid', channelId);
                    alert(response.message);
                }).complete(function () {
                    //恢复按钮的初始状态
                    _this.button('reset');
                    _this.data('editing', false);
                });
            }
        });
    }

    function createChannelSelect(channelId) {
        var selectHtml = '<select class="form-control">';
        if (ALL_CHANNEL_LIST.length > 0) {
            selectHtml += createChannelOptions(channelId);
        }
        selectHtml += '</select>';
        return selectHtml;
    }

    function createChannelOptions(channelId) {
        var optionHtml = '';
        $.each(ALL_CHANNEL_LIST, function (i, v) {
            console.log(v);
            //判断当前值是否是已选择
            if (channelId == v.id) {
                optionHtml += '<option value="' + v.id + '" selected="selected">' + v.name + '</option>';
            } else {
                optionHtml += '<option value="' + v.id + '">' + v.name + '</option>';
            }
        });
        return optionHtml;
    }

    function paramStr2Obj(argu) {
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

}(window, document, $));
