package com.example.yupeng.todoapp;

import java.util.Date;
import java.util.UUID;

public class Task {
    private UUID mUUID;
    private Date mData;
    private String mTitle;
    private String mContent;
    private Boolean mSolved = false;
    private String mReporter;

    public UUID getUUID() {
        return mUUID;
    }

    public Date getDate() {
        return mData;
    }

    public void setDate(Date data) {
        mData = data;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public Boolean getSolved() {
        return mSolved;
    }

    public void setSolved(Boolean solved) {
        mSolved = solved;
    }

    public Task() {
        mUUID = UUID.randomUUID();
        mData = new Date();
    }

    public Task(UUID uuid) {
        mUUID = uuid;
        mData =  new Date();
    }

    public String getReporter() {
        return mReporter;
    }

    public void setReporter(String mReporter) {
        this.mReporter = mReporter;
    }
}
