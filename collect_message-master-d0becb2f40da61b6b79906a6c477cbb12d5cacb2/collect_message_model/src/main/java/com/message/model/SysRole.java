package com.message.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author fanxialong
 * @since 2019-06-02
 */
@TableName("sys_role")
public class SysRole extends Model<SysRole> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 父级id
     */
    private String pid;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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
        return "SysRole{" +
                ", id=" + id +
                ", name=" + name +
                ", pid=" + pid +
                ", resver1=" + resver1 +
                ", resver2=" + resver2 +
                ", resver3=" + resver3 +
                ", resver4=" + resver4 +
                "}";
    }
}
