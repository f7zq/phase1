package com.atlas.downloader.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.PlaylistPlay
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material.icons.rounded.VideoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atlas.downloader.R
import com.atlas.downloader.ui.components.AtlasLogo
import com.atlas.downloader.ui.components.ComingSoonBadge
import com.atlas.downloader.ui.components.GlassListItem
import com.atlas.downloader.ui.components.GlassSurface
import com.atlas.downloader.ui.theme.Atlas

/**
 * Phase 1 home: brand header, an honest hero card and the list of upcoming features.
 * The space at the bottom (above the navigation bar) is where the banner ad is reserved in Phase 6.
 */
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val colors = Atlas.colors
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        AtlasLogo(fontSize = 30.sp)
        Text(
            text = stringResource(R.string.tagline),
            style = MaterialTheme.typography.bodyMedium,
            color = colors.textSecondary,
        )
        Spacer(Modifier.height(4.dp))

        GlassSurface(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(20.dp),
        ) {
            Icon(
                imageVector = Icons.Rounded.Link,
                contentDescription = null,
                tint = colors.accent,
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.home_hero_title),
                style = MaterialTheme.typography.headlineSmall,
                color = colors.textPrimary,
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = stringResource(R.string.home_hero_body),
                style = MaterialTheme.typography.bodyMedium,
                color = colors.textSecondary,
            )
            Spacer(Modifier.height(14.dp))
            ComingSoonBadge(modifier = Modifier.align(Alignment.Start))
        }

        Text(
            text = stringResource(R.string.home_features_title),
            style = MaterialTheme.typography.titleMedium,
            color = colors.textPrimary,
            modifier = Modifier.padding(top = 6.dp),
        )
        GlassListItem(
            icon = Icons.Rounded.Download,
            title = stringResource(R.string.feature_video_audio_title),
            subtitle = stringResource(R.string.feature_video_audio_body),
            comingSoon = true,
        )
        GlassListItem(
            icon = Icons.AutoMirrored.Rounded.PlaylistPlay,
            title = stringResource(R.string.feature_playlists_title),
            subtitle = stringResource(R.string.feature_playlists_body),
            comingSoon = true,
        )
        GlassListItem(
            icon = Icons.Rounded.VideoLibrary,
            title = stringResource(R.string.feature_library_title),
            subtitle = stringResource(R.string.feature_library_body),
            comingSoon = true,
        )
        GlassListItem(
            icon = Icons.Rounded.Public,
            title = stringResource(R.string.feature_browser_title),
            subtitle = stringResource(R.string.feature_browser_body),
            comingSoon = true,
        )
    }
}
