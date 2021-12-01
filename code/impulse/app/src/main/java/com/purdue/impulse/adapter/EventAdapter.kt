package com.purdue.impulse.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.view.LayoutInflater
import android.view.View

import android.widget.TextView
import com.purdue.impulse.R
import com.purdue.impulse.entities.EventItem

class EventAdapter(var context: Context, var events: List<EventItem>) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventAdapter.ViewHolder {
        var view: View = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.eventTitle.text = events[position].eventTitle
        holder.eventDetails.text = events[position].eventDetails
    }

    override fun getItemCount(): Int {
        return events.size
    }

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var eventTitle: TextView = itemView.findViewById(R.id.item_title)
        var eventDetails: TextView = itemView.findViewById(R.id.item_description)
    }

}