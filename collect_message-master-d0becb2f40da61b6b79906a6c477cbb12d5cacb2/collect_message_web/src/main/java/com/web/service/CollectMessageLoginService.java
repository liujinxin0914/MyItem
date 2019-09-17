package com.web.service;

import com.alibaba.fastjson.JSONObject;

public interface CollectMessageLoginService {

    /**
     * 被采集对象登录
     *
     * @param bodyObject
     * @param bodyObject
     * @return
     */
    JSONObject collectObjectLogin(JSONObject headObject, JSONObject bodyObject);

    /**
     * 用户修改密码
     *
     * @param headObject
     * @param bodyObject
     * @return
     */
    JSONObject collectObjectUpdatePassword(JSONObject headObject, JSONObject bodyObject);

    /**
     * 获取用户信息
     *
     * @param headObject
     * @param bodyObject
     * @return
     */
    JSONObject collectObjectgetInfo(JSONObject headObject, JSONObject bodyObject);

    /**
     * 获取机构信息
     *
     * @param headObject
     * @param bodyObject
     * @return
     */
    JSONObject getSysOrganize(JSONObject headObject, JSONObject bodyObject);

}
