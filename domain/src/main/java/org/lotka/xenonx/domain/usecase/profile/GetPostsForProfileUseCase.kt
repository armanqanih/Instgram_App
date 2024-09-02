package org.lotka.xenonx.domain.usecase.profile


import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import org.lotka.xenonx.domain.model.PostModel

import org.lotka.xenonx.domain.model.UserModel

import org.lotka.xenonx.domain.repository.profile.ProfileRepository
import org.lotka.xenonx.domain.util.Resource
import javax.inject.Inject

class GetPostsForProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
     operator fun invoke(userId: String): Flow<PagingData<PostModel>> {
        return flow {
             repository.getPostsPage(userId)

}}
}
