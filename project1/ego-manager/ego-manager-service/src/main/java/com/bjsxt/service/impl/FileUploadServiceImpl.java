package com.bjsxt.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bjsxt.result.FileResult;
import com.bjsxt.service.FileUploadService;
import com.bjsxt.util.DateUtil;
import com.bjsxt.util.FileUploadUtil;

@Service
public class FileUploadServiceImpl implements FileUploadService {
	

	private static Logger logger = LoggerFactory.getLogger(FileUploadServiceImpl.class);
	
	@Value("${ftp.host}")
	private String ftpHost;
	@Value("${ftp.username}")
	private String ftpUsername;
	@Value("${ftp.password}")
	private String ftpPassword;
	@Value("${ftp.path}")
	private String ftpPath;
	

	@Override
	public FileResult fileUploadSave(String fileName, InputStream inputStream) {
		//得到日期 格式 2016/12/20
		String path = DateUtil.getDateStr(new Date(),DateUtil.pattern_xg);
		FileResult result = null;
		try {
			//得到文件上传至服务器中的名称
			String remotename = FileUploadUtil.fileUpload(ftpHost, ftpUsername, ftpPassword, 
					fileName, inputStream, ftpPath+"/"+path);
			if(remotename!=null && remotename.length()>0){
				String imageUrl = (path+"/"+remotename);
				result = new FileResult();
				result.setSuccess("上传文件成功");
				result.setFileUrl(imageUrl);
			}
		} catch (IOException e) {
			logger.error("文件上传失败："+e.getMessage());
		}
		return result;
	}

	
}
