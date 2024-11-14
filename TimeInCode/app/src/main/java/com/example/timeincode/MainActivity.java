package com.example.timeincode;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new GroupFragment()).commit();
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