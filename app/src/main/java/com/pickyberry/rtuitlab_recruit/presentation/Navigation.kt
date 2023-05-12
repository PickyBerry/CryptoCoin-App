package com.pickyberry.rtuitlab_recruit.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pickyberry.rtuitlab_recruit.presentation.coin_details.CoinDetailsScreen
import com.pickyberry.rtuitlab_recruit.presentation.coins_list.CoinsListScreen

const val DestinationListRoute = "list"
const val DestinationDetailsRoot = "details"
const val DestinationListArg = "id"
const val DestinationDetailsRoute = "$DestinationDetailsRoot/{$DestinationListArg}"

@Composable
fun CoinApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "list") {
        composable(route = DestinationListRoute) {
            CoinsListScreen(navController, onNavigate = { argument ->
                navController.navigate("$DestinationDetailsRoot/$argument")
            })
        }
        composable(route = DestinationDetailsRoute) {
            CoinDetailsScreen(navController)
        }
    }
}

public fun NavGraphBuilder.composable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit,
) {
    addDestination(
        ComposeNavigator.Destination(provider[ComposeNavigator::class], content).apply {
            this.route = route
            arguments.forEach { (argumentName, argument) ->
                addArgument(argumentName, argument)
            }
            deepLinks.forEach { deepLink ->
                addDeepLink(deepLink)
            }
        }
    )
}