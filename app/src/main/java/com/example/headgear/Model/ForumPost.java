package com.example.headgear.Model;

public class ForumPost {
    private String Message;
    private String UserID;

    public ForumPost() {
    }

    public ForumPost(String message, String userID) {

        Message = message;
        UserID = userID;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}


