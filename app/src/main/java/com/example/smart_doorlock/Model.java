package com.example.smart_doorlock;

public class Model {

    public String imageUrl;
    public String time;
    public String name;

    public Model() {}

    public Model(String imageUrl, String name, String time) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.time = time;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

