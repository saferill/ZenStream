package com.saferill.zenstream.ui.screens.player

import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.media3.common.util.UnstableApi
import dagger.hilt.android.lifecycle.HiltViewModel
import com.saferill.zenstream.data.localmedia.LocalMediaIds
import com.saferill.zenstream.player.EnhancedPlayerManager
import com.saferill.zenstream.player.stats.PlayerStatsReader
import com.saferill.zenstream.player.stats.PlayerStatsSnapshot
import javax.inject.Inject

/** Reads the player for the stats panel, which only exists while the panel is open. */
@OptIn(UnstableApi::class)
@HiltViewModel
class PlayerStatsViewModel
    @Inject
    constructor(
        private val playerManager: EnhancedPlayerManager,
    ) : ViewModel() {
        internal fun read(
            videoId: String,
            client: String?,
        ): PlayerStatsSnapshot? {
            val player = playerManager.getPlayer() ?: return null
            val kind =
                when {
                    LocalMediaIds.isLocal(videoId) -> "local"
                    player.isCurrentMediaItemLive -> "live"
                    else -> "vod"
                }
            return PlayerStatsReader.read(player, playerManager.statsReadings(), videoId, kind, client)
        }
    }
