package org.lotka.xenonx.presentation.composable.item

import android.nfc.cardemulation.CardEmulation
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.auth.User
import org.lotka.xenonx.domain.model.UserModel
import org.lotka.xenonx.presentation.R
import org.lotka.xenonx.presentation.util.Dimension.IconSizeMedium
import org.lotka.xenonx.presentation.util.Dimension.SpaceMedium
import org.lotka.xenonx.presentation.util.Dimension.SpaceSmall
import org.lotka.xenonx.presentation.util.Dimension.profilePictureSizeMedium
import org.lotka.xenonx.presentation.util.Dimension.profilePictureSmallSize

@Composable
fun UserProfileItem (
    modifier: Modifier = Modifier ,
    user:UserModel,
    actionItem : @Composable ()-> Unit = {},
    onCardClick : () -> Unit = {},
    onActionItemClick : () -> Unit = {},

){

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        onClick = onCardClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colors.onSurface,
        ),


    ) {

        Row (modifier = Modifier.fillMaxSize()
            .padding(SpaceMedium)
            , horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
            ){

            Image(painter = painterResource(id = R.drawable.arman ),
                contentDescription = null,
                modifier = Modifier
//                    .weight(1f)
                    .size(50.dp)
                    .clip(CircleShape)
                , contentScale = ContentScale.Crop

            )
             Column(
                 modifier = Modifier
                     .fillMaxHeight()
                     .weight(5f)
                     .padding(horizontal = SpaceMedium),
                 verticalArrangement = Arrangement.Center
             ) {

                 Text(text = user.userName
                 , style =  MaterialTheme.typography.body1.copy(
                     fontWeight = FontWeight.Bold
                 ))
                 Spacer(modifier = Modifier.height(SpaceSmall))

                 Text(text = user.description
                     , style =  MaterialTheme.typography.body2.copy(),
                     overflow = TextOverflow.Ellipsis,
                     maxLines = 2,
                     )


             }
            
            IconButton(onClick = onActionItemClick,
                modifier = Modifier
//                    .weight(2f)
                    .size(IconSizeMedium)
                ) {
                
                actionItem()
                
            }


        }


    }

}