package com.saferill.zenstream.ui.extensions

import com.saferill.zenstream.api.obj.Comment

fun List<Comment>.filterNonEmptyComments(): List<Comment> {
    return filter { !it.commentText.isNullOrEmpty() }
}
