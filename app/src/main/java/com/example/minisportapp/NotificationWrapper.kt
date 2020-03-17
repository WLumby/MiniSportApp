package com.example.minisportapp

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.gson.Gson

class NotificationWrapper {
    fun createNotificationChannel(context: Context, channelID: String) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "minisport.channel"
            val descriptionText = "Minisport channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun createNotification(context: Context, channelID: String, title: String, text: String, bigText: String, intent: PendingIntent): Notification {
        return NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_duration)
            .setContentTitle(title)
            .setContentText(text)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(bigText))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(intent)
            .build()
    }

    fun showNotification(context: Context, notificationID: Int, notification: Notification) {
        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationID, notification)
        }
    }

    fun createPendingIntent(context: Context, item: Item): PendingIntent {
        val gson = Gson()
        val serialisedItem = gson.toJson(item)

        val intent = Intent(context, DisplayStoryActivity::class.java).apply {
            putExtra("com.minisportapp.storydata", serialisedItem)
        }

        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }
}