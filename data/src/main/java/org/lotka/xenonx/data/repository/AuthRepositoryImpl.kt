package org.lotka.xenonx.data.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import ir.pinto.market.data.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import org.lotka.xenonx.domain.repository.AuthRepository
import org.lotka.xenonx.domain.util.Resource
import org.lotka.xenonx.domain.util.UiText
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): AuthRepository {
    override suspend fun registerUser(name:String,email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            try {
                emit(Resource.Loading()) // Emit loading state
                val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                if (result.user != null) {
                     emit(Resource.Success(result)) // Emit success state
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
            }
        }

    }

    override suspend fun loginUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            try {
                emit(Resource.Loading()) // Emit loading state
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                emit(Resource.Success(result)) // Emit success state
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Unknown Error")) // Emit error state
            }
        }
    }


}