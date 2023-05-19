package com.pickyberry.rtuitlab_recruit.presentation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pickyberry.rtuitlab_recruit.presentation.coin_details.CoinDetailsScreen
import com.pickyberry.rtuitlab_recruit.presentation.coins_list.CoinsListScreen

const val DestinationListRoute = "list"
const val DestinationDetailsRoot = "details"
const val DestinationArg = "id"
const val DestinationDetailsRoute = "$DestinationDetailsRoot/{$DestinationArg}"

@Composable
fun CoinApp() {

    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController, startDestination = "list") {

        composable(route = DestinationListRoute) {
            CoinsListScreen(

                //Route depends on the coin ID argument
                onNavigate = { argument -> navController.navigate("$DestinationDetailsRoot/$argument") },

                //If QR is correct, navigate to the specific coin details screen
                onQrCodeScanned = { qr ->
                    if (qr.startsWith("https://rtuitlab.dev/crypto/"))
                        navController.navigate("$DestinationDetailsRoot/${qr.split('-').last()}")
                    else Toast.makeText(context, "Wrong QR", Toast.LENGTH_SHORT).show()
                })
        }

        composable(route = DestinationDetailsRoute) {
            CoinDetailsScreen(navController)
        }
    }


}
