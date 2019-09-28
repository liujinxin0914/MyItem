package com.web.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.message.model.Student;
import com.message.model.SysOrganize;
import com.message.model.SysOrganizeVo;
import com.message.model.Teacher;
import com.message.model.TeacherClasses;
import com.message.service.StudentService;
import com.message.service.SysOrganizeService;
import com.message.service.TeacherClassesService;
import com.message.service.TeacherService;
import com.web.enums.ResultEnum;
import com.web.service.CollerctTeacherService;
import com.web.utils.JSONUtil;
import com.web.utils.MD5Utils;

@Service
public class CollerctTeacherServiceImpl implements CollerctTeacherService {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SysOrganizeService sysOrganizeService;

    @Autowired
    private TeacherClassesService teacherClassesService;

    /**
     * 用户登录
     */
    @Transactional
    @Override
    public Teacher loginByAccount(String name, String cardnum, String openid, String appid, String password) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<Teacher>();
        wrapper.eq("name", name).eq("idcard", cardnum).eq("password", MD5Utils.encodeByMD5(password));
        Teacher teacher = teacherService.selectOne(wrapper);
        if (teacher != null) {
            if (StringUtils.isAnyEmpty(teacher.getChannelId(), teacher.getChannel())) {
                teacher.setChannelId(openid);
                teacher.setChannel(appid);
                teacherService.updateById(teacher);
            }
            return teacher;
        } else {
            return null;
        }


    }

    /**
     * 注册
     */
    @Transactional
    @Override
    public JSONObject register(JSONObject head, JSONObject body) {
        JSONObject newbody = new JSONObject();
        String name = body.getString("name");
        String cardnum = body.getString("cardnum");
        String password = body.getString("password");
        String appid = head.getString("appid");
        String openid = head.getString("openid");
        QueryWrapper<Teacher> wrapper = new QueryWrapper<Teacher>();
        wrapper.eq("name", name).eq("idcard", cardnum);
        Teacher teacher = teacherService.selectOne(wrapper);
        if (null != teacher) {
            teacher.setCreateTime(new Date());
            teacher.setPassword(MD5Utils.encodeByMD5(password));
            teacher.setChannelId(openid);
            teacher.setChannel(appid);
            teacherService.updateById(teacher);
            newbody.put("name", teacher.getName());
            newbody.put("cardnum", teacher.getIdcard());
            newbody.put("classid", teacher.getOrgId());
            SysOrganize sysOrganize = sysOrganizeService.selectById(teacher.getOrgId());
            if (null != sysOrganize) {
                newbody.put("classname", sysOrganize.getName());
            }
            return JSONUtil.getSuccessResult(head, newbody);
        } else {
            return JSONUtil.getErrorResult(ResultEnum.USER_NONEXIST);
        }
    }

    @Transactional
    @Override
    public JSONObject updatePassword(JSONObject headObject, String name, String userid, String oripassword, String password) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<Teacher>();
        oripassword = MD5Utils.encodeByMD5(oripassword);
        wrapper.eq("password", oripassword).eq("idcard", userid).eq("name", name);
        Teacher teacher = teacherService.selectOne(wrapper);
        if (null == teacher) {
            return JSONUtil.getErrorResult(ResultEnum.ERROR_PASSWORD);
        } else {
            teacher.setPassword(MD5Utils.encodeByMD5(password));
            teacherService.updateById(teacher);
            JSONObject body = new JSONObject();
            return JSONUtil.getSuccessResult(headObject, body);
        }
    }

    @Override
    public JSONObject getTeacherInfo(JSONObject headObject, String buserid) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<Teacher>();
        wrapper.eq("idcard", buserid);
        Teacher teacher = teacherService.selectOne(wrapper);
        if (null == teacher) {
            return JSONUtil.getErrorResult(ResultEnum.USER_NONEXIST);
        } else {
            JSONObject newbody = new JSONObject();
            newbody.put("name", teacher.getName());
            newbody.put("cardnum", teacher.getIdcard());
            newbody.put("classid", teacher.getOrgId());
            newbody.put("classname", "");
            SysOrganize sysOrganize = sysOrganizeService.selectById(teacher.getOrgId());
            if (null != sysOrganize) {
                newbody.put("classname", sysOrganize.getName());
            }
            newbody.put("openid", teacher.getChannelId());
            return JSONUtil.getSuccessResult(headObject, newbody);
        }
    }

    @Override
    public Teacher loginByOpendId(String openid) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<Teacher>();
        wrapper.eq("channel_id", openid);
        Teacher teacher = teacherService.selectOne(wrapper);
        if (null != teacher) {
            return teacher;
        }
        return null;
    }

    @Transactional
    @Override
    public JSONObject bindorganize(JSONObject headObject, String userid, String classid) {
        String[] strings = classid.split(",");
        if (strings != null || strings.length > 0) {
            TeacherClasses teacherClasses;
            QueryWrapper<TeacherClasses> wrapper = new QueryWrapper<TeacherClasses>();
            wrapper.eq("tea_id", userid);
            //先删除老师原来选择的班级 重新添加
            teacherClassesService.delete(wrapper);
            for (int i = 0; i < strings.length; i++) {
                teacherClasses = new TeacherClasses();
                teacherClasses.setTeaId(userid);
                teacherClasses.setOrgId(strings[i]);
                teacherClassesService.insert(teacherClasses);
            }

            return JSONUtil.getSuccessResult(headObject, new JSONObject());
        }
        return JSONUtil.getErrorResult(ResultEnum.ZFDD_ZWSJ);
    }

    @Override
    public JSONObject getbindorganize(JSONObject headObject, String userid) {
        QueryWrapper<TeacherClasses> wrapper = new QueryWrapper<TeacherClasses>();
        wrapper.eq("tea_id", userid);
        List<TeacherClasses> list = teacherClassesService.selectList(wrapper);
        List<SysOrganizeVo> solist = new ArrayList<SysOrganizeVo>();
        SysOrganizeVo sysOrganizeVo = null;
        if (null != list || list.size() > 0) {
            for (TeacherClasses teacherClasses : list) {
                //获取机构id
                String orgId = teacherClasses.getOrgId();
                SysOrganizeVo organizeVo = sysOrganizeService.selectInfoById(orgId);
                solist.add(organizeVo);
            }
            JSONObject body = new JSONObject();
            body.put("txndata", solist);
            return JSONUtil.getSuccessResult(headObject, body);
        }
        return JSONUtil.getErrorResult(ResultEnum.ZFDD_ZWSJ);
    }


}
