package com.example.weatherApp.alarm

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class AlarmOneWorkmanger (private val context: Context, private val params : WorkerParameters)
    : CoroutineWorker(context,params ) {
    override suspend fun doWork(): Result {
        val inputData = inputData
        val description = inputData.getString("description")
        val icon = inputData.getString("icon")
        startMyService(description!!,icon)
        return Result.success()


    }

    private fun startMyService(description: String, icon: String?) {
        val intent = Intent(applicationContext, AlarmService::class.java)
        intent.putExtra("description", description)
        intent.putExtra("icon", icon)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(applicationContext, intent)
        } else {
            applicationContext.startService(intent)
        }
    }
}