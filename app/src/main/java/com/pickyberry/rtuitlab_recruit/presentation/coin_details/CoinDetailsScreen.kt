package com.pickyberry.rtuitlab_recruit.presentation.coin_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import java.time.LocalDate

@Composable
fun CoinDetailsScreen(
    navController: NavController,
) {
    //   val swipeRefreshState = rememberSwipeRefreshState(
    //  isRefreshing = viewModel.state.isLoading
    //  )
    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.primary)) {
        HistoricalDataChart(listOf(
            "2022-07-14" to 2f,
            "2022-07-15" to 4f,
            "2022-07-17" to 2f,
            "2022-08-01" to 8f,
        )
            .mapIndexed { index, (dateString, y) ->
                Entry(
                    LocalDate.parse(dateString),
                    index.toFloat(),
                    y
                )
            }
            .let { ChartEntryModelProducer(it) })
    }

}