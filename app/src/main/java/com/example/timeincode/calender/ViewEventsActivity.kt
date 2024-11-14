package com.example.timeincode

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

data class Event(val date: String, val time: String, val title: String, val description: String)

class ViewEventsActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var eventsLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_events)

        eventsLayout = findViewById(R.id.eventsLinearLayout)

        loadEventDataFromFirebase()
    }

    private fun loadEventDataFromFirebase() {
        database = FirebaseDatabase.getInstance().getReference("Events")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val selectedDate = intent.getStringExtra("selectedDate")

                    eventsLayout.removeAllViews()

                    val eventList = mutableListOf<Event>()

                    snapshot.children.forEach { childSnapshot ->
                        val eventDate = childSnapshot.child("date").getValue(String::class.java) ?: ""
                        val eventTime = childSnapshot.child("time").getValue(String::class.java) ?: ""
                        val eventTitle = childSnapshot.child("title").getValue(String::class.java) ?: ""
                        val eventDescription = childSnapshot.child("description").getValue(String::class.java) ?: ""

                        if (eventDate == selectedDate) {
                            val event = Event(eventDate, eventTime, eventTitle, eventDescription)
                            eventList.add(event)
                        }
                    }

                    // 시간 순으로 정렬
                    val sortedEventList = eventList.sortedWith(compareBy { it.time })

                    sortedEventList.forEach { event ->
                        val dateTextView = TextView(this@ViewEventsActivity).apply {
                            text = "선택된 날짜: ${event.date}"
                            textSize = 20f
                        }
                        val timeTextView = TextView(this@ViewEventsActivity).apply {
                            text = "시간: ${event.time}"
                            textSize = 20f
                        }
                        val titleTextView = TextView(this@ViewEventsActivity).apply {
                            text = "제목: ${event.title}"
                            textSize = 25f
                        }
                        val descriptionTextView = TextView(this@ViewEventsActivity).apply {
                            text = "내용: ${event.description}"
                            textSize = 20f
                        }

                        eventsLayout.addView(dateTextView)
                        eventsLayout.addView(titleTextView)
                        eventsLayout.addView(timeTextView)
                        eventsLayout.addView(descriptionTextView)

                        val divider = TextView(this@ViewEventsActivity).apply {
                            text = "--------------------------------"
                        }
                        eventsLayout.addView(divider)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // 에러 처리
            }
        })
    }
}
