package com.chutianlong.dao;

import com.chutianlong.pojo.Images;
import com.chutianlong.pojo.Student;
import com.chutianlong.pojo.Sys_organize;
import com.chutianlong.pojo.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UploadDao {

    void add(@Param("url") String url,@Param("org_id") String org_id);

    List findList(@Param("bj") String bj,@Param("name") String name,@Param("code") String code);

    int findListByphoto(String bj);

    int goShens(String str);

    int turnDown(@Param("str") String str,@Param("refuse") String refuse);

    List findImg(String str);

    int findStudentStatus(String str);

    void updateStudentStatus(String studentid);

    List findListByCode(String code);

    void updateStudentStatusByOrg_id(String org_id);

    Student findTest(String str);

    int tongguo(String str);

    String findURLByStudentCode(String str);

    String findStu_ID(String s);

    void deletePhoto(String str);

    void deletePhotoByCode(String str);

    List findListByName(String name);

    String findStudentStastus(String orgId);

    String findStudentByCodeAndName(@Param("studentname") String studentname,@Param("orgId") String orgId);

    Sys_organize findClassName(int orgId);

    Student findStudentMessage(int parseDouble);
}
