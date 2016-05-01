package com.earl.carnet.commons.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component("fileUpload")
public class FileUploadImpl {

	/**
	 * log4j实例对象.
	 */
	private static  Logger logger = Logger.getLogger(String.valueOf(FileUploadImpl.class));

	@SuppressWarnings("unused")
	private String filePath = "C:/";


	private String userfilePath;


	@Value("#{public[basePath]}")
	public void setFilePath(String filePath) {
		System.out.println("filePath=" + filePath);
		this.filePath = filePath;
	}

	@Value("#{public[basePath]}" + "#{public.userfilePath}")
	public void setUserFilePath(String filePath) {
		System.out.println("userfilePath=" + filePath);
		this.userfilePath = filePath;
	}

	// 1: 获取文件后缀名
	private String getExt(String oldName) {
		return FilenameUtils.getExtension(oldName);
	}

	// 2: 通过UUID生成新文件名
	private String newName(String oldName) {
		String ext = this.getExt(oldName);
		String newName = UUID.randomUUID().toString() + "." + ext;
		System.out.println(ext);
		if (ext.equals(""))
			newName = UUID.randomUUID().toString() + "." + "png";
		System.out.println(newName);
		return newName;
	}
//
//	public List<String> uploadMulitUserFile(List<File> file,
//											List<String> oldName) {
//		List<String> filename = new ArrayList<String>();
//		for (int i = 0; i < file.size(); i++) {
//			String uploadFile = uploadFile(file.get(i), userfilePath,
//					oldName.get(i));
//			filename.add(uploadFile);
//		}
//		return filename;
//	}


	/**
	 * 上传用户头像.
	 *
	 * @author 宋文光.
	 * @return
	 */
	public String uploadUserFile(MultipartFile userfile) {
		return uploadFile(userfile, userfilePath);
	}

	/**
	 * 上传图片.
	 *
	 * @author 宋文光.
	 */
	private String uploadFile(MultipartFile file, String filePath) {
		logger.info("进入图片上传uploadFile方法");
		String dir = getDir(filePath);
		if (file.isEmpty())
			throw new RuntimeException("上传的文件为空");
		String newName = this.newName(file.getName());// 定义上传图片的文件名

		File newfile = new File(filePath);
		if(!newfile.exists()){
			newfile.mkdirs();
		}

		try {
			FileOutputStream out = new FileOutputStream(filePath+"//"+newName);
			out.write(file.getBytes());
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.info("图片写入文件失败");
			e.printStackTrace();
		}
		logger.info("上传图片地址：" + dir + newName);
		logger.info("退出图片上传uploadFile方法");
		return dir + newName;
	}


	/**
	 * 自定义文件保存地址.
	 *
	 * @author 宋文光.
	 * @param filePath2
	 * @return
	 */
	private String getDir(String filePath2) {
		String substring2 = filePath2.substring(filePath2.lastIndexOf(".."),
				filePath2.length());
		return substring2;
	}

	/**
	 * 根据json格式的文件路径删除多文件.
	 *
	 * @author 宋文光.
	 */
	public Boolean deleFileByJson(String JsonPath) {
		logger.info("进入deleFileByJson方法");
		Boolean result = true;
		List<String> pathList = JsonHelper.fileJsonToStringList(JsonPath);
		try {
			for (String path : pathList) {
				result = deleFile(path);
				if (!result)
					break; // 如果删除失败，直接退出循环
			}
			logger.info("退出deleFileByJson方法");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 根据路径删除文件.
	 *
	 * @author 宋文光.
	 */
	public Boolean deleFile(String path) {
		Boolean result = true;

//		String absolutePath1 = FileUploadImpl.class.getClassLoader().getResource("/").getPath();
		String absolutePath1 = filePath;
		if (absolutePath1 != null) {
			String absolutePath = absolutePath1  + path;
			File file = new File(absolutePath);
			result = deleFile(file);
			logger.info("删除 结果：" + result);
			logger.info("-------------- the deleFile's absolutePath is :"
					+ absolutePath + "----------------------");
		}
		return result;

	}

	/**
	 * 删除文件.
	 *@author 宋文光.
	 * @return
	 */
	public Boolean deleFile(File file) {
		Boolean result = true;
		if (!file.exists()) { // 判断该路径是否存在
			System.out.println("该图片路径不存在");
			return true;
		} else {
			if (!file.isFile()) {// 判断是否为文件
				System.out.println("该路径不是文件");
				result = false;
			} else {
				file.delete();
			}
		}
		return result;
	}

}
