package org.lotka.xenonx.presentation.screen.edit_profile.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.lotka.xenonx.presentation.R
import org.lotka.xenonx.presentation.util.Dimension.profilePictureSizeLarge

@Composable
fun BannerEditSection(
  banner:Painter,
  profileImage:Painter,
  onBannerImageClick : () -> Unit = {},
  onProfileImageClick : () -> Unit = {},
  profilePictureSize : Dp = profilePictureSizeLarge
) {
    val bannerHeight = (LocalConfiguration.current.screenWidthDp / 2.5f).dp
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(bannerHeight + profilePictureSize / 2)
    ) {
        Image(painter = banner ,
            contentDescription = stringResource(R.string.banner_image),
            modifier = Modifier
                .fillMaxWidth()
                .height(bannerHeight)
                .clickable { onBannerImageClick() }
            ,
            contentScale = ContentScale.Crop
        )

        Image(
            painter = profileImage,
            contentDescription = "",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(profilePictureSize)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.onSurface,
                    shape = CircleShape
                )
                .clickable { onProfileImageClick() }

            ,
            contentScale = ContentScale.Crop

        )
    }


}