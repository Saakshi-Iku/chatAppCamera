package com.example.chatappss.Model;

public class User {

    private String id;
    private String username;
    private String imageUrl;
    public User(String id,String username,String imageUrl)
    {
        this.id=id;
        this.username=username;
        this.imageUrl=imageUrl;
    }
    public User(){

    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUsername() {
        return username;
    }
}
