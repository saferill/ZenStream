package com.saferill.zenstream.innertube.pages

import com.saferill.zenstream.innertube.models.YTItem

data class ArtistItemsContinuationPage(
    val items: List<YTItem>,
    val continuation: String?,
)
