package com.thinkmobiles.mysmallcommunity.models;

import android.graphics.Bitmap;

/**
 * Created by dreamfire on 30.11.15.
 */
public class Galleryitem {
    private Bitmap bitmap;
    private String id;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
