package com.chutianlong.service;

import com.chutianlong.pojo.Images;
import com.chutianlong.pojo.Student;
import com.chutianlong.pojo.Sys_organize;
import com.chutianlong.pojo.Teacher;

import java.util.List;

public interface UploadService {
    void add(String url,String org_id);

    List findList(String bj,String name,String code);

    int findListByphoto(String bj);

    int goShen(String str);

    int turnDown(String str,String refuse);

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

    String findStudentByCodeAndName(String studentname, String orgId);

    Sys_organize findClassName(int orgId);

    Student findStudentMessage(int parseDouble);
}
