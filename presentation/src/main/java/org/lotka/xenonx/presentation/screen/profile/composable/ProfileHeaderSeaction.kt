package org.lotka.xenonx.presentation.screen.profile.composable


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.lotka.xenonx.domain.model.UserModel
import org.lotka.xenonx.presentation.R
import org.lotka.xenonx.presentation.util.Dimension.SpaceLarge
import org.lotka.xenonx.presentation.util.Dimension.SpaceMedium
import org.lotka.xenonx.presentation.util.Dimension.SpaceSmall
import org.lotka.xenonx.presentation.util.Dimension.profilePictureSizeLarge

@Composable
fun ProfileHeaderSection(
    modifier: Modifier = Modifier,
    isOwnProfile : Boolean? = true ,
    user: UserModel,
    onEditClick : () -> Unit = {}
){

    Column(
        modifier = modifier
            .fillMaxWidth()
            .offset(y = -profilePictureSizeLarge / 2),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {



        Spacer(modifier = Modifier.height(SpaceSmall))

        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.offset(x =
            if (isOwnProfile == true) (30.dp + SpaceSmall) /2
            else 0.dp
            )
        ){
            Text(
                text = user.username,
                style = MaterialTheme.typography.h1.copy(
                    fontSize = 24.sp
                ),
                textAlign = TextAlign.Center
            )
            if (isOwnProfile == true){
                Spacer(modifier = Modifier.width(SpaceSmall))


                IconButton(onClick = { onEditClick() },
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.edit_icon) )
                }
            }


        }
        Spacer(modifier = Modifier.height(SpaceMedium))

        user.bio?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center

            )
        }
        }

        Spacer(modifier = Modifier.height(SpaceLarge))
        ProfileStats(user = user)


    }

