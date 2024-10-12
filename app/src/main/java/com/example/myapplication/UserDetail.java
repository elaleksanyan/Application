package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserDetail extends AppCompatActivity {


    private ImageView avatarImageView;
    private TextView usernameTextView, bioTextView, followersTextView, followingTextView,
            reposTextView, gistsTextView, updatedAtTextView, locationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        ImageView backButton = findViewById(R.id.backButton);
        avatarImageView = findViewById(R.id.avatarImageView);
        usernameTextView = findViewById(R.id.usernameTextView);
        bioTextView = findViewById(R.id.bioDescriptionTextView);
        followersTextView = findViewById(R.id.followersTextView);
        followingTextView = findViewById(R.id.followingTextView);
        reposTextView = findViewById(R.id.repositoriesCountTextView);
        gistsTextView = findViewById(R.id.gistsCountTextView);
        updatedAtTextView = findViewById(R.id.updatedAtTextView);
        locationTextView = findViewById(R.id.locationTextView);
        String username = getIntent().getStringExtra("username");

        fetchUserDetails(username);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void fetchUserDetails(String username) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService gitHubService = retrofit.create(GitHubService.class);
        Call<User> call = gitHubService.getUser(username);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();

                    if (user.getAvatarUrl() != null) {
                        Picasso.get().load(user.getAvatarUrl())
                                .transform(new CircleTransform())
                                .placeholder(R.drawable.avatar_placeholder)
                                .into(avatarImageView);
                    } else {
                        avatarImageView.setImageResource(R.drawable.avatar_placeholder); // Use a placeholder image
                    }

                    usernameTextView.setText(user.getLogin()); // Use getLogin() for the username
                    bioTextView.setText(user.getBio() != null ? user.getBio() : "No bio available");
                    followersTextView.setText(user.getFollowers() + " Followers");
                    followingTextView.setText(user.getFollowing() + " Following");
                    reposTextView.setText(String.valueOf(user.getPublicRepos()));
                    gistsTextView.setText(String.valueOf(user.getPublicGists()));
                    updatedAtTextView.setText(user.getUpdatedAt() != null ? user.getUpdatedAt() : "Not available");
                    locationTextView.setText(user.getLocation() != null ? user.getLocation() : "Location not specified");
                } else {
                    Log.e("API Error", "Response not successful: " + response.message());
                }
            }


            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
    }
}
