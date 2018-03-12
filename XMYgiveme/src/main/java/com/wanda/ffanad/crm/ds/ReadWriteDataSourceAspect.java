package com.wanda.ffanad.crm.ds;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//delete by xuyao16, 暂时移除读写分离逻辑
//@Aspect
//@Order(-1)
//@Component
public class ReadWriteDataSourceAspect {
	/**
	 * In order no nested DataSource set,
	 * normalize the application package architecture:
	 * Controller, Service, Manager, DAO/Mapper
	 */
	@Around("execution(public * com.wanda.ffanad.*.service*.*.get*(..)) or execution(public * com.wanda.ffanad.*.service*.*.find*(..)) or execution(public * com.wanda.ffanad.*.service*.*.select*(..)) or execution(public * com.wanda.ffanad.*.service*.*.query*(..))")
	public Object setReadOnlyDataSourceType(ProceedingJoinPoint joinPoint) throws Throwable {
		if (DataSourceContextHolder.isEmpty()) {
			DataSourceContextHolder.read();
		}
		
		Object object = joinPoint.proceed();
		
		if (!DataSourceContextHolder.isEmpty()) {
			DataSourceContextHolder.clear();
		}
		
		return object;
	}

}
