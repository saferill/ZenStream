package com.saferill.zenstream.ui.screens.settings.security

import dagger.hilt.android.lifecycle.HiltViewModel
import com.saferill.zenstream.applock.AppLock
import com.saferill.zenstream.applock.AppLockSettings
import com.saferill.zenstream.data.local.PlayerPreferences
import com.saferill.zenstream.ui.screens.settings.SettingsViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SecurityViewModel
    @Inject
    constructor(
        val appLock: AppLock,
        private val preferences: PlayerPreferences,
    ) : SettingsViewModel() {
        val settings: StateFlow<AppLockSettings?> = appLock.settings

        fun isDeviceSecure(): Boolean = appLock.isDeviceSecure()

        fun setEnabled(enabled: Boolean) = write { preferences.setAppLockEnabled(enabled) }

        fun setDelayMinutes(minutes: Int) = write { preferences.setAppLockDelayMinutes(minutes) }

        fun setHideInRecents(hide: Boolean) = write { preferences.setAppLockHideInRecents(hide) }
    }
