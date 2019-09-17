package com.chutianlong.util;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

public class FileDownLoad {

	public static String uploadImg(MultipartFile file,HttpServletRequest request) throws IllegalStateException, IOException{
		String priceName = file.getOriginalFilename();	// 获取图片名
		String suffixName = priceName.substring(priceName.lastIndexOf("."));	// 获取图片的后缀名
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");	// 时间戳文件夹
		String dirPath = sdf.format(new Date());
		String realPath = request.getServletContext().getRealPath("/");		// 获取目录地址
		String fileName = UUID.randomUUID().toString()+suffixName;	// 获取文件名
		// 相对路径
		File dest = new File(realPath+dirPath+"\\"+fileName);
		// 检测是否有此目录
		if(!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		
		try {
			file.transferTo(dest);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dirPath+"/"+fileName; 
	}

	public static String download(String filepath,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		OutputStream fos = null;
		InputStream fis = null;

		try {
		// 如果是从服务器上取就用这个获得系统的绝对路径方法。
		//String filepath1 = request.getRealPath();//方法过时了
			/*String filepathall  = request.getSession().getServletContext().getRealPath("/");*/
			//String path = filepath.substring(32);

		File uploadFile = new File(filepath);
			if (!uploadFile.exists()) {
				uploadFile.getParentFile().mkdirs();//
			}

		//图片对象流
		fis = new FileInputStream(uploadFile);
		bis = new BufferedInputStream(fis);
		fos = response.getOutputStream();
		bos = new BufferedOutputStream(fos);
		
		//得到文件名
		String filename = filepath.substring(filepath.lastIndexOf("\\")+1);

		// 这个就就是弹出下载对话框的关键代码
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(filename, "utf-8"));
		
		int bytesRead = 0;
		// 用输出流去写，缓冲输入输出流
		byte[] buffer = new byte[8192];
		while ((bytesRead = bis.read(buffer, 0, 8192)) != -1) {
		bos.write(buffer, 0, bytesRead);
		}

		} catch (FileNotFoundException e) {
		e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
		} catch (NumberFormatException e) {
		e.printStackTrace();
		} finally {
		try {
		if (bos != null) {
		bos.flush();
		}
		if (fis != null) {
		fis.close();
		}
		if (bis != null) {
		bis.close();
		}
		if (fos != null) {
		fos.close();
		}
		if (bos != null) {
		bos.close();
		}
		} catch (IOException e) {
		e.printStackTrace();
		}
		}
		return null;
	}
}
