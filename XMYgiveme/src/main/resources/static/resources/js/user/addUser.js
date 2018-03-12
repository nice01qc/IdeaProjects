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
	}, {
		name : "广告客户",
		value : 2
	}]
} , {
    name : "DMP",
    value : 4,
    roles : [ {
        name : "一般用户",
        value : 1
    }]
}]

$(function() {

	$.each(sysAndRole, function(i, n) {
		$("#usertype").append(
				"<option value='" + n.value + "'>" + n.name + "</option>");
	});
	$.each(sysAndRole[0].roles, function(j, m) {
		$("#role").append(
				"<option value='" + m.value + "'>" + m.name + "</option>");
	});

	$("#usertype").change(
			function() {
				var tval = $(this).val();
				if (tval == 1) {
					document.getElementById("sdemail").style.display = "none";
					document.getElementById("pshare").style.display = "none";
					document.getElementById("company").style.display = "none";
					$("#crmemail").show();
					document.getElementById("btdsp").style.display = "none";
					document.getElementById("btlm").style.display = "none";
					document.getElementById("btgj").style.display = "none";
					document.getElementById("btdmp").style.display = "none";
					$("#btcrm").show();
				}
				if (tval == 2) {
					document.getElementById("crmemail").style.display = "none";
					$("#pshare").show();
					$("#company").show();
					$("#sdemail").show();
					document.getElementById("btcrm").style.display = "none";
					document.getElementById("btdsp").style.display = "none";
					document.getElementById("btgj").style.display = "none";
					document.getElementById("btdmp").style.display = "none";
					$("#btlm").show();

				}
				if (tval == 3) {
					document.getElementById("crmemail").style.display = "none";
					document.getElementById("pshare").style.display = "none";
					$("#sdemail").show();
					$("#company").show();
					document.getElementById("btcrm").style.display = "none";
					document.getElementById("btlm").style.display = "none";
					document.getElementById("btgj").style.display = "none";
					document.getElementById("btdmp").style.display = "none";
					$("#btdsp").show();
				}
				if (tval == 4) {
					document.getElementById("sdemail").style.display = "none";
					document.getElementById("pshare").style.display = "none";
					document.getElementById("company").style.display = "none";
					$("#crmemail").show();
					document.getElementById("btdsp").style.display = "none";
					document.getElementById("btlm").style.display = "none";
					document.getElementById("btgj").style.display = "none";
					document.getElementById("btcrm").style.display = "none";
					$("#btdmp").show();
				}
				for (var i = 0; i < sysAndRole.length; i++) {
					if (sysAndRole[i].value == $(this).val()) {
						$("#role").empty();
						$.each(sysAndRole[i].roles, function(j, m) {
							$("#role").append(
									"<option value='" + m.value + "'>" + m.name
											+ "</option>");
						});
					}
				}
				cancel();
			});
	
	
	$("#role").change(function() {
		var rval = $(this).val();
    	var tval = $("#usertype").val();
    	if(tval == 2){
    		if(rval==2){
        		document.getElementById("sdemail").style.display="none";
            	document.getElementById("pshare").style.display="none";
            	$("#crmemail").show();
            	document.getElementById("btdsp").style.display="none";
            	document.getElementById("btlm").style.display="none";
            	$("#btgj").show();
        	}
        	if(rval == 1){
        		document.getElementById("crmemail").style.display="none";
        		$("#sdemail").show();
        		$("#pshare").show();
        		document.getElementById("btcrm").style.display="none";
        		document.getElementById("btdsp").style.display="none";
        		document.getElementById("btgj").style.display="none";
        		$("#btlm").show();
        		
        	}
    	}
    	cancel();
	});



});

