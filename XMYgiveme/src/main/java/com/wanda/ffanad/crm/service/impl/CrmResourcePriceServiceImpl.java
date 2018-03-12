package com.wanda.ffanad.crm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wanda.ffanad.base.constants.ResPositionType;
import com.wanda.ffanad.base.dal.entities.ResourceInfoEntity;
import com.wanda.ffanad.base.dal.entities.ResourceInfoEntityExample;
import com.wanda.ffanad.base.dal.entities.ResourcePriceInfoEntity;
import com.wanda.ffanad.base.dal.entities.ResourcePriceInfoEntityExample;
import com.wanda.ffanad.base.dal.mappers.ResourceInfoEntityMapper;
import com.wanda.ffanad.base.dal.mappers.ResourcePriceInfoEntityMapper;
import com.wanda.ffanad.base.error.FfanadException;
import com.wanda.ffanad.common.redis.RedisUtil;
import com.wanda.ffanad.common.utils.CurrencyUtils;
import com.wanda.ffanad.core.common.FfanadStatus;
import com.wanda.ffanad.core.common.RestResult;
import com.wanda.ffanad.core.common.constant.SystemConstant;
import com.wanda.ffanad.core.common.redis.RedisCacheConstants;
import com.wanda.ffanad.core.constants.OperationLogOperation;
import com.wanda.ffanad.core.constants.OperationLogPage;
import com.wanda.ffanad.core.constants.ResAreaOrient;
import com.wanda.ffanad.core.constants.ResBidType;
import com.wanda.ffanad.core.domains.OperationLog;
import com.wanda.ffanad.core.domains.ResourceRegion;
import com.wanda.ffanad.core.domains.ResourceRegionExample;
import com.wanda.ffanad.core.domains.vo.req.ResourceMonopolyPriceReqVo;
import com.wanda.ffanad.core.domains.vo.req.ResourceMonopolyPriceUpdateReqVo;
import com.wanda.ffanad.core.domains.vo.req.ResourceRTBPriceReqVo;
import com.wanda.ffanad.core.domains.vo.res.MonopolyPriceInfo;
import com.wanda.ffanad.core.domains.vo.res.ResourceMonopolyPriceResVo;
import com.wanda.ffanad.core.domains.vo.res.ResourcePriceInfoResVo;
import com.wanda.ffanad.core.domains.vo.res.ResourceRTBPriceResVo;
import com.wanda.ffanad.core.enums.PromoteResBidType;
import com.wanda.ffanad.core.enums.RegionTypeEnum;
import com.wanda.ffanad.core.mappers.ResourceRegionMapper;
import com.wanda.ffanad.core.mappers.ext.ResourceInfoMapperExt;
import com.wanda.ffanad.core.mappers.ext.ResourcePriceInfoMapperExt;
import com.wanda.ffanad.core.services.OperationLogService;
import com.wanda.ffanad.core.services.api.RegionService;
import com.wanda.ffanad.core.services.impl.ResourcePriceServiceImpl;
import com.wanda.ffanad.core.vo.PlazaVo;
import com.wanda.ffanad.core.vo.RegionVo;
import com.wanda.ffanad.core.vo.StoreVo;
import com.wanda.ffanad.crm.service.CrmResourcePriceService;
import com.wanda.ffanad.crm.service.CrmResourceService;

/**
 * Created by liujie136 on 17/1/22.
 */
@Service
public class CrmResourcePriceServiceImpl implements CrmResourcePriceService {
    private Logger                        logger       = LoggerFactory.getLogger(ResourcePriceServiceImpl.class);

    public static final Long              defaultPrice = 1000000000L;

    /**
     * 自动转配的Mapper.
     */
    @Autowired
    private ResourceInfoEntityMapper      resourceInfoMapper;

    @Autowired
    private ResourceInfoMapperExt         resourceInfoMapperExt;

    @Autowired
    private ResourcePriceInfoEntityMapper resPriceInfoMapper;

    @Autowired
    private ResourcePriceInfoMapperExt    resPriceInfoMapperExt;

    @Autowired
    private ResourceRegionMapper          resourceRegionMapper;

    @Autowired
    private RegionService                 regionService;

    @Autowired
    private CrmResourceService            crmResourceService;

    @Autowired
    private OperationLogService           operationLogService;

