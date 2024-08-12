package org.lotka.xenonx.presentation.screen.post.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.lotka.xenonx.domain.model.Post
import org.lotka.xenonx.presentation.R
import org.lotka.xenonx.presentation.theme.HintGray
import org.lotka.xenonx.presentation.theme.MediumGray
import org.lotka.xenonx.presentation.theme.TextGray
import org.lotka.xenonx.presentation.util.Constants.MAX_DESCRIPTION_LINE
import org.lotka.xenonx.presentation.util.Dimension.SpaceMedium

@Composable
fun PostItem(
    post: Post,
    profilePictureSize: Dp = 50.dp
) {

    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxWidth()
        .shadow(5.dp).padding(SpaceMedium)
    ) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .offset(y = profilePictureSize / 2f)
            .clip(shape = MaterialTheme.shapes.medium)
            .background(MediumGray)

        ) {
            Image(
                painter = painterResource(id = R.drawable.post),
                contentDescription = "profile image",

            )
            Spacer(modifier = Modifier.height(SpaceMedium))

            ActionRow(userName = "Arman Sherwamii"
            , modifier = Modifier.fillMaxWidth(),
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
                text = buildAnnotatedString {
                    append(text = post.description)
                    withStyle(
                        SpanStyle(
                            color = HintGray
                        )
                    ) {
                        append((stringResource(R.string.read_more)))
                    }
                },
                style = MaterialTheme.typography.body2,
                overflow = TextOverflow.Ellipsis,
                maxLines = MAX_DESCRIPTION_LINE,
                )

            Row (modifier = Modifier.fillMaxWidth()
            , horizontalArrangement = Arrangement.SpaceBetween
            ){


                Text(
                    text = stringResource(R.string.liked_by_x_people,post.likes)
                    , style = MaterialTheme.typography.body2
                    , color = TextGray
                )

                Text(
                    text = stringResource(R.string.comment)
                    , style = MaterialTheme.typography.body2
                    , color = TextGray
                )
            }

        }

        Image(
            painter = painterResource(id = R.drawable.arman),
            contentDescription = "profile image",
            modifier = Modifier.clip(CircleShape)
                .size(profilePictureSize),
            alignment = Alignment.TopCenter
        )

    }}

