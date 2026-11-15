package com.saferill.zenstream.innertube.models

import kotlinx.serialization.Serializable

@Serializable
data class ContinuationItemRenderer(
    val continuationEndpoint: ContinuationEndpoint?,
) {
    @Serializable
    data class ContinuationEndpoint(
        val continuationCommand: ContinuationCommand?,
    ) {
        @Serializable
        data class ContinuationCommand(
            val token: String?,
        )
    }
}
