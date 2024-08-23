package org.lotka.xenonx.domain.repository.post

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.lotka.xenonx.domain.model.PostModel
import org.lotka.xenonx.domain.util.Resource

interface PostRepository {

    fun getPosts(): Flow<PagingData<PostModel>>

}