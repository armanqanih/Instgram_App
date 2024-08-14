package org.lotka.xenonx.domain.model

data class Comment(
    val commentId: Int = -1,
    val userName: String = "",
    val profilePictureUrl: String = "",
    val timeStamp: Long = System.currentTimeMillis(),
    val comment: String = "",
    val isLiked: Boolean = false,
    val likeCount: Int = 12
)
