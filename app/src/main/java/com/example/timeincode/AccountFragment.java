package com.example.timeincode;

import static androidx.compose.ui.semantics.SemanticsPropertiesKt.setText;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.TestLooperManager;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.timeincode.model.UserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.net.InternetDomainName;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class AccountFragment extends Fragment {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView emailText;
    private TextView usernameText;
    private ImageView profileImage;
    private StorageReference mStorage;
    private StorageReference pathRef;
    private Button logout;
    private Button add_friend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uid = currentUser.getUid();
        //프로필 사진 가져오기
        FirebaseStorage storage = FirebaseStorage.getInstance();
        mStorage = storage.getReference();
        pathRef = mStorage.child("userImages").child(uid);
        pathRef.getDownloadUrl().addOnSuccessListener(
                new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        profileImage = view.findViewById(R.id.profile_ImageView);
                        Picasso.get().load(uri).into(profileImage);
                    }
                }
        );

        //username 가져오기
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(uid).child("userName").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String username = snapshot.getValue(String.class);
                        usernameText = view.findViewById(R.id.usernameTextView);
                        usernameText.setText(username);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

        //이메일 주소 가져오기
        emailText = view.findViewById(R.id.emailTextView);
        emailText.setText(currentUser.getEmail());

        //로그아웃
        logout = view.findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutConfirmationDialog();
            }
        });

        add_friend = view.findViewById(R.id.add_friend_button);
        add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Add_friend.class);
                startActivity(intent);

            }
        });

        return view;

    }
    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Firebase에서 로그아웃
                        mAuth.signOut();
                        // 로그인 화면으로 이동
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish(); // 현재 액티비티 종료
                    }
                })
                .setNegativeButton("아니오", null)
                .show();
    }
}