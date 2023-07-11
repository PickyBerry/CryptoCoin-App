package com.pickyberry.rtuitlab_recruit.presentation.notifications

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.core.app.NotificationCompat
import com.pickyberry.rtuitlab_recruit.MainActivity
import com.pickyberry.rtuitlab_recruit.R
import com.pickyberry.rtuitlab_recruit.domain.CoinRepository
import com.pickyberry.rtuitlab_recruit.domain.NetworkChecker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.math.absoluteValue

@AndroidEntryPoint
class DailyNotificationReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repository: CoinRepository

    @Inject
    lateinit var networkChecker: NetworkChecker

    override fun onReceive(context: Context, intent: Intent) {

        //Check that we receive the right action
        if (intent.action == "NOTIFICATION_ACTION") {

            //Get the information
            val coinId = intent.getStringExtra("coinId")
            val sharedPreferences =
                context.getSharedPreferences("notification_prefs", Context.MODE_PRIVATE)
            val notificationEnabled = sharedPreferences.getBoolean("$coinId.enabled", false)

            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (notificationEnabled) {

                //If user doesn't have Internet connection - we'll send the notification as soon as it appears
                if (networkChecker.isNetworkAvailable())
                    prepareAndSendNotification(context, coinId!!, sharedPreferences)
                else {
                    val networkCallback = object : ConnectivityManager.NetworkCallback() {
                        override fun onAvailable(network: Network) {
                            prepareAndSendNotification(context, coinId!!, sharedPreferences)
                            connectivityManager.unregisterNetworkCallback(this)
                        }
                    }
                    connectivityManager.registerNetworkCallback(
                        NetworkRequest.Builder().build(), networkCallback
                    )
                }


            }


        }
    }

    private fun prepareAndSendNotification(
        context: Context,
        coinId: String,
        sharedPreferences: SharedPreferences,
    ) {


        //Create the channel for notification
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        val channel = NotificationChannel(
            "channelId",
            "Channel Name",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)


        CoroutineScope(Dispatchers.Main).launch {

            val language: String = Locale.getDefault().language
            val name = sharedPreferences.getString("$coinId.name", "Coin")

            val coinInfo =
                if (language == "ru") repository.getSimpleCoinPrice(coinId, "rub")
                else repository.getSimpleCoinPrice(coinId, "usd")


            //Notification title and content
            val title =
                if (language == "ru") (name + if (coinInfo!!.rub24hChange!!.toDouble() > 0.0) " растёт!" else " падает!")
                else (name + if (coinInfo!!.usd24hChange!!.toDouble() > 0.0) " is on the rise!" else " is falling!")
            val content =
                if (language == "ru") ("Цена: ₽" + coinInfo.rub + " ( " + (if (coinInfo.rub24hChange!!.toDouble() > 0.0) "+ ₽" else "- ₽") + String.format(
                    "%.1f",
                    coinInfo.rub24hChange.absoluteValue
                ) + " за сутки)") else ("Price: $" + coinInfo.usd + " ( " + (if (coinInfo.usd24hChange!!.toDouble() > 0.0) "+ $" else "- $") + String.format(
                    "%.1f",
                    coinInfo.usd24hChange.absoluteValue
                ) + " since yesterday)")


            //Intent to open app on click
            val openAppIntent = Intent(context, MainActivity::class.java)
            openAppIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val contentIntent = PendingIntent.getActivity(context, 0, openAppIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

            //Build and send notification
            val notification =
                NotificationCompat.Builder(context, "channelId")
                    .setContentTitle(title)
                    .setContentText(content)
                    .setContentIntent(contentIntent)
                    .setSmallIcon(R.drawable.baseline_currency_bitcoin_24)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .build()

            notificationManager.notify(coinId.hashCode(), notification)

            sendNextNotification(context, sharedPreferences, coinId)
        }
    }

    private fun sendNextNotification(
        context: Context,
        sharedPreferences: SharedPreferences,
        coinId: String
    ) {

        //Get the time for notification
        val notificationHour = sharedPreferences.getInt("$coinId.hour", 12)
        val notificationMinute = sharedPreferences.getInt("$coinId.minute", 0)
        val now = Calendar.getInstance()
        val notificationTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, notificationHour)
            set(Calendar.MINUTE, notificationMinute)
            set(Calendar.SECOND, 0)
            if (before(now)) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }


        //Intent and alarm manager for next notification
        val intent = Intent(context, DailyNotificationReceiver::class.java)
        intent.action = "NOTIFICATION_ACTION"
        intent.putExtra("coinId", coinId)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            coinId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            notificationTime.timeInMillis,
            pendingIntent
        )
    }

}