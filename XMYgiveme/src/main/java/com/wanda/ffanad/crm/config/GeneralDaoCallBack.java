package com.wanda.ffanad.crm.config;

/**
 * Created by shushangjin on 2017-3-8.
 */

import com.alibaba.fastjson.JSON;
import com.baidu.disconf.client.common.annotations.DisconfUpdateService;
import com.baidu.disconf.client.common.update.IDisconfUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@DisconfUpdateService(classes = {MyConfig.class})
public class GeneralDaoCallBack implements IDisconfUpdate {
    private static final Logger logger = LoggerFactory.getLogger(GeneralDaoCallBack.class);

    @Autowired
    private MyConfig myConfig;

    @Override
    public void reload() throws Exception {
        logger.info("call back success config: {}", JSON.toJSONString(myConfig));
    }
}