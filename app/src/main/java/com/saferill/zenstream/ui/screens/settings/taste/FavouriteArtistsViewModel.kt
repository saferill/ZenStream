package com.saferill.zenstream.ui.screens.settings.taste

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.saferill.zenstream.data.recommendation.music.FavouriteArtistCatalog
import com.saferill.zenstream.data.recommendation.music.FavouriteArtistPicker
import com.saferill.zenstream.data.recommendation.music.FavouriteArtistsStore
import javax.inject.Inject

@HiltViewModel
internal class FavouriteArtistsViewModel
    @Inject
    constructor(
        store: FavouriteArtistsStore,
        catalog: FavouriteArtistCatalog,
    ) : ViewModel() {
        val picker = FavouriteArtistPicker(viewModelScope, store, catalog)
    }
