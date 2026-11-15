package com.saferill.zenstream.ui.screens.music

import com.saferill.zenstream.data.local.LYRICS_ALIGN_CENTER
import com.saferill.zenstream.data.local.LyricsAnimationStyle
import com.saferill.zenstream.data.local.LyricsBackgroundStyle
import com.saferill.zenstream.data.local.LyricsGlow
import com.saferill.zenstream.data.lyrics.LyricsCandidate
import com.saferill.zenstream.data.lyrics.LyricsEntry
import com.saferill.zenstream.data.music.model.MusicTrack
import com.saferill.zenstream.player.RepeatMode

data class MusicPlayerUiState(
    val currentTrack: MusicTrack? = null,
    val isPlaying: Boolean = false,
    val isBuffering: Boolean = false,
    val duration: Long = 0,
    val queue: List<MusicTrack> = emptyList(),
    val autoplaySuggestions: List<MusicTrack> = emptyList(),
    val currentQueueIndex: Int = 0,
    val shuffleEnabled: Boolean = false,
    val repeatMode: RepeatMode = RepeatMode.OFF,
    val isLiked: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val lyrics: String? = null,
    val syncedLyrics: List<LyricsEntry> = emptyList(),
    val isLyricsLoading: Boolean = false,
    val playingFrom: String = "",
    val endlessRadioEnabled: Boolean = true,
    val relatedContent: List<MusicTrack> = emptyList(),
    val isRelatedLoading: Boolean = false,
    val isRadioLoading: Boolean = false,
    val downloadedTrackIds: Set<String> = emptySet(),
    val lyricsProviderName: String = "",
    val lyricsSyncOffsetMs: Long = 0L,
    val lyricsTextAlign: String = LYRICS_ALIGN_CENTER,
    val lyricsShowTranslation: Boolean = true,
    val lyricsShowRomanization: Boolean = true,
    val lyricsAutoRomanize: Boolean = false,
    val lyricsRomanizationOnly: Boolean = false,
    val lyricsKeepScreenOn: Boolean = false,
    val lyricsBlurOtherLines: Boolean = true,
    val lyricsAnimationStyle: LyricsAnimationStyle = LyricsAnimationStyle.CADENCE,
    val lyricsGlow: LyricsGlow = LyricsGlow.NORMAL,
    val lyricsBackground: LyricsBackgroundStyle = LyricsBackgroundStyle.SOLID,
    val lyricsCandidates: List<LyricsCandidate> = emptyList(),
    val isBrowsingLyrics: Boolean = false,
)
