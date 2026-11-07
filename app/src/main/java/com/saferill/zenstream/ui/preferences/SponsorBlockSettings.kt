package com.saferill.zenstream.ui.preferences

import android.os.Bundle
import com.saferill.zenstream.R
import com.saferill.zenstream.ui.base.BasePreferenceFragment

class SponsorBlockSettings : BasePreferenceFragment() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.sponsorblock_settings, rootKey)
    }
}
