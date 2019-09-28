package com.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.message.model.Teacher;
import com.web.enums.ResultEnum;
import com.web.service.CollerctTeacherService;
import com.web.utils.CommonalityUtil;
import com.web.utils.JSONUtil;
import com.web.common.Constants;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    private final Logger logger = LoggerFactory.getLogger(TeacherController.class);
    @Autowired
    private CollerctTeacherService collerctTeacherService;

    @PostMapping("/register")
    public JSONObject register(@RequestBody JSONObject json) {

        JSONObject head = json.getJSONObject("head");
        JSONObject body = json.getJSONObject("body");
        if (json.isEmpty()) {
            //TODO 统一返回处理
            return JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
        }

        logger.info("修改密码请求参数----->" + json.toString());
        //判断交易类型,交易时间,交易日期 接口标识 是否为空
        if (CommonalityUtil.getCommonality(head)) {
            return JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
        }
        //判断接口标识
        String action = body.getString("action");
        if (!(Constants.TEACHER_REGISTER).equals(action)) {
            return JSONUtil.getErrorResult(ResultEnum.ERROR_INTERFACE_ID);
        }
        JSONObject response = null;
        //判断私有参数是否为空
        String name = body.getString("name");
        String cardnum = body.getString("cardnum");
        String password = body.getString("password");
        if (StringUtils.isAnyEmpty(name, cardnum, password)) {
            response = JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
            logger.info("修改密码响应参数----->" + response.toString());
            return response;
        }
        response = collerctTeacherService.register(head, body);
        logger.info("修改密码响应参数----->" + response.toString());
        return response;
    }

    /**
     * 教师绑定班级
     */
    @PostMapping("/bindorganize")
    public JSONObject bindorganize(@RequestBody JSONObject json) {
        JSONObject response = null;
        if (json.isEmpty()) {
            //TODO 统一返 回处理
            return JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
        }
        logger.info("教师绑定机构请求参数----->" + json.toString());
        JSONObject headObject = json.getJSONObject("head");
        JSONObject bodyObject = json.getJSONObject("body");

        //判断交易类型,交易时间,交易日期 接口标识 是否为空
        if (CommonalityUtil.getCommonality(headObject)) {
            response = JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
            logger.info("教师绑定机构信息响应参数----->" + response.toString());
            return response;
        }
        //判断接口标识
        String action = headObject.getString("action");
        if (!(Constants.BIND_ORGANIZE).equals(action)) {
            response = JSONUtil.getErrorResult(ResultEnum.ERROR_INTERFACE_ID);
            logger.info("教师绑定机构信息应参数----->" + response.toString());
            return response;
        }
        String classid = bodyObject.getString("classid");
        String userid = bodyObject.getString("userid");

        if (StringUtils.isAnyEmpty(classid, userid)) {
            response = JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
            logger.info("教师绑定机构信息应参数----->" + response.toString());
            return response;
        }
        response = collerctTeacherService.bindorganize(headObject, userid, classid);
        logger.info("教师绑定机构信息应参数----->" + response.toString());
        return response;
    }


    /**
     * 教师绑定班级
     */
    @PostMapping("/getbindorganize")
    public JSONObject getbindorganize(@RequestBody JSONObject json) {
        JSONObject response = null;
        if (json.isEmpty()) {
            //TODO 统一返 回处理
            return JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
        }
        logger.info("获取教师绑定机构请求参数----->" + json.toString());
        JSONObject headObject = json.getJSONObject("head");
        JSONObject bodyObject = json.getJSONObject("body");

        //判断交易类型,交易时间,交易日期 接口标识 是否为空
        if (CommonalityUtil.getCommonality(headObject)) {
            response = JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
            logger.info("获取教师绑定机构信息响应参数----->" + response.toString());
            return response;
        }
        //判断接口标识
        String action = headObject.getString("action");
        if (!(Constants.GET_BIND_ORGANIZE).equals(action)) {
            response = JSONUtil.getErrorResult(ResultEnum.ERROR_INTERFACE_ID);
            logger.info("获取教师绑定机构信息应参数----->" + response.toString());
            return response;
        }
        String userid = bodyObject.getString("userid");

        if (StringUtils.isEmpty(userid)) {
            response = JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
            logger.info("获取教师绑定机构信息应参数----->" + response.toString());
            return response;
        }
        response = collerctTeacherService.getbindorganize(headObject, userid);
        logger.info("获取教师绑定机构信息应参数----->" + response.toString());
        return response;
    }


}
