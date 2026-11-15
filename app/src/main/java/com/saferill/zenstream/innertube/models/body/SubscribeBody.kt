package com.saferill.zenstream.innertube.models.body

import com.saferill.zenstream.innertube.models.Context
import kotlinx.serialization.Serializable

@Serializable
data class SubscribeBody(
    val channelIds: List<String>,
    val context: Context,
)
