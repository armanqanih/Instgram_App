package org.lotka.xenonx.presentation.screen.post

import org.lotka.xenonx.domain.model.PostModel

data class PostState (
    val isLoading: Boolean = false,
    val posts: List<PostModel> = emptyList(),
    val error: String = "",
    val endReached: Boolean = false,
    val page: Int = 0,
    val pageSize: Int = 20
)