package com.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.web.common.Constants;
import com.web.enums.ResultEnum;
import com.web.service.CollectMessageLoginService;
import com.web.service.CollerctOrganizeService;
import com.web.service.CollerctStudentService;
import com.web.service.CollerctTeacherService;
import com.web.utils.CommonalityUtil;
import com.web.utils.JSONUtil;

/**
 * 信息采集
 *
 * @author fxl
 * @date 2019年5月23日
 */
@RestController
public class CollectMessageController {
    private final Logger logger = LoggerFactory.getLogger(CollectMessageController.class);
    @Autowired
    private CollectMessageLoginService loginService;

    @Autowired
    private CollerctOrganizeService organizeService;

    @Autowired
    private CollerctTeacherService collerctTeacherService;

    @Autowired
    private CollerctStudentService studentService;

    @PostMapping("/interface")
    public JSONObject Interface(@RequestBody JSONObject json) {

        JSONObject headObject = json.getJSONObject("head");
        JSONObject bodyObject = json.getJSONObject("body");

        //返回信息
        JSONObject response = null;
        if (json.isEmpty()) {
            //TODO 统一返回处理
            return JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
        }
        //渠道类型0-微信 1-APP 2-web终端
        String channeltype = headObject.getString("channeltype");
        //交易日期
        String txndate = headObject.getString("txndate");
        //交易时间
        String txntime = headObject.getString("txntime");
        String action = bodyObject.getString("action");
        if (StringUtils.isAnyEmpty(channeltype, txndate, txntime, action)) {
            //TODO 统一返回处理
            response = JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
            logger.info("响应参数----->" + response.toString());
            return response;
        }


        if (Constants.COLLECTOBJECT_LOGIN.equals(action)) {
            //登录1000
            response = loginService.collectObjectLogin(headObject, bodyObject);


        } else if ((Constants.TEACHER_REGISTER).equals(action)) {
            //教师注册1001
            String name = bodyObject.getString("name");
            String cardnum = bodyObject.getString("cardnum");
            String password = bodyObject.getString("password");
            if (StringUtils.isAnyEmpty(name, cardnum, password)) {
                response = JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
                logger.info("修改密码响应参数----->" + response.toString());
                return response;
            }
            response = collerctTeacherService.register(headObject, bodyObject);

        } else if ((Constants.UPDATE_PASSWORD).equals(action)) {
            //修改密码1002
            response = loginService.collectObjectUpdatePassword(headObject, bodyObject);

        } else if ((Constants.GET_INFO).equals(action)) {
            //用户信息查询1003
            response = loginService.collectObjectgetInfo(headObject, bodyObject);

        } else if ((Constants.GET_SYS_ORGANIZE).equals(action)) {
            //获取机构信息1004
            response = loginService.getSysOrganize(headObject, bodyObject);

        } else if ((Constants.BIND_ORGANIZE).equals(action)) {
            //教师绑定班级1005
            String classid = bodyObject.getString("classid");
            String userid = bodyObject.getString("userid");

            if (StringUtils.isAnyEmpty(classid, userid)) {
                response = JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
                logger.info("教师绑定机构信息应参数----->" + response.toString());
                return response;
            }
            response = collerctTeacherService.bindorganize(headObject, userid, classid);

        } else if ((Constants.GET_BIND_ORGANIZE).equals(action)) {
            //获取教师绑定的班级1006
            String userid = bodyObject.getString("userid");
            if (StringUtils.isEmpty(userid)) {
                response = JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
                logger.info("获取教师绑定机构信息应参数----->" + response.toString());
                return response;
            }
            response = collerctTeacherService.getbindorganize(headObject, userid);

        } else if ((Constants.GET_SYS_ORGANIZE_TYPE).equals(action)) {
            //获取机构完成信息 1007
            String classid = bodyObject.getString("classid");
            if (StringUtils.isEmpty(classid)) {
                response = JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
                logger.info("获取机构信息响应参数----->" + response.toString());
                return response;
            }
            response = organizeService.getSysOrganizeType(headObject, classid);

        } else if ((Constants.GET_TEACHER_TO_STUDENT).equals(action)) {
            //获取学生列表  1008
            String classid = bodyObject.getString("classid");
            String status = bodyObject.getString("status");

            if (StringUtils.isAnyEmpty(classid, status)) {
                response = JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
                logger.info("获取学生用户请求参数----->" + response.toString());
                return response;
            }
            response = studentService.getStudents(headObject, classid, status);

        } else if ((Constants.GET_STUDENT_PHOTO).equals(action)) {
            //获取学生照片1009
            String userid = bodyObject.getString("userid");
            String acquiredid = bodyObject.getString("acquiredid");

            if (StringUtils.isAnyEmpty(userid, acquiredid)) {
                response = JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
                logger.info("获取学生照片请求参数---->" + response.toString());
                return response;
            }
            response = studentService.getInfoPhotos(headObject, acquiredid);

        } else if ((Constants.PHOTO_FIRST_TRIAL).equals(action)) {
            //学生照片初审  1010
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
        } else if ((Constants.UPLOAD_PHOTO).equals(action)) {
            //学生上传照片 1011
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

        } else {
            response = JSONUtil.getErrorResult(ResultEnum.ERROR_INTERFACE_ID);

        }
        logger.info("action==" + action + "=响应参数----->" + response.toString());
        return response;
    }


