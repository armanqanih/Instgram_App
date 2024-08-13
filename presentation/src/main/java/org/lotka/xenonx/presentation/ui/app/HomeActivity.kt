package org.lotka.xenonx.presentation.ui.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.lotka.xenonx.presentation.composable.StandardScaffold
import org.lotka.xenonx.presentation.theme.CleanArchitectureNoteAppTheme
import org.lotka.xenonx.presentation.ui.navigation.ScreensNavigation


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val navController = rememberNavController()
            val keyboardController = LocalSoftwareKeyboardController.current
            CleanArchitectureNoteAppTheme {


            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                if (keyboardController != null) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    StandardScaffold(
                        navController = navController,
                        showBottomBar = currentRoute in listOf(
                            ScreensNavigation.PostScreen.route,
                            ScreensNavigation.ChatScreen.route,
                            ScreensNavigation.ActivityScreen.route,
                            ScreensNavigation.ProfileScreen.route
                        ),
                        modifier = Modifier.fillMaxSize(),
                        onFabClick = {
                            navController.navigate(ScreensNavigation.CreatePostScreen.route)
                        }
                    ) {
                        HomeApp(
                            activity = this@HomeActivity,
                            navController = navController,
                            onNavigateToRecipeDetailScreen = {
                            },
                            isDarkTheme = false,
                            onToggleTheme = { },
                            keyboardController = keyboardController,

                            )
                    }
                    }
                }
            }
            }
        }





    }



