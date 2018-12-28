package com.mgh3326.app;

public class Music {
    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmArtist() {
        return mArtist;
    }

    public void setmArtist(String mArtist) {
        this.mArtist = mArtist;
    }

    public String getmAlbum() {
        return mAlbum;
    }

    public void setmAlbum(String mAlbum) {
        this.mAlbum = mAlbum;
    }

    public String getmReleased() {
        return mReleased;
    }

    public void setmReleased(String mReleased) {
        this.mReleased = mReleased;
    }

    public String getmGenre() {
        return mGenre;
    }

    public void setmGenre(String mGenre) {
        this.mGenre = mGenre;
    }

    public String getmImagePath() {
        return mImagePath;
    }

    public void setmImagePath(String mImagePath) {
        this.mImagePath = mImagePath;
    }

    public String getmLyric() {
        return mLyric;
    }

    public void setmLyric(String mLyric) {
        this.mLyric = mLyric;
    }

    public int getmResultCode() {
        return mResultCode;
    }

    public void setmResultCode(int mResultCode) {
        this.mResultCode = mResultCode;
    }

    String mTitle = "";
    String mArtist = "";
    String mAlbum = "";
    String mReleased = "";
    String mGenre = "";
    String mImagePath = "";
    String mLyric = "";
    int mResultCode = 0;

    public Music() {
    }

}
