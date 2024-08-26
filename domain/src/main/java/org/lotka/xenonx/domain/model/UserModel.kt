package org.lotka.xenonx.domain.model

data class UserModel(
    val userId : String,
    val profilePictureUrl : String,
    val userName:String,
    val description:String,
    val followingCount : Int,
    val followerCount : Int,
    val postCount : Int

)
