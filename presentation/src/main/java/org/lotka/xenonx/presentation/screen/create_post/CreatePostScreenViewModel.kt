package org.lotka.xenonx.presentation.screen.create_post

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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

            is CreatePostEvent.PickImage -> {
                _state.value = _state.value.copy(imageUri = event.uri)
            }

            CreatePostEvent.SendPost -> {
                postContent()
            }
        }

    }


    fun postContent() {
        println("My PrintLn 1")
        viewModelScope.launch {
            val description = state.value.description
            val imageUri = state.value.imageUri

            if (description.text.isNotBlank() && imageUri != null) {
                _state.value = _state.value.copy(isLoading = true)
                val result = try {
                    val imageUrl = postUseCases.uploadImage(imageUri)
                    println("My PrintLn 2: $imageUrl")
                    postUseCases.createPost(description.text, imageUrl)
                } catch (e: Exception) {
                    println("My PrintLn 3: ${e.localizedMessage}")
                    Resource.Error(e.localizedMessage ?: "Unknown error")
                }

                when (result) {
                    is Resource.Error -> {
                        println("My PrintLn 4")
                        _eventFlow.emit(UiEvent.ShowSnakeBar("Oops, something went wrong."))
                    }

                    is Resource.Loading -> _state.value = _state.value.copy(isLoading = true)
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            result = result.data,
                        )
                        _eventFlow.emit(UiEvent.Navigate)
                        println("My PrintLn 5")
                    }

                    null -> {
                        _eventFlow.emit(UiEvent.ShowSnakeBar("Null result"))
                        println("My PrintLn 6")
                    }
                }
            } else {
                _eventFlow.emit(UiEvent.ShowSnakeBar("Description or Image cannot be empty."))
                println("My PrintLn 7")
            }
        }
    }


}
