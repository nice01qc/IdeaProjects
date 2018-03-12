/**
 * 一个配合搜索按钮进行查询并展示分页数据的组件
 * created by zhanghua54
 * 2016.10.26
 */

;(function (window, document, $) {
    'use strict';

    var PAGE_SIZE = 20;
    var CURRENT_PAGE_NO = 1;
    var TOTAL_PAGE_NO = 1;
    var PAGE_ALL_DATA = {};
    var SELECTED_CHECK_DATA = [],
        SELECTED_CHECK_ROWS = [];

    var COL_NO = 0;

    function TableWithSearchComponent(element, options) {
        this.defaultOptions = {
            paginationTarget: '',       //分页组件的容器
            url: '',                    //数据请求的地址
            method: 'get',              //ajax请求类型，默认get
            params: {},                 //ajax参数
            cols: [],                   //表格要展示的列
            items: '',                  //ajax返回值中，数据在哪个字段中
            showTopBanner: false,       //是否在表头展示banner，默认不展示
            topBannerTemplate: '',      //banner模板
            paramJSON: false,           //参数是否是JSON格式
            showThead: true,            //是否显示表头，默认显示
            showCheckbox: false,        //是否显示复选框，默认不显示
            showIndex: false,           //是否显示“序号”列
            successHandler: '',         //数据加载成功的处理函数
            refreshRowHandler: '',      //刷新单行数据完成后的回调处理函数
            searchParamHandler: '',     //参数处理函数，必须是函数，可以动态生成搜索参数
            searchBtn: ''               //搜索按钮
        };
        options.target = element;
        this.options = options = $.extend(true, {}, this.defaultOptions, options);
        this.init(options);
    }

    TableWithSearchComponent.prototype = {
        constructor: TableWithSearchComponent,
        init: function (options) {
            var cols = options.cols;
            var tableHtml = '', theadHtml = '';
            options.target.addClass('table-container');
            if (options.target === '' || options.paginationTarget === '' || !cols || cols.length === 0) return;
            if (options.showTopBanner) {
                tableHtml += options.topBannerTemplate;
            }
            if (options.showThead) {
                theadHtml = this.initTableHead(options);
            }
            tableHtml += '<table border="1" cellspacing="0" class="table-ele">' + theadHtml + '<tbody></tbody></table>';
            options.target.html(tableHtml);
            this.initColNo(options);
            this.initEvent(options);
            this.getRemoteData(options);
        },
        initColNo: function (options) {
            COL_NO = options.cols.length;
            if (options.showCheckbox) {
                ++COL_NO;
            }
            if (options.showIndex) {
                ++COL_NO;
            }
        },
        initEvent: function (options) {
            var _this = this;
            if (options.searchBtn) {
                options.searchBtn.on('click', function () {
                    _this.getRemoteData(options, true);
                });
            }
        },
        getRemoteData: function (options, searchFlag) {
            var _this = this;
            if (!options.url) return;
            var cols = options.cols;
            options.target.find('tbody').html('<tr><td class="td-aa text-info" colspan="' + COL_NO + '">正在查询数据...</td></tr>');
            options.paginationTarget.empty();
            if (searchFlag) {
                CURRENT_PAGE_NO = 1;
            }
            if (typeof options.searchParamHandler === 'function') {
                if (!options.searchParamHandler()) return;
                options.params = options.searchParamHandler();
            }
            options.params.page = CURRENT_PAGE_NO;
            options.params.limit = PAGE_SIZE;

            $.ajax({
                url: options.url,
                method: options.method,
                contentType: options.paramJSON ? 'application/json' : 'application/x-www-form-urlencoded;charset=UTF-8',
                data: options.paramJSON ? JSON.stringify(options.params) : options.params,
                dataType: 'json',
                success: function (data) {
                    _this.initTableBody(data, options);
                    if (typeof options.successHandler === 'function') {
                        options.successHandler();
                    }
                },
                error: function () {
                    options.target.find('tbody').html('<tr><td class="td-aa text-danger" colspan="' + COL_NO + '">查询数据出错</td></tr>');
                }
            });
        },
        initTableHead: function (options) {
            var theadHtml = '<thead><tr class="tr-a">';
            if (options.showCheckbox) {
                theadHtml += '<th style="width: 80px;text-align: center">选择</th>';//TODO
            }
            if (options.showIndex) {
                theadHtml += '<th style="width: 80px;text-align: center">序号</th>';
            }
            $.each(options.cols, function (i, v) {
                var tdClass = v.name ? 'td-aa' : 'td-aa td-200';
                var style = 'style=';
                if (v.width) {
                    style += 'width:' + v.width + 'px;';
                }
                theadHtml += '<th class="' + tdClass + '"' + style + '>' + v.title + '</th>';
            });
            theadHtml += '</tr></thead>';
            return theadHtml;
        },
        initTableBody: function (data, options) {
            var _this = this;
            var cols = options.cols;
            if (data.status === 200) {
                var remoteData = data[options.items];
                if (remoteData && remoteData.list && remoteData.list.length > 0) {
                    PAGE_ALL_DATA = remoteData;
                    var tableBodyHtml = '';
                    TOTAL_PAGE_NO = (remoteData.totalCount % PAGE_SIZE === 0) ? remoteData.totalCount / PAGE_SIZE : Math.floor(remoteData.totalCount / PAGE_SIZE + 1);
                    /**
                     * 1. 生成表格数据
                     * 2. 生成分页控件
                     */
                    $.each(remoteData.list, function (i, v) {
                        tableBodyHtml += (i % 2 == 0 ? '<tr class="tr-b">' : '<tr class="tr-a">');
                        if (options.showCheckbox) {
                            tableBodyHtml += '<td style="width: 80px;"><input class="check-index-btn" type="checkbox" data-index="' + i + '" /></td>';
                        }
                        if (options.showIndex) {
                            tableBodyHtml += '<td style="width: 80px;">' + ((CURRENT_PAGE_NO - 1) * PAGE_SIZE + i + 1) + '</td>';
                        }
                        $.each(cols, function (ii, vv) {
                            var colVal = '',
                                style = 'style=',
                                tdClass = vv.name ? 'td-aa' : 'td-aa td-200';
                            if (vv.render) {
                                (function (iii) {
                                    colVal = vv.render(v[vv.name], v, iii);
                                })(i);
                            } else {
                                colVal = (vv.name && v[vv.name]) ? v[vv.name] : '';
                            }
                            if (vv.width) {
                                style += 'width:' + vv.width + 'px;';
                            }
                            tableBodyHtml += '<td class="' + tdClass + '"' + style + '>' + colVal + '</td>';
                        });
                        tableBodyHtml += '</tr>';
                    });
                    options.target.find('tbody').html(tableBodyHtml);
                    _this.initCheckboxEvent(options);
                    _this.initPagination(options);
                } else {
                    options.target.find('tbody').html('<tr><td class="td-aa text-info" colspan="' + COL_NO + '">未查询到数据</td></tr>');
                }
                options.target.find('.total-count-no').text(remoteData.totalCount);
            } else {
                options.target.find('tbody').html('<tr><td class="td-aa text-danger" colspan="' + COL_NO + '">' + data.message + '</td></tr>');
            }
        },
        initCheckboxEvent: function (options) {
            if (options.showCheckbox) {
                options.target.find('.check-index-btn').on('click', function (e) {
                    var checkIndex = e.target.getAttribute('data-index');
                    if (e.target.checked) {
                        SELECTED_CHECK_DATA.push(PAGE_ALL_DATA.list[checkIndex]);
                        SELECTED_CHECK_ROWS.push(options.target.find('tbody tr')[checkIndex]);
                    } else {
                        SELECTED_CHECK_DATA.splice(SELECTED_CHECK_DATA.indexOf(PAGE_ALL_DATA.list[checkIndex]), 1);
                        SELECTED_CHECK_ROWS.splice(SELECTED_CHECK_ROWS.indexOf(options.target.find('tbody tr')[checkIndex]), 1);
                        if (options.target.find('#select-all-checkbox')) {
                            options.target.find('#select-all-checkbox')[0].checked = false;
                        }
                    }
                });
            }
            if (options.showTopBanner) {
                options.target.find('#select-all-checkbox').on('click', function (e) {
                    var checkboxes = options.target.find('.check-index-btn').toArray();
                    var allCheck = e.target.checked;
                    SELECTED_CHECK_DATA = [];
                    SELECTED_CHECK_ROWS = [];
                    $.each(checkboxes, function (i, v) {
                        v.checked = allCheck;
                        if (allCheck) {
                            var checkIndex = v.getAttribute('data-index');
                            SELECTED_CHECK_DATA.push(PAGE_ALL_DATA.list[checkIndex]);
                            SELECTED_CHECK_ROWS.push(options.target.find('tbody tr')[checkIndex]);
                        }
                    });
                });
            }

        },
        initPaginationEvent: function (options) {
            var _this = this;
            var pagination = options.paginationTarget;
            pagination.find('.pre-page-btn').not('[disabled]').on('click', function () {
                --CURRENT_PAGE_NO;
                _this.getRemoteData(options);
            });
            pagination.find('.next-page-btn').not('[disabled]').on('click', function () {
                ++CURRENT_PAGE_NO;
                _this.getRemoteData(options);
            });
            //判断是否大于最大页或小于最小页
            //若用户输入非整数、大于最大页或小于最小页的数字，则不查询，修改数值为当前页码
            pagination.find('.jump-page-btn').on('click', function () {
                var inputEle = $(this).parent().find('.jump-page-no');
                var inputNo = Number(inputEle.val());
                if (typeof inputNo === 'number' && inputNo % 1 === 0 && inputNo > 0 && inputNo <= TOTAL_PAGE_NO) {
                    CURRENT_PAGE_NO = inputNo;
                    _this.getRemoteData(options);
                } else {
                    inputEle.val('');
                }
            });
        },
        initPagination: function (options) {
            var _this = this;
            //大于1页时，才显示分页控件
            if (!options.paginationTarget) return;
            if (TOTAL_PAGE_NO > 1) {
                var paginationHtml = '<div class="table-pagination-container"><div class="pre-page-btn" ' + (CURRENT_PAGE_NO === 1 ? 'disabled' : '') + '><i class="glyphicon glyphicon-triangle-left"></i></div>' +
                    '<div class="page-no-info">' + CURRENT_PAGE_NO + ' / ' + TOTAL_PAGE_NO + '</div>' +
                    '<div class="next-page-btn" ' + (CURRENT_PAGE_NO === TOTAL_PAGE_NO ? 'disabled' : '') + '><i class="glyphicon glyphicon-triangle-right"></i></div>' +
                    '<div style="display: inline-block;"><input type="number" placeholder="页码" class="jump-page-no" value="' + CURRENT_PAGE_NO + '" />' +
                    '<button class="jump-page-btn">跳转</button></div></div>';
                options.paginationTarget.html(paginationHtml);
                _this.initPaginationEvent(options);
            }
        },
        refreshSingleRow: function (single, rowIndex) {
            var options = this.options;
            var cols = options.cols;
            var targetTr = options.target.find('tbody tr')[rowIndex];
            var targetTds = $(targetTr).find('td');
            $.each(cols, function (i, v) {
                if (v.render) {
                    $(targetTds[i]).html(v.render(single[v.name], single, rowIndex));
                } else {
                    $(targetTds[i]).html(single[v.name]);
                }
            });
            //给当前行添加按钮的处理函数
            if (typeof options.refreshRowHandler === 'function') {
                options.refreshRowHandler(targetTr);
            }
        },
        getSelectedData: function (handler) {
            var _this = this;
            var selectedData = {
                data: SELECTED_CHECK_DATA,
                rows: SELECTED_CHECK_ROWS
            };
            if (typeof handler === 'function') {
                handler(selectedData);
            }
            _this.resetSelectedRows(_this.options);
        },
        resetSelectedRows: function (options) {
            SELECTED_CHECK_DATA = [];
            SELECTED_CHECK_ROWS = [];
            var checks = options.target.find('.check-index-btn').toArray();
            $.each(checks, function (i, v) {
                v.checked = false;
            });
            if (options.target.find('#select-all-checkbox').length > 0) {
                options.target.find('#select-all-checkbox')[0].checked = false;
            }
        },
        refreshGrid: function () {
            var _this = this;
            CURRENT_PAGE_NO = 1;
            _this.getRemoteData(this.options);
        }
    };

    $.fn.TableWithSearchComponent = function (options) {
        return new TableWithSearchComponent(this, options);
    };
    $.fn.TableWithSearchComponent.Constructor = TableWithSearchComponent;
}(window, document, window.jQuery));
