package org.lotka.xenonx.data.repository

import android.net.Uri
import org.lotka.xenonx.data.paging.PostSourcePagination
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import org.lotka.xenonx.domain.model.PostModel
import org.lotka.xenonx.domain.repository.post.PostRepository
import org.lotka.xenonx.domain.util.Constants.POSTS_PATH
import org.lotka.xenonx.domain.util.Resource
import java.util.UUID
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val pagination: PostSourcePagination,
    private val firestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage,

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

    override suspend fun uploadImage(imageUri: Uri): String {
        println("My PrintLn Upload Image")
        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child("images/${UUID.randomUUID()}")
        return try {
            imageRef.putFile(imageUri).await() // Upload file
            imageRef.downloadUrl.await().toString() // Get download URL

        } catch (e: Exception) {
            throw e
        }
    }


    override suspend fun createPost(description: String, imageUri: String): Resource<Unit>? {
        return try {
            val post = mapOf(
                "description" to description,
                "imageUrl" to imageUri,
                "timestamp" to FieldValue.serverTimestamp()
            )
            firestore.collection("posts").add(post).await()
            println("My PrintLn create post")
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Unknown Error")
        }
    }
}









