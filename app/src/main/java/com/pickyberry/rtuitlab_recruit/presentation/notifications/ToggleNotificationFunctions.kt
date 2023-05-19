package com.pickyberry.rtuitlab_recruit.presentation.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*

fun scheduleNotificationForCoin(context: Context, coinId: String) {

    val sharedPreferences = context.getSharedPreferences("notification_prefs", Context.MODE_PRIVATE)
    val notificationEnabled = sharedPreferences.getBoolean("$coinId.enabled", false)

    if (notificationEnabled) {

        //Set time
        val notificationHour = sharedPreferences.getInt("$coinId.hour", 12)
        val notificationMinute = sharedPreferences.getInt("$coinId.minute", 0)
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, notificationHour)
        calendar.set(Calendar.MINUTE, notificationMinute)
        calendar.set(Calendar.SECOND, 0)


        //Create intent
        val intent = Intent(context, DailyNotificationReceiver::class.java)
        intent.action = "NOTIFICATION_ACTION"
        intent.putExtra("coinId", coinId)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            coinId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )


        //Alarm manager for exact time
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }
}

fun cancelNotificationForCoin(context: Context, coinId: String) {
    val intent = Intent(context, DailyNotificationReceiver::class.java)
    intent.action = "NOTIFICATION_ACTION"
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        coinId.hashCode(),
        intent,
        PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
    )
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    pendingIntent?.let {
        alarmManager.cancel(it)
        it.cancel()
    }
}