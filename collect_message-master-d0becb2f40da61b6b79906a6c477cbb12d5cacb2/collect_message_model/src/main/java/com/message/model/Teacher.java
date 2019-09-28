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
public class Teacher extends Model<Teacher> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String idcard;
    /**
     * 教师姓名
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 所属机构（最后一层）
     */
    @TableField("org_id")
    private String orgId;
    /**
     * 微信的 openid 支付宝id
     */
    @TableField("channel_id")
    private String channelId;
    /**
     * appid
     */
    private String channel;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 性别(0:女1:男)
     */
    private String sex;
    /**
     * 备注1
     */
    private String resver1;
    private String resver2;
    private String resver3;
    private String resver4;


    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
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

    public String getResver1() {
        return resver1;
    }

    public void setResver1(String resver1) {
        this.resver1 = resver1;
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
        return this.idcard;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                ", idcard=" + idcard +
                ", name=" + name +
                ", password=" + password +
                ", orgId=" + orgId +
                ", channelId=" + channelId +
                ", channel=" + channel +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", sex=" + sex +
                ", resver1=" + resver1 +
                ", resver2=" + resver2 +
                ", resver3=" + resver3 +
                ", resver4=" + resver4 +
                "}";
    }
}
