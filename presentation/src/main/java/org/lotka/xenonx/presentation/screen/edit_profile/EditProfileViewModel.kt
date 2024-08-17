package org.lotka.xenonx.presentation.screen.edit_profile

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(


):ViewModel(){

  private val _state = MutableStateFlow(EditProfileState())
    val state = _state.asStateFlow()


    fun onEvent(event: EditProfileEvent){
        when(event){
            is EditProfileEvent.UserNameChange ->{
                _state.value = _state.value.copy(userNameState = event.userNameState)
            }

            is EditProfileEvent.GithubTextChange -> {
                _state.value = _state.value.copy(githubTextState = event.githubTextState)
            }
            is EditProfileEvent.InstagramTextChange -> {
                _state.value = _state.value.copy(instagramTextState = event.instagramTextState)
            }
            is EditProfileEvent.LinkedInTextChange -> {
                _state.value = _state.value.copy(linkedInTextState = event.linkedInTextState)
            }

            is EditProfileEvent.BioTextChange -> {
                _state.value = _state.value.copy(bioState = event.bioTextState)
            }
        }
    }


}