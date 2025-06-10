package com.example.azbuka.ui.navs.auth

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.azbuka.R
import com.example.azbuka.ui.navs.main.Nav
import com.example.azbuka.ui.theme.*
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Login(navController: NavController) {
    val context = LocalContext.current
    val auth = remember { FirebaseAuth.getInstance() }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    fun signIn() {
        val trimmedEmail = email.trim()
        val trimmedPassword = password.trim()

        when {
            trimmedEmail.isEmpty() || trimmedPassword.isEmpty() -> {
                Toast.makeText(context, R.string.login_error_empty, Toast.LENGTH_SHORT).show()
            }
            !Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches() -> {
                Toast.makeText(context, R.string.signup_invalid_email, Toast.LENGTH_SHORT).show()
            }
            trimmedPassword.length < 6 -> {
                Toast.makeText(context, R.string.signup_short_password, Toast.LENGTH_SHORT).show()
            }
            else -> {
                auth.signInWithEmailAndPassword(trimmedEmail, trimmedPassword)
                    .addOnSuccessListener {
                        navController.navigate(Nav.Main.route) {
                            popUpTo(Nav.Login.route) { inclusive = true }
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            context,
                            context.getString(R.string.login_error_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = PageHorizontalPadding, vertical = PageVerticalPadding),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                    contentDescription = stringResource(R.string.app_name),
                    modifier = Modifier.size(MediumIconSize)
                )
                Spacer(modifier = Modifier.width(SmallSpacer))
                Text(
                    text = stringResource(R.string.login_greeting),
                    style = Typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(ExtraLargeSpacer))

            Text(
                text = stringResource(R.string.login_subtitle),
                style = Typography.titleMedium,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(LargeSpacer))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(stringResource(R.string.login_email_label), color = TextDark) },
                placeholder = { Text(stringResource(R.string.login_email_placeholder)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(TextFieldCornerRadius),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = customTextFieldColors(),
                textStyle = Typography.bodyLarge.copy(color = TextDark)
            )

            Spacer(modifier = Modifier.height(MediumSpacer))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(R.string.login_password_label), color = TextDark) },
                placeholder = { Text(stringResource(R.string.login_password_placeholder)) },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = icon, contentDescription = null)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(TextFieldCornerRadius),
                colors = customTextFieldColors(),
                textStyle = Typography.bodyLarge.copy(color = TextDark)
            )

            Spacer(modifier = Modifier.height(SmallSpacer))

            Text(
                text = stringResource(R.string.btn_forgot_password),
                color = PrimaryViolet,
                style = Typography.CaptionText,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate(Nav.ResetPasswordEmail.route) }
                    .padding(end = SmallSpacer),
                textAlign = TextAlign.End
            )

            Spacer(modifier = Modifier.height(LargeSpacer))

            Button(
                onClick = { signIn() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(StandardButtonHeight),
                shape = RoundedCornerShape(TextFieldCornerRadius),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryViolet)
            ) {
                Text(
                    text = stringResource(R.string.btn_continue),
                    color = White,
                    style = Typography.ButtonText
                )
            }
        }

        Row(modifier = Modifier.padding(bottom = BottomPadding)) {
            Text(
                text = stringResource(R.string.login_no_account),
                style = Typography.CaptionText
            )
            Spacer(modifier = Modifier.width(SmallSpacer))
            Text(
                text = stringResource(R.string.btn_sign_up),
                color = PrimaryViolet,
                style = Typography.CaptionText,
                modifier = Modifier.clickable {
                    navController.navigate(Nav.SignUp.route)
                }
            )
        }
    }
}

@Composable
private fun customTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = FieldBackground,
    unfocusedContainerColor = FieldBackground,
    focusedBorderColor = FieldBorderFocused,
    unfocusedBorderColor = FieldBorderUnfocused,
    focusedTextColor = TextDark,
    unfocusedTextColor = TextDark,
    cursorColor = TextDark,
    focusedLabelColor = TextDark,
    unfocusedLabelColor = TextDark
)
