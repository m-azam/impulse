package com.purdue.impulse

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
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
    private fun PackageManager.missingSystemFeature(name: String): Boolean = !hasSystemFeature(name)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash)
        var events: ArrayList<EventItem> = ArrayList()
        val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.event_recycler)

        mMessageListener = object : MessageListener() {
            override fun onFound(message: Message) {
                Log.i("INCOMING MESSAGE", "onFound: "+String(message.content))

                events.add(EventItem("Title",String(message.content) , 40.0,"1"))
                recyclerView.adapter?.notifyDataSetChanged()
            }
            override fun onLost(message: Message) {
                Log.i("ERROR", "onFound: ${message.content}")
            }
        }
        packageManager.takeIf { it.missingSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE) }?.also {
            Toast.makeText(this, "BLE NOT SUPPORTED", Toast.LENGTH_SHORT).show()
            finish()
        }
        subscribe()
        events.add(EventItem("Pizza at the clubhouse", "2nd December 8PM", 40.0,"1"))
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