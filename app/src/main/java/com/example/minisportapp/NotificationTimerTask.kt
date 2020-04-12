package com.example.minisportapp

import android.app.*
import android.content.Context
import com.example.minisportapp.repository.SportData
import java.util.*

class NotificationTimerTask(wrapper: NotificationWrapper, sportData: SportData, context: Context) : TimerTask() {
    private val sData = sportData
    private val ctx = context
    private val nWrapper = wrapper

    override fun run() {
        if (!appInForeground(ctx)) {
            createRandomStoryNotification(nWrapper, sData, ctx)
        }
    }

    private fun createRandomStoryNotification(wrapper: NotificationWrapper, sportData: SportData, context: Context) {
        val random = (0..(sportData.data.items.size-1)).random()

        val pendingIntent = wrapper.createPendingIntent(context,
            sportData.data.items[random])

        val notification = wrapper.createNotification(
            context,
            "minisport.channel.id",
            sportData.data.items[random].sectionLabel,
            sportData.data.items[random].title,
            sportData.data.items[random].title,
            pendingIntent)

        notification.flags = Notification.FLAG_AUTO_CANCEL
        wrapper.showNotification(context, 28748, notification)
    }

    private fun appInForeground(context: Context): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningAppProcesses = activityManager.runningAppProcesses ?: return false
        return runningAppProcesses.any { it.processName == context.packageName && it.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND }
    }
}