    @Override
    public ResourceMonopolyPriceResVo getResourceMonopolyPrice(ResourceMonopolyPriceReqVo resMonopolyPrice) {
        if (logger.isDebugEnabled()) {
            logger.debug("Received get monopoly price with req:" + objectToString(resMonopolyPrice));
        }
        ResourceMonopolyPriceResVo resVo = new ResourceMonopolyPriceResVo();

        ResourceInfoEntityExample resourceInfoEntityExample = new ResourceInfoEntityExample();
        ResourceInfoEntityExample.Criteria criteria = resourceInfoEntityExample.createCriteria()
                .andResBidtypeEqualTo(PromoteResBidType.PMP.getValue().byteValue());
        if (resMonopolyPrice.getResId() != null) {
            criteria.andResIdEqualTo(resMonopolyPrice.getResId());
        }
        if (resMonopolyPrice.getResAreaOrient() != null) {
            criteria.andResAreaOrientEqualTo(resMonopolyPrice.getResAreaOrient().byteValue());
        }

        List<ResourceInfoEntity> resInfo = resourceInfoMapper.selectByExample(resourceInfoEntityExample);

        if (null == resInfo || resInfo.size() == 0) {
            logger.warn("No resource record found with request:" + objectToString(resMonopolyPrice));
        } else {
            resVo.setResBidtype(ResBidType.PMP_CODE);
            resVo.setResId(resMonopolyPrice.getResId());
            resVo.setResAreaOrient(resMonopolyPrice.getResAreaOrient());
            resVo.setDefaultPrice(resInfo.get(0).getReservePrice());
            resVo.setPriceList(new ArrayList<MonopolyPriceInfo>());

            //所有的手动定价
            ResourcePriceInfoEntityExample resourcePriceInfoExample = new ResourcePriceInfoEntityExample();
            resourcePriceInfoExample.createCriteria().andResIdEqualTo(resMonopolyPrice.getResId()).andFrameIndexEqualTo((byte) 1); //用第一屏来处理
            List<ResourcePriceInfoEntity> priceList = resPriceInfoMapper.selectByExample(resourcePriceInfoExample);
            //查询出所有的大区
            List<RegionVo> lisAllRegion = regionService.queryListByRegionType(RegionTypeEnum.REGION);
            Map<Integer, RegionVo> mapAllRegion = new HashedMap();
            for (RegionVo regionVo : lisAllRegion) {
                mapAllRegion.put(regionVo.getRegionId().intValue(), regionVo);
            }
            //查询出所有的省份
            List<RegionVo> lisAllProvince = regionService.queryListByRegionType(RegionTypeEnum.PROVINCE);
            Map<Integer, RegionVo> mapAllProvince = new HashedMap();
            for (RegionVo regionVo : lisAllProvince) {
                mapAllProvince.put(regionVo.getRegionId().intValue(), regionVo);
            }
            //查询所有的城市
            List<RegionVo> lisAllCity = regionService.queryAllCity();
            //当前存在的手动设定的是否可投数据
            ResourceRegionExample rre = new ResourceRegionExample();
            rre.createCriteria().andResIdEqualTo(resMonopolyPrice.getResId());
            List<ResourceRegion> lisExistingResourceRegion = resourceRegionMapper.selectByExample(rre);
            Map<Integer, ResourceRegion> mapAllResourceRegion = new HashMap<>();

            if (resMonopolyPrice.getResAreaOrient() == ResAreaOrient.CITY) {
                for (ResourceRegion resourceRegion : lisExistingResourceRegion) {
                    if (!mapAllResourceRegion.containsKey(resourceRegion.getCityId().intValue())) {
                        mapAllResourceRegion.put(resourceRegion.getCityId().intValue(), resourceRegion);
                    }
                }
                Map<Integer, ResourcePriceInfoEntity> mapResourcePriceInfo = new HashMap<>();
                for (ResourcePriceInfoEntity resourcePriceInfo : priceList) {
                    mapResourcePriceInfo.put(resourcePriceInfo.getCityId().intValue(), resourcePriceInfo);
                }
                //先设置默认价格，默认价格为资源的保留价
                for (RegionVo region : lisAllCity) {
                    Long price = resInfo.get(0).getReservePrice();
                    if (mapResourcePriceInfo.containsKey(region.getCityId().intValue())) {
                        price = mapResourcePriceInfo.get(region.getCityId().intValue()).getPrice();
                    }
                    MonopolyPriceInfo priceInfo = new MonopolyPriceInfo(region.getAreaId().longValue(),
                            mapAllRegion.get(region.getAreaId()).getRegionName(), region.getProvinceId().longValue(),
                            mapAllProvince.get(region.getProvinceId()).getRegionName(), region.getCityId().longValue(),
                            region.getRegionName(), price);
                    if (mapAllResourceRegion.containsKey(region.getCityId().intValue())) {
                        priceInfo.setThrowable(mapAllResourceRegion.get(region.getCityId().intValue()).getThrowable());
                    }
                    resVo.getPriceList().add(priceInfo);
                }
            } else if (resMonopolyPrice.getResAreaOrient() == ResAreaOrient.SQUARE) {
                for (ResourceRegion resourceRegion : lisExistingResourceRegion) {
                    if (!mapAllResourceRegion.containsKey(resourceRegion.getPlazaId().intValue())) {
                        mapAllResourceRegion.put(resourceRegion.getPlazaId().intValue(), resourceRegion);
                    }
                }
                Map<Integer, RegionVo> mapAllCity = new HashedMap();
                for (RegionVo regionVo : lisAllCity) {
                    mapAllCity.put(regionVo.getCityId().intValue(), regionVo);
                }
                List<PlazaVo> lisAllPlaza = regionService.queryAllPlaza();
                Map<Integer, ResourcePriceInfoEntity> mapResourcePriceInfo = new HashMap<Integer, ResourcePriceInfoEntity>();
                for (ResourcePriceInfoEntity resourcePriceInfo : priceList) {
                    mapResourcePriceInfo.put(resourcePriceInfo.getPlazaId().intValue(), resourcePriceInfo);
                }
                for (PlazaVo plazaVo : lisAllPlaza) {
                    Long price = resInfo.get(0).getReservePrice();
                    Long priceId = null;
                    if (mapResourcePriceInfo.containsKey(plazaVo.getPlazaId().intValue())) {
                        price = mapResourcePriceInfo.get(plazaVo.getPlazaId().intValue()).getPrice();
                        priceId = mapResourcePriceInfo.get(plazaVo.getPlazaId().intValue()).getId();
                    }
                    MonopolyPriceInfo priceInfo = new MonopolyPriceInfo(plazaVo.getRegionId().longValue(),
                            mapAllRegion.get(plazaVo.getRegionId()).getRegionName(), plazaVo.getProvinceId().longValue(),
                            mapAllProvince.get(plazaVo.getProvinceId()).getRegionName(),
                            plazaVo.getCityId() == null ? null : plazaVo.getCityId().longValue(),
                            plazaVo.getCityId() == null ? null : mapAllCity.get(plazaVo.getCityId()).getRegionName(),
                            plazaVo.getPlazaId().longValue(), plazaVo.getPlazaName() == null ? "" : plazaVo.getPlazaName(),
                            price);
                    priceInfo.setPriceId(priceId);
                    if (mapAllResourceRegion.containsKey(plazaVo.getPlazaId().intValue())) {
                        priceInfo.setThrowable(mapAllResourceRegion.get(plazaVo.getPlazaId().intValue()).getThrowable());
                    }
                    resVo.getPriceList().add(priceInfo);
                }
            }
        }
        return resVo;
    }

    @Override
    public Page<ResourcePriceInfoResVo> queryStoreWithPriceInPage(@NotNull Integer resId, int pageNum, int pageSize) {
        //查询
        ResourceInfoEntity ri = resourceInfoMapper.selectByPrimaryKey(resId);
        if (ri == null) {
            return null;
        }
        Page<ResourcePriceInfoResVo> page = PageHelper.startPage(pageNum, pageSize);
        resPriceInfoMapperExt.selectStorePriceWithStore(resId, 1);
        if ("Y".equals(ri.getEnableReservePrice())) {
            for (ResourcePriceInfoResVo resourcePriceInfoResVo : page.getResult()) {
                if (resourcePriceInfoResVo.getPrice() == null || resourcePriceInfoResVo.getPriceId() == null) {
                    resourcePriceInfoResVo.setPrice(ri.getReservePrice());
                }
                if (resourcePriceInfoResVo.getThrowable() == null) {
                    resourcePriceInfoResVo.setThrowable("Y");
                }
            }
        }
        return page;
    }

    @Override
    public Page<ResourcePriceInfoResVo> getResourcePriceInfoFromCache(Integer resId, Integer accountId, int pageNum,
                                                                      int pageSize) {
        String resourcePriceKey = RedisCacheConstants.RESOURCE_PRICE_CACHE_KEY_PREFIX + RedisCacheConstants.CACHE_KEY_SPLIT
                + ResAreaOrient.STORE + RedisCacheConstants.CACHE_KEY_SPLIT + resId + RedisCacheConstants.CACHE_KEY_SPLIT
                + accountId;
        String cachedValue = RedisUtil.getString(resourcePriceKey);
        List<ResourcePriceInfoResVo> lisAll = JSON.parseArray(cachedValue, ResourcePriceInfoResVo.class);
        if (lisAll == null) {
            throw new FfanadException(FfanadStatus.S_ERROR_UNDEFINED, "缓存已过期");
        }
        int pageCount = lisAll.size() / pageSize + pageSize % lisAll.size();

        Page page = new Page();
        page.setTotal(lisAll.size());
        page.setPageSize(pageSize);
        page.setPageNum(pageNum);
        page.getResult().addAll(lisAll.subList(pageNum <= pageCount ? (pageNum - 1) * pageSize : 0,
                pageNum * pageSize > lisAll.size() ? lisAll.size() : pageNum * pageSize));
        return page;
    }

