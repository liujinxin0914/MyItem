package com.web.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.message.model.Student;
import com.message.model.SysOrganize;
import com.message.model.Teacher;
import com.message.model.TeacherClasses;
import com.message.service.SysOrganizeService;
import com.message.service.TeacherClassesService;
import com.web.common.Constants;
import com.web.enums.ResultEnum;
import com.web.service.CollectMessageLoginService;
import com.web.service.CollerctOrganizeService;
import com.web.service.CollerctStudentService;
import com.web.service.CollerctTeacherService;
import com.web.service.CollerctTeacherService;
import com.web.utils.JSONUtil;

@Service
public class CollectMessageLoginServiceImpl implements CollectMessageLoginService {

    @Autowired
    private CollerctStudentService studentService;

    @Autowired
    private CollerctTeacherService teacherService;

    @Autowired
    private SysOrganizeService sysOrganizeService;

    @Autowired
    private CollerctOrganizeService organizeService;


    @Override
    public JSONObject collectObjectLogin(JSONObject headObject, JSONObject bodyObject) {
        JSONObject body = new JSONObject();
        // appid
        String appid = headObject.getString("appid");
        //渠道类型0-微信 1-APP 2-web终端
        String channeltype = headObject.getString("channeltype");
        //区分登录用户角色（0:老师；1：学生）
        String usertype = bodyObject.getString("usertype");
        //登陆类型0：使用openid登陆  1：使用账号登陆
        String logintype = bodyObject.getString("logintype");
        //用户名
        String name = bodyObject.getString("name");
        //证件号（学生：学籍号；教师：身份证号）
        String cardNum = bodyObject.getString("cardnum");
        //密码
        String password = bodyObject.getString("password");

        //微信openid
        String openid = null;
        //如果是微信登录
        if (channeltype.equals(Constants.COLLECTOBJECT_CHANNELTYPE_0)) {
            openid = headObject.getString("openid");
        } else {//如如果openId 为空 要去判断 用户的登录账号密码为空
            if (StringUtils.isAnyEmpty(logintype, cardNum, password)) {
//				TODO 返回结果
                return JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
            }
        }

        //学生用户
        if (usertype.equals(Constants.COLLECTOBJECT_TYPE_1)) {
            Student student = null;
            if (StringUtils.isEmpty(openid) || Constants.COLLECTOBJECT_LOGINTYPE_1.equals(logintype)) {
                student = studentService.loginByAccount(name, cardNum, openid, appid, password);
            } else {
                student = studentService.loginByOpendId(openid);
            }

            if (student != null) {
                body.put("name", student.getName());
                body.put("cardnum", student.getCode());
                body.put("classid", student.getClassesId());
                SysOrganize sysOrganize = sysOrganizeService.selectById(student.getClassesId());
                body.put("classname", "");
                if (sysOrganize != null) {
                    body.put("classname", sysOrganize.getName());
                }
                return JSONUtil.getSuccessResult(headObject, body);
            } else {
                return JSONUtil.getErrorResult(ResultEnum.USER_NONEXIST);
            }
        } else {  //老师用户
            Teacher teacher = null;
            if (StringUtils.isEmpty(openid) || Constants.COLLECTOBJECT_LOGINTYPE_1.equals(logintype)) {
                teacher = teacherService.loginByAccount(name, cardNum, openid, appid, password);
            } else {
                teacher = teacherService.loginByOpendId(openid);
            }

            if (teacher != null) {
                body.put("name", teacher.getName());
                body.put("cardnum", teacher.getIdcard());
                body.put("classid", teacher.getOrgId());
                body.put("classname", "");
                SysOrganize sysOrganize = sysOrganizeService.selectById(teacher.getOrgId());
                if (sysOrganize != null) {
                    body.put("classname", sysOrganize.getName());
                }
                return JSONUtil.getSuccessResult(headObject, body);
            } else {
                return JSONUtil.getErrorResult(ResultEnum.USER_NONEXIST);
            }

        }

    }


    @Override
    public JSONObject collectObjectUpdatePassword(JSONObject headObject, JSONObject bodyObject) {
        //用户类型   0 老师  1学生
        String usertype = bodyObject.getString("usertype");
        //姓名
        String name = bodyObject.getString("name");
        //身份证或者学籍号
        String userid = bodyObject.getString("userid");
        //原密码
        String oripassword = bodyObject.getString("oripassword");
        //新密码
        String password = bodyObject.getString("password");
        //判断body 参数是否为空
        if (StringUtils.isAnyEmpty(usertype, name, userid, oripassword, password)) {
            return JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
        }
        //老师
        if (usertype.equals(Constants.COLLECTOBJECT_TYPE_0)) {
            return teacherService.updatePassword(headObject, name, userid, oripassword, password);
        }
        //学生
        if (usertype.equals(Constants.COLLECTOBJECT_TYPE_1)) {
            return studentService.updatePassword(headObject, name, userid, oripassword, password);
        }


        return null;
    }


    @Override
    public JSONObject collectObjectgetInfo(JSONObject headObject, JSONObject bodyObject) {
        //用户类型   0 老师  1学生
        String usertype = bodyObject.getString("usertype");
        //身份证或者学籍号
        String userid = bodyObject.getString("userid");
        //被获取用户类型   0 老师  1学生
        String busertype = bodyObject.getString("busertype");
        //被获取身份证或者学籍号
        String buserid = bodyObject.getString("buserid");
        //判断body 参数是否为空
        if (StringUtils.isAnyEmpty(usertype, userid, busertype, buserid)) {
            return JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
        }
        if (busertype.equals(Constants.COLLECTOBJECT_TYPE_0)) {
            return teacherService.getTeacherInfo(headObject, buserid);
        }
        //学生
        if (busertype.equals(Constants.COLLECTOBJECT_TYPE_1)) {
            return studentService.getStudentInfo(headObject, buserid);
        }
        return null;
    }


    @Override
    public JSONObject getSysOrganize(JSONObject headObject, JSONObject bodyObject) {
        // 机构ID
        String classid = bodyObject.getString("classid");
        //身份证或者学籍号
        String userid = bodyObject.getString("userid");
        //判断body 参数是否为空
        if (StringUtils.isAnyEmpty(userid, classid)) {
            return JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
        }
        //如果用户为空 查询出全部的一级机构  老师对应的机构

        return organizeService.getOrganizeByPidEqClassId(headObject, classid);

    }


}
