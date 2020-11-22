package com.example.chatappss.Model;

public class User {

    private String id;
    private String username;
    private String imageUrl;
    private String status;
    public User(String id,String username,String imageUrl,String status)
    {
        this.id=id;
        this.username=username;
        this.imageUrl=imageUrl;
        this.status = status;
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

    public String getStatus() {
        return status;
    }
}
