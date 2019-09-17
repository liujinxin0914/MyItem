package com.chutianlong.pojo;

public class Sys_organize {
    private int id;
    private String name;
    private int pid;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Sys_organize{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pid=" + pid +
                ", status='" + status + '\'' +
                '}';
    }
}
