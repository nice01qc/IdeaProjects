package com.wanda.ffanad.crm.services;

import com.alibaba.fastjson.JSON;
import com.wanda.ffanad.core.domains.vo.res.ResourceCodeResVo;
import com.wanda.ffanad.core.services.ResourceService;
import com.wanda.ffanad.crm.CRMApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CRMApplication.class)
@WebAppConfiguration
public class ResourceServiceTest {
    private Logger  logger = LoggerFactory.getLogger(ResourcePriceServiceTest.class);

    @Autowired
    ResourceService resService;

    @Test
    public void getResourceCodeList() {
        ResourceCodeResVo resVo = resService.getResouceCodeValue();
        String jsonString = JSON.toJSONString(resVo);
        logger.debug(jsonString);
    }

}
