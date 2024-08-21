package org.lotka.xenonx.domain.usecase

import android.util.Patterns
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import org.lotka.xenonx.domain.repository.AuthRepository
import org.lotka.xenonx.domain.util.Constants.MIN_USERNAME_LENGTH
import org.lotka.xenonx.domain.util.error.RegisterResult
import org.lotka.xenonx.domain.util.Resource
import org.lotka.xenonx.domain.util.ValidationUtil
import org.lotka.xenonx.domain.util.error.AuthError
import java.util.regex.Pattern
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(name:String,email: String, password: String): RegisterResult {
        val emailError = ValidationUtil.validateEmail(email)
        val passwordError = ValidationUtil.validatePassword(password)
        val usernameError = ValidationUtil.validateUserName(name)

        val result = repository.registerUser(name,email, password)

        return RegisterResult(
            emailError = emailError,
            passwordError = passwordError,
            userNameError = usernameError,
            result = result
        )
    }
}