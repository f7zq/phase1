package com.atlas.downloader.data.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "atlas_settings")

/**
 * Persists user settings. Language is not stored here: it is owned by AppCompat's
 * per-app locale support (see SettingsScreen).
 */
class SettingsRepository(private val context: Context) {

    private object Keys {
        val DARK_THEME = booleanPreferencesKey("dark_theme")
    }

    /** Dark (black & gold) is the default look. */
    val darkTheme: Flow<Boolean> = context.settingsDataStore.data
        .catch { error ->
            if (error is IOException) emit(emptyPreferences()) else throw error
        }
        .map { prefs -> prefs[Keys.DARK_THEME] ?: DEFAULT_DARK_THEME }

    suspend fun setDarkTheme(enabled: Boolean) {
        context.settingsDataStore.edit { prefs -> prefs[Keys.DARK_THEME] = enabled }
    }

    companion object {
        const val DEFAULT_DARK_THEME = true
    }
}
