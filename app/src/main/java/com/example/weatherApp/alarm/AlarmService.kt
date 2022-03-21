package com.example.weatherApp.alarm

import android.app.*
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import androidx.core.app.NotificationCompat
import com.example.weatherApp.R
import com.example.weatherApp.main.MainActivity

class AlarmService:Service() {
    val CHANNEL_ID = 1
    val FOREGROUND_ID = 2
    var notificationManager: NotificationManager? = null
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        var description = intent?.getStringExtra("description")
        var icon = intent?.getStringExtra("icon")
        notificationChannel()
        startForeground(FOREGROUND_ID, makeNotification(description!!, icon!!))
        if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Settings.canDrawOverlays(this.applicationContext)

            } else {
                TODO("VERSION.SDK_INT < M")
            }
        )
        {

        }
            return START_NOT_STICKY
    }

        private fun makeNotification(description: String, icon: String): Notification {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            // val bitmap = BitmapFactory.decodeResource(resources, getIcon(icon))

            return NotificationCompat.Builder(
                applicationContext, "$CHANNEL_ID"
            )
                //.setSmallIcon(getIcon(icon))
                .setContentText(description)
                .setContentTitle("Weather Alarm")
                // .setLargeIcon(bitmap)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(
                    Uri.parse(
                        ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + this.packageName +
                                "/" + R.raw.empty
                    )
                )
                .setAutoCancel(true)
                .build()


    }

        private fun notificationChannel() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name: CharSequence = getString(R.string.channel_name)
                val description = getString(R.string.channel_description)
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(
                    "$CHANNEL_ID",
                    name, importance
                )
                channel.description = description
                notificationManager = this.getSystemService(NotificationManager::class.java)
                notificationManager?.createNotificationChannel(channel)
            }
        }


    }









