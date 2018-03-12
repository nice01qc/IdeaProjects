/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

/**
 * 类FileManagerService.java的实现描述：TODO 类实现描述
 * 
 * @author liuzhenkai1 2016年8月25日 下午3:15:04
 */
public interface FileManagerService {
    /**
     * 上传图片,返回图片服务器地址
     * 
     * @param file
     * @throws IOException
     */
    String uploadPic(MultipartFile file) throws IOException;
    
    /**
     * 上传图片,返回图片服务器地址
     * 
     * @param file
     * @throws IOException
     */
    String uploadFileByte(byte[]fileBytes,String fileName) throws IOException;
}
