var rid = getQueryString("rid");

$(function() {
    $(".fakeloader").fakeLoader({
        bgColor : "#3498db",
        spinner : "spinner1"
    });

    $(".fancybox").fancybox();
    $.get("/finance/invoice/recharge/" + rid, function(data) {
        if (data.result != null) {
           
            if (data.result.invoiceCategory == 2) {
                $("#companyAddress").parent().parent().hide();
                $("#companyTel").parent().parent().hide();
                $("#bankName").parent().parent().hide();
                $("#bankAccount").parent().parent().hide();
                $("#bankAccountsPermitsId").hide();
                $("#generalTaxpayerQualificationId").hide();
            }
            $("#invoiceCategory").text(data.result.invoiceCategoryName);
            $("#invoiceContent").text(data.result.invoiceContentName)
            $("#rechargeInfo_proceedsAmount").text(data.result.invoiceAmountStr)
            $("#consigneeName").text(data.result.consigneeName);
            $("#consigneeTel").text(data.result.consigneeTel);
            $("#consigneeAddress").text(data.result.consigneeAddress);
            $("#expressCompanyName").text(data.result.expressCompanyName ? data.result.expressCompanyName : ''  + "　");
            $("#expressNumber").text(data.result.expressNumber ? data.result.expressNumber : '' + "　");
            $("#companyName").text(data.result.companyName);
            $("#taxpayerNumber").text(data.result.taxpayerNumber);
            $("#companyAddress").text(data.result.companyAddress);
            $("#companyTel").text(data.result.companyTel);
            $("#bankName").text(data.result.bankName);
            $("#bankAccount").text(data.result.bankAccount);
            $("#businessLicenseId").attr("src", data.result.businessLicenseId)
            $("#organizationCodeCertificateId").attr("src",
                    data.result.organizationCodeCertificateId)
            $("#taxRegistrationCertificateId").attr("src",
                    data.result.taxRegistrationCertificateId)
            $("#bankAccountsPermitsId").attr("src", data.result.bankAccountsPermitsId)
            $("#generalTaxpayerQualificationId").attr("src",
                    data.result.generalTaxpayerQualificationId)
            $("#invoiceId").val(data.result.invoiceId);
            $('#rechargeId').val(data.result.rechargeInfoId);
        } else {
            alert("未查询到数据")
        }
        $(".file-item").fancybox();

        $(".fakeloader").fakeLoader().hide();
    }).done(function(){
    	 $.get("/recharge/" + $('#rechargeId').val(), function(data) {
    		 if (data.result.status == 3) {
                 $("#confirmInvoice").hide();
             } else if(data.result.status>3) {   
                 $("#confirmInvoice").hide();
                 $("#expressInvoice").hide(); 
             } else {
                 $("#expressInvoice").hide(); 
             }
    	 });
    });
    
    $("#confirmInvoice").click(function() {
        
        $.ajax({
            url : "/finance/invoice",
            data : {
               invoiceId:$("#invoiceId").val()
            },
            type : 'PUT',
            dataType : 'json',
            error : function() {
                alert('执行错误！');
            },
            success : function(data) {
                if (data.status == 200) {
                    $("#confirmInvoice").hide();
                    $("#expressInvoice").show();
                    $("#confirmInvoice").attr("disabled", true).text("开票中")
                } else {
                    alert('执行错误！');
                }
            }
        });
    });

    $("#expressInvoice").click(function() {
        $('#expressModal').modal('toggle')
        $('#expressCompanyName').val("").css("border-color", "").next("span").empty();
        $('#expressNumber').val("").css("border-color", "").next("span").empty();
    });

});

function completeExpress(event) {
    var expressCompanyName = $("#expressCompanyNameWin").val();
    var expressNumber = $("#expressNumberWin").val();
    var invoiceId = $("#invoiceId").val()
    if (expressCompanyName.length < 1) {
        $("#expressCompanyNameWin").css("border-color", "red").next("span").text("快递公司不可为空");
        return;
    } else {
        $("#expressCompanyNameWin").css("border-color", "").next("span").empty();
    }
    if (expressNumber.length < 1) {
        $("#expressNumberWin").css("border-color", "red").next("span").text("快递单号不可为空");
        return;
    } else {
        $("#expressNumberWin").css("border-color", "").next("span").empty();
    }
    $(event.target).prop("disabled", true).html("提交中...");
    $.ajax({
        url : "/finance/invoice/express",
        method : 'POST',
        dataType : 'json',
        data : {
            invoiceId : invoiceId,
            expressNumber : expressNumber,
            expressCompanyName : expressCompanyName,
            rechargeInfoId : rid,
            rechargeStatus : 4
        },
        success : function(result) {
            if (result.status == 200) {
                $('#expressModal').modal('toggle');
                $(event.target).prop("disabled", false).html("确定")
                window.location.reload();
            } else {
                alert("修改失败")
                $('#expressModal').modal('toggle');
                $(event.target).prop("disabled", false).html("确定")
            }
        }
    });
}