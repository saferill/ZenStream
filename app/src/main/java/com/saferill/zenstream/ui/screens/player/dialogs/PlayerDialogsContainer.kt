package com.saferill.zenstream.ui.screens.player.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saferill.zenstream.data.model.Video
import com.saferill.zenstream.player.EnhancedPlayerManager
import com.saferill.zenstream.player.dlna.DlnaCastManager
import com.saferill.zenstream.player.state.EnhancedPlayerState
import com.saferill.zenstream.ui.components.videoplayer.DlnaDevicePickerDialog
import com.saferill.zenstream.ui.screens.player.VideoPlayerViewModel
import com.saferill.zenstream.ui.screens.player.state.PlayerScreenState
import com.saferill.zenstream.ui.screens.player.state.PlayerSheet
import com.saferill.zenstream.ui.screens.player.state.VideoPlayerPreferencesState
import com.saferill.zenstream.ui.screens.player.state.VideoPlayerUiState
import kotlinx.coroutines.launch

@Composable
internal fun PlayerDialogsContainer(
    screenState: PlayerScreenState,
    playerState: EnhancedPlayerState,
    uiState: VideoPlayerUiState,
    video: Video,
    viewModel: VideoPlayerViewModel,
    prefs: VideoPlayerPreferencesState,
    hostedInSidePanel: Boolean = false,
    mediaSheetExpandedHeight: Dp? = null,
    mediaSheetCollapsedHeight: Dp = 0.dp,
    onMediaSheetProgressChange: (Float) -> Unit = {},
) {
    val playerPreferences = prefs.preferences
    val coroutineScope = rememberCoroutineScope()

    if (screenState.activeSheet == PlayerSheet.Download && !hostedInSidePanel) {
        PlayerDownloadSheetHost(
            video = video,
            uiState = uiState,
            playerState = playerState,
            asSidePanel = false,
            expandedHeight = mediaSheetExpandedHeight,
            collapsedHeight = mediaSheetCollapsedHeight,
            onSheetProgressChange = onMediaSheetProgressChange,
            onDismiss = { screenState.closeSheet() },
        )
    }

    if (screenState.isSettingsOpen && !hostedInSidePanel) {
        PlayerSettingsSheetHost(
            screenState = screenState,
            playerState = playerState,
            uiState = uiState,
            viewModel = viewModel,
            playerPreferences = playerPreferences,
            scope = coroutineScope,
            rememberPlaybackSpeed = prefs.rememberPlaybackSpeed,
            ambientModeEnabled = prefs.ambientModeEnabled,
            ambientGlowEnabled = prefs.ambientGlowEnabled,
            groupedQualitySelectorEnabled = prefs.groupedQualitySelectorEnabled,
            rememberSubtitleLanguage = { language ->
                coroutineScope.launch { playerPreferences.setPreferredSubtitleLanguage(language) }
            },
            asSidePanel = false,
            expandedHeight = mediaSheetExpandedHeight,
            collapsedHeight = mediaSheetCollapsedHeight,
            pipAspectRatio = null,
            onSheetProgressChange = onMediaSheetProgressChange,
            onDismiss = { screenState.closeSheet() },
        )
    }

    if (screenState.activeSheet == PlayerSheet.Dlna) {
        val dlnaDevices by DlnaCastManager.devices.collectAsStateWithLifecycle()
        val isDlnaDiscovering by DlnaCastManager.isDiscovering.collectAsStateWithLifecycle()
        DlnaDevicePickerDialog(
            devices = dlnaDevices,
            isDiscovering = isDlnaDiscovering,
            isCasting = DlnaCastManager.isCasting,
            videoTitle = video.title,
            onDeviceSelected = { device ->
                val playerManager = EnhancedPlayerManager.getInstance()
                val currentPlayerUrl =
                    playerManager
                        .getPlayer()
                        ?.currentMediaItem
                        ?.localConfiguration
                        ?.uri
                        ?.toString()
                val (videoOnlyStreams, muxedStreams) = playerManager.getCastVideoStreams().partition { it.isVideoOnly }
                DlnaCastManager.castStreamInfo(
                    device = device,
                    title = video.title,
                    currentPlayerUrl = currentPlayerUrl,
                    videoOnlyStreams = videoOnlyStreams,
                    audioStreams = playerManager.getCastAudioStreams(),
                    muxedStreams = muxedStreams,
                    durationSeconds = playerManager.getDuration() / 1000,
                )
                screenState.closeSheet()
            },
            onStopCasting = {
                DlnaCastManager.disconnect()
                screenState.closeSheet()
            },
            onDismiss = {
                DlnaCastManager.stopDiscovery()
                screenState.closeSheet()
            },
        )
    }
}
