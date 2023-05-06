package com.pickyberry.rtuitlab_recruit.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.*
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.pickyberry.rtuitlab_recruit.presentation.coin_details.Entry
import com.pickyberry.rtuitlab_recruit.presentation.coin_details.myChart
import com.pickyberry.rtuitlab_recruit.presentation.coins_list.CoinsListScreen
import java.time.LocalDate


@Composable
fun CoinApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "list") {
        composable(route = "list") {
            CoinsListScreen(navController)
        }
        composable(route="details"){
            myChart(listOf(
                "2022-07-14" to 2f,
                "2022-07-15" to 4f,
                "2022-07-17" to 2f,
                "2022-08-01" to 8f,
            )
                .mapIndexed { index, (dateString, y) -> Entry(LocalDate.parse(dateString), index.toFloat(), y) }
                .let { ChartEntryModelProducer(it) })
        }
    }
}

public fun NavGraphBuilder.composable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit
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