package org.lotka.xenonx.presentation.screen.create_post

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.lotka.xenonx.domain.usecase.posts.PostUseCases
import org.lotka.xenonx.domain.util.Resource
import org.lotka.xenonx.domain.util.state.StandardTextFieldState
import org.lotka.xenonx.presentation.util.UiEvent
import java.io.File
import javax.inject.Inject
@HiltViewModel
class CreatePostScreenViewModel @Inject constructor(
    private val postUseCases: PostUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(CreatePostState())
    val state: StateFlow<CreatePostState> = _state.asStateFlow()



    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event:CreatePostEvent) {
        when (event) {
            is CreatePostEvent.EnteredDescription -> {
                _state.value = _state.value.copy(
                    description =
                    StandardTextFieldState(text = event.description)
                )
            }


            CreatePostEvent.SendPost -> {
                postContent()
            }

            is CreatePostEvent.CropImage -> {
                _state.value = _state.value.copy(imageUri = event.cropUri)

            }


        }

    }


    private fun postContent() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            delay(2000L)

            val description = state.value.description
            val imageUri = state.value.imageUri


            if (description.text.isNotBlank() && imageUri != null) {
                val result = try {
                    val imageUrl = postUseCases.uploadImage(imageUri)
                    postUseCases.createPost(description.text, imageUrl)
                } catch (e: Exception) {
                    Resource.Error(e.localizedMessage ?: "Unknown error")
                }

                when (result) {
                    is Resource.Success -> {
                        _eventFlow.emit(UiEvent.Navigate)
                    }
                    is Resource.Error -> {
                        _eventFlow.emit(UiEvent.ShowSnakeBar(result.message ?: "Unknown error"))
                    }
                    is Resource.Loading -> _state.value = _state.value.copy(isLoading = true)
                    null -> {
                        _eventFlow.emit(UiEvent.ShowSnakeBar("Null result"))
                    }
                }
            } else {
                _eventFlow.emit(UiEvent.ShowSnakeBar("Description or Image cannot be empty."))
            }
        }
    }
}


