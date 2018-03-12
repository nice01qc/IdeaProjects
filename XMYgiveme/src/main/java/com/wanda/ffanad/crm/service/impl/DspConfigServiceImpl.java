package com.wanda.ffanad.crm.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
import com.wanda.ffanad.base.constants.OperationLogOperation;
import com.wanda.ffanad.base.constants.OperationLogPage;
import com.wanda.ffanad.base.constants.ResBidType;
import com.wanda.ffanad.base.dal.entities.AccountEntity;
import com.wanda.ffanad.base.dal.entities.AccountEntityExample;
import com.wanda.ffanad.base.dal.entities.AppEntity;
import com.wanda.ffanad.base.dal.entities.AppEntityExample;
import com.wanda.ffanad.base.dal.entities.AppResourceEntity;
import com.wanda.ffanad.base.dal.entities.AppResourceEntityExample;
import com.wanda.ffanad.base.dal.entities.ResourceDspConfigEntity;
import com.wanda.ffanad.base.dal.entities.ResourceDspConfigEntityExample;
import com.wanda.ffanad.base.dal.entities.ResourceInfoEntity;
import com.wanda.ffanad.base.dal.entities.ResourceInfoEntityExample;
import com.wanda.ffanad.base.dal.entities.TFfanadAdxDspEntity;
import com.wanda.ffanad.base.dal.entities.TFfanadAdxDspEntityExample;
import com.wanda.ffanad.base.dal.mappers.AccountEntityMapper;
import com.wanda.ffanad.base.dal.mappers.AppEntityMapper;
import com.wanda.ffanad.base.dal.mappers.AppResourceEntityMapper;
import com.wanda.ffanad.base.dal.mappers.ResourceDspConfigEntityMapper;
import com.wanda.ffanad.base.dal.mappers.ResourceInfoEntityMapper;
import com.wanda.ffanad.base.dal.mappers.TFfanadAdxDspEntityMapper;
import com.wanda.ffanad.base.enums.ResourceStatusEnum;
import com.wanda.ffanad.base.error.AdAsserts;
import com.wanda.ffanad.base.error.AdRuntimeException;
import com.wanda.ffanad.base.error.FfanadException;
import com.wanda.ffanad.common.utils.CurrencyUtils;
import com.wanda.ffanad.core.common.PaginationBo;
import com.wanda.ffanad.core.services.OperationLogService;
import com.wanda.ffanad.crm.dto.req.DspConfigPageReqDto;
import com.wanda.ffanad.crm.dto.req.DspConfigUpdateReqDto;
import com.wanda.ffanad.crm.dto.resp.DspConfigRespDto;
import com.wanda.ffanad.crm.service.DspConfigService;

/**
 * Created by kevin on 16/9/20.
 */
@Service
public class DspConfigServiceImpl implements DspConfigService {

    private static final Logger           logger = LoggerFactory.getLogger(DspConfigServiceImpl.class);

    @Autowired
    private ResourceInfoEntityMapper      resourceInfoMapper;

    @Autowired
    private AppEntityMapper               appEntityMapper;

    @Autowired
    private AppResourceEntityMapper       appResourceEntityMapper;

    @Autowired
    private AccountEntityMapper           accountEntityMapper;

    @Autowired
    private ResourceDspConfigEntityMapper resourceDspConfigEntityMapper;

    @Autowired
    private TFfanadAdxDspEntityMapper     tFfanadAdxDspEntityMapper;

    @Autowired
    private OperationLogService           operationLogService;