    @Override
    @Transactional
    public RestResult saveResourcePriceInfoFromCache(Integer resId, Integer accountId, HttpSession session) {
        String resourcePriceKey = RedisCacheConstants.RESOURCE_PRICE_CACHE_KEY_PREFIX + RedisCacheConstants.CACHE_KEY_SPLIT
                + ResAreaOrient.STORE + RedisCacheConstants.CACHE_KEY_SPLIT + resId + RedisCacheConstants.CACHE_KEY_SPLIT
                + accountId;
        String cachedValue = RedisUtil.getString(resourcePriceKey);
        List<ResourcePriceInfoResVo> lisAll = JSON.parseArray(cachedValue, ResourcePriceInfoResVo.class);
        if (CollectionUtils.isEmpty(lisAll)) {
            return new RestResult(FfanadStatus.S_BAD_REQUEST, "缓存已过期，请重新上传");
        }

        ResourceInfoEntity ri = resourceInfoMapper.selectByPrimaryKey(resId);
        if (ri.getResCarouselNum() <= 0) {
            return new RestResult(FfanadStatus.S_BAD_REQUEST, "该资源屏数小于1，不能定价");
        }

        StringBuffer logMsg = new StringBuffer();
        String defaultPrice = "";
        if ("Y".equals(ri.getEnableReservePrice())) {
            defaultPrice = CurrencyUtils.format(ri.getReservePrice());
        }

        ResourceRegionExample rre = new ResourceRegionExample();
        rre.createCriteria().andResIdEqualTo(resId);
        //当前存在的手动设定的是否可投数据
        List<ResourceRegion> lisExistingResourceRegion = resourceRegionMapper.selectByExample(rre);
        Map<Integer, ResourceRegion> mapAllResourceRegion = new HashMap<>();
        for (ResourceRegion resourceRegion : lisExistingResourceRegion) {
            if (!mapAllResourceRegion.containsKey(resourceRegion.getStoreId().intValue())) {
                mapAllResourceRegion.put(resourceRegion.getStoreId().intValue(), resourceRegion);
            }
        }
        //所有的门店数据
        Map<Integer, StoreVo> mapAllStore = getStoreMap(regionService.queryAllStore());

        //// TODO: 17/1/22 这里做一个调整,值保存第一屏的数据,如果要增加分屏定价功能,只需要将注释去掉即可
        //        for (int frameIndex = 1; frameIndex <= ri.getResCarouselNum(); frameIndex++) {
        for (int frameIndex = 1; frameIndex <= 1; frameIndex++) {
            //分屏处理，为以后扩展准备
            //当前存在的手动定价
            List<ResourcePriceInfoEntity> lisExistingPrice = resPriceInfoMapperExt.selectStorePrice(resId, frameIndex);
            Map<Integer, ResourcePriceInfoEntity> mapExistingPrice = new HashMap<>();
            for (ResourcePriceInfoEntity resourcePriceInfo : lisExistingPrice) {
                if (!mapExistingPrice.containsKey(resourcePriceInfo.getStoreId().intValue())) {
                    mapExistingPrice.put(resourcePriceInfo.getStoreId().intValue(), resourcePriceInfo);
                }
            }

            List<MonopolyPriceInfo> lisInsertPrice = new ArrayList<>();//要进行insert操作的定价
            List<MonopolyPriceInfo> lisUpdatePrice = new ArrayList<>();//要进行update操作的定价
            List<ResourceRegion> lisInsertRegion = new ArrayList<>();//要进行insert操作的是否可投
            List<ResourceRegion> lisUpdateRegion = new ArrayList<>();//要进行update操作的是否可投
            List<OperationLog> lisOperationLog = new ArrayList<>();//要插入的日志
            for (int i = 0; i < lisAll.size(); i++) {
                ResourcePriceInfoResVo resourcePriceInfoResVo = lisAll.get(i);
                if (StringUtils.isEmpty(resourcePriceInfoResVo.getOption())) {
                    //说明该门店是不存在的
                    continue;
                }

                resourcePriceInfoResVo.setFrameIndex((byte) frameIndex);
                //设置门店信息
                StoreVo store = mapAllStore.get(resourcePriceInfoResVo.getStoreId().intValue());
                resourcePriceInfoResVo.setCityId((long) store.getCityId());
                resourcePriceInfoResVo.setPlazaId(store.getPlazaId() == null ? 0L : store.getPlazaId());
                resourcePriceInfoResVo.setProvinceId(store.getProvinceId().longValue());
                resourcePriceInfoResVo
                        .setRegionId(store.getRegionId() == null ? resourcePriceInfoResVo.getRegionId() : store.getRegionId());
                resourcePriceInfoResVo.setProvinceName(store.getProvinceName());
                resourcePriceInfoResVo.setCityName(store.getCityName());
                resourcePriceInfoResVo.setStoreName(store.getStoreName());

                //判断是否存在price
                if (mapExistingPrice.containsKey(resourcePriceInfoResVo.getStoreId().intValue())) {
                    ResourcePriceInfoEntity resourcePriceInfo = mapExistingPrice
                            .get(resourcePriceInfoResVo.getStoreId().intValue());
                    if (resourcePriceInfo.getPrice().compareTo(resourcePriceInfoResVo.getPrice()) != 0) {
                        logMsg.append(
                                String.format(OperationLogPage.REMARK_UPDATE_MONOPOLY_PRICE, resourcePriceInfoResVo.getCityName(),
                                        resourcePriceInfoResVo.getStoreName(), CurrencyUtils.format(resourcePriceInfo.getPrice()),
                                        CurrencyUtils.format(resourcePriceInfoResVo.getPrice())));
                        MonopolyPriceInfo monopolyPriceInfo = new MonopolyPriceInfo();
                        monopolyPriceInfo.setPriceId(resourcePriceInfo.getId());
                        monopolyPriceInfo.setPrice(resourcePriceInfoResVo.getPrice());
                        lisUpdatePrice.add(monopolyPriceInfo);
                    }
                } else {
                    logMsg.append(String.format("【%s-%s】原定价为【%s】，调整为【%s】；<br/>", resourcePriceInfoResVo.getCityName(),
                            resourcePriceInfoResVo.getStoreName(), defaultPrice,
                            CurrencyUtils.format(resourcePriceInfoResVo.getPrice())));
                    lisInsertPrice.add(resourcePriceInfoResVo);
                }
                if (frameIndex == 1) {
                    //判断是否存在region,只处理一次
                    boolean isExitingRegion = mapAllResourceRegion.containsKey(resourcePriceInfoResVo.getStoreId().intValue());
                    if (isExitingRegion) {
                        ResourceRegion resourceRegion = mapAllResourceRegion.get(resourcePriceInfoResVo.getStoreId().intValue());
                        if (!resourceRegion.getThrowable().equals(resourcePriceInfoResVo.getThrowable())) {
                            logMsg.append(String.format("【%s-%s】是否可投为【%s】，调整为【%s】；<br/>", resourcePriceInfoResVo.getCityName(),
                                    resourcePriceInfoResVo.getStoreName(), resourceRegion.getThrowable(),
                                    resourcePriceInfoResVo.getThrowable()));
                            resourceRegion.setThrowable(resourcePriceInfoResVo.getThrowable());
                            lisUpdateRegion.add(resourceRegion);
                        }
                    }

                    if (!isExitingRegion && "N".equals(resourcePriceInfoResVo.getThrowable())) {
                        logMsg.append(String.format("【%s-%s】是否可投为【Y】，调整为【N】；<br/>", resourcePriceInfoResVo.getCityName(),
                                resourcePriceInfoResVo.getStoreName()));
                        ResourceRegion resourceRegion = new ResourceRegion();
                        resourceRegion.setResId(resId);
                        resourceRegion.setStoreId(resourcePriceInfoResVo.getStoreId());
                        resourceRegion.setCityId(resourcePriceInfoResVo.getCityId());
                        resourceRegion.setPlazaId(resourcePriceInfoResVo.getPlazaId());
                        resourceRegion.setThrowable(resourcePriceInfoResVo.getThrowable());
                        lisInsertRegion.add(resourceRegion);
                    }
                }
                if (i % 5000 == 0) {
                    if (lisInsertPrice.size() > 0) {
                        resPriceInfoMapperExt.batchInsertPriceList(resId, lisInsertPrice);
                        logger.debug("Insert price record in db:" + lisInsertPrice.size());
                        lisInsertPrice.clear();
                    }
                    if (lisUpdatePrice.size() > 0) {
                        resPriceInfoMapperExt.batchUpdatePriceList(resId, lisUpdatePrice);
                        logger.debug("Update price record in db:" + lisUpdatePrice.size());
                        lisUpdatePrice.clear();
                    }

                    if (lisInsertRegion.size() > 0) {
                        resourceRegionMapper.batchInsert(lisInsertRegion);
                        logger.debug("Insert resourceRegion record in db:" + lisInsertRegion.size());
                        lisInsertRegion.clear();
                    }
                    if (lisUpdateRegion.size() > 0) {
                        resourceRegionMapper.batchUpdateThrowableList(lisUpdateRegion);
                        logger.debug("Update price resourceRegion in db:" + lisUpdatePrice.size());
                        lisUpdateRegion.clear();
                    }
                    try {
                        if (lisOperationLog.size() > 0) {
                            int count = operationLogService.addOperationLog(lisOperationLog);
                            logger.info("Insert operationLog of Monopoly price record in db:" + count);
                            if (count > 0) {
                                lisOperationLog.clear();
                            }
                        }
                    } catch (Exception e) {
                        logger.error("Failed to log update monopoly price for id:" + resId + "  reason:" + e.getMessage());
                    }
                }

                if (logMsg.length() >= 900) {
                    String accountEmail = (String) session.getAttribute(SystemConstant.SESSION_ACCOUNT_EMAIL);
                    OperationLog ol = new OperationLog();
                    ol.setLogPage(OperationLogPage.RESOURCE_AUDIT_PRICE);
                    ol.setLogAction(OperationLogPage.UPDATE_PMP_PRICE);
                    ol.setLogUserEmail(accountEmail);
                    ol.setLogTarget(resId + "");
                    ol.setLogRemark(logMsg.toString());
                    ol.setLogOperation(OperationLogOperation.MODIFY_PRICE);
                    lisOperationLog.add(ol);
                    logMsg.delete(0, logMsg.length() - 1);
                }
            }

            if (lisInsertPrice.size() > 0) {
                resPriceInfoMapperExt.batchInsertPriceList(resId, lisInsertPrice);
                logger.debug("Insert price record in db:" + lisInsertPrice.size());
            }
            if (lisUpdatePrice.size() > 0) {
                resPriceInfoMapperExt.batchUpdatePriceList(resId, lisUpdatePrice);
                logger.debug("Update price record in db:" + lisUpdatePrice.size());
            }

            if (lisInsertRegion.size() > 0) {
                resourceRegionMapper.batchInsert(lisInsertRegion);
                logger.debug("Insert resourceRegion record in db:" + lisInsertRegion.size());
            }
            if (lisUpdateRegion.size() > 0) {
                resourceRegionMapper.batchUpdateThrowableList(lisUpdateRegion);
                logger.debug("Update price resourceRegion in db:" + lisUpdateRegion.size());
            }

            try {
                if (logMsg.length() > 0) {
                    String accountEmail = (String) session.getAttribute(SystemConstant.SESSION_ACCOUNT_EMAIL);
                    OperationLog ol = new OperationLog();
                    ol.setLogPage(OperationLogPage.RESOURCE_AUDIT_PRICE);
                    ol.setLogAction(OperationLogPage.UPDATE_PMP_PRICE);
                    ol.setLogUserEmail(accountEmail);
                    ol.setLogTarget(resId + "");
                    ol.setLogRemark(logMsg.toString());
                    ol.setLogOperation(OperationLogOperation.MODIFY_PRICE);
                    lisOperationLog.add(ol);
                }
                if (lisOperationLog.size() > 0) {
                    int count = operationLogService.addOperationLog(lisOperationLog);
                    logger.info("Insert operationLog of Monopoly price record in db:" + count);
                }
            } catch (Exception e) {
                logger.error("Failed to log update monopoly price for id:" + resId + "  reason:" + e.getMessage());
            }
        }
        RedisUtil.del(resourcePriceKey);
        //生成最低价的缓存
        List<ResourceInfoEntity> list = new ArrayList<>();
        list.add(ri);
        crmResourceService.buildCptMinPriceCache(list);
        return new RestResult(FfanadStatus.S_OK, "保存成功");
    }

