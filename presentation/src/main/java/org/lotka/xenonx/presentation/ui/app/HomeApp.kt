package org.lotka.xenonx.presentation.ui.app

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import org.lotka.xenonx.domain.model.PostModel
import org.lotka.xenonx.presentation.screen.edit_profile.EditProfileScreen
import org.lotka.xenonx.presentation.screen.activity.ActivityScreen
import org.lotka.xenonx.presentation.screen.profile.ProfileScreen
import org.lotka.xenonx.presentation.screen.chat.ChatScreen
import org.lotka.xenonx.presentation.screen.create_post.CreatePostScreen
import org.lotka.xenonx.presentation.screen.home.HomeScreen
import org.lotka.xenonx.presentation.screen.login.LoginScreen
import org.lotka.xenonx.presentation.screen.person_list_screen.PersonListScreen
import org.lotka.xenonx.presentation.screen.post.PostScreen
import org.lotka.xenonx.presentation.screen.post_detail.PostDetailScreen
import org.lotka.xenonx.presentation.screen.register.RegisterScreen
import org.lotka.xenonx.presentation.screen.search.SearchScreen
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


    Scaffold(
        content = { _ ->
            NavHost(
                navController = navController,
                startDestination = ScreensNavigation.LoginScreen.route
            ) {
                composable(
                    route = ScreensNavigation.SplashScreen.route,
                ) {

                    SplashScreen(navController = navController)

                }
                composable(
                    route = ScreensNavigation.LoginScreen.route,
                ) {

                    LoginScreen(
                        onNavigate = navController::navigate,
                        onNavigateUp = navController::navigateUp
                        )

                }
                composable(
                    route = ScreensNavigation.RegisterScreen.route,
                ) {

                    RegisterScreen(
                        onNavigate = navController::navigate,
                        onNavigateUp = navController::navigateUp
                    )

                }
                composable(
                    route = ScreensNavigation.HomeScreen.route,
                ) {

                    HomeScreen(
                        onNavigate = navController::navigate,
                        onNavigateUp = navController::navigateUp
                    )

                }
                composable(
                    route = ScreensNavigation.PostScreen.route,
                ) {

                    PostScreen(
                        onNavigate = navController::navigate,
                        onNavigateUp = navController::navigateUp
                    )

                }
                composable(
                    route = ScreensNavigation.ChatScreen.route,
                ) {

                    ChatScreen(
                        onNavigate = navController::navigate,
                        onNavigateUp = navController::navigateUp
                    )

                }
                composable(
                    route = ScreensNavigation.ActivityScreen.route,
                ) {
                    ActivityScreen(
                        onNavigate = navController::navigate,
                        onNavigateUp = navController::navigateUp
                    )
                }

                composable(
                    route = ScreensNavigation.ProfileScreen.route +"?userId={userId}",
                    arguments = listOf(navArgument("userId") {
                        type = NavType.StringType
                        defaultValue = null
                        nullable = true
                    })
                ) {
                    ProfileScreen(
                        userId = it.arguments?.getString("userId") ?: "" ,
                        onNavigate = navController::navigate,
                        onNavigateUp = navController::navigateUp
                    )
                }
                composable(
                    route = ScreensNavigation.CreatePostScreen.route,
                ) {
                    CreatePostScreen(
                        onNavigate = navController::navigate,
                        onNavigateUp = navController::navigateUp
                    )
                }
                composable(
                    route = ScreensNavigation.PostDetailScreen.route,
                ) {
                    PostDetailScreen(
                        onNavigate = navController::navigate,
                        onNavigateUp = navController::navigateUp,
                        postModel = PostModel(
                            id = 1,
                            userName = "Arman Sherwamii",
                            profileImage = "",
                            postImage = "",
                            description = "ahahaha",
                            likes = 17,
                            comments = 7
                        )
                    )
                }

                composable(
                    route = ScreensNavigation.EditProfileScreen.route+"?userId={userId}",
                    arguments = listOf(navArgument("userId")
                    {
                        type = NavType.StringType
                    }) ) {
                    EditProfileScreen(
                        onNavigate = navController::navigate,
                        onNavigateUp = navController::navigateUp
                    )
                }
                composable(
                    route = ScreensNavigation.SearchScreen.route,
                ) {
                    SearchScreen(
                        onNavigate = navController::navigate,
                        onNavigateUp = navController::navigateUp
                    )
                }
                composable(
                    route = ScreensNavigation.CreatePostScreen.route,
                ) {
                    CreatePostScreen(
                        onNavigate = navController::navigate,
                        onNavigateUp = navController::navigateUp
                    )
                }

                composable(
                    route = ScreensNavigation.PersonListScreen.route,
                ) {
                    PersonListScreen(
                        onNavigate = navController::navigate,
                        onNavigateUp = navController::navigateUp
                    )
                }

            }

        },
    )

}




