package com.purdue.impulse

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.purdue.impulse.entities.EventItem
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import java.util.*
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.PublishCallback
import com.google.android.gms.nearby.messages.PublishOptions
import com.google.android.gms.nearby.messages.Strategy
import java.nio.charset.Charset

class AnnouncementActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener, com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {

    var dateTimeData: String = ""
    private val TTL_IN_SECONDS = 10 * 60
    private val PUB_SUB_STRATEGY = Strategy.Builder()
        .setTtlSeconds(TTL_IN_SECONDS).build()
    private var mMessage: Message? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announcement)
        setClickListeners();

    }

    fun setClickListeners() {

        val dateTimeView = findViewById<EditText>(R.id.event_time)
        dateTimeView.setOnClickListener {
            val now: Calendar = Calendar.getInstance()
            val datePickerDialog: DatePickerDialog = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show(supportFragmentManager, "DatePickerDialog")
        }
        val eventTitleView = findViewById<EditText>(R.id.event_title)
        val eventDetailsView = findViewById<EditText>(R.id.event_details)

        val eventBountyView = findViewById<EditText>(R.id.event_bounty)
        findViewById<Button>(R.id.make_announcement_button).setOnClickListener {
            val eventItem: EventItem = EventItem(eventTitleView.text.toString()
                , eventDetailsView.text.toString(), eventBountyView.text.toString().toDouble(), dateTimeData)
            publish(eventDetailsView)
            finish()
        }
        findViewById<Button>(R.id.cancel_button).setOnClickListener {
            finish()
        }

    }

    private fun publish(details: EditText) {
        Log.i("PUBLISHING MESSAGE", "Publishing")

        val options = PublishOptions.Builder()
            .setStrategy(PUB_SUB_STRATEGY)
            .setCallback(object : PublishCallback() {
                override fun onExpired() {
                    super.onExpired()
                    Log.i("PUBLISHING MESSAGE", "No longer publishing")

                }
            }).build()
        mMessage = Message(details.text.toString().toByteArray())

        Nearby.getMessagesClient(this).publish(mMessage!!, options)

    }

    override fun onTimeSet(view: TimePickerDialog?, hourOfDay: Int, minute: Int, second: Int) {
        dateTimeData = "$dateTimeData  $hourOfDay:$minute"
        findViewById<EditText>(R.id.event_time).setText(dateTimeData)
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val timePickerDialog: TimePickerDialog = TimePickerDialog.newInstance(this, true)
        timePickerDialog.show(supportFragmentManager, "TimePickerDialog")
        dateTimeData = "$dateTimeData$monthOfYear/$dayOfMonth/$year"
    }

}