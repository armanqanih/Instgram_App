package org.lotka.xenonx.di

import org.lotka.xenonx.data.paging.PostSourcePagination
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.lotka.xenonx.data.repository.PostRepositoryImpl
import org.lotka.xenonx.domain.repository.post.PostRepository
import org.lotka.xenonx.domain.usecase.posts.CreatePostUseCase
import org.lotka.xenonx.domain.usecase.posts.GetPostsUseCase
import org.lotka.xenonx.domain.usecase.posts.PostUseCases
import org.lotka.xenonx.domain.usecase.posts.UploadImageUseCase
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object PostModule {

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Provides
    @Singleton
    fun provideFireStore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun providePostSourcePagination(firestore: FirebaseFirestore): PostSourcePagination {
        return PostSourcePagination(firestore)
    }

    @Provides
    @Singleton
    fun providePostRepository(
        pagination: PostSourcePagination,
        firestore: FirebaseFirestore,
        firebaseStorage: FirebaseStorage

    ): PostRepository = PostRepositoryImpl(pagination,firestore,firebaseStorage)

    @Provides
    @Singleton
    fun providePostUseCases(
        repository: PostRepository
    ): PostUseCases {
        return PostUseCases(
            getPosts = GetPostsUseCase(repository),
            createPost = CreatePostUseCase(repository),
            uploadImage = UploadImageUseCase(repository)
            )
    }
}
