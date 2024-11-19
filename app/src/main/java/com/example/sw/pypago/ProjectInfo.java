package com.example.sw.pypago;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.google.firebase.firestore.Exclude;

import java.util.Date;
import java.util.List;

public class ProjectInfo {
    private String title;

    private String documentId;
    private String purpose;
    private long time;
    private String date;

    private String userID;
    public ProjectInfo(){ }
    public ProjectInfo(String userID,String title,String purpose, long time, String day){
        this.title =title;
        this.purpose = purpose;
        this.time = time;
        this.date = day;
        this.userID = userID;


    }
    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }


    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

}

