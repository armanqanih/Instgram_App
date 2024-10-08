package org.lotka.xenonx.data.repository

import android.net.Uri
import android.util.Log
import org.lotka.xenonx.data.paging.PostSourcePagination
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.tasks.await

import org.lotka.xenonx.domain.model.PostModel

import org.lotka.xenonx.domain.repository.post.PostRepository

import org.lotka.xenonx.domain.util.Resource

import java.util.UUID
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage,

    ): PostRepository {
    override fun getPosts(): Flow<PagingData<PostModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20, // Adjust the page size
                enablePlaceholders = false
            ),
            pagingSourceFactory = {PostSourcePagination(firestore, PostSourcePagination.Source.Follows)}
        ).flow
    }

    override suspend fun uploadImage(imageUri: Uri): String {
        val storageRef = firebaseStorage.reference
        val imageRef = storageRef.child("images/${UUID.randomUUID()}")

        return try {
            // Ensure the Uri is a local file URI
            if (imageUri.scheme != "content" && imageUri.scheme != "file") {
                Log.e("UploadDebug", "Invalid Uri scheme: ${imageUri.scheme}")
                throw IllegalArgumentException("Invalid Uri scheme: ${imageUri.scheme}")
            }
            // Upload the file
            val uploadTask = imageRef.putFile(imageUri).await()
            // Get the download URL
            val downloadUrl = imageRef.downloadUrl.await().toString()
            Log.i("UploadDebug", "Download URL: $downloadUrl")
            downloadUrl
        } catch (e: StorageException) {
            Log.e("UploadDebug", "StorageException during upload: ${e.message}")
            Log.e("UploadDebug", "HTTP Result Code: ${e.httpResultCode}")
            Log.e("UploadDebug", "Inner Exception: ${e.cause}")
            e.printStackTrace()
            throw e
        } catch (e: Exception) {
            Log.e("UploadDebug", "Error: ${e.message}")
            e.printStackTrace()
            throw e
        }

    }
        override suspend fun createPost(description: String, imageUri: String): Resource<Unit>? {
        return try {
            val post = mapOf(
                "description" to description,
                "imageUrl" to imageUri,
                "timestamp" to FieldValue.serverTimestamp(),  // Auto-generates a timestamp on Firestore
                "likesCount" to 0,  // Initialize the number of likes
                "commentsCount" to 0  // Initialize the number of comments
            )
            firestore.collection("PostModel").add(post).await()
            Log.i("PostDebug", "Post created successfully.")
            Resource.Success(Unit)
        } catch (e: Exception) {
            Log.e("PostDebug", "Error creating post: ${e.localizedMessage}")
            Resource.Error(e.localizedMessage ?: "Unknown Error")

        }
    }


}









