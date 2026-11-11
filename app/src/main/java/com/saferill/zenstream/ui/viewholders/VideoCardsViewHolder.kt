package com.saferill.zenstream.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.saferill.zenstream.databinding.AllCaughtUpRowBinding
import com.saferill.zenstream.databinding.TrendingRowBinding

class VideoCardsViewHolder : RecyclerView.ViewHolder {
    var trendingRowBinding: TrendingRowBinding? = null
    var allCaughtUpBinding: AllCaughtUpRowBinding? = null

    constructor(binding: TrendingRowBinding) : super(binding.root) {
        trendingRowBinding = binding
    }

    constructor(binding: AllCaughtUpRowBinding) : super(binding.root) {
        allCaughtUpBinding = binding
    }
}
