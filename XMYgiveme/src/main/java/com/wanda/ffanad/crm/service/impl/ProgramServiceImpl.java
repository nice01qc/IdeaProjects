package com.wanda.ffanad.crm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.wanda.ffanad.base.dal.entities.ProgramTagEntity;
import com.wanda.ffanad.core.bo.PromoteBo;
import com.wanda.ffanad.core.services.promote.TFfadPromoteService;
import com.wanda.ffanad.crm.dto.resp.ProgramDetailDto;
import com.wanda.ffanad.crm.integration.userprofile.resp.TagDto;
import com.wanda.ffanad.crm.service.ProgramService;
import com.wanda.ffanad.crm.service.TagService;

/**
 * 投放 类PromoteServiceImpl.java的实现描述：TODO 类实现描述
 * 
 * @author shiyu 2016年8月1日 下午2:57:51
 */
@Service
public class ProgramServiceImpl implements ProgramService {

    @Autowired
    private TFfadPromoteService promoteService;
    
    @Autowired
    private TagService tagService;

    public ProgramDetailDto queryProgramDetail(Long programId) {
    	 PromoteBo promoteBo = promoteService.queryPromoteDetail(programId);
         ProgramDetailDto programDetail = new ProgramDetailDto();
         BeanUtils.copyProperties(promoteBo, programDetail);
         
         if (promoteBo != null && ! CollectionUtils.isEmpty(promoteBo.getProgramTags())) {
        	 List<TagDto> tags = new ArrayList<>(promoteBo.getProgramTags().size());
         	for (ProgramTagEntity entity : promoteBo.getProgramTags()) {
         		TagDto tag = tagService.queryTagByName(entity.getTagName());
         		
         		if (tag.getId() != null) {
             		tags.add(tag);
         		}
         	}
         	
         	programDetail.setTags(tags);
         }
         
         return programDetail;
    }
    
}
