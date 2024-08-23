package org.lotka.xenonx.presentation.screen.post

sealed class PostEvent {

    object LoadMorePosts : PostEvent()

    object LoadedPage : PostEvent()

}