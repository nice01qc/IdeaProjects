package com.wanda.ffanad.crm.controllers;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import com.wanda.ffanad.crm.service.CrmResourcePriceService;
import com.wanda.ffanad.crm.service.CrmResourceService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.util.TextUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.wanda.ffanad.base.annotation.RolePermission;
import com.wanda.ffanad.base.dal.entities.ResourceInfoEntity;
import com.wanda.ffanad.common.utils.CurrencyUtils;
import com.wanda.ffanad.common.utils.EXCELUtils;
import com.wanda.ffanad.base.error.FfanadException;
import com.wanda.ffanad.core.common.FfanadStatus;
import com.wanda.ffanad.core.common.PaginationBo;
import com.wanda.ffanad.core.common.RestResult;
import com.wanda.ffanad.core.common.constant.SystemConstant;
import com.wanda.ffanad.core.domains.vo.req.ResourceMonopolyPriceReqVo;
import com.wanda.ffanad.core.domains.vo.req.ResourceMonopolyPriceUpdateReqVo;
import com.wanda.ffanad.core.domains.vo.req.ResourceRTBPriceReqVo;
import com.wanda.ffanad.core.domains.vo.req.ResourceReqVo;
import com.wanda.ffanad.core.domains.vo.req.ResourceStatisticsReqVo;
import com.wanda.ffanad.core.domains.vo.req.ResourceStatusReqVo;
import com.wanda.ffanad.core.domains.vo.res.ResourcePriceInfoResVo;
import com.wanda.ffanad.core.domains.vo.res.ResourceResVo;
import com.wanda.ffanad.core.domains.vo.res.StatisticsResult;
import com.wanda.ffanad.core.enums.PromoteTerminalEnum;
import com.wanda.ffanad.core.enums.ResPositionTypeEnum;
import com.wanda.ffanad.core.resp.dto.PageBean;
import com.wanda.ffanad.core.services.ResourcePriceService;
import com.wanda.ffanad.core.services.ResourceService;
import com.wanda.ffanad.core.services.ResourceStatisticsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 资源审核定价相关服务的控制器.
 */
@Api(tags = { "资源审核相关接口" })
@RestController
@RequestMapping("/resources")
@SessionAttributes("user")
public class ResourceController {

    private Logger                    logger = LoggerFactory.getLogger(ResourceController.class);

    /**
     * 自动注入{@link com.wanda.ffanad.core.services.ResourceService}的实例
     */
    @Autowired
    private ResourceService           resourceService;

    @Autowired
    private ResourcePriceService      resourcePriceService;

    @Autowired
    private ResourceStatisticsService resourceStatisticsService;

    @Autowired
    private CrmResourceService        crmResourceService;

    @Autowired
    private CrmResourcePriceService   crmResourcePriceService;

    @Value("${resource.statistics.api}")
    String                            resourceStatisticsURL;

    /**
     * 按页查询资源位的请求。 调用
     *
     * @param resReqVo 前台传入的查询条件
     * @param pageIndex 页码
     * @param pageSize 分页大小
     * @return 按页返回符合条件的资源位
     */
    @ApiOperation(value = "按页查询符合条件的资源")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    @ResponseBody
    public RestResult getResourcesByPage(@Validated @RequestBody ResourceReqVo resReqVo,
                                         @RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
                                         @RequestParam(value = "pageSize", defaultValue = "20") int pageSize) {
        if (resReqVo != null && resReqVo.getResTerminaltype() == PromoteTerminalEnum.BEACON.getValue()) {
            //如果是Beacon，在这个接口里面只返回beacon的资源位
            if (resReqVo.getResPositiontype() == null || resReqVo.getResPositiontype() == ResPositionTypeEnum.BEACON.getValue()) {
                resReqVo.setResPositiontype(ResPositionTypeEnum.BEACON.getValue());
            } else {
                PaginationBo<ResourceResVo> retObject = new PaginationBo<ResourceResVo>();
                retObject.setRows(new ArrayList<ResourceResVo>());
                return new RestResult(retObject);
            }
        }
        return new RestResult(crmResourceService.getResourcesPriceByPage(resReqVo, pageIndex, pageSize));
    }

    /**
     * 查询资源统计信息。 调用
     *
     * @param resReqVo 前台传入的查询条件
     * @return 返回资源统计信息
     */
    @ApiOperation(value = "查询资源统计信息")
    @RequestMapping(value = "/statistics", method = RequestMethod.POST)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    @ResponseBody
    public StatisticsResult getResourceStatistics(@Validated @RequestBody ResourceStatisticsReqVo resReqVo) {
        return resourceStatisticsService.getResourceStatisticsData(resReqVo, resourceStatisticsURL);
    }

