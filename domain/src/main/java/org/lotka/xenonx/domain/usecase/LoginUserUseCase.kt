package org.lotka.xenonx.domain.usecase

import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import org.lotka.xenonx.domain.repository.AuthRepository
import org.lotka.xenonx.domain.util.Resource
import org.lotka.xenonx.domain.util.ValidationUtil
import org.lotka.xenonx.domain.util.error.AuthError
import org.lotka.xenonx.domain.util.result.LoginResult
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): LoginResult {

      val emailError = if (email.isEmpty()) AuthError.FieldEmpty else null
      val passwordError = if (password.isEmpty()) AuthError.FieldEmpty else null

        if (emailError != null || passwordError !=null ) {
            return LoginResult(
                emailError = emailError,
                passwordError = passwordError
            )
        }
        val result = repository.loginUser(email, password)
        return LoginResult(
          result = result
        )


    }
}