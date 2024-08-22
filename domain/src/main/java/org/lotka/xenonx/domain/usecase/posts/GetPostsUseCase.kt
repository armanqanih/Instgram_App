package org.lotka.xenonx.domain.usecase.posts

import kotlinx.coroutines.flow.Flow
import org.lotka.xenonx.domain.model.PostModel
import org.lotka.xenonx.domain.repository.post.PostRepository
import org.lotka.xenonx.domain.util.Resource
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val repository: PostRepository
) {
    suspend operator fun invoke(page: Int, pageSize: Int): Flow<Resource<List<PostModel>>> {
        return repository.getPostFromFollowers(page, pageSize)
    }
}
