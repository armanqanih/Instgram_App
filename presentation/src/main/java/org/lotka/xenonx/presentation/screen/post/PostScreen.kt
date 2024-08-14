package org.lotka.xenonx.presentation.screen.post


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight

import androidx.navigation.NavController
import org.lotka.xenonx.domain.model.Post
import org.lotka.xenonx.presentation.R

import org.lotka.xenonx.presentation.composable.StandardToolBar
import org.lotka.xenonx.presentation.screen.post.compose.PostItem
import org.lotka.xenonx.presentation.ui.navigation.ScreensNavigation


@Composable
fun PostScreen(
    navController: NavController,
){
    Column(modifier = Modifier.fillMaxSize()) {
        StandardToolBar(
            navController = navController,
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = true,
            title = {
                Text(text = stringResource(R.string.your__feed)
                , fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            },
            navAction = {
                IconButton(onClick = {

                }) {
                    Icon(imageVector = Icons.Outlined.Search
                        , contentDescription = "add",
                          tint = MaterialTheme.colors.onBackground
                    )

                }
            }

        )



        PostItem(
            post = Post(
                id = 1,
                userName = "Arman Sherwamii",
                profileImage = "",
                postImage = "",
                description = "ahahaha",
                likes = 17,
                comments = 7,

            ),
            onPostClick = {
                navController.navigate(ScreensNavigation.PostDetailScreen.route)
            }
        )


    }





    }

