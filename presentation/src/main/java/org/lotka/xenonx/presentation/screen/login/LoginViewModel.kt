package org.lotka.xenonx.presentation.screen.login

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.lotka.xenonx.domain.usecase.auth.LoginUserUseCase
import org.lotka.xenonx.domain.util.Resource

import org.lotka.xenonx.presentation.util.TestTag.IS_LOGIN_PREFERENCES

import org.lotka.xenonx.presentation.util.UiEvent

import org.lotka.xenonx.domain.util.state.PasswordTextFieldState
import org.lotka.xenonx.domain.util.state.StandardTextFieldState
import org.lotka.xenonx.presentation.ui.navigation.ScreensNavigation
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUserUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _emailState = MutableStateFlow(StandardTextFieldState())
    val emailState = _emailState.asStateFlow()

    private val _passwordState = MutableStateFlow(PasswordTextFieldState())
    val passwordState = _passwordState.asStateFlow()

    fun saveLoginStatus(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean(IS_LOGIN_PREFERENCES, isLoggedIn).apply()
    }

    fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(IS_LOGIN_PREFERENCES, false)
    }


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
                viewModelScope.launch {
                    login()
                }
            }

            LoginEvent.IsPasswordVisibility -> {
                _passwordState.value = _passwordState.value.copy(
                    isPasswordVisible = !passwordState.value.isPasswordVisible
                )
            }
        }
    }


    private suspend fun login() {
        _emailState.value = _emailState.value.copy(error = null)
        _passwordState.value = _passwordState.value.copy(error = null)

        _state.value = _state.value.copy(isLoading = true)


        val email = emailState.value.text
        val password = passwordState.value.text

        // Call the use case to register the user
        val loginResult = loginUseCase(email, password)

        // Update the UI with potential errors

        if (loginResult.emailError != null) {
            _emailState.value = _emailState.value.copy(
                error = loginResult.emailError)
        }
        if (loginResult.passwordError != null) {
            _passwordState.value = _passwordState.value.copy(
                error = loginResult.passwordError)
        }

        _state.value = _state.value.copy(isLoading = true)

        try {
            // Collect the result from the use case
            loginUseCase(email, password).result?.collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(isLoading = false)
                        result.data?.let {
                            // Emit an event for successful login
                            saveLoginStatus(true)
                            _eventFlow.emit(UiEvent.Navigate(ScreensNavigation.HomeScreen.route))
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

