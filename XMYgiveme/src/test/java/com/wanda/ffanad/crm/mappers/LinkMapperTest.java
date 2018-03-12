package com.wanda.ffanad.crm.mappers;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.wanda.ffanad.core.domains.Link;
import com.wanda.ffanad.core.mappers.LinkMapper;
import com.wanda.ffanad.crm.CRMApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CRMApplication.class)
@WebAppConfiguration
public class LinkMapperTest {

    @Autowired
    LinkMapper mapper;

    @Test
    public void insertSelective() {
        Link record = new Link();
        record.setName("测试123");
        record.setComment("下一步要用到的内容");
        assertTrue(mapper.insertSelective(record) > 0);
    }
}
