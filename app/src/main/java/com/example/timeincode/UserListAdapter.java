package com.example.timeincode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class UserListAdapter extends ArrayAdapter<User> {
    private Context context;
    private List<User> users;

    public UserListAdapter(Context context, List<User> users) {
        super(context, 0, users);
        this.context = context;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        }

        TextView userNameTextView = convertView.findViewById(R.id.user_name);
        Button addFriendButton = convertView.findViewById(R.id.add_friend_button);

        User user = users.get(position);
        userNameTextView.setText(user.getUserName());

        addFriendButton.setOnClickListener(v -> addFriend(user.getUid()));

        return convertView;
    }

    private void addFriend(String friendId) {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference friendsRef = FirebaseDatabase.getInstance().getReference("friends");

        friendsRef.child(currentUserId).child(friendId).setValue(true)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(context, "친구 추가 되었습니다", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "친구추가 실패", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
