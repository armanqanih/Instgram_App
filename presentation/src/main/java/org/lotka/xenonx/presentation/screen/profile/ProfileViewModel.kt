package org.lotka.xenonx.presentation.screen.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.lotka.xenonx.domain.model.ProfileResponse
import org.lotka.xenonx.domain.usecase.profile.GetProfileUseCase
import org.lotka.xenonx.domain.usecase.profile.ProfileUseCases
import org.lotka.xenonx.domain.util.Resource
import org.lotka.xenonx.presentation.util.UiEvent
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel@Inject constructor(
  private val profileUseCases: ProfileUseCases,
    savedStateHandle: SavedStateHandle
):ViewModel() {

    private val _toolbarState = MutableStateFlow(ProfileToolbarState())
     val toolbarState = _toolbarState.asStateFlow()

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    private val _profileState = mutableStateOf<ProfileResponse?>(null)
    val profileState: State<ProfileResponse?> = _profileState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
       savedStateHandle.get<String>("userId")?.let { userId ->
           fetchProfile(userId)
       }
    }



    private fun fetchProfile(userId : String) {
        viewModelScope.launch {

            _state.value = _state.value.copy(isLoading = true)

            profileUseCases.getProfile.invoke(userId).collect {result->
                 when(result){
                     is Resource.Success -> {
                       _state.value = _state.value.copy(
                           profile = result.data,
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







    fun setToolbarOffsetY(offset : Float){
       _toolbarState.value = _toolbarState.value.copy(
           toolbarOffsetY = offset
       )
    }

    fun setExpandedRatio(offset : Float){
        _toolbarState.value = _toolbarState.value.copy(
            expandedRatio = offset
        )
    }




}