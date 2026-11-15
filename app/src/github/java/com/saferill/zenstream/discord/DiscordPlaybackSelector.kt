package com.saferill.zenstream.discord

class DiscordPlaybackSelector {
    fun select(
        short: PlaybackSnapshot?,
        video: PlaybackSnapshot?,
        music: PlaybackSnapshot?,
    ): PlaybackSnapshot? = sequenceOf(short, video, music)
        .filterNotNull()
        .firstOrNull { snapshot -> snapshot.isPlaying && snapshot.mediaId.isNotBlank() }
}
