package com.example.myapplication;

public class User {
    private String login;
    private String avatar_url;
    private int id;
    private String bio;
    private String location;
    private int followers;
    private int following;
    private int public_repos;
    private int public_gists;
    private String updated_at;

    public String getLogin() {
        return login;
    }

    public String getAvatarUrl() {
        return avatar_url;
    }

    public int getId() {
        return id;
    }

    public String getBio() {
        return bio;
    }

    public String getLocation() {
        return location;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }

    public int getPublicRepos() {
        return public_repos;
    }

    public int getPublicGists() {
        return public_gists;
    }

    public String getUpdatedAt() {
        return updated_at;
    }
}
