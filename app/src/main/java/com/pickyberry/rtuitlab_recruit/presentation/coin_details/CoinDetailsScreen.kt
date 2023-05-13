package com.pickyberry.rtuitlab_recruit.presentation.coin_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
                            text = (viewModel.state.coinDetails?.name
                                ?: "") + ("  (" + viewModel.state.coinDetails?.symbol?.uppercase() + ")"),
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = MaterialTheme.colors.onPrimary,
                        )
                        Text(
                            text = "$" + viewModel.state.coinDetails?.marketData?.currentPrice?.usd,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MaterialTheme.colors.onBackground,
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Button(
                        onClick = { /* handle button click */ },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onBackground),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        modifier = Modifier.size(width = 80.dp, height = 40.dp)
                    ) {
                        Text(text = "USD", color = Color.White)
                    }
                }
                Spacer(modifier = Modifier.height(60.dp))
                if (viewModel.state.historicalData.isNotEmpty()) {
                    HistoricalDataChart(
                        data = viewModel.state.historicalData.map { it[0] to it[1] }.toList(),
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
                descriptions?.get(0)!!

                if (viewModel.state.coinDetails?.description?.get(0)
                        ?.isNotEmpty() == true || viewModel.state.coinDetails?.description?.get(1)
                        ?.isNotEmpty() == true
                ) DescriptionComposable(description.split('\n').first().replace(Regex("<.*?>"), ""))

                Spacer(modifier = Modifier.height(10.dp))
                MarketDataComposable(viewModel.state.coinDetails?.marketData!!, "usd")
                Spacer(modifier = Modifier.height(10.dp))
                if (viewModel.state.coinDetails?.links?.isNotEmpty() == true) LinksComposable(viewModel.state.coinDetails?.links!!)
                Spacer(modifier = Modifier.height(10.dp))

                if (viewModel.state.coinDetails?.hashingAlgorithm !=null) {
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
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