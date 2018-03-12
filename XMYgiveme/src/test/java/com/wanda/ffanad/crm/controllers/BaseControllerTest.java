package com.wanda.ffanad.crm.controllers;

import com.wanda.ffanad.crm.CRMApplication;
import com.wanda.ffanad.crm.services.ResourcePriceServiceTest;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CRMApplication.class)
@WebIntegrationTest("server.port:8080")
public class BaseControllerTest {
    Logger                logger  = LoggerFactory.getLogger(ResourcePriceServiceTest.class);

    @Autowired
    WebApplicationContext context;
    static MockMvc        mockMvc;
    static MvcResult      adminLoginResult;
    static MvcResult      yyLoginResult;
    static MvcResult      cwLoginResult;
    static String         baseUrl = "http://localhost:8080/";

    @Before
    public void login() throws Exception {
        if (mockMvc == null) {
            mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        }
        if (adminLoginResult == null) {
            //管理员登录
            String loginJson = "{\"accountEmail\":\"xuyao_crm_1@wanda.cn\",\"password\":\"c0f1b6a831c399e2\"}";
            MvcResult mr = mockMvc
                    .perform(post("http://localhost:8080/account/login").contentType("application/json").content(loginJson))
                    .andExpect(status().isOk()).andReturn();
            assert mr.getResponse().getContentAsString().contains("200");
            adminLoginResult = mr;
        }
        if (yyLoginResult == null) {
            //登录
            String loginJson = "{\"accountEmail\":\"xuyao_crm_2@wanda.cn\",\"password\":\"c0f1b6a831c399e2\"}";
            MvcResult mr = mockMvc
                    .perform(post("http://localhost:8080/account/login").contentType("application/json").content(loginJson))
                    .andExpect(status().isOk()).andReturn();
            assert mr.getResponse().getContentAsString().contains("200");
            yyLoginResult = mr;
        }
        if (cwLoginResult == null) {
            //登录
            String loginJson = "{\"accountEmail\":\"xuyao_crm_3@wanda.cn\",\"password\":\"c0f1b6a831c399e2\"}";
            MvcResult mr = mockMvc
                    .perform(post("http://localhost:8080/account/login").contentType("application/json").content(loginJson))
                    .andExpect(status().isOk()).andReturn();
            assert mr.getResponse().getContentAsString().contains("200");
            cwLoginResult = mr;
        }
    }
}
