/**
 * <p>Project: 网络管理委员会招新网站, NetUnion Recruit WebSite </p>
 * <p>File: SendMailUtil.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * @author 张健顺
 * @email: 1224522500@qq.com
 * @date 2017年3月28日
 */
package utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



/**
 * JavaMail
 * 报名成功后发送邮件
 */
public class SendMailUtil implements Runnable {
	
	
	// 邮件发送方需要设置为dormforce的官方邮箱
	private static final String fromEmail = "734522310@qq.com";
	
	private static final String host = "smtp.qq.com";
	
	private static final String password = "okdnjspbthmvbdhb";
	
//	private static final String fromEmail = "zhangjianshun@dormforce.net";
//	
//	private static final String host = "smtp.qiye.163.com";
//	
//	private static final String password = "zhangjianshun";
	
	//邮件主题
	private static final String subject = "网管会报名反馈";
	
	private String name;
	
	private String toEmail;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}
	
	@Override
	public void run() {
		sendMail(name, toEmail);
	}
	
	/**
	 * 发送邮件
	 * @param name 接收方姓名(报名人员姓名)
	 * @param toEmail 接收方地址(报名人员邮箱地址)
	 */
	public static void sendMail(String name,String toEmail){
		
		String html = "11111111111111111";

		String content = name+"--> <a href='http://localhost:8080/tomcat-1/email'>click it and regist!</a>";
		
		Properties properties = new Properties();
		
		properties.setProperty("mail.smtp.host", host);
		
		properties.put("mail.smtp.auth", "true");
		
		properties.setProperty("mail.smtp.starttls.enable", "true");
		
		Session session =Session.getDefaultInstance(properties,new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		});
		
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			message.setSubject(subject);
			message.setContent(content,"text/html;charset=UTF-8");
			Transport.send(message);
			System.out.println("Sent message successfully!");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		sendMail("Mache", "3213134284@qq.com");
	}
}