package com.atlas.downloader.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.atlas.downloader.ui.theme.Atlas

/**
 * Deep background with two slowly drifting gold glows that give the glass panels something
 * to catch. The animation only runs when [animated] is true, to save battery on other screens.
 */
@Composable
fun AtlasBackground(
    modifier: Modifier = Modifier,
    animated: Boolean = false,
    content: @Composable BoxScope.() -> Unit,
) {
    val colors = Atlas.colors
    val drift: State<Float> = if (animated) {
        rememberInfiniteTransition(label = "atlasBackground").animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 14_000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse,
            ),
            label = "drift",
        )
    } else {
        remember { mutableFloatStateOf(0.5f) }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colors.background)
            .drawBehind {
                val t = drift.value
                val radius = size.width * 0.95f
                val topGlowCenter = Offset(size.width * (0.1f + 0.3f * t), size.height * 0.08f)
                val bottomGlowCenter = Offset(size.width * (0.9f - 0.3f * t), size.height * 0.92f)
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(colors.glow, Color.Transparent),
                        center = topGlowCenter,
                        radius = radius,
                    ),
                    radius = radius,
                    center = topGlowCenter,
                )
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(colors.glow, Color.Transparent),
                        center = bottomGlowCenter,
                        radius = radius,
                    ),
                    radius = radius,
                    center = bottomGlowCenter,
                )
            },
        content = content,
    )
}
