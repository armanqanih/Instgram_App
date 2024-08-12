package org.lotka.xenonx.domain.model

data class Post(
    val id: Int? = null,
    val userName: String,
    val profileImage: String,
    val postImage: String,
    val description: String,
    val likes: Int,
    val comments: Int,
)
