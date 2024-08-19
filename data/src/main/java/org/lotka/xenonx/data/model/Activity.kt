package org.lotka.xenonx.data.model



data class Activity(
    val id :String  ,
    val parentId :String ,
    val toUserId :String ,
    val byUserId :String ,
    val type :Int ,
    val timeStamp :Long

)
