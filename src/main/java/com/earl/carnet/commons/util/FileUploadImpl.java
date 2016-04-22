package com.earl.carnet.commons.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


/*
 * 实现文件上传功能  HTTP  FTP
 * */
@Component("fileUpload")
public class FileUploadImpl {

	@SuppressWarnings("unused")
	private String filePath = "C:/";

//	private String categoryfilePath = "category/";
	private String categoryfilePath;
	
//	private String goodsfilePath = "goods/";
	private String goodsfilePath;

//	private String userfilePath = "user/";
	private String userfilePath ;

	private String fishmanfilePath ;
//	private String fishmanfilePath = "fishman/";

//	private String farmerfilePath = "farmer/";
	private String farmerfilePath ;

	@Value("#{public[basePath]}" + "#{public.filePath}")
	public void setFilePath(String filePath) {
		System.out.println("filePath=" + filePath);
		this.filePath = filePath;
	}

	@Value("#{public[basePath]}" + "#{public.userfilePath}")
	public void setUserFilePath(String filePath) {
		System.out.println("userfilePath=" + filePath);
		this.userfilePath = filePath;
	}

	@Value("#{public[basePath]}" + "#{public.categoryfilePath}")
	public void setCategoryFilePath(String filePath) {
		System.out.println("categoryfilePath=" + filePath);
		this.categoryfilePath = filePath;
	}

	@Value("#{public[basePath]}" + "#{public.fishmanfilePath}")
	public void setFishmanFilePath(String filePath) {
		System.out.println("fishmanfilePath=" + filePath);
		this.fishmanfilePath = filePath;
	}

	@Value("#{public[basePath]}" + "#{public.farmerfilePath}")
	public void setFarmerFilePath(String filePath) {
		System.out.println("farmerfilePath=" + filePath);
		this.farmerfilePath = filePath;
	}
	
	@Value("#{public[basePath]}" + "#{public.goodsfilePath}")
	public void setGoodsFilePath(String filePath) {
		System.out.println("farmerfilePath=" + filePath);
		this.goodsfilePath = filePath;
	}

	// 1: 获取文件后缀名
	private String getExt(String oldName) {
		return FilenameUtils.getExtension(oldName);
	}

	// 2: 通过UUID生成新文件名
	private String newName(String oldName) {
		String ext = this.getExt(oldName);
		return UUID.randomUUID().toString() + "." + ext;
	}

	private String uploadFile(MultipartFile file, String filePath, String oldName) {
		String dir = getDir(filePath);
		String newName = this.newName(oldName);
			try {
				file.transferTo(new File(filePath,newName));
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return dir + newName;
	}

	/**
	 * 多文件上传.
	 *@author 宋文光.
	 * @param file 多文件
	 * @param filePath 
	 * @param oldName
	 * @return
	 */
	private List<String> uploadFile(List<MultipartFile> file, String filePath, List<String> oldName) {
		String dir = getDir(filePath);
		List<String> newNameList = new ArrayList<String>();
		for (int i = 0; i < file.size(); i++) {
			String newName = this.newName(oldName.get(i));
				try {
					file.get(i).transferTo(new File(filePath, newName));
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
				newNameList.add(dir + newName);
		}
		return newNameList;
	}

	private String getDir(String filePath2) {
		String substring = filePath2.substring(filePath2.indexOf("/") + 1,
				filePath2.length());
		return substring;
	}
}
