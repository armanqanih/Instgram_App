package org.lotka.xenonx.presentation.util.state

import org.lotka.xenonx.presentation.util.error.Error


data class StandardTextFieldState(
    val text : String = "",
    val error: Error? = null
)