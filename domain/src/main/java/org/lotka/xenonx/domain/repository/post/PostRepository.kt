package org.lotka.xenonx.domain.repository.post

import kotlinx.coroutines.flow.Flow
import org.lotka.xenonx.domain.model.PostModel
import org.lotka.xenonx.domain.util.Resource

interface PostRepository {

    suspend fun getPostFromFollowers(page: Int, pageSize: Int): Flow<Resource<List<PostModel>>>

}