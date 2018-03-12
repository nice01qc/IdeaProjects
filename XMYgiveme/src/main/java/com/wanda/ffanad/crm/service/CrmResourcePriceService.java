package com.wanda.ffanad.crm.service;

import com.github.pagehelper.Page;
import com.wanda.ffanad.core.common.RestResult;
import com.wanda.ffanad.core.domains.vo.req.ResourceMonopolyPriceReqVo;
import com.wanda.ffanad.core.domains.vo.req.ResourceMonopolyPriceUpdateReqVo;
import com.wanda.ffanad.core.domains.vo.req.ResourceRTBPriceReqVo;
import com.wanda.ffanad.core.domains.vo.res.ResourceMonopolyPriceResVo;
import com.wanda.ffanad.core.domains.vo.res.ResourcePriceInfoResVo;
import com.wanda.ffanad.core.domains.vo.res.ResourceRTBPriceResVo;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by liujie136 on 17/1/22.
 */
public interface CrmResourcePriceService {

    /**
     * 更新资源位RTB价格信息
     *
     * @param resRTBPrice
     * @return
     */
    ResourceRTBPriceResVo updateResourceRTBPrice(ResourceRTBPriceReqVo resRTBPrice, HttpSession session);

    /**
     * 更新资源位独占价格信息
     *
     * @param resMonopolyPrice
     * @return
     */
    ResourceMonopolyPriceResVo updateResourceMonopolyPrice(ResourceMonopolyPriceUpdateReqVo resMonopolyPrice,
                                                           HttpSession session);

    /**
     * 获取资源位独占价格信息
     *
     * @param resMonopolyPrice
     * @return
     */
    ResourceMonopolyPriceResVo getResourceMonopolyPrice(ResourceMonopolyPriceReqVo resMonopolyPrice);

    /**
     * 查询出资源的所有门店的信息，并且带上价格
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<ResourcePriceInfoResVo> queryStoreWithPriceInPage(@NotNull Integer resId, int pageNum, int pageSize);

    /**
     * 将通过excel上传的门店定价数据保存到缓存数据库中
     *
     * @param resId 资源Id
     * @param accountId 用户Id
     * @param lisResouceInfo 定价数据
     * @return
     */
    RestResult buildResourcePriceInfoToCache(Integer resId, Integer accountId, List<ResourcePriceInfoResVo> lisResouceInfo);

    /**
     * 从缓存中获取临时的门店定价数据
     *
     * @param resId 资源Id
     * @param accountId 用户Id
     * @param pageNum 页数
     * @param pageSize 每页数量
     * @return
     */
    Page<ResourcePriceInfoResVo> getResourcePriceInfoFromCache(Integer resId, Integer accountId, int pageNum, int pageSize);

    /**
     * 保存缓存中的临时门店定价数据到数据库中
     *
     * @param resId
     * @param accountId
     * @return
     */
    RestResult saveResourcePriceInfoFromCache(Integer resId, Integer accountId, HttpSession session);
}
