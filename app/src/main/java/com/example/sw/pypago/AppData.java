package com.example.sw.pypago;

import android.graphics.drawable.Drawable;

import com.android.volley.toolbox.StringRequest;


public class AppData {
    private String name;
    private String author;
    private String publisher;
    private String appLink;
    private String picture;
    private String price;
    private String date;
    public AppData(){

    }
    public AppData(String _name,String author,String publisher,String date, String price,String applink,String picture){
        this.name = _name;
        this.publisher = publisher;
        this.appLink = applink;
        this.author = author;
        this.date = date;
        this.price = price;
        this.picture = picture;
    }
    public String getName(){
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getApplink(){
        return appLink;
    }
    public void setApplink(String applink) {
        this.appLink = applink;
    }
    public String getPicture(){
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
    public String getAuthor(){
        return author;
    }
    public void setAuthor(String author)  { this.author = author;}
    public String getPublisher(){
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public String getPrice(){
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getDate(){
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

}
