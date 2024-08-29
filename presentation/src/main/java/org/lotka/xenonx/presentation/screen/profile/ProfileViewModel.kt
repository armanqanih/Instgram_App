package org.lotka.xenonx.presentation.screen.profile



import android.util.Log
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
import org.lotka.xenonx.presentation.util.UiEvent
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _toolbarState = MutableStateFlow(ProfileToolbarState())
    val toolbarState = _toolbarState.asStateFlow()

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<String>("userId")?.let { userId ->
            Log.d("ProfileViewModel", "User ID received: $userId")
            getProfile(userId)
        }
    }

    private fun getProfile(userId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            profileUseCases.getProfile(userId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        Log.d("ProfileViewModel", "Profile fetched successfully: ${result.data}")
                        _state.value = _state.value.copy(
                            profile = result.data ?: UserModel("0",
                                "armansherwanii", "", "", "", emptyList(), "", "", false, false, 0, 0, 0),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        Log.e("ProfileViewModel", "Error fetching profile: ${result.message}")
                        _state.value = _state.value.copy(isLoading = false)
                        _eventFlow.emit(UiEvent.ShowSnakeBar(result.message ?: "Unknown error"))
                    }
                    is Resource.Loading -> {
                        Log.d("ProfileViewModel", "Loading profile data...")
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

