package org.lotka.xenonx.domain.usecase.posts

data class PostUseCases(
    val getPosts: GetPostsUseCase,
    val createPost: CreatePostUseCase,
    val uploadImage: UploadImageUseCase,


)
