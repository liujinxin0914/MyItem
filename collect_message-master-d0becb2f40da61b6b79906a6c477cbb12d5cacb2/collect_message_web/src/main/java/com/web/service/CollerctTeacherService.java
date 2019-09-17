package com.web.service;

import com.alibaba.fastjson.JSONObject;

import com.message.model.Teacher;

public interface CollerctTeacherService {
    /**
     * 用户登录
     *
     * @param name    姓名
     * @param cardnum 老师身份证
     * @param openid
     * @param appid
     * @return
     */
    Teacher loginByAccount(String name, String cardnum, String openid, String appid, String password);

    /**
     * 老师注册
     *
     * @param name
     * @param cardnum  身份证号
     * @param password
     * @return
     */
    JSONObject register(JSONObject head, JSONObject body);

    /**
     * 老师修改密码
     *
     * @param headObject
     * @param userid      身份证
     * @param oripassword 新密码
     * @param password
     * @return
     */
    JSONObject updatePassword(JSONObject headObject, String name, String userid, String oripassword, String password);

    /**
     * 获取老师的信息
     *
     * @param headObject
     * @param buserid    身份证
     * @return
     */
    JSONObject getTeacherInfo(JSONObject headObject, String buserid);

    /**
     * 老师根据OpenID 去登录
     *
     * @param openid
     */
    Teacher loginByOpendId(String openid);

    /**
     * 教师绑定机构
     *
     * @param headObject
     * @param bodyObject
     * @return
     */
    JSONObject bindorganize(JSONObject headObject, String userid, String classid);

    /**
     * 获取教师绑定的班级
     *
     * @param headObject
     * @param userid
     * @return
     */
    JSONObject getbindorganize(JSONObject headObject, String userid);


}
