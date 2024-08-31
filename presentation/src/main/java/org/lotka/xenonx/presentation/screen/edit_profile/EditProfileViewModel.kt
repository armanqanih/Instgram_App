package org.lotka.xenonx.presentation.screen.edit_profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.lotka.xenonx.domain.model.UserModel
import org.lotka.xenonx.domain.usecase.profile.ProfileUseCases
import org.lotka.xenonx.domain.util.Resource
import org.lotka.xenonx.domain.util.state.StandardTextFieldState
import org.lotka.xenonx.presentation.util.UiEvent
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    savedStateHandle: SavedStateHandle
):ViewModel(){

  private val _state = MutableStateFlow(EditProfileState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {

        savedStateHandle.get<String>("userId")?.let { userId ->
                 getSkills()
                getProfile(userId)
        }
    }

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

            is EditProfileEvent.CropBannerImage -> {
              _state.value = _state.value.copy(bannerImageUri = event.uri)
            }
            is EditProfileEvent.CropProfilePicture ->{
                _state.value = _state.value.copy(profileImageUri = event.uri)
            }
            is EditProfileEvent.SetSkillSelected -> {
               val result = profileUseCases.setSkillsSelected.invoke(
                   selectedSkill = _state.value.selectedSkills,
                   selectedToToggle = event.skill
               )
                viewModelScope.launch {
                when(result){
                    is Resource.Error -> {
                        _eventFlow.emit(UiEvent.ShowSnakeBar(result.message ?: "Unknown error"))
                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {

                        _state.value = _state.value.copy(selectedSkills = result.data ?: run {
                            _eventFlow.emit(UiEvent.ShowSnakeBar("Unknown error"))
                            return@launch
                        })
                    }

                    }
                }


            }
            EditProfileEvent.UpdateProfile ->{
                updateProfile()

            }
        }
    }

    private fun updateProfile() {

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            profileUseCases.updateProfile.invoke(
                updateProfileData = UserModel(
                    userId = _state.value.profile?.userId ?: "",
                    username = _state.value.userNameState.text,
                    profileImageUrl = _state.value.profile?.profileImageUrl ?: "",
                    bannerUrl = _state.value.profile?.bannerUrl ?: "",
                    bio = _state.value.bioState.text,
                    skills = _state.value.selectedSkills,
                    githubUrl = _state.value.githubTextState.text,
                    linkedInUrl = _state.value.linkedInTextState.text,

                ), bannerImageUri = _state.value.bannerImageUri,
                    profileImageUri = _state.value.profileImageUri
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(isLoading = false)
                        _eventFlow.emit(UiEvent.ShowSnakeBar("Profile Updated Successfully"))

                            _eventFlow.emit(UiEvent.NavigateUp)

                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(isLoading = false)
                        _eventFlow.emit(UiEvent.ShowSnakeBar(result.message ?: "Unknown error"))
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }}
        }



    }


    private fun getProfile(userId : String) {
        viewModelScope.launch {

            _state.value = _state.value.copy(isLoading = true)

            profileUseCases.getProfile.invoke(userId).collect {result->
                when(result){
                    is Resource.Success -> {
                       val profile = result.data?: kotlin.run {
                           _eventFlow.emit(UiEvent.ShowSnakeBar("Profile Not Found"))
                            null
                       }
                        _state.value = _state.value.copy(
                            userNameState = StandardTextFieldState(text = profile?.username ?: ""),
                            githubTextState = StandardTextFieldState(text = profile?.githubUrl ?: ""),
                            linkedInTextState = StandardTextFieldState(text = profile?.linkedInUrl ?: ""),
                            bioState = StandardTextFieldState(text = profile?.bio ?: ""),
                            selectedSkills = profile?.skills ?: emptyList(),
                            profile = profile,
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(isLoading = false)
                        _eventFlow.emit(UiEvent.ShowSnakeBar(result.message ?: "Unknown error"))
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }

                }
            }
        }
    }


    private fun getSkills(){
        viewModelScope.launch {
            profileUseCases.getSkills.invoke().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            skills = result.data ?: kotlin.run {
                                _eventFlow.emit(UiEvent.ShowSnakeBar("Skills Not Found"))
                                return@collect
                            }
                        )
                    }

                    is Resource.Error -> {
                        _eventFlow.emit(UiEvent.ShowSnakeBar(result.message ?: "Unknown error"))
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }

                }


            }}}


        }