package org.lotka.xenonx.di

import com.google.firebase.auth.FirebaseAuth
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
    fun providePostRepository(
        firestore: FirebaseFirestore
    ): PostRepository = PostRepositoryImpl(firestore)

    @Provides
    fun providePostUseCases(
        getPostsUseCase: GetPostsUseCase,
        // add other use cases or dependencies
    ): PostUseCases {
        return PostUseCases(getPostsUseCase)
    }
}






