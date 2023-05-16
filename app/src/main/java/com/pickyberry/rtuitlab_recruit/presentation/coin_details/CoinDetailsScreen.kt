package com.pickyberry.rtuitlab_recruit.presentation.coin_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
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


@Composable
fun CoinDetailsScreen(
    navController: NavController,
    viewModel: CoinDetailsViewModel = hiltViewModel(),
) {
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.state.isLoading
    )



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
                    IconButton(onClick = { viewModel.updateFavoriteStatus(viewModel.state.coinDetails?.id ?: "",true) }) { Icon(Icons.Filled.Star, "") }
                    IconButton(onClick = { }) { Icon(Icons.Filled.Notifications, "") }
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
                        modifier = Modifier.clip(CircleShape).size(width = 80.dp, height = 80.dp)
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
                            text = if (viewModel.state.coinDetails?.marketData?.currentPrice?.usd != null) if (viewModel.state.currency=="USD") "$"+ viewModel.state.coinDetails?.marketData?.currentPrice?.usd else "₽ "+ viewModel.state.coinDetails?.marketData?.currentPrice?.rub else "",
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
                        ?.isNotEmpty() == true || viewModel.state.coinDetails?.description?.get(1)
                        ?.isNotEmpty() == true
                ) DescriptionComposable(description.split('\n').first().replace(Regex("<.*?>"), ""))

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