/**
 * 添加DSP
 * created by zhanghua54
 * 2016.10.24
 */

;(function(window, document, $) {
    'use strict';

    var dspForm = $('#dsp-form');

    var locationSearch = window.location.search;
    var urlSearchParams = parseSearch2Obj(locationSearch);

    var addDspUrl = '/adxDspManager/addDsp',
        getDspInfoUrl = '/adxDspManager/getDspById',
        updateDspUrl = '/adxDspManager/modifyDspInfo';

    if (urlSearchParams.id) {
        //编辑非新增
        $('#dsp-title').text('编辑DSP');
        $('#submit-btn').text('立即更新');
        $('title').text('编辑DSP');
        getDspInfo();
    } else {
        //新增
        $('#dsp-title').text('新增DSP');
        $('#submit-btn').text('开通');
        $('title').text('新建DSP');
    }

    function getDspInfo() {
        $.get(getDspInfoUrl, { id: urlSearchParams.id }, function(data) {
            console.log(data);
            if (data.status === 200) {
                loadData2Form(data.result);
            } else {
                alert(data.message);
            }
        });
    }

    function loadData2Form(result) {
        for (var attr in result) {
            if (!result.hasOwnProperty(attr) && typeof result[attr] === 'function')
                continue;
            var input = dspForm.find('input[name=' + attr + ']'),
                inputType = input.attr('type'),
                img = dspForm.find('img[name=' + attr + ']')[0],
                select = dspForm.find('select[name=' + attr + ']')[0];
            if (inputType !== 'checkbox' && inputType !== 'radio') {
                input.val(result[attr]);
            } else {
                var values = result[attr].split(',');
                for (var i = 0; i < values.length; i++) {
                    input.each(function(j , v) {
                        var value = $(v).val();
                        if (value === values[i]) {
                            $(v).attr('checked', 'checked');
                        }
                    });
                }
            }
            if (select) {
                var options = select.querySelectorAll('option');
                $.each(options, function(index, value) {
                    var optionVal = value.value;
                    if (optionVal == result[attr]) {
                        value.selected = true;
                    }
                });
            }
        }
    }

    //对输入框进行输入校验，验证输入的合法性
    dspForm.find('input').on('keyup blur', function(e) {
        var inputEle = e.target,
            inputClass = inputEle.classList,
            inputValue = inputEle.value;
        var pattern = inputEle.getAttribute('data-pattern');
        var tipEle = $(inputEle).parent().parent().find('.tip');
        if (!pattern) {
            inputClass.remove('has-error');
            tipEle.addClass('hide');
            return false;
        }
        var regExp = new RegExp(pattern);
        var textFlag = regExp.test(inputValue);
        if (textFlag) {
            inputClass.remove('has-error');
        } else {
            inputClass.add('has-error');
        }

        var errorInputs = $(inputEle).parent().find('.has-error');
        if (textFlag && errorInputs.length === 0) {
            tipEle.addClass('hide');
        } else {
            tipEle.removeClass('hide');
        }
    });
    //校验下拉框必选
    dspForm.find('select[required=required]').on('change blur', function(e) {
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

    //提交dsp信息
    $('#submit-btn').on('click', function() {
        var btn = $(this);
        var formParams = validateForm();
        var submitUrl = urlSearchParams.id ? updateDspUrl : addDspUrl;
        if (formParams) {
            btn.attr('disabled');
            $.post(submitUrl, formParams, function(data) {
                alert(data.message);
                if (data.status === 200) {
                    localStorage.setItem("crmClickedMenuId", 44);
                    window.location.href = '/pages/dsp/dspList.html';
                }
            }, 'json')
                .always(function() {
                    btn.removeAttr('disabled');
                });
        }
    });

    //校验输入项
    function validateForm() {
        dspForm.find('input').trigger('keyup');
        dspForm.find('select[required=required]').trigger('change');
        if (dspForm.find('.has-error').length === 0) {
            var params = {
                name: dspForm.find('input[name=name]').val(),
                accessMode: dspForm.find('select[name=accessMode]').val(),
                appkey: dspForm.find('input[name=appkey]').val(),
                appsecret: dspForm.find('input[name=appsecret]').val(),
                cpm: dspForm.find('input[name=cpm]').val(),
                feedbackUrl: dspForm.find('input[name=feedbackUrl]').val(),
                creationReview: dspForm.find('select[name=creationReview]').val()
            };
            if (urlSearchParams.id) {
                params.id = urlSearchParams.id;
            }
            return params;
        }
    }

    function parseSearch2Obj(str) {
        var params = {};
        if (str && str.indexOf('?') !== -1) {
            var paramStr = str.substr(str.indexOf('?') + 1);
            var paramStrs = paramStr.split('&');
            $.each(paramStrs, function(i, v) {
                var param = v.split('=');
                params[param[0]] = param[1];
            });
        }
        return params;
    }

}(window, document, $));