package com.message.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

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
public class Student extends Model<Student> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String code;
    /**
     * 学生名称
     */
    private String name;
    /**
     * 身份证号
     */
    private String idcard;
    /**
     * 密码
     */
    private String password;
    /**
     * appid
     */
    private String channel;
    /**
     * openid
     */
    @TableField("channel_id")
    private String channelId;
    /**
     * 所属班级
     */
    @TableField("classes_id")
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
    /**
     * 操作者
     */
    @TableField("user_id")
    private String userId;
    /**
     * 操作类型
     */
    private String operation;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 更更新时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 性别(0:女1:男)
     */
    private String sex;
    /**
     * 审核说明
     */
    private String msg;
    private String resver2;
    private String resver3;
    private String resver4;


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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResver2() {
        return resver2;
    }

    public void setResver2(String resver2) {
        this.resver2 = resver2;
    }

    public String getResver3() {
        return resver3;
    }

    public void setResver3(String resver3) {
        this.resver3 = resver3;
    }

    public String getResver4() {
        return resver4;
    }

    public void setResver4(String resver4) {
        this.resver4 = resver4;
    }

    @Override
    protected Serializable pkVal() {
        return this.code;
    }

    @Override
    public String toString() {
        return "Student{" +
                ", code=" + code +
                ", name=" + name +
                ", idcard=" + idcard +
                ", password=" + password +
                ", channel=" + channel +
                ", channelId=" + channelId +
                ", classesId=" + classesId +
                ", status=" + status +
                ", userId=" + userId +
                ", operation=" + operation +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", sex=" + sex +
                ", msg=" + msg +
                ", resver2=" + resver2 +
                ", resver3=" + resver3 +
                ", resver4=" + resver4 +
                "}";
    }
}
