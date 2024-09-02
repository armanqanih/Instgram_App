package org.lotka.xenonx.presentation.screen.profile


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceIn
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import org.lotka.xenonx.domain.model.PostModel
import org.lotka.xenonx.domain.model.UserModel
import org.lotka.xenonx.presentation.screen.post.compose.PostItem
import org.lotka.xenonx.presentation.screen.profile.composable.BannerSeaction
import org.lotka.xenonx.presentation.screen.profile.composable.ProfileHeaderSection
import org.lotka.xenonx.presentation.ui.navigation.ScreensNavigation
import org.lotka.xenonx.presentation.util.Dimension.profilePictureSizeLarge
import org.lotka.xenonx.presentation.util.UiEvent
import org.lotka.xenonx.presentation.util.toPx

@Composable
fun ProfileScreen(
    userId:String,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: ProfileViewModel = hiltViewModel(),
    profilePictureSize : Dp = profilePictureSizeLarge
) {

    val posts = viewModel.posts.collectAsLazyPagingItems()

    val scaffoldState = rememberScaffoldState()

    var toolbarState = viewModel.toolbarState.collectAsState().value
    var state = viewModel.state.collectAsState().value

    val lazyListState = rememberLazyListState()
    val iconSizeExpanded = 35.dp
    val toolbarHeightCollapsed = 75.dp
    val bannerHeight = (LocalConfiguration.current.screenWidthDp / 2.5f).dp
    val toolbarHeightExpanded = remember {
        bannerHeight + profilePictureSize
    }
    val iconCollapsedOffsetY = remember {
        (toolbarHeightCollapsed - iconSizeExpanded) / 2f
    }
    val imageCollapsedOffsetY = remember {
        (toolbarHeightCollapsed - profilePictureSize / 2) / 2
    }

    val maxOffset = toolbarHeightExpanded - toolbarHeightCollapsed
    val nestedScrollConnection = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            val delta = available.y
            val newOffset = toolbarState.toolbarOffsetY + delta

            // Only scroll the banner/profile when the user is scrolling up
            if (lazyListState.firstVisibleItemIndex == 0) {
                viewModel.setToolbarOffsetY(newOffset.coerceIn(
                    minimumValue = -maxOffset.toPx(),
                    maximumValue = 0f
                ))
                viewModel.setExpandedRatio(((toolbarState.toolbarOffsetY + maxOffset.toPx()) / maxOffset.toPx()).coerceIn(0f, 1f))
            }

            return Offset.Zero
        }
    }


   LaunchedEffect(key1 = true) {
       viewModel.getProfile(userId)
       viewModel.eventFlow.collect { event ->
           when (event) {
               is UiEvent.ShowSnakeBar -> {
                   scaffoldState.snackbarHostState.showSnackbar(message = event.message)
               }
               is UiEvent.Navigate -> {
                   onNavigate(ScreensNavigation.EditProfileScreen.route)
               }

               UiEvent.NavigateUp -> TODO()
           }
       }
   }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (state.profile == null) {
            Text("No Profile Data Found", modifier = Modifier.align(Alignment.Center))
        }


        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                Spacer(
                    modifier = Modifier.height(
                        toolbarHeightCollapsed + profilePictureSize / 2 + 150.dp
                    )
                )
            }

            item {
                    state.profile?.let { profile ->
                        ProfileHeaderSection(
                            user = UserModel(
                                userId = profile.userId,
                                profileImageUrl = profile.profileImageUrl,
                                username = profile.username,
                                bio = profile.bio,
                                followerCount = profile.followerCount,
                                followingCount = profile.followingCount,
                                postCount = profile.postCount,
                                isOwenProfile = profile.isOwenProfile,
                                isFollowing = profile.isFollowing,
                                bannerUrl = profile.bannerUrl

                            ),
                            isOwnProfile = profile.isOwenProfile,
                            onEditClick = {
                                onNavigate(ScreensNavigation.EditProfileScreen.route + "?userId=${profile.userId}")
                            }
                        )
                    }

            }

            items(posts.itemSnapshotList) { post ->
                        PostItem(
                        postModel = PostModel(
                        id = post?.id ?: 0,
                        userName = post?.userName ?: "",
                        profileImage = post?.profileImage ?: "",
                        postImage = post?.postImage ?: "",
                        description = post?.description ?: "",
                        likes = post?.likes ?: 0,
                        comments = post?.comments ?: 0,
                        ),
                    showProfileImage = false,
                    onPostClick = {
                        onNavigate(ScreensNavigation.PostDetailScreen.route)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            state.profile?.let { profile ->
                BannerSeaction(
                    modifier = Modifier.height(
                        (bannerHeight * toolbarState.expandedRatio).coerceIn(
                            minimumValue = toolbarHeightCollapsed,
                            maximumValue = bannerHeight
                        )
                    ),
                    iconModifier = Modifier.graphicsLayer {
                        translationY = (1f - toolbarState.expandedRatio) *
                                -iconCollapsedOffsetY.toPx()
                    },
                    topSkills = profile.skills,
                    shouldShowGithub = profile.githubUrl != null && profile.githubUrl!!.isNotBlank(),
                    shouldShowLinkedIn = profile.linkedInUrl != null && profile.linkedInUrl!!.isNotBlank(),

                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(profilePictureSize)
                ) {
                    Image(
                        painter = rememberImagePainter(
                        data = profile.profileImageUrl,
                            builder = {
                                crossfade(true)
                            }
                        )
                            ,
                        contentDescription = "",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .graphicsLayer {
                                translationY = -profilePictureSize.toPx() / 2f +
                                        (1f - toolbarState.expandedRatio) * imageCollapsedOffsetY.toPx()
                                transformOrigin = TransformOrigin(
                                    pivotFractionX = 0.5f,
                                    pivotFractionY = 0f
                                )
                                val scale = 0.5f + toolbarState.expandedRatio * 0.5f
                                scaleX = scale
                                scaleY = scale
                            }
                            .size(profilePictureSize)
                            .clip(CircleShape)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colors.onSurface,
                                shape = CircleShape
                            ),
                        contentScale = ContentScale.Crop
                    )
                }

            }


        }
    }
}
