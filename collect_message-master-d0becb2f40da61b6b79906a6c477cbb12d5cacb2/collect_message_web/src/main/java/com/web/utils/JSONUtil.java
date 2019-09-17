package com.web.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.web.enums.ResultEnum;

public class JSONUtil {

    public static JSONObject getSuccessResult(String bodystr) {
        JSONObject result = new JSONObject();
        JSONObject head = new JSONObject();
        head.put("code", ResultEnum.SUCCESS.getCode());
        head.put("text", ResultEnum.SUCCESS.getMsg());
        result.put("head", head);
        if (bodystr != null) {
            result.put("body", bodystr);
        }
        return result;
    }

    public static JSONObject getSuccessResult(JSONObject head, JSONObject body) {
        JSONObject result = new JSONObject();
        head.remove("channeltype");
        head.remove("openid");
        head.remove("action");
        head.put("txndate", DateUtils.getDate());
        head.put("txntime", DateUtils.getTime());
        head.put("respcode", ResultEnum.SUCCESS.getCode());
        head.put("respdesc", ResultEnum.SUCCESS.getMsg());
        result.put("head", head);
        if (body != null) {
            result.put("body", body);
        }
        head = null;
        body = null;
        return result;
    }

    public static JSONObject getSuccessResult(JSONArray jsonArray) {
        JSONObject result = new JSONObject();
        JSONObject head = new JSONObject();
        head.put("code", ResultEnum.SUCCESS.getCode());
        head.put("text", ResultEnum.SUCCESS.getMsg());
        result.put("head", head);
        if (jsonArray != null) {
            result.put("body", jsonArray);
        }
        return result;
    }

    public static JSONObject getErrorResult(ResultEnum resultEnum) {
        JSONObject result = new JSONObject();
        JSONObject head = new JSONObject();
        JSONObject body = new JSONObject();
        head.put("respcode", resultEnum.getCode());
        head.put("respdesc", resultEnum.getMsg());

        result.put("head", head);
        if (body != null) {
            result.put("body", body);
        }
        head = null;
        body = null;
        return result;
    }
}
