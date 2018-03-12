package com.wanda.ffanad.crm.service.impl;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.wanda.ffanad.base.dal.entities.ResourceInfoEntity;
import com.wanda.ffanad.base.dal.entities.ResourceInfoEntityExample;
import com.wanda.ffanad.base.dal.mappers.ResourceInfoEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wanda.ffanad.base.common.PageBo;
import com.wanda.ffanad.base.constants.OperationLogOperation;
import com.wanda.ffanad.base.constants.OperationLogPage;
import com.wanda.ffanad.base.dal.entities.ChannelEntity;
import com.wanda.ffanad.base.dal.entities.ChannelEntityExample;
import com.wanda.ffanad.base.dal.mappers.ChannelEntityMapper;
import com.wanda.ffanad.core.services.OperationLogService;
import com.wanda.ffanad.crm.dto.req.ChannelSaveReqDto;
import com.wanda.ffanad.crm.service.ChannelService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kevin on 16/11/17.
 */
@Service
public class ChannelServiceImpl implements ChannelService {
    private static final Logger      logger = LoggerFactory.getLogger(ChannelServiceImpl.class);

    @Autowired
    private ResourceInfoEntityMapper resourceInfoEntityMapper;

    @Autowired
    ChannelEntityMapper              channelEntityMapper;

    @Autowired
    OperationLogService              operationLogService;

    @Override
    public List<ChannelEntity> getAllChannel() {
        ChannelEntityExample channelEntityExample = new ChannelEntityExample();
        channelEntityExample.setOrderByClause("update_time desc");
        return channelEntityMapper.selectByExample(channelEntityExample);
    }

    @Override
    public PageBo<ChannelEntity> getChannelPage(@NotNull Integer pageNumber, @NotNull Integer pageSize) {
        PageBo<ChannelEntity> retObject = new PageBo<>();
        ChannelEntityExample channelEntityExample = new ChannelEntityExample();
        channelEntityExample.setOrderByClause("update_time desc");
        Page<ChannelEntity> page = PageHelper.startPage(pageNumber, pageSize);
        channelEntityMapper.selectByExample(channelEntityExample);
        retObject.setList(page.getResult());
        retObject.setTotalCount((int) page.getTotal());
        return retObject;
    }

    @Override
    public boolean save(ChannelSaveReqDto channelSaveReqDto, String accountEmail) {
        ChannelEntity channelEntity = new ChannelEntity();
        BeanUtils.copyProperties(channelSaveReqDto, channelEntity);
        boolean result;
        StringBuilder sbLogMsg = new StringBuilder();
        if (channelSaveReqDto.getId() == null) {
            result = channelEntityMapper.insertSelective(channelEntity) > 0;
            if (result) {
                sbLogMsg.append(String.format("添加频道【%s】", channelSaveReqDto.getName()));
            }
        } else {
            ChannelEntityExample channelEntityExample = new ChannelEntityExample();
            channelEntityExample.createCriteria().andIsDeletedEqualTo("N").andIdEqualTo(channelSaveReqDto.getId());
            result = channelEntityMapper.updateByExampleSelective(channelEntity, channelEntityExample) > 0;
            if (result) {
                sbLogMsg.append(String.format("修改频道【%s】", channelSaveReqDto.getName()));
            }
        }
        try {
            operationLogService.addOperationLog(OperationLogPage.PAGE_ADD_CHANNEL, OperationLogOperation.CHANNEL_ADD,
                    accountEmail, OperationLogOperation.CHANNEL_ADD, channelSaveReqDto.getName(), sbLogMsg.toString());
        } catch (Exception e) {
            logger.error("添加日志失败:" + e.getMessage(), e);
        }
        return result;
    }

    @Override
    @Transactional
    public boolean delete(Integer channelId, String accountEmail) {
        if (channelId != null && channelId > 0) {
            ChannelEntity channelEntity = channelEntityMapper.selectByPrimaryKey(channelId);
            if (channelEntity != null) {
                ResourceInfoEntity resourceInfoEntity = new ResourceInfoEntity();
                resourceInfoEntity.setChannelId(0);
                ResourceInfoEntityExample resourceInfoEntityExample = new ResourceInfoEntityExample();
                resourceInfoEntityExample.createCriteria().andChannelIdEqualTo(channelId);
                resourceInfoEntityMapper.updateByExampleSelective(resourceInfoEntity, resourceInfoEntityExample);
                boolean result = channelEntityMapper.deleteByPrimaryKey(channelId) > 0;
                if (result) {
                    try {
                        operationLogService.addOperationLog(OperationLogPage.PAGE_ADD_CHANNEL,
                                OperationLogOperation.CHANNEL_DELETE, accountEmail, OperationLogOperation.CHANNEL_DELETE,
                                channelEntity.getName(), String.format("删除频道【%s】", channelEntity.getName()));
                    } catch (Exception e) {
                        logger.error("添加日志失败:" + e.getMessage(), e);
                    }
                }
                return result;
            }
        }
        return false;
    }
}
