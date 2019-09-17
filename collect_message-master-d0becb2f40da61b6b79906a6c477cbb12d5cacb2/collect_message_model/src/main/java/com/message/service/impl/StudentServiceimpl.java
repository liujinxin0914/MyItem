package com.message.service.impl;

import com.message.model.Student;
import com.message.model.StudentVo;
import com.message.dao.StudentMapper;
import com.message.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fanxialong
 * @since 2019-06-02
 */
@Service
public class StudentServiceimpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public int selectnoupload(String id) {
        // TODO Auto-generated method stub
        return studentMapper.selectnoupload(id);
    }

    @Override
    public int selectnocheck(String id) {
        // TODO Auto-generated method stub
        return studentMapper.selectnocheck(id);
    }

    @Override
    public int selectfinished(String id) {
        // TODO Auto-generated method stub
        return studentMapper.selectfinished(id);
    }

    @Override
    public List<StudentVo> selectStudentByClassId(String classid) {
        // TODO Auto-generated method stub
        return studentMapper.selectStudentByClassId(classid);
    }

    @Override
    public List<StudentVo> selectStudentByClassIdAndStatus(String classid, String status) {
        // TODO Auto-generated method stub
        String[] split = status.split(",");
        List<String> aslist = Arrays.asList(split);
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("classid", classid);
        map2.put("aslist", aslist); //网址id
        return studentMapper.selectStudentByClassIdAndStatus(map2);
    }

    @Override
    public StudentVo selectStudentByCode(String code) {
        // TODO Auto-generated method stub
        return studentMapper.selectStudentByCode(code);
    }

}
