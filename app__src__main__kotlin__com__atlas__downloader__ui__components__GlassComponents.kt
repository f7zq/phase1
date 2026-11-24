package com.atlas.downloader.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atlas.downloader.R
import com.atlas.downloader.ui.navigation.TopLevel
import com.atlas.downloader.ui.theme.Atlas

/** Screen title with an optional back button (detail screens only). */
@Composable
fun ScreenHeader(
    title: String,
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null,
) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        if (onBack != null) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.cd_back),
                    tint = Atlas.colors.accent,
                )
            }
            Spacer(Modifier.width(4.dp))
        }
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = Atlas.colors.textPrimary,
        )
    }
}

/** Small pill telling the user a feature is not implemented yet. */
@Composable
fun ComingSoonBadge(modifier: Modifier = Modifier) {
    val colors = Atlas.colors
    Text(
        text = stringResource(R.string.badge_coming_soon),
        modifier = modifier
            .border(1.dp, colors.accent.copy(alpha = 0.6f), RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 3.dp),
        style = MaterialTheme.typography.labelSmall,
        color = colors.accent,
        maxLines = 1,
    )
}

/**
 * Row inside a glass card: gold icon, title, optional subtitle. Shows a "coming soon" badge
 * (and is not clickable) for features that are not implemented yet.
 */
@Composable
fun GlassListItem(
    icon: ImageVector,
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    comingSoon: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    val colors = Atlas.colors
    GlassSurface(
        modifier = modifier.fillMaxWidth(),
        onClick = if (comingSoon) null else onClick,
        shape = RoundedCornerShape(20.dp),
        contentPadding = PaddingValues(14.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(colors.accent.copy(alpha = 0.16f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = colors.accent)
            }
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = colors.textPrimary,
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.textSecondary,
                    )
                }
            }
            Spacer(Modifier.width(10.dp))
            when {
                comingSoon -> ComingSoonBadge()
                onClick != null -> Icon(
                    imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                    contentDescription = null,
                    tint = colors.textSecondary,
                )
            }
        }
    }
}

/** Centered empty-state card used by screens whose feature is not built yet. */
@Composable
fun EmptyStateCard(
    icon: ImageVector,
    title: String,
    body: String,
    modifier: Modifier = Modifier,
) {
    val colors = Atlas.colors
    GlassSurface(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 32.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = colors.accent,
                modifier = Modifier.size(44.dp),
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = colors.textPrimary,
                textAlign = TextAlign.Center,
            )
            Text(
                text = body,
                style = MaterialTheme.typography.bodyMedium,
                color = colors.textSecondary,
                textAlign = TextAlign.Center,
            )
        }
    }
}

/** Single-choice chip. Wrap several in a Row with `Modifier.selectableGroup()`. */
@Composable
fun RowScope.GlassChoiceChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = Atlas.colors
    val shape = RoundedCornerShape(50)
    val textColor by animateColorAsState(
        targetValue = if (selected) colors.onAccent else colors.textPrimary,
        label = "chipText",
    )
    val fill = if (selected) {
        Modifier.background(Brush.linearGradient(listOf(colors.accentBright, colors.accent)))
    } else {
        Modifier.background(Brush.verticalGradient(listOf(colors.glassTop, colors.glassBottom)))
    }
    Box(
        modifier = modifier
            .weight(1f)
            .heightIn(min = 44.dp)
            .clip(shape)
            .then(fill)
            .border(1.dp, colors.borderStart, shape)
            .selectable(selected = selected, role = Role.RadioButton, onClick = onClick)
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            color = textColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

/** Floating glass bottom navigation bar. */
@Composable
fun GlassBottomBar(
    items: List<TopLevel>,
    currentRoute: String?,
    onSelect: (TopLevel) -> Unit,
    modifier: Modifier = Modifier,
) {
    GlassSurface(
        modifier = modifier
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(32.dp),
        contentPadding = PaddingValues(6.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectableGroup(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            items.forEach { item ->
                BottomBarItem(
                    item = item,
                    selected = item.route == currentRoute,
                    onClick = { onSelect(item) },
                )
            }
        }
    }
}

@Composable
private fun RowScope.BottomBarItem(
    item: TopLevel,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val colors = Atlas.colors
    val tint by animateColorAsState(
        targetValue = if (selected) colors.accent else colors.textSecondary,
        label = "barItemTint",
    )
    Column(
        modifier = Modifier
            .weight(1f)
            .heightIn(min = 56.dp)
            .clip(RoundedCornerShape(26.dp))
            .background(if (selected) colors.accent.copy(alpha = 0.16f) else androidx.compose.ui.graphics.Color.Transparent)
            .selectable(selected = selected, role = Role.Tab, onClick = onClick)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(imageVector = item.icon, contentDescription = null, tint = tint)
        Text(
            text = stringResource(item.label),
            color = tint,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            maxLines = 1,
        )
    }
}