    @Override
    public ResourceRTBPriceResVo updateResourceRTBPrice(ResourceRTBPriceReqVo resRTBPrice, HttpSession session) {
        //rtb,或者定制轮播的独占
        ResourceRTBPriceResVo resVo = new ResourceRTBPriceResVo();
        ResourceInfoEntityExample resourceInfoEntityExample = new ResourceInfoEntityExample();
        ResourceInfoEntityExample.Criteria criteria = resourceInfoEntityExample.createCriteria()
                .andResBidtypeEqualTo(PromoteResBidType.RTB.getValue().byteValue());
        ResourceInfoEntityExample.Criteria criteria2 = resourceInfoEntityExample.or()
                .andResPositiontypeEqualTo(ResPositionType.CAROUSEL_CODE.byteValue());
        if (resRTBPrice.getResId() != null) {
            criteria.andResIdEqualTo(resRTBPrice.getResId());
            criteria2.andResIdEqualTo(resRTBPrice.getResId());
        }

        List<ResourceInfoEntity> resInfo = resourceInfoMapper.selectByExample(resourceInfoEntityExample);
        if (null == resInfo || resInfo.size() == 0) {
            String errmsg = "No RTB resource record found with resId:" + resRTBPrice.getResId();
            logger.error(errmsg);
            throw new FfanadException(FfanadStatus.S_BAD_REQUEST, errmsg);
        } else if (resInfo.size() > 1) {
            String errmsg = "Multiple RTB resource records found with resId:" + resRTBPrice.getResId() + ", size:"
                    + resInfo.size();
            logger.error(errmsg);
            throw new FfanadException(FfanadStatus.S_BAD_REQUEST, errmsg);
        } else {
            resVo.setResId(resRTBPrice.getResId());
            resourceInfoMapperExt.updateRTBPrice(resRTBPrice.getResId(),
                    CurrencyUtils.convert(resRTBPrice.getResCpc().doubleValue()),
                    CurrencyUtils.convert(resRTBPrice.getResCpm().doubleValue()));

            ResourceInfoEntity updateResRecord = resourceInfoMapper.selectByPrimaryKey(resRTBPrice.getResId());
            resVo.setResCpc(updateResRecord.getResCpc());
            resVo.setResCpm(updateResRecord.getResCpm());
            try {
                ResourceInfoEntity oriInfo = resInfo.get(0);
                String accountEmail = (String) session.getAttribute(SystemConstant.SESSION_ACCOUNT_EMAIL);
                String logRemark = String.format(OperationLogPage.REMARK_UPDATE_RTB_PRICE,
                        CurrencyUtils.format(oriInfo.getResCpm()), CurrencyUtils.format(oriInfo.getResCpc()),
                        resRTBPrice.getResCpm(), resRTBPrice.getResCpc());
                logger.info("{" + resRTBPrice.getResId() + "}修改资源RTB价格. Detail:" + logRemark);
                operationLogService.addOperationLog(OperationLogPage.RESOURCE_AUDIT_PRICE, OperationLogOperation.MODIFY_PRICE,
                        accountEmail, OperationLogPage.UPDATE_RTB_PRICE, resRTBPrice.getResId().toString(), logRemark);
            } catch (Exception ex) {
                logger.error("Failed to log update rtb price for id:" + resRTBPrice.getResId());
            }
            return resVo;
        }
    }

