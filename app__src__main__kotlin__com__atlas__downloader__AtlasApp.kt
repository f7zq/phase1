package com.atlas.downloader

import android.app.Application
import com.atlas.downloader.ads.AdsManager
import com.atlas.downloader.ads.NoOpAdsManager
import com.atlas.downloader.data.settings.SettingsRepository

/**
 * Application entry point. Holds the few app-wide singletons (no DI framework yet).
 */
class AtlasApp : Application() {

    val settingsRepository: SettingsRepository by lazy { SettingsRepository(applicationContext) }

    /** Phase 1: ads are not active. Replaced by the real implementation in Phase 6. */
    val adsManager: AdsManager = NoOpAdsManager
}
