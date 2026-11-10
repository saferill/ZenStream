package com.saferill.zenstream.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.saferill.zenstream.databinding.ChannelRowBinding
import com.saferill.zenstream.databinding.PlaylistsRowBinding
import com.saferill.zenstream.databinding.VideoRowBinding

class SearchViewHolder : RecyclerView.ViewHolder {
    var videoRowBinding: VideoRowBinding? = null
    var channelRowBinding: ChannelRowBinding? = null
    var playlistRowBinding: PlaylistsRowBinding? = null

    constructor(binding: VideoRowBinding) : super(binding.root) {
        videoRowBinding = binding
    }

    constructor(binding: ChannelRowBinding) : super(binding.root) {
        channelRowBinding = binding
    }

    constructor(binding: PlaylistsRowBinding) : super(binding.root) {
        playlistRowBinding = binding
    }
}
