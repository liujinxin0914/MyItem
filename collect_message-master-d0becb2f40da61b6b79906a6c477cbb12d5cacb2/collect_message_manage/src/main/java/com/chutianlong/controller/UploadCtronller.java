package com.chutianlong.controller;
import com.chutianlong.pojo.Student;
import com.chutianlong.pojo.Sys_organize;
import com.chutianlong.service.UploadService;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.map.LinkedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
@RequestMapping("/File")
public class UploadCtronller {
    @Autowired
    private UploadService uploadService;
    @Value("${default.upload.image}")
    private String img;
    @Value("${file.path}")
    private String down;
    @Value("${file.url}")
    private String urls;
    @RequestMapping("/index")
    public String index(){
        return "fileUpAndDown";
    }

    //文件上传
    @RequestMapping("/Upload")
    @ResponseBody
    public  Object uploadFile(@RequestParam("files") MultipartFile[] files, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //多个图片上传
        LinkedMap map = new LinkedMap();
        int k = 0;
        for(int j = 0;j<files.length;j++){
            MultipartFile file = files[j];
            try {
                //windows图片实际存放
                String path =img;
                String virtualPath = "upload";
                //获取文件原始名称
                String filename = file.getOriginalFilename();
                String org_id = filename.substring(0,filename.indexOf("."));
                if(!org_id.contains("Z")){
                    map.put(k,filename+":"+"请上传正确的证件照");
                    k++;
                    continue;
                }
                //获取学生姓名
                String studentname =  org_id.replaceAll("[^\u4E00-\u9FA5]", "");
                //获取学籍号
                String REGEX_CHINESE = "[\u4e00-\u9fa5]";
                String z = org_id.replaceAll(REGEX_CHINESE, "");
                String orgId = z.substring(0,z.indexOf("Z"));
                //根据学生姓名和学籍号同时搜索
                String name = uploadService.findStudentByCodeAndName(studentname,orgId);
                if(name==null){
                    map.put(k,filename+":"+"该学生不存在,请认真核对学籍号与姓名");
                    k++;
                    continue;
                }
                String StudentName = uploadService.findStudentStastus(orgId);
                if(StudentName==null){
                    map.put(k,filename+":"+"该学生没有被下载，无法上传");
                    k++;
                    continue;
                }
                //验证通过，拼接写入地址
                Student student = uploadService.findStudentMessage((int)Double.parseDouble(orgId));
                Sys_organize classesName = uploadService.findClassName(student.getClasses_id());
                Sys_organize gradeName = uploadService.findClassName(classesName.getPid());
                Sys_organize schoolName = uploadService.findClassName(gradeName.getPid());
                path = path+"/"+schoolName.getName()+"/"+gradeName.getName()+"/"+classesName.getName();
                //验证通过，修改学生状态为7（美工通过）
                org_id=orgId;
                uploadService.updateStudentStatusByOrg_id(org_id);
                //uploadService.deletePhotoByCode(org_id);
                //获取文件类型
                String suffixname = filename.substring(filename.lastIndexOf(".")+1);
                System.out.println(suffixname+"a");
                //创建图片类型的数组
                String img[] = {"jpg","jpeg","png"};
                //定义变量,判断是否为指定格式图片
                int count = 0;
                for (int i = 0; i < img.length; i++) {
                    if (img[i].equals(suffixname)) {
                        count++;
                    }
                }
                //如果count>0说明图片格式是指定格式
                if(count>-1){
                    //uuid作为主键，也作为文件别名
                    String uuid = UUID.randomUUID().toString();
                    String newChartName = uuid+"."+suffixname;
                    System.out.println(path+"存储文件名称");
                    //文件路径,第二个参数为保存的文件名称
                    File dir = new File(path,filename);//newChartName
                    //没有文件路则创建文件路径
                    if (!dir.exists()) {
                        dir.getParentFile().mkdirs();//
                    }

                    try {
                        file.transferTo(dir);
                    }catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    /*//文件上传完成之后将一些信息保存到数据库
                    String url = urls+filename;
                    System.out.println(filename);
                    //String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"+virtualPath+"/"+newChartName;
                    uploadService.add(url,org_id);*/
                }else {
                    System.out.println("失败");
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return map;
    }
    @RequestMapping("/list")
    public String list(){
        return "test";
    }

    @RequestMapping("/findphoto")
    public String findphoto(Model model,String Bj,String name,String code,String pageAum){
        if(code!=null&&!"".equals(code)){
            List ilist = uploadService.findListByCode(code);
            model.addAttribute("ilist",ilist);
            model.addAttribute("path",down);
            return "test";
        }
        if(name!=null&&!"".equals(name)){
            List ilist = uploadService.findListByName(name);
            model.addAttribute("ilist",ilist);
            model.addAttribute("path",down);
            return "test";
        }
        if(Bj!=null&&!"".equals(Bj)){
            int pagenum = 1;
            if(pageAum!=null&&!"".equals(pageAum)){
                System.out.println(pageAum);
                pagenum = (int)Double.parseDouble(pageAum);
            }
            int count = uploadService.findListByphoto(Bj);
            int pageSize = 0;
            if (count % 10 == 0) {
                pageSize = count / 10;
            } else if (count % 10 != 0) {
                pageSize = (count / 10) + 1;
            }
            PageHelper.startPage(pagenum,10);
            List ilist = uploadService.findList(Bj,name,code);
            if(ilist.isEmpty()){
                String error="查询无此信息，请重新选择条件";
                model.addAttribute("error",error);
                return "test";
            }
            model.addAttribute("ilist",ilist);
            model.addAttribute("pagenum",pagenum);
            model.addAttribute("pageSize",pageSize);
            model.addAttribute("Bj",Bj);
            model.addAttribute("path",down);
            return "test";
        }else{
            String error="请选择条件至班级";
            model.addAttribute("error",error);
            return "test";
        }
    }
    @RequestMapping("/shenhe")
    @ResponseBody
    public Object shenhe(String str){
        int i = 0;
        String refuse = "成功";
        if(str!=null){
            String [] strs =  str.split(",");
            for (int j=0;j<strs.length;j++){
                Student student = uploadService.findTest(strs[j]);
                if(student!=null&&!"".equals(student)){
                    return "4";
                }
                i = uploadService.tongguo(strs[j]);
            }
            return i;
        }else{
            return "0";
        }

    }
    @RequestMapping("/turndown")
    @ResponseBody
    public Object turndown(String str,String refuse){
        int i = 0;
        if(str!=null){
            String [] strs =  str.split(",");
            for (int j=0;j<strs.length;j++){
                Student student = uploadService.findTest(strs[j]);
                if(student!=null&&!"".equals(student)){
                    return "4";
                }
                 i = uploadService.turnDown(strs[j],refuse);
            }
            return i;
        }else{
            return "0";
        }
    }
    @RequestMapping("/download")
    public Object download(String str,HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(str!=null){
            //建立一个网络链接
            HttpURLConnection con = null;
            String [] strs =  str.split(",");
            String [] urls = new String[15];
            String [] filenames = new String[15];
            for (int j=0;j<strs.length;j++){
                uploadService.updateStudentStatus(strs[j]);
                String filepath = uploadService.findURLByStudentCode(strs[j]);
               // filenames[j] = filepath.substring(filepath.lastIndexOf("d")+1);
                filenames[j] = "http://jykcj.ihongwan.com:9001/upload/"+filepath;
                System.out.println(filenames[j]);
                //防止连接乱码，进行编码
                String remotetest = UriUtils.encodePath(filenames[j], "UTF-8");
                urls[j]= remotetest;
            }
                try {
                    String downloadFilename = "photo"+".zip";
                    //文件的名称
                    downloadFilename = URLEncoder.encode(downloadFilename, "UTF-8");
                    //转换中文否则可能会产生乱码
                    response.setContentType("application/octet-stream");
                    // 指明response的返回对象是文件流
                   response.setHeader("Content-Disposition", "attachment;filename=" + downloadFilename);
                    // 设置在下载框默认显示的文件名
                    ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
                    for (int i = 0; i < urls.length-1; i++) {
                        if(urls[i]!=null&&!"".equals(urls[i])){
                            //通过连接下载
                            URL url = new URL(urls[i]);
                            zos.putNextEntry(new ZipEntry(filenames[i]));
                            InputStream fis = url.openConnection().getInputStream();
                            byte[] buffer = new byte[1024];
                            int r = 0;
                            while ((r = fis.read(buffer)) != -1) {
                                zos.write(buffer, 0, r);
                            }
                            fis.close();
                        }else{
                            continue;
                        }
                    }
                    zos.flush();
                    zos.close();
                    request.getSession().setAttribute("shuaxin",1);
                    } catch (Exception e) {
                    System.out.println(e);
                }
        }
        return null;
    }
    @RequestMapping("/getSession")
    @ResponseBody
    public Object getSession(HttpServletRequest request){
        Object shuaxin = request.getSession().getAttribute("shuaxin");
        return shuaxin;
    }
    @RequestMapping("/delSession")
    @ResponseBody
    public Object delSession(HttpServletRequest request){
        request.getSession().removeAttribute("shuaxin");
        return "111";
    }

}