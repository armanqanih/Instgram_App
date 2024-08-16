package org.lotka.xenonx.presentation.screen.profile


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.onebone.toolbar.CollapsingToolbar
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import me.onebone.toolbar.rememberCollapsingToolbarState
import org.lotka.xenonx.domain.model.ActivityModel
import org.lotka.xenonx.domain.model.PostModel
import org.lotka.xenonx.domain.model.UserModel
import org.lotka.xenonx.domain.util.ActivityAction
import org.lotka.xenonx.domain.util.DataFormater
import org.lotka.xenonx.presentation.R
import org.lotka.xenonx.presentation.composable.StandardToolBar
import org.lotka.xenonx.presentation.screen.activity.compose.ActivityItem
import org.lotka.xenonx.presentation.screen.post.compose.PostItem
import org.lotka.xenonx.presentation.screen.profile.composable.BannerSeaction
import org.lotka.xenonx.presentation.screen.profile.composable.ProfileHeaderSection
import org.lotka.xenonx.presentation.ui.navigation.ScreensNavigation
import org.lotka.xenonx.presentation.util.Dimension.SpaceMedium
import org.lotka.xenonx.presentation.util.Dimension.profilePictureSizeLarge
import javax.xml.transform.Source
import kotlin.random.Random

@Composable
fun ProfileScreen(
    navController: NavController,
) {


    var toolbarOffsetY by remember {
        mutableStateOf(0)
    }
    val toolbarHeightCollapsed = 56.dp
    val bannerHeight = (LocalConfiguration.current.screenWidthDp /2.5f).dp
    val toolbarHeightExpended = remember {
        bannerHeight + profilePictureSizeLarge
    }


    val nestedScrollConnection = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
             val delta = available.y
            val newOffset = toolbarOffsetY + delta
//            toolbarOffsetY = newOffset.coerceIn(
//                minimumValue = ,
//                maximumValue =
//            )
            return super.onPreScroll(available, source)
        }
    }

    // Top Toolbar
//    StandardToolBar(
//        navController = navController,
//        title = {
//            Text(
//                text = stringResource(R.string.you_profile),
//                fontWeight = FontWeight.Bold,
//                color = MaterialTheme.colors.onBackground,
//            )
//        },
//        modifier = Modifier.fillMaxWidth(),
//        showBackArrow = false,
//        navAction = {
//            IconButton(onClick = { /* Menu action */ }) {
//                Icon(
//                    imageVector = Icons.Default.Menu,
//                    contentDescription = "") // Use proper description
//
//            }
//        }
//    )

Box(modifier = Modifier.fillMaxSize()){
    // Main Content
    LazyColumn(
        modifier = Modifier
            .fillMaxSize() // Take remaining space
    ) {
        item { 
            Spacer(modifier = Modifier.height(
                toolbarHeightExpended
            ))
        }

        item {
            ProfileHeaderSection(
                user = UserModel(
                    profilePictureUrl = "",
                    userName = "ArmanSherwanii",
                    description = "To display numbers in a format similar to " +
                            "Instagram, where numbers greater than 1,000 are shown as \"1k\", and numbers greater " +
                            "than 1,000,000 are shown as \"1M\", you can create a function ",
                    followerCount = 1,
                    followingCount = 250000,
                    postCount = 12
                )
            )
        }

        items(20) {
            PostItem(
                postModel = PostModel(
                    id = 1,
                    userName = "Arman Sherwamii",
                    profileImage = "",
                    postImage = "",
                    description = "ahahaha",
                    likes = 17,
                    comments = 7,
                ),
                showProfileImage = false,
                onPostClick = {
                    navController.navigate(ScreensNavigation.PostDetailScreen.route)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .align(Alignment.TopCenter)
    ) {
        BannerSeaction(modifier = Modifier.height(bannerHeight))
        Image(
            painter = painterResource(id = R.drawable.arman) ,
            contentDescription = "",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .graphicsLayer {
                    translationY = -profilePictureSizeLarge.toPx() / 2f
                }
                .size(profilePictureSizeLarge)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.onSurface,
                    shape = CircleShape
                )
            , contentScale = ContentScale.Crop

        )
    }
}


    }

