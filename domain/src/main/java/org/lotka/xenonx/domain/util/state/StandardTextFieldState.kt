package org.lotka.xenonx.domain.util.state

import org.lotka.xenonx.domain.util.error.Error


data class StandardTextFieldState(
    val text : String = "",
    val error: Error? = null
)