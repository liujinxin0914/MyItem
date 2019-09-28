package com.chutianlong.service;

import com.chutianlong.dao.LoginDao;
import com.chutianlong.pojo.Picture_worker;
import com.chutianlong.pojo.Student;
import com.chutianlong.pojo.Teacher;
import com.chutianlong.pojo.admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceIM implements LoginService{
    @Autowired
    private LoginDao loginDao;

    @Override
    public Teacher teacherLogin(String idcard) {
        return loginDao.teacherLogin(idcard);
    }

    @Override
    public Picture_worker pictureWorker(String idcard) {
        return loginDao.pictureWorker(idcard);
    }

    @Override
    public admin adminLogin(String idcard) {
        return loginDao.adminLogin(idcard);
    }
}
