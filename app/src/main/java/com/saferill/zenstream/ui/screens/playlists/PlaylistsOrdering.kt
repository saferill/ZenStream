package com.saferill.zenstream.ui.screens.playlists

import com.saferill.zenstream.data.model.PlaylistInfo
import com.saferill.zenstream.data.playlist.PlaylistListOrder
import com.saferill.zenstream.data.playlist.sortedFor
import com.saferill.zenstream.ui.components.library.PlaylistOwnershipFilter

/**
 * Owned playlists lead saved ones in every order except [PlaylistListOrder.CUSTOM], where the
 * viewer's own arrangement decides.
 */
internal fun orderedPlaylists(
    owned: List<PlaylistInfo>,
    saved: List<PlaylistInfo>,
    filter: PlaylistOwnershipFilter,
    order: PlaylistListOrder,
): List<PlaylistInfo> =
    if (order == PlaylistListOrder.CUSTOM) {
        filter.select(owned, saved).sortedFor(order)
    } else {
        filter.select(owned.sortedFor(order), saved.sortedFor(order))
    }
