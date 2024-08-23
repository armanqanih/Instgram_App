package org.lotka.xenonx.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.lotka.xenonx.domain.model.PostModel

class PostSourcePagination(
    private val firestore: FirebaseFirestore
) : PagingSource<Int, PostModel>() {

    override fun getRefreshKey(state: PagingState<Int, PostModel>): Int? {
        // Try to return the key of the closest page to the anchor position
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostModel> {
        return try {
            // Define your page
            val currentPage = params.key ?: 1
            val pageSize = params.loadSize

            // Fetch data from Firestore
            val snapshot = firestore.collection("posts")
                .orderBy("timestamp") // Assuming posts have a timestamp field
                .startAfter(currentPage * pageSize)
                .limit(pageSize.toLong())
                .get()
                .await()

            // Convert snapshot to list of PostModel
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
}