    /**
     * 查询Resource相关code。 调用
     * {@link com.wanda.ffanad.core.services.ResourceService#getResouceCodeValue()}
     *
     * @return 返回Resource相关code
     */
    @ApiOperation(value = "查询资源位相关code")
    @RequestMapping(value = "/code", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    @ResponseBody
    public RestResult getResouceCodeValue() {
        return new RestResult(resourceService.getResouceCodeValue());
    }

    /**
     * 获取资源位详细信息请求。 调用
     * {@link com.wanda.ffanad.core.services.ResourceService#getResourceById(Integer)}
     *
     * @param resourceId 资源位id
     * @return 返回资源位详细信息
     */
    @ApiOperation(value = "查询指定资源信息")
    @RequestMapping(value = "/{resourceId}", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    @ResponseBody
    public RestResult getResourcesById(@PathVariable Integer resourceId, HttpSession session) {
        return new RestResult(resourceService.getResourceById(resourceId));
    }

    /**
     * 资源审核状态更新。调用｛
     * {@link com.wanda.ffanad.core.services.ResourceService#updateResourceAuditResult(ResourceStatusReqVo,HttpSession)}
     *
     * @param resAuditResult 前台传入的数据
     * @return 返回更新的资源位状态信息
     */
    @ApiOperation(value = "更新资源审核状态")
    @RequestMapping(value = "/auditresult", method = RequestMethod.POST)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    @ResponseBody
    public RestResult updateResourceAuditResult(@Validated @RequestBody ResourceStatusReqVo resAuditResult, HttpSession session) {
        return new RestResult(resourceService.updateResourceAuditResult(resAuditResult, session));
    }

    /**
     * 资源RTB价格更新。调用｛
     *
     * @param resRTBPrice 前台传入信息
     * @return 返回更新的资源位RTB价格信息
     */
    @ApiOperation(value = "更新资源位RTB价格")
    @RequestMapping(value = "/rtbprice", method = RequestMethod.POST)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    @ResponseBody
    public RestResult updateRTBPrice(@Validated @RequestBody ResourceRTBPriceReqVo resRTBPrice, HttpSession session) {
        return new RestResult(crmResourcePriceService.updateResourceRTBPrice(resRTBPrice, session));
    }

    /**
     * 更新资源位独占价格。调用｛
     *
     * @param resPriceVo 前台传入信息
     * @return 返回资源位价格信息
     */
    @ApiOperation(value = "更新资源独占价格")
    @RequestMapping(value = "/monopolyprice", method = RequestMethod.POST)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    @ResponseBody
    public RestResult updateMonopolyPrice(@Validated @RequestBody ResourceMonopolyPriceUpdateReqVo resPriceVo,
                                          HttpSession session) {
        return new RestResult(crmResourcePriceService.updateResourceMonopolyPrice(resPriceVo, session));
    }

    /**
     * 获取资源位独占价格(按城市、广场定价)。调用
     *
     * @param resPriceVo 前台传入信息
     * @return 返回资源位独占价格信息
     */
    @ApiOperation(value = "获取资源独占价格")
    @RequestMapping(value = "/monopolyprice", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    @ResponseBody
    public RestResult getMonopolyPrice(@Validated ResourceMonopolyPriceReqVo resPriceVo) {
        return new RestResult(crmResourcePriceService.getResourceMonopolyPrice(resPriceVo));
    }

    /**
     * 获取资源独占的定价(按门店定价)
     *
     * @param resId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "按页查询符合条件的资源")
    @RequestMapping(value = "/{resId}/price", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    @ResponseBody
    public RestResult getResourcesByPage(@PathVariable Integer resId,
                                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "20") int pageSize) {
        Page<ResourcePriceInfoResVo> page = crmResourcePriceService.queryStoreWithPriceInPage(resId, pageNum, pageSize);
        PageBean pageBean = new PageBean();
        pageBean.setData(page.getResult());
        pageBean.setPageNum(pageNum);
        pageBean.setPages(page.getPages());
        pageBean.setPageSize(page.getPageSize());
        pageBean.setTotal(page.getTotal());
        return new RestResult(pageBean);
    }

    @ApiOperation(value = "批量上传门店定价")
    @RequestMapping(value = "/{resId}/tprice", method = RequestMethod.POST)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult uploadStorePriceExcel(HttpServletRequest request, @PathVariable Integer resId,
                                            @RequestParam("storePriceFile") final MultipartFile storePriceFile) {
        ResourceInfoEntity ri = resourceService.getById(resId);
        if (ri == null) {
            return new RestResult(FfanadStatus.S_BAD_REQUEST, "资源不存在");
        }
        if (storePriceFile == null || (!storePriceFile.getOriginalFilename().toLowerCase().endsWith(".xls")
                && !storePriceFile.getOriginalFilename().toLowerCase().endsWith(".xlsx"))) {
            return new RestResult(FfanadStatus.S_BAD_REQUEST, "请正确上传后缀为\".xls/.xlsx\"的文件");
        }
        Integer currentAccountId = (Integer) request.getSession().getAttribute(SystemConstant.SESSION_ACCOUNT_ID);
        List<String[]> storePrices = EXCELUtils.parseFile(storePriceFile);

        if (!CollectionUtils.isEmpty(storePrices)) {
            try {
                List<ResourcePriceInfoResVo> lisStorePriceInfo = new ArrayList<ResourcePriceInfoResVo>();
                List<Long> lisStoreId = new ArrayList<Long>();
                //1、转换为对象：ResourcePriceInfoResVo
                //2、校验对象
                //3、保存到缓存
                //4、返回校验结果
                //index:6:门店Id；7：价格；8：是否可投
                StringBuilder sbError = new StringBuilder();
                for (int i = 0; i < storePrices.size(); i++) {
                    String[] storePrice = storePrices.get(i);
                    ResourcePriceInfoResVo rpiv = new ResourcePriceInfoResVo();

                    String storeIdStr = storePrice[6];
                    String priceStr = storePrice[7];
                    String throwable = storePrice[8];
                    if (TextUtils.isBlank(priceStr)) {
                        sbError.append(String.format("第%d行第%d列：%s\r\n", i + 1, 8, "门店定价不能为空,且必须为数字"));
                    } else {
                        try {
                            BigDecimal test = new BigDecimal(priceStr);
                        } catch (Exception e) {
                            sbError.append(String.format("第%d行第%d列：%s\r\n", i + 1, 8, "门店定价必须为数字"));
                        }
                    }
                    if (TextUtils.isBlank(throwable.trim())) {
                        sbError.append(String.format("第%d行第%d列：%s\r\n", i + 1, 9, "是否可投不能为空"));
                    } else if (!"N".equals(throwable.toUpperCase()) && !"Y".equals(throwable.trim().toUpperCase())) {
                        //判断格式是否正常
                        sbError.append(String.format("第%d行第%d列：%s\r\n", i + 1, 9, "只能填写Y/N"));
                    }

                    if (TextUtils.isBlank(storeIdStr) || !StringUtils.isNumeric(storeIdStr)) {
                        sbError.append(String.format("第%d行第%d列：%s\r\n", i + 1, 7, "门店ID不能为空,且必须位数字"));
                    }

                    if (sbError.length() == 0) {
                        if (lisStoreId.contains(Long.valueOf(storeIdStr.trim()))) {
                            //校验storeId是否有重复
                            return new RestResult(FfanadStatus.S_BAD_REQUEST, "门店ID有重复，请检查后再上传");
                            // sbError.append(String.format("第%d行第%d列：%s\r\n", i + 1, 7, " 门店ID有重复，请检查后再上传"));
                        } else {
                            rpiv.setRegionName(storePrice[0]);
                            rpiv.setCityName(storePrice[1]);
                            rpiv.setStoreBusinessTypeName(storePrice[3]);
                            rpiv.setPrice(CurrencyUtils.convert(Double.parseDouble(priceStr)));
                            rpiv.setStoreId(Long.valueOf(storeIdStr));
                            rpiv.setStoreName(storePrice[5]);
                            rpiv.setThrowable(throwable);
                            lisStorePriceInfo.add(rpiv);
                            lisStoreId.add(Long.valueOf(storeIdStr));
                        }
                    } else {
                        RestResult restResult = new RestResult(FfanadStatus.S_BAD_REQUEST, "部分数据格式不正确，请检查后重新上传");
                        restResult.setResult(sbError.toString());
                        return restResult;
                    }
                }

                //数据检查，保存到缓存数据库
                return crmResourcePriceService.buildResourcePriceInfoToCache(resId, currentAccountId, lisStorePriceInfo);
            } catch (Exception e) {
                logger.error("门店定价数据解析失败：" + e.getMessage(), e);
                return new RestResult(FfanadStatus.S_BAD_REQUEST, "部分数据格式不正确，请检查后重新上传");
            }
        } else {
            //空列表
            return new RestResult(FfanadStatus.S_BAD_REQUEST, "上传内容为空");
        }
    }

    @ApiOperation(value = "分页获取批量上传的结果")
    @RequestMapping(value = "/{resId}/tprice", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult getTPrice(HttpServletRequest request, @PathVariable Integer resId,
                                @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "20") int pageSize) {
        Integer currentAccountId = (Integer) request.getSession().getAttribute(SystemConstant.SESSION_ACCOUNT_ID);
        Page<ResourcePriceInfoResVo> page = crmResourcePriceService.getResourcePriceInfoFromCache(resId, currentAccountId,
                pageNum, pageSize);
        PageBean pageBean = new PageBean();
        pageBean.setData(page.getResult());
        pageBean.setPageNum(pageNum);
        pageBean.setPages(page.getPages());
        pageBean.setPageSize(page.getPageSize());
        pageBean.setTotal(page.getTotal());
        return new RestResult(FfanadStatus.S_OK, pageBean);
    }

    @ApiOperation(value = "保存批量上传结果")
    @RequestMapping(value = "/{resId}/price", method = RequestMethod.POST)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult saveTPrice(HttpServletRequest request, @PathVariable Integer resId, HttpSession session) {
        Integer currentAccountId = (Integer) request.getSession().getAttribute(SystemConstant.SESSION_ACCOUNT_ID);
        return crmResourcePriceService.saveResourcePriceInfoFromCache(resId, currentAccountId, session);
    }

    @ApiOperation(value = "导出某一屏所有的数据")
    @RequestMapping(value = "/{resId}/price/excel", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public void exportResPrice(@NotNull @PathVariable Integer resId, HttpServletResponse response) {
        //校验资源ID
        ResourceInfoEntity resInfo = resourceService.getById(resId);
        if (resInfo == null) {
            throw new FfanadException(HttpStatus.BAD_REQUEST.value(), "不存在的资源ID");
        }

        List<ResourcePriceInfoResVo> resPriceInfos = resourcePriceService.queryStoreWithPrice(resId);
        //组装excel标题栏
        List<Object> head = new ArrayList<Object>();
        head.add("地区");
        head.add("城市");
        head.add("城市ID");
        head.add("类别名称");
        head.add("类别ID");
        head.add("门店名称");
        head.add("门店ID");
        head.add("价格");
        head.add("是否可投(Y/N)");
        //文件数据
        List<List<Object>> dataList = new ArrayList<List<Object>>();
        for (ResourcePriceInfoResVo resPriceInfo : resPriceInfos) {
            List<Object> data = new ArrayList<Object>();
            data.add(resPriceInfo.getRegionName());
            data.add(resPriceInfo.getCityName());
            data.add(resPriceInfo.getCityId());
            data.add(resPriceInfo.getStoreBusinessTypeName());
            data.add(resPriceInfo.getStoreBusinessTypeId());
            data.add(resPriceInfo.getStoreName());
            data.add(resPriceInfo.getStoreId());
            data.add(resPriceInfo.getPriceStr());
            data.add(resPriceInfo.getThrowable());
            dataList.add(data);
        }
        //开始导出文件操作
        try {
            //｛资源位名称｝按门店定价{日期，例如20160701}.xls 
            String fileName = resInfo.getResName() + "按门店定价" + new DateTime().toString("yyyyMMdd");
            response.setHeader("Content-disposition",
                    "attachment; filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1") + ".xlsx");
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            EXCELUtils.export07Excel(head, dataList, response.getOutputStream());
            OutputStream os = response.getOutputStream();// 取得输出流      
            os.flush();
            os.close();
        } catch (Exception e) {
            logger.error("导出资源价格表失败：" + e.getMessage(), e);
        }
    }

    /**
     * 保存保留价
     *
     * @param resId 资源ID
     * @param reservePrice 保留价（单位元）
     * @param enableReservePrice 是否启用保留价（Y启用，N不启用）
     * @param response
     * @return
     */
    @ApiOperation(value = "保存保留价")
    @RequestMapping(value = "/{resId}/reserveprice", method = RequestMethod.POST)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult updateReservePrice(@NotNull @PathVariable Integer resId, String reservePrice, String enableReservePrice,
                                         HttpServletResponse response) {
        //校验资源ID
        ResourceInfoEntity resInfo = resourceService.getById(resId);
        if (resInfo == null)
            throw new FfanadException(HttpStatus.BAD_REQUEST.value(), "不存在的资源ID");
        //处理价格
        if (!NumberUtils.isNumber(reservePrice))
            throw new FfanadException(HttpStatus.BAD_REQUEST.value(), "reservePrice必需为数字且不能为空");
        if (StringUtils.isBlank(enableReservePrice))
            throw new FfanadException(HttpStatus.BAD_REQUEST.value(), "enableReservePrice不能为空");
        if (!StringUtils.equals("Y", enableReservePrice) && !StringUtils.equals("N", enableReservePrice))
            throw new FfanadException(HttpStatus.BAD_REQUEST.value(), "enableReservePrice只能是'Y'或'N");

        BigDecimal reservePriceDecimal = new BigDecimal(reservePrice);
        resourceService.updateReservePrice(resId, reservePriceDecimal, enableReservePrice);
        return new RestResult(FfanadStatus.S_OK, "成功");
    }
}
