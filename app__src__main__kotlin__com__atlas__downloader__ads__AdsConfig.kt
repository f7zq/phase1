package com.atlas.downloader.ads

import com.atlas.downloader.BuildConfig

/**
 * Single source of AdMob identifiers. Values come from BuildConfig, which is filled per build type
 * in app/build.gradle.kts: Google test IDs for debug, the real ATLAS IDs for release.
 * No other class may hard-code an ad ID.
 */
object AdsConfig {
    val appId: String = BuildConfig.ADMOB_APP_ID
    val bannerUnitId: String = BuildConfig.AD_UNIT_BANNER
    val interstitialUnitId: String = BuildConfig.AD_UNIT_INTERSTITIAL
    val nativeUnitId: String = BuildConfig.AD_UNIT_NATIVE
    val appOpenUnitId: String = BuildConfig.AD_UNIT_APP_OPEN
}
