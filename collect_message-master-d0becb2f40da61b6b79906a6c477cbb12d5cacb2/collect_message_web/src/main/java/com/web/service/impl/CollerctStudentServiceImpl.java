package com.web.service.impl;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.message.model.Images;
import com.message.model.Student;
import com.message.model.StudentVo;
import com.message.model.SysOrganize;
import com.message.model.Teacher;
import com.message.service.StudentService;
import com.message.service.SysOrganizeService;
import com.web.common.Constants;
import com.web.enums.ResultEnum;
import com.web.service.CollerctStudentService;
import com.web.utils.ImgErToFileUtil;
import com.web.utils.JSONUtil;
import com.web.utils.MD5Utils;
import com.web.utils.UrlUtils;

@Service
public class CollerctStudentServiceImpl implements CollerctStudentService {

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Value("${organize.num}")
    private String num;

    @Autowired
    private StudentService studentService;

    @Autowired
    private SysOrganizeService sysOrganizeService;

    @Override
    public Student loginByAccount(String name, String cardNum, String openid, String appid, String password) {
        QueryWrapper<Student> wrapper = new QueryWrapper<Student>();
        wrapper.eq("name", name).eq("code", cardNum).eq("password", MD5Utils.encodeByMD5(password));
        Student student = studentService.selectOne(wrapper);
        //用户首次登录时候绑定渠道id
        if (student != null) {
            if (StringUtils.isAnyEmpty(student.getChannelId(), student.getChannel())) {
                student.setChannelId(openid);
                student.setChannel(appid);
                student.setCreateTime(new Date());
                studentService.updateById(student);
            }
            return student;
        }
        return null;
    }

    @Override
    public JSONObject updatePassword(JSONObject headObject, String name, String userid, String oripassword, String password) {
        QueryWrapper<Student> wrapper = new QueryWrapper<Student>();
        oripassword = MD5Utils.encodeByMD5(oripassword);
        wrapper.eq("password", oripassword).eq("code", userid).eq("name", name);
        Student student = studentService.selectOne(wrapper);
        if (null == student) {
            return JSONUtil.getErrorResult(ResultEnum.ERROR_PASSWORD);
        } else {
            student.setPassword(MD5Utils.encodeByMD5(password));
            student.setUpdateTime(new Date());
            studentService.updateById(student);
            JSONObject body = new JSONObject();
            return JSONUtil.getSuccessResult(headObject, body);
        }
    }

    @Override
    public JSONObject getStudentInfo(JSONObject headObject, String buserid) {
        QueryWrapper<Student> wrapper = new QueryWrapper<Student>();
        wrapper.eq("code", buserid);
        Student selectOne = studentService.selectOne(wrapper);
        Student student = studentService.selectOne(wrapper);
        if (null == student) {
            return JSONUtil.getErrorResult(ResultEnum.USER_NONEXIST);
        } else {
            JSONObject newbody = new JSONObject();
            newbody.put("name", student.getName());
            newbody.put("cardnum", student.getCode());
            newbody.put("classid", student.getClassesId());
            newbody.put("classname", "");
            SysOrganize sysOrganize = sysOrganizeService.selectById(student.getClassesId());
            if (null != sysOrganize) {
                newbody.put("classname", sysOrganize.getName());
            }
            newbody.put("openid", student.getChannelId());
            return JSONUtil.getSuccessResult(headObject, newbody);
        }
    }

    @Override
    public Student loginByOpendId(String openid) {
        QueryWrapper<Student> wrapper = new QueryWrapper<Student>();
        wrapper.eq("channel_id", openid);
        Student student = studentService.selectOne(wrapper);
        if (null != student) {
            return student;
        }
        return null;
    }

    @Override
    public JSONObject getStudents(JSONObject headObject, String classid, String status) {
        //如果状态包含0 直接查询全部
        String[] arr = status.split(",");
        List<StudentVo> students = null;
        if (Arrays.asList(arr).contains("0")) {
            students = studentService.selectStudentByClassId(classid);
        } else {
            students = studentService.selectStudentByClassIdAndStatus(classid, status);
        }
        JSONObject body = new JSONObject();
        body.put("txndata", students);
        return JSONUtil.getSuccessResult(headObject, body);

    }

    @Override
    public JSONObject getInfoPhotos(JSONObject headObject, String acquiredid) {
        StudentVo studentVo = studentService.selectStudentByCode(acquiredid);
        if (null != studentVo) {
            JSONObject body = new JSONObject();
            body.put("student", studentVo);
            return JSONUtil.getSuccessResult(headObject, body);
        }
        return JSONUtil.getErrorResult(ResultEnum.ZFDD_ZWSJ);
    }

    @Override
    public JSONObject PhotosFirstTrial(JSONObject headObject, String userid, String studentid, String result,
                                       String msg) {
        QueryWrapper<Student> wrapper = new QueryWrapper<Student>();
        wrapper.eq("code", studentid);
        Student student = studentService.selectOne(wrapper);
        //根据老师的评审修改学生的信息
        if (null != student) {
            //如果是不通过
            student.setUserId(userid);
            if (Constants.PHOTO_PASS.equals(result)) {
                student.setStatus("3");
                student.setMsg("");
            } else {
                student.setStatus("4");
                student.setMsg(msg);
            }
            student.setUpdateTime(new Date());
            studentService.updateById(student);
            return JSONUtil.getSuccessResult(headObject, new JSONObject());

        }
        return JSONUtil.getErrorResult(ResultEnum.ZFDD_ZWSJ);
    }

    @Override
    public JSONObject updatePhotos(JSONObject headObject, String userid, JSONArray array) {
        QueryWrapper<Student> wrapper = new QueryWrapper<Student>();
        wrapper.eq("code", userid);
        Student student = studentService.selectOne(wrapper);
        //修改学生状态 为待审核
        if (null != student) {
            student.setStatus("2");
            String id = student.getClassesId();
            //组装上传路径
            SysOrganize organize = sysOrganizeService.selectSysOrganizeById(id);
            String pid = organize.getPid();
            List<String> arr = new ArrayList<String>();
            arr.add(organize.getName());
            for (int i = 0; i < Integer.parseInt(num); i++) {
                if (pid.equals("0")) {
                    break;
                }
                organize = sysOrganizeService.selectSysOrganizeById(pid);
                pid = organize.getPid();
                id = organize.getId();
                arr.add(organize.getName());
            }

            String url = UrlUtils.getUrl(uploadFolder, arr);

            //上传图片信息
            Images image = null;
            for (int i = 0; i < array.size(); i++) {
                image = new Images();
                JSONObject object = array.getJSONObject(i);
                String content = object.getString("content");
                String picturetype = object.getString("picturetype");
                //拼接结构规则


                ImgErToFileUtil.saveToImgByStr(content, url, "1.jpg");


            }
            studentService.updateById(student);
        }
        return JSONUtil.getErrorResult(ResultEnum.ZFDD_ZWSJ);
    }


}
