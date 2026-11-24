package com.atlas.downloader.ui.screens.downloads

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Download
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.atlas.downloader.R
import com.atlas.downloader.ui.components.EmptyStateCard
import com.atlas.downloader.ui.components.ScreenHeader

/** Phase 1 placeholder. The download list and controls arrive in Phase 2. */
@Composable
fun DownloadsScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        ScreenHeader(title = stringResource(R.string.downloads_title))
        EmptyStateCard(
            icon = Icons.Rounded.Download,
            title = stringResource(R.string.downloads_empty_title),
            body = stringResource(R.string.downloads_empty_body),
        )
    }
}
