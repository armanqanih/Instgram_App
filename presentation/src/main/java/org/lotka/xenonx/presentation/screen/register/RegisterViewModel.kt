package org.lotka.xenonx.presentation.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import org.lotka.xenonx.presentation.util.UiEvent
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(

) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: RegisterEvent) {
        when(event) {
            is RegisterEvent.EnterPassword -> {
                _state.value = state.value.copy(
                    password = event.password)
            }
            is RegisterEvent.EnterUserName -> {
                _state.value = state.value.copy(
                    userName = event.userName)
            }
            is RegisterEvent.Register -> {
                viewModelScope.launch {

                    _eventFlow.emit(UiEvent.ShowSnakeBar("You have successfully registered"))
                }
            }
            is RegisterEvent.ShowSnakeBar -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.ShowSnakeBar(event.message))
                }
            }

            is RegisterEvent.EnterEmail -> {
                _state.value = _state.value.copy(
                    email = event.email
                )
            }


        }
    }



    }

