package org.lotka.xenonx.presentation.ui.app

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
                    composable(
                        route = ScreensNavigation.ChatScreen.route,
                    ) {

                        ChatScreen(navController = navController)

                    }
                    composable(
                        route = ScreensNavigation.ActivityScreen.route,
                    ) {
                        ActivityScreen(navController = navController)
                    }

                    composable(
                        route = ScreensNavigation.ProfileScreen.route,
                    ) {
                        ProfileScreen(navController = navController)
                    }
                    composable(
                        route = ScreensNavigation.CreatePostScreen.route,
                    ) {
                        CreatePostScreen(navController = navController)
                    }
                    composable(
                        route = ScreensNavigation.PostDetailScreen.route,
                    ) {
                        PostDetailScreen(navController = navController,
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
                        route = ScreensNavigation.EditProfileScreen.route,
                    ) {
                        EditProfileScreen(navController = navController)
                    }
                    composable(
                        route = ScreensNavigation.SearchScreen.route,
                    ) {
                        SearchScreen(navController = navController)
                    }
                    composable(
                        route = ScreensNavigation.CreatePostScreen.route,
                    ) {
                        CreatePostScreen(navController = navController)
                    }

                    composable(
                        route = ScreensNavigation.PersonListScreen.route,
                    ) {
                        PersonListScreen(navController = navController)
                    }

                }

            },
        )

    }




