package com.message.dao;

import com.message.model.Student;
import com.message.model.StudentVo;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author fanxialong
 * @since 2019-06-02
 */
public interface StudentMapper extends BaseMapper<Student> {
    //查询未上传总数
    int selectnoupload(String classid);

    //待审核总数
    int selectnocheck(String classid);

    //已完成总数
    int selectfinished(String classid);

    List<StudentVo> selectStudentByClassId(String classid);

    List<StudentVo> selectStudentByClassIdAndStatus(Map<String, Object> map2);

    StudentVo selectStudentByCode(String code);

}
