package org.lotka.xenonx.presentation.screen.profile


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import org.lotka.xenonx.domain.model.ActivityModel
import org.lotka.xenonx.domain.util.ActivityAction
import org.lotka.xenonx.domain.util.DataFormater
import org.lotka.xenonx.presentation.R
import org.lotka.xenonx.presentation.composable.StandardToolBar
import org.lotka.xenonx.presentation.screen.activity.compose.ActivityItem
import org.lotka.xenonx.presentation.util.Dimension.SpaceMedium
import kotlin.random.Random


@Composable
fun  ProfileScreen(
    navController: NavController,
) {

    Column(modifier = Modifier.fillMaxSize()) {
        StandardToolBar(
            navController = navController,
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = false,
            title = {
                Text(
                    text = stringResource(R.string.you_profile),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            },
            navAction = {
             IconButton(onClick = {  }) {
                 Icon(imageVector = Icons.Default.Menu,
                     contentDescription = "")
             }
            }
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
            , contentPadding = PaddingValues(SpaceMedium)
        ) {
            items(20) {


            }

        }


    }

}