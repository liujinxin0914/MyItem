package com.message.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author fanxialong
 * @since 2019-06-02
 */
public class StudentVo {


    /**
     * 主键
     */
    private String code;
    /**
     * 学生名称
     */
    private String name;
    /**
     * 身份证号
     */
    private String idcard;

    private String classesId;
    /**
     * 0：全部
     * 1：未上传
     * 2：待审核
     * 3、初审通过
     * 4、初审失败
     * 5、美工已下载
     * 6、复审通过
     * 7:复审失败
     */
    private String status;


    private List<Images> photos;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getClassesId() {
        return classesId;
    }

    public void setClassesId(String classesId) {
        this.classesId = classesId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public List<Images> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Images> photos) {
        this.photos = photos;
    }


}
