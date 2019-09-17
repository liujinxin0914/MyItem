package com.chutianlong.pojo;

public class XXX {
    private String classes;
    private String teacher;
    private int count;
    private int notcount;

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getNotcount() {
        return notcount;
    }

    @Override
    public String toString() {
        return "XXX{" +
                "classes='" + classes + '\'' +
                ", teacher='" + teacher + '\'' +
                ", count=" + count +
                ", notcount=" + notcount +
                '}';
    }

    public void setNotcount(int notcount) {
        this.notcount = notcount;
    }
}
