package com.wanda.ffanad.crm.service;

import com.wanda.ffanad.base.dal.entities.TFfanadAdxDspEntity;
import com.wanda.ffanad.core.common.PaginationBo;
import com.wanda.ffanad.crm.dto.req.DspConfigPageReqDto;
import com.wanda.ffanad.crm.dto.req.DspConfigUpdateReqDto;
import com.wanda.ffanad.crm.dto.resp.DspConfigRespDto;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 资源审核定价相关业务接口.
 */
public interface DspConfigService {

    /**
     * 获取配置信息的分页数据
     * 
     * @param dspConfigPageReqDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    PaginationBo<DspConfigRespDto> getAllResourceDspConfigPage(DspConfigPageReqDto dspConfigPageReqDto, @NotNull Integer pageNum,
                                                               @NotNull Integer pageSize);

    void saveDspConfig(DspConfigUpdateReqDto dspConfigUpdateReqDto, @NotNull Integer account, @NotNull String accountEmail);

    /**
     * 获取所有的adxDsp
     * 
     * @return
     */
    List<TFfanadAdxDspEntity> getAllAdxDsp();
}
