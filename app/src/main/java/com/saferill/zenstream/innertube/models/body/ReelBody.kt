package com.saferill.zenstream.innertube.models.body

import com.saferill.zenstream.innertube.models.Context
import kotlinx.serialization.Serializable

@Serializable
data class ReelBody(
    val context: Context,
    val sequenceParams: String,
)
