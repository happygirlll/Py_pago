package com.example.sw.pypago;

public class ProvblemData {
    private String name;
    private String appLink;

    public ProvblemData(){

    }
    public ProvblemData(String _name,String applink){
        this.name = _name;

        this.appLink = applink;

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

}
