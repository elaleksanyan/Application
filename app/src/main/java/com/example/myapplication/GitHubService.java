package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubService {
    @GET("users")
    Call<List<User>> getUsers();

    @GET("users/{username}")
    Call<User> getUser(@Path("username") String username);
}