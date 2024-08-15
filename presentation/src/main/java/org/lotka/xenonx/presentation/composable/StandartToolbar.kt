package org.lotka.xenonx.presentation.composable

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.lotka.xenonx.presentation.R

@Composable
fun StandardToolBar(
    navController : NavController,
    modifier: Modifier = Modifier ,
    showBackArrow : Boolean = true ,
    navAction : @Composable RowScope.()-> Unit = {},
   title : @Composable () -> Unit = {}
){

   TopAppBar(
       modifier = modifier,
       navigationIcon = {
           if (showBackArrow){
               IconButton(onClick = {
                   navController.navigateUp()
               }) {
                   Icon(imageVector = Icons.Default.ArrowBack
                       , contentDescription = stringResource(R.string.arrowback),
                       tint = MaterialTheme.colors.onBackground
                   )
               }
           }
       },
       title = title,
       actions = navAction,
       backgroundColor = MaterialTheme.colors.surface,
       elevation = 0.dp
   )

}