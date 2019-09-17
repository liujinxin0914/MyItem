package com.chutianlong.dao;

import com.chutianlong.pojo.Picture_worker;
import com.chutianlong.pojo.Student;
import com.chutianlong.pojo.Teacher;
import com.chutianlong.pojo.admin;

public interface LoginDao {

    Teacher teacherLogin(String idcard);

    Picture_worker pictureWorker(String idcard);

    admin adminLogin(String idcard);
}
