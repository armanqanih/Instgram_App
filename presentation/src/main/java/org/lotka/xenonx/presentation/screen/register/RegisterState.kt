package org.lotka.xenonx.presentation.screen.register

data class RegisterState (
    val userName: String = "",
    val password: String = "",
    val email: String = "",
    val isLoading: Boolean = false,
    val emailError: String = "",
    val userNameError: String = "",
    val passwordError: String = "",
    val error: String? = null
)