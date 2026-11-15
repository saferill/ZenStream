package com.saferill.zenstream.di

import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.saferill.zenstream.data.localmedia.LocalMediaIds
import com.saferill.zenstream.data.localmedia.LocalSubtitles
import com.saferill.zenstream.data.recommendation.FlowNeuroEngine
import com.saferill.zenstream.data.video.OfflineSubtitleStore
import com.saferill.zenstream.data.video.VideoDownloadManager
import com.saferill.zenstream.player.EnhancedPlayerManager
import com.saferill.zenstream.player.FeedExclusionsSource
import com.saferill.zenstream.player.LocalCaptionSource
import com.saferill.zenstream.player.LocalCaptions
import com.saferill.zenstream.player.LocalCopySource
import com.saferill.zenstream.player.shorts.ShortsPlayerPool
import com.saferill.zenstream.utils.PerformanceDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NetworkIoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

/**
 * Transitional providers around the player-path singletons so consumers can inject them instead of
 * reaching for the static accessors. Every provider delegates to the existing singleton, so object
 * identity and first-creation timing are unchanged (see CLAUDE.md, DI rule 10).
 */
@Module
@InstallIn(SingletonComponent::class)
object PlayerManagerModule {
    @Provides
    fun provideEnhancedPlayerManager(
        videoDownloadManager: VideoDownloadManager,
        neuroEngine: Lazy<FlowNeuroEngine>,
        localSubtitles: Lazy<LocalSubtitles>,
        offlineSubtitleStore: Lazy<OfflineSubtitleStore>,
    ): EnhancedPlayerManager =
        EnhancedPlayerManager.getInstance().also {
            it.localCopySource =
                LocalCopySource { videoId ->
                    LocalMediaIds.videoUri(videoId)?.toString() ?: videoDownloadManager.localCopyPath(videoId)
                }
            it.localCaptionSource =
                LocalCaptionSource { videoId, path ->
                    val subtitles = localSubtitles.get()
                    val own =
                        if (LocalMediaIds.isLocal(videoId)) subtitles.beside(path) else offlineSubtitleStore.get().load(videoId)
                    LocalCaptions(own + subtitles.picked(videoId), subtitles.offsetMs(videoId))
                }
            it.feedExclusionsSource = FeedExclusionsSource { neuroEngine.get().feedExclusions() }
        }

    @Provides
    fun provideShortsPlayerPool(): ShortsPlayerPool = ShortsPlayerPool.getInstance()

    @Provides
    @NetworkIoDispatcher
    fun provideNetworkIoDispatcher(): CoroutineDispatcher = PerformanceDispatcher.networkIO

    @Provides
    @IoDispatcher
    fun provideIoDispatcher(): CoroutineDispatcher = PerformanceDispatcher.diskIO
}
