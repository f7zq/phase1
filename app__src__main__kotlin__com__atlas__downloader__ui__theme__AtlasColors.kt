package com.atlas.downloader.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/** Design tokens for the black & gold liquid-glass look. */
@Immutable
data class AtlasColors(
    val isDark: Boolean,
    val background: Color,
    /** Soft gold light painted on the background. */
    val glow: Color,
    val glassTop: Color,
    val glassBottom: Color,
    /** Light sheen along the top edge of glass panels. */
    val glassHighlight: Color,
    val borderStart: Color,
    val borderEnd: Color,
    val shadow: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val accent: Color,
    val accentBright: Color,
    val onAccent: Color,
)

val DarkAtlasColors = AtlasColors(
    isDark = true,
    background = Color(0xFF050505),
    glow = Color(0x33D4AF37),
    glassTop = Color(0x1FFFFFFF),
    glassBottom = Color(0x0DD4AF37),
    glassHighlight = Color(0x2EFFFFFF),
    borderStart = Color(0x66F5D77A),
    borderEnd = Color(0x1AD4AF37),
    shadow = Color(0x33D4AF37),
    textPrimary = Color(0xFFF4EFE0),
    textSecondary = Color(0xFFA9A18A),
    accent = Color(0xFFD4AF37),
    accentBright = Color(0xFFF5D77A),
    onAccent = Color(0xFF1A1405),
)

val LightAtlasColors = AtlasColors(
    isDark = false,
    background = Color(0xFFFAF6EA),
    glow = Color(0x33D4AF37),
    glassTop = Color(0xCCFFFFFF),
    glassBottom = Color(0x80FFFFFF),
    glassHighlight = Color(0x99FFFFFF),
    borderStart = Color(0x99B8921F),
    borderEnd = Color(0x33B8921F),
    shadow = Color(0x22B8921F),
    textPrimary = Color(0xFF1B1608),
    textSecondary = Color(0xFF6B6248),
    accent = Color(0xFF8A6D1F),
    accentBright = Color(0xFF9C7A17),
    onAccent = Color(0xFFFFFFFF),
)
