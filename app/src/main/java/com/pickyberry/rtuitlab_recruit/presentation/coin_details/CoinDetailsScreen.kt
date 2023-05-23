package com.pickyberry.rtuitlab_recruit.presentation.coin_details

import android.app.TimePickerDialog
import android.content.Context
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
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.pickyberry.rtuitlab_recruit.R
import com.pickyberry.rtuitlab_recruit.presentation.notifications.cancelNotificationForCoin
import com.pickyberry.rtuitlab_recruit.presentation.notifications.scheduleNotificationForCoin
import java.util.*


@Composable
fun CoinDetailsScreen(
    navController: NavController,
    viewModel: CoinDetailsViewModel = hiltViewModel(),
) {

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = viewModel.state.isLoading)
    val context = LocalContext.current
    val timePickerState = remember { mutableStateOf(Calendar.getInstance()) }
    val sharedPreferences = context.getSharedPreferences("notification_prefs", Context.MODE_PRIVATE)

    RequestNotificationsPermission {
        viewModel.state = viewModel.state.copy(permissionGranted = true)
    }

    val notificationEnabled = remember {
        mutableStateOf(
            sharedPreferences.getBoolean("${viewModel.state.coinDetails?.id}.enabled", false)
        )
    }

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { viewModel.refresh() }
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background)) {

            //Top Bar
            item {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.coin_details)) },

                    //Go back button
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Filled.ArrowBack, "")
                        }
                    },

                    actions = {
                        if (viewModel.state.coinDetails != null) {

                            notificationEnabled.value = sharedPreferences.getBoolean("${viewModel.state.coinDetails?.id}.enabled", false)

                            //Switch between USD and RUB
                            TextButton(
                                onClick = { viewModel.updateCurrency() },
                                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background)
                            ) { Text(text = viewModel.state.currency, fontSize = 16.sp) }

                            //Toggle favorite
                            IconButton(onClick = {
                                viewModel.updateFavoriteStatus(viewModel.state.coinDetails?.id ?: "")
                            }) {
                                Icon(
                                    if (viewModel.state.isFavorite) Icons.Filled.Star else Icons.Filled.StarBorder, "")
                            }

                            //Toggle notification
                            IconButton(onClick = {
                                if (notificationEnabled.value) {
                                    sharedPreferences.edit()
                                        .putBoolean(
                                            "${viewModel.state.coinDetails?.id}.enabled",
                                            false
                                        )
                                        .apply()
                                    notificationEnabled.value = false
                                    cancelNotificationForCoin(
                                        context,
                                        viewModel.state.coinDetails!!.id
                                    )
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

                                    } else Toast.makeText(context, context.getString(R.string.no_permission), Toast.LENGTH_SHORT).show()
                                }
                            }) {
                                Icon(
                                    if (notificationEnabled.value) Icons.Filled.Notifications else Icons.Filled.NotificationsNone,
                                    ""
                                )
                            }
                        }
                    },
                    backgroundColor = MaterialTheme.colors.background,
                    contentColor = MaterialTheme.colors.onBackground
                )

            }

            //Main content
            item {

                if (viewModel.state.isLoading)
                    LinearProgressIndicator(modifier = Modifier.fillMaxSize())
                else {
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
                                color = MaterialTheme.colors.onBackground,
                            )

                            Text(
                                text = if (viewModel.state.coinDetails?.marketData?.currentPrice?.usd != null) if (viewModel.state.currency == "USD") "$" + viewModel.state.coinDetails?.marketData?.currentPrice?.usd else "₽ " + viewModel.state.coinDetails?.marketData?.currentPrice?.rub else "",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = MaterialTheme.colors.primary,
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
                        , context = context)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    //Getting description data
                    val descriptions = viewModel.state.coinDetails?.description
                    val description = if ( Locale.getDefault().language =="ru" && descriptions?.get(1)!!.isNotEmpty()) descriptions.get(1)
                        else descriptions?.get(0) ?: ""

                    if (viewModel.state.coinDetails?.description?.get(0)?.isNotEmpty() == true ||
                        viewModel.state.coinDetails?.description?.get(1)?.isNotEmpty() == true
                    )
                        DescriptionComposable(
                            description.split('\n').first().replace(Regex("<.*?>"), "")
                        )
                    Spacer(modifier = Modifier.height(10.dp))

                    //Getting market data
                    if (viewModel.state.coinDetails?.marketData != null)
                        MarketDataComposable(
                            viewModel.state.coinDetails?.marketData!!,
                            viewModel.state.currency
                        )
                    Spacer(modifier = Modifier.height(10.dp))

                    //Getting links data
                    if (viewModel.state.coinDetails?.links?.isNotEmpty() == true)
                        LinksComposable(viewModel.state.coinDetails?.links!!, context)
                    Spacer(modifier = Modifier.height(10.dp))

                    //Getting hashing algorighm data
                    if (viewModel.state.coinDetails?.hashingAlgorithm != null && viewModel.state.coinDetails!!.hashingAlgorithm!!.isNotEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                stringResource(R.string.hashing_algorithm) +" ${viewModel.state.coinDetails?.hashingAlgorithm}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = MaterialTheme.colors.primary
                            )
                        }
                    }

                }
            }
        }
    }
    if (viewModel.state.error.isNotEmpty())
        Toast.makeText(LocalContext.current, viewModel.state.error, Toast.LENGTH_SHORT).show()
}



