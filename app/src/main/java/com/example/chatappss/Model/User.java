package com.example.chatappss.Model;

public class User {

    private String id;
    private String username;
    private String imageUrl;
    private String status;
    private String search;
    public User(String id,String username,String imageUrl,String status,String search)
    {
        this.id=id;
        this.username=username;
        this.imageUrl=imageUrl;
        this.status = status;
        this.search = search;
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

    public String getSearch() {
        return search;
    }
}
