package com.chutianlong.service;

import com.chutianlong.dao.ManageDao;
import com.chutianlong.pojo.Student;
import com.chutianlong.pojo.Sys_organize;
import com.chutianlong.pojo.Teacher;
import com.chutianlong.pojo.teacher_classes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ManageServiceIM implements ManageService{
    @Autowired
    private ManageDao manageDao;

    @Override
    public List<Sys_organize> findGrade(int id) {
        return manageDao.findGrade(id);
    }

    @Override
    public List<Sys_organize> findClass(int messages) {
        return manageDao.findClass(messages);
    }

    @Override
    public List<Student> getStudentMesg(Student student) {
        return manageDao.getStudentMesg(student);
    }

    @Override
    public int getStudentMesgCount(String classes) {
        return manageDao.getStudentMesgCount(classes);
    }

    @Override
    public int getStudentMesgCountG(String grade) {
        return manageDao.getStudentMesgCountG(grade);
    }

    @Override
    public List<Student> getStudentMesgG(Student student) {
        return manageDao.getStudentMesgG(student);
    }

    @Override
    public Student findStudent(String code) {
        return manageDao.findStudent(code);
    }

    @Override
    public List<Sys_organize> findGrades(String code) {
        return manageDao.findGrades(code);
    }

    @Override
    public int deletes(String code) {
        return manageDao.deletes(code);
    }

    @Override
    public List<Sys_organize> findstudentclass(int ids) {
        return manageDao.findstudentclass(ids);
    }

    @Override
    public Student findOneByCode(String code) {
        return manageDao.findOneByCode(code);
    }

    @Override
    public Student findOneByIdcard(String idcard) {
        return manageDao.findOneByIdcard(idcard);
    }

    @Override
    public int updateStudent(String class_id, int isSex, String name, String code, String idcard, String codeByone) {
        return manageDao.updateStudent(class_id,isSex,name,code,idcard,codeByone);
    }

    @Override
    public int updatepasswordByStu(String code, String password) {
        return manageDao.updatepasswordByStu(code,password);
    }

    @Override
    public int addStudent(String class_id, int isSex, String name, String code, String idcard,String password) {
        return manageDao.addStudent(class_id,isSex,name,code,idcard,password);
    }

    @Override
    public List<Sys_organize> findGradesByorgid(String org_id) {
        return manageDao.findGradesByorgid(org_id);
    }



    @Override
    public List<Sys_organize> findGradeBytea(int id) {
        return manageDao.findGradeBytea(id);
    }

    @Override
    public int getTeacherMesgCountG(String grade) {
        return manageDao.getTeacherMesgCountG(grade);
    }

    @Override
    public List<Teacher> getTeacherMesgGs(Teacher teacher) {
        return manageDao.getTeacherMesgGs(teacher);
    }

    @Override
    public Teacher findteacher(String idcard) {
        return manageDao.findteacher(idcard);
    }

    @Override
    public int getTeacherMesgCounts(String classes) {
        return manageDao.getTeacherMesgCounts(classes);
    }

    @Override
    public List getTeacherMesgGss(Teacher teacher) {
        return manageDao.getTeacherMesgGss(teacher);
    }

    @Override
    public List<Sys_organize> findGradess(String org_id) {
        return manageDao.findGradess(org_id);
    }

    @Override
    public Teacher findOneByIdcardTea(String idcard) {
        return manageDao.findOneByIdcardTea(idcard);
    }

    @Override
    public int updateTeacher(String name, String idcard, String mobile,String idcardByone,int isSex) {
        return manageDao.updateTeacher(name,idcard,mobile,idcardByone,isSex);
    }

    @Override
    public int deletesByTea(String idcard) {
        return manageDao.deletesByTea(idcard);
    }

    @Override
    public int updatepasswordByTea(String idcard, String password) {
        return manageDao.updatepasswordByTea(idcard,password);
    }

    @Override
    public Teacher findOneByIdcardToTea(String idcard) {
        return manageDao.findOneByIdcardToTea(idcard);
    }

    @Override
    public int addTeacher(String org_id,int isSex, String name, String mobile, String idcard,String temp) {
        return manageDao.addTeacher(org_id,isSex,name,mobile,idcard,temp);
    }

    @Override
    public List find(String idcardByone) {
        return manageDao.find(idcardByone);
    }

    @Override
    public void updateTeacherAndClass(String class_id, String idcardByone,String classids) {
        manageDao.updateTeacherAndClass(class_id,idcardByone,classids);
    }

    @Override
    public void addTeaAndOrg(String idcard, int classid) {
        manageDao.addTeaAndOrg(idcard,classid);
    }

    @Override
    public List getGrade(String org_id) {
        return manageDao.getGrade(org_id);
    }

    @Override
    public List findMessageToSch(String id) {
        return manageDao.findMessageToSch(id);
    }

    @Override
    public int getCountByclassId(Object object) {
        return manageDao.getCountByclassId(object);
    }

    @Override
    public String findTeacherNameByClasId(Object object) {
        return manageDao.findTeacherNameByClasId(object);
    }

    @Override
    public int getNotCountByclassId(Object object) {
        return manageDao.getNotCountByclassId(object);
    }

    @Override
    public String findTeacherNameByIdcard(String teahcerIdcard) {
        return manageDao.findTeacherNameByIdcard(teahcerIdcard);
    }

    @Override
    public String findClassname(Object object) {
        return manageDao.findClassname(object);
    }

    @Override
    public String xxid(String id) {
        return manageDao.xxid(id);
    }

    @Override
    public List getTeacherByIdcard(String idcard) {
        return manageDao.getTeacherByIdcard(idcard);
    }

    @Override
    public List getStudentByCode(String code) {
        return manageDao.getStudentByCode(code);
    }

    @Override
    public Student findStudentByCode(String code) {
        return manageDao.findStudentByCode(code);
    }

    @Override
    public String findOneTeacherByClassidAndTeaIdcard(String class_id) {
        return manageDao.findOneTeacherByClassidAndTeaIdcard(class_id);
    }

    @Override
    public int getTeacherMesgCounta(String grade) {
        return manageDao.getTeacherMesgCounta(grade);
    }

    @Override
    public List getTeacherMesg(Teacher teacher) {
        return manageDao.getTeacherMesg(teacher);
    }

    @Override
    public String findTeacherMangeClass(String idcardByone,String class_id) {
        return manageDao.findTeacherMangeClass(idcardByone,class_id);
    }
    @Override
    public String findTeacherIdcard(String classid) {
        return manageDao.findTeacherIdcard(classid);
    }

    @Override
    public void deleteTeacherClass(String classid) {
        manageDao.deleteTeacherClass(classid);
    }

    @Override
    public void updateTeacherClasses(String class_id, String idcard,String idcardByone,String id) {
        manageDao.updateTeacherClasses(class_id,idcard,idcardByone,id);
    }

    @Override
    public String findSchoolId(String id) {
        return manageDao.findSchoolId(id);
    }

    @Override
    public List<Sys_organize> findStudentGrade(String org_id) {
        return manageDao.findStudentGrade(org_id);
    }

    @Override
    public int getTeacherCount(int orgid) {
        return manageDao.getTeacherCount(orgid);
    }

    @Override
    public List selectTeacher(Teacher teacher) {
        return manageDao.selectTeacher(teacher);
    }

    @Override
    public List<Sys_organize> grade(String org_id) {
        return manageDao.grade(org_id);
    }

    @Override
    public List<Sys_organize> classes(String org_id) {
        return manageDao.classes(org_id);
    }

    @Override
    public String findgradea(int classes_id) {
        return manageDao.findgradea(classes_id);
    }

    @Override
    public List getStudentByName(String name) {
        return manageDao.getStudentByName(name);
    }

    @Override
    public String findTeacherAndClass(String idcardByone, String class_id) {
        return manageDao.findTeacherAndClass(idcardByone,class_id);
    }

    @Override
    public void insertTeacherClasses(String idcard, String class_id) {
        manageDao.insertTeacherClasses(idcard,class_id);
    }


}
