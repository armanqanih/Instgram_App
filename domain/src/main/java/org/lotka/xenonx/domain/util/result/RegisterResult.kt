package org.lotka.xenonx.domain.util.result

import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import org.lotka.xenonx.domain.util.Resource
import org.lotka.xenonx.domain.util.error.AuthError

data class RegisterResult(
    val emailError: AuthError? = null,
    val userNameError: AuthError? = null,
    val passwordError: AuthError? = null,
    val result : Flow<Resource<AuthResult>>? = null
)