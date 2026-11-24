package com.atlas.downloader.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.atlas.downloader.R
import com.atlas.downloader.ui.theme.Atlas

/** The ATLAS wordmark in a gold gradient. */
@Composable
fun AtlasLogo(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 32.sp,
) {
    val colors = Atlas.colors
    Text(
        text = stringResource(R.string.app_name),
        modifier = modifier,
        style = TextStyle(
            brush = Brush.linearGradient(listOf(colors.accentBright, colors.accent)),
            fontSize = fontSize,
            fontWeight = FontWeight.Black,
            letterSpacing = (fontSize.value * 0.18f).sp,
        ),
    )
}
