package com.bjsxt.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bjsxt.result.FileResult;
import com.bjsxt.service.FileUploadService;

@Controller
@RequestMapping("/fileUpload")
public class FileUploadController {
	
	@Autowired
	FileUploadService fileUploadService;
	
	/**
	 * 保存文件
	 * @param file input name属性
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/save")
	@ResponseBody
	public FileResult fileUploadSave(MultipartFile file) throws IOException{
		return fileUploadService.fileUploadSave(file.getOriginalFilename(),file.getInputStream());
	}

}
