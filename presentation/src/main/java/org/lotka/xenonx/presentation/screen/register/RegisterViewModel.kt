package org.lotka.xenonx.presentation.screen.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.lotka.xenonx.presentation.util.Constants.MIN_PASSWORD_LENGTH
import org.lotka.xenonx.presentation.util.Constants.MIN_USERNAME_LENGTH

import org.lotka.xenonx.presentation.util.UiEvent
import org.lotka.xenonx.presentation.util.error.AuthError
import org.lotka.xenonx.presentation.util.state.PasswordTextFieldState
import org.lotka.xenonx.presentation.util.state.StandardTextFieldState
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(

) : ViewModel() {

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
                    validateUserName(userNameState.value.text)
                    validateEmail(emailState.value.text)
                    validatePassword(passwordState.value.text)
                    _eventFlow.emit(UiEvent.ShowSnakeBar("You have successfully registered"))
                }
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
        if (!capitalLetterInPassword || numberInPassword) {
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

