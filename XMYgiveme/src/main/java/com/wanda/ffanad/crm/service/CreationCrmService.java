package com.wanda.ffanad.crm.service;

import com.wanda.ffanad.crm.dto.resp.CreativeRespDto;

/**
 * Created by liujie136 on 17/5/5.
 */
public interface CreationCrmService {

    /**
     * 通过id获取创意详情
     * 
     * @param creationId
     * @return
     */
    CreativeRespDto getCreationById(Integer creationId);
}
