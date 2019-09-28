package com.chutianlong.dao;

import com.chutianlong.pojo.Student;
import com.chutianlong.pojo.Sys_organize;
import com.chutianlong.pojo.Teacher;
import com.chutianlong.pojo.teacher_classes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ManageDao {
    List<Sys_organize> findGrade(@Param("id") int id);

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

    int updateStudent(@Param("class_id") String class_id, @Param("isSex") int isSex, @Param("name") String name,@Param("code") String code,@Param("idcard") String idcard, @Param("codeByone") String codeByone);

    int updatepasswordByStu(@Param("code") String code,@Param("password") String password);

    int addStudent(@Param("class_id") String class_id, @Param("isSex") int isSex, @Param("name") String name,@Param("code") String code,@Param("idcard") String idcard,@Param("password") String password);

    List<Sys_organize> findGradesByorgid(String org_id);

    int getTeacherMesgCountG(String grade);

    List<Teacher> getTeacherMesgGs(Teacher teacher);

    List<Sys_organize> findGradeBytea(int id);

    Teacher findteacher(String idcard);

    int getTeacherMesgCounts(String classes);

    List getTeacherMesgGss(Teacher teacher);

    List<Sys_organize> findGradess(String org_id);

    Teacher findOneByIdcardTea(String idcard);

    int updateTeacher(@Param("name") String name, @Param("idcard") String idcard, @Param("mobile") String mobile,@Param("idcardByone") String idcardByone,@Param("isSex") int isSex);

    int deletesByTea(String idcard);

    int updatepasswordByTea(@Param("idcard") String idcard, @Param("password") String password);

    Teacher findOneByIdcardToTea(String idcard);

    int addTeacher(@Param("org_id")String org_id,@Param("isSex") int isSex,@Param("name")String name,@Param("mobile") String mobile,@Param("idcard") String idcard,@Param("temp") String temp);

    List find(String idcardByone);

    void updateTeacherAndClass(@Param("class_id") String class_id,@Param("idcardByone") String idcardByone,@Param("classids") String classids);

    void addTeaAndOrg(@Param("idcard") String idcard, @Param("classid") int classid);

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

    String findOneTeacherByClassidAndTeaIdcard(@Param("class_id") String class_id);

    int getTeacherMesgCounta(String grade);

    List getTeacherMesg(Teacher teacher);

    String findTeacherMangeClass(@Param("idcardByone") String idcardByone,@Param("class_id") String class_id);

    String findTeacherIdcard(String classid);

    void deleteTeacherClass(String classid);

    void updateTeacherClasses(@Param("class_id") String class_id, @Param("idcard") String idcard,@Param("idcardByone") String idcardByone,@Param("id")String id);

    String findSchoolId(String id);

    List<Sys_organize> findStudentGrade(String org_id);

    int getTeacherCount(int orgid);

    List selectTeacher(Teacher teacher);

    List<Sys_organize> grade(String org_id);

    List<Sys_organize> classes(String org_id);

    String findgradea(int classes_id);

    List getStudentByName(String name);

    String findTeacherAndClass(@Param("idcardByone") String idcardByone,@Param("class_id")  String class_id);

    void insertTeacherClasses(@Param("idcard")String idcard, @Param("class_id")String class_id);
}
