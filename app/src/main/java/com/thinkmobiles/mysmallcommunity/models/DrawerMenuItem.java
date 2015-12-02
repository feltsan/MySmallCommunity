package com.thinkmobiles.mysmallcommunity.models;

import android.graphics.drawable.Drawable;

import com.thinkmobiles.mysmallcommunity.ui.custom_views.CustomBadge;

/**
 * Created by feltsan on 30.11.15.
 */
public class DrawerMenuItem {
    private int id;
    private Drawable iconRecource;
    private String title;
    private int countBadge;

    public DrawerMenuItem(int id, Drawable iconRecource, String title) {
        this.id = id;
        this.iconRecource = iconRecource;
        this.title = title;
        this.countBadge = 0;
    }

    public DrawerMenuItem(int id, Drawable iconRecource, String title, int badge) {
        this.id = id;
        this.iconRecource = iconRecource;
        this.title = title;
        this.countBadge = badge;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Drawable getIcon() {
        return iconRecource;
    }

    public void setIcon(Drawable iconRecource) {
        this.iconRecource = iconRecource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCountBadge() {
        return countBadge;
    }

    public void setCountBadge(int countBadge) {
        this.countBadge = countBadge;
    }
}
