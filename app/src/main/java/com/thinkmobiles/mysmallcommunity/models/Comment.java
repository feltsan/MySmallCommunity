package com.thinkmobiles.mysmallcommunity.models;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by dreamfire on 02.12.15.
 */
public class Comment {
    private Bitmap userIcon;
    private String userName;
    private String text;
    private Date date;

    public Bitmap getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(Bitmap userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
