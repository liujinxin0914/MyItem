package com.web.service;

import com.alibaba.fastjson.JSONObject;

public interface CollerctOrganizeService {

    /**
     * 根据classId
     *
     * @param classid
     * @return
     */
    JSONObject getOrganizeByPidEqClassId(JSONObject headObject, String classid);


    /**
     * 获取机构完成状态
     *
     * @param headObject
     * @param classid
     * @return
     */
    JSONObject getSysOrganizeType(JSONObject headObject, String classid);
}
