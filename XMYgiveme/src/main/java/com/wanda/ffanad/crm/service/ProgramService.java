package com.wanda.ffanad.crm.service;

import com.wanda.ffanad.crm.dto.resp.ProgramDetailDto;

public interface ProgramService {
    public ProgramDetailDto queryProgramDetail(Long programId);
    
}
