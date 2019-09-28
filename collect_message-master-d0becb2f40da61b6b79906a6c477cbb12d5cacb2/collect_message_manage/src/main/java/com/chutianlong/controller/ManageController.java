package com.chutianlong.controller;

import com.chutianlong.pojo.*;
import com.chutianlong.service.ManageService;
import com.chutianlong.util.IdCardUtil;
import com.chutianlong.util.MD5Utils;
import com.github.pagehelper.PageHelper;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.LinkedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/Manage")
public class ManageController {
    @Autowired
    private ManageService manageService;

    @RequestMapping("/index")
    public String index(String org_id, Model model){
        model.addAttribute("org_id",org_id);
/*        model.addAttribute("classname",classname);*/
        return "manage";
    }
    @RequestMapping("/index1")
    public String index1(HttpServletRequest request){
        return "fileUpAndDown";
    }
    @RequestMapping("/toTeacher")
    public String toTeacher(String org_id,String name,String classname,Model model){
        model.addAttribute("org_id",org_id);
        return "manageTea";
    }

    @RequestMapping("/findGrade")
    @ResponseBody
    public Object findGrade(String ids,String classname){
        //接收参数并将ids初始化
        int id =0;
        //如果该老师有管理的学校则查询
        if(ids!=null&&!"".equals(ids)){
            id = (int)Double.parseDouble(ids);
            List<Sys_organize> org = manageService.findGrade(id);
            return org;
        }else{
            return "null";
        }

    }
    @RequestMapping("/findGradeBytea")
    @ResponseBody
    public Object findGradeBytea(String ids){
        //接收参数并将ids初始化
        int id =0;
        //如果该老师有管理的学校则查询
        if(ids!=null&&!"".equals(ids)){
            id = (int)Double.parseDouble(ids);
            List<Sys_organize> org = manageService.findGradeBytea(id);
            System.out.println(org);
            return org;
        }else{
            return "null";
        }

    }

