package org.lotka.xenonx.presentation.screen.profile


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
import kotlin.random.Random

@Composable
fun ProfileScreen(
    navController: NavController,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top Toolbar
        StandardToolBar(
            navController = navController,
            title = {
                Text(
                    text = stringResource(R.string.you_profile),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground,
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = false,
            navAction = {
                IconButton(onClick = { /* Menu action */ }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "") // Use proper description

                }
            }
        )

        // Main Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize() // Take remaining space
        ) {
            item {
                BannerSeaction(
                    modifier = Modifier.aspectRatio(2.5f)
                )
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
    }
}
