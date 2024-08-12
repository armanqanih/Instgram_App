package org.lotka.xenonx.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AddAlert
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import org.lotka.xenonx.presentation.R
import org.lotka.xenonx.presentation.composable.item.BottomNavItem


@Composable
fun StandardScaffold(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: StandardScaffoldViewModel = hiltViewModel(),
    bottomNavItems: List<BottomNavItem> = listOf(),
    content: @Composable () -> Unit
) {

    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                cutoutShape = CircleShape,
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 5.dp
            ) {



                BottomNavigation(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    for (item in bottomNavItems.indices) {
                        StandardBottomNavigationItem(
                            icon = bottomNavItems[item].icon,
                            contentDescription = stringResource(id = R.string.home),
                            selected =viewModel.selectBottomNavItem.value == item,
                            alertCount = bottomNavItems[item].alertCount,
                            onClick = { /*TODO*/ }
                        )
                    }



                    StandardBottomNavigationItem(
                        icon = Icons.Default.ChatBubbleOutline,
                        contentDescription = stringResource(R.string.chat),
                        selected = false,

                        onClick = { /*TODO*/ }
                    )
                    StandardBottomNavigationItem(
                        icon = Icons.Outlined.AddAlert,
                        contentDescription = "",
                        selected = false,
                        onClick = { /*TODO*/ }
                    )
                    StandardBottomNavigationItem(
                        icon = Icons.Outlined.Person,
                        contentDescription = "",
                        selected = false,
                        alertCount = 100,
                        onClick = { /*TODO*/ }
                    )


                }

            }
        },
        modifier = modifier


    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {

        }


    }
}