package org.lotka.xenonx.data.model


import java.sql.Timestamp

data class Post (

    val id :String  ,
    val userId : String ,
    val imageUrl : String ,
    val description : String ,
    val timestamp: Long



    )