    @Override
    public ResourceMonopolyPriceResVo updateResourceMonopolyPrice(ResourceMonopolyPriceUpdateReqVo resMonopolyPrice,
                                                                  HttpSession session) {
        ResourceMonopolyPriceResVo resVo = new ResourceMonopolyPriceResVo();

        ResourceInfoEntityExample resourceInfoEntityExample = new ResourceInfoEntityExample();
        ResourceInfoEntityExample.Criteria criteria = resourceInfoEntityExample.createCriteria()
                .andResBidtypeEqualTo(PromoteResBidType.PMP.getValue().byteValue());
        if (resMonopolyPrice.getResId() != null) {
            criteria.andResIdEqualTo(resMonopolyPrice.getResId());
        }
        if (resMonopolyPrice.getResAreaOrient() != null) {
            criteria.andResAreaOrientEqualTo(resMonopolyPrice.getResAreaOrient().byteValue());
        }

        List<ResourceInfoEntity> resInfo = resourceInfoMapper.selectByExample(resourceInfoEntityExample);
        if (null == resInfo || resInfo.size() == 0) {
            String errmsg = "No PMP resource record found with resId:" + objectToString(resMonopolyPrice);
            logger.error(errmsg);
            throw new FfanadException(FfanadStatus.S_BAD_REQUEST, errmsg);
        } else {
            if (CollectionUtils.isNotEmpty(resMonopolyPrice.getResPriceList())) {
                String accountEmail = (String) session.getAttribute(SystemConstant.SESSION_ACCOUNT_EMAIL);
                if (resMonopolyPrice.getResAreaOrient() == ResAreaOrient.CITY) {
                    updateCityPrice(resMonopolyPrice, accountEmail, resInfo.get(0));
                } else if (resMonopolyPrice.getResAreaOrient() == ResAreaOrient.SQUARE) {
                    updatePlazaPrice(resMonopolyPrice, accountEmail, resInfo.get(0));
                }
            }
        }
        return resVo;
    }

