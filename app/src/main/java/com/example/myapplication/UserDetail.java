package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class UserDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        // Retrieve the username passed from the MainActivity
        String username = getIntent().getStringExtra("username");

        // Fetch and display the user details (to be implemented)
    }
}