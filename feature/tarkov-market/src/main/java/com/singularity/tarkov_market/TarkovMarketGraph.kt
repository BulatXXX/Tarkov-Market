package com.singularity.tarkov_market

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.singularity.tarkov_market.di.DaggerFleaMarketComponent
import com.singularity.tarkov_market.di.FleaMarketDepsProvider

@Serializable
sealed interface TarkovMarketGraph

@Serializable
data object SearchRoute : TarkovMarketGraph

@Serializable
data class ItemRoute(val id: String) : TarkovMarketGraph


fun NavGraphBuilder.tarkovNavGraph(navController: NavHostController) {


        composable<SearchRoute> {
            SearchScreen(
                onItemClick = { id ->
                    navController.navigate(ItemRoute(id))
                }
            )
        }

        composable<ItemRoute> { backStackEntry ->
            val route: ItemRoute = backStackEntry.toRoute()
            ItemScreen(route.id)
        }

}