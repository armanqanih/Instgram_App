package org.lotka.xenonx.domain.model

data class UserModel (
    val userId:String,
    val username : String,
    val profileImageUrl : String?=null,
    val bannerUrl : String ? = null,
    val bio : String?=null,
    val skills : List<SkillsModel> =  emptyList(),
    val githubUrl : String? = null,
    val linkedInUrl : String  ? = null,
    val isOwenProfile : Boolean ? = null,
    val isFollowing : Boolean ?=null,
    val followingCount : Int ? = null,
    val followerCount : Int  ? =null,
    val postCount : Int ? = null
    
)