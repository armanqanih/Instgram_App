package org.lotka.xenonx.domain.util.state

import org.lotka.xenonx.domain.util.error.Error


data class PasswordTextFieldState(
    val text : String = "",
    val error: Error? = null,
    val isPasswordVisible : Boolean = false
)
