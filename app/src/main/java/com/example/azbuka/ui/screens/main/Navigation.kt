package com.example.azbuka.ui.screens.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.azbuka.ui.screens.auth.LoginScreen
import com.example.azbuka.ui.screens.auth.ResetPasswordEmailScreen
import com.example.azbuka.ui.screens.auth.SignUpScreen
import com.example.azbuka.ui.screens.book.BookScreen
import com.example.azbuka.ui.screens.rating.RatingScreen
import com.example.azbuka.ui.screens.onboarding.WelcomeScreen
import com.example.azbuka.ui.screens.splash.SplashScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object Main : Screen("main")

    object Book : Screen("book")
    object Rating : Screen("rating")
    object ResetPasswordEmail : Screen("reset_password_email")
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {

        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }

        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onStartClicked = { navController.navigate(Screen.SignUp.route) },
                onLoginClicked = { navController.navigate(Screen.Login.route) }
            )
        }
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(Screen.SignUp.route) {
            SignUpScreen(navController)
        }
        composable(Screen.Main.route) {
            MainScreen(navController)
        }
        composable(Screen.Book.route) {
            BookScreen()
        }
        composable(Screen.Rating.route) {
            RatingScreen()
        }
        composable(Screen.ResetPasswordEmail.route) {
            ResetPasswordEmailScreen(navController)
        }
    }
}