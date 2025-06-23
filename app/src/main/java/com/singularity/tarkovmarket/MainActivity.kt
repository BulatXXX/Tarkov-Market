package com.singularity.tarkovmarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.singularity.tarkovapi.ui.SearchRoute
import com.singularity.tarkovapi.ui.tarkovGraph
import com.singularity.tarkovmarket.ui.theme.TarkovMarketTheme
import com.singularity.tarkovmarket.ui.theme.ThemeController
import com.singularity.tarkovmarket.ui.theme.ThemeOption

val themeOptions = arrayOf(ThemeOption.RED, ThemeOption.BLUE, ThemeOption.DARK, ThemeOption.LIGHT)

class MainActivity : ComponentActivity() {
    private val themeController by lazy { ThemeController(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        themeController.setTheme(ThemeOption.RED)
        setContent {
            val current = remember { mutableIntStateOf(0) }
            val navController = rememberNavController()
            TarkovMarketTheme(themeController) {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground
                ) {
                    Column {
                        Spacer(modifier = Modifier.padding(100.dp))
                        Text("Main")
                        Spacer(modifier = Modifier.padding(100.dp))
                        NavHost(
                            navController = navController,
                            startDestination = SearchRoute
                        ) {
                            tarkovGraph(navController)

                        }
                    }
                }
                Text(
                    "Upper", modifier = Modifier
                        .padding(vertical = 100.dp)
                        .clickable {
                            current.intValue++
                            themeController.setTheme(
                                themeOptions[current.intValue % themeOptions.size]
                            )
                        })

            }
        }

    }


    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    TarkovMarketTheme {
//        Greeting("Android")
//    }
//}