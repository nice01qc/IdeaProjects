package com.wanda.ffanad.crm.services;

import com.wanda.ffanad.common.redis.RedisUtil;
import com.wanda.ffanad.core.common.redis.RedisCacheConstants;
import com.wanda.ffanad.core.constants.ResAreaOrient;
import com.wanda.ffanad.core.domains.vo.req.RecommendationRTBPriceReqVo;
import com.wanda.ffanad.core.domains.vo.req.ResourceMonopolyPriceQueryVo;
import com.wanda.ffanad.core.domains.vo.req.ResourceMonopolyPriceReqVo;
import com.wanda.ffanad.core.domains.vo.req.ResourceMonopolyPriceTotalVo;
import com.wanda.ffanad.core.domains.vo.res.RecommendationRTBPriceResVo;
import com.wanda.ffanad.core.domains.vo.res.ResourceMonopolyPriceResVo;
import com.wanda.ffanad.core.domains.vo.res.ThrowableRegionResVo;
import com.wanda.ffanad.core.enums.RegionTypeEnum;
import com.wanda.ffanad.core.services.ResourcePriceService;
import com.wanda.ffanad.core.services.api.RegionService;
import com.wanda.ffanad.core.vo.PlazaVo;
import com.wanda.ffanad.core.vo.RegionVo;
import com.wanda.ffanad.crm.CRMApplication;
import com.wanda.ffanad.crm.service.CrmResourcePriceService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CRMApplication.class)
@WebAppConfiguration
public class ResourcePriceServiceTest {
    private Logger          logger = LoggerFactory.getLogger(ResourcePriceServiceTest.class);

    @Autowired
    ResourcePriceService    resPriceService;

    @Autowired
    CrmResourcePriceService crmResourcePriceService;

    @Autowired
    RegionService           regService;

    private String objectToString(Object obj) {
        return ToStringBuilder.reflectionToString(obj, ToStringStyle.DEFAULT_STYLE);
    }

    /**
     * 初始化默认价到redis
     */
    public void initDefaultPrice() {
        //查看logger的值，检查redis的加载情况
        //        resPriceService.initDefaultPriceCache();
    }

    /**
     * 检查广场数据获取接口
     */
    @Test
    public void queryAllPlaza() {
        List<PlazaVo> lisPlazaVo = regService.queryPlazaByRegionType(RegionTypeEnum.REGION, 1);
        if (CollectionUtils.isNotEmpty(lisPlazaVo)) {
            logger.info("all plaza size:" + lisPlazaVo.size());
            for (PlazaVo plazaVo : lisPlazaVo) {
                logger.info("all plaza:" + objectToString(plazaVo));
            }
        } else {
            logger.info("请检查，广场数据为空，all plaza size:" + 0);
            assert CollectionUtils.isNotEmpty(lisPlazaVo);
        }
    }

    /**
     *
     */
    @Test
    public void queryCityInProvince() {
        RegionVo regionVo = regService.queryCountyById(131003);
        logger.info("region info for id 131003:" + objectToString(regionVo));
    }

    /**
     * 查询城市/广场的定价
     */
    @Test
    public void queryPrice() {
        ResourceMonopolyPriceQueryVo queryVo = new ResourceMonopolyPriceQueryVo();
        queryVo.setResId(27);
        queryVo.setRegionId(1L);
        ResourceMonopolyPriceTotalVo priceVo = resPriceService.queryTotalPrice(queryVo);
        logger.info("result:" + objectToString(priceVo) + " for " + objectToString(queryVo));
    }

    @Test
    public void queryRecommendedRTBPrice() {
        RecommendationRTBPriceReqVo queryVo = new RecommendationRTBPriceReqVo();
        //        queryVo.setResId(6);
        //        queryVo.setResAppType(1);
        //        queryVo.setResPositionType(0);
        //        queryVo.setResTerminalType(0);
        //        queryVo.setResUserType(0);

        RecommendationRTBPriceResVo priceVo = resPriceService.queryRecommendedRTBPrice(queryVo);
        logger.info("result:" + objectToString(priceVo) + " for " + objectToString(queryVo));
    }

