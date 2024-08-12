package org.lotka.xenonx.presentation.screen.post

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.lotka.xenonx.domain.model.Post
import org.lotka.xenonx.presentation.screen.post.compose.PostItem

@Composable
fun PostScreen(
    navController: NavController,
){

    PostItem(post = Post(id = 1, userName = "Arman Sherwamii",
        profileImage = "",
        postImage = "",
        description = "Hello WorldHello WorldHello WorldHello " +
                "WorldHello WorldHello WorldWorldHello WorldHello World" +
                "WorldHello WorldHello WorldWorldHello WorldHello World" +
                "WorldHello WorldHello World",
        likes = 10,
        comments = 7

        ))


}