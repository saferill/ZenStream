package com.saferill.zenstream.repo

import com.saferill.zenstream.api.RetrofitInstance
import com.saferill.zenstream.api.obj.StreamItem
import com.saferill.zenstream.helpers.PreferenceHelper

class PipedAccountFeedRepository : FeedRepository {
    override suspend fun getFeed(
        forceRefresh: Boolean,
        onProgressUpdate: (FeedProgress) -> Unit
    ): List<StreamItem> {
        val token = PreferenceHelper.getToken()

        return RetrofitInstance.authApi.getFeed(token)
    }
}