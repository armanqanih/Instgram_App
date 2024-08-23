package org.lotka.xenonx.presentation.screen.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


import org.lotka.xenonx.domain.usecase.posts.PostUseCases
import javax.inject.Inject


@HiltViewModel
class PostViewModel @Inject constructor(
    private val getPostsUseCases: PostUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(PostState())
    val state = _state.asStateFlow()


   val posts = getPostsUseCases.getPosts.invoke().cachedIn(viewModelScope)

    fun onEvent(event: PostEvent) {
        when(event){
            PostEvent.LoadMorePosts -> {
             _state.value = _state.value.copy(
                 isLoadingNewPosts = true
             )

            }

            PostEvent.LoadedPage -> {
                _state.value = _state.value.copy(
                    isLoadingFirstTime = false,
                    isLoadingNewPosts = false
                )
            }
        }
    }

}
