package org.lotka.xenonx.presentation.screen.profile.composable

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.lotka.xenonx.presentation.R
import org.lotka.xenonx.presentation.util.Dimension.SpaceMedium
import org.lotka.xenonx.presentation.util.Dimension.SpaceSmall
import org.lotka.xenonx.presentation.util.toPx

@Composable
fun BannerSeaction(
    modifier: Modifier = Modifier,
    iconSize : Dp = 30.dp,
    onGithubClick : () ->Unit = {},
    onLinkedInClick : () ->Unit = {},
) {

    BoxWithConstraints(modifier = modifier){


        Image(painter = painterResource(id = R.drawable.newbanner) ,
            contentDescription = stringResource(R.string.banner_image),
            modifier = Modifier
                .fillMaxSize()
        )

        Box(modifier = Modifier.fillMaxSize()
            .background(brush = Brush.verticalGradient(
                colors = listOf(
                    Color.Transparent,
                    Color.Black
                ), startY = constraints.maxHeight - iconSize.toPx() * 2f
            )
            )
        )
        
        Row (
            modifier = Modifier
                .height(iconSize)
                .align(Alignment.BottomStart)
                .padding(SpaceSmall),



        ){
            Spacer(modifier = Modifier.width(SpaceSmall) )
            Image(painter = painterResource(id = R.drawable.c_sharp_logo),
                contentDescription = stringResource(R.string.c_sharp),
                modifier = Modifier.height(iconSize)
                )
            Spacer(modifier = Modifier.width(SpaceMedium) )

              Image(painter = painterResource(id = R.drawable.kotlin),
                contentDescription = stringResource(R.string.kotlin),
                  modifier = Modifier.height(iconSize)
              )



        }

        Row (
            modifier = Modifier
                .height(iconSize)
                .align(Alignment.BottomEnd)
                .padding(SpaceSmall),


            ){

            IconButton(onClick = { onGithubClick() }
            , modifier = Modifier.size(iconSize)

            ) {
                Image(painter = painterResource(id = R.drawable.github),
                    contentDescription = stringResource(R.string.github),

                    )
            }

         IconButton(onClick = { onLinkedInClick() }
         , modifier = Modifier.size(iconSize)
         ) {
             Image(painter = painterResource(id = R.drawable.linkedin),
                 contentDescription = stringResource(R.string.linkedin),

             )
         }





        }


    }




}