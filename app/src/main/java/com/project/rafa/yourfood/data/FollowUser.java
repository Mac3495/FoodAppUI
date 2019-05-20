package com.project.rafa.yourfood.data;

public class FollowUser {
    private String followedUserId;
    private String followerUserId;

    public FollowUser(String followedUserId, String followerUserId) {
        this.followedUserId = followedUserId;
        this.followerUserId = followerUserId;
    }

    public FollowUser() {
    }

    public String getFollowedUserId() {
        return followedUserId;
    }

    public void setFollowedUserId(String followedUserId) {
        this.followedUserId = followedUserId;
    }

    public String getFollowerUserId() {
        return followerUserId;
    }

    public void setFollowerUserId(String followerUserId) {
        this.followerUserId = followerUserId;
    }

    @Override
    public String toString() {
        return "FollowUser{" +
                "followedUserId='" + followedUserId + '\'' +
                ", followerUserId='" + followerUserId + '\'' +
                '}';
    }
}
