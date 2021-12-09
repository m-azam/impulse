package com.purdue.impulse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.messages.*
import com.purdue.impulse.adapter.EventAdapter
import com.purdue.impulse.entities.EventItem


class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    private val TTL_IN_SECONDS = 3 * 60 // Three minutes.
    private val PUB_SUB_STRATEGY = Strategy.Builder()
        .setTtlSeconds(TTL_IN_SECONDS).build()

    private val mNearbyDevicesArrayAdapter: ArrayAdapter<String>? = null
    private var mMessageListener: MessageListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash)
        var events: ArrayList<EventItem> = ArrayList()

        mMessageListener = object : MessageListener() {
            override fun onFound(message: Message) {
                Log.d("INCOMING MESSAGE", "onFound: ${message.content}")
            }
            override fun onLost(message: Message) {
                Log.d("ERROR", "onFound: ${message.content}")
            }
        }
        subscribe()
        events.add(EventItem("Pizza at the clubhouse", "2nd December 8PM", 40.0,"1"))
        val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.event_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = EventAdapter(this, events)
        val makeAnnouncementButton = findViewById<Button>(R.id.make_announcement_dash)

        makeAnnouncementButton.setOnClickListener {
            val intent = Intent(this, AnnouncementActivity::class.java)
            this.startActivity(intent)
        }


    }
    private fun subscribe() {
        Log.i(TAG, "Subscribing")
        mNearbyDevicesArrayAdapter?.clear()
        val options = SubscribeOptions.Builder()
            .setStrategy(PUB_SUB_STRATEGY)
            .setCallback(object : SubscribeCallback() {
                override fun onExpired() {
                    super.onExpired()
                    Log.i(TAG, "No longer subscribing")
                }
            }).build()
        mMessageListener?.let { Nearby.getMessagesClient(this).subscribe(it, options) }
    }
}