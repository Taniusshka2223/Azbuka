package com.example.azbuka.ui.navs.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.azbuka.ui.navs.auth.Login
import com.example.azbuka.ui.navs.auth.ResetPasswordEmailNav
import com.example.azbuka.ui.navs.auth.SignUpNav
import com.example.azbuka.ui.navs.book.Book
import com.example.azbuka.ui.navs.rating.RatingNav
import com.example.azbuka.ui.navs.onboarding.WelcomeNav
import com.example.azbuka.ui.navs.splash.SplashNav

sealed class Nav(val route: String) {
    object Splash : Nav("splash")
    object Welcome : Nav("welcome")
    object Login : Nav("login")
    object SignUp : Nav("signup")
    object Main : Nav("main")

    object Book : Nav("book")
    object Rating : Nav("rating")
    object ResetPasswordEmail : Nav("reset_password_email")
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Nav.Splash.route) {

        composable(Nav.Splash.route) {
            SplashNav(navController)
        }

        composable(Nav.Welcome.route) {
            WelcomeNav(
                onStartClicked = { navController.navigate(Nav.SignUp.route) },
                onLoginClicked = { navController.navigate(Nav.Login.route) }
            )
        }
        composable(Nav.Login.route) {
            Login(navController)
        }
        composable(Nav.SignUp.route) {
            SignUpNav(navController)
        }
        composable(Nav.Main.route) {
            MainNav(navController)
        }
        composable(Nav.Book.route) {
            Book()
        }
        composable(Nav.Rating.route) {
            RatingNav()
        }
        composable(Nav.ResetPasswordEmail.route) {
            ResetPasswordEmailNav(navController)
        }
    }
}