package com.example.com.fullscreenverticalplay;

/**
 * create by libo
 * description
 */
public class VideoBean {
    private int picRes;
    private int videoRes;

    public VideoBean(int picRes, int videoRes) {
        this.picRes = picRes;
        this.videoRes = videoRes;
    }

    public int getPicRes() {
        return picRes;
    }

    public void setPicRes(int picRes) {
        this.picRes = picRes;
    }

    public int getVideoRes() {
        return videoRes;
    }

    public void setVideoRes(int videoRes) {
        this.videoRes = videoRes;
    }
}
