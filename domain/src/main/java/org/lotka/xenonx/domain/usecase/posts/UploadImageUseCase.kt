package org.lotka.xenonx.domain.usecase.posts

import android.net.Uri
import org.lotka.xenonx.domain.repository.post.PostRepository
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val repository: PostRepository
) {
   suspend operator fun invoke(imageUri: Uri): String {
       return repository.uploadImage(imageUri)
   }

}
