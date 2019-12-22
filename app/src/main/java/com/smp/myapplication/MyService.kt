package com.smp.myapplication

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

const val TASK_CHANNEL_NAME: String = "task_channel"
const val TASK_CHANNEL_ID: Int = 341

class MyService : Service() {


    fun cancelNotification(context: Context, id: Int) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(id)
    }

    fun generateTaskNotification(context: Context): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createPlaybackChannel(context)
        }
        return loadDefaultTaskNotification(context)
    }

    fun loadDefaultTaskNotification(context: Context): Notification {
        return NotificationCompat.Builder(context, TASK_CHANNEL_NAME)
            .setContentTitle("")
            .setContentText("").build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createPlaybackChannel(context: Context) {
        val notificationManager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel =
            notificationManager.getNotificationChannel(TASK_CHANNEL_NAME)
        if (channel != null) return
        val name: CharSequence = "foo"
        val description =
            "bar"
        val importance = NotificationManager.IMPORTANCE_LOW
        val mChannel = NotificationChannel(
            TASK_CHANNEL_NAME,
            name,
            importance
        )
        mChannel.description = description
        mChannel.setShowBadge(false)
        mChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        mChannel.enableLights(false)
        mChannel.enableVibration(false)
        notificationManager.createNotificationChannel(mChannel)
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification: Notification = generateTaskNotification(this)
        startForeground(TASK_CHANNEL_ID, notification)
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