    /**
     * 更新按城市定价
     *
     * @param resMonopolyPrice
     * @return
     */
    @Transactional
    private ResourceMonopolyPriceResVo updateCityPrice(ResourceMonopolyPriceUpdateReqVo resMonopolyPrice, String accountEmail,
                                                       ResourceInfoEntity resourceInfo) {
        ResourceMonopolyPriceResVo resVo = new ResourceMonopolyPriceResVo();
        ResourceInfoEntity queryRecord = new ResourceInfoEntity();
        queryRecord.setResId(resMonopolyPrice.getResId());
        queryRecord.setResBidtype(PromoteResBidType.PMP.getValue().byteValue());
        queryRecord.setResAreaOrient(resMonopolyPrice.getResAreaOrient().byteValue());

        List<MonopolyPriceInfo> newPriceList = resMonopolyPrice.getResPriceList();
        List<OperationLog> lisOperationLog = new ArrayList<>();//要插入的日志

        if (null != newPriceList && newPriceList.size() > 0) {
            StringBuffer logMsg = new StringBuffer();
            List<ResourcePriceInfoEntity> existingPriceList = resPriceInfoMapperExt
                    .queryByResourceId(resMonopolyPrice.getResId());
            Map<Integer, ResourcePriceInfoEntity> mapExistingPrice = new HashMap<>();
            for (ResourcePriceInfoEntity resourcePriceInfoEntity : existingPriceList) {
                if (!mapExistingPrice.containsKey(resourcePriceInfoEntity.getCityId())) {
                    mapExistingPrice.put(resourcePriceInfoEntity.getCityId(), resourcePriceInfoEntity);
                }
            }
            //查询出所有的city
            Map<Integer, RegionVo> mapAllCity = new HashedMap();
            List<RegionVo> lisAllCity = regionService.queryAllCity();
            for (RegionVo city : lisAllCity) {
                mapAllCity.put(city.getCityId().intValue(), city);
            }

            //当前存在的手动设定的是否可投数据
            ResourceRegionExample rre = new ResourceRegionExample();
            rre.createCriteria().andResIdEqualTo(resMonopolyPrice.getResId());
            List<ResourceRegion> lisExistingResourceRegion = resourceRegionMapper.selectByExample(rre);
            Map<Integer, ResourceRegion> mapAllResourceRegion = new HashMap<>();
            for (ResourceRegion resourceRegion : lisExistingResourceRegion) {
                if (!mapAllResourceRegion.containsKey(resourceRegion.getCityId().intValue())) {
                    mapAllResourceRegion.put(resourceRegion.getCityId().intValue(), resourceRegion);
                }
            }

            List<MonopolyPriceInfo> updatePriceList = new ArrayList<>();
            List<MonopolyPriceInfo> insertPriceList = new ArrayList<>();
            List<ResourceRegion> lisInsertRegion = new ArrayList<>();//要进行insert操作的是否可投
            List<ResourceRegion> lisUpdateRegion = new ArrayList<>();//要进行update操作的是否可投
            for (MonopolyPriceInfo priceInfo : newPriceList) {
                //校验关键值
                if (priceInfo.getPrice() == null || priceInfo.getCityId() == null
                        || StringUtils.isEmpty(priceInfo.getThrowable())) {
                    continue;
                }

                RegionVo city = mapAllCity.get(priceInfo.getCityId().intValue());
                if (!mapAllCity.containsKey(priceInfo.getCityId().intValue())) {
                    continue;
                }
                priceInfo.setRegionId(city.getRegionId().longValue());
                priceInfo.setProvinceId(city.getProvinceId().longValue());
                priceInfo.setCityId(city.getCityId() == null ? -1 : city.getCityId().longValue());
                priceInfo.setPlazaId(0L);

                if (mapExistingPrice.containsKey(priceInfo.getCityId().intValue()) && priceInfo.getPrice() != null
                        && !priceInfo.getPrice().equals(mapExistingPrice.get(priceInfo.getCityId().intValue()).getPrice())) {
                    //做更新操作
                    priceInfo.setPriceId(mapExistingPrice.get(priceInfo.getCityId().intValue()).getId());
                    updatePriceList.add(priceInfo);
                    logMsg.append(String.format(OperationLogPage.REMARK_UPDATE_MONOPOLY_PRICE, priceInfo.getProvinceName(),
                            priceInfo.getCityName() + priceInfo.getPlazaName(),
                            CurrencyUtils.format(mapExistingPrice.get(priceInfo.getCityId().intValue()).getPrice()),
                            priceInfo.getPriceStr()));
                    if (logMsg.length() >= 900) {
                        OperationLog ol = new OperationLog();
                        ol.setLogPage(OperationLogPage.RESOURCE_AUDIT_PRICE);
                        ol.setLogAction(OperationLogPage.UPDATE_PMP_PRICE);
                        ol.setLogUserEmail(accountEmail);
                        ol.setLogTarget(resMonopolyPrice.getResId() + "");
                        ol.setLogRemark(logMsg.toString());
                        ol.setLogOperation(OperationLogOperation.MODIFY_PRICE);
                        lisOperationLog.add(ol);
                        logMsg.delete(0, logMsg.length() - 1);
                    }
                } else {
                    //做插入操作
                    insertPriceList.add(priceInfo);
                    logMsg.append(String.format(OperationLogPage.REMARK_UPDATE_MONOPOLY_PRICE, priceInfo.getProvinceName(),
                            priceInfo.getCityName() + priceInfo.getPlazaName(), CurrencyUtils.format(defaultPrice),
                            CurrencyUtils.format(priceInfo.getPrice())));
                    if (logMsg.length() >= 900) {
                        OperationLog ol = new OperationLog();
                        ol.setLogUserEmail(accountEmail);
                        ol.setLogPage(OperationLogPage.RESOURCE_AUDIT_PRICE);
                        ol.setLogAction(OperationLogPage.UPDATE_PMP_PRICE);
                        ol.setLogUserEmail(accountEmail);
                        ol.setLogTarget(resMonopolyPrice.getResId() + "");
                        ol.setLogOperation(OperationLogOperation.MODIFY_PRICE);
                        ol.setLogRemark(logMsg.toString());
                        lisOperationLog.add(ol);
                        logMsg.delete(0, logMsg.length() - 1);
                    }
                }

                boolean isExitingRegion = mapAllResourceRegion.containsKey(priceInfo.getCityId().intValue());
                if (isExitingRegion) {
                    ResourceRegion resourceRegion = mapAllResourceRegion.get(priceInfo.getCityId().intValue());
                    if (!resourceRegion.getThrowable().equals(priceInfo.getThrowable())) {
                        logMsg.append(String.format("【%s-%s】是否可投为【%s】，调整为【%s】；<br/>", priceInfo.getCityName(),
                                priceInfo.getCityName(), resourceRegion.getThrowable(), priceInfo.getThrowable()));
                        resourceRegion.setThrowable(priceInfo.getThrowable());
                        lisUpdateRegion.add(resourceRegion);
                    }
                }

                if (!isExitingRegion && "N".equals(priceInfo.getThrowable())) {
                    logMsg.append(
                            String.format("【%s-%s】是否可投为【Y】，调整为【N】；<br/>", priceInfo.getCityName(), priceInfo.getCityName()));
                    ResourceRegion resourceRegion = new ResourceRegion();
                    resourceRegion.setResId(resMonopolyPrice.getResId());
                    resourceRegion.setStoreId(0L);
                    resourceRegion.setCityId(priceInfo.getCityId());
                    resourceRegion.setPlazaId(0L);
                    resourceRegion.setThrowable(priceInfo.getThrowable());
                    lisInsertRegion.add(resourceRegion);
                }
            }
            if (insertPriceList.size() > 0) {
                resPriceInfoMapperExt.batchInsertPriceList(resMonopolyPrice.getResId(), insertPriceList);
                logger.debug("Insert price record in db:" + insertPriceList.size());
            }
            if (updatePriceList.size() > 0) {
                resPriceInfoMapperExt.batchUpdatePriceList(resMonopolyPrice.getResId(), updatePriceList);
                logger.debug("Update price record in db:" + updatePriceList.size());
            }
            if (lisInsertRegion.size() > 0) {
                resourceRegionMapper.batchInsert(lisInsertRegion);
                logger.debug("Insert resourceRegion record in db:" + lisInsertRegion.size());
                lisInsertRegion.clear();
            }
            if (lisUpdateRegion.size() > 0) {
                resourceRegionMapper.batchUpdateThrowableList(lisUpdateRegion);
                logger.debug("Update price resourceRegion in db:" + lisUpdateRegion.size());
                lisUpdateRegion.clear();
            }
            try {
                if (logMsg.length() > 0) {
                    OperationLog ol = new OperationLog();
                    ol.setLogPage(OperationLogPage.RESOURCE_AUDIT_PRICE);
                    ol.setLogAction(OperationLogPage.UPDATE_PMP_PRICE);
                    ol.setLogUserEmail(accountEmail);
                    ol.setLogOperation(OperationLogOperation.MODIFY_PRICE);
                    ol.setLogTarget(resMonopolyPrice.getResId() + "");
                    ol.setLogRemark(logMsg.toString());
                    lisOperationLog.add(ol);
                    logMsg.delete(0, logMsg.length() - 1);
                }
                operationLogService.addOperationLog(lisOperationLog);
            } catch (Exception ex) {
                logger.error("Failed to log update monopoly price for id:" + resMonopolyPrice.getResId());
            }

            List<ResourceInfoEntity> list = new ArrayList<>();
            list.add(resourceInfo);
            crmResourceService.buildCptMinPriceCache(list);
        }
        return resVo;
    }

