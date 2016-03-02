package com.haoqee.chatsdk.entity;

import java.io.Serializable;

public class ImageMessageBody extends BaseMessageBody implements Serializable{

	private static final long serialVersionUID = -121245051552L;
	
	private String imgUrlS = "";				//小图URL
	private String imgUrlL = "";				//大图URL
	private String imgWidth;					//小图宽度
	private String imgHeight;					//小图高度
	
	public ImageMessageBody(){
		
	}

	/**
	 * 设置上传图片路径
	 * @param filePath
	 */
	
	public ImageMessageBody(String filePath){
		this.imgUrlS = filePath;
	}
	
	/**
	 * 赋值小图路径
	 * @param imageUrls
	 */
	public void setImageUrlS(String imageUrls){
		this.imgUrlS = imageUrls;
	}
	
	/**
	 * 获取小图URL
	 * @return
	 */
	public String getImageUrlS(){
		return imgUrlS;
	}
	
	/**
	 * 赋值原图URL路径
	 * @param imageUrll
	 */
	public void setImageUrlL(String imageUrll){
		this.imgUrlL = imageUrll;
	}
	
	/**
	 * 获取原图URL
	 * @return
	 */
	public String getImageUrlL(){
		return imgUrlL;
	}
	
	/**
	 * 赋值小图的宽度
	 * @param imgSWidth
	 */
	public void setImageSWidth(String imgSWidth){
		this.imgWidth = imgSWidth;
	}
	
	/**
	 * 获取小图宽度
	 * @return
	 */
	public String getImageSWidth(){
		return imgWidth;
	}
	
	/**
	 * 赋值小图的高度
	 * @param imgSHeight
	 */
	public void setImageSHeight(String imgSHeight){
		this.imgHeight = imgSHeight;
	}
	
	/**
	 * 获取小图高度
	 * @return
	 */
	public String getImageSHeight(){
		return imgHeight;
	}
}
