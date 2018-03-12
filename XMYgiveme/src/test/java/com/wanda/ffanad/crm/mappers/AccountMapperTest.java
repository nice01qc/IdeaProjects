package com.wanda.ffanad.crm.mappers;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wanda.ffanad.base.dal.entities.AccountEntity;
import com.wanda.ffanad.core.mappers.ext.AccountMapperExt;
import com.wanda.ffanad.crm.CRMApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CRMApplication.class)
public class AccountMapperTest {

    @Autowired
    AccountMapperExt accountMapperExt;

    @Test
    public void selectByParamTest() {
    List<AccountEntity> acList =   accountMapperExt.selectByParam(0, 10, null, null, new AccountEntity());
    Assert.assertTrue(!acList.isEmpty());
    }
}
