package org.lotka.xenonx.domain.usecase

import org.lotka.xenonx.domain.repository.AuthRepository
import org.lotka.xenonx.domain.util.result.RegisterResult
import org.lotka.xenonx.domain.util.ValidationUtil
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(name:String,email: String, password: String): RegisterResult {
        val emailError = ValidationUtil.validateEmail(email)
        val passwordError = ValidationUtil.validatePassword(password)
        val usernameError = ValidationUtil.validateUserName(name)

        if (emailError != null || passwordError !=null || usernameError !=null ) {
            return RegisterResult(
                emailError = emailError,
                passwordError = passwordError,
                userNameError = usernameError)
            }


        val result = repository.registerUser(name.trim(),email.trim(), password)
        return RegisterResult(result = result)

    }
}