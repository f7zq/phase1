package com.atlas.downloader

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.atlas.downloader.ui.AtlasRoot
import com.atlas.downloader.ui.screens.settings.SettingsViewModel
import com.atlas.downloader.ui.theme.AtlasTheme

// AppCompatActivity (not ComponentActivity) is required for per-app language switching.
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            val settingsViewModel: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory)
            val darkTheme by settingsViewModel.darkTheme.collectAsStateWithLifecycle()

            DisposableEffect(darkTheme) {
                val transparent = android.graphics.Color.TRANSPARENT
                val barStyle = if (darkTheme) {
                    SystemBarStyle.dark(transparent)
                } else {
                    SystemBarStyle.light(transparent, transparent)
                }
                enableEdgeToEdge(statusBarStyle = barStyle, navigationBarStyle = barStyle)
                onDispose { }
            }

            AtlasTheme(darkTheme = darkTheme) {
                AtlasRoot(settingsViewModel = settingsViewModel)
            }
        }
    }
}
