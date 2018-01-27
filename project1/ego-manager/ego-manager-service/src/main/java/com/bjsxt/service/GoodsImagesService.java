package com.bjsxt.service;

import java.io.InputStream;

import com.bjsxt.result.FileResult;

public interface GoodsImagesService {

	/**
	 * 将图片的url保存至数据
	 * @param fileUrl
	 */
	int goodsImagesSave(String fileUrl,Integer goodsId);


}
