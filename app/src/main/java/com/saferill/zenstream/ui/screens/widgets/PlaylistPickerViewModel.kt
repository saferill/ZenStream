package com.saferill.zenstream.ui.screens.widgets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.saferill.zenstream.widget.playlist.PlaylistWidgetSource
import com.saferill.zenstream.widget.playlist.WidgetPlaylist
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PlaylistPickerViewModel
    @Inject
    constructor(
        source: PlaylistWidgetSource,
    ) : ViewModel() {
        val playlists: StateFlow<List<WidgetPlaylist>?> =
            source.options().stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                null,
            )
    }
