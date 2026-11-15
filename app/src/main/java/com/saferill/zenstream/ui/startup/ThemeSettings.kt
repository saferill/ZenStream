package com.saferill.zenstream.ui.startup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.font.FontFamily
import com.saferill.zenstream.data.local.AppFontPreferences
import com.saferill.zenstream.data.local.LocalDataManager
import com.saferill.zenstream.ui.components.shared.LocalClassicLoadingIndicator
import com.saferill.zenstream.ui.components.shared.LocalMarqueeEnabled
import com.saferill.zenstream.ui.components.shared.LocalSkeletonGradientEnabled
import com.saferill.zenstream.ui.theme.CustomTheme
import com.saferill.zenstream.ui.theme.FlowFontFamily
import com.saferill.zenstream.ui.theme.FlowTheme
import com.saferill.zenstream.ui.theme.ThemeMode
import com.saferill.zenstream.ui.theme.ThemeVariant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

/** Every stored theme choice the root theme needs, read as one value so the first frame has all of them. */
@Immutable
data class ThemeSettings(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val themeVariant: ThemeVariant = ThemeVariant.DARK,
    val customTheme: CustomTheme? = null,
    val systemLightThemeMode: ThemeMode = ThemeMode.DARK,
    val systemDarkThemeMode: ThemeMode = ThemeMode.DARK,
    val systemDarkThemeVariant: ThemeVariant = ThemeVariant.DARK,
    val fontFamily: FontFamily = FlowFontFamily,
    val skeletonGradient: Boolean = false,
    val marqueeEnabled: Boolean = true,
    val classicLoadingIndicator: Boolean = false,
)

fun LocalDataManager.themeSettings(fonts: AppFontPreferences): Flow<ThemeSettings> =
    combine(themeMode, themeVariant, activeCustomTheme, systemLightThemeMode, systemDarkThemeMode) { mode, variant, custom, light, dark ->
        ThemeSettings(mode, variant, custom, light, dark)
    }.combine(systemDarkThemeVariant) { settings, darkVariant -> settings.copy(systemDarkThemeVariant = darkVariant) }
        .combine(fonts.fontFamily) { settings, family -> settings.copy(fontFamily = family) }
        .combine(skeletonGradient) { settings, gradient -> settings.copy(skeletonGradient = gradient) }
        .combine(marqueeEnabled) { settings, marquee -> settings.copy(marqueeEnabled = marquee) }
        .combine(classicLoadingIndicator) { settings, classic -> settings.copy(classicLoadingIndicator = classic) }

@Composable
fun FlowTheme(
    settings: ThemeSettings,
    content: @Composable () -> Unit,
) = FlowTheme(
    themeMode = settings.themeMode,
    themeVariant = settings.themeVariant,
    customTheme = settings.customTheme,
    systemLightThemeMode = settings.systemLightThemeMode,
    systemDarkThemeMode = settings.systemDarkThemeMode,
    systemDarkThemeVariant = settings.systemDarkThemeVariant,
    fontFamily = settings.fontFamily,
    content = {
        CompositionLocalProvider(
            LocalSkeletonGradientEnabled provides settings.skeletonGradient,
            LocalMarqueeEnabled provides settings.marqueeEnabled,
            LocalClassicLoadingIndicator provides settings.classicLoadingIndicator,
            content = content,
        )
    },
)
