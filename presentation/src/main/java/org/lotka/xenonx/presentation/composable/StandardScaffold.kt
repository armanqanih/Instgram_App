package org.lotka.xenonx.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddAlert
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.lotka.xenonx.presentation.R
import org.lotka.xenonx.presentation.composable.item.BottomNavItem
import org.lotka.xenonx.presentation.ui.navigation.ScreensNavigation






@Composable
fun StandardScaffold(
    navController: NavController,
    modifier: Modifier = Modifier,
    showBottomBar: Boolean = true,
    bottomNavItems: List<BottomNavItem> = listOf(
        BottomNavItem(
            route = ScreensNavigation.PostScreen.route,
            icon = Icons.Outlined.Home
        ),
        BottomNavItem(
            route = ScreensNavigation.ChatScreen.route,
            icon = Icons.Outlined.ChatBubbleOutline
        ),

        BottomNavItem(
            route = ScreensNavigation.ActivityScreen.route,
            icon = Icons.Outlined.AddAlert
        ),

        BottomNavItem(
            route = ScreensNavigation.ProfileScreen.route,
            icon = Icons.Outlined.Person
        )
    ),
    content: @Composable () -> Unit
) {

    Scaffold(
        bottomBar = {
            if (showBottomBar){
                BottomAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    cutoutShape = CircleShape,
                    backgroundColor = MaterialTheme.colors.surface,
                    elevation = 5.dp
                ) {

                    BottomNavigation(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        bottomNavItems.forEachIndexed { index, item ->
                            StandardBottomNavigationItem(
                                icon = item.icon,
                                contentDescription = stringResource(id = R.string.home),
                                selected = item.route == navController.currentDestination?.route,
                                alertCount = item.alertCount,
                                onClick = {
                                    navController.navigate(item.route)
                                }
                            ) }

                    }

                }
            }

        },
        modifier = modifier


    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
        content()

        }


    }
}