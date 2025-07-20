package com.singularity.tarkovmarket

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.singularity.profile.profileNavGraph
import com.singularity.tarkov_market.SearchRoute
import com.singularity.tarkov_market.tarkovNavGraph
import com.singularity.tarkovmarket.ui.theme.TarkovMarketTheme
import com.singularity.tarkovmarket.ui.theme.ThemeController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private val themeController by lazy { ThemeController(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            TarkovMarketTheme(themeController) {
                Scaffold(
                    topBar = { },
                    bottomBar = { SampleBottomNavBar(navController) },
                    floatingActionButton = {

                    }
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = SearchRoute,
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        tarkovNavGraph(navController)
                        profileNavGraph(navController)
                    }
                }

            }
        }
    }
}





