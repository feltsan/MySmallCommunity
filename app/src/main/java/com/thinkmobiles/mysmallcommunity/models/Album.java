package com.thinkmobiles.mysmallcommunity.models;

import android.graphics.Bitmap;

/**
 * Created by dreamfire on 30.11.15.
 */
public class Album {
    private String name;
    private int count;
    private String albumId;
    private Bitmap albumIcon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public Bitmap getAlbumIcon() {
        return albumIcon;
    }

    public void setAlbumIcon(Bitmap albumIcon) {
        this.albumIcon = albumIcon;
    }
}
