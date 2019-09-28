package com.message.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * <p>
 * 教师班级对应表
 * </p>
 *
 * @author fanxialong
 * @since 2019-06-02
 */
@TableName("teacher_classes")
public class TeacherClasses extends Model<TeacherClasses> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    /**
     * 教师id
     */
    @TableField("tea_id")
    private String teaId;
    /**
     * 机构id
     */
    @TableField("org_id")
    private String orgId;
    /**
     * 备注1
     */
    private String resver1;
    private String resver2;
    private String resver3;
    private String resver4;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeaId() {
        return teaId;
    }

    public void setTeaId(String teaId) {
        this.teaId = teaId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
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
        return this.id;
    }

    @Override
    public String toString() {
        return "TeacherClasses{" +
                ", id=" + id +
                ", teaId=" + teaId +
                ", orgId=" + orgId +
                ", resver1=" + resver1 +
                ", resver2=" + resver2 +
                ", resver3=" + resver3 +
                ", resver4=" + resver4 +
                "}";
    }
}
