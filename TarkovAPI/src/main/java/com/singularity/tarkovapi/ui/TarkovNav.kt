package com.singularity.tarkovapi.ui

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable


sealed interface TarkovRoute

@Serializable
data object SearchRoute : TarkovRoute

@Serializable
data class ItemRoute(val name: String) : TarkovRoute

fun NavGraphBuilder.tarkovGraph(navController: NavController) {
    composable<SearchRoute> {
        SearchScreen { clicked ->
            navController.navigate(ItemRoute(clicked))
        }
    }
    composable<ItemRoute> {
        val args = it.toRoute<ItemRoute>()
        ItemScreen(args.name)
    }

}