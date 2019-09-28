package com.web.utils;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.web.enums.ResultEnum;

public class CommonalityUtil {

    public static boolean getCommonality(JSONObject json) {
        if (!json.isEmpty()) {
            //渠道类型0-微信 1-APP 2-web终端
            String channeltype = json.getString("channeltype");
            //交易日期
            String txndate = json.getString("txndate");
            //交易时间
            String txntime = json.getString("txntime");
            String action = json.getString("action");
            if (StringUtils.isAnyEmpty(channeltype, txndate, txntime, action)) {
                //TODO 统一返回处理
                return true;
            }
        }
        return false;
    }

}
