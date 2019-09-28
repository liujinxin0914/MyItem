package com.web.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.message.model.Student;

public interface CollerctStudentService {

    /**
     * @param name     学生姓名
     * @param cardNum  学籍号
     * @param openid
     * @param appid
     * @param password
     * @return
     */
    Student loginByAccount(String name, String cardNum, String openid, String appid, String password);

    /**
     * 学生修改密码
     *
     * @param headObject
     * @param userid      学籍号
     * @param oripassword 原密码
     * @param password    新密码
     * @return
     */
    JSONObject updatePassword(JSONObject headObject, String name, String userid, String oripassword, String password);

    /**
     * 获取学生信息
     *
     * @param headObject
     * @param buserid    被查询的学籍号
     * @return
     */
    JSONObject getStudentInfo(JSONObject headObject, String buserid);

    /**
     * 学生通过openId登录
     *
     * @param openid
     * @return
     */
    Student loginByOpendId(String openid);

    /**
     * 获取学生列表
     *
     * @param headObject
     * @param classid
     * @param status     查询状态
     * @return
     */
    JSONObject getStudents(JSONObject headObject, String classid, String status);

    /**
     * 获取学生照片
     *
     * @param headObject
     * @param userid     获取人
     * @param acquiredid 被获取人
     * @return
     */
    JSONObject getInfoPhotos(JSONObject headObject, String acquiredid);

    /**
     * 照片初审
     *
     * @param headObject
     * @param userid
     * @param studentid
     * @param result
     * @param msg
     * @return
     */
    JSONObject PhotosFirstTrial(JSONObject headObject, String userid, String studentid, String result, String msg);

    /**
     * 学生图片上传
     *
     * @param headObject
     * @param userid
     * @param array
     * @return
     */
    JSONObject updatePhotos(JSONObject headObject, String userid, JSONArray array);

}
