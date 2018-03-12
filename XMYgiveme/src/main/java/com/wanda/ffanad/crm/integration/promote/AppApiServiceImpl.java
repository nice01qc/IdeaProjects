package com.wanda.ffanad.crm.integration.promote;

import com.wanda.ffanad.common.utils.JsonUtils;
import com.wanda.ffanad.crm.integration.common.ApiUrlBuilder;
import com.wanda.ffanad.crm.integration.promote.respdto.AppStatisticsResponse;
import com.wanda.ffanad.crm.integration.promote.respdto.PromoteStatistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Kevin on 2016-8-12.
 */
@Service
public class AppApiServiceImpl implements AppApiService {

    @Autowired
    private ApiUrlBuilder apiUrlBuilder;

    @Autowired
    private RestTemplate  restTemplate;

    @Override
    public List<PromoteStatistic> queryAppStatistics(List<Integer> appIds) {
        String adSummaryInfoUrl = apiUrlBuilder.buildAppStatisticsURL();

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<String>(JsonUtils.toJSON(appIds), headers);

        ResponseEntity<AppStatisticsResponse> statisticsResponse = restTemplate.postForEntity(adSummaryInfoUrl, formEntity,
                AppStatisticsResponse.class);
        List<PromoteStatistic> promoteStatistics = statisticsResponse.getBody().getData();

        return promoteStatistics;
    }
}
