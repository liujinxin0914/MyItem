package com.message.model;

import java.util.List;

public class SysOrganizeVo {
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 父级id
     */
    private String pid;
    /**
     * 禁用 0:否1：是
     */
    private String status;


    /**
     * 机构学生 总数
     */
    private Integer total;

    /**
     * 未上传总数
     */
    private Integer noupload;

    /**
     * 待审核总数
     */
    private Integer nocheck;

    /**
     * 待审核总数
     */
    private Integer finished;


    private List<SysOrganizeVo> child;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public List<SysOrganizeVo> getChild() {
        return child;
    }

    public void setChild(List<SysOrganizeVo> child) {
        this.child = child;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getNoupload() {
        return noupload;
    }

    public void setNoupload(Integer noupload) {
        this.noupload = noupload;
    }

    public Integer getNocheck() {
        return nocheck;
    }

    public void setNocheck(Integer nocheck) {
        this.nocheck = nocheck;
    }

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }


}
