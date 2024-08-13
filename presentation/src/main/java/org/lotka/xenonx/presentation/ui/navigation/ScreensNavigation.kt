package org.lotka.xenonx.presentation.ui.navigation

sealed class ScreensNavigation(val route: String) {
    object SplashScreen : ScreensNavigation(route = "splash_screen")
    object LoginScreen : ScreensNavigation(route = "login_screen")
    object RegisterScreen : ScreensNavigation(route = "register_screen")
    object PostDetailScreen : ScreensNavigation(route = "post_detail_screen")
    object PostScreen : ScreensNavigation(route = "post_screen")
    object ChatScreen : ScreensNavigation(route = "chat_screen")
    object ProfileScreen : ScreensNavigation(route = "profile_screen")
    object EditProfileScreen : ScreensNavigation(route = "edit_profile_screen")
    object PersonListScreen : ScreensNavigation(route = "person_list_screen")
    object CreatePostScreen : ScreensNavigation(route = "create_post_screen")
    object SearchScreen : ScreensNavigation(route = "search_screen")
    object ActivityScreen : ScreensNavigation(route = "activity_screen")
}