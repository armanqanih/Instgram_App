package org.lotka.xenonx.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import org.lotka.xenonx.domain.model.PostModel

class PostSourcePagination(
    private val firestore: FirebaseFirestore,
    private val source: Source
) : PagingSource<Int, PostModel>() {

    override fun getRefreshKey(state: PagingState<Int, PostModel>): Int? {
        // Try to return the key of the closest page to the anchor position
        return state.anchorPosition?.let { anchorPosition ->
            val closestPage = state.closestPageToPosition(anchorPosition)
            closestPage?.prevKey?.plus(1) ?: closestPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostModel> {
        return try {
            val currentPage = params.key ?: 1
            val pageSize = params.loadSize

            val snapshot: QuerySnapshot = when (source) {
                Source.Follows -> {
                    firestore.collection("PostModel")
                        .orderBy("timestamp")
                        .limit(pageSize.toLong())
                        .get()
                        .await()
                }

                is Source.Profile -> {
                    firestore.collection("PostModel")
                        .whereEqualTo("userId", source.userId) // Filter posts by userId
                        .orderBy("timestamp")
                        .limit(pageSize.toLong())
                        .get()
                        .await()
                }
            }

            val posts = snapshot.documents.mapNotNull { it.toObject(PostModel::class.java) }

            // Determine next and previous page keys
            val nextKey = if (posts.size < pageSize) null else currentPage + 1
            val prevKey = if (currentPage == 1) null else currentPage - 1

            LoadResult.Page(
                data = posts,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    sealed class Source {
        data class Profile(val userId: String) : Source()
        object Follows : Source()
    }
}
