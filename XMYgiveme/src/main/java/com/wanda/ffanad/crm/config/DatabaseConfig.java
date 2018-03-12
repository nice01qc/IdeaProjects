package com.wanda.ffanad.crm.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;
import com.wanda.ffanad.crm.ds.DataSourceType;
import com.wanda.ffanad.crm.ds.ReadWriteRoutingDataSource;

//delete by xuyao16, 暂时移除读写分离逻辑
//@Configuration
public class DatabaseConfig {
	@Bean
	@Primary
	public ReadWriteRoutingDataSource dataSource() {
		ReadWriteRoutingDataSource dataSource = new ReadWriteRoutingDataSource();
		dataSource.setTargetDataSources(targetDataSources());
		dataSource.setDefaultTargetDataSource(masterDataSource());
		return dataSource;
	}

	@Bean(name = "masterDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.master")
	public DataSource masterDataSource() {
		return new DruidDataSource();
	}

	@Bean(name = "slaveDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.slave")
	public DataSource slaveDataSource() {
		return new DruidDataSource();
	}

	public Map<Object, Object> targetDataSources() {
		Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
		targetDataSources.put(DataSourceType.MASTER, masterDataSource());
		targetDataSources.put(DataSourceType.SLAVE, slaveDataSource());
		return targetDataSources;
	}

}
