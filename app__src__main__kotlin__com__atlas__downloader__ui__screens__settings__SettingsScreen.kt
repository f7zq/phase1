package com.atlas.downloader.ui.screens.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.atlas.downloader.R
import com.atlas.downloader.ui.components.GlassChoiceChip
import com.atlas.downloader.ui.components.GlassSurface
import com.atlas.downloader.ui.components.ScreenHeader
import com.atlas.downloader.ui.theme.Atlas

private enum class AppLanguage(val tag: String) {
    System(""),
    Arabic("ar"),
    English("en"),
}

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = Atlas.colors
    val darkTheme by viewModel.darkTheme.collectAsStateWithLifecycle()

    // Re-read the locale whenever the configuration changes (the activity is recreated on switch).
    val configuration = LocalConfiguration.current
    val selectedLanguage = remember(configuration) {
        val tag = AppCompatDelegate.getApplicationLocales().toLanguageTags()
        AppLanguage.entries.firstOrNull { it != AppLanguage.System && tag.startsWith(it.tag) }
            ?: AppLanguage.System
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        ScreenHeader(title = stringResource(R.string.settings_title), onBack = onBack)

        Text(
            text = stringResource(R.string.settings_general),
            style = MaterialTheme.typography.titleMedium,
            color = colors.accent,
            modifier = Modifier.padding(top = 8.dp),
        )

        GlassSurface(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.settings_language),
                style = MaterialTheme.typography.titleMedium,
                color = colors.textPrimary,
            )
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectableGroup(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                GlassChoiceChip(
                    text = stringResource(R.string.language_system),
                    selected = selectedLanguage == AppLanguage.System,
                    onClick = { setLanguage(AppLanguage.System) },
                )
                GlassChoiceChip(
                    text = stringResource(R.string.language_arabic),
                    selected = selectedLanguage == AppLanguage.Arabic,
                    onClick = { setLanguage(AppLanguage.Arabic) },
                )
                GlassChoiceChip(
                    text = stringResource(R.string.language_english),
                    selected = selectedLanguage == AppLanguage.English,
                    onClick = { setLanguage(AppLanguage.English) },
                )
            }
        }

        GlassSurface(modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.settings_dark_theme),
                        style = MaterialTheme.typography.titleMedium,
                        color = colors.textPrimary,
                    )
                    Text(
                        text = stringResource(R.string.settings_dark_theme_desc),
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.textSecondary,
                    )
                }
                Spacer(Modifier.width(12.dp))
                Switch(
                    checked = darkTheme,
                    onCheckedChange = viewModel::setDarkTheme,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = colors.onAccent,
                        checkedTrackColor = colors.accent,
                        uncheckedThumbColor = colors.textSecondary,
                        uncheckedTrackColor = colors.glassTop,
                        uncheckedBorderColor = colors.borderStart,
                    ),
                )
            }
        }
    }
}

/** Per-app language: AppCompat stores it and recreates the activity (RTL/LTR follows the locale). */
private fun setLanguage(language: AppLanguage) {
    val locales = if (language == AppLanguage.System) {
        LocaleListCompat.getEmptyLocaleList()
    } else {
        LocaleListCompat.forLanguageTags(language.tag)
    }
    AppCompatDelegate.setApplicationLocales(locales)
}
