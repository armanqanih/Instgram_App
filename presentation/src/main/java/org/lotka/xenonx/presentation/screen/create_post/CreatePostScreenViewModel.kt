package org.lotka.xenonx.presentation.screen.create_post

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CreatePostScreenViewModel @Inject constructor(

):ViewModel() {


    private val _state = MutableStateFlow(CreatePostState())
    val state = _state.asStateFlow()


    fun onEvent(event : CreatePostEvent){
        when(event){
            is CreatePostEvent.DescriptionOfPostChange -> {
                _state.value = _state.value.copy(
                    descriptionOfPost = event.descriptionOfPost)
            }
        }
    }



}