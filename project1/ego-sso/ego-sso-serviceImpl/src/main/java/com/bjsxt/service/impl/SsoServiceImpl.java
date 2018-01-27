package com.bjsxt.service.impl;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.bjsxt.mapper.AdminMapper;
import com.bjsxt.pojo.Admin;
import com.bjsxt.pojo.AdminExample;
import com.bjsxt.service.SsoService;
import com.bjsxt.util.Md5Util;
import com.bjsxt.util.SerializeUtil;
import com.bjsxt.util.UuidUtil;

import redis.clients.jedis.JedisCluster;

public class SsoServiceImpl implements SsoService {

	//日志打印对象
	private static Logger logger = LoggerFactory.getLogger(SsoServiceImpl.class);

	@Autowired
	AdminMapper adminMapper;

	@Autowired
	JedisCluster jedisCluster;
	
	@Value("${user.ticket}")
	String userTicket;

	/**
	 * 登录，返回的是用户ticket
	 */
	@Override
	public String login(Admin admin) {
		//判断输入的数据是否合法
		String userName = admin.getUserName();
		String password = admin.getPassword();
		if(userName!=null && userName.length()>0){
			if(password!=null && password.length()>0){
				//验证begin
				//根据用户名查询出用户的信息
				//创建查询对象
				AdminExample example = new AdminExample();
				//指定查询条件
				example.createCriteria().andUserNameEqualTo(userName.trim());
				//进行查询
				List<Admin> adminList =adminMapper.selectByExample(example);
				if(adminList!=null && adminList.size()==1){
					Admin adminDb = adminList.get(0);
					//验证密码
					if(adminDb.getPassword().equals(Md5Util.GetMD5WithSalt(password, adminDb.getEcSalt()))){
						//验证通过
						String ticket = userTicket+"_"+UuidUtil.getUuid();
						//将用户信息，添加至redis中
						jedisCluster.set(SerializeUtil.serialize(ticket), SerializeUtil.serialize(adminDb));
						return ticket;
					}
				}else{
					logger.error("系统出错了，根据用户名查询出多个用户，用户名为："+userName);
				}
				//验证end
			}
		}
		logger.error("用户名或密码不能为空");
		return null;
	}

	/**
	 * 根据ticket进行验证
	 */
	@Override
	public Admin validate(String ticket) {
		Admin admin = null;
		//根据传递过来的ticket，从redis中获取用户数据
		byte[] adminRedis = jedisCluster.get(SerializeUtil.serialize(ticket));
		if(adminRedis!=null && adminRedis.length>0){
			//反序列化为Admin对象
			admin = (Admin) SerializeUtil.unserialize(adminRedis);
		}
		return admin;
	}

	/**
	 * 退出
	 */
	@Override
	public void logout(String ticket) {
		jedisCluster.del(SerializeUtil.serialize(ticket));
	}

}
