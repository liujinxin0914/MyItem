package com.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.web.common.Constants;
import com.web.enums.ResultEnum;
import com.web.service.CollerctStudentService;
import com.web.utils.CommonalityUtil;
import com.web.utils.JSONUtil;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final Logger logger = LoggerFactory.getLogger(StudentController.class);
    @Autowired
    private CollerctStudentService studentService;

    /**
     * 教师绑定班级
     */
    @PostMapping("/getstudents")
    public JSONObject getbindorganize(@RequestBody JSONObject json) {
        JSONObject response = null;
        if (json.isEmpty()) {
            //TODO 统一返 回处理
            return JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
        }
        logger.info("获取学生用户请求参数----->" + json.toString());
        JSONObject headObject = json.getJSONObject("head");
        JSONObject bodyObject = json.getJSONObject("body");

        //判断交易类型,交易时间,交易日期 接口标识 是否为空
        if (CommonalityUtil.getCommonality(headObject)) {
            response = JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
            logger.info("获取学生用户请求参数----->" + json.toString());
            return response;
        }
        //判断接口标识
        String action = headObject.getString("action");
        if (!(Constants.GET_TEACHER_TO_STUDENT).equals(action)) {
            response = JSONUtil.getErrorResult(ResultEnum.ERROR_INTERFACE_ID);
            logger.info("获取学生用户请求参数----->" + json.toString());
            return response;
        }
        String classid = bodyObject.getString("classid");
        String status = bodyObject.getString("status");

        if (StringUtils.isAnyEmpty(classid, status)) {
            response = JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
            logger.info("获取学生用户请求参数----->" + response.toString());
            return response;
        }
        response = studentService.getStudents(headObject, classid, status);
        logger.info("获取学生用户请求参数----->" + response.toString());
        return response;
    }


    /**
     * 　获取学生照片
     */
    @PostMapping("/getphotos")
    public JSONObject getPhotos(@RequestBody JSONObject json) {
        JSONObject response = null;
        if (json.isEmpty()) {
            //TODO 统一返 回处理
            return JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
        }
        logger.info("获取学生照片请求参数----->" + json.toString());
        JSONObject headObject = json.getJSONObject("head");
        JSONObject bodyObject = json.getJSONObject("body");

        //判断交易类型,交易时间,交易日期 接口标识 是否为空
        if (CommonalityUtil.getCommonality(headObject)) {
            response = JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
            logger.info("获取学生照片请求参数----->" + json.toString());
            return response;
        }
        //判断接口标识
        String action = headObject.getString("action");
        if (!(Constants.GET_STUDENT_PHOTO).equals(action)) {
            response = JSONUtil.getErrorResult(ResultEnum.ERROR_INTERFACE_ID);
            logger.info("获取学生照片请求参数----->" + json.toString());
            return response;
        }
        String userid = bodyObject.getString("userid");
        String acquiredid = bodyObject.getString("acquiredid");

        if (StringUtils.isAnyEmpty(userid, acquiredid)) {
            response = JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
            logger.info("获取学生照片请求参数---->" + response.toString());
            return response;
        }
        response = studentService.getInfoPhotos(headObject, acquiredid);
        logger.info("获取学生照片请求参数----->" + response.toString());
        return response;
    }

    /**
     * 　获取学生照片
     */
    @PostMapping("/photofirstrial")
    public JSONObject photofirstrial(@RequestBody JSONObject json) {
        JSONObject response = null;
        if (json.isEmpty()) {
            //TODO 统一返 回处理
            return JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
        }
        logger.info("学生照片初审请求参数----->" + json.toString());
        JSONObject headObject = json.getJSONObject("head");
        JSONObject bodyObject = json.getJSONObject("body");

        //判断交易类型,交易时间,交易日期 接口标识 是否为空
        if (CommonalityUtil.getCommonality(headObject)) {
            response = JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
            logger.info("学生照片初审请求参数----->" + json.toString());
            return response;
        }
        //判断接口标识
        String action = headObject.getString("action");
        if (!(Constants.PHOTO_FIRST_TRIAL).equals(action)) {
            response = JSONUtil.getErrorResult(ResultEnum.ERROR_INTERFACE_ID);
            logger.info("学生照片初审请求参数----->" + json.toString());
            return response;
        }
        String userid = bodyObject.getString("userid");
        String studentid = bodyObject.getString("studentid");
        String result = bodyObject.getString("result");
        String msg = bodyObject.getString("msg");

        if (StringUtils.isAnyEmpty(userid, studentid, result, msg)) {
            response = JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
            logger.info("学生照片初审请求参数---->" + response.toString());
            return response;
        }
        response = studentService.PhotosFirstTrial(headObject, userid, studentid, result, msg);
        logger.info("学生照片初审请求参数----->" + response.toString());
        return response;
    }


    /**
     * 　学生上传照片
     */
    @PostMapping("/uploadphoto")
    public JSONObject uploadphoto(@RequestBody JSONObject json) {
        JSONObject response = null;
        if (json.isEmpty()) {
            //TODO 统一返 回处理
            return JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
        }
        logger.info("学生上传照片请求参数----->" + json.toString());
        JSONObject headObject = json.getJSONObject("head");
        JSONObject bodyObject = json.getJSONObject("body");

        //判断交易类型,交易时间,交易日期 接口标识 是否为空
        if (CommonalityUtil.getCommonality(headObject)) {
            response = JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
            logger.info("学生上传照片请求参数----->" + json.toString());
            return response;
        }
        //判断接口标识
        String action = headObject.getString("action");
        if (!(Constants.PHOTO_FIRST_TRIAL).equals(action)) {
            response = JSONUtil.getErrorResult(ResultEnum.ERROR_INTERFACE_ID);
            logger.info("学生上传照片请求参数----->" + json.toString());
            return response;
        }
        String userid = bodyObject.getString("userid");
        JSONArray array = bodyObject.getJSONArray("txndata");

        if (StringUtils.isEmpty(userid) || null == array) {
            response = JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
            logger.info("学生上传照片请求参数---->" + response.toString());
            return response;
        }
        response = studentService.updatePhotos(headObject, userid, array);
        logger.info("学生上传照片请求参数----->" + response.toString());
        return response;
    }

}
