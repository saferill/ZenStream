package com.saferill.zenstream.service

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.saferill.zenstream.player.LifecyclePlaybackPreferences

/** [VideoPlayerService] is not a Hilt entry point, so the singletons it reads come through here. */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface PlaybackServiceEntryPoint {
    fun lifecyclePlaybackPreferences(): LifecyclePlaybackPreferences
}
