package com.pickyberry.rtuitlab_recruit.presentation.notifications

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.core.app.NotificationCompat
import com.pickyberry.rtuitlab_recruit.R
import com.pickyberry.rtuitlab_recruit.domain.CoinRepository
import com.pickyberry.rtuitlab_recruit.util.InternetValidation
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

    override fun onReceive(context: Context, intent: Intent) {

        //Check that we receive the right action
        if (intent.action == "NOTIFICATION_ACTION") {

            //Get the information
            val coinId = intent.getStringExtra("coinId")
            val sharedPreferences = context.getSharedPreferences("notification_prefs", Context.MODE_PRIVATE)
            val notificationEnabled = sharedPreferences.getBoolean("$coinId.enabled", false)
            val name = sharedPreferences.getString("$coinId.name", "Coin")


            if (notificationEnabled) {

                //If user doesn't have Internet connection - we'll send the notification as soon as it appears
                if (!InternetValidation.hasInternetConnection(context)) {
                    val networkCallback = object : ConnectivityManager.NetworkCallback() {
                        override fun onAvailable(network: Network) {
                            prepareAndSendNotification(
                                context,
                                coinId!!,
                                name!!,
                                sharedPreferences
                            )
                            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).unregisterNetworkCallback(
                                this
                            )
                        }
                    }
                    (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).registerNetworkCallback(
                        NetworkRequest.Builder().build(), networkCallback
                    )
                } else prepareAndSendNotification(
                    context,
                    coinId!!,
                    name!!,
                    sharedPreferences
                )


            }


        }
    }

    private fun prepareAndSendNotification(
        context: Context,
        coinId: String,
        name: String,
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
            val result =
                if (language == "ru") repository.getSimpleCoinPrice(coinId, "rub")
                else repository.getSimpleCoinPrice(coinId, "usd")


            val notification =
                if (language == "ru") NotificationCompat.Builder(context, "channelId")
                    .setContentTitle(name + if (result!!.rub24hChange!!.toDouble() > 0.0) " растёт!" else " падает!")
                    .setContentText(
                        "Цена: ₽" + result.rub + " ( " + (if (result.rub24hChange!!.toDouble() > 0.0) "+ ₽" else "- ₽") + String.format(
                            "%.1f", result.rub24hChange.absoluteValue
                        ) + " за сутки)"
                    )
                    .setSmallIcon(R.drawable.baseline_currency_bitcoin_24)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .build()
                else NotificationCompat.Builder(context, "channelId")
                    .setContentTitle(name + if (result!!.usd24hChange!!.toDouble() > 0.0) " is on the rise!" else " is falling!")
                    .setContentText(
                        "Price: $" + result.usd + " ( " + (if (result.usd24hChange!!.toDouble() > 0.0) "+ $" else "- $") + String.format(
                            "%.1f", result.usd24hChange.absoluteValue
                        ) + " since yesterday)"
                    )
                    .setSmallIcon(R.drawable.baseline_currency_bitcoin_24)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .build()

            notificationManager.notify(coinId.hashCode(), notification)

            sendNextNotification(context, sharedPreferences, coinId)
        }
    }

    private fun sendNextNotification(context: Context, sharedPreferences: SharedPreferences, coinId: String) {

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


        val intent = Intent(context, DailyNotificationReceiver::class.java)
        intent.action = "NOTIFICATION_ACTION"
        intent.putExtra("coinId", coinId)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            coinId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            notificationTime.timeInMillis,
            pendingIntent
        )
    }

}