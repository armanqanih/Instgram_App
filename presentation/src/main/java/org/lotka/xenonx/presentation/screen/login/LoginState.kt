package org.lotka.xenonx.presentation.screen.login

data class LoginState (
    val isLoading: Boolean = false,
    val error: String? = null
)