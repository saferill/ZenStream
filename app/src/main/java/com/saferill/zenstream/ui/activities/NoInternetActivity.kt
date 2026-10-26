package com.saferill.zenstream.ui.activities

import android.content.Intent
import android.os.Bundle
import com.saferill.zenstream.constants.IntentData
import com.saferill.zenstream.databinding.ActivityNointernetBinding
import com.saferill.zenstream.helpers.NavigationHelper
import com.saferill.zenstream.ui.base.BaseActivity

class NoInternetActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityNointernetBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        if (intent.getBooleanExtra(IntentData.maximizePlayer, false)) {
            NavigationHelper.openAudioPlayerFragment(this, offlinePlayer = true)
        }
    }
}
