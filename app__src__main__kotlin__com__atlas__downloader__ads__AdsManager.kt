package com.atlas.downloader.ads

/**
 * The only ads entry point the rest of the app is allowed to depend on.
 *
 * Phase 1 ships this interface and a no-op implementation so no real ads are initialised yet.
 * Phase 6 adds the Google Mobile Ads SDK, consent (UMP), frequency capping and the four
 * ad managers behind this same interface, so UI and download code never change.
 */
interface AdsManager {
    /** True only once the SDK is initialised and consent allows requesting ads. */
    val isActive: Boolean
}

/** Used until Phase 6. Never loads or shows anything. */
object NoOpAdsManager : AdsManager {
    override val isActive: Boolean = false
}
