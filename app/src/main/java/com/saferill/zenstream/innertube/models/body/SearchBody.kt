package com.saferill.zenstream.innertube.models.body

import com.saferill.zenstream.innertube.models.Context
import kotlinx.serialization.Serializable

@Serializable
data class SearchBody(
    val context: Context,
    val query: String?,
    val params: String?,
    val continuation: String? = null,
)
