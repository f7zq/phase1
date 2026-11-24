package com.atlas.downloader.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material.icons.rounded.VideoLibrary
import androidx.compose.ui.graphics.vector.ImageVector
import com.atlas.downloader.R

object Routes {
    const val SPLASH = "splash"
    const val HOME = "home"
    const val DOWNLOADS = "downloads"
    const val LIBRARY = "library"
    const val MORE = "more"
    const val SETTINGS = "settings"
    const val ABOUT = "about"
}

/** Destinations shown in the bottom bar. */
enum class TopLevel(
    val route: String,
    @StringRes val label: Int,
    val icon: ImageVector,
) {
    Home(Routes.HOME, R.string.nav_home, Icons.Rounded.Home),
    Downloads(Routes.DOWNLOADS, R.string.nav_downloads, Icons.Rounded.Download),
    Library(Routes.LIBRARY, R.string.nav_library, Icons.Rounded.VideoLibrary),
    More(Routes.MORE, R.string.nav_more, Icons.Rounded.MoreHoriz),
}
