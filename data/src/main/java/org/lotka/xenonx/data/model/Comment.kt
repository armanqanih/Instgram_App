package org.lotka.xenonx.data.model



data class Comment (

    val id :String  ,
    val userId: String,
    val postId: String,
    val comment: String,
    val timeStamp: Long

    )