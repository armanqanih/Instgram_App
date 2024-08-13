package org.lotka.xenonx.presentation.screen.post


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.navigation.NavController
import org.lotka.xenonx.domain.model.Post
import org.lotka.xenonx.presentation.composable.StandardScaffold
import org.lotka.xenonx.presentation.screen.post.compose.PostItem

@Composable
fun PostScreen(
    navController: NavController,
){
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

