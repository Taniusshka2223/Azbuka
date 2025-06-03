package com.example.azbuka.ui.screens.main

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.unit.dp
import com.example.azbuka.ui.theme.BottomNavHeight
import com.example.azbuka.ui.theme.BottomNavIconSize
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.azbuka.R
import com.example.azbuka.ui.screens.book.BookScreen
import com.example.azbuka.ui.screens.cards.CardScreen
import com.example.azbuka.ui.screens.profile.ProfileScreenContent
import com.example.azbuka.ui.screens.rating.RatingScreen
import com.example.azbuka.ui.theme.*
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainScreen(navController: NavHostController) {
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = bottomNavController)
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavScreen.Cards.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavScreen.Cards.route) {
                CardScreen()
            }
            composable(BottomNavScreen.Book.route) {
                BookScreen()
            }
            composable(BottomNavScreen.Rating.route) {
                RatingScreen()
            }
            composable(BottomNavScreen.Profile.route) {
                ProfileScreenContent(
                    navController = bottomNavController,
                    onSignOut = {
                        FirebaseAuth.getInstance().signOut()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Main.route) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavScreen.Cards,
        BottomNavScreen.Book,
        BottomNavScreen.Rating,
        BottomNavScreen.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = FieldBackground,
        tonalElevation = 0.dp,
        modifier = Modifier.height(BottomNavHeight)
    ) {
        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = null,
                        modifier = Modifier.size(BottomNavIconSize)
                    )
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                alwaysShowLabel = false
            )
        }
    }
}

sealed class BottomNavScreen(val route: String, val icon: Int) {
    object Cards : BottomNavScreen("cards", R.drawable.cardsa)
    object Book : BottomNavScreen("book", R.drawable.book)
    object Rating : BottomNavScreen("rating", R.drawable.rating)
    object Profile : BottomNavScreen("profile", R.drawable.profile)
}