package com.saferill.zenstream.util

import android.content.Context
import com.saferill.zenstream.constants.PreferenceKeys
import com.saferill.zenstream.helpers.NetworkHelper
import com.saferill.zenstream.helpers.PreferenceHelper

object DataSaverMode {
    fun isEnabled(context: Context): Boolean {
        val pref = PreferenceHelper.getString(PreferenceKeys.DATA_SAVER_MODE, "disabled")
        return when (pref) {
            "enabled" -> true
            "disabled" -> false
            "metered" -> NetworkHelper.isNetworkMetered(context)
            else -> throw IllegalArgumentException()
        }
    }
}
