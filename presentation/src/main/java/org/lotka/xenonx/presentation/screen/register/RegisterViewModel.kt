package org.lotka.xenonx.presentation.screen.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.lotka.xenonx.domain.usecase.RegisterUserUseCase
import org.lotka.xenonx.domain.util.Resource
import org.lotka.xenonx.presentation.util.Constants.MIN_PASSWORD_LENGTH
import org.lotka.xenonx.presentation.util.Constants.MIN_USERNAME_LENGTH

import org.lotka.xenonx.presentation.util.UiEvent
import org.lotka.xenonx.presentation.util.error.AuthError
import org.lotka.xenonx.presentation.util.state.PasswordTextFieldState
import org.lotka.xenonx.presentation.util.state.StandardTextFieldState
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
                    validateAndRegisterUser()
                }
            }


        }
    }


    private fun validateAndRegisterUser() {
        val username = _userNameState.value.text
        val email = _emailState.value.text
        val password = _passwordState.value.text

        validateUserName(username)
        validateEmail(email)
        validatePassword(password)

        if (_userNameState.value.error == null &&
            _emailState.value.error == null &&
            _passwordState.value.error == null
        ) {
            registerUser(email, password)




        }
    }



    private fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            try {
                // Collect the result from the use case
                registerUserUseCase(email, password).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            // Set loading to false and handle success
                            _state.value = _state.value.copy(isLoading = false)
                            result.data?.let {
                                _eventFlow.emit(UiEvent.ShowSnakeBar("You have successfully registered"))
                                _eventFlow.emit(UiEvent.Navigate)

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
                    }
                }
            } catch (e: Exception) {
                // Handle any unexpected exceptions
                _state.value = _state.value.copy(isLoading = false)
                _eventFlow.emit(UiEvent.ShowSnakeBar("An internal error occurred: ${e.localizedMessage}"))
            }
        }
    }



    private fun validateUserName(username: String) {
        val trimmedUserName = username.trim()
        if (trimmedUserName.isBlank()) {
            _userNameState.value = _userNameState.value.copy(
                error = AuthError.FieldEmpty
            )
            return
        }
        if (trimmedUserName.length < MIN_USERNAME_LENGTH) {
            _userNameState.value = _userNameState.value.copy(
                error = AuthError.InputTooShort

            )
            return
        }
        _userNameState.value = _userNameState.value.copy(
            error = null
        )


    }

    private fun validateEmail(email: String) {
        val trimmedEmail = email.trim()
        if (trimmedEmail.isBlank()) {
            _emailState.value = _emailState.value.copy(
                error = AuthError.FieldEmpty
            )
            return
        }
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailState.value = _emailState.value.copy(
                error = AuthError.InvalidEmail
            )
            return
        }
        _emailState.value = _emailState.value.copy(
            error = null
        )


    }

    private fun validatePassword(password: String) {
        if (password.isBlank()) {
            _passwordState.value = _passwordState.value.copy(
                error = AuthError.FieldEmpty
            )
            return
        }
        if (password.length < MIN_PASSWORD_LENGTH) {
            _passwordState.value = _passwordState.value.copy(
                error = AuthError.InputTooShort
            )
            return
        }
        val capitalLetterInPassword = password.any { it.isUpperCase() }
        val numberInPassword = password.any { it.isDigit() }
        if (!capitalLetterInPassword || !numberInPassword) {
            _passwordState.value = _passwordState.value.copy(
                error = AuthError.InvalidPassword
            )
            return
        }

        _passwordState.value = _passwordState.value.copy(
            error = null
        )
    }



}

