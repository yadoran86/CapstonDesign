package com.example.timeincode;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.timeincode.calender.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.mainactivity_bottomnavigationview);

        //처음화면
        getSupportFragmentManager().beginTransaction().add(R.id.main_frame, new CalenderFragment()).commit(); //FrameLayout에 fragment.xml 띄우기

        bottomNavigationView.setOnNavigationItemSelectedListener((item) -> {
            if (item.getItemId() == R.id.action_calender) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new CalenderFragment()).commit();
            }
            if (item.getItemId() == R.id.action_group) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new FriendFragment()).commit();
            }
            if (item.getItemId() == R.id.action_chatting) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new ChatFragment()).commit();
            }
            if (item.getItemId() == R.id.action_account) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new AccountFragment()).commit();
            }

            return true;

        });

    }
}