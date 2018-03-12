package com.wanda.ffanad.crm.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;

import com.wanda.ffanad.base.annotation.RolePermission;
import com.wanda.ffanad.common.utils.CurrencyUtils;
import com.wanda.ffanad.common.utils.JsonUtils;
import com.wanda.ffanad.core.common.constant.SystemConstant;
import com.wanda.ffanad.core.domains.App;
import com.wanda.ffanad.core.domains.SysTblColCode;
import com.wanda.ffanad.core.enums.AppStatusEnum;
import com.wanda.ffanad.core.remote.resp.AppStatisticsRemoteRespDetail;
import com.wanda.ffanad.core.services.AppService;
import com.wanda.ffanad.core.services.system.SysTblColCodeService;
import com.wanda.ffanad.crm.common.ResDashboardRestResponse;
import com.wanda.ffanad.crm.integration.common.ApiUrlBuilder;

@RestController
@RequestMapping("/res/dashboard")
@SessionAttributes("user")
public class ResDashBoardController {

	private Logger logger = LoggerFactory.getLogger(ResDashBoardController.class);
	
	@Autowired
	private SysTblColCodeService sysTblColCodeService;
	
	@Autowired
	private AppService appService;
	
	@Autowired
	private ApiUrlBuilder ApiUrlBuilder;
	
	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * 根据高级用户查询二级下拉列表
	 * 
	 * @author 韩鹏 2016年6月7日
	 * @param 用户角色
	 * @return 二级资源对应终端
	 */
	@RequestMapping(path = "/getSecondTitle", method = RequestMethod.GET)
	@RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
	public Object getSecondTitle(String pKey) {
		List<SysTblColCode> codes =sysTblColCodeService.getParams(pKey);
		return JsonUtils.toJSON(codes);
	}
	
	/**
	 * 查询所有联盟用户自建的App
	 * 
	 * @author 韩鹏 2016年6月7日
	 * @param accountId
	 * @return List<App>
	 */
	@RequestMapping(path = "/getAllUnionApps", method = RequestMethod.GET)
	@RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
	public List<App> getAllUnionApps(HttpServletRequest request) {
		Byte[] statusList = new Byte[] { AppStatusEnum.APP_NORMAL.getValue().byteValue() };
		List<App> apps = appService.getAllUnionApps(Arrays.asList(statusList));
		return apps;
	}
	
	/**
	 * 查询后台资源数据
	 * 
	 * @author 韩鹏 2016年6月7日
	 * @param sspUserType 用户类型
	 * @param terminal 投放终端
	 * @param appType  投放应用
	 * @param appKey 自建应用的appKey 
	 * @param beginDate 查询开始日期
	 * @param endDate 查询结束日期
	 * @throws ParseException 
	 */
	@RequestMapping(path = "/statistics", method = RequestMethod.GET)
	@RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
	public ResponseEntity<ResDashboardRestResponse> resStatistics(String sspUserType, String terminal,
			String appType, String appKey, String beginDate, String endDate){
		ResponseEntity<ResDashboardRestResponse> respData = null;
		try {
			String accountId="";
			//日期转换成毫秒
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String beginDateTime ="";
			String endDateTime="";
			
			if(!StringUtils.isBlank(beginDate)){
				beginDateTime = String.valueOf(new DateTime(sdf.parse(beginDate).getTime()).getMillis());
			}
			if(!StringUtils.isBlank(endDate)){
				endDateTime = String.valueOf(new DateTime(sdf.parse(endDate).getTime()).getMillis());
			}
			
			String url = ApiUrlBuilder.buildResdashboardAdURL(sspUserType, accountId, terminal, appType, appKey,
					beginDateTime, endDateTime);
			respData = restTemplate.getForEntity(url,ResDashboardRestResponse.class);
			List<AppStatisticsRemoteRespDetail> statisticData = respData.getBody().getData();

			if (respData.getBody().getStatus() != 200) {
				logger.error("调数据中心接口失败 error={}", respData.getBody().getMessage());
			}

			if (CollectionUtils.isEmpty(statisticData)) {
				logger.error("查无数据 warn={}", respData.getBody().getMessage());
				respData.getBody().setStatus(300);
			}
			for (AppStatisticsRemoteRespDetail detail : statisticData) {
                detail.setAmountStr(CurrencyUtils.format(detail.getAmount() == null ? 0 : detail.getAmount()));
            }
		}catch(ParseException e){
			logger.error("日期转换异常 error={}", e);
		} 
		catch (Exception e) {
			logger.error("异常失败 error={}", e);
		}
		return respData;
	}
}
