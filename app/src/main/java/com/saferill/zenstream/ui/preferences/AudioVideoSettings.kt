package com.saferill.zenstream.ui.preferences

import android.os.Bundle
import com.saferill.zenstream.R
import com.saferill.zenstream.ui.base.BasePreferenceFragment

class AudioVideoSettings : BasePreferenceFragment() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.audio_video_settings, rootKey)
    }
}
