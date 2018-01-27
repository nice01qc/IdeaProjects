package com.bjsxt.service;

import java.io.InputStream;

import com.bjsxt.result.FileResult;

public interface FileUploadService {

	/**
	 * 上传文件
	 * @param originalFilename
	 * @param inputStream
	 * @return
	 */
	FileResult fileUploadSave(String originalFilename, InputStream inputStream);

}
