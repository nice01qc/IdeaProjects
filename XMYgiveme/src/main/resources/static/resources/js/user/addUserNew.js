var sysAndRole = [ {
    name : "CRM",
    value : 1,
    roles : [ {
        name : "管理员",
        value : 1
    }, {
        name : "运营人员",
        value : 2
    }, {
        name : "财务人员",
        value : 3
    } ]
}, {
    name : "SSP",
    value : 2,
    roles : [ {
        name : "联盟用户",
        value : 1
    }, {
        name : "高级用户",
        value : 2
    } ]
}, {
    name : "DSP",
    value : 3,
    roles : [ {
        name : "一般用户",
        value : 1
    } ]
} ]

$(function() {

    $.each(sysAndRole, function(i, n) {
        $("#userType").append("<option value='" + n.value + "'>" + n.name + "</option>");
    });
    $.each(sysAndRole[0].roles, function(j, m) {
        $("#userRoles").append("<option value='" + m.value + "'>" + m.name + "</option>");
    });

    $("#userType").change(function() {
        var userTypeVal = $(this).val();
        if (userTypeVal == 1) {
            $("#wandaEmailLabel").removeClass("hidden").addClass("show");
            $("#emailLabel").removeClass("show").addClass("hidden");
            $("#profitShareDiv").removeClass("show").addClass("hidden");
            $("#companyDiv").removeClass("show").addClass("hidden");
        }
        if (userTypeVal == 2) {
            $("#wandaEmailLabel").removeClass("show").addClass("hidden");
            $("#emailLabel").removeClass("hidden").addClass("show");
            $("#profitShareDiv").removeClass("hidden").addClass("show");
            $("#companyDiv").removeClass("hidden").addClass("show");
        }
        if (userTypeVal == 3) {
            $("#wandaEmailLabel").removeClass("show").addClass("hidden");
            $("#emailLabel").removeClass("hidden").addClass("show");
            $("#profitShareDiv").removeClass("show").addClass("hidden");
            $("#companyDiv").removeClass("hidden").addClass("show");
        }
        for (var i = 0; i < sysAndRole.length; i++) {
            if (sysAndRole[i].value == $(this).val()) {
                $("#userRoles").empty();
                $.each(sysAndRole[i].roles, function(j, m) {
                    $("#userRoles").append("<option value='" + m.value + "'>" + m.name + "</option>");
                });
            }
        }
    });

    $("#userRoles").change(function() {
        var userRolesVal = $(this).val();
        var userTypeVal =  $("#userType").val();
        if (userTypeVal == 2) {
            if (userRolesVal == 2) {
                $("#profitShareDiv").removeClass("show").addClass("hidden");
            }
            if (userRolesVal == 1) {
                $("#profitShareDiv").removeClass("hidden").addClass("show");
            }
        }
    });

    $("#").blur(function() {

    });

});
