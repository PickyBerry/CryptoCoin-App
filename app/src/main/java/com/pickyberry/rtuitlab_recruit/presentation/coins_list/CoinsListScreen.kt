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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.zxing.integration.android.IntentIntegrator
import com.pickyberry.rtuitlab_recruit.R
import com.pickyberry.rtuitlab_recruit.presentation.ScannerCaptureActivity


@Composable
fun CoinsListScreen(
    onNavigate: (String) -> Unit,
    onQrCodeScanned: (String) -> Unit,
    viewModel: CoinsListViewModel = hiltViewModel(),
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = viewModel.state.isLoading)
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
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background)
    )
    {
        Row(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colors.background), verticalAlignment = Alignment.CenterVertically) {

            //Search query input
            OutlinedTextField(
                value = viewModel.state.query,
                onValueChange = {
                    viewModel.search(it)
                },
                placeholder = {
                    Text(text = stringResource(R.string.search))
                },
                modifier = Modifier.padding(16.dp).weight(1f),
                maxLines = 1,
                singleLine = true
            )

            //QR-Scanner launcher
            IconButton(onClick = {
                qrScanLauncher.launch(
                    IntentIntegrator(context as Activity).setCaptureActivity(ScannerCaptureActivity::class.java)
                        .createScanIntent()
                )
            }, modifier = Modifier.padding(end=5.dp, top = 16.dp,bottom=16.dp)) {
                Icon(
                    imageVector = Icons.Filled.QrCodeScanner,
                    contentDescription = "",
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.size(48.dp)
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


                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {

                    //Toggle between all and favorite coins
                    item {
                        val selectedTabIndex = remember { mutableStateOf(if (viewModel.state.displayingFavorites) 1 else 0) }
                        TabRow(
                            selectedTabIndex = selectedTabIndex.value,
                            backgroundColor = MaterialTheme.colors.background
                        ) {
                            Tab(
                                selected = selectedTabIndex.value == 0,
                                onClick = { viewModel.toggleFavorites(false)
                                    selectedTabIndex.value=0 },
                                text = { Text(stringResource(R.string.all_coins)) }
                            )
                            Tab(
                                selected = selectedTabIndex.value == 1,
                                onClick = { viewModel.toggleFavorites(true)
                                    selectedTabIndex.value=1},
                                text = { Text(stringResource(R.string.favorite_coins)) }
                            )
                        }
                    }



                    //Header for list
                    item {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colors.primary),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.width(80.dp))
                            Text(
                                text = stringResource(R.string.coin),
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                color = Color.White,
                                modifier = Modifier.weight(2f)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = stringResource(R.string.price),
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                color = Color.White,
                                modifier = Modifier.weight(2f)
                            )
                            Text(
                                text = stringResource(R.string.hour_24),
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                maxLines = 2,
                                color = Color.White,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = stringResource(R.string.hour_24_market_cap),
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                maxLines = 2,
                                color = Color.White,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                    }

                    if (!viewModel.state.isLoading) {
                    //Main Coin list
                    items(viewModel.state.coins.size) { i ->
                        val coinItem = viewModel.state.coins[i]
                        CoinItemComposable(
                            coinItem = coinItem,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onNavigate(coinItem.id)
                                    viewModel.toggleFavorites(false)
                                }
                                .padding(16.dp),
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

