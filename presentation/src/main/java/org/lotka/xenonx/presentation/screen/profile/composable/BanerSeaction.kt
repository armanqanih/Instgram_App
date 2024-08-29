package org.lotka.xenonx.presentation.screen.profile.composable

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import org.lotka.xenonx.domain.model.Skills
import org.lotka.xenonx.presentation.R
import org.lotka.xenonx.presentation.util.Dimension.SpaceMedium
import org.lotka.xenonx.presentation.util.Dimension.SpaceSmall
import org.lotka.xenonx.presentation.util.Dimension.profilePictureSizeLarge
import org.lotka.xenonx.presentation.util.toPx

@Composable
fun BannerSeaction(
    modifier: Modifier = Modifier,
    iconSize : Dp = 35.dp,
    iconModifier: Modifier = Modifier ,
    topSkills : List<Skills> = emptyList(),
    bannerUrl : String? = null,
    shouldShowGithub : Boolean = false,
    shouldShowLinkedIn : Boolean = false,
    onGithubClick : () ->Unit = {},
    onLinkedInClick : () ->Unit = {},
) {

        BoxWithConstraints(
            modifier = modifier){


            bannerUrl.let {bannerUrl->
                Image(painter = rememberImagePainter(
                    data = bannerUrl,
                    builder = {
                        crossfade(true)
                    }
                ) ,
                    contentDescription = stringResource(R.string.banner_image),
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }


            Box(modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ), startY = constraints.maxHeight - iconSize.toPx() * 2f
                    )
                )
            )

            Row (
                modifier = iconModifier
                    .height(iconSize)
                    .align(Alignment.BottomStart)
                    .padding(SpaceSmall),



                ){

                topSkills.forEach {
                    Spacer(modifier = Modifier.width(SpaceSmall) )
                    Image(painter = rememberImagePainter(
                        data = topSkills,
                        builder = {
                            crossfade(true) }
                    ),
                        contentDescription = null,
                        modifier = Modifier.height(iconSize)
                    )



                }





            }

            Row (
                modifier = iconModifier
                    .height(iconSize)
                    .align(Alignment.BottomEnd)
                    .padding(SpaceSmall),


                ){
               if (shouldShowGithub){
                    IconButton(onClick = { onGithubClick() }
                        , modifier = Modifier.size(iconSize)

                    ) {
                        Image(painter = painterResource(id = R.drawable.github),
                            contentDescription = stringResource(R.string.github),
                            ) }
                }

              if (shouldShowLinkedIn){
                   IconButton(onClick = { onLinkedInClick() }
                       , modifier = Modifier.size(iconSize)
                   ) {
                       Image(painter = painterResource(id = R.drawable.linkedin),
                           contentDescription = stringResource(R.string.linkedin),

                           ) }

               }







            }



        }



    }


