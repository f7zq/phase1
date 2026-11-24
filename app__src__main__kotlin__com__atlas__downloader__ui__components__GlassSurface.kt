package com.atlas.downloader.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.atlas.downloader.ui.theme.Atlas

/**
 * Translucent "liquid glass" panel: vertical glass gradient, a light sheen on the upper half,
 * a thin gold-tinted border and a faint gold shadow. When [onClick] is set the panel
 * gives a small press animation.
 *
 * This simulates glass with gradients; it does not blur what is behind it.
 */
@Composable
fun GlassSurface(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(24.dp),
    onClick: (() -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = Atlas.colors
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed && onClick != null) 0.97f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium,
        ),
        label = "glassPressScale",
    )

    val glass = modifier
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .shadow(
            elevation = 12.dp,
            shape = shape,
            ambientColor = colors.shadow,
            spotColor = colors.shadow,
        )
        .clip(shape)
        .background(Brush.verticalGradient(listOf(colors.glassTop, colors.glassBottom)))
        .drawBehind {
            val sheenHeight = size.height * 0.5f
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(colors.glassHighlight, Color.Transparent),
                    endY = sheenHeight,
                ),
                size = Size(size.width, sheenHeight),
            )
        }
        .border(
            width = 1.dp,
            brush = Brush.linearGradient(listOf(colors.borderStart, colors.borderEnd)),
            shape = shape,
        )

    val interactive = if (onClick != null) {
        glass.clickable(
            interactionSource = interactionSource,
            indication = null,
            role = Role.Button,
            onClick = onClick,
        )
    } else {
        glass
    }

    Column(modifier = interactive.padding(contentPadding), content = content)
}
