var SAFE_LINK_CHECK_KEY = "safe_link_check";

$(document).ready(function() {
	$("#editBtn").click(function() {
		$("#display-bar").hide();
		$("#edit-bar").show();
		$("#display-view").hide();
		$("#edit-view").show();
	});

	$("#saveBtn").click(function() {
		$.ajax({
			url : "/adxSetting/saveConfigs",
			type : 'POST',
			data: $("#adxSettingForm").serialize(),
			dataType : "json",
			success : function(data) {
				if(data.status == 200) {
					alert("adx设置保存成功!");
					location.reload();
				} else {
					alert("保存adx设置失败：" + data.message);
				}
			},
			error : function(xhr, textStatus) {
				alert('保存adx设置失败！');
			}
		});
	});

	$("#cancelBtn").click(function() {
		document.getElementById("adxSettingForm").reset();
		$("#display-bar").show();
		$("#edit-bar").hide();
		$("#display-view").show();
		$("#edit-view").hide();
	});

	$.ajax({
		url : "/adxSetting/getConfigs",
		type : 'GET',
		dataType : "json",
		success : function(data) {
			if(data.status == 200) {
				if(data.result[SAFE_LINK_CHECK_KEY] != null) {
					var safeLinkCheckValue = data.result[SAFE_LINK_CHECK_KEY].value;
					$("#safeLinkCheckValue").val($("#safeLinkCheck").find("option[value='" + safeLinkCheckValue + "']").text());
					$("#safeLinkCheck").find("option[value='" + safeLinkCheckValue + "']").attr("selected", "selected");
				} else {
					$("#safeLinkCheckValue").val("未设置");
				}
			}
		},
		error : function(xhr, textStatus) {
			alert('获取adx设置失败！');
		}
	});
});