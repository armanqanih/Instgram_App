package org.lotka.xenonx.presentation.screen.post_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.lotka.xenonx.domain.model.CommentModel
import org.lotka.xenonx.domain.model.PostModel
import org.lotka.xenonx.presentation.R
import org.lotka.xenonx.presentation.composable.StandardToolBar

import org.lotka.xenonx.presentation.screen.post.compose.ActionRow
import org.lotka.xenonx.presentation.screen.post_detail.compose.CommentCard
import org.lotka.xenonx.presentation.theme.MediumGray
import org.lotka.xenonx.presentation.theme.TextWhite
import org.lotka.xenonx.presentation.util.Dimension.SpaceMedium
import org.lotka.xenonx.presentation.util.Dimension.SpaceSmall
import org.lotka.xenonx.presentation.util.Dimension.profilePictureSize

@Composable
fun PostDetailScreen(
   navController: NavController,
   postModel: PostModel
) {

   val context = LocalContext.current

   Column(modifier = Modifier.fillMaxSize()) {
      StandardToolBar(
         navController = navController,
         modifier = Modifier.fillMaxWidth(),
         showBackArrow = true,
         title = {
            Text(
               text = stringResource(R.string.your__feed),
               fontWeight = FontWeight.Bold,
               color = MaterialTheme.colors.onBackground
            )
         },


         )
//
//      Spacer(modifier = Modifier.height(SpaceLarge))
      LazyColumn(modifier = Modifier
         .fillMaxWidth()
         .background(MaterialTheme.colors.surface)
      ) {

         item {
            Column(modifier = Modifier.fillMaxSize()
               .background(MaterialTheme.colors.background)
            ) {

               Box(
                  modifier = Modifier.fillMaxWidth()

               ) {

                  Column(
                     modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = profilePictureSize / 2f)
                        .clip(shape = MaterialTheme.shapes.medium)
                        .shadow(elevation = 5.dp)
                        .background(MediumGray)
                  ) {
                     Image(
                        painter = painterResource(id = R.drawable.post),
                        contentDescription = "profile detail image",
                        modifier = Modifier.fillMaxWidth()

                     )
                     Column(
                        modifier = Modifier
                           .fillMaxSize()
                           .padding(SpaceMedium)
                     ) {
                        ActionRow(
                           modifier = Modifier.fillMaxWidth(),
                           userName = "Arman Sherwamii",
                           onLikeClicked = { isLiked ->

                           },
                           onCommentClicked = {

                           },
                           onShareClicked = {

                           },
                           onUserNameClicked = { userName ->

                           }
                        )
                        Text(
                           text = postModel.description,
                           style = MaterialTheme.typography.body2
                           , modifier = Modifier.padding(start = SpaceSmall)
                        )

                        Spacer(modifier = Modifier.height(SpaceMedium))

                        Text(
                           text = stringResource(R.string.liked_by_x_people, postModel.likes),
                           style = MaterialTheme.typography.body2,
                           color = TextWhite,
                           fontSize = 16.sp,
                           modifier = Modifier.padding(start =SpaceSmall)
                        )
                        Spacer(modifier = Modifier.height(SpaceMedium))


                     }



                  }

                  Image(
                     painter = painterResource(id = R.drawable.arman),
                     contentDescription = "profile image",
                     modifier = Modifier
                        .size(profilePictureSize)
                        .clip(CircleShape)
                        .align(Alignment.TopCenter)
                  )


               }
            }

            Spacer(modifier = Modifier.height(SpaceMedium))

         }


         items(20) {
            CommentCard(
               modifier = Modifier
                  .fillMaxWidth()
                  .padding(SpaceMedium),
               commentModel = CommentModel(
                  userName = "Arman Sherwamii $it",
                  comment = "Awesome postAwesome postAwesome postAwesome postAwesome" +
                          " postAwesome post",
                  profilePictureUrl = "",
                  likeCount = 16,
                  isLiked = true),
               onLikeClicked = { isLiked ->

               }
            )
         }

      }



   }


}
