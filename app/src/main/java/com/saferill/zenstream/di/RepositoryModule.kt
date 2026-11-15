package com.saferill.zenstream.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.saferill.zenstream.data.local.PlayerPreferences
import com.saferill.zenstream.data.repository.YouTubeRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideYouTubeRepository(playerPreferences: PlayerPreferences): YouTubeRepository = YouTubeRepository.getInstance(playerPreferences)

    @Provides
    @Singleton
    fun provideSubscriptionRepository(
        @ApplicationContext context: Context,
    ): com.saferill.zenstream.data.local.SubscriptionRepository =
        com.saferill.zenstream.data.local.SubscriptionRepository
            .getInstance(context)

    @Provides
    @Singleton
    fun provideLikedVideosRepository(
        @ApplicationContext context: Context,
    ): com.saferill.zenstream.data.local.LikedVideosRepository =
        com.saferill.zenstream.data.local.LikedVideosRepository
            .getInstance(context)

    @Provides
    @Singleton
    fun provideViewHistory(
        @ApplicationContext context: Context,
    ): com.saferill.zenstream.data.local.ViewHistory =
        com.saferill.zenstream.data.local.ViewHistory
            .getInstance(context)

    @Provides
    @Singleton
    fun provideHomeFeedCacheRepository(
        @ApplicationContext context: Context,
    ): com.saferill.zenstream.data.local.HomeFeedCacheRepository =
        com.saferill.zenstream.data.local
            .HomeFeedCacheRepository(context)

    @Provides
    @Singleton
    fun provideMusicPlaylistRepository(
        @ApplicationContext context: Context,
    ): com.saferill.zenstream.data.music.PlaylistRepository =
        com.saferill.zenstream.data.music
            .PlaylistRepository(context)

    // VideoDownloadManager is now @Singleton @Inject — Hilt provides it automatically
    @Provides
    @Singleton
    fun providePlayerPreferences(
        @ApplicationContext context: Context,
    ): com.saferill.zenstream.data.local.PlayerPreferences =
        com.saferill.zenstream.data.local
            .PlayerPreferences(context)

    /** Transitional: the music player manager still reaches the queue store through getInstance, so this hands injected consumers the same instance. */
    @Provides
    @Singleton
    fun provideQueuePersistence(
        @ApplicationContext context: Context,
    ): com.saferill.zenstream.data.local.QueuePersistence =
        com.saferill.zenstream.data.local.QueuePersistence
            .getInstance(context)

    /**
     * Transitional: [com.saferill.zenstream.data.local.BackupRepository] still builds its own
     * collaborators, so it is provided here rather than injected through its constructor. One
     * instance serves the whole app.
     */
    @Provides
    @Singleton
    fun provideBackupRepository(
        @ApplicationContext context: Context,
    ): com.saferill.zenstream.data.local.BackupRepository =
        com.saferill.zenstream.data.local
            .BackupRepository(context)
}
