package org.lotka.xenonx.domain.usecase

import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import org.lotka.xenonx.domain.repository.AuthRepository
import org.lotka.xenonx.domain.util.Resource
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<Resource<AuthResult>> {
        return repository.loginUser(email, password)
    }
}