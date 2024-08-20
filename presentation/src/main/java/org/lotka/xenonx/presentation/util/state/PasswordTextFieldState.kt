package org.lotka.xenonx.presentation.util.state

import org.lotka.xenonx.presentation.util.error.Error

data class PasswordTextFieldState(
    val text : String = "",
    val error: Error? = null,
    val isPasswordVisible : Boolean = false
)
