package com.singularity.tarkovmarket.ui.theme

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

val TarkovLightColorScheme = lightColorScheme(
    primary = Color(0xFF908032),              // Приглушённый латунно-оливковый (армейский акцент)
    onPrimary = Color(0xFFFFFFFF),            // Белый текст на primary
    primaryContainer = Color(0xFFF5E9B4),     // Светлый песочный контейнер
    onPrimaryContainer = Color(0xFF2D280E),   // Тёмно-оливковый текст на контейнере

    inversePrimary = Color(0xFFBEB264),       // Аналогично dark-схеме, чуть мягче

    secondary = Color(0xFF6F8B65),            // Мягкий зелёно-серый
    onSecondary = Color(0xFFFFFFFF),          // Белый текст
    secondaryContainer = Color(0xFFD5DEC5),   // Светлый болотный контейнер
    onSecondaryContainer = Color(0xFF232D1A), // Тёмный зелёный на контейнере

    tertiary = Color(0xFFAF855B),             // Бронзово-бежевый (медные элементы)
    onTertiary = Color(0xFFFFFFFF),           // Белый текст
    tertiaryContainer = Color(0xFFF9E4CC),    // Светлый беж
    onTertiaryContainer = Color(0xFF3A2711),  // Тёмный коричневый текст на контейнере

    background = Color(0xFFF3F1EB),           // Общий фон: песочно-серый, с чуть холодным оттенком
    onBackground = Color(0xFF24251F),         // Очень тёмный серо-зелёный

    surface = Color(0xFFF7F6F2),              // Светлая поверхность, почти белая с беж/серым
    onSurface = Color(0xFF24251F),            // Тёмный для читаемости

    surfaceVariant = Color(0xFFE0E2CE),       // Светлый серо-зелёный вариант
    onSurfaceVariant = Color(0xFF454C39),     // Хаки для текста на surfaceVariant

    surfaceTint = Color(0xFF908032),          // Primary для tint

    inverseSurface = Color(0xFF313332),       // Для bottom sheet/диалогов — тёмный
    inverseOnSurface = Color(0xFFE5E8DA),     // Белый текст на inverseSurface

    error = Color(0xFFD44F3A),                // Неярко-красный для ошибок
    onError = Color(0xFFFFFFFF),              // Белый текст на ошибке
    errorContainer = Color(0xFFF3D4CF),       // Светлый бежево-розовый контейнер
    onErrorContainer = Color(0xFF601410),     // Тёмно-коричневый текст на контейнере

    outline = Color(0xFF868776),              // Серый для линий, иконок, разделителей
    outlineVariant = Color(0xFFD1D4C5),       // Очень светлый вариант outline

    scrim = Color(0x1A000000),                // Едва заметный чёрный

    // Surface containers для tonal elevation (Material 3)
    surfaceBright = Color(0xFFFDFCF8),
    surfaceContainer = Color(0xFFECE9E2),
    surfaceContainerHigh = Color(0xFFE3E0D7),
    surfaceContainerHighest = Color(0xFFD5D3C6),
    surfaceContainerLow = Color(0xFFF5F4EF),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceDim = Color(0xFFE1DED6),
)

val TarkovDarkColorScheme = darkColorScheme(
    primary = Color(0xFFBEB264),             // Латунь, бронза (патроны, армейский акцент)
    onPrimary = Color(0xFF181A16),           // Очень тёмно-оливковый
    primaryContainer = Color(0xFF403C23),    // Глубокий хаки
    onPrimaryContainer = Color(0xFFF2ECD1),  // Светлый песочный

    inversePrimary = Color(0xFFD3C27A),      // Ярче primary, используется редко

    secondary = Color(0xFF758575),           // Армейский зелёный, приглушённый
    onSecondary = Color(0xFF161D16),         // Почти чёрный зелёный
    secondaryContainer = Color(0xFF232E23),  // Тёмный болотный контейнер
    onSecondaryContainer = Color(0xFFE5E8DA),// Светлый

    tertiary = Color(0xFF9A7A56),            // Грязно-коричневый (медные элементы)
    onTertiary = Color(0xFF22180E),          // Очень тёмный коричневый
    tertiaryContainer = Color(0xFF46392A),   // Глубокий коричневый
    onTertiaryContainer = Color(0xFFF2E7D6), // Светлый беж

    background = Color(0xFF181B1B),          // Общий фон: асфальтовый, чуть зеленоватый
    onBackground = Color(0xFFE9EBE4),        // Бело-серый

    surface = Color(0xFF23272A),             // Поверхность: графитовый, с холодным оттенком
    onSurface = Color(0xFFDEE0DC),           // Светло-серый для текста

    surfaceVariant = Color(0xFF40473A),      // Серовато-зелёный вариант поверхности
    onSurfaceVariant = Color(0xFFC0C8B2),    // Светлый мутный для текста на surfaceVariant

    surfaceTint = Color(0xFFBEB264),         // Primary для tint

    inverseSurface = Color(0xFFE5E8DA),      // Светлый контейнер, противоположный surface
    inverseOnSurface = Color(0xFF232823),    // Тёмный текст на inverseSurface

    error = Color(0xFFE85D4A),               // Ошибка: грязно-красный (не кислотный!)
    onError = Color(0xFF3C1816),             // Тёмный текст на ошибке
    errorContainer = Color(0xFF5C1F18),      // Коричневатый для контейнера ошибки
    onErrorContainer = Color(0xFFFFDBD6),    // Светлый текст на errorContainer

    outline = Color(0xFF6B6E64),             // Грязно-серый (рамки, разделители)
    outlineVariant = Color(0xFF454843),      // Более тёмный для вторичных линий

    scrim = Color(0x66000000),               // Полупрозрачный чёрный

    // Surface containers для tonal elevation (Material 3)
    surfaceBright = Color(0xFF323534),       // Самая яркая поверхность
    surfaceContainer = Color(0xFF222422),    // Базовая контейнерная поверхность
    surfaceContainerHigh = Color(0xFF292B29),
    surfaceContainerHighest = Color(0xFF313332),
    surfaceContainerLow = Color(0xFF1B1C1B),
    surfaceContainerLowest = Color(0xFF121312),
    surfaceDim = Color(0xFF171817),          // Самая тёмная поверхность
)
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
    controller: ThemeController = ThemeController(),
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
    val colorScheme = controller.getColorScheme(darkTheme)
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

class ThemeController(private val context: Context?=null) {
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
    fun getColorScheme(darkTheme: Boolean): ColorScheme {
        val option by themeOption.collectAsState()
        return when (option) {
            ThemeOption.LIGHT -> TarkovLightColorScheme
            ThemeOption.DARK -> TarkovDarkColorScheme
            ThemeOption.RED -> RedColorScheme
            ThemeOption.BLUE -> BlueColorScheme
            ThemeOption.SYSTEM -> if (darkTheme) TarkovDarkColorScheme else TarkovLightColorScheme
        }
    }
}