package com.wanda.ffanad.crm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wanda.ffanad.base.common.PageBo;
import com.wanda.ffanad.base.constants.OperationLogAction;
import com.wanda.ffanad.base.constants.OperationLogOperation;
import com.wanda.ffanad.base.constants.OperationLogPage;
import com.wanda.ffanad.base.constants.ResAreaOrient;
import com.wanda.ffanad.base.constants.ResBidType;
import com.wanda.ffanad.base.dal.entities.AccountEntity;
import com.wanda.ffanad.base.dal.entities.AccountEntityExample;
import com.wanda.ffanad.base.dal.entities.AppEntity;
import com.wanda.ffanad.base.dal.entities.AppEntityExample;
import com.wanda.ffanad.base.dal.entities.AppResourceEntity;
import com.wanda.ffanad.base.dal.entities.AppResourceEntityExample;
import com.wanda.ffanad.base.dal.entities.ChannelEntity;
import com.wanda.ffanad.base.dal.entities.ChannelEntityExample;
import com.wanda.ffanad.base.dal.entities.ResourceInfoEntity;
import com.wanda.ffanad.base.dal.entities.ResourceInfoEntityExample;
import com.wanda.ffanad.base.dal.entities.ResourcePriceInfoEntity;
import com.wanda.ffanad.base.dal.entities.ResourcePriceInfoEntityExample;
import com.wanda.ffanad.base.dal.mappers.AccountEntityMapper;
import com.wanda.ffanad.base.dal.mappers.AppEntityMapper;
import com.wanda.ffanad.base.dal.mappers.AppResourceEntityMapper;
import com.wanda.ffanad.base.dal.mappers.ChannelEntityMapper;
import com.wanda.ffanad.base.dal.mappers.ResourceInfoEntityMapper;
import com.wanda.ffanad.base.dal.mappers.ResourcePriceInfoEntityMapper;
import com.wanda.ffanad.base.error.AdAsserts;
import com.wanda.ffanad.base.error.AdRuntimeException;
import com.wanda.ffanad.base.error.FfanadException;
import com.wanda.ffanad.base.redis.RedisUtil;
import com.wanda.ffanad.core.common.PaginationBo;
import com.wanda.ffanad.core.common.redis.RedisCacheConstants;
import com.wanda.ffanad.core.constants.ResPositionType;
import com.wanda.ffanad.core.domains.ResourceRegion;
import com.wanda.ffanad.core.domains.ResourceRegionExample;
import com.wanda.ffanad.core.domains.ResourceSpecInfo;
import com.wanda.ffanad.core.domains.vo.req.ResourceReqVo;
import com.wanda.ffanad.core.domains.vo.res.ResourceResVo;
import com.wanda.ffanad.core.mappers.ResourceRegionMapper;
import com.wanda.ffanad.core.mappers.ResourceSpecInfoMapper;
import com.wanda.ffanad.core.services.OperationLogService;
import com.wanda.ffanad.core.services.api.RegionService;
import com.wanda.ffanad.core.vo.PlazaVo;
import com.wanda.ffanad.core.vo.RegionVo;
import com.wanda.ffanad.core.vo.StoreVo;
import com.wanda.ffanad.crm.dto.req.ResourceChannelPageReqDto;
import com.wanda.ffanad.crm.dto.resp.ResourceChannelRepDto;
import com.wanda.ffanad.crm.service.CrmResourceService;

/**
 * Created by kevin on 16/11/17.
 */
@Service
public class CrmResourceServiceImpl implements CrmResourceService {
    private static final Logger logger = LoggerFactory.getLogger(CrmResourceServiceImpl.class);
    private static final long RESOURCE_MIN_DEFAULT_MIN_PRICE = 1000000000L;

    @Autowired
    private ResourceInfoEntityMapper resourceInfoEntityMapper;

    @Autowired
    private ChannelEntityMapper channelEntityMapper;

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private ResourceInfoEntityMapper resourceInfoMapper;

    @Autowired
    private AccountEntityMapper accountEntityMapper;

    @Autowired
    private AppResourceEntityMapper appResourceEntityMapper;

    @Autowired
    private AppEntityMapper appEntityMapper;

