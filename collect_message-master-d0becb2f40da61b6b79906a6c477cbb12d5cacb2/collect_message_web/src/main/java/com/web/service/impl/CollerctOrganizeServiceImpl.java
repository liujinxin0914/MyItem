package com.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.message.model.Student;
import com.message.model.SysOrganize;
import com.message.model.SysOrganizeVo;
import com.message.model.Teacher;
import com.message.service.StudentService;
import com.message.service.SysOrganizeService;
import com.web.common.Constants;
import com.web.enums.ResultEnum;
import com.web.service.CollerctOrganizeService;
import com.web.utils.JSONUtil;
import com.web.utils.MD5Utils;


@Service
public class CollerctOrganizeServiceImpl implements CollerctOrganizeService {

    @Autowired
    private SysOrganizeService sysOrganizeService;


    @Autowired
    private StudentService studentService;

    @Override
    public JSONObject getOrganizeByPidEqClassId(JSONObject headObject, String classid) {

        List<SysOrganizeVo> organizes = sysOrganizeService.seletSysOrganizeByPid(classid);
        if (null != organizes || organizes.size() > 0) {
            JSONObject body = new JSONObject();
            body.put("txndata", organizes);
            for (SysOrganizeVo sysOrganizeVo : organizes) {
                List<SysOrganizeVo> list = sysOrganizeService.seletSysOrganizeByPid(sysOrganizeVo.getId());
                sysOrganizeVo.setChild(list);
            }
            return JSONUtil.getSuccessResult(headObject, body);
        }

        return JSONUtil.getErrorResult(ResultEnum.ZFDD_ZWSJ);
        //return null;
    }


    @Override
    public JSONObject getSysOrganizeType(JSONObject headObject, String classid) {
        String[] split = classid.split(",");
        QueryWrapper<Student> wrapper = null;
        List<SysOrganizeVo> lists = new ArrayList<SysOrganizeVo>();
        for (int i = 0; i < split.length; i++) {
            String id = split[i];
            SysOrganizeVo organizeVo = sysOrganizeService.selectInfoById(id);
            if (null != organizeVo) {
                //根据id 查询学生总数
                wrapper = new QueryWrapper<Student>();
                wrapper.eq("classes_id", id);
                int total = studentService.selectCount(wrapper);
                //查询未上传总数
                int noupload = studentService.selectnoupload(id);
                //待审核总数
                int nocheck = studentService.selectnocheck(id);
                //已完成总数
                int finished = studentService.selectfinished(id);
                organizeVo.setTotal(total);
                organizeVo.setNoupload(noupload);
                organizeVo.setNocheck(nocheck);
                organizeVo.setFinished(finished);
            }
            lists.add(organizeVo);
        }
        JSONObject body = new JSONObject();
        body.put("txndata", lists);
        return JSONUtil.getSuccessResult(headObject, body);
    }
}
