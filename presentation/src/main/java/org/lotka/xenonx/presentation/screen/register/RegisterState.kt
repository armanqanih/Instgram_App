package org.lotka.xenonx.presentation.screen.register

data class RegisterState (
    val userName: String = "",
    val userNameError: String = "",
    val password: String = "",
    val passwordError: String = "",
    val email: String = "",
    val emailError: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)