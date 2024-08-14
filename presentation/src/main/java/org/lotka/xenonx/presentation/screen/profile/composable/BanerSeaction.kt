package org.lotka.xenonx.presentation.screen.profile.composable

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.lotka.xenonx.presentation.R

@Composable
fun BannerSeaction(
    modifier: Modifier = Modifier,
    iconSize : Dp = 30.dp,
    onGithubClick : () ->Unit = {},
    onInstagramClick : () ->Unit = {},
    onLinkedInClick : () ->Unit = {},
) {

    Box(modifier = Modifier){
        Image(painter = painterResource(id = R.drawable.banner) ,
            contentDescription = stringResource(R.string.banner_image),
            modifier = Modifier.fillMaxSize()
        )

        Row (
            modifier = Modifier.size(iconSize),

        ){

        }

    }




}