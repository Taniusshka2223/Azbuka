package com.example.azbuka.ui.navs.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.azbuka.R
import com.example.azbuka.ui.theme.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import androidx.compose.ui.res.stringResource

@Composable
fun SplashNav(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(2000)
        val isLoggedIn = FirebaseAuth.getInstance().currentUser != null
        navController.navigate(if (isLoggedIn) "main" else "welcome") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher),
                contentDescription = "App Logo",
                modifier = Modifier.size(SplashImageSize)
            )

            Spacer(modifier = Modifier.height(SplashSpacerHeight))

            Text(
                text = stringResource(id = R.string.app_title),
                style = AppTitleTextStyle
            )

        }
    }
}

