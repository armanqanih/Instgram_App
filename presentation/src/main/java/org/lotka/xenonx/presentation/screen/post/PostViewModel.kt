package org.lotka.xenonx.presentation.screen.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

import org.lotka.xenonx.domain.usecase.posts.GetPostsUseCase
import org.lotka.xenonx.domain.usecase.posts.PostUseCases
import org.lotka.xenonx.domain.util.Resource
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val getPostsUseCases: PostUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(PostState())
    val state = _state.asStateFlow()


    init {
        viewModelScope.launch {
        getPosts(page = state.value.page, pageSize = state.value.pageSize)
        }
    }

    private suspend fun getPosts(page: Int, pageSize: Int ) {
        getPostsUseCases.getPosts(page, pageSize).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                  _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(isLoading = false)
                    _state.value = _state.value.copy(
                        posts = result.data ?: emptyList(),
                        page = page + 1,
                        pageSize = pageSize

                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false)
                    _state.value = _state.value.copy(error = result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }
}
