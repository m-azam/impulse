package com.purdue.impulse

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.purdue.impulse.entities.EventItem
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import java.util.*

class AnnouncementActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener, com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {

    var dateTimeData: String = ""
    val publishMessage = PublishSubscribeMessage()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announcement)
        setClickListeners();
    }

    private fun setClickListeners() {

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
            finish()
            publishMessage.publish(eventDetailsView.text.toString())
        }
        findViewById<Button>(R.id.cancel_button).setOnClickListener {
            finish()
        }
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