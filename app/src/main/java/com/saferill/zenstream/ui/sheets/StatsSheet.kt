package com.saferill.zenstream.ui.sheets

import android.os.Bundle
import android.view.View
import com.saferill.zenstream.R
import com.saferill.zenstream.constants.IntentData
import com.saferill.zenstream.databinding.DialogStatsBinding
import com.saferill.zenstream.extensions.parcelable
import com.saferill.zenstream.helpers.ClipboardHelper
import com.saferill.zenstream.obj.VideoStats

class StatsSheet : ExpandedBottomSheet(R.layout.dialog_stats) {
    private lateinit var stats: VideoStats

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stats = arguments?.parcelable(IntentData.videoStats)!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = DialogStatsBinding.bind(view)
        binding.videoId.setText(stats.videoId)
        binding.videoIdCopy.setEndIconOnClickListener {
            ClipboardHelper.save(requireContext(), "text", stats.videoId)
        }
        binding.videoInfo.setText(stats.videoInfo)
        binding.audioInfo.setText(stats.audioInfo)
        binding.videoQuality.setText(stats.videoQuality)
    }
}
