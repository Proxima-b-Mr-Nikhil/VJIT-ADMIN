package com.nikhil.vjitadmin;

public class feedUpload {
    private String name;
    private String imageUrl;
    private String time;
    private String id;

    public feedUpload() {
        //empty constructor needed
    }

    public feedUpload(String name, String imageUrl,String time,String id) {

       this. name = name;
        this.time=time;
       this. imageUrl = imageUrl;
       this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}