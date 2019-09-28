package com.chutianlong.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


public class FileUtil {

	
	public static String uploadFile(@RequestParam("file") MultipartFile file,HttpServletRequest request) throws Exception{
		 String filename = file.getOriginalFilename();
		 //UUID文件名
		 String pagename=UUID.randomUUID().toString();
		 //获取后缀名
         String suffixname = filename.substring(filename.lastIndexOf("."));
         //获取项目根路径
         String realPath = request.getServletContext().getRealPath("/");
		 //判断文件路径是否存在
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 String  datapath= dateFormat.format(new Date());
		 String uploadpath=realPath+datapath+"/"+pagename+suffixname;
		 File wj=new File(uploadpath);
		 
		 if (!wj.getParentFile().exists()) {
			 wj.getParentFile().mkdirs();
			}
		  file.transferTo(wj);
		return "/"+datapath+"/"+pagename+suffixname;
	}
	
	public static void downloadFile(HttpServletResponse response, String realPath, String filename){
		
		File file = new File(realPath, filename);

        if (file.exists()) {
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + filename);// 设置文件名
			byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {

                // TODO: handle exception
                e.printStackTrace();

            } finally {
               if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block

                        e.printStackTrace();
                    }

                }

                if (fis != null) {

                    try {

                        fis.close();

                    } catch (IOException e) {

                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            	}
            }
        }
	
}
