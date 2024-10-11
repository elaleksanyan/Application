package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize adapter and set it to RecyclerView
        adapter = new UserAdapter(this, userList, user -> {
            // Navigate to user details page when item clicked
            Intent intent = new Intent(MainActivity.this, UserDetail.class);
            intent.putExtra("username", user.getLogin());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        // Fetch users from API
        fetchUsers();
    }

    private void fetchUsers() {
        GitHubService service = RetrofitClient.getClient().create(GitHubService.class);

        // Make API call to get the first 30 users
        Call<List<User>> call = service.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userList.addAll(response.body().subList(0, 30)); // Fetch first 30 users
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("API Response", "Failed to get users.");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("API Error", t.getMessage());
            }
        });
    }
}