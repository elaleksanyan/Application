package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private final Context context;
    private final List<User> userList;
    private final OnUserClickListener onUserClickListener;

    public UserAdapter(Context context, List<User> userList, OnUserClickListener onUserClickListener) {
        this.context = context;
        this.userList = userList;
        this.onUserClickListener = onUserClickListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_list_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);

        holder.usernameTextView.setText(user.getLogin());
        holder.userIdTextView.setText("Id: " + user.getId());

        // Load avatar with Glide
        Glide.with(context)
                .load(user.getAvatarUrl())
                .circleCrop()
                .into(holder.avatarImageView);

        // Set click listener on item
        holder.itemView.setOnClickListener(v -> onUserClickListener.onUserClick(user));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        ImageView avatarImageView;
        TextView usernameTextView;
        TextView userIdTextView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.avatarImageView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            userIdTextView = itemView.findViewById(R.id.userIdTextView);
        }
    }

    public interface OnUserClickListener {
        void onUserClick(User user);
    }
}