package com.pickyberry.rtuitlab_recruit.presentation.coin_details

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.pickyberry.rtuitlab_recruit.R
import com.pickyberry.rtuitlab_recruit.presentation.notifications.DailyNotificationReceiver
import java.util.*


@Composable
fun CoinDetailsScreen(
    navController: NavController,
    viewModel: CoinDetailsViewModel = hiltViewModel(),
) {
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.state.isLoading
    )
    val context = LocalContext.current
    val timePickerState = remember { mutableStateOf(Calendar.getInstance()) }
    RequestNotificationsPermission {
        viewModel.state = viewModel.state.copy(permissionGranted = true)
    }

    val sharedPreferences = context.getSharedPreferences("notification_prefs", Context.MODE_PRIVATE)



    if (viewModel.state.isLoading) {
        LinearProgressIndicator(modifier = Modifier.fillMaxSize())
    } else {

        val notificationEnabled = remember {

            mutableStateOf(
                sharedPreferences.getBoolean(
                    "${viewModel.state.coinDetails?.id}.enabled",
                    false
                )
            )
        }


        LazyColumn(modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.primary)) {


            item {
                TopAppBar(
                    title = { Text(text = "Coin Details") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                Icons.Filled.ArrowBack,
                                ""
                            )
                        }
                    },
                    actions = {
                        TextButton(
                            onClick = { viewModel.updateCurrency() },
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                        ) { Text(text = viewModel.state.currency, fontSize = 16.sp) }
                        IconButton(onClick = {
                            viewModel.updateFavoriteStatus(
                                viewModel.state.coinDetails?.id ?: ""
                            )
                        }) {
                            Icon(
                                if (viewModel.state.isFavorite) Icons.Filled.Star else Icons.Filled.StarBorder,
                                ""
                            )
                        }

                        IconButton(onClick = {
                            if (notificationEnabled.value) {
                                sharedPreferences.edit()
                                    .putBoolean(
                                        "${viewModel.state.coinDetails?.id}.enabled",
                                        false
                                    )
                                    .apply()
                                notificationEnabled.value = false
                                cancelNotificationForCoin(context, viewModel.state.coinDetails!!.id)
                            } else {
                                if (viewModel.state.permissionGranted) {

                                    val timeSetListener =
                                        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->

                                            sharedPreferences.edit().putBoolean(
                                                "${viewModel.state.coinDetails?.id}.enabled",
                                                true
                                            ).apply()

                                            sharedPreferences.edit().putInt(
                                                "${viewModel.state.coinDetails?.id}.hour",
                                                hourOfDay
                                            ).apply()
                                            sharedPreferences.edit().putInt(
                                                "${viewModel.state.coinDetails?.id}.minute",
                                                minute
                                            ).apply()
                                            sharedPreferences.edit().putString(
                                                "${viewModel.state.coinDetails?.id}.name",
                                                viewModel.state.coinDetails?.name
                                            ).apply()
                                            notificationEnabled.value = true

                                            scheduleNotificationForCoin(
                                                context,
                                                viewModel.state.coinDetails!!.id
                                            )

                                        }


                                    TimePickerDialog(
                                        context,
                                        timeSetListener,
                                        timePickerState.value.get(Calendar.HOUR_OF_DAY),
                                        timePickerState.value.get(Calendar.MINUTE),
                                        true
                                    ).show()

                                } else Toast.makeText(context, "No permission!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }) {
                            Icon(
                                if (notificationEnabled.value) Icons.Filled.Notifications else Icons.Filled.NotificationsNone,
                                ""
                            )
                        }
                    },
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onPrimary
                )

            }
            item {
                if (viewModel.state.isLoading) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxSize())
                } else {
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp)
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(viewModel.state.coinDetails?.image)
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(R.drawable.placeholder),
                            contentDescription = stringResource(R.string.description),
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.clip(CircleShape)
                                .size(width = 80.dp, height = 80.dp)
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Column {
                            Text(
                                text = if (viewModel.state.coinDetails?.name != null && viewModel.state.coinDetails?.symbol != null) (viewModel.state.coinDetails?.name
                                    ?: "") + ("  (" + viewModel.state.coinDetails?.symbol?.uppercase() + ")") else "",
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp,
                                color = MaterialTheme.colors.onPrimary,
                            )
                            Text(
                                text = if (viewModel.state.coinDetails?.marketData?.currentPrice?.usd != null) if (viewModel.state.currency == "USD") "$" + viewModel.state.coinDetails?.marketData?.currentPrice?.usd else "₽ " + viewModel.state.coinDetails?.marketData?.currentPrice?.rub else "",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = MaterialTheme.colors.onBackground,
                            )
                        }
                        Spacer(modifier = Modifier.width(20.dp))
                    }
                    Spacer(modifier = Modifier.height(60.dp))
                    if (viewModel.state.historicalData.isNotEmpty()) {
                        HistoricalDataChart(
                            data = viewModel.state.historicalData,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(end = 20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    val descriptions = viewModel.state.coinDetails?.description
                    val description = if (descriptions?.get(0)?.isNotEmpty() == true)
                        descriptions[0] else if (descriptions?.get(1)
                            ?.isNotEmpty() == true
                    ) descriptions[1] else ""
                    if (descriptions != null) descriptions?.get(0)

                    if (viewModel.state.coinDetails?.description?.get(0)
                            ?.isNotEmpty() == true || viewModel.state.coinDetails?.description?.get(
                            1
                        )
                            ?.isNotEmpty() == true
                    ) DescriptionComposable(
                        description.split('\n').first().replace(Regex("<.*?>"), "")
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    if (viewModel.state.coinDetails?.marketData != null) MarketDataComposable(
                        viewModel.state.coinDetails?.marketData!!,
                        viewModel.state.currency
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    if (viewModel.state.coinDetails?.links?.isNotEmpty() == true) LinksComposable(
                        viewModel.state.coinDetails?.links!!
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    if (viewModel.state.coinDetails?.hashingAlgorithm != null) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "HASHING ALGORITHM ${viewModel.state.coinDetails?.hashingAlgorithm}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = MaterialTheme.colors.onBackground
                            )
                        }
                    }


                }
            }
        }
    }
}


fun scheduleNotificationForCoin(context: Context, coinId: String) {
    val sharedPreferences = context.getSharedPreferences("notification_prefs", Context.MODE_PRIVATE)
    val notificationEnabled = sharedPreferences.getBoolean("$coinId.enabled", false)
    if (notificationEnabled) {
        val notificationHour = sharedPreferences.getInt("$coinId.hour", 12)
        val notificationMinute = sharedPreferences.getInt("$coinId.minute", 0)
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, notificationHour)
        calendar.set(Calendar.MINUTE, notificationMinute)
        calendar.set(Calendar.SECOND, 0)
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
