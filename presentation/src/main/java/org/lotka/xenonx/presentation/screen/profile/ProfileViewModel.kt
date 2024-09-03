package org.lotka.xenonx.presentation.screen.profile



import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.lotka.xenonx.domain.model.UserModel
import org.lotka.xenonx.domain.usecase.GetOwnUserIdUseCase

import org.lotka.xenonx.domain.usecase.profile.ProfileUseCases
import org.lotka.xenonx.domain.util.Resource
import org.lotka.xenonx.presentation.util.UiEvent
import timber.log.Timber
import javax.inject.Inject
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    private val getOwnUserId: GetOwnUserIdUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _toolbarState = MutableStateFlow(ProfileToolbarState())
    val toolbarState = _toolbarState.asStateFlow()

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    val posts = profileUseCases.getPostsForProfile(
        userId = savedStateHandle.get<String>("userId") ?: ""

    ).cachedIn(viewModelScope)

    fun getProfile(userId: String?) {
        viewModelScope.launch {
//            _state.value = _state.value.copy(isLoading = true) // Start loading
             profileUseCases.getProfile(userId?:getOwnUserId()
             ).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        Timber.tag("ProfileViewModel").d("Profile fetched successfully: %s", result.data)
                        _state.value = _state.value.copy(
                            profile = result.data,
                            isLoading = false // Loading finished
                        )
                    }
                    is Resource.Error -> {
                        Timber.tag("ProfileViewModel")
                            .e("Error fetching profile: %s", result.message)
                        _state.value = _state.value.copy(isLoading = false) // Loading finished
                        _eventFlow.emit(UiEvent.ShowSnakeBar(result.message ?: "Unknown error"))
                    }
                    is Resource.Loading -> {
                        Timber.tag("ProfileViewModel").d("Loading profile data...")
                        // This state is optional. You might already set it before collecting the flow.
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }
        }
    }


    fun setToolbarOffsetY(offset: Float) {
        _toolbarState.value = _toolbarState.value.copy(
            toolbarOffsetY = offset
        )
    }

    fun setExpandedRatio(offset: Float) {
        _toolbarState.value = _toolbarState.value.copy(
            expandedRatio = offset
        )
    }
}


