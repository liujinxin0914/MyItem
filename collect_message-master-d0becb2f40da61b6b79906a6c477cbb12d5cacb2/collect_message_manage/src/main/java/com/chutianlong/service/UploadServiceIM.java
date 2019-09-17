package com.chutianlong.service;

import com.chutianlong.dao.UploadDao;
import com.chutianlong.pojo.Images;
import com.chutianlong.pojo.Student;
import com.chutianlong.pojo.Sys_organize;
import com.chutianlong.pojo.Teacher;
import com.chutianlong.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UploadServiceIM implements UploadService {

    @Autowired
    private UploadDao uploadDao;

    @Override
    public void add(String url,String org_id) {
        uploadDao.add(url,org_id);
    }

    @Override
    public List findList(String bj,String name,String code) {
        return uploadDao.findList(bj,name,code);
    }

    @Override
    public int findListByphoto(String bj) {
        return uploadDao.findListByphoto(bj);
    }

    @Override
    public int goShen(String str) {
        return uploadDao.goShens(str);
    }

    @Override
    public int turnDown(String str,String refuse) {
        return uploadDao.turnDown(str,refuse);
    }

    @Override
    public List findImg(String str) {
        return uploadDao.findImg(str);
    }

    @Override
    public int findStudentStatus(String str) {
        return uploadDao.findStudentStatus(str);
    }

    @Override
    public void updateStudentStatus(String studentid) {
        uploadDao.updateStudentStatus(studentid);
    }

    @Override
    public List findListByCode(String code) {
        return uploadDao.findListByCode(code);
    }

    @Override
    public void updateStudentStatusByOrg_id(String org_id) {
        uploadDao.updateStudentStatusByOrg_id(org_id);
    }

    @Override
    public Student findTest(String str) {
        return uploadDao.findTest(str);
    }

    @Override
    public int tongguo(String str) {
        return uploadDao.tongguo(str);
    }

    @Override
    public String findURLByStudentCode(String str) {
        return uploadDao.findURLByStudentCode(str);
    }

    @Override
    public String findStu_ID(String s) {
        return uploadDao.findStu_ID(s);
    }

    @Override
    public void deletePhoto(String str) {
        uploadDao.deletePhoto(str);
    }

    @Override
    public void deletePhotoByCode(String str) {
        uploadDao.deletePhotoByCode(str);
    }

    @Override
    public List findListByName(String name) {
        return uploadDao.findListByName(name);
    }

    @Override
    public String findStudentStastus(String orgId) {
        return uploadDao.findStudentStastus(orgId);
    }

    @Override
    public String findStudentByCodeAndName(String studentname, String orgId) {
        return uploadDao.findStudentByCodeAndName(studentname,orgId);
    }

    @Override
    public Sys_organize findClassName(int orgId) {
        return uploadDao.findClassName(orgId);
    }

    @Override
    public Student findStudentMessage(int parseDouble) {
        return uploadDao.findStudentMessage(parseDouble);
    }
}
