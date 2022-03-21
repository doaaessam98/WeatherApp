package com.example.weatherApp.alarm

import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmReceiver : BroadcastReceiver()  {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Log.e(TAG, "onReceive: ", )
    }
}