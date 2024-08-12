package org.lotka.xenonx.presentation.ui.app

import android.annotation.SuppressLint
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import org.lotka.xenonx.presentation.screen.home.HomeScreen
import org.lotka.xenonx.presentation.screen.login.LoginScreen
import org.lotka.xenonx.presentation.screen.post.PostScreen
import org.lotka.xenonx.presentation.screen.register.RegisterScreen
import org.lotka.xenonx.presentation.screen.splash.SplashScreen


import org.lotka.xenonx.presentation.ui.navigation.ScreensNavigation


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(
    ExperimentalPagerApi::class, ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun HomeApp(
    activity: HomeActivity,
    navController: NavHostController,
    onNavigateToRecipeDetailScreen: (String) -> Unit,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    keyboardController: SoftwareKeyboardController,

    ) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val scaffoldState = rememberScaffoldState()




    Scaffold(
        content = { _ ->
            NavHost(
                navController = navController,
                startDestination = ScreensNavigation.SplashScreen.route
            ) {
                composable(route = ScreensNavigation.SplashScreen.route,
                ) {

                    SplashScreen(navController = navController)

                }
                composable(
                    route = ScreensNavigation.LoginScreen.route,
                ) {

                    LoginScreen(navController = navController)

                }
                composable(
                    route = ScreensNavigation.RegisterScreen.route,
                ) {

                    RegisterScreen(navController = navController)

                }
                composable(
                    route = ScreensNavigation.ChatScreen.route,
                ) {

                    HomeScreen()

                }
                composable(
                    route = ScreensNavigation.PostScreen.route,
                ) {

                    PostScreen(navController = navController)

                }


            }

        },
    )

}



