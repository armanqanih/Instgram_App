package org.lotka.xenonx.presentation.screen.create_post

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.lotka.xenonx.presentation.util.state.StandardTextFieldState
import javax.inject.Inject

@HiltViewModel
class CreatePostScreenViewModel @Inject constructor(

):ViewModel() {



    private val _descriptionState = MutableStateFlow(StandardTextFieldState())
    val descriptionState = _descriptionState.asStateFlow()

    fun setDescription(description: String){
        _descriptionState.value = _descriptionState.value.copy(
            text = description
        )
    }



}
