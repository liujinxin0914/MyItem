package com.chutianlong.controller;


import com.chutianlong.pojo.Sys_organize;
import com.chutianlong.service.ExcelService;
import com.chutianlong.util.JSONUtil;
import com.chutianlong.util.ZipUtils;
import net.sf.json.JSON;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

@Controller
@RequestMapping("/Excel")
public class ExcelController {
    @Autowired
    private ExcelService excelService;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }
    @RequestMapping("/toTea")
    public String toTea() {
        return "indexfind";
    }

    /***
     * 导入学生信息以及学校名称
     * @param session
     * @param productData
     * @param model
     * @return
     */
    @RequestMapping("/studentExcelDR")
    public String studentExcelDR(HttpSession session, MultipartFile productData, Model model,HttpServletResponse response) {
        String name = productData.getOriginalFilename();
        String suffixname = name.substring(name.lastIndexOf(".")+1);
        Map<String, Object> map = new HashMap<>();
        if(!suffixname.equals("xlsx")){
            map.put("系统错误","请使用xlsx文件，请使用推荐模板导入，防止错误");
            model.addAttribute("map",map);
            return "forward:index";
        }
        if(productData.isEmpty()){
            map.put("系统错误","请先选择文件");
            model.addAttribute("map",map);
            return "forward:index";

        }else{
            long ExcelSize = productData.getSize();
            if(ExcelSize>133500){
                map.put("系统错误","导入数据请勿超过3000条");
            }
            map = excelService.studentExcelDR(session,productData,model);

        }
        // 返回
        if(map.size()>1){
            int totalRow = Integer.parseInt(map.get("totalRow").toString());
            model.addAttribute("totalRow",totalRow);
            model.addAttribute("errorcount",map.get("errorcount"));
            model.addAttribute("successcount",map.get("successcount"));
            model.addAttribute("studentListError",map.get("studentListError"));
            model.addAttribute("isStuOrTea",map.get("isStuOrTea"));
            model.addAttribute("message",map.get("message"));
            map.remove("totalRow");map.remove("errorcount");map.remove("successcount");map.remove("isStuOrTea");map.remove("studentListError");map.remove("message");
            model.addAttribute("map",map);
        }else{
            model.addAttribute("map",map);
        }
        return "forward:index";
    }
    /**
     * 导入老师信息
     *
     * @param session
     * @param productData
     * @param model
     * @return
     */
    @RequestMapping("/teacherExcelDR")
    public String teacherExcelDR(HttpSession session, MultipartFile productData, Model model) {
        String name = productData.getOriginalFilename();
        String suffixname = name.substring(name.lastIndexOf(".")+1);
        Map<String, Object> map = new HashMap<>();
        if(!suffixname.equals("xlsx")){
            map.put("系统错误","请使用xlsx文件，请使用推荐模板导入，防止错误");
            model.addAttribute("map",map);
            return "forward:index";
        }
        if(productData.isEmpty()){
            map.put("系统错误","请先选择文件");
        }else{
            long ExcelSize = productData.getSize();
            if(ExcelSize>133500){
                map.put("系统错误","导入数据请勿超过3000条");
                model.addAttribute("map",map);
                return "forward:index";
            }
            map = excelService.teacherExcelDR(session,productData,model);
        }
        // 返回
        if(map.size()>2){
            int totalRow = Integer.parseInt(map.get("totalRow").toString());
            model.addAttribute("totalRow",totalRow);
            model.addAttribute("errorcount",map.get("errorcount"));
            model.addAttribute("successcount",map.get("successcount"));
            model.addAttribute("teacherListError",map.get("teacherListError"));
            model.addAttribute("message",map.get("message"));
            model.addAttribute("isStuOrTea",map.get("isStuOrTea"));map.remove("isStuOrTea");
            map.remove("message");map.remove("totalRow");map.remove("errorcount");map.remove("successcount");map.remove("teacherListError");map.remove("isStuOrTea");
            model.addAttribute("map",map);
        }else{
            model.addAttribute("map",map);
        }


        return "forward:index";
    }

    /***
     * 查询学校（第一级联动）
     * @return
     */
    @RequestMapping("/findSchool")
    @ResponseBody
    public List<Sys_organize> findSchool() {
        int pid = 0;
        List<Sys_organize> SchoolList = excelService.findSchool(pid);
        return SchoolList;
    }

    /***
     * 查询年级（第二级联动）
     * @return
     */
    @RequestMapping("/findNj")
    @ResponseBody
    public List<Sys_organize> findNj(int pid) {
        List<Sys_organize> NjList = excelService.findNj(pid);
        return NjList;
    }

    /***
     * 查询班级（第三级联动）
     * @return
     */
    @RequestMapping("/findBj")
    @ResponseBody
    public List<Sys_organize> findBj(int pid) {
        List<Sys_organize> BjList = excelService.findBj(pid);
        return BjList;
    }

    /***
     * 查询班级（第三级联动）
     * @return
     */
    @RequestMapping("/findYj")
    @ResponseBody
    public List<Sys_organize> findYj(int pid) {
        List<Sys_organize> YjList = excelService.findYj(pid);
        return YjList;
    }

    /***
     * 根据不同情况查询学生信息，有个细节，在判断学校年级班级参数的时候，应该倒过来，在某种意义上，减少判断次数
     * @param school
     * @param Nj
     * @param Bj
     * @param name
     * @param code
     * @param response
     * @param pageNum
     * @param model
     * @return
     */
    @RequestMapping("/findStudent")
    @ResponseBody
    public Object findStudent(String school, String Nj, String Bj, String name,String Yj, String code, HttpServletResponse response, String pageNum, Model model) {

        Map map = excelService.findStudentBy(school,Nj,Bj,name,Yj,code,response,pageNum);
        return map;
    }
    /**s
     * 导出Excel（Teacher）
     */
    @RequestMapping("/ExcelDownTeacher")
    public String ExcelDown(HttpServletResponse response, HttpServletRequest request) {
        try {
            //创建Excel工作簿
            XSSFWorkbook book = new XSSFWorkbook();
            // 字体样式
            XSSFFont xssfFont = book.createFont();
            // 加粗
            xssfFont.setBold(true);
            // 字体名称
            xssfFont.setFontName("宋体");
            // 字体大小
            xssfFont.setFontHeight(12);
            //创建样式
            XSSFCellStyle cellStyle = book.createCellStyle();
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setFont(xssfFont);
            //创建Sheet对象
            XSSFSheet sheet = book.createSheet("Sheet1");
            //规定合并
            CellRangeAddress callRangeAddress = new CellRangeAddress(0,0,0,7);//起始行,结束行,起始列,结束列
            //实现合并
            sheet.addMergedRegion(callRangeAddress);
            XSSFRow row1=sheet.createRow(0);
            XSSFCell row1Cell1 =row1.createCell(0);
            //下边框并且水平+高度居中
            row1Cell1.setCellValue("XX市在职教师花名册");
            row1Cell1.setCellStyle(cellStyle);
            //创建表格的行
            XSSFRow row = sheet.createRow(1);
            //创建第二行第一列
            XSSFCell rowCell0 = row.createCell(0);
            //下边框并且水平+高度居中
            rowCell0.setCellValue("XX省");
            rowCell0.setCellStyle(cellStyle);
            //第二行第二列
            XSSFCell rowCell1 = row.createCell(1);
            rowCell1.setCellValue("XX市");
            rowCell1.setCellStyle(cellStyle);
            //第二行第三列
            XSSFCell rowCell2 = row.createCell(2);
            rowCell2.setCellValue("XX县（区）");
            rowCell2.setCellStyle(cellStyle);
            //第二行第四列
            XSSFCell rowCell3 = row.createCell(3);
            rowCell3.setCellValue("XX镇");
            rowCell3.setCellStyle(cellStyle);
            //第二行第五列
            XSSFCell rowCell4 = row.createCell(4);
            rowCell4.setCellValue("XX村");
            rowCell4.setCellStyle(cellStyle);
            //创建第三行
            XSSFRow row2 = sheet.createRow(2);
            row2.createCell(0).setCellValue("序号");
            row2.createCell(1).setCellValue("学校名称");
            row2.createCell(2).setCellValue("姓名");
            row2.createCell(3).setCellValue("性别");
            row2.createCell(4).setCellValue("身份证号码");
            row2.createCell(5).setCellValue("手机号码");
            row2.createCell(6).setCellValue("是否是校长");
            row2.createCell(7).setCellValue("注:只可导入同一所学校老师");
            //response.setHeader("Content-disposition", "attachment;filename=Teacher.xlsx");
            //输出信息
            File temDir = new File("D:/Zip");
            if(!temDir.exists()){
                temDir.mkdirs();
            }
            FileOutputStream fout = new FileOutputStream("D:/Zip/老师.xlsx");

            book.write(fout);
            fout.close();

            //创建StudentExcel工作簿
            XSSFWorkbook studentb = new XSSFWorkbook();
            // 字体样式
            XSSFFont xssfFontStu = studentb.createFont();
            // 加粗
            xssfFontStu.setBold(true);
            // 字体名称
            xssfFontStu.setFontName("宋体");
            // 字体大小
            xssfFontStu.setFontHeight(12);
            //创建样式
            XSSFCellStyle StudentSty = studentb.createCellStyle();
            StudentSty.setVerticalAlignment(VerticalAlignment.CENTER);
            StudentSty.setAlignment(HorizontalAlignment.CENTER);
            StudentSty.setFont(xssfFont);
            //创建Sheet对象
            XSSFSheet studentSheet = studentb.createSheet("Sheet1");
            //规定合并
            CellRangeAddress callRangeAddress1 = new CellRangeAddress(0,0,0,8);//起始行,结束行,起始列,结束列
            //实现合并
            studentSheet.addMergedRegion(callRangeAddress1);
            XSSFRow rowstu=studentSheet.createRow(0);
            XSSFCell row1Cellstu0 =rowstu.createCell(0);
            //下边框并且水平+高度居中
            row1Cellstu0.setCellValue("XX市XXXX在校学生花名册");
            row1Cellstu0.setCellStyle(StudentSty);
            //创建表格的行
            XSSFRow rowstu1 = studentSheet.createRow(1);
            //创建第二行第一列
            XSSFCell rowCellstu0 = rowstu1.createCell(0);
            //下边框并且水平+高度居中
            rowCellstu0.setCellValue("XX省");
            rowCellstu0.setCellStyle(StudentSty);
            //第二行第二列
            XSSFCell rowCell11 = rowstu1.createCell(1);
            rowCell11.setCellValue("XX市");
            rowCell11.setCellStyle(StudentSty);
            //第二行第三列
            XSSFCell rowCell22 = rowstu1.createCell(2);
            rowCell22.setCellValue("XX县（区）");
            rowCell22.setCellStyle(StudentSty);
            //第二行第四列
            XSSFCell rowCell33 = rowstu1.createCell(3);
            rowCell33.setCellValue("XX镇");
            rowCell33.setCellStyle(StudentSty);
            //第二行第五列
            XSSFCell rowCell44 = rowstu1.createCell(4);
            rowCell44.setCellValue("XX村");
            rowCell44.setCellStyle(StudentSty);
            //第二行第五列
            XSSFCell rowCell55 = rowstu1.createCell(5);
            rowCell55.setCellValue("XX学校");
            rowCell55.setCellStyle(StudentSty);
            //创建第三行
            XSSFRow row22 = studentSheet.createRow(2);
            row22.createCell(0).setCellValue("序号");
            row22.createCell(1).setCellValue("班级");
            row22.createCell(2).setCellValue("姓名");
            row22.createCell(3).setCellValue("性别");
            row22.createCell(4).setCellValue("学籍号");
            row22.createCell(5).setCellValue("身份证号码");
            row22.createCell(6).setCellValue("年级");
            row22.createCell(7).setCellValue("注:请最少填写省市及学校");
            /*CellRangeAddress yuanxi = new CellRangeAddress(2,2,6,8);//起始行,结束行,起始列,结束列
            //合并mobile单元格
            studentSheet.addMergedRegion(yuanxi);*/
            //response.setHeader("Content-disposition", "attachment;filename=Teacher.xlsx");
            //输出信息
           /* File temDir = new File("D:/Zip");
            if(!temDir.exists()){
                temDir.mkdirs();
            }*/
            FileOutputStream fout1 = new FileOutputStream("D:/Zip/学生.xlsx");

            studentb.write(fout1);
            fout1.close();
            //设置自动刷新通道
            //response.getOutputStream().flush();  response.getOutputStream()
            //设置头部信息以及内容
            response.setContentType("application/zip;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=excel.zip");
            ZipUtils.toZip(temDir.getPath(), response.getOutputStream(),false);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return "redirect:/Excel/index";
    }
    /**
     * 导出错误信息（Error）
     */
    @RequestMapping("/errordc")
    public String errordc(String studentListError,String teacherListError,int isStuOrTea,HttpServletResponse response,String message) {
        if(isStuOrTea==0){
            excelService.exportStudent(studentListError,response,message);
        }else{
            excelService.export(teacherListError,response,message);
        }
        return "21211111";
    }
}
