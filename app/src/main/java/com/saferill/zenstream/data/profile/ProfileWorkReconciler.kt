package com.saferill.zenstream.data.profile

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import com.saferill.zenstream.data.local.LocalDataManager
import com.saferill.zenstream.data.local.PlayerPreferences
import com.saferill.zenstream.data.scrobble.ScrobbleStore
import com.saferill.zenstream.data.scrobble.ScrobbleWorker
import com.saferill.zenstream.notification.UpcomingReminder
import com.saferill.zenstream.notification.UpcomingVideoReminderWorker
import com.saferill.zenstream.notification.WellbeingAlarms
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

/** Queues again, once per process, the work a switch cancelled for the profile this process runs as. */
@Singleton
class ProfileWorkReconciler
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
        private val preferences: PlayerPreferences,
        private val settings: LocalDataManager,
        private val scrobbleStore: ScrobbleStore,
    ) {
        suspend fun reconcile() {
            val (keep, drop) = remindersToRestore(preferences.upcomingReminders.first(), System.currentTimeMillis())
            keep.forEach { UpcomingVideoReminderWorker.restore(context, it) }
            drop.forEach { preferences.setUpcomingVideoReminder(it.videoId, enabled = false) }
            if (scrobbleStore
                    .current()
                    .pending.values
                    .sum() > 0
            ) {
                ScrobbleWorker.enqueue(context)
            }
            WellbeingAlarms.arm(context, settings)
        }

        companion object {
            /** A premiere this long past its start is over; a reminder for it would only arrive late. */
            private const val STALE_AFTER_MS = 6 * 60 * 60 * 1000L

            internal fun remindersToRestore(
                reminders: List<UpcomingReminder>,
                nowMs: Long,
            ): Pair<List<UpcomingReminder>, List<UpcomingReminder>> = reminders.partition { it.releaseTimeMs >= nowMs - STALE_AFTER_MS }
        }
    }
