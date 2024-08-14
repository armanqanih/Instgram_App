package org.lotka.xenonx.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Add

import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
    onFabClick: () -> Unit = {},
    content: @Composable () -> Unit,
) {

    val bottomNavItems: List<BottomNavItem> = listOf(
        BottomNavItem(
            route = ScreensNavigation.PostScreen.route,
            icon = Icons.Outlined.Home,
            contentDescription = stringResource(id = R.string.home)
        ),
        BottomNavItem(
            route = ScreensNavigation.ChatScreen.route,
            icon = Icons.Outlined.ChatBubbleOutline,
            contentDescription = stringResource(id = R.string.chat)
        ),
        BottomNavItem(
            route = "",
        ),

        BottomNavItem(
            route = ScreensNavigation.ActivityScreen.route,
            icon = Icons.Outlined.Notifications,
            contentDescription = stringResource(R.string.notifications)
        ),

        BottomNavItem(
            route = ScreensNavigation.ProfileScreen.route,
            icon = Icons.Outlined.Person,
            contentDescription = stringResource(R.string.profile),
        )
    )


    Scaffold(
        bottomBar = {
            if (showBottomBar) {
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
                                enabled = item.icon != null,
                                onClick = {
                                    if(navController.currentDestination?.route != item.route){
                                    navController.navigate(item.route)
                                    }
                                }
                            )
                        }

                    }

                }
            }

        },
        modifier = modifier,
        floatingActionButton = {
            if (showBottomBar) {
                FloatingActionButton(
                    onClick = onFabClick,
                    backgroundColor = MaterialTheme.colors.primary
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = stringResource(R.string.add_post),

                        )
                }
        }
            }, isFloatingActionButtonDocked = true,
            floatingActionButtonPosition = FabPosition.Center

    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            content()

        }


    }
}