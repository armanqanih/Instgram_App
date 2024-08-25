package org.lotka.xenonx.domain.usecase.posts

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.lotka.xenonx.domain.model.PostModel
import org.lotka.xenonx.domain.repository.post.PostRepository
import org.lotka.xenonx.domain.util.Resource
import javax.inject.Inject

class CreatePostUseCase @Inject constructor(
    private val repository: PostRepository
) {
    suspend operator fun invoke(description: String, imageUri: String): Resource<Unit>? {

        if (imageUri == null){
            return Resource.Error("Image cannot be empty Please pick an image")
        }


        if (description.isBlank()) {
            return Resource.Error("Description cannot be empty")
        }


        val imageUriToString = Uri.parse(imageUri) // Convert String to Uri
        val imageUrl = repository.uploadImage(imageUriToString)

         return repository.createPost(description, imageUrl)

    }

}
