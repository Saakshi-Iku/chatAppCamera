package com.example.chatappss.Model;

public class Chat {

    private String sender;
    private String receiver;
    private String message;
    private boolean isseen;

    public Chat(String sender,String receiver,String message,boolean isseen){
        this.message=message;
        this.sender=sender;
        this.receiver=receiver;
        this.isseen = isseen;
    }

    public Chat(){

    }

    public String getMessage() {
        return message;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }

    public boolean isIsseen() {
        return isseen;
    }

    public void setIsseen(boolean isseen) {
        this.isseen = isseen;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }


}

