package com.chutianlong.pojo;

public class Teacher {
    private String idcard;
    private String name;
    private String password;
    private int org_id;
    private String channel_id;
    private String channel;
    private String create_time;
    private String update_time;
    private int sex;
    private String mobile;
    private int ismaster;
    private String only;
    private String classname;

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

    public int getOrg_id() {
        return org_id;
    }

    public void setOrg_id(int org_id) {
        this.org_id = org_id;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getIsmaster() {
        return ismaster;
    }

    public void setIsmaster(int ismaster) {
        this.ismaster = ismaster;
    }

    public String getOnly() {
        return only;
    }

    public void setOnly(String only) {
        this.only = only;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "idcard='" + idcard + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", org_id=" + org_id +
                ", channel_id='" + channel_id + '\'' +
                ", channel='" + channel + '\'' +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                ", sex=" + sex +
                ", mobile='" + mobile + '\'' +
                ", ismaster=" + ismaster +
                ", only='" + only + '\'' +
                ", classname='" + classname + '\'' +
                '}';
    }

    public Teacher() {
        super();
    }
}