    /**
     * 用户修改密码
     *
     * @param json
     * @return
     */
    @PostMapping("/updatepw")
    public JSONObject updatepw(@RequestBody JSONObject json) {

        JSONObject response = null;
        if (json.isEmpty()) {
            //TODO 统一返回处理
            return JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
        }
        logger.info("修改密码请求参数----->" + json.toString());
        JSONObject headObject = json.getJSONObject("head");
        JSONObject bodyObject = json.getJSONObject("body");

        //判断交易类型,交易时间,交易日期 接口标识 是否为空
        if (CommonalityUtil.getCommonality(headObject)) {
            response = JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
            logger.info("修改密码响应参数----->" + response.toString());
            return response;
        }
        //判断接口标识
        String action = headObject.getString("action");
        if (!(Constants.UPDATE_PASSWORD).equals(action)) {
            response = JSONUtil.getErrorResult(ResultEnum.ERROR_INTERFACE_ID);
            logger.info("修改密码响应参数----->" + response.toString());
            return response;
        }


        response = loginService.collectObjectUpdatePassword(headObject, bodyObject);
        logger.info("修改密码响应参数----->" + response.toString());
        return response;
    }


    /**
     * 获取用户信息
     *
     * @param json
     * @return
     */
    @PostMapping("/getinfo")
    public JSONObject getinfo(@RequestBody JSONObject json) {
        JSONObject response = null;
        if (json.isEmpty()) {
            //TODO 统一返回处理
            return JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
        }
        logger.info("获取用户信息请求参数----->" + json.toString());
        JSONObject headObject = json.getJSONObject("head");
        JSONObject bodyObject = json.getJSONObject("body");

        //判断交易类型,交易时间,交易日期 接口标识 是否为空
        if (CommonalityUtil.getCommonality(headObject)) {
            response = JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
            logger.info("获取用户信息响应参数----->" + response.toString());
            return response;
        }
        //判断接口标识
        String action = headObject.getString("action");
        if (!(Constants.GET_INFO).equals(action)) {
            response = JSONUtil.getErrorResult(ResultEnum.ERROR_INTERFACE_ID);
            logger.info("获取用户信息应参数----->" + response.toString());
            return response;
        }

        response = loginService.collectObjectgetInfo(headObject, bodyObject);
        logger.info("获取用户信息应参数----->" + response.toString());
        return response;
    }

    /**
     * 获取机构信息
     */

    @PostMapping("/getorganize")
    public JSONObject getorganize(@RequestBody JSONObject json) {
        JSONObject response = null;
        if (json.isEmpty()) {
            //TODO 统一返回处理
            return JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
        }
        logger.info("获取机构信息请求参数----->" + json.toString());
        JSONObject headObject = json.getJSONObject("head");
        JSONObject bodyObject = json.getJSONObject("body");

        //判断交易类型,交易时间,交易日期 接口标识 是否为空
        if (CommonalityUtil.getCommonality(headObject)) {
            response = JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
            logger.info("获取机构信息响应参数----->" + response.toString());
            return response;
        }
        //判断接口标识
        String action = headObject.getString("action");
        if (!(Constants.GET_SYS_ORGANIZE).equals(action)) {
            response = JSONUtil.getErrorResult(ResultEnum.ERROR_INTERFACE_ID);
            logger.info("获取机构信息应参数----->" + response.toString());
            return response;
        }

        response = loginService.getSysOrganize(headObject, bodyObject);
        logger.info("获取机构信息应参数----->" + response.toString());
        return response;
    }


    /**
     * 获取机构信息
     */

    @PostMapping("/getorganizetype")
    public JSONObject getorganizetype(@RequestBody JSONObject json) {
        JSONObject response = null;
        if (json.isEmpty()) {
            //TODO 统一返回处理
            return JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
        }
        logger.info("获取机构完成信息请求参数----->" + json.toString());
        JSONObject headObject = json.getJSONObject("head");
        JSONObject bodyObject = json.getJSONObject("body");

        //判断交易类型,交易时间,交易日期 接口标识 是否为空
        if (CommonalityUtil.getCommonality(headObject)) {
            response = JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
            logger.info("获取机构完成信息响应参数----->" + response.toString());
            return response;
        }
        //判断接口标识
        String action = headObject.getString("action");
        if (!(Constants.GET_SYS_ORGANIZE_TYPE).equals(action)) {
            response = JSONUtil.getErrorResult(ResultEnum.ERROR_INTERFACE_ID);
            logger.info("获取机构完成信息应参数----->" + response.toString());
            return response;
        }
        String classid = bodyObject.getString("classid");

        if (StringUtils.isEmpty(classid)) {
            response = JSONUtil.getErrorResult(ResultEnum.ERROR_DATA_NULL);
            logger.info("获取机构完成信息应参数----->" + response.toString());
            return response;
        }
        response = organizeService.getSysOrganizeType(headObject, classid);
        logger.info("获取机构完成信息应参数----->" + response.toString());
        return response;
    }
}
