package com.example.qqlist.bean;

import org.litepal.crud.DataSupport;

/**
 * @Author: Paper
 * time :2019/10/6 14:03
 */
public class QQBean extends DataSupport {

    private String name;
    private String contents;
    private String times;
    private  int mPic;

    public int getmPic() {
        return mPic;
    }

    public void setmPic(int mPic) {
        this.mPic = mPic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }
}