    @Autowired
    private ResourceSpecInfoMapper resSpecInfoMapper;

    @Autowired
    private ResourcePriceInfoEntityMapper resPriceInfoMapper;

    @Autowired
    private RegionService regionService;

    @Autowired
    private ResourceRegionMapper resourceRegionMapper;

    @Override
    public PageBo<ResourceChannelRepDto> getResourcesByPage(ResourceChannelPageReqDto resourceChannelPageReqDto) {
        PageBo<ResourceChannelRepDto> retObject = new PageBo<>();
        ResourceInfoEntityExample resourceInfoEntityExample = new ResourceInfoEntityExample();
        ResourceInfoEntityExample.Criteria criteria = resourceInfoEntityExample.createCriteria();
        if (resourceChannelPageReqDto.getChannelId() != null && resourceChannelPageReqDto.getChannelId() >= 0) {
            criteria.andChannelIdEqualTo(resourceChannelPageReqDto.getChannelId());
        }
        if (resourceChannelPageReqDto.getResId() != null) {
            criteria.andResIdEqualTo(resourceChannelPageReqDto.getResId());
        }
        if (StringUtils.isNotEmpty(resourceChannelPageReqDto.getResName())) {
            criteria.andResNameLike("%" + resourceChannelPageReqDto.getResName() + "%");
        }
        resourceInfoEntityExample.setOrderByClause("res_updatedate desc");
        Page<ResourceInfoEntity> page = PageHelper.startPage(resourceChannelPageReqDto.getPage(),
                resourceChannelPageReqDto.getLimit());
        resourceInfoEntityMapper.selectByExample(resourceInfoEntityExample);
        if (!CollectionUtils.isEmpty(page)) {
            List<ResourceChannelRepDto> resultList = new ArrayList<>();
            ChannelEntityExample channelEntityExample = new ChannelEntityExample();
            channelEntityExample.setOrderByClause("update_time desc");
            List<ChannelEntity> channelEntityList = channelEntityMapper.selectByExample(channelEntityExample);
            Map<Integer, ChannelEntity> channelEntityMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(channelEntityList)) {
                for (ChannelEntity channelEntity : channelEntityList) {
                    if (!channelEntityMap.containsKey(channelEntity.getId())) {
                        channelEntityMap.put(channelEntity.getId(), channelEntity);
                    }
                }
            }

            for (ResourceInfoEntity resourceInfoEntity : page) {
                ResourceChannelRepDto resourceChannelRepDto = new ResourceChannelRepDto();
                BeanUtils.copyProperties(resourceInfoEntity, resourceChannelRepDto);
                if (channelEntityMap.containsKey(resourceInfoEntity.getChannelId())) {
                    resourceChannelRepDto.setChannelName(channelEntityMap.get(resourceInfoEntity.getChannelId()).getName());
                }
                resultList.add(resourceChannelRepDto);
            }
            retObject.setList(resultList);
            retObject.setTotalCount((int) page.getTotal());
        } else {
            retObject.setList(new ArrayList<ResourceChannelRepDto>());
            retObject.setTotalCount(0);
        }
        return retObject;
    }

    @Override
    public PaginationBo<ResourceResVo> getResourcesPriceByPage(ResourceReqVo resReqVo, Integer pageIndex, Integer pageSize) {
        PaginationBo<ResourceResVo> retObject = new PaginationBo<>();
        Map<Integer, AccountEntity> mapAccount = new HashMap<>();
        Map<Integer, String> mapResAppType = new HashMap<>();

        ResourceInfoEntityExample resourceInfoEntityExample = null;
        try {
            resourceInfoEntityExample = buildResourceInfoExample(resReqVo);
        } catch (AdRuntimeException e) {
            return retObject;
        }
        resourceInfoEntityExample.setOrderByClause("res_updatedate desc");
        Page<ResourceInfoEntity> page = PageHelper.startPage(pageIndex, pageSize);
        resourceInfoMapper.selectByExample(resourceInfoEntityExample);

        //查询关联的app和account信息
        List<Integer> lisAllAccountId = new ArrayList<>();
        List<Integer> lisAllResId = new ArrayList<>();
        //定制轮播的资源位
        List<ResourceInfoEntity> lisAllCarouselRes = new ArrayList<>();

        for (ResourceInfoEntity resourceInfoEntity : page) {
            if (CollectionUtils.isEmpty(mapAccount)
                    && !lisAllAccountId.contains(resourceInfoEntity.getResAccountId().intValue())) {
                lisAllAccountId.add(resourceInfoEntity.getResAccountId().intValue());
            }
            if (CollectionUtils.isEmpty(mapResAppType) && !lisAllResId.contains(resourceInfoEntity.getResId().intValue())) {
                lisAllResId.add(resourceInfoEntity.getResId().intValue());
            }
            //如果是cpt定价的方式,就需要cpt最低价
            if (ResBidType.PMP_CODE.byteValue() == resourceInfoEntity.getResBidtype().byteValue()) {
                lisAllCarouselRes.add(resourceInfoEntity);
            }
        }

        if (CollectionUtils.isEmpty(mapAccount) && !CollectionUtils.isEmpty(lisAllAccountId)) {
            AccountEntityExample accountEntityExample = new AccountEntityExample();
            accountEntityExample.createCriteria().andAccountIdIn(lisAllAccountId);
            List<AccountEntity> lisAllAccount = accountEntityMapper.selectByExample(accountEntityExample);
            for (AccountEntity accountEntity : lisAllAccount) {
                if (!mapAccount.containsKey(accountEntity.getAccountId().intValue())) {
                    mapAccount.put(accountEntity.getAccountId(), accountEntity);
                }
            }
        }

        if (CollectionUtils.isEmpty(mapResAppType) && !CollectionUtils.isEmpty(lisAllResId)) {
            //查询AppResource
            AppResourceEntityExample appResourceEntityExample = new AppResourceEntityExample();
            appResourceEntityExample.createCriteria().andResIdIn(lisAllResId).andIsDeletedEqualTo("N");
            List<AppResourceEntity> lisAllAppResource = appResourceEntityMapper.selectByExample(appResourceEntityExample);
            //查询app
            List<Integer> lisAllAppId = new ArrayList<>();
            for (AppResourceEntity appResourceEntity : lisAllAppResource) {
                if (!lisAllAppId.contains(appResourceEntity.getAppId().intValue())) {
                    lisAllAppId.add(appResourceEntity.getAppId().intValue());
                }
            }
            AppEntityExample appEntityExample = new AppEntityExample();
            appEntityExample.createCriteria().andIdIn(lisAllAppId);
            List<AppEntity> lisAllApp = appEntityMapper.selectByExample(appEntityExample);

            Map<Integer, AppEntity> mapApp = new HashMap<>();
            for (AppEntity appEntity : lisAllApp) {
                if (!mapApp.containsKey(appEntity.getId().intValue())) {
                    mapApp.put(appEntity.getId().intValue(), appEntity);
                }
            }

            for (AppResourceEntity appResourceEntity : lisAllAppResource) {
                if (mapApp.containsKey(appResourceEntity.getAppId().intValue())) {
                    if (!mapResAppType.containsKey(appResourceEntity.getResId().intValue())) {
                        mapResAppType.put(appResourceEntity.getResId().intValue(),
                                mapApp.get(appResourceEntity.getAppId().intValue()).getAppType().toString());
                    } else {
                        mapResAppType.put(appResourceEntity.getResId().intValue(),
                                mapResAppType.get(appResourceEntity.getResId().intValue()).concat(
                                        "," + mapApp.get(appResourceEntity.getAppId().intValue()).getAppType().toString()));
                    }
                }
            }
        }

        //查询出所有手动定价的最低价
        Map<Integer, Long> mapAllMinCptPrice = getMinCptPrice(lisAllCarouselRes);

        List<ResourceResVo> lisResult = new ArrayList<>();
        for (ResourceInfoEntity resourceInfoEntity : page) {
            ResourceResVo resourceResVo = ResourceResVo.fromResourceInfo(resourceInfoEntity);
            //关联用户邮箱
            resourceResVo.setAccountEmail(mapAccount.get(resourceInfoEntity.getResAccountId().intValue()).getAccountEmail());
            //关联app类型
            resourceResVo.setResApptype(mapResAppType.get(resourceInfoEntity.getResId()));

            if (ResPositionType.CAROUSEL_CODE.byteValue() == resourceResVo.getResPositiontype().byteValue()) {
                List<ResourceSpecInfo> resSpecInfoList = resSpecInfoMapper.getResourceSpecsByResourceId(resourceResVo.getResId());
                if (null != resSpecInfoList && resSpecInfoList.size() > 0) {
                    resourceResVo
                            .setImageSize(resSpecInfoList.get(0).getResWidth() + "*" + resSpecInfoList.get(0).getResLength());
                }
            } else {
                resourceResVo.setImageSize("默认");
            }
            //如果是cpt定价的方式,就需要cpt最低价
            if (ResBidType.PMP_CODE.byteValue() == resourceInfoEntity.getResBidtype().byteValue()) {
                //关联cpt最小定价
                if (mapAllMinCptPrice.containsKey(resourceInfoEntity.getResId())) {
                    resourceResVo.setMinResCpt(mapAllMinCptPrice.get(resourceInfoEntity.getResId()));
                } else {
                    resourceResVo.setMinResCpt(RESOURCE_MIN_DEFAULT_MIN_PRICE);
                }
            }
            lisResult.add(resourceResVo);
        }

        retObject.setRows(lisResult);
        retObject.setTotal((int) page.getTotal());
        return retObject;
    }

    @Override
    public boolean saveResourceChannel(int resourceId, int channelId, String accountEmail) {
        if (resourceId > 0 && channelId >= 0) {
            ResourceInfoEntity resourceInfoEntity = resourceInfoEntityMapper.selectByPrimaryKey(resourceId);
            if (resourceInfoEntity == null) {
                throw new FfanadException("参数resId对应的值不存在");
            }
            if (resourceInfoEntity.getChannelId() == channelId) {
                return true;
            }
            ChannelEntity channelEntity = null;
            if (channelId != 0) {
                channelEntity = channelEntityMapper.selectByPrimaryKey(channelId);
                if (channelEntity == null) {
                    throw new FfanadException("参数channelId对应的值不存在");
                }
            }
            ResourceInfoEntity saveEntity = new ResourceInfoEntity();
            saveEntity.setResId(resourceId);
            saveEntity.setChannelId(channelId);
            boolean result = resourceInfoEntityMapper.updateByPrimaryKeySelective(saveEntity) > 0;
            if (result) {
                try {
                    String originChannelName = "";
                    if (resourceInfoEntity.getChannelId() != null && resourceInfoEntity.getChannelId() != 0) {
                        ChannelEntity originChannel = channelEntityMapper.selectByPrimaryKey(resourceInfoEntity.getChannelId());
                        if (originChannel != null) {
                            originChannelName = originChannel.getName();
                        }
                    }
                    operationLogService.addOperationLog(OperationLogPage.PAGE_CHANNEL_MANAGE,
                            OperationLogOperation.CHANNEL_MANAGE_UPDATE, accountEmail, OperationLogAction.CHANNEL_MANAGE_UPDATE,
                            resourceId + "", String.format("【%s】所属原频道：【%s】，修改为：【%s】", resourceInfoEntity.getResName(),
                                    originChannelName, channelEntity == null ? "未归档" : channelEntity.getName()));
                } catch (Exception e) {
                    logger.error("添加日志失败:" + e.getMessage(), e);
                }
            }
            return result;
        }
        return false;
    }

    /**
     * 生成查询条件
     *
     * @param resReqVo
     * @return
     */
    private ResourceInfoEntityExample buildResourceInfoExample(ResourceReqVo resReqVo) {
        ResourceInfoEntityExample resourceInfoEntityExample = new ResourceInfoEntityExample();
        if (resReqVo != null) {
            //用户条件过滤
            AccountEntityExample accountEntityExample = new AccountEntityExample();
            AccountEntityExample.Criteria accountCriteria = accountEntityExample.createCriteria();
            if (StringUtils.isNotEmpty(resReqVo.getResUserEmail())) {
                accountCriteria.andAccountEmailLike("%" + resReqVo.getResUserEmail() + "%");
            }

            List<AccountEntity> lisAccount = new ArrayList<>();
            if (accountCriteria.getAllCriteria().size() > 0) {
                lisAccount = accountEntityMapper.selectByExample(accountEntityExample);
                AdAsserts.notEmpty(lisAccount, "没有该用户的数据");
            }

            //app条件过滤
            AppEntityExample appEntityExample = new AppEntityExample();
            AppEntityExample.Criteria appCriteria = appEntityExample.createCriteria();
            if (resReqVo.getResApptype() != null) {
                appCriteria.andAppTypeEqualTo(resReqVo.getResApptype().byteValue());
            }
            List<Integer> lisResourceId = new ArrayList<>();
            if (appCriteria.getAllCriteria().size() > 0) {
                List<AppEntity> lisApp = appEntityMapper.selectByExample(appEntityExample);
                AdAsserts.notEmpty(lisApp, "没有改app的资源位");
                //查处对应的resId
                List<Integer> lisAppId = new ArrayList<>();
                Map<Integer, AppEntity> mapApp = new HashMap<>();
                for (AppEntity appEntity : lisApp) {
                    if (!lisAppId.contains(appEntity.getId().intValue())) {
                        lisAppId.add(appEntity.getId().intValue());
                    }
                    if (!mapApp.containsKey(appEntity.getId().intValue())) {
                        mapApp.put(appEntity.getId().intValue(), appEntity);
                    }
                }
                AppResourceEntityExample appResourceEntityExample = new AppResourceEntityExample();
                appResourceEntityExample.createCriteria().andIsDeletedEqualTo("N").andAppIdIn(lisAppId);
                List<AppResourceEntity> lisAppResource = appResourceEntityMapper.selectByExample(appResourceEntityExample);
                if (!CollectionUtils.isEmpty(lisAppResource)) {
                    for (AppResourceEntity appResourceEntity : lisAppResource) {
                        if (!lisResourceId.contains(appResourceEntity.getResId().intValue())) {
                            lisResourceId.add(appResourceEntity.getResId().intValue());
                        }
                    }
                }

            }

            ResourceInfoEntityExample.Criteria criteria = resourceInfoEntityExample.createCriteria();
            if (resReqVo.getResId() != null) {
                criteria.andResIdEqualTo(resReqVo.getResId());
            }
            if (StringUtils.isNotEmpty(resReqVo.getResName())) {
                criteria.andResNameLike("%" + resReqVo.getResName() + "%");
            }
            if (StringUtils.isNotEmpty(resReqVo.getAlias())) {
                criteria.andAliasEqualTo(resReqVo.getAlias());
            }
            if (resReqVo.getResStatus() != null) {
                criteria.andResStatusEqualTo(resReqVo.getResStatus().byteValue());
            }
            if (resReqVo.getResPositiontype() != null) {
                criteria.andResPositiontypeEqualTo(resReqVo.getResPositiontype().byteValue());
            }
            if (resReqVo.getResTerminaltype() != null) {
                criteria.andResTerminaltypeEqualTo(resReqVo.getResTerminaltype().byteValue());
            }
            if (resReqVo.getStartTime() != null) {
                criteria.andResCreatedateGreaterThan(resReqVo.getStartTime());
            }
            if (resReqVo.getEndTime() != null) {
                criteria.andResCreatedateLessThan(resReqVo.getEndTime());
            }
            if (StringUtils.isNotEmpty(resReqVo.getEnableReservePrice())) {
                criteria.andEnableReservePriceEqualTo(resReqVo.getEnableReservePrice());
            }
            if (resReqVo.getResUsertype() != null) {
                criteria.andResUsertypeEqualTo(resReqVo.getResUsertype().byteValue());
            }
            if (resReqVo.getResAreaOrient() != null) {
                criteria.andResAreaOrientEqualTo(resReqVo.getResAreaOrient());
            }
            if (accountCriteria.getAllCriteria().size() > 0 && !CollectionUtils.isEmpty(lisAccount)) {
                List<Integer> lisAccountId = new ArrayList<>();
                for (AccountEntity accountEntity : lisAccount) {
                    if (!lisAccountId.contains(accountEntity.getAccountId().intValue())) {
                        lisAccountId.add(accountEntity.getAccountId().intValue());
                    }
                }
                criteria.andResAccountIdIn(lisAccountId);
            }
            if (appCriteria.getAllCriteria().size() > 0 && !CollectionUtils.isEmpty(lisResourceId)) {
                criteria.andResIdIn(lisResourceId);
            }
        }
        return resourceInfoEntityExample;
    }

    /**
     * 从数据库获取cpt最低价
     *
     * @param lisCarouselRes
     * @return
     */
    private Map<Integer, Long> getMinCptPriceFromDb(List<ResourceInfoEntity> lisCarouselRes) {
        //优化策略:先查询出资源的手动定价城市,再查只需判断一条就可以了
        Map<Integer, Long> result = new HashMap<>();
        if (!CollectionUtils.isEmpty(lisCarouselRes)) {
            for (ResourceInfoEntity resourceInfoEntity : lisCarouselRes) {
                //所有的手动定价
                ResourcePriceInfoEntityExample resourcePriceInfoExample = new ResourcePriceInfoEntityExample();
                resourcePriceInfoExample.createCriteria().andResIdEqualTo(resourceInfoEntity.getResId())
                        .andFrameIndexEqualTo((byte) 1); //用第一屏来处理
                List<ResourcePriceInfoEntity> priceList = resPriceInfoMapper.selectByExample(resourcePriceInfoExample);

                Long minPrice = null;
                if (resourceInfoEntity.getResAreaOrient() == ResAreaOrient.CITY) {
                    //查询所有的城市, 定价城市的数目只能是小于等于城市的总数目,如果是等于就不用比较保留价
                    List<RegionVo> lisAllCity = regionService.queryAllCity();
                    if (lisAllCity.size() > priceList.size()) {
                        minPrice = resourceInfoEntity.getReservePrice();
                    }
                    for (ResourcePriceInfoEntity resourcePriceInfo : priceList) {
                        if (resourcePriceInfo.getPrice() < minPrice) {
                            minPrice = resourcePriceInfo.getPrice();
                        }
                    }
                } else if (resourceInfoEntity.getResAreaOrient() == ResAreaOrient.PLAZA) {
                    //查询所有广场
                    List<PlazaVo> lisAllPlaza = regionService.queryAllPlaza();
                    if (lisAllPlaza.size() > priceList.size()) {
                        minPrice = resourceInfoEntity.getReservePrice();
                    }
                    for (ResourcePriceInfoEntity resourcePriceInfo : priceList) {
                        if (resourcePriceInfo.getPrice() < minPrice) {
                            minPrice = resourcePriceInfo.getPrice();
                        }
                    }
                } else if (resourceInfoEntity.getResAreaOrient() == ResAreaOrient.STORE) {
                    //查询出是否可投的设置
                    ResourceRegionExample resourceRegionExample = new ResourceRegionExample();
                    resourceRegionExample.createCriteria().andResIdEqualTo(resourceInfoEntity.getResId())
                            .andThrowableEqualTo("N"); //查询资源的不可投放情况
                    List<ResourceRegion> lisAllResourceRegion = resourceRegionMapper.selectByExample(resourceRegionExample);
                    Map<Integer, ResourceRegion> mapAllResourceRegion = new HashMap<>(); //不可投的门店
                    for (ResourceRegion resourceRegion : lisAllResourceRegion) {
                        if (!mapAllResourceRegion.containsKey(resourceRegion.getStoreId().intValue())) {
                            mapAllResourceRegion.put(resourceRegion.getStoreId().intValue(), resourceRegion);
                        }
                    }
                    List<StoreVo> lisAllStore = regionService.queryAllStore();
                    if (lisAllStore.size() > priceList.size() && "Y".equals(resourceInfoEntity.getEnableReservePrice())) {
                        minPrice = resourceInfoEntity.getReservePrice();
                    }
                    for (ResourcePriceInfoEntity resourcePriceInfo : priceList) {
                        if ((minPrice == null || resourcePriceInfo.getPrice() < minPrice)
                                && !mapAllResourceRegion.containsKey(resourcePriceInfo.getStoreId())) {
                            minPrice = resourcePriceInfo.getPrice();
                        }
                    }
                }
                if (minPrice == null) {
                    //默认使用100
                    minPrice = RESOURCE_MIN_DEFAULT_MIN_PRICE;
                }
                result.put(resourceInfoEntity.getResId(), minPrice);
            }
        }
        return result;
    }

    /**
     * 查询cpt定价方式最低价 100/地
     *
     * @param lisCarouselRes 定制轮播列表
     * @return
     */
    private Map<Integer, Long> getMinCptPrice(List<ResourceInfoEntity> lisCarouselRes) {
        //先从redis里面获取最低价
        Map<Integer, Long> mapResultCptMinPrice = new HashMap<>();

        //如果redis里面没有,再从db里面获取,并保持到redis
        List<ResourceInfoEntity> lisDbCarouselRes = new ArrayList<>(); // 需要从db中读取的资源
        for (ResourceInfoEntity resourceInfoEntity : lisCarouselRes) {
            String cacheValue = RedisUtil.getString(RedisCacheConstants.RESOURCE_CPT_MIN_PRICE_CACHE_KEY_PREFIX
                    + RedisCacheConstants.CACHE_KEY_SPLIT + resourceInfoEntity.getResId());
            if (!StringUtils.isEmpty(cacheValue)) {
                logger.debug(String.format("从缓存中读取到资源:%dcpt最低价为%s", resourceInfoEntity.getResId(), cacheValue));
                mapResultCptMinPrice.put(resourceInfoEntity.getResId(), Long.valueOf(cacheValue));
            } else {
                lisDbCarouselRes.add(resourceInfoEntity);
            }
        }
        if (!CollectionUtils.isEmpty(lisDbCarouselRes)) {
            Map<Integer, Long> mapDbCptMinPrice = getMinCptPriceFromDb(lisDbCarouselRes);
            if (!CollectionUtils.isEmpty(mapDbCptMinPrice)) {
                mapResultCptMinPrice.putAll(mapDbCptMinPrice);

                //保存到redis
                for (ResourceInfoEntity resourceInfoEntity : lisDbCarouselRes) {
                    logger.debug(String.format("将%d的cpt最低价%d保存到redis", resourceInfoEntity.getResId(),
                            mapDbCptMinPrice.get(resourceInfoEntity.getResId())));
                    RedisUtil
                            .setString(
                                    RedisCacheConstants.RESOURCE_CPT_MIN_PRICE_CACHE_KEY_PREFIX
                                            + RedisCacheConstants.CACHE_KEY_SPLIT + resourceInfoEntity.getResId(),
                                    mapDbCptMinPrice.get(resourceInfoEntity.getResId()) + "");
                }
            }
        }
        return mapResultCptMinPrice;
    }

    /**
     * 生成最低价的redis缓存
     *
     * @param lisCarouselRes
     */
    @Override
    public void buildCptMinPriceCache(List<ResourceInfoEntity> lisCarouselRes) {
        if (!CollectionUtils.isEmpty(lisCarouselRes)) {
            Map<Integer, Long> mapDbCptMinPrice = getMinCptPriceFromDb(lisCarouselRes);
            for (ResourceInfoEntity resourceInfoEntity : lisCarouselRes) {
                Long minPrice = RESOURCE_MIN_DEFAULT_MIN_PRICE;
                if (mapDbCptMinPrice.containsKey(resourceInfoEntity.getResId())) {
                    minPrice = mapDbCptMinPrice.get(resourceInfoEntity.getResId());
                }

                logger.debug(String.format("强制更新%d的cpt最低价缓存%d", resourceInfoEntity.getResId(),
                        mapDbCptMinPrice.get(resourceInfoEntity.getResId())));
                RedisUtil.setString(RedisCacheConstants.RESOURCE_CPT_MIN_PRICE_CACHE_KEY_PREFIX
                        + RedisCacheConstants.CACHE_KEY_SPLIT + resourceInfoEntity.getResId(), minPrice + "");
            }
        }
    }
}
