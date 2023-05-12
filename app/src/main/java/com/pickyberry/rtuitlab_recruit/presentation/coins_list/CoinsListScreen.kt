package com.pickyberry.rtuitlab_recruit.presentation.coins_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun CoinsListScreen(
    navController: NavController,
    onNavigate: (String) -> Unit,
    viewModel: CoinsListViewModel = hiltViewModel(),
) {
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.state.isLoading
    )

    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.primary)
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
            if (viewModel.state.isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxSize())
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colors.onBackground),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.width(80.dp))
                            Text(
                                text = "Coin",
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                color = Color.White,
                                modifier = Modifier.weight(2f)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Price",
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                color = Color.White,
                                modifier = Modifier.weight(2f)
                            )
                            Text(
                                text = "24h",
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                maxLines = 2,
                                color = Color.White,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "24h market cap",
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                maxLines = 2,
                                color = Color.White,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                    }

                    items(viewModel.state.coins.size) { i ->
                        val coinItem = viewModel.state.coins[i]
                        CoinItemComposable(
                            coinItem = coinItem,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                   // navController.navigate("details/${coinItem.id}")
                                    onNavigate(coinItem.id)
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
}