package com.atlas.downloader.ui.screens.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.VideoLibrary
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.atlas.downloader.R
import com.atlas.downloader.ui.components.EmptyStateCard
import com.atlas.downloader.ui.components.ScreenHeader

/** Phase 1 placeholder. The real, device-backed library arrives in Phase 4. */
@Composable
fun LibraryScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        ScreenHeader(title = stringResource(R.string.library_title))
        EmptyStateCard(
            icon = Icons.Rounded.VideoLibrary,
            title = stringResource(R.string.library_empty_title),
            body = stringResource(R.string.library_empty_body),
        )
    }
}
