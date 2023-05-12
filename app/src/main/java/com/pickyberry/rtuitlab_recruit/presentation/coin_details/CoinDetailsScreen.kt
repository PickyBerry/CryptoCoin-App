package com.pickyberry.rtuitlab_recruit.presentation.coin_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.AlignmentLine
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
    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.primary)) {
        if (viewModel.state.isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxSize())
        } else {
            Spacer(modifier = Modifier.height(50.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
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
                        fontSize = 32.sp,
                        color = MaterialTheme.colors.onPrimary,
                    )
                    Text(
                        text = "$" + viewModel.state.coinDetails?.marketData?.currentPrice?.usd,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = MaterialTheme.colors.onBackground,
                    )
                }
            }
            Spacer(modifier = Modifier.height(60.dp))
            if (viewModel.state.historicalData.isNotEmpty()) {
                HistoricalDataChart(
                    data = viewModel.state.historicalData.map { it[0] to it[1] }.toList(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(end=20.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }

        }
    }

}