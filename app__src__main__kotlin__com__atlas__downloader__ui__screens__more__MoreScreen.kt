package com.atlas.downloader.ui.screens.more

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Storage
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.atlas.downloader.R
import com.atlas.downloader.ui.components.GlassListItem
import com.atlas.downloader.ui.components.ScreenHeader

@Composable
fun MoreScreen(
    onOpenSettings: () -> Unit,
    onOpenAbout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        ScreenHeader(title = stringResource(R.string.more_title))
        GlassListItem(
            icon = Icons.Rounded.Settings,
            title = stringResource(R.string.more_settings),
            onClick = onOpenSettings,
        )
        GlassListItem(
            icon = Icons.Rounded.Public,
            title = stringResource(R.string.more_browser),
            comingSoon = true,
        )
        GlassListItem(
            icon = Icons.Rounded.Lock,
            title = stringResource(R.string.more_private_folder),
            comingSoon = true,
        )
        GlassListItem(
            icon = Icons.Rounded.Storage,
            title = stringResource(R.string.more_storage),
            comingSoon = true,
        )
        GlassListItem(
            icon = Icons.Rounded.Info,
            title = stringResource(R.string.more_about),
            onClick = onOpenAbout,
        )
    }
}
