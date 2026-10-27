package com.saferill.zenstream.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.ListAdapter
import com.saferill.zenstream.api.PlaylistsHelper
import com.saferill.zenstream.constants.IntentData
import com.saferill.zenstream.databinding.CarouselPlaylistThumbnailBinding
import com.saferill.zenstream.helpers.ImageHelper
import com.saferill.zenstream.helpers.NavigationHelper
import com.saferill.zenstream.ui.adapters.callbacks.DiffUtilItemCallback
import com.saferill.zenstream.ui.base.BaseActivity
import com.saferill.zenstream.ui.sheets.PlaylistOptionsBottomSheet
import com.saferill.zenstream.ui.viewholders.CarouselPlaylistViewHolder

data class CarouselPlaylist(
    val id: String,
    val title: String?,
    val thumbnail: String?
)

class CarouselPlaylistAdapter : ListAdapter<CarouselPlaylist, CarouselPlaylistViewHolder>(
    DiffUtilItemCallback()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CarouselPlaylistViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CarouselPlaylistViewHolder(CarouselPlaylistThumbnailBinding.inflate(layoutInflater))
    }

    override fun onBindViewHolder(
        holder: CarouselPlaylistViewHolder,
        position: Int
    ) {
        val item = getItem(position)!!

        with(holder.binding) {
            playlistName.text = item.title
            ImageHelper.loadImage(item.thumbnail, thumbnail)

            val type = PlaylistsHelper.getPlaylistType(item.id)
            root.setOnClickListener {
                NavigationHelper.navigatePlaylist(root.context, item.id, type)
            }

            root.setOnLongClickListener {
                val playlistOptionsDialog = PlaylistOptionsBottomSheet()
                playlistOptionsDialog.arguments = bundleOf(
                    IntentData.playlistId to item.id,
                    IntentData.playlistName to item.title,
                    IntentData.playlistType to type
                )
                playlistOptionsDialog.show((root.context as BaseActivity).supportFragmentManager)

                true
            }
        }
    }
}