    @Override
    public PaginationBo<DspConfigRespDto> getAllResourceDspConfigPage(DspConfigPageReqDto dspConfigPageReqDto, Integer pageNum,
                                                                      Integer pageSize) {
        PaginationBo<DspConfigRespDto> retObject = new PaginationBo<>();
        Map<Integer, ResourceDspConfigEntity> mapResultDspConfig = new HashMap<>();

        ResourceInfoEntityExample resourceInfoEntityExample = null;
        ResourceInfoEntityExample.Criteria criteria = null;
        try {
            resourceInfoEntityExample = buildResourceInfoExample(dspConfigPageReqDto);
            if (CollectionUtils.isEmpty(resourceInfoEntityExample.getOredCriteria())) {
                criteria = resourceInfoEntityExample.createCriteria();
            } else {
                criteria = resourceInfoEntityExample.getOredCriteria().get(0);
            }
            //状态:审核通过,计价方式:独占
            criteria.andResStatusEqualTo(ResourceStatusEnum.AUDIT_SUCCESS.getValue());
            criteria.andResBidtypeEqualTo(ResBidType.PMP_CODE.byteValue());
        } catch (AdRuntimeException e) {
            return retObject;
        }
        Page<ResourceInfoEntity> page = null;
        if (StringUtils.isNotEmpty(dspConfigPageReqDto.getDspEnable())) {
            //查询出设置为打开的配置信息
            ResourceDspConfigEntityExample resourceDspConfigEntityExample = new ResourceDspConfigEntityExample();
            resourceDspConfigEntityExample.createCriteria().andIsDeletedEqualTo("N").andDspEnableEqualTo("Y")
                    .andDspIdEqualTo(dspConfigPageReqDto.getDspId());
            Page<ResourceDspConfigEntity> resourceDspConfigEntityPage = PageHelper.startPage(pageNum, pageSize);
            resourceDspConfigEntityMapper.selectByExample(resourceDspConfigEntityExample);
            List<Integer> lisResId = new ArrayList<>();
            if (!CollectionUtils.isEmpty(resourceDspConfigEntityPage)) {
                for (ResourceDspConfigEntity resourceDspConfigEntity : resourceDspConfigEntityPage) {
                    lisResId.add(resourceDspConfigEntity.getResId());
                    if ("Y".equals(dspConfigPageReqDto.getDspEnable())) {
                        mapResultDspConfig.put(resourceDspConfigEntity.getResId().intValue(), resourceDspConfigEntity);
                    }
                }
            }
            if ("Y".equals(dspConfigPageReqDto.getDspEnable())) {
                //如果是查询打开的
                if (CollectionUtils.isEmpty(resourceDspConfigEntityPage)) {
                    retObject.setTotal(0);
                    retObject.setRows(new ArrayList<DspConfigRespDto>());
                    return retObject;
                } else {
                    criteria.andResIdIn(lisResId);
                }
            } else if (!CollectionUtils.isEmpty(lisResId)) {
                //如果是查询关闭的
                criteria.andResIdNotIn(lisResId);
            }
        }
        resourceInfoEntityExample.setOrderByClause("res_updatedate desc");
        page = PageHelper.startPage(pageNum, pageSize);
        resourceInfoMapper.selectByExample(resourceInfoEntityExample);

        if (!"Y".equals(dspConfigPageReqDto.getDspEnable())) {
            //只要不是查询打开的,就要单独来关联设置信息
            ResourceDspConfigEntityExample resourceDspConfigEntityExample = new ResourceDspConfigEntityExample();
            resourceDspConfigEntityExample.createCriteria().andIsDeletedEqualTo("N")
                    .andDspIdEqualTo(dspConfigPageReqDto.getDspId());
            Page<ResourceDspConfigEntity> resourceDspConfigEntityPage = PageHelper.startPage(pageNum, pageSize);
            resourceDspConfigEntityMapper.selectByExample(resourceDspConfigEntityExample);
            if (!CollectionUtils.isEmpty(resourceDspConfigEntityPage)) {
                for (ResourceDspConfigEntity resourceDspConfigEntity : resourceDspConfigEntityPage) {
                    mapResultDspConfig.put(resourceDspConfigEntity.getResId().intValue(), resourceDspConfigEntity);
                }
            }
        }

        //最后做所有的关联操作
        List<DspConfigRespDto> lisResult = new ArrayList<>();
        for (ResourceInfoEntity resourceInfoEntity : page) {
            DspConfigRespDto dspConfigRespDto = new DspConfigRespDto();
            BeanUtils.copyProperties(resourceInfoEntity, dspConfigRespDto);
            dspConfigRespDto.setReservePrice(null);

            //不管是否为Y都进行数据关联
            if (mapResultDspConfig.containsKey(resourceInfoEntity.getResId().intValue())) {
                BeanUtils.copyProperties(mapResultDspConfig.get(resourceInfoEntity.getResId().intValue()), dspConfigRespDto);
            } else {
                dspConfigRespDto.setDspEnable("N");
            }
            lisResult.add(dspConfigRespDto);
        }

        retObject.setRows(lisResult);
        retObject.setTotal((int) page.getTotal());
        return retObject;
    }

