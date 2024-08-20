package org.lotka.xenonx.presentation.screen.register

data class RegisterState (
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError : String? = ""
)