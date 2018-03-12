package com.wanda.ffanad.crm.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.wanda.ffanad.crm.service.impl.DspConfigServiceImpl;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.wanda.ffanad.common.utils.JsonUtils;
import com.wanda.ffanad.crm.dto.req.ChannelSaveReqDto;

/**
 * Created by kevin on 16/11/17.
 */
public class ChannelControllerTest extends BaseControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(ChannelControllerTest.class);

    @Test
    public void insertTest() throws Exception {
        ChannelSaveReqDto channelSaveReqDto = new ChannelSaveReqDto();
        channelSaveReqDto.setName("测试二");
        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "channel/save").contentType("application/json")
                .session(((MockHttpSession) yyLoginResult.getRequest().getSession()))
                .content(JsonUtils.toJSON(channelSaveReqDto))).andExpect(status().isOk());
    }

    @Test
    public void updateTest() throws Exception {
        ChannelSaveReqDto channelSaveReqDto = new ChannelSaveReqDto();
        channelSaveReqDto.setId(2);
        channelSaveReqDto.setName("测试二.2");
        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "channel/save").contentType("application/json")
                .session(((MockHttpSession) yyLoginResult.getRequest().getSession()))
                .content(JsonUtils.toJSON(channelSaveReqDto))).andExpect(status().isOk());
    }

    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl + "channel/1").contentType("application/json")
                .session(((MockHttpSession) yyLoginResult.getRequest().getSession()))).andExpect(status().isOk());
    }
}
