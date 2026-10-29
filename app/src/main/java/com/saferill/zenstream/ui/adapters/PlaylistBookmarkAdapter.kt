package com.saferill.zenstream.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import com.saferill.zenstream.R
import com.saferill.zenstream.constants.IntentData
import com.saferill.zenstream.databinding.PlaylistsRowBinding
import com.saferill.zenstream.db.DatabaseHolder
import com.saferill.zenstream.db.obj.PlaylistBookmark
import com.saferill.zenstream.enums.PlaylistType
import com.saferill.zenstream.helpers.ImageHelper
import com.saferill.zenstream.helpers.NavigationHelper
import com.saferill.zenstream.ui.adapters.callbacks.DiffUtilItemCallback
import com.saferill.zenstream.ui.base.BaseActivity
import com.saferill.zenstream.ui.sheets.PlaylistOptionsBottomSheet
import com.saferill.zenstream.ui.viewholders.PlaylistBookmarkViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaylistBookmarkAdapter: ListAdapter<PlaylistBookmark, PlaylistBookmarkViewHolder>(
    DiffUtilItemCallback(
        areItemsTheSame = { oldItem, newItem -> oldItem.playlistId == newItem.playlistId }
    )
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistBookmarkViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PlaylistBookmarkViewHolder(
            PlaylistsRowBinding.inflate(layoutInflater, parent, false)
        )
    }

    private fun showPlaylistOptions(context: Context, bookmark: PlaylistBookmark) {
        val sheet = PlaylistOptionsBottomSheet()
        sheet.arguments = bundleOf(
            IntentData.playlistId to bookmark.playlistId,
            IntentData.playlistName to bookmark.playlistName,
            IntentData.playlistType to PlaylistType.PUBLIC
        )
        sheet.show(
            (context as BaseActivity).supportFragmentManager
        )
    }

    override fun onBindViewHolder(holder: PlaylistBookmarkViewHolder, position: Int) {
        val bookmark = getItem(holder.bindingAdapterPosition)

        with(holder.binding) {
            var isBookmarked = true

            ImageHelper.loadImage(bookmark.thumbnailUrl, playlistThumbnail)
            playlistTitle.text = bookmark.playlistName
            playlistDescription.text = bookmark.uploader
            videoCount.text = bookmark.videos.toString()

            bookmarkPlaylist.setOnClickListener {
                isBookmarked = !isBookmarked
                bookmarkPlaylist.setImageResource(
                    if (isBookmarked) R.drawable.ic_bookmark else R.drawable.ic_bookmark_outlined
                )
                CoroutineScope(Dispatchers.IO).launch {
                    if (!isBookmarked) {
                        DatabaseHolder.Database.playlistBookmarkDao()
                            .deleteById(bookmark.playlistId)
                    } else {
                        DatabaseHolder.Database.playlistBookmarkDao().insert(bookmark)
                    }
                }
            }
            bookmarkPlaylist.isVisible = true

            root.setOnClickListener {
                NavigationHelper.navigatePlaylist(
                    root.context,
                    bookmark.playlistId,
                    PlaylistType.PUBLIC
                )
            }

            root.setOnLongClickListener {
                showPlaylistOptions(root.context, bookmark)
                true
            }
        }
    }
}
