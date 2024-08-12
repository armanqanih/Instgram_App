package org.lotka.xenonx.presentation.screen.login

data class LoginState (
    val userName: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val userNameError: String = "",
    val passwordError: String = "",
    val showPassword: Boolean = false,
    val error: String? = null
)