    @RequestMapping("/insert")
    @ResponseBody
    public Object addStudent(String class_id,String sex,String name,String code,String idcard,String org_id,String classname){
        if(class_id!=null&&!"".equals(class_id)){
            String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？ ]";
            Pattern p= Pattern.compile(regEx);
            Matcher m=p.matcher(name);
            if(name==null||"".equals(name)){
                return "7";
            }
            if(name.length()<2){
                return "12";
            }
            if(sex==null||"".equals(sex)){
                return "8";
            }
            if(code==null||"".equals(code)||code.contains(" ")){
                return "9";
            }
            if(idcard==null||"".equals(idcard)||idcard.contains(" ")){
                return "10";
            }
            //验证是否包含英文
            String regex1 = ".*[a-zA-z].*";
            boolean English = name.matches(regex1);
            //验证是否包含数字
            String regex2 = ".*[0-9].*";
            boolean result4 = name.matches(regex2);
            if(m.find()||result4||English){
                return "6";
            }
            int classid= (int)Double.parseDouble(class_id);
            int isSex=0;
            if(sex.equals("男")){
                isSex=1;
            }
            Student student = manageService.findOneByCode(code);
            if(student!=null){
                return "4";
            }
            Student students = manageService.findOneByIdcard(idcard);
            if(students!=null){
                return "4";
            }
            if(idcard.length()!=18){
                return "5";
            }
            String Chinese = "[\\u4e00-\\u9fa5]";
            Pattern havaCis = Pattern.compile(Chinese);
            Matcher idcardHavaCis = havaCis.matcher(idcard);
            if(idcardHavaCis.find()){
                return "5";
            }
            Matcher codeHavaCis = havaCis.matcher(code);
            if(codeHavaCis.find()){
                return "13";
            }
            int b = ((int)Double.parseDouble(idcard.substring(16, 17)));
            System.out.println(b+"a");
            System.out.println(isSex+"b");
            if(isSex%2!=b%2){
                return "5";
            }
            String password = "670B14728AD9902AECBA32E22FA4F6BD";
            int i = manageService.addStudent(class_id,isSex,name,code,idcard,password);
            JSONObject jsonObject = new JSONObject();
            if(i!=0){
                jsonObject.put("i",i);
            }
            jsonObject.put("org_id",org_id);
            jsonObject.put("classname",classname);
            return jsonObject;
        }else{
            return "3";
        }
    }
    @RequestMapping("/insertTea")
    @ResponseBody
    public Object insertTea(String class_id,String name,String mobile,String idcard,String sex,String org_id,String classname){
        if(class_id!=null&&!"".equals(class_id)){
            //根据班级查询该班级是否存在，如果存在说明有老师管理
            String ids = manageService.findOneTeacherByClassidAndTeaIdcard(class_id);
            if(ids!=null){
                return "15";
            }
            if(name==null||"".equals(name)){
                return "8";
            }
            if(name.length()<2){
                return "12";
            }
            if(sex==null||"".equals(sex)){
                return "9";
            }
            if(mobile==null||"".equals(mobile)||mobile.contains(" ")){
                return "10";
            }
            if(idcard==null||"".equals(idcard)||idcard.contains(" ")){
                return "11";
            }
            org_id = org_id.substring(0,org_id.indexOf(","));
            name = name.replace(",","");
            String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？ ]";
            Pattern p= Pattern.compile(regEx);
            Matcher m=p.matcher(name);

            //验证是否包含英文
            String regex1 = ".*[a-zA-z].*";
            boolean English = name.matches(regex1);
            //验证是否包含数字
            String regex2 = ".*[0-9].*";
            boolean result4 = name.matches(regex2);
            if(m.find()||result4||English){
                return "6";
            }
            //检验身份证是否存在中文
            idcard = idcard.replace(",","");
            String Chinese = "[\\u4e00-\\u9fa5]";
            Pattern havaCis = Pattern.compile(Chinese);
            if(!IdCardUtil.isValidatedAllIdcard(idcard)){
                return "5";
            }
            //检验手机号是否合格
            mobile = mobile.replace(",","");
            Matcher mobileHavaCis = havaCis.matcher(mobile);
            if(mobileHavaCis.find()){
                return "13";
            }
            if(mobile.length()!=11){
                return "14";
            }
            int classid= (int)Double.parseDouble(class_id);
            Teacher teacher = manageService.findOneByIdcardToTea(idcard);
            if(teacher!=null){
                String id = manageService.findOneTeacherByClassidAndTeaIdcard(class_id);
                if(id!=null){
                    return "7";
                }
            }
            int isSex=0;
            if(sex.equals("男")){
                isSex=1;
            }
            if(teacher!=null){
                return "4";
            }
            if(idcard.length()!=18){
                return "5";
            }
            manageService.addTeaAndOrg(idcard,classid);
            UUID uuid = UUID.randomUUID();
            String str = uuid.toString();
            // 去掉"-"符号
            String temp = str.substring(0, 8) +str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) +str.substring(24);
            int i = manageService.addTeacher(org_id,isSex,name,mobile,idcard,temp);
            JSONObject jsonObject = new JSONObject();
            if(i!=0){
                jsonObject.put("i",i);
            }
            jsonObject.put("org_id",org_id);
            jsonObject.put("classname",classname);
            return jsonObject;
        }else{
            return "3";
        }
    }
    @RequestMapping("/toadd")
    public String toadd(String classname,String org_id,Model model){
        List<Sys_organize> orgList = manageService.findGradesByorgid(org_id);
        model.addAttribute("orgList",orgList);
        model.addAttribute("classname",classname);
        model.addAttribute("org_id",org_id);
        return "insertStudent";
    }
    @RequestMapping("/toaddTea")
    public String toaddTea(String classname,String org_id,Model model){
        List<Sys_organize> orgList = manageService.findGradesByorgid(org_id);
        model.addAttribute("orgList",orgList);
        model.addAttribute("classname",classname);
        model.addAttribute("org_id",org_id);
        return "insertTeacher";
    }

    @RequestMapping("/findClass")
    @ResponseBody
    public Object findGrade(String msg){
        int messages=0;
        if(msg!=null&&!"".equals(msg)){
            messages = (int)Double.parseDouble(msg);
            List<Sys_organize> org = manageService.findClass(messages);
            return org;
        }else{
            return "null";
        }
    }

    @RequestMapping("/findTeamessage")
    @ResponseBody
    public Object findTeamessage(String Grade,String Classes,String name,String idcard,String org_id,String classname,String pageAum,String panduan){
        HashMap<String, Object> map = new HashMap<>();

        if(panduan.equals("1")){
            //判断年级和班级是否为空，如果不为空则查询
            int pagenum = 1;
            String error="";
            if(pageAum!=null&&!"".equals(pageAum)){
                pagenum = (int)Double.parseDouble(pageAum);
            }
            //第一种情况：只根据了年级查询
            if(Grade!=null&&!"".equals(Grade)) {
                //根据班级查询了
                if (Classes != null && !"".equals(Classes)) {
                    int count = manageService.getTeacherMesgCounts(Classes);
                    int pageSize = 0;
                    if (count % 10 == 0) {
                        pageSize = count / 10;
                    } else if (count % 10 != 0) {
                        pageSize = (count / 10) + 1;
                    }
                    Teacher teacher = new Teacher();
                    teacher.setOrg_id((int) Double.parseDouble(Classes));
                    teacher.setName(name);
                    teacher.setIdcard(idcard);
                    PageHelper.startPage(pagenum, 10);
                    List TeacherList = manageService.getTeacherMesgGss(teacher);
                    map.put("TeacherList", TeacherList);
                    if (org_id != null && !"".equals(org_id)) {
                        map.put("org_id", org_id);
                    }
                    map.put("classname", classname);
                    map.put("Teacher", teacher);
                    map.put("pagenum", pagenum);
                    map.put("pageSize", pageSize);
                    map.put("Grade", Grade);
                    map.put("Classes", Classes);
                    map.put("error", error);
                    return map;
                }
              int count = manageService.getTeacherMesgCounta(Grade);
            int pageSize = 0;
            if (count % 10 == 0) {
                pageSize = count / 10;
            } else if (count % 10 != 0) {
                pageSize = (count / 10) + 1;
            }
          Teacher teacher = new Teacher();
            teacher.setOrg_id((int) Double.parseDouble(Grade));
            teacher.setName(name);
            teacher.setIdcard(idcard);
            PageHelper.startPage(pagenum, 10);
            List TeacherList = manageService.getTeacherMesg(teacher);
            map.put("TeacherList", TeacherList);
            if (org_id != null && !"".equals(org_id)) {
                map.put("org_id", org_id);
            }
            map.put("classname", classname);
            map.put("Teacher", teacher);
            map.put("pagenum", pagenum);
            map.put("pageSize", pageSize);
            map.put("Grade", Grade);
            map.put("Classes", Classes);
            map.put("error", error);
             return map;
            }else{
                error="请选择条件";
                map.put("classname",classname);
                map.put("org_id",org_id);
                map.put("error",error);
                return map;
            }
        }else if(panduan.equals("2")){
            //判断年级和班级是否为空，如果不为空则查询
            int pagenum = 1;
            String error="";
            if(pageAum!=null&&!"".equals(pageAum)){
                pagenum = (int)Double.parseDouble(pageAum);
            }
            //求教师总人数
            int orgid = Integer.parseInt(org_id);
            int count = manageService.getTeacherCount(orgid);
            int pageSize = 0;
            if (count % 10 == 0) {
                pageSize = count / 10;
            } else if (count % 10 != 0) {
                pageSize = (count / 10) + 1;
            }
            Teacher teacher = new Teacher();
            teacher.setName(name);
            teacher.setIdcard(idcard);
            teacher.setOrg_id(orgid);
            PageHelper.startPage(pagenum, 10);
            List TeacherList = manageService.selectTeacher(teacher);
            map.put("TeacherList", TeacherList);
            if (org_id != null && !"".equals(org_id)) {
                map.put("org_id", org_id);
            }
            map.put("classname", classname);
            map.put("Teacher", teacher);
            map.put("pagenum", pagenum);
            map.put("pageSize", pageSize);
            map.put("Grade", Grade);
            map.put("Classes", Classes);
            map.put("error", error);
            return map;
        }
        return null;
    }
    @RequestMapping("/findstudentgrade")
    @ResponseBody
    public Object findstudentgrade(String org_id){
        List<Sys_organize> data = manageService.findStudentGrade(org_id);
        return data;
    }
    @RequestMapping("/findmessage")
    @ResponseBody
    public Object findmessage(String Grade,String Classes,String name,String code,String org_id,String classname,String pageAum){
        //判断年级和班级是否为空，如果不为空则查询
        HashMap<String, Object> map = new HashMap<>();
        if(code!=null&&!"".equals(code)){
            List StuMessageList = manageService.getStudentByCode(code);
            map.put("StuMessageList",StuMessageList);
            if(org_id!=null&&!"".equals(org_id)){
                map.put("org_id",org_id);
            }
            map.put("classname",classname);
            return map;
        }
        if(name!=null&&!"".equals(name)){
            List StuMessageList = manageService.getStudentByName(name);
            map.put("StuMessageList",StuMessageList);
            if(org_id!=null&&!"".equals(org_id)){
                map.put("org_id",org_id);
            }
            map.put("classname",classname);
            return map;
        }
        int pagenum = 1;
        String error="";
        if(pageAum!=null&&!"".equals(pageAum)){
            pagenum = (int)Double.parseDouble(pageAum);
        }

        //第一种情况：只根据了年级查询
        if(Grade!=null&&!"".equals(Grade)){
            //根据班级查询了
            if(Classes!=null&&!"".equals(Classes)){
                int count = manageService.getStudentMesgCount(Classes);
                int pageSize = 0;
                if (count % 10 == 0) {
                    pageSize = count / 10;
                } else if (count % 10 != 0) {
                    pageSize = (count / 10) + 1;
                }
                Student student = new Student();
                student.setClasses_id((int)Double.parseDouble(Classes));
                student.setName(name);
                student.setCode(code);
                PageHelper.startPage(pagenum,10);
                List<Student> StuMessageList = manageService.getStudentMesg(student);
                map.put("StuMessageList",StuMessageList);
                if(org_id!=null&&!"".equals(org_id)){
                    map.put("org_id",org_id);
                }
                map.put("classname",classname);
                map.put("student",student);
                map.put("pagenum",pagenum);
                map.put("pageSize",pageSize);
                map.put("Grade",Grade);
                map.put("Classes",Classes);
                map.put("error",error);
                return map;
            }
            /////////////////分割线用于区分///////////////////
            int count = manageService.getStudentMesgCountG(Grade);
            int pageSize = 0;
            if (count % 10 == 0) {
                pageSize = count / 10;
            } else if (count % 10 != 0) {
                pageSize = (count / 10) + 1;
            }
            Student student = new Student();
            student.setClasses_id((int)Double.parseDouble(Grade));
            student.setName(name);
            student.setCode(code);
            PageHelper.startPage(pagenum,10);
            List<Student> StuMessageList = manageService.getStudentMesgG(student);
            map.put("StuMessageList",StuMessageList);
            if(org_id!=null&&!"".equals(org_id)){
                map.put("org_id",org_id);
            }
            map.put("classname",classname);
            map.put("student",student);
            map.put("pagenum",pagenum);
            map.put("pageSize",pageSize);
            map.put("Grade",Grade);
            map.put("Classes",Classes);
            map.put("error",error);
            return map;
        }else{
            error="请选择条件";
            map.put("classname",classname);
            map.put("org_id",org_id);
            map.put("error",error);
            return map;
        }

    }
    @RequestMapping("/toupdate")
    @ResponseBody
    public Object toupdate(String code,String classname,String org_id){
        LinkedMap map = new LinkedMap();
        Student studentCode = manageService.findStudentByCode(code);
        if(studentCode==null||"".equals(studentCode)){
            map.put("classname",classname);
            map.put("org_id",org_id);
            return map;
        }
        //查询出学生的信息以及班级
        Student stuList = manageService.findStudent(code);
        //根据班级查出年级作为回显判断的条件使用
        String orgnj = manageService.findgradea(stuList.getClasses_id());
        String codeByone = stuList.getCode();
        String idcardByone = stuList.getIdcard();
        List<Sys_organize> grade = manageService.grade(org_id);
        List<Sys_organize> classes = manageService.classes(org_id);
        map.put("orgnj",orgnj);
        map.put("stuList",stuList);
        map.put("nianji",grade);
        map.put("banji",classes);
        map.put("classname",classname);
        map.put("org_id",org_id);
        map.put("codeByone",codeByone);
        map.put("idcardByone",idcardByone);
        return map;
    }
   @RequestMapping("/toupdateBytea")
   @ResponseBody
    public Object toupdateBytea(String idcard,String classname,String org_id,String classid){
       LinkedMap map = new LinkedMap();
       //根据身份证号查询老师是否存在，如果不存在那么直接返回刷新一次页面
        Teacher teaList = manageService.findteacher(idcard);
        if(teaList==null||"".equals(teaList)){
            map.put("classname",classname);
            map.put("org_id",org_id);
            return map;
        }
        String idcardByone = teaList.getIdcard();
        List<Sys_organize> orgList = manageService.findGradess(org_id);
           map.put("classid",classid);
           map.put("teaList",teaList);
           map.put("orgList",orgList);
           map.put("classname",classname);
           map.put("org_id",org_id);
           map.put("idcardByone",idcardByone);
        return map;
    }

    @RequestMapping("/deletes")
    @ResponseBody
    public int deletes(String code){
        return manageService.deletes(code);
    }
    @RequestMapping("/deletesByTea")
    @ResponseBody
    public int deletesByTea(String classid){
        //manageService.findTeacherIdcard(classid);
        String idcard = classid;
        manageService.deleteTeacherClass(classid);
        return manageService.deletesByTea(idcard);
    }
    @RequestMapping("/findstudentclass")
    @ResponseBody
    public List<Sys_organize> findstudentclass(String id){
        if(id!=null){
            int ids = (int)Double.parseDouble(id);
            List<Sys_organize> sysList = manageService.findstudentclass(ids);
            return sysList;
        }else{
            return null;
        }
    }
    @RequestMapping("update")
    @ResponseBody
    public Object update(String class_id,String sex,String name,String code,String idcard,String org_id,String classname,String codeByone,String idcardByone){
        if(class_id!=null&&!"".equals(class_id)){
            String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？ ]";
            Pattern p= Pattern.compile(regEx);
            Matcher m=p.matcher(name);
            if(name==null||"".equals(name)){
                return "7";
            }
            if(name.length()<2){
                return "12";
            }
            if(sex==null||"".equals(sex)){
                return "8";
            }
            if(code==null||"".equals(code)||code.contains(" ")){
                return "9";
            }
            if(idcard==null||"".equals(idcard)||idcard.contains(" ")){
                return "10";
            }
            //验证是否包含英文
            String regex1 = ".*[a-zA-z].*";
            boolean English = name.matches(regex1);
            //验证是否包含数字
            String regex2 = ".*[0-9].*";
            boolean result4 = name.matches(regex2);
            if(m.find()||result4||English){
                return "6";
            }
            int classid= (int)Double.parseDouble(class_id);
            int isSex=0;
            if(sex.equals("男")){
                isSex=1;
            }
            if(!codeByone.equals(code)){
                Student student = manageService.findOneByCode(code);
                if(student!=null){
                    return "4";
                }
            }
            if(!idcardByone.equals(idcard)){
                Student students = manageService.findOneByIdcard(idcard);
                if(students!=null){
                    return "4";
                }
            }
            //判断身份证号是否合格
            if(idcard.length()!=18){
                return "13";
            }
            //检查身份证号是否存在中文
            String Chinese = "[\\u4e00-\\u9fa5]";
            Pattern havaCis = Pattern.compile(Chinese);
            Matcher idcardHavaCis = havaCis.matcher(idcard);
            if(idcardHavaCis.find()){
                return "5";
            }
            Matcher codeHavaCis = havaCis.matcher(code);
            if(codeHavaCis.find()){
                return "5";
            }
            int i = manageService.updateStudent(class_id,isSex,name,code,idcard,codeByone);
            JSONObject jsonObject = new JSONObject();
            if(i!=0){
                jsonObject.put("i",i);
            }
            jsonObject.put("org_id",org_id);
            jsonObject.put("classname",classname);
            return jsonObject;
        }else{
            return "3";
        }
    }

    /***
     * 修改老师
     * @param class_id
     * @param name
     * @param idcard
     * @param mobile
     * @param org_id
     * @param classname
     * @param idcardByone
     * @return
     */
    @RequestMapping("/updateTea")
    @ResponseBody
    public Object updateTea(String class_id,String name,String idcard,String sex,String mobile,String org_id,String classname,String idcardByone,String classids){
        if(class_id!=null&&!"".equals(class_id)){
            //姓名是否为空
            if(name==null||"".equals(name)){
                return "8";
            }
            //姓名不能小于2
            if(name.length()<2){
                return "12";
            }
            //手机号不能为空
            if(mobile==null||"".equals(mobile)||mobile.contains(" ")){
                return "10";
            }
            //身份证号不能为空
            if(idcard==null||"".equals(idcard)||idcard.contains(" ")){
                return "11";
            }
            if(sex==null||"".equals(sex)){
                return "13";
            }
            int isSex=0;
            if(sex.equals("男")){
                isSex=1;
            }
            String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？ ]";
            Pattern p= Pattern.compile(regEx);
            Matcher m=p.matcher(name);
            //验证是否包含英文
            String regex1 = ".*[a-zA-z].*";
            boolean English = name.matches(regex1);
            //验证是否包含数字
            String regex2 = ".*[0-9].*";
            boolean result4 = name.matches(regex2);
            //姓名是否合法
            if(m.find()||result4||English){
                return "6";
            }
            //获取班级id
            int class_ids= (int)Double.parseDouble(class_id);
            //先根据原来的身份证和班级号码去查询，如果查询结果不为空，说明该班级是这个老师管理，那么就不进行班级判断
            String classid = manageService.findTeacherMangeClass(idcardByone,class_id);
            if(classid==null){
                String id = manageService.findOneTeacherByClassidAndTeaIdcard(class_id);
                if(id!=null){
                    return "7";
                }
            }
            //查看身份证号是否重复
            if(!idcardByone.equals(idcard)){
                Teacher teacher = manageService.findOneByIdcardTea(idcard);
                if(teacher!=null){
                    return "4";
                }
            }
            //验证中文
            String Chinese = "[\\u4e00-\\u9fa5]";
            Pattern havaCis = Pattern.compile(Chinese);
            if(!IdCardUtil.isValidatedAllIdcard(idcard)){
                return "11";
            }
            Matcher mobileHavaCis = havaCis.matcher(mobile);
            if(mobileHavaCis.find()){
                return "5";
            }
            if(mobile.length()!=11){
                return "14";
            }
            //根据唯一信息查询该老师是否存在
            List teaclass = manageService.find(idcardByone);
            //如果存在则直接修改
            if(teaclass!=null){
                //修改班级和老师的信息
                manageService.updateTeacherAndClass(class_id,idcardByone,classid);
            }
            int i = manageService.updateTeacher(name,idcard,mobile,idcardByone,isSex);
            //查询该老师是否管理该班级，如果管理则修改，不然则添加
            String id = manageService.findTeacherAndClass(idcardByone,class_id);
            if(id==null||id.equals("")){
                manageService.insertTeacherClasses(idcard,class_id);
            }else {
                //修改教师管理班级的信息
                manageService.updateTeacherClasses(class_id,idcard,idcardByone,id);
            }
            JSONObject jsonObject = new JSONObject();
            if(i!=0){
                jsonObject.put("i",i);
            }
            jsonObject.put("org_id",org_id);
            jsonObject.put("classname",classname);
            return jsonObject;
        }else{
            return "3";
        }
    }
    @RequestMapping("updatepasswordByStu")
    @ResponseBody
    public int updatepasswordByStu(String code,String refuse){
        Student studentCode = manageService.findStudentByCode(code);
        if(studentCode==null||"".equals(studentCode)){
            return 5;
        }
        String password = MD5Utils.encodeByMD5(refuse);

        int i = manageService.updatepasswordByStu(code,password);
        return i;
    }
    @RequestMapping("updatepasswordByTea")
    @ResponseBody
    public int updatepasswordByTea(String idcard,String refuse){
        Teacher teaList = manageService.findteacher(idcard);
        if(teaList==null||"".equals(teaList)){
            return 5;
        }

        String password = MD5Utils.encodeByMD5(refuse);

        int i = manageService.updatepasswordByTea(idcard,password);
        return i;
    }
    @RequestMapping("toFind")
    public String toFind(String org_id,Model model){

        List grade = manageService.getGrade(org_id);
        model.addAttribute("org_id",org_id);
        model.addAttribute("grade",grade);
        return "message";
    }
    @RequestMapping("findMessageToSch")
    @ResponseBody
    public Object findMessageToSch(String id){
        LinkedMap linkedMap = new LinkedMap();
        List messageTeaAndStu = manageService.findMessageToSch(id);
        //声明list存放对象参数
        List<XXX> list = new ArrayList();
        String org_id = manageService.findSchoolId(id);
        List grade = manageService.getGrade(org_id);
        //每循环一次把参数放进对象并且再把对象放入list
        for (int i = 0;i<messageTeaAndStu.size();i++){
            XXX xxx = new XXX();
            Object object = messageTeaAndStu.get(i);
            int count = manageService.getCountByclassId(object);
            xxx.setCount(count);
            String teahcerIdcard = manageService.findTeacherNameByClasId(object);
            String teahcerName = manageService.findTeacherNameByIdcard(teahcerIdcard);
            xxx.setTeacher(teahcerName);
            int Notcount = manageService.getNotCountByclassId(object);
            xxx.setNotcount(Notcount);
            String classname=manageService.findClassname(object);
            xxx.setClasses(classname);
            list.add(i,xxx);
        }
        String org_ids = manageService.xxid(id);
        linkedMap.put("grade",grade);
        linkedMap.put("list",list);
        linkedMap.put("org_id",org_ids);
        return linkedMap;
    }
}
