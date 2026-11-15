package com.saferill.zenstream.ui.components.profiles

import androidx.annotation.StringRes
import com.saferill.zenstream.R
import com.saferill.zenstream.data.profile.SwitchRefusal

/** What to tell someone whose switch didn't happen. */
@StringRes
fun switchRefusalText(reason: SwitchRefusal?): Int =
    when (reason) {
        SwitchRefusal.CAR_CONNECTED -> R.string.profiles_switch_refused_car
        SwitchRefusal.SYNC_RUNNING -> R.string.profiles_switch_refused_sync
        SwitchRefusal.BACKUP_RUNNING -> R.string.profiles_switch_refused_backup
        SwitchRefusal.UNKNOWN_PROFILE, SwitchRefusal.ALREADY_ACTIVE, null -> R.string.profiles_switch_failed
    }
