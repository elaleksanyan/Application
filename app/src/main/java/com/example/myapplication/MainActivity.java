package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
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
    private List<User> filteredUserList = new ArrayList<>(); // To hold filtered users
    private EditText searchBar; // Search bar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView and Search Bar
        recyclerView = findViewById(R.id.recyclerView);
        searchBar = findViewById(R.id.searchBar); // Initialize search bar

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(this, filteredUserList, user -> {
            // Navigate to user details page when item clicked
            Intent intent = new Intent(MainActivity.this, UserDetail.class);
            intent.putExtra("username", user.getLogin());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        // Fetch users from API
        fetchUsers();

        // Set up search functionality
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterUsers(s.toString()); // Filter users based on search input
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
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
                    filteredUserList.addAll(userList); // Initially show all users
                    adapter.notifyDataSetChanged(); // Notify adapter
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

    private void filterUsers(String query) {
        filteredUserList.clear(); // Clear the filtered list
        if (query.isEmpty()) {
            filteredUserList.addAll(userList); // Show all users if search query is empty
        } else {
            for (User user : userList) {
                if (user.getLogin().toLowerCase().contains(query.toLowerCase())) {
                    filteredUserList.add(user); // Add matching users to filtered list
                }
            }
        }
        adapter.notifyDataSetChanged(); // Notify adapter of changes
    }
}