    @Override
    public void saveDspConfig(DspConfigUpdateReqDto dspConfigUpdateReqDto, Integer accountId, String accountEmail) {
        ResourceInfoEntity resourceInfoEntity = resourceInfoMapper.selectByPrimaryKey(dspConfigUpdateReqDto.getResId());
        if (resourceInfoEntity == null) {
            throw new FfanadException("无此资源位");
        }
        TFfanadAdxDspEntity targetAdxDsp = tFfanadAdxDspEntityMapper.selectByPrimaryKey(dspConfigUpdateReqDto.getDspId());
        if (targetAdxDsp == null) {
            throw new FfanadException("无此Dsp");
        }
        //查询是否已经有了相关的设置,如果有了就update,没有就insert
        ResourceDspConfigEntityExample dspConfigEntityExample = new ResourceDspConfigEntityExample();
        dspConfigEntityExample.createCriteria().andResIdEqualTo(dspConfigUpdateReqDto.getResId()).andIsDeletedEqualTo("N");
        List<ResourceDspConfigEntity> lisExistingConfig = resourceDspConfigEntityMapper.selectByExample(dspConfigEntityExample);
        ResourceDspConfigEntity originResourceDspConfigEntity = null;
        ResourceDspConfigEntity originResourceDspConfigCopy = null;
        if (CollectionUtils.isEmpty(lisExistingConfig)) {
            //只有开启才需要insert
            if ("Y".equals(dspConfigUpdateReqDto.getDspEnable())) {
                ResourceDspConfigEntity resourceDspConfigEntity = new ResourceDspConfigEntity();
                BeanUtils.copyProperties(dspConfigUpdateReqDto, resourceDspConfigEntity);
                resourceDspConfigEntity.setReservePrice(dspConfigUpdateReqDto.getReservePrice() == null ? 0
                        : CurrencyUtils.convert(dspConfigUpdateReqDto.getReservePrice()));
                resourceDspConfigEntity.setCreateTime(new Date());
                resourceDspConfigEntity.setUpdateTime(new Date());
                resourceDspConfigEntity.setUpdateBy(accountId);
                resourceDspConfigEntity.setCreateBy(accountId);
                resourceDspConfigEntityMapper.insertSelective(resourceDspConfigEntity);
            }
        } else {
            //update
            originResourceDspConfigEntity = lisExistingConfig.get(0);
            originResourceDspConfigCopy = new ResourceDspConfigEntity();
            BeanUtils.copyProperties(originResourceDspConfigEntity, originResourceDspConfigCopy);

            if ("N".equals(dspConfigUpdateReqDto.getDspEnable())) {
                originResourceDspConfigEntity.setDspEnable("N");
            } else {
                //如果存在已经开启,并且不是当前配置的 dspId,则不允许做更改
                if ("Y".equals(originResourceDspConfigEntity.getDspEnable())
                        && originResourceDspConfigEntity.getDspId().longValue() != dspConfigUpdateReqDto.getDspId().longValue()) {
                    TFfanadAdxDspEntity adxDspEntity = tFfanadAdxDspEntityMapper
                            .selectByPrimaryKey(originResourceDspConfigEntity.getDspId().longValue());
                    String adxDspName = "";
                    if (adxDspEntity != null) {
                        adxDspName = adxDspEntity.getName();
                    }
                    throw new FfanadException("此资源位已开放给" + adxDspName);
                }
                originResourceDspConfigEntity.setDspId(dspConfigUpdateReqDto.getDspId());
                originResourceDspConfigEntity.setDspEnable("Y");
            }
            originResourceDspConfigEntity
                    .setFrameIndex(dspConfigUpdateReqDto.getFrameIndex() == null ? 0 : dspConfigUpdateReqDto.getFrameIndex());
            originResourceDspConfigEntity.setReservePrice(dspConfigUpdateReqDto.getReservePrice() == null ? 0
                    : CurrencyUtils.convert(dspConfigUpdateReqDto.getReservePrice()));
            originResourceDspConfigEntity.setExternalResIosId(dspConfigUpdateReqDto.getExternalResIosId());
            originResourceDspConfigEntity.setExternalResAndroidId(dspConfigUpdateReqDto.getExternalResAndroidId());
            originResourceDspConfigEntity.setUpdateTime(new Date());
            originResourceDspConfigEntity.setUpdateBy(accountId);
            resourceDspConfigEntityMapper.updateByPrimaryKey(originResourceDspConfigEntity);
        }

        writeLog(resourceInfoEntity, targetAdxDsp, dspConfigUpdateReqDto, originResourceDspConfigCopy, accountEmail);
    }

