package com.saferill.zenstream.innertube.models.body

import com.saferill.zenstream.innertube.models.Context
import kotlinx.serialization.Serializable

@Serializable
data class GetTranscriptBody(
    val context: Context,
    val params: String,
)
