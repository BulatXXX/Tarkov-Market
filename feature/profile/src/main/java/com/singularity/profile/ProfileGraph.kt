package com.singularity.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
sealed interface ProfileRoutes

@Serializable
data object ProfileRoute : ProfileRoutes

@Serializable
data object ProfileSettingsRoute : ProfileRoutes

fun NavGraphBuilder.profileNavGraph(navController: NavController) {
    composable<ProfileRoute> {
        ProfileScreen(){
            navController.navigate(ProfileSettingsRoute)
        }
    }
    composable<ProfileSettingsRoute> {
        ProfileSettingsScreen()
    }
}