    /**
     * 日志记录
     * 
     * @param resourceInfoEntity
     * @param targetAdxDsp
     * @param dspConfigUpdateReqDto
     * @param originResourceDspConfigEntity
     * @param accountEmail
     */
    private void writeLog(ResourceInfoEntity resourceInfoEntity, TFfanadAdxDspEntity targetAdxDsp,
                          DspConfigUpdateReqDto dspConfigUpdateReqDto, ResourceDspConfigEntity originResourceDspConfigEntity,
                          String accountEmail) {
        StringBuilder sbLogMsg = new StringBuilder();
        try {
            sbLogMsg.append("【" + resourceInfoEntity.getResName() + "】的【" + targetAdxDsp.getName() + "】");
            if (originResourceDspConfigEntity == null) {
                if ("Y".equals(dspConfigUpdateReqDto.getDspEnable())) {
                    sbLogMsg.append("接入状态,修改为:【开】;");
                    if (StringUtils.isNotEmpty(dspConfigUpdateReqDto.getExternalResIosId())) {
                        sbLogMsg.append("广告位ID-iOS,修改为:【" + dspConfigUpdateReqDto.getExternalResIosId() + "】;");
                    }
                    if (StringUtils.isNotEmpty(dspConfigUpdateReqDto.getExternalResAndroidId())) {
                        sbLogMsg.append("广告位ID-安卓,修改为:【" + dspConfigUpdateReqDto.getExternalResAndroidId() + "】;");
                    }
                    sbLogMsg.append("插入位置,修改为:【" + dspConfigUpdateReqDto.getFrameIndex() + "】;");
                    if (dspConfigUpdateReqDto.getReservePrice() != null) {
                        String reservePriceStr = CurrencyUtils
                                .format(CurrencyUtils.convert(dspConfigUpdateReqDto.getReservePrice()));
                        sbLogMsg.append("保留价,修改为:【" + reservePriceStr + "】;");
                    }
                }
            } else {
                if (!originResourceDspConfigEntity.getDspEnable().equals(dspConfigUpdateReqDto.getDspEnable())) {
                    String statusStr = "Y".equals(dspConfigUpdateReqDto.getDspEnable()) ? "开" : "关";
                    sbLogMsg.append("接入状态,修改为:【" + statusStr + "】;");
                }
                if ("Y".equals(dspConfigUpdateReqDto.getDspEnable())) {
                    //如果设置为打开,需要记录参数变化日志
                    if ((StringUtils.isEmpty(originResourceDspConfigEntity.getExternalResIosId())
                            && StringUtils.isNotEmpty(dspConfigUpdateReqDto.getExternalResIosId()))
                            || !originResourceDspConfigEntity.getExternalResIosId()
                                    .equals(dspConfigUpdateReqDto.getExternalResIosId())) {
                        sbLogMsg.append("广告位ID-iOS,修改为:【" + dspConfigUpdateReqDto.getExternalResIosId() + "】;");
                    }
                    if ((StringUtils.isEmpty(originResourceDspConfigEntity.getExternalResAndroidId())
                            && StringUtils.isNotEmpty(dspConfigUpdateReqDto.getExternalResAndroidId()))
                            || !originResourceDspConfigEntity.getExternalResAndroidId()
                                    .equals(dspConfigUpdateReqDto.getExternalResAndroidId())) {
                        sbLogMsg.append("广告位ID-安卓,修改为:【" + dspConfigUpdateReqDto.getExternalResAndroidId() + "】;");
                    }
                    if ((originResourceDspConfigEntity.getFrameIndex() == null && dspConfigUpdateReqDto.getFrameIndex() != null)
                            || !originResourceDspConfigEntity.getFrameIndex().equals(dspConfigUpdateReqDto.getFrameIndex())) {
                        sbLogMsg.append("插入位置,修改为:【"
                                + (dspConfigUpdateReqDto.getFrameIndex() == null ? "" : dspConfigUpdateReqDto.getFrameIndex())
                                + "】;");
                    }
                    if (originResourceDspConfigEntity.getReservePrice() != null
                            || dspConfigUpdateReqDto.getReservePrice() != null) {
                        if ((originResourceDspConfigEntity.getReservePrice() != null
                                && dspConfigUpdateReqDto.getReservePrice() == null)
                                || (originResourceDspConfigEntity.getReservePrice() == null
                                        && dspConfigUpdateReqDto.getReservePrice() != null)
                                || !originResourceDspConfigEntity.getReservePrice()
                                        .equals(dspConfigUpdateReqDto.getReservePrice() == null ? null
                                                : CurrencyUtils.convert(dspConfigUpdateReqDto.getReservePrice()))) {
                            String reservePriceStr = dspConfigUpdateReqDto.getReservePrice() == null ? ""
                                    : CurrencyUtils.format(CurrencyUtils.convert(dspConfigUpdateReqDto.getReservePrice()));
                            sbLogMsg.append("保留价,修改为:【" + reservePriceStr + "】;");
                        }
                    }
                }
            }
            operationLogService.addOperationLog(OperationLogPage.RESOURCE_DSP_CONFIG,
                    OperationLogOperation.UPDATE_RESOURCE_DSP_CONFIG, accountEmail,
                    OperationLogOperation.UPDATE_RESOURCE_DSP_CONFIG, resourceInfoEntity.getResId() + "", sbLogMsg.toString());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public List<TFfanadAdxDspEntity> getAllAdxDsp() {
        TFfanadAdxDspEntityExample tFfanadAdxDspEntityExample = new TFfanadAdxDspEntityExample();
        tFfanadAdxDspEntityExample.setOrderByClause("create_time desc");
        List<TFfanadAdxDspEntity> adxDspEntityList = tFfanadAdxDspEntityMapper.selectByExample(tFfanadAdxDspEntityExample);
        return adxDspEntityList;
    }

    /**
     * 生成查询条件
     *
     * @param dspConfigPageReqDto
     * @return
     */
    private ResourceInfoEntityExample buildResourceInfoExample(DspConfigPageReqDto dspConfigPageReqDto) {
        ResourceInfoEntityExample resourceInfoEntityExample = new ResourceInfoEntityExample();
        if (dspConfigPageReqDto != null) {
            //用户条件过滤
            AccountEntityExample accountEntityExample = new AccountEntityExample();
            AccountEntityExample.Criteria accountCriteria = accountEntityExample.createCriteria();
            if (StringUtils.isNotEmpty(dspConfigPageReqDto.getResUserEmail())) {
                accountCriteria.andAccountEmailLike("%" + dspConfigPageReqDto.getResUserEmail() + "%");
            }

            List<AccountEntity> lisAccount = new ArrayList<>();
            if (accountCriteria.getAllCriteria().size() > 0) {
                lisAccount = accountEntityMapper.selectByExample(accountEntityExample);
                AdAsserts.notEmpty(lisAccount, "没有该用户的数据");
            }

            //app条件过滤
            AppEntityExample appEntityExample = new AppEntityExample();
            AppEntityExample.Criteria appCriteria = appEntityExample.createCriteria();
            if (dspConfigPageReqDto.getResApptype() != null) {
                appCriteria.andAppTypeEqualTo(dspConfigPageReqDto.getResApptype().byteValue());
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
            if (dspConfigPageReqDto.getResId() != null) {
                criteria.andResIdEqualTo(dspConfigPageReqDto.getResId());
            }
            if (StringUtils.isNotEmpty(dspConfigPageReqDto.getResName())) {
                criteria.andResNameLike("%" + dspConfigPageReqDto.getResName() + "%");
            }
            if (StringUtils.isNotEmpty(dspConfigPageReqDto.getAlias())) {
                criteria.andAliasEqualTo(dspConfigPageReqDto.getAlias());
            }
            if (dspConfigPageReqDto.getResPositiontype() != null) {
                criteria.andResPositiontypeEqualTo(dspConfigPageReqDto.getResPositiontype().byteValue());
            }
            if (dspConfigPageReqDto.getResTerminaltype() != null) {
                criteria.andResTerminaltypeEqualTo(dspConfigPageReqDto.getResTerminaltype().byteValue());
            }
            if (dspConfigPageReqDto.getResUsertype() != null) {
                criteria.andResUsertypeEqualTo(dspConfigPageReqDto.getResUsertype().byteValue());
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
}
