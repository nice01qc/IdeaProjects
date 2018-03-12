package com.wanda.ffanad.crm.config;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import org.springframework.stereotype.Component;

/**
 * Created by shushangjin on 2017-3-8.
 */
@Component
@DisconfFile(filename = "apiConfig.properties")
public class MyConfig {
    private volatile String apiUrl;

    @DisconfFileItem(name = "apiUrl", associateField = "apiUrl")
    public String getApiUrl() {
        return apiUrl;
    }
}
