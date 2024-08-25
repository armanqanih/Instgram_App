package org.lotka.xenonx.domain.model

data class User (
    val id :String  ,
    val email : String  ,
    val username : String  ,
    val password : String  ,
    val imageUrl : String  ,
    val bannerUrl : String  ,
    val bio : String  ,
    val skills : List<String> =  emptyList()  ,
    val githubUrl : String? ,
    val InstagramUrl : String ?,
    val LinkedInUrl : String  ?

)