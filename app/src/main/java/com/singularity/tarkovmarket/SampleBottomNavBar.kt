// SampleBottomNavBar.kt
package com.singularity.tarkovmarket

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.singularity.profile.ProfileRoute
import com.singularity.tarkov_market.FavouriteRoute
import com.singularity.tarkov_market.SearchRoute

@Composable
fun SampleBottomNavBar(navController: NavHostController) {


    val backStack by navController.currentBackStackEntryAsState()


    NavigationBar {
        NavigationBarItem(
            selected  = true,
            onClick = {
                navController.navigate(SearchRoute) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState   = true
                }
            },
            icon  = { Icon(Icons.Filled.Search, contentDescription = null) },
            label = { Text("Search") }
        )

        NavigationBarItem(
            selected  = true,
            onClick = {
                navController.navigate(ProfileRoute) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState   = true
                }
            },
            icon  = { Icon(Icons.Filled.Person, contentDescription = null) },
            label = { Text("Profile") }
        )

        NavigationBarItem(
            selected = true,
            onClick = {
                navController.navigate(FavouriteRoute) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState   = true
                }
            },
            icon  = { Icon(Icons.Filled.Favorite, contentDescription = null) },
            label = { Text("Favourite") }
        )

        NavigationBarItem(
            selected = false, // если у вас есть ещё третий таб, добавьте его аналогично
            onClick = { /* … */ },
            icon  = { Icon(Icons.Filled.Settings, contentDescription = null) },
            label = { Text("Settings") }
        )
    }
}