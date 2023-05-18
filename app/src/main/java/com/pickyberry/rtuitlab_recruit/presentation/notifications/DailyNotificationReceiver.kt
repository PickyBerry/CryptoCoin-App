package com.pickyberry.rtuitlab_recruit.presentation.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.pickyberry.rtuitlab_recruit.R
import com.pickyberry.rtuitlab_recruit.domain.CoinRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class DailyNotificationReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repository: CoinRepository

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action == "NOTIFICATION_ACTION") {

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            CoroutineScope(Dispatchers.Main).launch {

                val language: String = Locale.getDefault().language
                val result = if (language == "ru") repository.getSimpleCoinPrice("bitcoin", "rub")
                else repository.getSimpleCoinPrice("bitcoin", "usd")
                Log.e("check", "wow4")
                Log.e("check", result.toString())

                val channel = NotificationChannel(
                    "channelId",
                    "Channel Name",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationManager.createNotificationChannel(channel)

                val notification = NotificationCompat.Builder(context, "channelId")
                    .setContentTitle("Daily Notification")
                    .setContentText(result.toString())
                    .setSmallIcon(R.drawable.baseline_currency_bitcoin_24)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .build()

                notificationManager.notify(0, notification)
            }
        }


    }


}