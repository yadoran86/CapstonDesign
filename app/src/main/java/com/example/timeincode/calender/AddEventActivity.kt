package com.example.timeincode

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.timeincode.R
import com.example.timeincode.Event
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import java.text.SimpleDateFormat


class AddEventActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {
    private lateinit var eventTimeTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        var time :String = ""

        val selectedDate = intent.getStringExtra("selectedDate")
        findViewById<TextView>(R.id.selectedDateTextView).text = "선택된 날짜: $selectedDate"

        eventTimeTextView = findViewById(R.id.eventTimeTextView)

        findViewById<Button>(R.id.pickTimeButton).setOnClickListener {
            val calendar = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)

                // SimpleDateFormat을 사용하여 원하는 시간 형식으로 포맷
                val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                // 선택된 시간을 time 변수에 저장
                time = timeFormat.format(calendar.time)
            }
            val now = Calendar.getInstance()
            TimePickerDialog(this, this, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true).show()
        }

        findViewById<Button>(R.id.completeButton).setOnClickListener {
            // 여기에서 일정 저장 로직을 구현합니다.

            // EditText 참조 얻기
            val editTextDescription = findViewById<EditText>(R.id.eventDescriptionEditText)
            val editTextTitle = findViewById<EditText>(R.id.eventTitleEditText)
            val editTextTime = findViewById<TextView>(R.id.eventTimeTextView)

            // EditText로부터 텍스트를 읽어와서 description 변수에 할당
            var description = editTextDescription.text.toString()
            var title = editTextTitle.text.toString()
            var time = editTextTime.text.toString()
            var date = selectedDate


            val databaseReference = FirebaseDatabase.getInstance().getReference("Events")
            val eventId = databaseReference.push().key

            val event = date?.let { it1 -> Event(title, description, it1, time) }

            if (eventId != null) {
                databaseReference.child(eventId).setValue(event).addOnCompleteListener {
                    Toast.makeText(applicationContext, "일정이 추가되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        eventTimeTextView.text = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute)
    }
}
