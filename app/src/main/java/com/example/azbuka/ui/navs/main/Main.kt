package com.example.azbuka.ui.navs.main

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
import com.example.azbuka.ui.navs.book.Book
import com.example.azbuka.ui.navs.cards.CardNav
import com.example.azbuka.ui.navs.profile.ProfileNavContent
import com.example.azbuka.ui.navs.rating.RatingNav
import com.example.azbuka.ui.theme.*
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainNav(navController: NavHostController) {
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = bottomNavController)
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavNav.Cards.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavNav.Cards.route) {
                CardNav()
            }
            composable(BottomNavNav.Book.route) {
                Book()
            }
            composable(BottomNavNav.Rating.route) {
                RatingNav()
            }
            composable(BottomNavNav.Profile.route) {
                ProfileNavContent(
                    onSignOut = {
                        FirebaseAuth.getInstance().signOut()
                        navController.navigate(Nav.Login.route) {
                            popUpTo(Nav.Main.route) { inclusive = true }
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
        BottomNavNav.Cards,
        BottomNavNav.Book,
        BottomNavNav.Rating,
        BottomNavNav.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = FieldBackground,
        tonalElevation = 0.dp,
        modifier = Modifier.height(BottomNavHeight)
    ) {
        items.forEach { nav ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = nav.icon),
                        contentDescription = null,
                        modifier = Modifier.size(BottomNavIconSize)
                    )
                },
                selected = currentRoute == nav.route,
                onClick = {
                    navController.navigate(nav.route) {
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

sealed class BottomNavNav(val route: String, val icon: Int) {
    object Cards : BottomNavNav("cards", R.drawable.cardsa)
    object Book : BottomNavNav("book", R.drawable.book)
    object Rating : BottomNavNav("rating", R.drawable.rating)
    object Profile : BottomNavNav("profile", R.drawable.profile)
}