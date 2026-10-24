package com.saferill.zenstream.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.saferill.zenstream.constants.IntentData
import com.saferill.zenstream.services.DownloadService
import com.saferill.zenstream.ui.activities.MainActivity

class DownloadReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val activityIntent = Intent(context, MainActivity::class.java)

        when (intent?.action) {
            DownloadService.ACTION_SERVICE_STARTED -> {
                activityIntent.putExtra(IntentData.downloading, true)
            }

            DownloadService.ACTION_SERVICE_STOPPED -> {
                activityIntent.putExtra(IntentData.downloading, false)
            }
        }
        context?.startActivity(activityIntent)
    }
}