    /**
     * 按广场定价
     *
     * @param resMonopolyPrice
     * @return
     */
    @Transactional
    private ResourceMonopolyPriceResVo updatePlazaPrice(ResourceMonopolyPriceUpdateReqVo resMonopolyPrice, String accountEmail,
                                                        ResourceInfoEntity resourceInfo) {
        ResourceMonopolyPriceResVo resVo = new ResourceMonopolyPriceResVo();
        ResourceInfoEntity queryRecord = new ResourceInfoEntity();
        queryRecord.setResId(resMonopolyPrice.getResId());
        queryRecord.setResBidtype(PromoteResBidType.PMP.getValue().byteValue());
        queryRecord.setResAreaOrient(resMonopolyPrice.getResAreaOrient().byteValue());

        List<MonopolyPriceInfo> newPriceList = resMonopolyPrice.getResPriceList();
        List<OperationLog> lisOperationLog = new ArrayList<>();//要插入的日志

        if (null != newPriceList && newPriceList.size() > 0) {
            StringBuffer logMsg = new StringBuffer();
            List<ResourcePriceInfoEntity> existingPriceList = resPriceInfoMapperExt
                    .queryByResourceId(resMonopolyPrice.getResId());
            Map<Integer, ResourcePriceInfoEntity> mapExistingPrice = new HashMap<>();
            for (ResourcePriceInfoEntity resourcePriceInfoEntity : existingPriceList) {
                if (!mapExistingPrice.containsKey(resourcePriceInfoEntity.getPlazaId().intValue())) {
                    mapExistingPrice.put(resourcePriceInfoEntity.getPlazaId().intValue(), resourcePriceInfoEntity);
                }
            }

            //查询出所有的广场
            Map<Integer, PlazaVo> mapAllPlaza = new HashedMap();
            List<PlazaVo> lisAllPlaza = regionService.queryAllPlaza();
            for (PlazaVo plazaVo : lisAllPlaza) {
                mapAllPlaza.put(plazaVo.getPlazaId().intValue(), plazaVo);
            }

            //当前存在的手动设定的是否可投数据
            ResourceRegionExample rre = new ResourceRegionExample();
            rre.createCriteria().andResIdEqualTo(resMonopolyPrice.getResId());
            List<ResourceRegion> lisExistingResourceRegion = resourceRegionMapper.selectByExample(rre);
            Map<Integer, ResourceRegion> mapAllResourceRegion = new HashMap<>();
            for (ResourceRegion resourceRegion : lisExistingResourceRegion) {
                if (!mapAllResourceRegion.containsKey(resourceRegion.getPlazaId().intValue())) {
                    mapAllResourceRegion.put(resourceRegion.getPlazaId().intValue(), resourceRegion);
                }
            }

            List<MonopolyPriceInfo> updatePriceList = new ArrayList<>();
            List<MonopolyPriceInfo> insertPriceList = new ArrayList<>();
            List<ResourceRegion> lisInsertRegion = new ArrayList<>();//要进行insert操作的是否可投
            List<ResourceRegion> lisUpdateRegion = new ArrayList<>();//要进行update操作的是否可投

            for (MonopolyPriceInfo priceInfo : newPriceList) {
                //校验关键值
                if (priceInfo.getPrice() == null || priceInfo.getPlazaId() == null) {
                    continue;
                }
                PlazaVo plazaVo = mapAllPlaza.get(priceInfo.getPlazaId().intValue());
                if (!mapAllPlaza.containsKey(priceInfo.getPlazaId().intValue())) {
                    continue;
                }
                priceInfo.setRegionId(plazaVo.getRegionId().longValue());
                priceInfo.setProvinceId(plazaVo.getProvinceId().longValue());
                priceInfo.setCityId(plazaVo.getCityId() == null ? -1 : plazaVo.getCityId().longValue());

                if (mapExistingPrice.containsKey(priceInfo.getPlazaId().intValue()) && priceInfo.getPrice() != null
                        && !priceInfo.getPrice().equals(mapExistingPrice.get(priceInfo.getPlazaId().intValue()).getPrice())) {
                    //做更新操作
                    priceInfo.setPriceId(mapExistingPrice.get(priceInfo.getPlazaId().intValue()).getId());
                    updatePriceList.add(priceInfo);
                    logMsg.append(String.format(OperationLogPage.REMARK_UPDATE_MONOPOLY_PRICE, priceInfo.getProvinceName(),
                            priceInfo.getCityName() + priceInfo.getPlazaName(),
                            CurrencyUtils.format(mapExistingPrice.get(priceInfo.getPlazaId().intValue()).getPrice()),
                            priceInfo.getPriceStr()));
                    if (logMsg.length() >= 900) {
                        OperationLog ol = new OperationLog();
                        ol.setLogPage(OperationLogPage.RESOURCE_AUDIT_PRICE);
                        ol.setLogUserEmail(accountEmail);
                        ol.setLogAction(OperationLogPage.UPDATE_PMP_PRICE);
                        ol.setLogTarget(resMonopolyPrice.getResId() + "");
                        ol.setLogRemark(logMsg.toString());
                        ol.setLogOperation(OperationLogOperation.MODIFY_PRICE);
                        lisOperationLog.add(ol);
                        logMsg.delete(0, logMsg.length() - 1);
                    }
                } else {
                    //做插入操作
                    insertPriceList.add(priceInfo);
                    logMsg.append(String.format(OperationLogPage.REMARK_UPDATE_MONOPOLY_PRICE, priceInfo.getProvinceName(),
                            priceInfo.getCityName() + priceInfo.getPlazaName(),
                            CurrencyUtils.format(resourceInfo.getReservePrice()), CurrencyUtils.format(priceInfo.getPrice())));
                    if (logMsg.length() >= 900) {
                        OperationLog ol = new OperationLog();
                        ol.setLogPage(OperationLogPage.RESOURCE_AUDIT_PRICE);
                        ol.setLogUserEmail(accountEmail);
                        ol.setLogAction(OperationLogPage.UPDATE_PMP_PRICE);
                        ol.setLogRemark(logMsg.toString());
                        ol.setLogTarget(resMonopolyPrice.getResId() + "");
                        ol.setLogOperation(OperationLogOperation.MODIFY_PRICE);
                        lisOperationLog.add(ol);
                        logMsg.delete(0, logMsg.length() - 1);
                    }
                }

                boolean isExitingRegion = mapAllResourceRegion.containsKey(priceInfo.getPlazaId().intValue());
                if (isExitingRegion) {
                    ResourceRegion resourceRegion = mapAllResourceRegion.get(priceInfo.getPlazaId().intValue());
                    if (!resourceRegion.getThrowable().equals(priceInfo.getThrowable())) {
                        logMsg.append(String.format("【%s-%s】是否可投为【%s】，调整为【%s】；<br/>", priceInfo.getProvinceName(),
                                priceInfo.getCityName() + priceInfo.getPlazaName(), resourceRegion.getThrowable(),
                                priceInfo.getThrowable()));
                        resourceRegion.setThrowable(priceInfo.getThrowable());
                        lisUpdateRegion.add(resourceRegion);
                    }
                }

                if (!isExitingRegion && "N".equals(priceInfo.getThrowable())) {
                    logMsg.append(String.format("【%s-%s】是否可投为【Y】，调整为【N】；<br/>", priceInfo.getProvinceName(),
                            priceInfo.getCityName() + priceInfo.getPlazaName()));
                    ResourceRegion resourceRegion = new ResourceRegion();
                    resourceRegion.setResId(resMonopolyPrice.getResId());
                    resourceRegion.setStoreId(0L);
                    resourceRegion.setCityId(priceInfo.getCityId());
                    resourceRegion.setPlazaId(priceInfo.getPlazaId());
                    resourceRegion.setThrowable(priceInfo.getThrowable());
                    lisInsertRegion.add(resourceRegion);
                }
            }
            if (insertPriceList.size() > 0) {
                resPriceInfoMapperExt.batchInsertPriceList(resMonopolyPrice.getResId(), insertPriceList);
                logger.debug("Insert price record in db:" + insertPriceList.size());
            }
            if (updatePriceList.size() > 0) {
                resPriceInfoMapperExt.batchUpdatePriceList(resMonopolyPrice.getResId(), updatePriceList);
                logger.debug("Update price record in db:" + updatePriceList.size());
            }
            if (lisInsertRegion.size() > 0) {
                resourceRegionMapper.batchInsert(lisInsertRegion);
                logger.debug("Insert resourceRegion record in db:" + lisInsertRegion.size());
                lisInsertRegion.clear();
            }
            if (lisUpdateRegion.size() > 0) {
                resourceRegionMapper.batchUpdateThrowableList(lisUpdateRegion);
                logger.debug("Update price resourceRegion in db:" + lisUpdateRegion.size());
                lisUpdateRegion.clear();
            }
            try {
                if (logMsg.length() > 0) {
                    OperationLog ol = new OperationLog();
                    ol.setLogPage(OperationLogPage.RESOURCE_AUDIT_PRICE);
                    ol.setLogUserEmail(accountEmail);
                    ol.setLogAction(OperationLogPage.UPDATE_PMP_PRICE);
                    ol.setLogTarget(resMonopolyPrice.getResId() + "");
                    ol.setLogRemark(logMsg.toString());
                    ol.setLogOperation(OperationLogOperation.MODIFY_PRICE);
                    lisOperationLog.add(ol);
                }
                operationLogService.addOperationLog(lisOperationLog);
            } catch (Exception ex) {
                logger.error("Failed to log update monopoly price for id:" + resMonopolyPrice.getResId());
            }

            List<ResourceInfoEntity> list = new ArrayList<>();
            list.add(resourceInfo);
            crmResourceService.buildCptMinPriceCache(list);
        }
        return resVo;
    }

