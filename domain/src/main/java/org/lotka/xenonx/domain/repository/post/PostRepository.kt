package org.lotka.xenonx.domain.repository.post

import android.net.Uri
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.lotka.xenonx.domain.model.PostModel
import org.lotka.xenonx.domain.util.Resource

interface PostRepository {

    fun getPosts(): Flow<PagingData<PostModel>>

    suspend fun uploadImage(imageUri: Uri): String

    suspend fun createPost(description: String, imageUri: String):  Resource<Unit>?



}