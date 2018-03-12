package com.wanda.ffanad.crm.controllers;

import com.wanda.ffanad.common.utils.JsonUtils;
import com.wanda.ffanad.core.domains.Link;
import com.wanda.ffanad.crm.common.PaginationBo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 创意链接
 */
public class LinkControllerTest extends BaseControllerTest {

    @Test
    public void getPage() throws Exception {
        MvcResult mr = mockMvc
                .perform(
                        MockMvcRequestBuilders.get(baseUrl + "link/list").session(
                                ((MockHttpSession) yyLoginResult.getRequest().getSession()))).andExpect(status().isOk())
                .andReturn();
        String responseString = mr.getResponse().getContentAsString();
        logger.info("返回内容：" + responseString);
        Assert.assertNotNull(responseString);
        @SuppressWarnings("unchecked")
        PaginationBo<Link> page = JsonUtils.toT(responseString, PaginationBo.class);
        assert page.getRows() != null && page.getRows().size() > 0;
    }

    @Test
    public void create() throws Exception {
        Link record = new Link(null, "测试Link", (byte) 0, (byte) 1, (byte) 1, "单元测试");
        mockMvc.perform(
                MockMvcRequestBuilders.post(baseUrl + "link").contentType("application/json")
                        .session(((MockHttpSession) yyLoginResult.getRequest().getSession())).content(JsonUtils.toJSON(record)))
                .andExpect(status().isOk()).andExpect(content().string("1"));
    }
}
