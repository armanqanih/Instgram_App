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
        val storageRef = firebaseStorage.reference
        val imageRef = storageRef.child("images/${UUID.randomUUID()}")

        return try {
            // Start the file upload
            val uploadTask = imageRef.putFile(imageUri)

            // Listen for success and failure
            uploadTask.addOnSuccessListener {
                Log.i("My PrintLn 1", "Upload successful")
            }.addOnFailureListener { exception ->
                Log.e("My PrintLn 3", "Error during upload: ${exception.message}")
                Log.e("My PrintLn 3", "HTTP Result Code: ${exception.javaClass.name}")
                exception.printStackTrace()
            }.await()  // Await until upload completes

            // After a successful upload, get the download URL
            val downloadUrl = imageRef.downloadUrl.await().toString()
            Log.i("My PrintLn 2", "Download URL: $downloadUrl")
            downloadUrl

        } catch (e: Exception) {
            Log.e("My PrintLn 3", "Error: ${e.message}")
            throw e  // Rethrow the exception to handle it further up the chain
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









