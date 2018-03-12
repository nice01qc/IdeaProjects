/**
 * 飞凡广告前端工具类
 * 
 * @author Yao
 */
var ffanadUtils = (function() {

	/**
	 * 显示loading
	 */
	function showLoading() {
		var loadingDiv = $('body .ffanad-loading');
		if (loadingDiv.length > 0) {
			loadingDiv.show();
		} else {
			var loadingDivHtml = [];
			loadingDivHtml.push('<div class="ffanad-loading">');
			loadingDivHtml.push('<div class="loader">');
			loadingDivHtml.push('<div class="loader-inner line-spin-fade-loader">');
			loadingDivHtml.push('<div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div>');
			loadingDivHtml.push('</div>');
			loadingDivHtml.push('</div>');
			loadingDivHtml.push('</div>');
			$('body').append(loadingDivHtml.join(' '));
			$('body .ffanad-loading').show();
		}
	}
	
	/**
	 * 隐藏loading
	 */
	function hideLoading() {
		var loadingDiv = $('body .ffanad-loading');
		if (loadingDiv.length > 0) {
			loadingDiv.hide();
		}
	}
	
	/**
	 * 显示提示框
	 */
	function showTips(tipsMsg) {
		var tipsModal = $("#ffanadTipsModal");
		if(tipsModal.length > 0) {
			tipsModal.find('.modal-body p').text(tipsMsg);
		} else {
			var modalHtml = [];
			modalHtml.push('<div id="ffanadTipsModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">');
			modalHtml.push('<div class="modal-dialog">');
			modalHtml.push('<div class="modal-content">');
			modalHtml.push('<div class="modal-header">');
			modalHtml.push('<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>');
			modalHtml.push('<h4 class="modal-title">提示</h4>');
			modalHtml.push('</div>');
			modalHtml.push('<div class="modal-body"><p>' + tipsMsg + '</p></div>');
			modalHtml.push('</div>');
			$('body').append(modalHtml.join(' '));
		}
		$('#ffanadTipsModal').modal();
	}
	
	/**
	 * 在date基础上往后推plusDays天
	 */
	function plusDays(date, plusDays) {
		var milliseconds = date.getTime();
		milliseconds = milliseconds + plusDays * 24 * 60 * 60 * 1000;
		return new Date(milliseconds);
	}
	
	/**
	 * 根据名称获取url上的查询参数
	 */
	function getQueryParameters(name){
	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	     var r = window.location.search.substr(1).match(reg);
	     if(r!=null)return  unescape(r[2]); return null;
	}

	$(function() {
		//window.alert = showTips;
	}());

	return {
		showLoading : showLoading,
		hideLoading : hideLoading,
		showTips : showTips,
		plusDays : plusDays,
		getQueryParameters : getQueryParameters
	}
})();