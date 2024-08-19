package org.lotka.xenonx.presentation.screen.activity.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.lotka.xenonx.domain.model.ActivityModel
import org.lotka.xenonx.domain.util.ActivityAction
import org.lotka.xenonx.presentation.R
import org.lotka.xenonx.presentation.util.Dimension.SpaceMedium


@Composable
fun ActivityItem(
    activity : ActivityModel ,
    modifier: Modifier = Modifier
){

   androidx.compose.material.Card(
       modifier = modifier,
       shape = MaterialTheme.shapes.medium,
       backgroundColor = MaterialTheme.colors.onSurface,
       elevation = 4.dp
   ) {

       Row (
           modifier = Modifier
               .fillMaxSize()
               .padding(SpaceMedium),
           horizontalArrangement = Arrangement.SpaceBetween,
           verticalAlignment = Alignment.CenterVertically
       ){

           val filterText = when(activity.actionType){
               ActivityAction.CommentedOnPost ->{
                   stringResource(R.string.Liked)
               }
               ActivityAction.LikePost -> {
                   stringResource(R.string.comment_on)
               }

               ActivityAction.FollowedYou -> {
                   stringResource(R.string.followed_you)
               }
           }

           val actionText = when(activity.actionType){
               ActivityAction.CommentedOnPost ->{
                   stringResource(R.string.your_post)
               }
               ActivityAction.LikePost -> {
                   stringResource(R.string.your_post)
               }

               ActivityAction.FollowedYou ->
                    ""
           }
           Text(text = buildAnnotatedString {
               val boldStyle = SpanStyle(fontWeight = FontWeight.Bold)
               withStyle(boldStyle){
                   append(activity.userName)
               }
               append(" $filterText ")
               withStyle(boldStyle){
                   append(actionText)
               }
           }, color = MaterialTheme.colors.onBackground,
                   modifier = Modifier.weight(8f)
               , fontSize = 12.sp
           )

           Text(text = activity.formatTime.toString(),
               textAlign = TextAlign.Right,
               color = MaterialTheme.colors.onBackground
               , modifier = Modifier.weight(3f)
               , fontSize = 12.sp
               )


       }

   }

}