package org.lotka.xenonx.domain.util

import android.util.Patterns
import org.lotka.xenonx.domain.util.Constants.MIN_USERNAME_LENGTH
import org.lotka.xenonx.domain.util.error.AuthError
import java.util.regex.Pattern

object ValidationUtil {

    fun validateEmail (email: String): AuthError? {
        val  trimmedEmail = email.trim()
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$"
        if (trimmedEmail.isBlank()) {
            return  AuthError.FieldEmpty
        }
        if (!Pattern.compile(emailRegex).matcher(trimmedEmail).matches()) {
            return AuthError.InvalidEmail
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
           return AuthError.InvalidEmail
        }
       return null
    }

    fun validateUserName (userName: String): AuthError? {
        val  trimmedUserName = userName.trim()

        if (trimmedUserName.isBlank()) {
            return AuthError.FieldEmpty
        }
        if (trimmedUserName.length < MIN_USERNAME_LENGTH) {
            return AuthError.InputTooShort
        }
       return null
}


    fun validatePassword (password: String): AuthError? {

        if (password.isBlank()) {
            return AuthError.FieldEmpty
        }
        if (password.length < MIN_USERNAME_LENGTH) {
          return AuthError.InputTooShort
        }
        val hasCapitalLetter = password.any { it.isUpperCase() }
        val hasNumber = password.any { it.isDigit() }
        if (!hasCapitalLetter || !hasNumber) {
           return AuthError.InvalidPassword
        }
        return null
    }


}