    @Override
    public RestResult buildResourcePriceInfoToCache(Integer resId, Integer accountId,
                                                    List<ResourcePriceInfoResVo> lisResourceInfo) {
        ResourceInfoEntity ri = resourceInfoMapper.selectByPrimaryKey(resId);
        if (ri == null || 3 != ri.getResAreaOrient()) {
            return new RestResult(FfanadStatus.S_BAD_REQUEST, "资源为空或不是按门店投放");
        }

        ResourcePriceInfoEntityExample rpie = new ResourcePriceInfoEntityExample();
        rpie.createCriteria().andResIdEqualTo(resId).andFrameIndexEqualTo((byte) 1); // 当前直接获取第一屏的价格
        List<ResourcePriceInfoEntity> lisAllResourcePriceInfo = resPriceInfoMapper.selectByExample(rpie);
        Map<Integer, ResourcePriceInfoEntity> mapAllResourcePriceInfo = new HashMap<Integer, ResourcePriceInfoEntity>();
        for (ResourcePriceInfoEntity resourcePriceInfo : lisAllResourcePriceInfo) {
            if (!mapAllResourcePriceInfo.containsKey(resourcePriceInfo.getStoreId().intValue())) {
                mapAllResourcePriceInfo.put(resourcePriceInfo.getStoreId().intValue(), resourcePriceInfo);
            }
        }

        ResourceRegionExample resourceRegionExample = new ResourceRegionExample();
        resourceRegionExample.createCriteria().andResIdEqualTo(resId);
        List<ResourceRegion> lisAllResourceRegion = resourceRegionMapper.selectByExample(resourceRegionExample);
        Map<Integer, ResourceRegion> mapAllResourceRegion = new HashMap<Integer, ResourceRegion>();
        for (ResourceRegion resourceRegion : lisAllResourceRegion) {
            if (!mapAllResourceRegion.containsKey(resourceRegion.getStoreId().intValue())) {
                mapAllResourceRegion.put(resourceRegion.getStoreId().intValue(), resourceRegion);
            }
        }
        Map<Integer, StoreVo> mapAllStore = getStoreMap(regionService.queryAllStore());
        int insertCount = 0, updateCount = 0;
        for (ResourcePriceInfoResVo resourcePriceInfoResVo : lisResourceInfo) {
            //判断该门店定价是否存在，进行更新/新增
            //先到resourcePrice表里面查，如果有就进行更新，resId,storeId
            if (mapAllResourcePriceInfo.containsKey(resourcePriceInfoResVo.getStoreId().intValue())) {
                updateCount++;
                resourcePriceInfoResVo.setOption(ResourcePriceInfoResVo.OPTION_UPDATE);
                continue;
            }

            //如果resourcePrice里面没有，就到resourceRegion里面查
            if (mapAllResourceRegion.containsKey(resourcePriceInfoResVo.getStoreId())) {
                updateCount++;
                resourcePriceInfoResVo.setOption(ResourcePriceInfoResVo.OPTION_UPDATE);
                continue;
            }

            if (mapAllStore.containsKey(resourcePriceInfoResVo.getStoreId().intValue())) {
                insertCount++;
                resourcePriceInfoResVo.setOption(ResourcePriceInfoResVo.OPTION_INSERT);
                continue;
            }
            //如果门店不存在，option直接为空
        }
        //保存到缓存里面
        String resourcePriceKey = RedisCacheConstants.RESOURCE_PRICE_CACHE_KEY_PREFIX + RedisCacheConstants.CACHE_KEY_SPLIT
                + ResAreaOrient.STORE + RedisCacheConstants.CACHE_KEY_SPLIT + resId + RedisCacheConstants.CACHE_KEY_SPLIT
                + accountId;
        //long endTime = DateUtil.getEndTimeOfCurrentDay();
        //expireAt接收的秒，getEndTimeOfCurrentDay返回的是毫秒
        long endTime = new DateTime().plusMinutes(5).getMillis() / 1000; //5分钟后过期
        RedisUtil.setStringExAt(resourcePriceKey, endTime, JSON.toJSONString(lisResourceInfo));
        return new RestResult(FfanadStatus.S_OK, insertCount + "," + updateCount);
    }

    /**
     * 将门店的list转换为map
     *
     * @param lisStore
     * @return
     */
    private Map<Integer, StoreVo> getStoreMap(List<StoreVo> lisStore) {
        Map<Integer, StoreVo> result = new HashMap<Integer, StoreVo>();
        if (CollectionUtils.isEmpty(lisStore)) {
            return result;
        }
        for (StoreVo storeVo : lisStore) {
            if (!result.containsKey(storeVo.getStoreId())) {
                result.put(storeVo.getStoreId().intValue(), storeVo);
            }
        }
        return result;
    }

    private String objectToString(Object obj) {
        return ToStringBuilder.reflectionToString(obj, ToStringStyle.DEFAULT_STYLE);
    }

}
