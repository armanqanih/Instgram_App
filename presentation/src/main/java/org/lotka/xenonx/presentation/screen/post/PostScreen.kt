package org.lotka.xenonx.presentation.screen.post


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.launch

import org.lotka.xenonx.presentation.R

import org.lotka.xenonx.presentation.composable.StandardToolBar
import org.lotka.xenonx.presentation.screen.post.compose.PostItem
import org.lotka.xenonx.presentation.ui.navigation.ScreensNavigation


@Composable
fun PostScreen(
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: PostViewModel = hiltViewModel()
){
    val scaffoldState = rememberScaffoldState()
    val state = viewModel.state.collectAsState().value
     val scope = rememberCoroutineScope()
    val posts = viewModel.posts.collectAsLazyPagingItems()


    Column(modifier = Modifier.fillMaxSize()) {
        StandardToolBar(
            onNavigateUp = onNavigateUp,
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
            onNavigate(ScreensNavigation.SearchScreen.route)
                }) {
                    Icon(imageVector = Icons.Outlined.Search
                        , contentDescription = "add",
                          tint = MaterialTheme.colors.onBackground
                    )

                }
            }
        )
   Box(modifier = Modifier.fillMaxSize()) {
       if (state.isLoadingFirstTime) {
           CircularProgressIndicator(
               modifier = Modifier.fillMaxWidth()
                   .padding(16.dp)
                   .align(Alignment.Center)
                   .wrapContentWidth(Alignment.CenterHorizontally))
       }

       if (posts.itemCount == 0) {
           Text(
               "No posts found",
               fontWeight = FontWeight.Bold,
               fontSize = 24.sp,
               textAlign = TextAlign.Center,
               modifier = Modifier.align(Alignment.Center)
           )
       }

       LazyColumn {

               items(posts.itemCount) { index ->
                   val post = posts[index]
                   post?.let {postModel->
                       PostItem(postModel = postModel)
                   }
               }

           item {
                if (state.isLoadingNewPosts) {
                   CircularProgressIndicator(
                       modifier = Modifier.fillMaxWidth()
                           .padding(16.dp)
                           .align(Alignment.Center)
                           .wrapContentWidth(Alignment.CenterHorizontally))

               }



               posts.apply {
                   when {
                       loadState.refresh is LoadState.Loading -> {
                           viewModel.onEvent(PostEvent.LoadedPage)

                       }
                       loadState.append is LoadState.Loading -> {
                           viewModel.onEvent(PostEvent.LoadMorePosts)
                       }
                       loadState.refresh is LoadState.NotLoading -> {
                           viewModel.onEvent(PostEvent.LoadedPage)
                       }

                       loadState.append is LoadState.Error -> {
                           scope.launch {
                               scaffoldState.snackbarHostState.showSnackbar(
                                   message = "Error loading posts"
                               )

                           }

                       }


                   }
               }


           }


       }
   }



    }}

