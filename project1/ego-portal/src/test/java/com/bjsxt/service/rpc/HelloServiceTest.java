package com.bjsxt.service.rpc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bjsxt.service.HelloService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-*.xml")
public class HelloServiceTest {
	
	@Autowired
	HelloService helloService;
	
	@Test
	public void testSayHello(){
		String result = helloService.sayHello();
		System.out.println("结果："+result);
	}

}
