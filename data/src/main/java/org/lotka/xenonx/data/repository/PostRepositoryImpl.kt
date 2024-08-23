package org.lotka.xenonx.data.repository

import org.lotka.xenonx.data.paging.PostSourcePagination
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.lotka.xenonx.domain.model.PostModel
import org.lotka.xenonx.domain.repository.post.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val pagination: PostSourcePagination
): PostRepository {
    override fun getPosts(): Flow<PagingData<PostModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20, // Adjust the page size
                enablePlaceholders = false
            ),
            pagingSourceFactory = { pagination }
        ).flow
    }

}

