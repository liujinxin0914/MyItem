package com.chutianlong.dao;

import com.chutianlong.pojo.Student;
import com.chutianlong.pojo.Sys_organize;
import com.chutianlong.pojo.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExcelDao {
    void importProduct(@Param("teacheList") List<Teacher> teacheList);

     List<Sys_organize> findSchool(@Param("pid") int pid);

    List<Sys_organize> findNj(int pid);

    List<Sys_organize> findBj(int pid);

    List findStudent(@Param("schools") int schools, @Param("name") String name,@Param("code") String code);

    void importStudent(@Param("studentList") List<Student> studentList);

    void importSchoolName(String Schoolname);

    List findStudentBynj(@Param("nj") int nj, @Param("name") String name, @Param("code") String code);

    List findStudentBybj(@Param("bj") int bj, @Param("name") String name, @Param("code") String code);

    int getCount(@Param("schools") int schools);

    int getCountN(@Param("nj") int nj);

    int getCountB(@Param("bj") int bj);

    Sys_organize findSchoolByName(String schoolObject);

    void addSchool(String schoolObject);

    void addYuanxi(@Param("yuanxi") String yuanxi,@Param("yid") int yid);

    Object findSchoolIsNot(String schoolname);

    void addClasses(@Param("ids") String ids, @Param("nianjiId") int nianjiId);

    List<Student> findidcard();

    List<Sys_organize> findYj(int pid);

    int findTeacherIdcard(String id);

    List<Student> findCode();


    int findPid(String schoolname);

    Sys_organize findclass(@Param("schoolname") String schoolname,@Param("yuanxi") String yuanxi);

    String findNainJiName(@Param("yuanxi") String yuanxi, @Param("yid") int yid);

    Sys_organize findclassid(@Param("ids") String ids,@Param("nianjiId") int nianjiId);

    String findSchoolName(int org_id);

    List<Sys_organize> findgrade(int yid);

    List<Sys_organize> findClass(int yid);

    Sys_organize findNianji(@Param("yuanxi") String yuanxi,@Param("yid") int yid);

    List getStudentByCode(String code);

    List getStudentByName(String name);

    List<Sys_organize> findClassAll(int yid);

    List<Sys_organize> findGradeAll(int yid);
}
