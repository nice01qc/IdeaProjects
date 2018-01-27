package com.bjsxt.service.impl;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjsxt.mapper.GoodsImagesMapper;
import com.bjsxt.pojo.GoodsImages;
import com.bjsxt.service.GoodsImagesService;

@Service
public class GoodsImagesServiceImpl implements GoodsImagesService{
	
	@Autowired
	GoodsImagesMapper goodsImagesMapper;

	/**
	 * 将图片的url保存至数据
	 */
	@Override
	public int goodsImagesSave(String fileUrl,Integer goodsId) {
		GoodsImages goodsImages = new GoodsImages();
		goodsImages.setImageUrl(fileUrl);
		goodsImages.setGoodsId(goodsId);
		return goodsImagesMapper.insertSelective(goodsImages);
	}
}
