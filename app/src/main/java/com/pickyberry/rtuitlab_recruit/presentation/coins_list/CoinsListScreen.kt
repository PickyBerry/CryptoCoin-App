package com.pickyberry.rtuitlab_recruit.presentation.coins_list

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.zxing.integration.android.IntentIntegrator
import com.pickyberry.rtuitlab_recruit.presentation.ScannerCaptureActivity


@Composable
fun CoinsListScreen(
    navController: NavController,
    onNavigate: (String) -> Unit,
    onQrCodeScanned: (String) -> Unit,
    viewModel: CoinsListViewModel = hiltViewModel(),
) {
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.state.isLoading
    )
    val context = LocalContext.current

    val qrScanLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val scannerResult = IntentIntegrator.parseActivityResult(result.resultCode, data)
            val qrData = scannerResult.contents
            onQrCodeScanned(qrData)
        }
    }


    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.primary)
    )
    {
        Row(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colors.primary)) {
            OutlinedTextField(
                value = viewModel.state.query,
                onValueChange = {
                    viewModel.search(it)
                },
                modifier = Modifier
                    .padding(16.dp),
                placeholder = {
                    Text(text = "Search...")
                },
                maxLines = 1,
                singleLine = true
            )
            IconButton(onClick = {
                qrScanLauncher.launch(
                    IntentIntegrator(context as Activity).setCaptureActivity(ScannerCaptureActivity::class.java)
                        .createScanIntent()
                )
            }, modifier = Modifier.weight(1f).size(48.dp)) {
                Icon(
                    Icons.Filled.QrCodeScanner,
                    "",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                viewModel.toggleFavorites(false)
                viewModel.refresh()
            }
        ) {
            if (viewModel.state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.fillMaxSize())
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {


                    item {
                        val selectedTabIndex = remember { mutableStateOf(if (viewModel.state.displayingFavorites) 1 else 0) }
                        TabRow(
                            selectedTabIndex = selectedTabIndex.value,
                            backgroundColor = MaterialTheme.colors.primary
                        ) {
                            Tab(
                                selected = selectedTabIndex.value == 0,
                                onClick = { viewModel.toggleFavorites(false)
                                    selectedTabIndex.value=0 },
                                text = { Text("All Coins") }
                            )
                            Tab(
                                selected = selectedTabIndex.value == 1,
                                onClick = { viewModel.toggleFavorites(true)
                                    selectedTabIndex.value=1},
                                text = { Text("Favorite Coins") }
                            )
                        }
                    }




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
                                    viewModel.toggleFavorites(false)
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
    if (viewModel.state.error.isNotEmpty())
        Toast.makeText(LocalContext.current, viewModel.state.error, Toast.LENGTH_SHORT).show()
}

