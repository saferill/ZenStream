package com.saferill.zenstream.innertube.pages

import com.saferill.zenstream.innertube.models.SongItem

data class PlaylistContinuationPage(
    val songs: List<SongItem>,
    val continuation: String?,
)
