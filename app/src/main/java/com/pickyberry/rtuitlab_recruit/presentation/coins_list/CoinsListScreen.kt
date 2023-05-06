package com.pickyberry.rtuitlab_recruit.presentation.coins_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun CoinsListScreen(
    navController: NavController,
    viewModel: CoinsListViewModel = hiltViewModel(),
) {
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.state.isLoading
    )

    Column(
        modifier = Modifier.fillMaxSize()
    )
    {
        OutlinedTextField(
            value = viewModel.state.query,
            onValueChange = {
                viewModel.search(it)
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            placeholder = {
                Text(text = "Search...")
            },
            maxLines = 1,
            singleLine = true
        )
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                viewModel.refresh()
            }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(viewModel.state.coins.size) { i ->
                    val coinItem = viewModel.state.coins[i]
                    CoinItemComposable(
                        coinItem = coinItem,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("details")
                            }
                            .padding(16.dp)
                    )
                    if (i < viewModel.state.coins.size) {
                        Divider(
                            modifier = Modifier.padding(
                                horizontal = 16.dp
                            )
                        )
                    }
                }
            }
        }
    }
}