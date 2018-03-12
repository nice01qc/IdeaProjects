/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.integration;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.InterceptingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.common.io.CharStreams;

/**
 * 类Config.java的实现描述：TODO 类实现描述
 * 
 * @author yanji 2016年6月7日 下午3:58:30
 */

@Configuration
public class Config {
    private static final Logger LOGGER                            = LoggerFactory.getLogger(Config.class);
    private static final int    DEFAULT_MAX_TOTAL_CONNECTIONS     = 100;
    private static final int    DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 10;
    private static final int    DEFAULT_READ_TIMEOUT_MILLISECONDS = 5000;

    @Bean
    public HttpClient httpClient() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(DEFAULT_MAX_TOTAL_CONNECTIONS);
        connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_CONNECTIONS_PER_ROUTE);
        RequestConfig config = RequestConfig.custom().setConnectTimeout(DEFAULT_READ_TIMEOUT_MILLISECONDS).build();
        return HttpClientBuilder.create().setConnectionManager(connectionManager).setDefaultRequestConfig(config).build();
    }

    @Bean
    public ClientHttpRequestFactory httpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    @Bean
    public RestTemplate restTemplate() {
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
                    throws IOException {
                LOGGER.info("Request {} {}", request.getMethod(), request.getURI());
                LOGGER.info("Request headers {}", request.getHeaders().toSingleValueMap());

                ClientHttpResponse response = execution.execute(request, body);
                LOGGER.info("Response status: {}", response.getStatusCode());
                LOGGER.info("Response content type: {}", response.getHeaders().getContentType());
                if (response.getHeaders().getContentType() != null
                        && (response.getHeaders().getContentType().includes(MediaType.APPLICATION_JSON)
                                || response.getHeaders().getContentType().includes(MediaType.TEXT_PLAIN)
                                || response.getHeaders().getContentType().includes(MediaType.APPLICATION_OCTET_STREAM)
                                || response.getHeaders().getContentType().includes(MediaType.TEXT_HTML))) {
                    LOGGER.info("Response body: {}", CharStreams.toString(new InputStreamReader(response.getBody(), "UTF-8")));
                }
                return response;
            }
        });

        RestTemplate restTemplate = new RestTemplate(httpRequestFactory());
        //restTemplate.setErrorHandler(new RestResponseErrorHandler());
        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.ALL));
        //MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN, MediaType.APPLICATION_OCTET_STREAM, MediaType.TEXT_HTML
        ObjectMapper mapper = new ObjectMapper().registerModule(new JodaModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
        converter.setObjectMapper(mapper);
        converters.add(converter);
        restTemplate.setMessageConverters(converters);

        restTemplate.setRequestFactory(new InterceptingClientHttpRequestFactory(
                new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()), interceptors));

        return restTemplate;
    }
}
