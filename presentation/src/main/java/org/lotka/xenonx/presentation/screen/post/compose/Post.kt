package org.lotka.xenonx.presentation.screen.post.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.lotka.xenonx.domain.model.PostModel
import org.lotka.xenonx.presentation.R
import org.lotka.xenonx.presentation.theme.HintGray
import org.lotka.xenonx.presentation.theme.MediumGray
import org.lotka.xenonx.presentation.theme.TextWhite
import org.lotka.xenonx.presentation.util.Constants.MAX_DESCRIPTION_LINE
import org.lotka.xenonx.presentation.util.Dimension.SpaceMedium
import org.lotka.xenonx.presentation.util.Dimension.SpaceSmall
import org.lotka.xenonx.presentation.util.Dimension.profilePictureSizeMedium


@Composable
fun PostItem(
    postModel: PostModel,
    showProfileImage : Boolean = true,
    onPostClick: () -> Unit ={},
) {

    val context = LocalContext.current

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(SpaceMedium)
    ) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .offset(y = if (showProfileImage) profilePictureSizeMedium / 2f else 0.dp)
            .clip(shape = MaterialTheme.shapes.medium)
            .shadow(elevation = 5.dp)
            .background(MediumGray)

        ) {
            Image(
                painter = painterResource(id = R.drawable.post),
                contentDescription = "profile image",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onPostClick()
                    }

            )
            Column(modifier = Modifier
                .fillMaxWidth()
               .padding(SpaceSmall)
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
                    onUserNameClicked = {userName ->

                    }
                )
                Text(
                    modifier = Modifier.padding(start = SpaceSmall),
                    text = buildAnnotatedString {
                        append(text = postModel.description)
                        withStyle(
                            SpanStyle(
                                color = HintGray
                            )
                        ) {
                            append(context.getString(R.string.read_more))
                        }
                    },
                    style = MaterialTheme.typography.body2,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = MAX_DESCRIPTION_LINE,

                )

                Spacer(modifier = Modifier.height(SpaceMedium))
                
                Row (modifier = Modifier.fillMaxWidth()
                    , horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){


                    Text(
                        text = stringResource(R.string.liked_by_x_people,postModel.likes)
                        , style = MaterialTheme.typography.body2
                        , color = TextWhite,
                        fontSize = 16.sp
                        ,modifier = Modifier.padding(start =SpaceSmall)

                    )

                    Text(
                        text = stringResource(R.string.comment ,postModel.comments)
                        , style = MaterialTheme.typography.body2
                        , color = TextWhite,
                          fontSize = 16.sp
                    )
                }

            }

        }
   if (showProfileImage){
       Image(
           painter = painterResource(id = R.drawable.arman),
           contentDescription = "profile image",
           modifier = Modifier
               .size(profilePictureSizeMedium)
               .clip(CircleShape)
               .align(Alignment.TopCenter)
       )
   }


    }}

