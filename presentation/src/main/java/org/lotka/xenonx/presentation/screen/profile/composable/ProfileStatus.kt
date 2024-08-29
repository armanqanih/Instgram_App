package org.lotka.xenonx.presentation.screen.profile.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.lotka.xenonx.domain.model.UserModel
import org.lotka.xenonx.presentation.R
import org.lotka.xenonx.presentation.theme.LightGray
import org.lotka.xenonx.presentation.theme.TextGray
import org.lotka.xenonx.presentation.util.Dimension.SpaceMedium

@Composable
fun ProfileStats(
    user: UserModel,
    modifier: Modifier = Modifier,
    isOwnProfile : Boolean = true,
    isFollowing : Boolean = true,
    onFlowClick: () -> Unit = {},
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {


            ProfileNumber(
                number = user.followerCount ?: 0,
                text = stringResource(R.string.follower)
            )
       Spacer(modifier = Modifier.width(SpaceMedium))

            ProfileNumber(
                number = user.followingCount ?: 0,
                text = stringResource(R.string.following)
            )

        Spacer(modifier = Modifier.width(SpaceMedium))

            ProfileNumber(
                number = user.postCount ?: 0,
                text = stringResource(R.string.posts)
            )

        if (!isOwnProfile){
            Spacer(modifier = Modifier.width(SpaceMedium))
            Button(onClick = { onFlowClick() }
                , colors = ButtonDefaults.buttonColors(
                    backgroundColor = if(isFollowing) LightGray
                    else MaterialTheme.colors.primary
                ),

                ) {
                Text(
                    text = if (isFollowing)
                        stringResource(R.string.unfollow)
                    else stringResource(R.string.follow),
                    color = if (isFollowing) Color.White else Color.Black
                )
            }
        }

    }


}