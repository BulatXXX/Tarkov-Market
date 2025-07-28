package com.singularity.tarkov_market

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable
import androidx.navigation.compose.composable
import androidx.navigation.toRoute

@Serializable
sealed interface TarkovMarketGraph

@Serializable
data object SearchRoute : TarkovMarketGraph

@Serializable
data class ItemRoute(val id: String) : TarkovMarketGraph

@Serializable
data object FavouriteRoute : TarkovMarketGraph


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
        ItemDetailsScreen(route.id)
    }

    composable<FavouriteRoute> {
        FavouritesScreen { id ->
            navController.navigate(ItemRoute(id))
        }
    }

}