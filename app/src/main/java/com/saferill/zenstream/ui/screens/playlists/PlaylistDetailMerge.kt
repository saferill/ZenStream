package com.saferill.zenstream.ui.screens.playlists

import com.saferill.zenstream.R
import com.saferill.zenstream.data.local.PlaylistRepository
import com.saferill.zenstream.data.model.Video

internal suspend fun mergeVideosIntoPlaylist(
    repository: PlaylistRepository,
    videos: List<Video>,
    targetPlaylistId: String,
): PlaylistUiMessage =
    try {
        repository.addVideosToPlaylist(targetPlaylistId, videos)
        val targetInfo = repository.getPlaylistInfo(targetPlaylistId)
        PlaylistUiMessage(
            pluralRes = R.plurals.merge_playlist_success,
            count = videos.size,
            args = listOf(videos.size, targetInfo?.name ?: ""),
        )
    } catch (_: Exception) {
        PlaylistUiMessage(stringRes = R.string.toast_failed_to_merge_playlist)
    }

internal suspend fun createVideoPlaylistAndMerge(
    repository: PlaylistRepository,
    videos: List<Video>,
    name: String,
    description: String,
): PlaylistUiMessage =
    try {
        val playlistId = System.currentTimeMillis().toString()
        repository.createPlaylist(playlistId, name, description, isPrivate = true)
        mergeVideosIntoPlaylist(repository, videos, playlistId)
    } catch (_: Exception) {
        PlaylistUiMessage(stringRes = R.string.toast_failed_to_merge_playlist)
    }
