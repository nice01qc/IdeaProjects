package com.wanda.ffanad.crm.config;

import com.wanda.configcenter.client.DynamicConfigScanBean;
import com.wanda.configcenter.client.StaticConfigScanBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by shushangjin on 2017-3-17.
 */
@Configuration
public class PropertiesBeanConfig {

	@Bean(name = "staticScanner")
	public StaticConfigScanBean staticScanner() {
		StaticConfigScanBean staticConfigScanBean = new StaticConfigScanBean();
		staticConfigScanBean.setScanPackage("com.wanda.ffanad");

		return staticConfigScanBean;
	}

    @Bean(name = "dynamicScanner")
    public DynamicConfigScanBean dynamicScanner() {
        return new DynamicConfigScanBean();
    }
}
