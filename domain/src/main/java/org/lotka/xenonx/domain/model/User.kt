package org.lotka.xenonx.domain.model

data class ProfileResponse (
    val id:String ,
    val username : String  ,
    val profileImageUrl : String  ,
    val bannerUrl : String  ,
    val bio : String  ,
    val topSkillsUrl : List<String> =  emptyList()  ,
    val githubUrl : String? ,
    val LinkedInUrl : String  ?,
    val isOwenProfile : Boolean  ,
    val isFollowing : Boolean  ,
    val followingCount : Int  ,
    val followerCount : Int  ,
    val postCount : Int


)