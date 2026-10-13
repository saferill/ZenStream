package com.saferill.zenstream.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.saferill.zenstream.db.dao.CustomInstanceDao
import com.saferill.zenstream.db.dao.DownloadDao
import com.saferill.zenstream.db.dao.LocalPlaylistsDao
import com.saferill.zenstream.db.dao.LocalSubscriptionDao
import com.saferill.zenstream.db.dao.PlaylistBookmarkDao
import com.saferill.zenstream.db.dao.SearchHistoryDao
import com.saferill.zenstream.db.dao.SubscriptionGroupsDao
import com.saferill.zenstream.db.dao.SubscriptionsFeedDao
import com.saferill.zenstream.db.dao.WatchHistoryDao
import com.saferill.zenstream.db.dao.WatchPositionDao
import com.saferill.zenstream.db.obj.CustomInstance
import com.saferill.zenstream.db.obj.Download
import com.saferill.zenstream.db.obj.DownloadChapter
import com.saferill.zenstream.db.obj.DownloadItem
import com.saferill.zenstream.db.obj.DownloadPlaylist
import com.saferill.zenstream.db.obj.DownloadPlaylistVideosCrossRef
import com.saferill.zenstream.db.obj.LocalPlaylist
import com.saferill.zenstream.db.obj.LocalPlaylistItem
import com.saferill.zenstream.db.obj.LocalSubscription
import com.saferill.zenstream.db.obj.PlaylistBookmark
import com.saferill.zenstream.db.obj.SearchHistoryItem
import com.saferill.zenstream.db.obj.SubscriptionGroup
import com.saferill.zenstream.db.obj.SubscriptionsFeedItem
import com.saferill.zenstream.db.obj.WatchHistoryItem
import com.saferill.zenstream.db.obj.WatchPosition

@Database(
    entities = [
        WatchHistoryItem::class,
        WatchPosition::class,
        SearchHistoryItem::class,
        CustomInstance::class,
        LocalSubscription::class,
        PlaylistBookmark::class,
        LocalPlaylist::class,
        LocalPlaylistItem::class,
        Download::class,
        DownloadItem::class,
        DownloadChapter::class,
        DownloadPlaylist::class,
        DownloadPlaylistVideosCrossRef::class,
        SubscriptionGroup::class,
        SubscriptionsFeedItem::class
    ],
    version = 21,
    autoMigrations = [
        AutoMigration(from = 7, to = 8),
        AutoMigration(from = 8, to = 9),
        AutoMigration(from = 9, to = 10),
        AutoMigration(from = 10, to = 11),
        AutoMigration(from = 16, to = 17),
        AutoMigration(from = 18, to = 19),
        AutoMigration(from = 19, to = 20),
        AutoMigration(from = 20, to = 21)
    ]
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Watch History
     */
    abstract fun watchHistoryDao(): WatchHistoryDao

    /**
     * Watch Positions
     */
    abstract fun watchPositionDao(): WatchPositionDao

    /**
     * Search History
     */
    abstract fun searchHistoryDao(): SearchHistoryDao

    /**
     * Custom Instances
     */
    abstract fun customInstanceDao(): CustomInstanceDao

    /**
     * Local Subscriptions
     */
    abstract fun localSubscriptionDao(): LocalSubscriptionDao

    /**
     * Bookmarked Playlists
     */
    abstract fun playlistBookmarkDao(): PlaylistBookmarkDao

    /**
     * Local playlists
     */
    abstract fun localPlaylistsDao(): LocalPlaylistsDao

    /**
     * Downloads
     */
    abstract fun downloadDao(): DownloadDao

    /**
     * Subscription groups
     */
    abstract fun subscriptionGroupsDao(): SubscriptionGroupsDao

    /**
     * Locally cached subscription feed
     */
    abstract fun feedDao(): SubscriptionsFeedDao
}
