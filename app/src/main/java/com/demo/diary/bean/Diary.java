package com.demo.diary.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by GuangSIR on 2017/11/30.
 * Description : 日记对应的实体类
 */

public class Diary extends DataSupport {
    private int id;
    private String picPath;
    private String date;
    private String content;

    public Diary() {
    }

    public Diary(String picPath, String date, String content) {
        this.picPath = picPath;
        this.date = date;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Diary{" +
                "id=" + id +
                ", picPath='" + picPath + '\'' +
                ", date='" + date + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
