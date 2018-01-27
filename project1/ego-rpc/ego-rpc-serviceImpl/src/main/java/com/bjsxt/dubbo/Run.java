package com.bjsxt.dubbo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Run {
	private final static Logger logger = LoggerFactory.getLogger(Run.class);
	public static void main(String[] args) {
		
		//1.加载spring配置文件
		String[] config = {"spring/applicationContext-*.xml"};
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(config);
		//2.启动
		context.start();
		
		//while循环，防止自动关闭
		while(true){
			try {
				Thread.sleep(3*1000);
				logger.info("dubbo主进程正在服务");
			} catch (Exception e) {
				logger.error("dubbo服务主进程出错了,"+e.getMessage());
			}
		}
	}
}
