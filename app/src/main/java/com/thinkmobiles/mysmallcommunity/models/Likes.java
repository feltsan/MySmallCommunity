package com.thinkmobiles.mysmallcommunity.models;

/**
 * Created by dreamfire on 26.11.15.
 */
public class Likes {
    private String userId;
    private String postId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
