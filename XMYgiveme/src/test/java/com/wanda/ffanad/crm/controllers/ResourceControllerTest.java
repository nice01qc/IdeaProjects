package com.wanda.ffanad.crm.controllers;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.pagehelper.Page;
import com.wanda.ffanad.common.utils.JsonUtils;
import com.wanda.ffanad.core.common.RestResult;
import com.wanda.ffanad.core.domains.vo.req.*;
import com.wanda.ffanad.core.domains.vo.res.*;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ResourceControllerTest extends BaseControllerTest {

    /**
     * 资源分页
     * 
     * @throws Exception
     */
    @Test
    public void getResourcesByPage() throws Exception {
        ResourceReqVo resReqVo = new ResourceReqVo();
        resReqVo.setResId(1);
        MultiValueMap<String, String> params = new HttpHeaders();
        params.set("pageIndex", "1");
        params.set("pageSize", "20");
        MvcResult mr = mockMvc
                .perform(
                        MockMvcRequestBuilders.post(baseUrl + "resources/list")
                                .session(((MockHttpSession) yyLoginResult.getRequest().getSession()))
                                .contentType("application/json").content(JsonUtils.toJSON(resReqVo)).params(params))
                .andExpect(status().isOk()).andReturn();
        String responseString = mr.getResponse().getContentAsString();
        logger.info("返回内容：" + responseString);
        Assert.assertNotNull(responseString);
        RestResult restResult = JsonUtils.toT(responseString, RestResult.class);
        assert restResult != null && restResult.getResult() != null && (int) ((Map) restResult.getResult()).get("total") > 0;
    }

    /**
     * 某一个资源位的统计
     * 
     * @throws Exception
     */
    @Test
    public void getResourceStatistics() throws Exception {
        ResourceStatisticsReqVo resReqVo = new ResourceStatisticsReqVo();
        resReqVo.setResId(1);
        resReqVo.setStartTime(new DateTime(2016, 7, 1, 0, 0).toDate());
        resReqVo.setEndTime(new Date());
        MvcResult mr = mockMvc
                .perform(
                        MockMvcRequestBuilders.post(baseUrl + "resources/statistics")
                                .session(((MockHttpSession) yyLoginResult.getRequest().getSession()))
                                .contentType("application/json").content(JsonUtils.toJSON(resReqVo))).andExpect(status().isOk())
                .andReturn();
        String responseString = mr.getResponse().getContentAsString();
        logger.info("返回内容：" + responseString);
        Assert.assertNotNull(responseString);
        StatisticsResult restResult = JsonUtils.toT(responseString, StatisticsResult.class);
        assert restResult != null && restResult.getData() != null;
    }

    /**
     * 某一个资源位的统计
     *
     * @throws Exception
     */
    @Test
    public void getResouceCodeValue() throws Exception {
        MvcResult mr = mockMvc
                .perform(
                        MockMvcRequestBuilders.get(baseUrl + "resources/code").session(
                                ((MockHttpSession) yyLoginResult.getRequest().getSession()))).andExpect(status().isOk())
                .andReturn();
        String responseString = mr.getResponse().getContentAsString();
        logger.info("返回内容：" + responseString);
        Assert.assertNotNull(responseString);
        RestResult restResult = JsonUtils.toT(responseString, RestResult.class);
        assert restResult != null && restResult.getResult() != null;
    }

    /**
     * 某一个资源位的统计
     *
     * @throws Exception
     */
    @Test
    public void getResourcesById() throws Exception {
        MvcResult mr = mockMvc
                .perform(
                        MockMvcRequestBuilders.get(baseUrl + "resources/1").session(
                                ((MockHttpSession) yyLoginResult.getRequest().getSession()))).andExpect(status().isOk())
                .andReturn();
        String responseString = mr.getResponse().getContentAsString();
        logger.info("返回内容：" + responseString);
        Assert.assertNotNull(responseString);
        ResourceInfoResVo restResult = JsonUtils.toT(JSON.parseObject(responseString).getString("result"),
                ResourceInfoResVo.class);
        assert restResult != null;
    }

    /**
     * 某一个资源位的统计
     *
     * @throws Exception
     */
    @Test
    public void updateResourceAuditResult() throws Exception {
        ResourceStatusReqVo resReqVo = new ResourceStatusReqVo();
        resReqVo.setResId(1);
        resReqVo.setResStatus(1);
        MvcResult mr = mockMvc
                .perform(
                        MockMvcRequestBuilders.post(baseUrl + "resources/auditresult")
                                .session(((MockHttpSession) yyLoginResult.getRequest().getSession()))
                                .contentType("application/json").content(JsonUtils.toJSON(resReqVo))).andExpect(status().isOk())
                .andReturn();
        String responseString = mr.getResponse().getContentAsString();
        logger.info("返回内容：" + responseString);
        Assert.assertNotNull(responseString);
        ResourceStatusReqVo restResult = JsonUtils.toT(JSON.parseObject(responseString).getString("result"),
                ResourceStatusReqVo.class);
        assert restResult != null;
    }

    /**
     * 某一个资源位的统计
     *
     * @throws Exception
     */
    @Test
    public void updateRTBPrice() throws Exception {
        ResourceRTBPriceReqVo resRTBPrice = new ResourceRTBPriceReqVo();
        resRTBPrice.setResId(1);
        resRTBPrice.setResCpc(new BigDecimal(0.23));
        resRTBPrice.setResCpm(new BigDecimal(0.64));
        MvcResult mr = mockMvc
                .perform(
                        MockMvcRequestBuilders.get(baseUrl + "resources/rtbprice")
                                .session(((MockHttpSession) yyLoginResult.getRequest().getSession()))
                                .contentType("application/json").content(JsonUtils.toJSON(resRTBPrice)))
                .andExpect(status().isOk()).andReturn();
        String responseString = mr.getResponse().getContentAsString();
        logger.info("返回内容：" + responseString);
        Assert.assertNotNull(responseString);
        ResourceRTBPriceResVo restResult = JsonUtils.toT(JSON.parseObject(responseString).getString("result"),
                ResourceRTBPriceResVo.class);
        assert restResult != null;
    }

    /**
     * 某一个资源位的统计
     *
     * @throws Exception
     */
    @Test
    public void updateMonopolyPrice() throws Exception {
        ResourceMonopolyPriceUpdateReqVo resPriceVo = new ResourceMonopolyPriceUpdateReqVo();
        resPriceVo.setResId(10037); //独占，按城市定向
        List<MonopolyPriceInfo> list = new ArrayList<>();
        MonopolyPriceInfo monopolyPriceInfo = new MonopolyPriceInfo();
        //TODO 增加参数
        list.add(monopolyPriceInfo);
        resPriceVo.setResPriceList(list);
        MvcResult mr = mockMvc
                .perform(
                        MockMvcRequestBuilders.get(baseUrl + "resources/monopolyprice").session(
                                ((MockHttpSession) yyLoginResult.getRequest().getSession()))).andExpect(status().isOk())
                .andReturn();
        String responseString = mr.getResponse().getContentAsString();
        logger.info("返回内容：" + responseString);
        Assert.assertNotNull(responseString);
        ResourceMonopolyPriceResVo restResult = JsonUtils.toT(JSON.parseObject(responseString).getString("result"),
                ResourceMonopolyPriceResVo.class);
        assert restResult != null;
    }

    /**
     * 某一个资源位的统计
     *
     * @throws Exception
     */
    @Test
    public void getResourcesPirceByPage() throws Exception {
        MultiValueMap<String, String> params = new HttpHeaders();
        params.set("pageNum", "1");
        params.set("pageSize", "20");
        MvcResult mr = mockMvc
                .perform(
                        MockMvcRequestBuilders.post(baseUrl + "resources/10119/price")
                                .session(((MockHttpSession) yyLoginResult.getRequest().getSession())).params(params))
                .andExpect(status().isOk()).andReturn();
        String responseString = mr.getResponse().getContentAsString();
        logger.info("返回内容：" + responseString);
        Assert.assertNotNull(responseString);
        Page<ResourcePriceInfoResVo> restResult = JsonUtils.toT(JSON.parseObject(responseString).getString("result"),
                new TypeReference<Page<ResourcePriceInfoResVo>>() {
                });
        assert restResult != null && restResult.getResult() != null && restResult.getTotal() > 0;
    }

    /**
     * 某一个资源位的统计
     *
     * @throws Exception
     */
    @Test
    public void updateReservePrice() throws Exception {
        MultiValueMap<String, String> params = new HttpHeaders();
        params.set("reservePrice", "129");
        params.set("enableReservePrice", "Y");
        MvcResult mr = mockMvc
                .perform(
                        MockMvcRequestBuilders.post(baseUrl + "resources/10119/reserveprice")
                                .session(((MockHttpSession) yyLoginResult.getRequest().getSession())).params(params))
                .andExpect(status().isOk()).andReturn();
        String responseString = mr.getResponse().getContentAsString();
        logger.info("返回内容：" + responseString);
        Assert.assertNotNull(responseString);
        RestResult restResult = JsonUtils.toT(responseString, RestResult.class);
        assert restResult != null;
    }
}
