package com.wanda.ffanad.crm.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.wanda.ffanad.crm.controllers")
public class SwaggerConfig {
    
	@Bean
	public Docket swaggerSpringMvcPlugin() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors .basePackage("com.wanda.ffanad.crm.controllers")).paths(PathSelectors.any()) 
				.build().apiInfo(apiInfo()).enableUrlTemplating(true);
	}


	private ApiInfo apiInfo() {
		return new ApiInfo("ffan", "万达广告投放系统", "1.0", "www.wanda.cn", new Contact("wanda", "", ""), "entertainment", "www.wanda.cn");
	}
}