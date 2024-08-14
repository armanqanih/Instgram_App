package org.lotka.xenonx.presentation.screen.post_detail.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.lotka.xenonx.domain.model.Comment
import org.lotka.xenonx.presentation.R

import org.lotka.xenonx.presentation.util.Dimension.SpaceMedium
import org.lotka.xenonx.presentation.util.Dimension.SpaceSmall
import org.lotka.xenonx.presentation.util.Dimension.profilePictureSmallSize


@Composable
fun CommentCard(
    modifier: Modifier = Modifier,
    onLikeClicked: (Boolean) -> Unit = {},
    comment: Comment = Comment()
) {

   Card(modifier = modifier,
       shape = MaterialTheme.shapes.medium,
       backgroundColor = MaterialTheme.colors.onSurface,
       elevation = 4.dp

       ) {

       Column(modifier = Modifier.fillMaxSize()
           .padding(SpaceMedium),
       ) {
           Row (modifier = Modifier.fillMaxWidth(),
               horizontalArrangement = Arrangement.SpaceBetween

               ){
               Row(verticalAlignment = CenterVertically) {
                   Image(
                       painter = painterResource(id = R.drawable.arman),
                       contentDescription = null,
                       modifier = Modifier
                           .size(profilePictureSmallSize)
                           .clip(CircleShape)
                       )

                   Spacer(modifier = Modifier.width(SpaceSmall))

                   Text(text = comment.userName,
                       fontWeight = FontWeight.Bold,
                       color = MaterialTheme.colors.onBackground,
                       style = MaterialTheme.typography.body2,
                       )
               }

                Text(text = "2 day ago",
                   color = MaterialTheme.colors.onBackground,
                   style = MaterialTheme.typography.body2,
                   )

           }

           Spacer(modifier = Modifier.height(SpaceMedium))

           Row(
               horizontalArrangement = Arrangement.SpaceBetween,
               modifier = Modifier.fillMaxWidth()
               ) {
               Text(text = comment.comment,
                   color = MaterialTheme.colors.onBackground,
                   style = MaterialTheme.typography.body2,
                   modifier = Modifier.weight(9f)
               )


               IconButton(onClick = {
                   onLikeClicked(comment.isLiked)
               }) {
                 Icon(imageVector = Icons.Outlined.Favorite,
                     tint = if (comment.isLiked) Color.White else Color.Red,
                     contentDescription = "like",
                     modifier = Modifier.weight(1f)
                     )
               }
           }

           Text(
               text = stringResource(R.string.liked_by_x_people, comment.likeCount),
               style = MaterialTheme.typography.body2,
               fontWeight = FontWeight.Bold,
           )

       }

   }

}