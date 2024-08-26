package org.lotka.xenonx.presentation.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.lotka.xenonx.domain.usecase.auth.RegisterUserUseCase
import org.lotka.xenonx.domain.util.Resource
import org.lotka.xenonx.presentation.util.UiEvent
import org.lotka.xenonx.domain.util.state.PasswordTextFieldState
import org.lotka.xenonx.domain.util.state.StandardTextFieldState
import org.lotka.xenonx.presentation.ui.navigation.ScreensNavigation
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    private val _userNameState = MutableStateFlow(StandardTextFieldState())
    val userNameState = _userNameState.asStateFlow()

    private val _emailState = MutableStateFlow(StandardTextFieldState())
    val emailState = _emailState.asStateFlow()

    private val _passwordState = MutableStateFlow(PasswordTextFieldState())
    val passwordState = _passwordState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()




    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.EnterPassword -> {
                _passwordState.value = _passwordState.value.copy(
                    text = event.password
                )
            }

            is RegisterEvent.EnterUserName -> {

                _userNameState.value = _userNameState.value.copy(
                    text = event.userName
                )
            }

            is RegisterEvent.ShowSnakeBar -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.ShowSnakeBar(event.message))
                }
            }

            is RegisterEvent.EnterEmail -> {
                _emailState.value = _emailState.value.copy(
                    text = event.email
                )
            }
            RegisterEvent.IsPasswordVisibility -> {
                _passwordState.value = _passwordState.value.copy(
                    isPasswordVisible = !passwordState.value.isPasswordVisible
                )
            }

            is RegisterEvent.Register -> {
                viewModelScope.launch {
                    register()
                }
            }


        }
    }


    private suspend fun register() {

        _userNameState.value = _userNameState.value.copy(error = null)
        _emailState.value = _emailState.value.copy(error = null)
        _passwordState.value = _passwordState.value.copy(error = null)

        _state.value = _state.value.copy(isLoading = true)

        val username = userNameState.value.text
        val email = emailState.value.text
        val password = passwordState.value.text

        // Call the use case to register the user
        val registerResult = registerUserUseCase(username, email, password)

        // Update the UI with potential errors
        if (registerResult.userNameError != null) {
            _userNameState.value = _userNameState.value.copy(
                error = registerResult.userNameError)
        }
        if (registerResult.emailError != null) {
            _emailState.value = _emailState.value.copy(
                error = registerResult.emailError)
        }
        if (registerResult.passwordError != null) {
            _passwordState.value = _passwordState.value.copy(
                error = registerResult.passwordError)
        }


        // If there are no errors, proceed with registration

            _state.value = _state.value.copy(isLoading = false)
            try {
                // Collect the result from the use case
                registerResult.result?.collect{ result ->
                    when (result) {
                        is Resource.Success -> {
                            // Set loading to false and handle success
                            _state.value = _state.value.copy(isLoading = false)
                            result.data?.let {
                                _eventFlow.emit(UiEvent.ShowSnakeBar("You have successfully registered"))
                                _eventFlow.emit(UiEvent.Navigate(ScreensNavigation.LoginScreen.route))
                            }


                        }
                        is Resource.Error -> {
                            // Set loading to false and handle error
                            _state.value = _state.value.copy(isLoading = false)
                            _eventFlow.emit(UiEvent.ShowSnakeBar(result.message ?: "An unexpected error occurred"))
                        }
                        is Resource.Loading -> {
                            // Set loading to true
                            _state.value = _state.value.copy(isLoading = true)
                        }
                        null -> {
                            _state.value = _state.value.copy(isLoading = false)
                        }
                    }
                }
            } catch (e: Exception) {
                // Handle any unexpected exceptions
                _state.value = _state.value.copy(isLoading = false)
                _eventFlow.emit(UiEvent.ShowSnakeBar("An internal error occurred: ${e.localizedMessage}"))
            }
        }




    }










