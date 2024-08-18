package org.lotka.xenonx.presentation.screen.create_post

sealed class CreatePostEvent {

    data class DescriptionOfPostChange(val descriptionOfPost:String):CreatePostEvent()
}