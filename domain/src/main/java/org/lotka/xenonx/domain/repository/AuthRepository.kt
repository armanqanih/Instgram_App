package org.lotka.xenonx.domain.repository

import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import org.lotka.xenonx.domain.util.Resource

interface AuthRepository{

    suspend fun registerUser(email: String, password: String):Flow<Resource<AuthResult>>

    suspend fun loginUser(email: String, password: String):Flow<Resource<AuthResult>>



}

