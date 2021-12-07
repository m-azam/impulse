package com.purdue.impulse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.purdue.impulse.adapter.EventAdapter
import com.purdue.impulse.entities.EventItem

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash)
        var events: ArrayList<EventItem> = ArrayList()
        events.add(EventItem("Pizza at the clubhouse", "2nd December 8PM", 40.0,"1"))
        var recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.event_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = EventAdapter(this, events)
        val makeAnnouncementButton = findViewById<Button>(R.id.make_announcement_dash)
        makeAnnouncementButton.setOnClickListener {
            val intent = Intent(this, AnnouncementActivity::class.java)
            this.startActivity(intent)
        }
    }
}