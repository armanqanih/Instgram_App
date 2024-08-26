package org.lotka.xenonx.domain.repository.profile

import kotlinx.coroutines.flow.Flow
import org.lotka.xenonx.domain.model.ProfileResponse
import org.lotka.xenonx.domain.util.Resource

interface ProfileRepository {

    suspend fun getProfile(userId: String): Flow<Resource<ProfileResponse>>

}