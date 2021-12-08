package com.purdue.impulse

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

class PublishSubscribeMessage: AppCompatActivity(){
    val TAG = "PublishActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val messageListener = object : MessageListener() {
            override fun onFound(message: Message) {
                Log.d(TAG, "onFound: ${message.content}")
            }
            override fun onLost(message: Message) {
                Log.d(TAG, "onFound: ${message.content}")
            }
        }
    }
    fun publish(message: String){
        Log.i(TAG, "Publishing message: $message")
        val mActiveMessage = Message(message.toByteArray());
        Nearby.getMessagesClient(this).publish(mActiveMessage);
    }



}