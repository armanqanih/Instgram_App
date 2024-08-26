package org.lotka.xenonx.domain.model

data class UserItem(
    val userId: String,
    val userName: String,
    val profilePictureUrl: String,
    val bio: String,
    val following: Int,

)
