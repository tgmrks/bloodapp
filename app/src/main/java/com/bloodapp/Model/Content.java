package com.bloodapp.Model;

import android.graphics.drawable.Drawable;

public class Content {

    private int id;
    private String title;
    private String subTitle;
    private Drawable drawable;
    private byte thumbnail;
    private int drawId;
    private boolean status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public void setThumbnail(byte thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getDrawId() {
        return drawId;
    }

    public void setDrawId(int drawId) {
        this.drawId = drawId;
    }

    public Content(String title, int drawId) {
        this.title = title;
        this.drawId = drawId;
    }
}
