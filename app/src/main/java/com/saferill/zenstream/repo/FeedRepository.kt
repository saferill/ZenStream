package com.saferill.zenstream.repo

import com.saferill.zenstream.api.obj.StreamItem
import com.saferill.zenstream.db.obj.SubscriptionsFeedItem

data class FeedProgress(
    val currentProgress: Int,
    val total: Int
)

interface FeedRepository {
    suspend fun getFeed(
        forceRefresh: Boolean,
        onProgressUpdate: (FeedProgress) -> Unit
    ): List<StreamItem>
    suspend fun removeChannel(channelId: String) {}
    suspend fun submitFeedItemChange(feedItem: SubscriptionsFeedItem) {}
}