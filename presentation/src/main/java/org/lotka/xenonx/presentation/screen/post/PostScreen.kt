package org.lotka.xenonx.presentation.screen.post


import androidx.compose.runtime.Composable

import androidx.navigation.NavController
import org.lotka.xenonx.domain.model.Post
import org.lotka.xenonx.presentation.composable.StandardScaffold
import org.lotka.xenonx.presentation.screen.post.compose.PostItem

@Composable
fun PostScreen(
    navController: NavController,
){
    StandardScaffold(){
        PostItem(
            post = Post(
                id = 1,
                userName = "Arman Sherwamii",
                profileImage = "",
                postImage = "",
                description = "ahahaha",
                likes = 17,
                comments = 7

            )
        )

    }

}