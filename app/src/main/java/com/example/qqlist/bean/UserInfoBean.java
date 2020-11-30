package com.example.qqlist.bean;

import org.litepal.crud.DataSupport;

/**
 * @Author: Paper
 * time :2019/10/6 14:03
 */
public class UserInfoBean extends DataSupport {

    private String name;
    private String pwd;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
