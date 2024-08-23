package org.lotka.xenonx.di

import org.lotka.xenonx.data.paging.PostSourcePagination
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.lotka.xenonx.data.repository.PostRepositoryImpl
import org.lotka.xenonx.domain.repository.post.PostRepository
import org.lotka.xenonx.domain.usecase.posts.GetPostsUseCase
import org.lotka.xenonx.domain.usecase.posts.PostUseCases
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object PostModule {

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
        pagination: PostSourcePagination
    ): PostRepository = PostRepositoryImpl(pagination)

    @Provides
    @Singleton
    fun providePostUseCases(
        repository: PostRepository
    ): PostUseCases {
        return PostUseCases(GetPostsUseCase(repository))
    }
}
