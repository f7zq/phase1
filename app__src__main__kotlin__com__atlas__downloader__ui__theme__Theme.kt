package com.atlas.downloader.ui.theme

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val LocalAtlasColors = staticCompositionLocalOf { DarkAtlasColors }

/** Access to ATLAS design tokens: `Atlas.colors.accent`. */
object Atlas {
    val colors: AtlasColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAtlasColors.current
}

private val AtlasTypography = Typography(
    headlineSmall = TextStyle(fontSize = 24.sp, lineHeight = 30.sp, fontWeight = FontWeight.Bold),
    titleMedium = TextStyle(fontSize = 16.sp, lineHeight = 22.sp, fontWeight = FontWeight.SemiBold),
    bodyMedium = TextStyle(fontSize = 14.sp, lineHeight = 21.sp, fontWeight = FontWeight.Normal),
    bodySmall = TextStyle(fontSize = 12.sp, lineHeight = 18.sp, fontWeight = FontWeight.Normal),
    labelSmall = TextStyle(fontSize = 11.sp, lineHeight = 14.sp, fontWeight = FontWeight.Medium),
)

@Composable
fun AtlasTheme(darkTheme: Boolean, content: @Composable () -> Unit) {
    val colors = if (darkTheme) DarkAtlasColors else LightAtlasColors
    val scheme = if (darkTheme) {
        darkColorScheme(
            primary = colors.accent,
            onPrimary = colors.onAccent,
            background = colors.background,
            onBackground = colors.textPrimary,
            surface = colors.background,
            onSurface = colors.textPrimary,
            onSurfaceVariant = colors.textSecondary,
        )
    } else {
        lightColorScheme(
            primary = colors.accent,
            onPrimary = colors.onAccent,
            background = colors.background,
            onBackground = colors.textPrimary,
            surface = colors.background,
            onSurface = colors.textPrimary,
            onSurfaceVariant = colors.textSecondary,
        )
    }

    CompositionLocalProvider(
        LocalAtlasColors provides colors,
        LocalContentColor provides colors.textPrimary,
    ) {
        MaterialTheme(colorScheme = scheme, typography = AtlasTypography, content = content)
    }
}
