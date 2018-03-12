package com.wanda.ffanad.crm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wanda.ffanad.base.dal.entities.CreationInfoEntity;
import com.wanda.ffanad.base.dal.mappers.CreationInfoEntityMapper;
import com.wanda.ffanad.core.domains.Link;
import com.wanda.ffanad.core.mappers.CreationInfoPicMapper;
import com.wanda.ffanad.core.mappers.LinkMapper;
import com.wanda.ffanad.crm.dto.resp.CreativeRespDto;
import com.wanda.ffanad.crm.service.CreationCrmService;

/**
 * Created by liujie136 on 17/5/5.
 */
@Service
public class CreationCrmServiceImpl implements CreationCrmService {

    private Logger                logger = LoggerFactory.getLogger(com.wanda.ffanad.core.services.impl.CreationServiceImpl.class);
    @Autowired
    CreationInfoEntityMapper      creationInfoEntityMapper;

    @Autowired
    private CreationInfoPicMapper creationPicMapper;

    @Autowired
    private LinkMapper            linkMapper;

    @Override
    public CreativeRespDto getCreationById(Integer creationId) {
        CreationInfoEntity creationInfoEntity = creationInfoEntityMapper.selectByPrimaryKey(creationId);
        if (creationInfoEntity != null) {
            CreativeRespDto creativeRespDto = new CreativeRespDto();
            BeanUtils.copyProperties(creationInfoEntity, creativeRespDto);

            creativeRespDto.setCreationPicList(creationPicMapper.selectByCreationId(creativeRespDto.getId()));
            try {
                creativeRespDto.setCreationLinkName("");
                if (creativeRespDto.getCreationLinkType() != null) {
                    Link creationLink = linkMapper.selectByPrimaryKey(new Integer(creativeRespDto.getCreationLinkType()));
                    if (null != creationLink) {
                        creativeRespDto.setCreationLinkName(creationLink.getName());
                    }
                }
                return creativeRespDto;
            } catch (Exception ex) {
                logger.error("Failed to find creation link name for type:" + creativeRespDto.getCreationLinkType());
            }
        }
        return null;
    }
}
