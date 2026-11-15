package com.saferill.zenstream.innertube.pages

import com.saferill.zenstream.innertube.models.YTItem

data class LibraryContinuationPage(
    val items: List<YTItem>,
    val continuation: String?,
)
