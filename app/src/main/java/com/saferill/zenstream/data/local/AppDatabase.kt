package com.saferill.zenstream.data.local

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.saferill.zenstream.data.local.dao.CacheDao
import com.saferill.zenstream.data.local.dao.DownloadCollectionDao
import com.saferill.zenstream.data.local.dao.DownloadDao
import com.saferill.zenstream.data.local.dao.HomeFeedCacheDao
import com.saferill.zenstream.data.local.dao.MusicGraphDao
import com.saferill.zenstream.data.local.dao.NoteDao
import com.saferill.zenstream.data.local.dao.NotificationDao
import com.saferill.zenstream.data.local.dao.PlaylistDao
import com.saferill.zenstream.data.local.dao.RecognitionHistoryDao
import com.saferill.zenstream.data.local.dao.SubscriptionGroupDao
import com.saferill.zenstream.data.local.dao.SyncLogDao
import com.saferill.zenstream.data.local.dao.SyncPeerDao
import com.saferill.zenstream.data.local.dao.VideoDao
import com.saferill.zenstream.data.local.dao.WatchHistoryDao
import com.saferill.zenstream.data.local.entity.DownloadCollectionEntity
import com.saferill.zenstream.data.local.entity.DownloadCollectionItemEntity
import com.saferill.zenstream.data.local.entity.DownloadEntity
import com.saferill.zenstream.data.local.entity.DownloadItemEntity
import com.saferill.zenstream.data.local.entity.HomeFeedCacheEntity
import com.saferill.zenstream.data.local.entity.MusicGraphAlbumEntity
import com.saferill.zenstream.data.local.entity.MusicGraphArtistEntity
import com.saferill.zenstream.data.local.entity.MusicGraphEdgeEntity
import com.saferill.zenstream.data.local.entity.MusicGraphPlaylistEntity
import com.saferill.zenstream.data.local.entity.MusicGraphTrackEntity
import com.saferill.zenstream.data.local.entity.MusicHomeCacheEntity
import com.saferill.zenstream.data.local.entity.MusicHomeChipEntity
import com.saferill.zenstream.data.local.entity.NoteEntity
import com.saferill.zenstream.data.local.entity.NotificationEntity
import com.saferill.zenstream.data.local.entity.PlaylistEntity
import com.saferill.zenstream.data.local.entity.PlaylistVideoCrossRef
import com.saferill.zenstream.data.local.entity.RecognitionHistoryEntity
import com.saferill.zenstream.data.local.entity.SubscriptionFeedEntity
import com.saferill.zenstream.data.local.entity.SubscriptionGroupEntity
import com.saferill.zenstream.data.local.entity.SyncLogEntity
import com.saferill.zenstream.data.local.entity.SyncPeerEntity
import com.saferill.zenstream.data.local.entity.VideoEntity
import com.saferill.zenstream.data.local.entity.WatchHistoryEntity
import com.saferill.zenstream.data.local.migrations.MIGRATIONS
import com.saferill.zenstream.data.local.migrations.Migration24To25
import com.saferill.zenstream.data.profile.ProfileFiles

@Database(
    entities = [
        VideoEntity::class,
        PlaylistEntity::class,
        PlaylistVideoCrossRef::class,
        NotificationEntity::class,
        SubscriptionFeedEntity::class,
        MusicHomeCacheEntity::class,
        MusicHomeChipEntity::class,
        DownloadEntity::class,
        DownloadItemEntity::class,
        WatchHistoryEntity::class,
        HomeFeedCacheEntity::class,
        SubscriptionGroupEntity::class,
        RecognitionHistoryEntity::class,
        SyncLogEntity::class,
        SyncPeerEntity::class,
        MusicGraphTrackEntity::class,
        MusicGraphArtistEntity::class,
        MusicGraphAlbumEntity::class,
        MusicGraphPlaylistEntity::class,
        MusicGraphEdgeEntity::class,
        NoteEntity::class,
        DownloadCollectionEntity::class,
        DownloadCollectionItemEntity::class,
    ],
    autoMigrations = [
        AutoMigration(from = 24, to = 25, spec = Migration24To25::class),
        AutoMigration(from = 25, to = 26),
        AutoMigration(from = 26, to = 27),
        AutoMigration(from = 27, to = 28),
        AutoMigration(from = 28, to = 29),
        AutoMigration(from = 29, to = 30),
        AutoMigration(from = 30, to = 31),
        AutoMigration(from = 31, to = 32),
        AutoMigration(from = 32, to = 33),
    ],
    version = 33,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun videoDao(): VideoDao

    abstract fun playlistDao(): PlaylistDao

    abstract fun notificationDao(): NotificationDao

    abstract fun noteDao(): NoteDao

    abstract fun cacheDao(): CacheDao

    abstract fun downloadDao(): DownloadDao

    abstract fun downloadCollectionDao(): DownloadCollectionDao

    abstract fun watchHistoryDao(): WatchHistoryDao

    abstract fun homeFeedCacheDao(): HomeFeedCacheDao

    abstract fun subscriptionGroupDao(): SubscriptionGroupDao

    abstract fun recognitionHistoryDao(): RecognitionHistoryDao

    abstract fun syncLogDao(): SyncLogDao

    abstract fun syncPeerDao(): SyncPeerDao

    abstract fun musicGraphDao(): MusicGraphDao

    companion object {
        const val DEFAULT_NAME = "flow_database"

        @Volatile
        private var deviceDatabase: AppDatabase? = null

        @Volatile
        private var profileDatabase: AppDatabase? = null

        /** Default's file, which also holds the download tables every profile shares. */
        fun device(context: Context): AppDatabase =
            deviceDatabase ?: synchronized(this) {
                deviceDatabase ?: open(context.applicationContext, DEFAULT_NAME).also { deviceDatabase = it }
            }

        /** The running profile's own file. With Default running it is [device]: Room must never open one file twice. */
        fun profile(context: Context): AppDatabase =
            profileDatabase ?: synchronized(this) {
                profileDatabase ?: ProfileFiles
                    .database(context, DEFAULT_NAME)
                    .let { name -> if (name == DEFAULT_NAME) device(context) else open(context.applicationContext, name) }
                    .also { profileDatabase = it }
            }

        internal fun open(
            context: Context,
            name: String,
        ): AppDatabase =
            databaseBuilder(context, AppDatabase::class.java, name)
                .addMigrations(*MIGRATIONS)
                .fallbackToDestructiveMigration(false)
                .build()
    }
}
