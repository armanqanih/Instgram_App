package org.lotka.xenonx.domain.usecase.profile

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.lotka.xenonx.domain.model.PostModel
import org.lotka.xenonx.domain.model.ProfileResponse
import org.lotka.xenonx.domain.repository.post.PostRepository
import org.lotka.xenonx.domain.repository.profile.ProfileRepository
import org.lotka.xenonx.domain.util.Resource
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(userId:String):Flow<Resource<ProfileResponse>>  {
        return flow {
          repository.getProfile(userId)
        }
    }
}
