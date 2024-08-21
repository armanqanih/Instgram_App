package org.lotka.xenonx.presentation.screen.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.lotka.xenonx.domain.usecase.LoginUserUseCase
import org.lotka.xenonx.domain.util.Resource
import org.lotka.xenonx.presentation.util.Constants.MIN_PASSWORD_LENGTH
import org.lotka.xenonx.presentation.util.Constants.MIN_USERNAME_LENGTH

import org.lotka.xenonx.presentation.util.UiEvent
import org.lotka.xenonx.presentation.util.error.AuthError
import org.lotka.xenonx.presentation.util.state.PasswordTextFieldState
import org.lotka.xenonx.presentation.util.state.StandardTextFieldState
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val loginUseCase: LoginUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    private val _emailState = MutableStateFlow(StandardTextFieldState())
    val emailState = _emailState.asStateFlow()

    private val _passwordState = MutableStateFlow(PasswordTextFieldState())
    val passwordState = _passwordState.asStateFlow()



    fun onEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.EnterPassword -> {
                _passwordState.value = _passwordState.value.copy(
                    text = event.password)
            }
            is LoginEvent.EnterEmail -> {
                _emailState.value = _emailState.value.copy(
                    text = event.userName)
            }

            is LoginEvent.ShowSnakeBar -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.ShowSnakeBar(event.message))
                }
            }
            is LoginEvent.Login -> {
                validateAndRegisterUser()
            }

            LoginEvent.IsPasswordVisibility -> {
                _passwordState.value = _passwordState.value.copy(
                    isPasswordVisible = !passwordState.value.isPasswordVisible
                )
            }
        }
    }





    private fun validateAndRegisterUser() {
        val email = _emailState.value.text
        val password = _passwordState.value.text

        validateEmail(email)
        validatePassword(password)

        if (_emailState.value.error == null &&
            _passwordState.value.error == null
        ) {
            loginUser(email, password)

        }
    }


    private fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            try {
                // Collect the result from the use case
                loginUseCase(email, password).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _state.value = _state.value.copy(isLoading = false)
                            result.data?.let {
                                // Emit an event for successful login
                                _eventFlow.emit(UiEvent.Navigate)
                            }
                        }
                        is Resource.Error -> {
                            _state.value = _state.value.copy(isLoading = false)
                            // If error message contains "no user", show "You don't have an account" message
                            val errorMessage = if (result.message?.contains("no user") == true) {
                                "You don't have an account"
                            } else {
                                result.message ?: "An unexpected error occurred"
                            }
                            _eventFlow.emit(UiEvent.ShowSnakeBar(errorMessage))
                        }
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(isLoading = true)
                        }
                    }
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false)
                _eventFlow.emit(UiEvent.ShowSnakeBar("An internal error occurred: ${e.localizedMessage}"))
            }
        }
    }







    private fun validateEmail(email: String) {
        val trimmedEmail = email.trim()
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$"

        if (trimmedEmail.isBlank()) {
            _emailState.value = _emailState.value.copy(error = AuthError.FieldEmpty)
            return
        }

        if (!Pattern.compile(emailRegex).matcher(trimmedEmail).matches()) {
            _emailState.value = _emailState.value.copy(error = AuthError.InvalidEmail)
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailState.value = _emailState.value.copy(error = AuthError.InvalidEmail)
            return
        }


        _emailState.value = _emailState.value.copy(error = null)
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

