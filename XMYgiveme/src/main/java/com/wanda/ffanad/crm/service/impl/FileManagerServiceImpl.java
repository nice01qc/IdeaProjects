/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.wanda.ffanad.crm.service.FileManagerService;

/**
 * 类FileManagerServiceImpl.java的实现描述：文件上传下载实现类
 * 
 * @author liuzhenkai1 2016年8月25日 下午3:15:16
 */
@Service
public class FileManagerServiceImpl implements FileManagerService {

    @Value("${wanda.file.api}")
    private String fileUploadUrl;
    @Value("${wanda.get.file.api}")
    private String GetFileUrl;

    @Override
    public String uploadPic(final MultipartFile file) throws IOException {
        RestTemplate rest = new RestTemplate();
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };
        param.add("file", resource);
        String string = rest.postForObject(fileUploadUrl, resource, String.class);

        return GetFileUrl + string;
    }

    @Override
    public String uploadFileByte(byte[] fileBytes, final String fileName) throws IOException {
        RestTemplate rest = new RestTemplate();
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        ByteArrayResource resource = new ByteArrayResource(fileBytes) {
            @Override
            public String getFilename() {
                return fileName;
            }
        };
        param.add("file", resource);
        String string = rest.postForObject(fileUploadUrl, resource, String.class);

        return GetFileUrl + "/" + string;
    }
}
