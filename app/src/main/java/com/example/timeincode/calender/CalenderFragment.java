package com.example.timeincode.calender;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.timeincode.AddEventActivity;
import com.example.timeincode.NoActivity;
import com.example.timeincode.R;
import com.example.timeincode.ViewEventsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalenderFragment extends Fragment {
    private String selectedDateString = null;
    private DataSnapshot matchingEvent = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calender, container, false);

        CalendarView calendarView = view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                selectedDateString = dateFormat.format(selectedDate.getTime());
                loadEventDataFromFirebase();
            }
        });

        Button addEventButton = view.findViewById(R.id.addEventButton);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedDateString != null) {
                    Intent intent = new Intent(getActivity(), AddEventActivity.class);
                    intent.putExtra("selectedDate", selectedDateString);
                    startActivity(intent);
                }
            }
        });

        Button viewEventsButton = view.findViewById(R.id.viewEventsButton);
        viewEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedDateString != null) {
                    if (matchingEvent != null) {
                        Intent intent = new Intent(getActivity(), ViewEventsActivity.class);
                        intent.putExtra("selectedDate", selectedDateString);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(), NoActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        return view;
    }

    private void loadEventDataFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("Events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                matchingEvent = null;
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                String date = childSnapshot.child("date").getValue(String.class);
                if (date != null && date.equals(selectedDateString)) {
                    matchingEvent = childSnapshot;
                    break;
                }
            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 에러 처리
            }
        });
    }
}
