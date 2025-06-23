package com.singularity.tarkovmarket.ui.theme

import android.app.Activity
import android.content.Context
import android.content.res.Resources.Theme
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

private val DarkColorScheme = darkColorScheme(
    primary = Color.Black,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    onSurface = Color.White,
    background = Color.Gray
)

private val LightColorScheme = lightColorScheme(
    primary = Color.Green,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    onSurface = Color.Black,
    background = Color.White
    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onSurface = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)
private val RedColorScheme = darkColorScheme(
    primary = Color.Red,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    onSurface = Color.Red,
    background = Color.Yellow
)

private val BlueColorScheme = darkColorScheme(
    primary = Color.Blue,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    onSurface = Color.Blue,
    background = Color.Green
)

@Composable
fun TarkovMarketTheme(
    controller: ThemeController,
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
//    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//
//        darkTheme -> DarkColorScheme
//        else -> LightColorScheme
//    }
    val colorScheme = controller.getColorScheme()
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

enum class ThemeOption {
    SYSTEM,
    LIGHT,
    DARK,
    RED,
    BLUE
}

class ThemeController(private val context: Context) {
    private val _themeOption = MutableStateFlow(getInitialThemeOption())
    private val themeOption = _themeOption.asStateFlow()

    private fun getInitialThemeOption(): ThemeOption {
        // Пока заглушка
        return ThemeOption.SYSTEM
    }

    fun setTheme(option: ThemeOption) {
        _themeOption.value = option
        // Тут можно добавить сохранение в DataStore
    }

    @Composable
    fun getColorScheme(): ColorScheme {
        val option by themeOption.collectAsState()
        return when (option) {
            ThemeOption.LIGHT -> LightColorScheme
            ThemeOption.DARK -> DarkColorScheme
            ThemeOption.RED -> RedColorScheme
            ThemeOption.BLUE -> BlueColorScheme
            ThemeOption.SYSTEM -> if (isSystemInDarkTheme()) DarkColorScheme else LightColorScheme
        }
    }
}