package com.wanda.ffanad.crm.service;

import java.util.List;

import com.wanda.ffanad.base.common.PageBo;
import com.wanda.ffanad.base.dal.entities.ResourceInfoEntity;
import com.wanda.ffanad.core.common.PaginationBo;
import com.wanda.ffanad.core.domains.vo.req.ResourceReqVo;
import com.wanda.ffanad.core.domains.vo.res.ResourceResVo;
import com.wanda.ffanad.crm.dto.req.ResourceChannelPageReqDto;
import com.wanda.ffanad.crm.dto.resp.ResourceChannelRepDto;

/**
 * Created by kevin on 16/11/17.
 */
public interface CrmResourceService {

    /**
     * 资源频道的分页
     * 
     * @param resourceChannelPageReqDto
     * @return
     */
    PageBo<ResourceChannelRepDto> getResourcesByPage(ResourceChannelPageReqDto resourceChannelPageReqDto);

    /**
     * 资源定价分页
     * 
     * @param resReqVo
     * @param pageIndex
     * @param pageSize
     * @return
     */
    PaginationBo<ResourceResVo> getResourcesPriceByPage(ResourceReqVo resReqVo, Integer pageIndex, Integer pageSize);

    /**
     * 保存频道关系
     * 
     * @param resourceId
     * @param channelId
     * @return
     */
    boolean saveResourceChannel(int resourceId, int channelId, String accountEmail);

    /**
     * 强制生成cpt最低价的redis缓存
     * 
     * @param lisCarouselRes
     */
    void buildCptMinPriceCache(List<ResourceInfoEntity> lisCarouselRes);
}
