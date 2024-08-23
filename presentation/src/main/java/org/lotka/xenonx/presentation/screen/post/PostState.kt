package org.lotka.xenonx.presentation.screen.post

import org.lotka.xenonx.domain.model.PostModel

data class PostState (
    val isLoadingFirstTime: Boolean = true,
    val isLoadingNewPosts: Boolean = false,
    val error: String = "",

)