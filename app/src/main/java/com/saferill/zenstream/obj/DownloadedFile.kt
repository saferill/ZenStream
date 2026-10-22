package com.saferill.zenstream.obj

import android.graphics.Bitmap
import com.saferill.zenstream.api.obj.Streams

data class DownloadedFile(
    val name: String,
    val size: Long,
    var metadata: Streams? = null,
    var thumbnail: Bitmap? = null
)
