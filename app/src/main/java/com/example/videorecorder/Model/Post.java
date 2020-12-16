package com.example.videorecorder.Model;

public class Post {
    private String userId;
    private String postId;
    private String video;

    public Post(String userId, String postId, String video) {
        this.userId = userId;
        this.postId = postId;
        this.video = video;
    }

    public Post() {
    }

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

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