    @Test
    public void getResourceMonopolyPrice() {
        ResourceMonopolyPriceReqVo resourceMonopolyPriceReqVo = new ResourceMonopolyPriceReqVo();
        resourceMonopolyPriceReqVo.setResId(10065);
        resourceMonopolyPriceReqVo.setResAreaOrient(1);
        ResourceMonopolyPriceResVo result = crmResourcePriceService.getResourceMonopolyPrice(resourceMonopolyPriceReqVo);
        logger.info("定价数量" + result.getPriceList().size());
    }

    /**
     * 查询克投放的门店 带价格信息
     */
    @Test
    public void queryThrowableStore() {
        List<ThrowableRegionResVo> lis = resPriceService.queryThrowableStore(10119);
        for (ThrowableRegionResVo throwableRegionResVo : lis) {
            logger.info(throwableRegionResVo.getStoreName() + "  " + throwableRegionResVo.getPlazaName() + " "
                    + throwableRegionResVo.getPrice());
        }
    }

    /**
     * 通过list<storeId>查询其中克投放的门店定价信息
     */
    @Test
    public void queryThrowableStorePriceByStoreIds() {
        List<Integer> lisSotreId = new ArrayList<Integer>();
        lisSotreId.add(2052507);
        lisSotreId.add(2052553);
        lisSotreId.add(2052713);
        lisSotreId.add(2052716);
        lisSotreId.add(2052718);
        lisSotreId.add(2052719);
        lisSotreId.add(9062963);//一个未手动定价的门店，也为开启保留价
        lisSotreId.add(1212121);//一个不存在的门店
        List<ThrowableRegionResVo> lis = resPriceService.queryThrowableStorePriceByStoreIds(10145, lisSotreId);
        for (ThrowableRegionResVo throwableRegionResVo : lis) {
            logger.info(objectToString(throwableRegionResVo));
        }
        assert lis.size() == 6;
    }

    public void testRedisCacheExpire() {
        Integer testCityId = 0;
        Integer testPlazaNumber = 100;
        String plazaNumberCachedKey = RedisCacheConstants.ResourcePriceCache.RESOURCE_REGION_PLAZA_NUM_PREFIX;
        RedisUtil.hset(plazaNumberCachedKey, testCityId.toString(), testPlazaNumber.toString());
        String cachedNumber = RedisUtil.hget(plazaNumberCachedKey, testCityId.toString());
        org.junit.Assert.assertEquals(cachedNumber, testPlazaNumber.toString());

        long endTime = System.currentTimeMillis() / 1000 + 5;//5s后过期
        RedisUtil.expireAt(plazaNumberCachedKey, endTime);
        try {
            Thread.sleep(6000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //long dendTime = DateTimeUtil.getEndTimeOfCurrentDay();
        cachedNumber = RedisUtil.hget(plazaNumberCachedKey, testCityId.toString());
        org.junit.Assert.assertNotEquals(cachedNumber, testPlazaNumber.toString());

    }

    public void deleteRedisCacheKey() {
        String cityKey = RedisCacheConstants.ResourcePriceCache.DEFAULT_RESOURCE_PRICE_KEY_PREFIX
                + RedisCacheConstants.CACHE_KEY_SPLIT + ResAreaOrient.CITY;
        String plazaKey = RedisCacheConstants.ResourcePriceCache.DEFAULT_RESOURCE_PRICE_KEY_PREFIX
                + RedisCacheConstants.CACHE_KEY_SPLIT + ResAreaOrient.SQUARE;

        RedisUtil.del(new String[] { cityKey, plazaKey, RedisCacheConstants.ResourcePriceCache.RESOURCE_REGION_CITY_NUM_PREFIX,
                RedisCacheConstants.ResourcePriceCache.RESOURCE_REGION_PLAZA_NUM_PREFIX });
    }
}
