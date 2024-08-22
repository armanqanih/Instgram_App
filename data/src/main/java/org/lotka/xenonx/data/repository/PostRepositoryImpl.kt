package org.lotka.xenonx.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import org.lotka.xenonx.domain.model.PostModel
import org.lotka.xenonx.domain.repository.post.PostRepository
import org.lotka.xenonx.domain.util.Resource
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): PostRepository {
    override suspend fun getPostFromFollowers(
        page: Int,
        pageSize: Int,
    ): Flow<Resource<List<PostModel>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val snapshot = firestore.collection("posts")
                    .limit(pageSize.toLong())
                    .get()
                    .await()

                val posts = snapshot.documents.map { document ->
                    document.toObject(PostModel::class.java)!!
                }
                emit(Resource.Success(posts))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }
    }
    }


//return flow {
//    try {
//        emit(Resource.Loading())
//
//        val offset = (page - 1) * pageSize // Calculate the number of documents to skip
//
//        val snapshot = firestore.collection("posts")
//            .orderBy("timestamp") // Ensure the posts are ordered
//            .limit((page * pageSize).toLong()) // Fetch a certain number of posts up to the current page
//            .get()
//            .await()
//
//        // Skip the previous pages' posts and get only the current page's posts
//        val posts = snapshot.documents.drop(offset).take(pageSize).map { document ->
//            document.toObject(PostModel::class.java)!!
//        }
//
//        emit(Resource.Success(posts))
//    } catch (e: Exception) {
//        emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
//    }
//}
//}