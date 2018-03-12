package com.wanda.ffanad.crm.ds;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class ReadWriteRoutingDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceContextHolder.getType();
	}

}
