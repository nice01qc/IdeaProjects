package com.bjsxt.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.UUID;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUploadUtil {
	
	private static Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);
	/**
	 * 文件上传 返回上传文件在服务器中的文件名
	 * @return
	 * @throws IOException 
	 */
	public static String fileUpload(String host,String username,String password,
			String fileName,InputStream inputStream,String path) throws IOException{
		//1.创建FtpClient对象
		FTPClient ftpClient = new FTPClient();
		String remote=null;
		try {
			//2.指定服务器地址
			ftpClient.connect(host);
			//3.指定用户名和密码
			ftpClient.login(username,password);
			
			//上传文件路径
			String bathPath = "/";
			for(String p:path.split("/")){
				bathPath +=(p+"/");
				//判断目录是否已经存在
				boolean hasPath = ftpClient.changeWorkingDirectory(bathPath);
				if(!hasPath){
					//创建目录 一次性只能创建一个目录
					ftpClient.makeDirectory(bathPath);
				}
				
			}
			//4.指定上传的路径
			ftpClient.changeWorkingDirectory(path);
			
			//5.指定上传方式为二进制
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			
			//得到文件后缀
			String suffix = fileName.substring(fileName.lastIndexOf("."));
			remote = UUID.randomUUID()+suffix;
			
			ftpClient.storeFile(remote, inputStream);
		} catch (SocketException e) {
			logger.error("文件上传连接错误:"+e.getMessage());
		} catch (IOException e) {
			logger.error("文件上传失败:"+e.getMessage());
		}finally {
			if(inputStream!=null){
				inputStream.close();
			}
			ftpClient.logout();
			ftpClient.disconnect();
			
		}
		return remote;
	}
}
