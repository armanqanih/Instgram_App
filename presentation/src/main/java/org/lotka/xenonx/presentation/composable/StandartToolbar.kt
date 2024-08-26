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
    onNavigateUp: () -> Unit = {},
    modifier: Modifier = Modifier,
    showBackArrow: Boolean = false,
    navAction: @Composable RowScope.() -> Unit = {},
    title: @Composable () -> Unit = {},
) {
    TopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = if(showBackArrow) {
            {
                IconButton(onClick = {
                    onNavigateUp()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back_arrow),
                        tint = MaterialTheme.colors.onBackground
                    )
                }
            }
        } else null,
        actions = navAction,
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 0.dp
    )
}