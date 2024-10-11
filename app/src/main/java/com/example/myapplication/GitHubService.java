package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubService {

    // For getting list of users
    @GET("users")
    Call<List<User>> getUsers();

    // For getting a specific user's details
    @GET("users/{username}")
    Call<UserDetail> getUserDetails(@Path("username") String username);
}