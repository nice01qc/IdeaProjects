package com.wanda.ffanad.crm.services;

import com.wanda.ffanad.core.domains.OperationLog;
import com.wanda.ffanad.core.services.OperationLogService;
import com.wanda.ffanad.crm.CRMApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CRMApplication.class)
@WebAppConfiguration
public class OperationLogServiceTest {
    private Logger      logger = LoggerFactory.getLogger(OperationLogServiceTest.class);

    @Autowired
    OperationLogService operationLogService;

    @Test
    public void queryAllPlaza() {
//        List<OperationLog> lis = new ArrayList<>();
//        OperationLog ol = new OperationLog();
//        ol.setLogPage("创意审核");
//        ol.setLogAction("通过");
//        ol.setLogUserEmail("xtest@wanda.cn");
//        ol.setLogAction("创意审核通过");
//        ol.setLogTarget("331");
//        ol.setLogRemark("云pos无商管压测2");
//        ol.setLogOperation("通过");
//        lis.add(ol);
//        lis.add(ol);
//        int count = operationLogService.addOperationLog(lis);
//        logger.info("插入数目：" + count);
//        assert count == 2;
    }
}
