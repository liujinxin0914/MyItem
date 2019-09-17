package com.chutianlong.service;

import com.chutianlong.dao.ExcelDao;
import com.chutianlong.pojo.Student;
import com.chutianlong.pojo.Sys_organize;
import com.chutianlong.pojo.Teacher;
import com.chutianlong.util.IdCardUtil;
import com.chutianlong.util.JSONUtil;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

@Service
public class ExcelServiceIM implements ExcelService {
    @Autowired
    private ExcelDao excelDao;

    @Override
    public List<Sys_organize> findSchool(int pid ) {
        return excelDao.findSchool(pid);
    }

    @Override
    public List<Sys_organize> findNj(int pid) {
        return excelDao.findNj(pid);
    }

    @Override
    public List<Sys_organize> findBj(int pid) {
        return excelDao.findBj(pid);
    }

    @Override
    public List<Sys_organize> findYj(int pid) { return excelDao.findYj(pid); }

    @Override
    public Map<String, Object> studentExcelDR(HttpSession session, MultipartFile productData,Model model) {
        // 运行时存储的路径
        String saveDirPath = session.getServletContext().getRealPath("/WEB-INF/runtime/temp");
        // 创建目录
        File file = new File(saveDirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        // 文件路径
        String saveFilePath = saveDirPath + "/" + productData.getOriginalFilename();
        // 删除之前上传文件
        file = new File(saveFilePath);
        if (file.exists()) {
            file.delete();
        }
        try {
            // 保存文件
            productData.transferTo(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new HashMap<String,Override>();
        // 保存路径
       model.addAttribute("saveFilePath", saveFilePath);
        // Excel的对象
        XSSFWorkbook book = null;
        try {
            // 文件的输入流
            FileInputStream fis = new FileInputStream(file);
            // 创建Excel的对象
            book = new XSSFWorkbook(fis);
            // 关闭文件输入
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 读取表单
        XSSFSheet sheet = book.getSheet("Sheet1");

        Map<String, Object> errormap = new LinkedHashMap<String, Object>();

        //判断是否是Excel学生或者老师
        try {
            if(null==sheet.getRow(0)){
                errormap.put("系统错误","Excel不正确");
                return errormap;
            }
            XSSFRow row2 = sheet.getRow(0);
            XSSFCell cell = row2.getCell(0);
            if(null==cell||"".equals(cell)){
                errormap.put("系统错误","Excel不正确");
                return errormap;
            }else{
                String ExcelHead = cell.toString();
                if(!ExcelHead.contains("学生花名册")){
                    errormap.put("系统错误","Excel不正确");
                    return errormap;
                }
            }
        } catch (NullPointerException e) {
            errormap.put("系统错误","Excel不正确");
            return errormap;
        }


        // 开始行号
        int beginRowIndex = 3;
        // 总计行数
        int totalRow = sheet.getLastRowNum();
        long l = System.currentTimeMillis();
        //创建省市区等信息列表，方便导出使用
        StringBuilder builder = new StringBuilder();


        //开始读取第一行并声明schoolname；
        StringBuffer name = new StringBuffer();
        XSSFRow row1 = sheet.getRow(1);
        //省
        XSSFCell provinceCell = row1.getCell(0);
        String Province = provinceCell.toString();
        builder.append(Province);
        if (!Province.contains("XX")) {
            name.append(Province);
        }
        //市
        XSSFCell marketCell = row1.getCell(1);
        String Market = marketCell.toString();
        builder.append(","+Market);
        //区
        XSSFCell quCell = row1.getCell(2);
        String qu = quCell.toString();
        builder.append(","+qu);
        //镇
        XSSFCell zhenCell = row1.getCell(3);
        String zhen = zhenCell.toString();
        builder.append(","+zhen);
        //村
        XSSFCell cunCell = row1.getCell(4);
        String cun = cunCell.toString();
        builder.append(","+cun);
        if (!Market.contains("XX")) {
            name.append(Market);
            //区
            XSSFCell countyCell = row1.getCell(2);
            String County = countyCell.toString();
            if (!County.contains("XX")) {
                name.append(County);
                //镇
                XSSFCell townCell = row1.getCell(3);
                String Town = townCell.toString();
                if (!Town.contains("XX")) {
                    name.append(Town);
                    //村
                    XSSFCell villageCell = row1.getCell(4);
                    String Village = villageCell.toString();
                    if (!Village.contains("XX")) {
                        name.append(Village);
                        //学校名称
                    }
                }
            }
        }
        XSSFCell schoolCell = row1.getCell(5);
        String Schoolname = null;
        if(schoolCell==null||"".equals(schoolCell)){
            errormap.put("重大错误","没有填写学校");
            return errormap;
        }else{
            String School = schoolCell.toString();
            builder.append(","+School);
            name.append(School);
            Schoolname = name.toString();
            Object obj = excelDao.findSchoolIsNot(Schoolname);
            if(obj==null){
                excelDao.importSchoolName(Schoolname);
            }
        }
        String message = builder.toString();
        // 学生列表，作为批量插入的条件
        List<Student> studentList = new ArrayList<Student>();
        //定义一个error列表，来导出使用
        List<Student> studentListError = new ArrayList<>();
        //这几个定义是显示成功，错误条数
        int count = 0;
        int errorcount=0;int successcount=0;

        List<Student> idcardList = excelDao.findidcard();
        List<Student> codeList = excelDao.findCode();
        int yid=0;
        //yid为学校id
        yid = excelDao.findPid(Schoolname);
        //年级所有信息
        List<Sys_organize> gradeList = excelDao.findgrade(yid);
        //班级所有信息
        List<Sys_organize> classList = excelDao.findClass(yid);

        //开始导入
        for (int rowIndex = beginRowIndex; rowIndex <=totalRow; rowIndex++) {

            int error=0;
            // 读取行的对象
            XSSFRow row = sheet.getRow(rowIndex);
            if (row == null||row.equals("")||row.equals(" ")) {
                continue;
            }
            if(row.getCell(0)==null|| "".equals(row.getCell(0))&&row.getCell(1)==null|| "".equals(row.getCell(1))&&row.getCell(2)==null|| "".equals(row.getCell(2))&&row.getCell(3)==null|| "".equals(row.getCell(3))&&row.getCell(4)==null|| "".equals(row.getCell(4))&&row.getCell(5)==null|| "".equals(row.getCell(5))&&row.getCell(6)==null|| "".equals(row.getCell(6))){
                continue;
            }
            count++;
            // 学生对象
            Student student = new Student();
            // 加入列表
            XSSFCell xuhaoCell = row.getCell(0);
            if(null==xuhaoCell||"".equals(xuhaoCell)){
                errormap.put("序号","不能为空");
                error++;
            }
            String xuhao = null;
            if(null!=xuhaoCell&&!"".equals(xuhaoCell)){
                String subString = xuhaoCell.toString();
                subString=subString.replaceAll(" ","");
                if(!subString.equals("")&&null!=subString){
                    xuhao = subString.substring(0,subString.indexOf("."));
                }
            }

            // 学生姓名
            XSSFCell nameCell = row.getCell(2);
            if(null==nameCell||"".equals(nameCell)){
                errormap.put("序号"+xuhao,"姓名不能为空");
                error++;
            }
            if(null!=nameCell&&!"".equals(nameCell)){
                String nameObject = nameCell.toString();
                student.setName(nameObject);
            }
            // 学生性别
            int sex1 = 0;
            XSSFCell sexCell = row.getCell(3);
            if(sexCell!=null&&!"".equals(sexCell)){
                String sex = sexCell.toString();
                if (sex.equals("男")) {
                    sex1 = 1;
                    if(error>0){
                        student.setSex(sex1);
                    }
                }
                if (sex.equals("女")) {
                    sex1 = 0;
                    if(error>0){
                        student.setSex(sex1);
                    }
                }
            }
            student.setSex(sex1);
            // 身份证号
            XSSFCell idcardCell = row.getCell(5);
            String idcard1 = null;
            if(idcardCell!=null&&!"".equals(idcardCell)){
                idcard1=idcardCell.toString();
            }else{
                errormap.put("序号"+xuhao,"身份证号不能为空");
                error++;
            }
            for (int i = 0;i<idcardList.size();i++){
                if(idcardList.get(i).getIdcard()==idcard1){
                    errormap.put("序号"+xuhao,"身份证号码重复");
                    error++;
                    if(error>0){
                        student.setIdcard(idcard1);
                    }
                    break;
                }
            }
            //int code = (int)Double.parseDouble(code1);
            if (idcard1 != null && !idcard1.equals("")) {
                if(idcard1.length()==18){
                    String idcard = idcard1.substring(16, 17);
                    int idcardd = (int)Double.parseDouble(idcard);
                    if (idcardd%2== sex1) {
                        student.setIdcard(idcard1);
                    }else{
                        errormap.put("序号"+xuhao,"身份证号与性别不符");
                        error++;
                        if(error>0){
                            student.setIdcard(idcard1);
                        }
                    }
                }else {
                    errormap.put("序号"+xuhao,"身份证格式不正确");
                    error++;
                    if(error>0){
                        student.setIdcard(idcard1);
                    }
                }
            }else{
                errormap.put("序号"+xuhao,"身份证号不能为空");
                error++;
                if(error>0){
                    student.setIdcard(idcard1);
                }
            }
            String code1=null;
            // 学籍号
            XSSFCell codeCell = row.getCell(4);
            if(codeCell==null||"".equals(codeCell)){
                errormap.put("序号"+xuhao,"学籍号不能为空");
                error++;

            }else{
                code1 = codeCell.toString();
                if(code1.length()>9){
                    errormap.put("序号"+xuhao,"学籍号不能大于8位数");
                    error++;
                    if(error>0){
                        student.setCode(code1);
                    }
                }else{
                        if(code1.contains(".")){
                            String code = code1.substring(0,code1.indexOf("."));
                            /*int studentCode = excelDao.findCode(code);*/
                            int j = 0;
                            for(int i = 0;i<codeList.size();i++){
                                if (codeList.get(i).getCode().equals(code)){
                                    errormap.put("序号"+xuhao,"学籍号重复");
                                    error++;
                                    ++j;
                                    if(error>0){
                                        student.setCode(code1);
                                    }
                                    break;
                                }
                            }
                            if(j==0){
                                student.setCode(code);
                            }
                        }else{
                            student.setCode(code1);
                        }
                }
            }


            // 年级
            String yuanxi=null;
            XSSFCell YuanCell = row.getCell(6);
            if(null==YuanCell||"".equals(YuanCell)){
                errormap.put("序号"+xuhao,"年级不能为空");
                error++;

            }
            int nianjiId=0;
            if(null!=YuanCell&&!"".equals(YuanCell)){
                yuanxi = YuanCell.toString();

                if(!yuanxi.isEmpty()){
                    int panduan=0;
                    for(int i = 0;i<gradeList.size();i++){
                        //先判断年级是否为空，如果为空，那就创建，然后把信息存进list
                        if(gradeList.get(i).getName().equals(yuanxi)){
                            nianjiId=gradeList.get(i).getId();
                            ++panduan;
                        }
                    }
                    if(panduan==0){
                        excelDao.addYuanxi(yuanxi,yid);
                        Sys_organize ss = excelDao.findNianji(yuanxi,yid);
                        nianjiId=ss.getId();
                        gradeList.add(ss);
                    }
                }

            }


            // 班级
            XSSFCell idCell = row.getCell(1);
            if(null==idCell||"".equals(idCell)){
                errormap.put("序号"+xuhao,"班级不能为空");
                error++;
            }
            String ids=null;
            if(null!=idCell&&!"".equals(idCell)){
                 ids = idCell.toString();
                if(!ids.isEmpty()){
                    int panduan=0;
                    for(int i = 0;i<classList.size();i++){
                        //判断name属性和pid是否与年级一致
                        if(classList.get(i).getName().equals(ids)&&classList.get(i).getPid()==nianjiId){
                            student.setClasses_id(classList.get(i).getId());
                            ++panduan;
                        }
                    }
                    //如果还等于0，说明班级不存在，不存在就创建
                    if(panduan==0){
                        excelDao.addClasses(ids,nianjiId);
                        Sys_organize classid1 = excelDao.findclassid(ids,nianjiId);
                        student.setClasses_id(classid1.getId());
                        classList.add(classid1);
                    }
                }
            }else{
                errormap.put("序号"+xuhao,"班级不能为空");
                error++;
            }


            if(error>0){
                ++errorcount;
            }else{
                ++successcount;
            }
            // 保存数据
            String password = "670B14728AD9902AECBA32E22FA4F6BD";
            student.setPassword(password);
            //////////////////////////   excelDao.importStudent(student);
            if(student.getCode()!=null&&student.getCode()!=""&&error==0){
                studentList.add(student);
            }
            if(error>0){
                studentListError.add(student);
            }
        }

        long m = System.currentTimeMillis();
        System.err.println(m-l);
        int isStuOrTea = 0;
        if(studentList.size()==0){
            errormap.put("totalRow",count);
            errormap.put("errorcount",errorcount);
            errormap.put("successcount",successcount);
            errormap.put("studentListError",studentListError);
            errormap.put("isStuOrTea",isStuOrTea);
            errormap.put("message",message);
            return errormap;
        }else{
            excelDao.importStudent(studentList);
        }
        errormap.put("totalRow",count);
        errormap.put("errorcount",errorcount);
        errormap.put("successcount",successcount);
        errormap.put("studentListError",studentListError);
        errormap.put("isStuOrTea",isStuOrTea);
        errormap.put("message",message);
        return errormap;
    }
    /***
     * 老师导入Excel
     *
     * @param session
     * @param productData
     * @param model
     */
    @Override
    public Map<String, Object> teacherExcelDR(HttpSession session, MultipartFile productData, Model model) {
// 运行时存储的路径
        String saveDirPath = session.getServletContext().getRealPath("/WEB-INF/runtime/temp");
        // 创建目录
        File file = new File(saveDirPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        // 文件路径
        String saveFilePath = saveDirPath + "/" + productData.getOriginalFilename();

        // 删除之前上传文件
        file = new File(saveFilePath);
        if (file.exists()) {
            file.delete();
        }
        try {
            // 保存文件
            productData.transferTo(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 保存路径
        model.addAttribute("saveFilePath", saveFilePath);
        // Excel的对象
        XSSFWorkbook book = null;
        try {
            // 文件的输入流
            FileInputStream fis = new FileInputStream(file);
            // 创建Excel的对象
            book = new XSSFWorkbook(fis);
            // 关闭文件输入流
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 读取表单
        XSSFSheet sheet = book.getSheet("Sheet1");
        Map<String, Object> errormap = new LinkedHashMap<>();
        try {
            if(null==sheet.getRow(0)){
                errormap.put("系统错误","Excel不正确");
                return errormap;
            }
            //判断是否是Excel学生或者老师
            XSSFRow row2 = sheet.getRow(0);
            XSSFCell cell = row2.getCell(0);
            if(null==cell||"".equals(cell)){
                errormap.put("系统错误","Excel不正确");
                return errormap;
            }else{
                String ExcelHead = cell.toString();
                if(!ExcelHead.contains("教师花名册")){
                    errormap.put("系统错误","Excel不正确");
                    return errormap;
                }
            }
        } catch (NullPointerException e) {
            errormap.put("系统错误","Excel不正确");
            return errormap;
        }
        // 开始行号
        int beginRowIndex = 3;
        // 总计行数
        int totalRow = sheet.getLastRowNum();
        // 定义老师列表用来添加还有一个error用来导出错误信息
        List<Teacher> teacherListError = new ArrayList<>();
        List<Teacher> teacheList = new ArrayList<>();

        //这几个定义是显示成功，错误条数
        int count=0;
        int errorcount=0;int successcount=0;

        long l = System.currentTimeMillis();
        XSSFRow rowsc = sheet.getRow(3);
        StringBuffer stringBuffer = new StringBuffer();
        // 学校名称
        String schoolObject1=null;
        XSSFCell titleCell = rowsc.getCell(1);
        if (titleCell == null || "".equals(titleCell)) {
            errormap.put("序号","学校不能为空");
        }
        if (titleCell != null && !"".equals(titleCell)) {
             schoolObject1 = titleCell.toString();
            stringBuffer.append(schoolObject1);
        }

        //开始读取第一行并声明schoolname；
        StringBuffer nameaa = new StringBuffer();
        XSSFRow row11 = sheet.getRow(1);
        Sys_organize organize = new Sys_organize();
        //省
        XSSFCell provinceCell = row11.getCell(0);
        String Province = provinceCell.toString();
        stringBuffer.append(","+Province);
        //市
        XSSFCell shiCell = row11.getCell(1);
        String shi = shiCell.toString();
        stringBuffer.append(","+shi);
        //区、、、
        XSSFCell quCell = row11.getCell(2);
        String qu = quCell.toString();
        stringBuffer.append(","+qu);
        //镇
        XSSFCell zhenCell = row11.getCell(3);
        String zhen = zhenCell.toString();
        stringBuffer.append(","+zhen);
        //村
        XSSFCell cunCell = row11.getCell(4);
        String cun = cunCell.toString();
        stringBuffer.append(","+cun);
        String message = stringBuffer.toString();
        if (!Province.contains("XX")) {
            //市
            XSSFCell marketCell = row11.getCell(1);
            String Market = marketCell.toString();
            if (!Market.contains("XX")) {
                nameaa.append(Province);
                nameaa.append(Market);
                //区、、、
                XSSFCell countyCell = row11.getCell(2);
                String Countys = countyCell.toString();
                if (!Countys.contains("XX")) {
                    nameaa.append(Countys);
                    //镇
                    XSSFCell townCell = row11.getCell(3);
                    String Town = townCell.toString();
                    if (!Town.contains("XX")) {
                        nameaa.append(Town);
                        //村
                        XSSFCell villageCell = row11.getCell(4);
                        String Villages = villageCell.toString();
                        if (!Villages.contains("XX")) {
                            nameaa.append(Villages);
                            //学校名称
                        }
                    }
                }
            }
        }
        String schoolObject = nameaa.append(schoolObject1).toString();
        //查询学校是否为空
        Sys_organize sys = excelDao.findSchoolByName(schoolObject);
        int aa = 0;
        if (sys != null) {
            //如果学校不为空，那就把学校id赋予给老师
            aa = sys.getId();
        } else {
            //否则创建一个学校然后把创建学校的id赋给老师
            excelDao.addSchool(schoolObject);
            sys = excelDao.findSchoolByName(schoolObject);
            aa = sys.getId();
        }
        int codeNo = 0;
        for (int rowIndex = beginRowIndex; rowIndex <= totalRow; rowIndex++) {
            int error=0;
            // 读取行的对象
            XSSFRow row = sheet.getRow(rowIndex);
            if (row == null||row.equals("")) {
                continue;
            }
            if(row.getCell(0)==null|| "".equals(row.getCell(0))&&row.getCell(1)==null|| "".equals(row.getCell(1))&&row.getCell(2)==null|| "".equals(row.getCell(2))&&row.getCell(3)==null|| "".equals(row.getCell(3))&&row.getCell(4)==null|| "".equals(row.getCell(4))&&row.getCell(5)==null|| "".equals(row.getCell(5))&&row.getCell(6)==null|| "".equals(row.getCell(6))){
                continue;
            }
            count++;
            // 老师对象
            Teacher teacher = new Teacher();
            Teacher teacherError = new Teacher();
            //序号
            XSSFCell xuhaoCell = row.getCell(0);
            String subString = null;
            if(xuhaoCell==null||"".equals(xuhaoCell)){
                errormap.put("序号","不能为空");
                error++;
            }else{
                subString = xuhaoCell.toString();
                if(subString.isEmpty()){
                    errormap.put("序号","不能为空");
                    error++;
                }
            }

            String xuhao=null;
            if(subString!=null&&!subString.equals("")){
                subString=subString.replaceAll(" ","");
                if(!subString.equals("")&&null!=subString){
                    xuhao = subString.substring(0,subString.indexOf("."));
                }
            }

            // 性别
            XSSFCell sexCell = row.getCell(3);
            if(sexCell==null||"".equals(sexCell)){
                errormap.put("序号"+xuhao,"性别不能为空");
                error++;
            }
            String sex1 =null;
            int sex = 0;
            if(sexCell!=null&&!"".equals(sexCell)){
                sex1=sexCell.toString();

                if (sex1.equals("男")) {
                    sex = 1;
                    if(error>0){
                        teacherError.setSex(sex);
                    }
                }
                if (sex1.equals("女")) {
                    sex = 0;
                    if(error>0){
                        teacherError.setSex(sex);
                    }
                }
                teacher.setSex(sex);
            }

            // 教师身份证号
            XSSFCell idCell = row.getCell(4);
            if(idCell!=null&&!idCell.equals("")){
                String id = idCell.toString();
                if (id != null && !id.equals("")) {
                    if(id.length()==18){
                        String idcard = id.substring(16, 17);
                        int idcardd = (int)Double.parseDouble(idcard);
                        if (idcardd%2== sex) {
                            if(IdCardUtil.isValidatedAllIdcard(id)){
                                int i = excelDao.findTeacherIdcard(id);
                                if(i==0){
                                    teacher.setIdcard(id);
                                }else{
                                    errormap.put("序号"+xuhao,"身份证号重复");
                                    error++;
                                    teacherError.setIdcard(id);
                                    codeNo++;
                                }
                            }else{
                                errormap.put("序号"+xuhao,"身份证号不正确");
                                error++;
                                teacherError.setIdcard(id);
                            }
                        }else{
                            errormap.put("序号"+xuhao,"身份证号与性别不符");
                            error++;
                            teacherError.setIdcard(id);
                        }
                    }else {
                        errormap.put("序号"+xuhao,"身份证格式不正确");
                        error++;
                        teacherError.setIdcard(id);
                    }
                }else{
                    errormap.put("序号"+xuhao,"身份证号不能为空");
                    error++;
                }
            }

            teacher.setOrg_id(aa);
            if(error>0){
                teacherError.setOrg_id(aa);
            }
            // 姓名
            XSSFCell nameCell = row.getCell(2);
            if(null==nameCell||"".equals(nameCell)){
                errormap.put("序号"+xuhao,"姓名不能为空");
                error++;
            }
            String name = nameCell.toString();
            if(name.isEmpty()){
                errormap.put("序号"+xuhao,"姓名不能为空");
                error++;
            }
            if(error>0){
                teacherError.setName(name);
            }
            teacher.setName(name);
            // 手机号
            if(row.getCell(5)!=null){
                row.getCell(5).setCellType(CellType.STRING);
                XSSFCell mobileCell = row.getCell(5);
                if(null!=mobileCell&&!"".equals(mobileCell)){
                    String mobile =mobileCell.toString();
                    String moblie1 = mobile.replace(".","");
                    if(moblie1.length()>10){
                        String moblie2 =moblie1.substring(0,11);
                        //验证是否包含英文
                        String regex1 = ".*[a-zA-z].*";
                        boolean English = moblie2.matches(regex1);
                        if(!English){
                            int mobile1 = (int)Double.parseDouble(moblie2);
                            if(mobile1!=0){
                                teacher.setMobile(moblie2);
                                if(error>0){
                                    teacherError.setMobile(moblie2);
                                }
                            }
                        }else{
                            errormap.put("序号"+xuhao,"手机号格式不正确");
                            error++;
                            teacherError.setMobile(moblie2);
                        }
                    }else{
                        errormap.put("序号"+xuhao,"手机号格式不正确");
                        error++;
                        teacherError.setMobile(moblie1);
                    }
                }else{
                    errormap.put("序号"+xuhao,"手机号不能为空");
                    error++;
                }
            }
            // 校长判断
            int isMaster=0;
            XSSFCell isCell = row.getCell(6);
            if(isCell!=null&&!isCell.equals("")){
                String master = isCell.toString();
                if(master.equals("是")){
                    isMaster=1;
                }
                if(error>0){
                    teacherError.setIsmaster(isMaster);
                }
                teacher.setIsmaster(isMaster);
            }
            /*// 保存数据
            UUID uuid = UUID.randomUUID();
            String str = uuid.toString();
           // 去掉"-"符号
             String temp = str.substring(0, 8) +str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) +str.substring(24);
             teacher.setOnly(temp);*/
            //判断是否是校长，如果是校长，就赋值密码
            if(isMaster==1){
                String password = "670B14728AD9902AECBA32E22FA4F6BD";
                teacher.setPassword(password);
            }
             //如果error>0,则表示改行存在不符合规则的信息
            if(error>0){
                ++errorcount;
            }else{
                ++successcount;
            }
             //这里要判断一下idcard是否为空，不然添加到集合之后，再添加数据库，会报主键不可为null的错
            if(teacher.getIdcard()!=null&&teacher.getIdcard()!=""&&error==0){
                teacheList.add(teacher);
            }
            if(error>0){
                teacherListError.add(teacherError);
            }

        }
        long m = System.currentTimeMillis();
        System.err.println(m-l);
        int isStuOrTea = 1;
        if(teacheList.size()==0){
            errormap.put("totalRow",count);
            errormap.put("errorcount",errorcount);
            errormap.put("successcount",successcount);
            errormap.put("teacherListError",teacherListError);
            errormap.put("isStuOrTea",isStuOrTea);
            errormap.put("message",message);
            return errormap;
        }else{
            excelDao.importProduct(teacheList);
        }
        errormap.put("totalRow",count);
        errormap.put("errorcount",errorcount);
        errormap.put("successcount",successcount);
        errormap.put("teacherListError",teacherListError);
        errormap.put("isStuOrTea",isStuOrTea);
        errormap.put("message",message);
        return errormap;
    }

    @Override
    public Map findStudentBy(String school, String Nj, String Bj, String name, String Yj,String code, HttpServletResponse response, String pageNum) {
        Map map = new HashMap<String, Object>();
        if(code!=null&&!"".equals(code)){
            List data = excelDao.getStudentByCode(code);
            map.put("data",data);
            return map;
        }
        if(name!=null&&!"".equals(name)){
            List data = excelDao.getStudentByName(name);
            map.put("data",data);
            return map;
        }

        //默认起步为1
        int pagenum = 1;
        //如果不为空则转换当前页数
        if (pageNum != null && !pageNum.equals("")) {
            if ((int) Double.parseDouble(pageNum) != 0) {
                pagenum = (int) Double.parseDouble(pageNum);
            }
        }
        int nj = 0;
        int bj = 0;
        //根据学校查询
        if (school != null && !school.equals("")) {
            int schools = (int) Double.parseDouble(school);
            //根据院系查询
            //if(Yj!=null&&!Yj.equals("")){
               // yj = (int) Double.parseDouble(Yj);
                if (Nj != null && !Nj.equals("")) {
                    if (Bj != null && !Bj.equals("")) {
                        bj = (int) Double.parseDouble(Bj);
                        //求总数
                        int count = excelDao.getCountB(bj);
                        int pageSize = 0;
                        if (count % 10 == 0) {
                            pageSize = count / 10;
                        } else if (count % 10 != 0) {
                            pageSize = (count / 10) + 1;
                        }
                        PageHelper.startPage(pagenum, 10);
                        List data = excelDao.findStudentBybj(bj, name, code);
                        map.put("data", data);
                        map.put("pageNum", pagenum);
                        map.put("pageSize", pageSize);
                        return map;
                    }
                    nj = (int) Double.parseDouble(Nj);
                    System.out.println(nj);
                    int count = excelDao.getCountN(nj);
                    int pageSize = 0;
                    if (count % 10 == 0) {
                        pageSize = count / 10;
                    } else if (count % 10 != 0) {
                        pageSize = (count / 10) + 1;
                    }
                    PageHelper.startPage(pagenum, 10);
                    List data = excelDao.findStudentBynj(nj, name, code);
                    map.put("data", data);
                    map.put("pageNum", pagenum);
                    map.put("pageSize", pageSize);
                    return map;
                }
            int count = excelDao.getCount(schools);
            int pageSize = 0;
            if (count % 10 == 0) {
                pageSize = count / 10;
            } else if (count % 10 != 0) {
                pageSize = (count / 10) + 1;
            }
            PageHelper.startPage(pagenum, 10);
            List data = excelDao.findStudent(schools, name, code);
            map.put("data", data);
            map.put("pageNum", pagenum);
            map.put("pageSize", pageSize);
            return map;
        }
        return map;
    }

    @Override
    public void export(String teacherListError, HttpServletResponse response,String message) {
        List<Teacher> teachers = JSONUtil.parseArray(teacherListError,Teacher.class);
        String[] split = message.split(",");
        if (teachers.size()>0) {
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
                CellRangeAddress callRangeAddress = new CellRangeAddress(0, 0, 0, 7);//起始行,结束行,起始列,结束列
                //实现合并
                sheet.addMergedRegion(callRangeAddress);
                XSSFRow row1 = sheet.createRow(0);
                XSSFCell row1Cell1 = row1.createCell(0);
                //下边框并且水平+高度居中
                row1Cell1.setCellValue("XX市在职教师花名册");
                row1Cell1.setCellStyle(cellStyle);
                //创建表格的行
                XSSFRow row = sheet.createRow(1);
                //创建第二行第一列
                XSSFCell rowCell0 = row.createCell(0);
                //下边框并且水平+高度居中
                rowCell0.setCellValue(split[1]);
                rowCell0.setCellStyle(cellStyle);
                //第二行第二列
                XSSFCell rowCell1 = row.createCell(1);
                rowCell1.setCellValue(split[2]);
                rowCell1.setCellStyle(cellStyle);
                //第二行第三列
                XSSFCell rowCell2 = row.createCell(2);
                rowCell2.setCellValue(split[3]);
                rowCell2.setCellStyle(cellStyle);
                //第二行第四列
                XSSFCell rowCell3 = row.createCell(3);
                rowCell3.setCellValue(split[4]);
                rowCell3.setCellStyle(cellStyle);
                //第二行第五列
                XSSFCell rowCell4 = row.createCell(4);
                rowCell4.setCellValue(split[5]);
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
                for (int i = 0; i < teachers.size(); i++) {
                    Teacher teacher = teachers.get(i);
                    XSSFRow row4 = sheet.createRow(i + 3);
                    for (int j = 0; j < 6; j++) {
                        String sex = null;
                        /*String name = excelDao.findSchoolName();*/
                        row4.createCell(0).setCellValue(i + 1);
                        row4.createCell(1).setCellValue(split[0]);
                        row4.createCell(2).setCellValue(teacher.getName());
                        if (teacher.getSex() == 0) {
                            sex = "女";
                        } else if (teacher.getSex() == 1) {
                            sex = "男";
                        }
                        row4.createCell(3).setCellValue(sex);
                        row4.createCell(4).setCellType(CellType.STRING);
                        row4.createCell(4).setCellValue(teacher.getIdcard());
                        row4.createCell(5).setCellType(CellType.NUMERIC);
                        row4.createCell(5).setCellValue(teacher.getMobile());
                        if(teacher.getIsmaster()==1){
                            row4.createCell(6).setCellValue("是");
                        }
                    }
                }
                //输出信息
                File temDir = new File("D:/Zip");
                if (!temDir.exists()) {
                    temDir.mkdirs();
                }
                /*FileOutputStream fout = new FileOutputStream("D:/Zip/老师.xlsx");*/
                OutputStream outputStream = response.getOutputStream();
                response.setContentType("application/octet-stream");
                response.setHeader("Content-disposition", "attachment;filename=Teacher.xlsx");

                //response.setContentType("application/vnd.ms-excel;charset=UTF-8");// 定义输出类型
                book.write(outputStream);
                outputStream.close();

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

    }

    @Override
    public void exportStudent(String studentListError, HttpServletResponse response,String message) {
        List<Student> students = JSONUtil.parseArray(studentListError, Student.class);
        String[] split = message.split(",");
        LinkedMap map = new LinkedMap();
        if (students.size() > 0) {
        try {

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
            StudentSty.setFont(xssfFontStu);
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
            rowCellstu0.setCellValue(split[0]);
            rowCellstu0.setCellStyle(StudentSty);
            //第二行第二列
            XSSFCell rowCell11 = rowstu1.createCell(1);
            rowCell11.setCellValue(split[1]);
            rowCell11.setCellStyle(StudentSty);
            //第二行第三列
            XSSFCell rowCell22 = rowstu1.createCell(2);
            rowCell22.setCellValue(split[2]);
            rowCell22.setCellStyle(StudentSty);
            //第二行第四列
            XSSFCell rowCell33 = rowstu1.createCell(3);
            rowCell33.setCellValue(split[3]);
            rowCell33.setCellStyle(StudentSty);
            //第二行第五列
            XSSFCell rowCell44 = rowstu1.createCell(4);
            rowCell44.setCellValue(split[4]);
            rowCell44.setCellStyle(StudentSty);
            //第二行第五列
            XSSFCell rowCell55 = rowstu1.createCell(5);
            rowCell55.setCellValue(split[5]);
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
            //yid是班级id
            int yid = students.get(0).getClasses_id();
            //根据班级去获取该班级所有的学校
            List<Sys_organize> grade = excelDao.findGradeAll(yid);
            //根据班级获取所有的班级
            List<Sys_organize> classes = excelDao.findClassAll(yid);
            System.out.println(grade);
            System.out.println(classes);
            for (int i = 0; i < students.size(); i++) {
                Student student = students.get(i);
                XSSFRow row4 = studentSheet.createRow(i + 3);
                for (int j = 0; j < 6; j++) {
                    int code = 0;
                    if(student.getCode()!=null){
                        code  = (int) Double.parseDouble(student.getCode());
                    }
                    String sex = null;
                    String nianji = null;
                    String banji = null;
                    /*String name = excelDao.findSchoolName();*/
                    row4.createCell(0).setCellValue(i + 1);
                    for(int k=0;k<classes.size();k++){
                       if(student.getClasses_id()==classes.get(k).getId()){
                           banji=classes.get(k).getName();
                           break;
                       }
                    }
                    row4.createCell(1).setCellValue(banji);
                    row4.createCell(2).setCellValue(student.getName());
                    if (student.getSex() == 0) {
                        sex = "女";
                    } else if (student.getSex() == 1) {
                        sex = "男";
                    }
                    row4.createCell(3).setCellValue(sex);
                    row4.createCell(5).setCellType(CellType.STRING);
                    row4.createCell(5).setCellValue(student.getIdcard());
                    row4.createCell(4).setCellType(CellType.STRING);
                    if(code!=0){
                        row4.createCell(4).setCellValue(student.getCode());
                    }else{
                        row4.createCell(4).setCellValue("");
                    }
                    for (int l = 0;l<grade.size();l++){
                        if(nianji==null){
                            for (int n =0;n<classes.size();n++){
                                if(classes.get(n).getPid()==grade.get(l).getId()){
                                    nianji=grade.get(l).getName();
                                    break;
                                }
                            }
                        }
                    }
                    row4.createCell(6).setCellValue(nianji);
                }
            }
            //输出信息
            File temDir = new File("D:/Zip");
            if (!temDir.exists()) {
                temDir.mkdirs();
            }

            /*FileOutputStream fout = new FileOutputStream("D:/Zip/老师.xlsx");*/
            OutputStream outputStream = response.getOutputStream();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment;filename=Student.xlsx");

            //response.setContentType("application/vnd.ms-excel;charset=UTF-8");// 定义输出类型
            studentb.write(outputStream);
            outputStream.close();
        } catch(Exception e){
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    }
}
