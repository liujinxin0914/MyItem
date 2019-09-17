package com.message.service;

import com.message.model.Student;
import com.message.model.StudentVo;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author fanxialong
 * @since 2019-06-02
 */
public interface StudentService extends IService<Student> {
    //查询未上传总数
    int selectnoupload(String id);

    //待审核总数
    int selectnocheck(String id);

    //已完成总数
    int selectfinished(String id);

    /**
     * 根据班级id 查询班级所有学生
     *
     * @param classid
     * @return
     */
    List<StudentVo> selectStudentByClassId(String classid);

    /**
     * 根据classid 和status查询
     *
     * @param map
     * @return
     */
    List<StudentVo> selectStudentByClassIdAndStatus(String classid, String status);


    /**
     * 根据学籍号查询 学生信息
     *
     * @param code
     * @return
     */
    StudentVo selectStudentByCode(String code);

}
