package com.chutianlong.service;

import com.chutianlong.pojo.Student;
import com.chutianlong.pojo.Sys_organize;
import com.chutianlong.pojo.Teacher;
import com.chutianlong.pojo.teacher_classes;
import org.apache.ibatis.annotations.Param;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface ManageService {
    List<Sys_organize> findGrade(int id);

    List<Sys_organize> findClass(int messages);

    List<Student> getStudentMesg(Student student);

    int getStudentMesgCount(String classes);

    int getStudentMesgCountG(String grade);

    List<Student> getStudentMesgG(Student student);

    Student findStudent(String code);

    List<Sys_organize> findGrades(String code);

    int deletes(String code);

    List<Sys_organize> findstudentclass(int ids);

    Student findOneByCode(String code);

    Student findOneByIdcard(String idcard);

    int updateStudent(String class_id, int isSex, String name, String code, String idcard, String codeByone);

    int updatepasswordByStu(String code, String password);

    int addStudent(String class_id, int isSex, String name, String code, String idcard,String password);

    List<Sys_organize> findGradesByorgid(String org_id);

    List<Sys_organize> findGradeBytea(int id);

    int getTeacherMesgCountG(String grade);

    List<Teacher> getTeacherMesgGs(Teacher teacher);

    Teacher findteacher(String idcard);

    int getTeacherMesgCounts(String classes);

    List getTeacherMesgGss(Teacher teacher);

    List<Sys_organize> findGradess(String org_id);

    Teacher findOneByIdcardTea(String idcard);

    int updateTeacher(String name, String idcard, String mobile,String idcardByone,int isSex);

    int deletesByTea(String idcard);

    int updatepasswordByTea(String idcard, String password);

    Teacher findOneByIdcardToTea(String idcard);

    int addTeacher(String org_id,int isSex, String name, String mobile, String idcard,String temp);

    List find(String idcardByone);

    void updateTeacherAndClass(String class_id, String idcardByone,String classids);

    void addTeaAndOrg(String idcard, int classid);

    List getGrade(String org_id);

    List findMessageToSch(String id);

    int getCountByclassId(Object object);

    String findTeacherNameByClasId(Object object);

    int getNotCountByclassId(Object object);

    String findTeacherNameByIdcard(String teahcerIdcard);

    String findClassname(Object object);

    String xxid(String id);

    List getTeacherByIdcard(String idcard);

    List getStudentByCode(String code);

    Student findStudentByCode(String code);

    String findOneTeacherByClassidAndTeaIdcard(String class_id);

    int getTeacherMesgCounta(String grade);

    List getTeacherMesg(Teacher teacher);

    String findTeacherMangeClass(String idcardByone,String class_id);

    String findTeacherIdcard(String classid);

    void deleteTeacherClass(String classid);

    void updateTeacherClasses(String class_id, String idcard,String idcardByone,String id);

    String findSchoolId(String id);

    List<Sys_organize> findStudentGrade(String org_id);

    int getTeacherCount(int orgid);

    List selectTeacher(Teacher teacher);

    List<Sys_organize> grade(String org_id);

    List<Sys_organize> classes(String org_id);

    String findgradea(int classes_id);

    List getStudentByName(String name);

    String findTeacherAndClass(String idcardByone, String class_id);

    void insertTeacherClasses(String idcard, String class_id);
}
