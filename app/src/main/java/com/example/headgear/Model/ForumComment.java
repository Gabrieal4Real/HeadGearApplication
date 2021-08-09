package com.example.headgear.Model;

public class ForumComment {
    private String CommentMessage;
    private String PostID;
    private String UserID;

    public ForumComment() {
    }

    public ForumComment(String commentMessage, String postID, String userID) {
        CommentMessage = commentMessage;
        PostID = postID;
        UserID = userID;
    }

    public String getCommentMessage() {
        return CommentMessage;
    }

    public void setCommentMessage(String commentMessage) {
        CommentMessage = commentMessage;
    }

    public String getPostID() {
        return PostID;
    }

    public void setPostID(String postID) {
        PostID = postID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}
