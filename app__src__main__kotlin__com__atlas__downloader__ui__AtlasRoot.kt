package com.atlas.downloader.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.atlas.downloader.ui.components.AtlasBackground
import com.atlas.downloader.ui.components.GlassBottomBar
import com.atlas.downloader.ui.navigation.Routes
import com.atlas.downloader.ui.navigation.TopLevel
import com.atlas.downloader.ui.screens.about.AboutScreen
import com.atlas.downloader.ui.screens.downloads.DownloadsScreen
import com.atlas.downloader.ui.screens.home.HomeScreen
import com.atlas.downloader.ui.screens.library.LibraryScreen
import com.atlas.downloader.ui.screens.more.MoreScreen
import com.atlas.downloader.ui.screens.settings.SettingsScreen
import com.atlas.downloader.ui.screens.settings.SettingsViewModel
import com.atlas.downloader.ui.screens.splash.SplashScreen

@Composable
fun AtlasRoot(settingsViewModel: SettingsViewModel) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route
    val showBottomBar = TopLevel.entries.any { it.route == route }

    AtlasBackground(animated = route == Routes.HOME || route == Routes.SPLASH) {
        Scaffold(
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = {
                AnimatedVisibility(
                    visible = showBottomBar,
                    enter = fadeIn() + slideInVertically { it },
                    exit = fadeOut() + slideOutVertically { it },
                ) {
                    GlassBottomBar(
                        items = TopLevel.entries,
                        currentRoute = route,
                        onSelect = { item ->
                            navController.navigate(item.route) {
                                popUpTo(Routes.HOME) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    )
                }
            },
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Routes.SPLASH,
                modifier = Modifier.padding(innerPadding),
                enterTransition = { fadeIn(tween(220)) },
                exitTransition = { fadeOut(tween(160)) },
                popEnterTransition = { fadeIn(tween(220)) },
                popExitTransition = { fadeOut(tween(160)) },
            ) {
                composable(Routes.SPLASH) {
                    SplashScreen(
                        onFinished = {
                            navController.navigate(Routes.HOME) {
                                popUpTo(Routes.SPLASH) { inclusive = true }
                            }
                        },
                    )
                }
                composable(Routes.HOME) { HomeScreen() }
                composable(Routes.DOWNLOADS) { DownloadsScreen() }
                composable(Routes.LIBRARY) { LibraryScreen() }
                composable(Routes.MORE) {
                    MoreScreen(
                        onOpenSettings = { navController.navigate(Routes.SETTINGS) },
                        onOpenAbout = { navController.navigate(Routes.ABOUT) },
                    )
                }
                composable(Routes.SETTINGS) {
                    SettingsScreen(
                        viewModel = settingsViewModel,
                        onBack = { navController.popBackStack() },
                    )
                }
                composable(Routes.ABOUT) {
                    AboutScreen(onBack = { navController.popBackStack() })
                }
            }
        }
    }
}
