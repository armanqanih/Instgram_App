package org.lotka.xenonx.presentation.screen.post.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.lotka.xenonx.presentation.R
import org.lotka.xenonx.presentation.theme.TextWhite
import org.lotka.xenonx.presentation.util.Dimension.SpaceMedium
import org.lotka.xenonx.presentation.util.Dimension.SpaceSmall


@Composable
fun PostButtonRow(
    modifier: Modifier = Modifier,
    isLiked: Boolean = false,
    onLikeClicked: (Boolean) -> Unit = {},
    onCommentClicked: () -> Unit = {},
    iconSize: Dp = 30.dp,
    onShareClicked: () -> Unit = {},
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
    ) {

        //Like
        IconButton(onClick = {
            onLikeClicked(!isLiked)
        },
            modifier = Modifier.size(iconSize)
        ) {

            Icon(
                imageVector = if (isLiked) Icons.Default.Favorite
                else Icons.Default.FavoriteBorder,
                tint = if (isLiked) Color.Red else TextWhite,
                contentDescription = if (isLiked) stringResource(R.string.liked)
                else stringResource(R.string.not_liked)
            )

        }
        Spacer(modifier = Modifier.width(SpaceMedium))
        //Comment
        IconButton(onClick = {
            onCommentClicked()
        }, modifier = Modifier.size(iconSize)
        ) {
            Icon(
                imageVector = Icons.Default.Comment,
                contentDescription = stringResource(R.string.comment)
            )

        }
        Spacer(modifier = Modifier.width(SpaceMedium))

        //Share
        IconButton(onClick = {
            onShareClicked()
        }, modifier = Modifier.size(iconSize)
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share"
            )

        }


    }

}
@Composable
fun ActionRow (
    modifier: Modifier = Modifier,
    isLiked: Boolean = false,
    onLikeClicked: (Boolean) -> Unit = {},
    onCommentClicked: () -> Unit = {},
    onShareClicked: () -> Unit = {},
    onUserNameClicked: (String) -> Unit = {},
    userName: String = ""
){
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
        ) {
        TextButton(
            onClick = {
                onUserNameClicked(userName)
            },

        ) {
            Text(
                text = userName, style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold
            )

        }
        PostButtonRow(
            isLiked = isLiked,
            onLikeClicked = onLikeClicked,
            onCommentClicked = onCommentClicked,
            onShareClicked = onShareClicked,
        )
    }
}