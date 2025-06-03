package com.example.azbuka.ui.screens.auth

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
import androidx.navigation.NavController
import com.example.azbuka.R
import com.example.azbuka.ui.theme.*
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SignUpScreen(navController: NavController) {
    val context = LocalContext.current
    val auth = remember { FirebaseAuth.getInstance() }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

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
                Text(stringResource(R.string.login_greeting), style = Typography.titleLarge)
            }

            Spacer(modifier = Modifier.height(ExtraLargeSpacer))

            Text(
                stringResource(R.string.signup_subtitle),
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
                colors = textFieldColors()
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
                colors = textFieldColors()
            )

            Spacer(modifier = Modifier.height(LargeSpacer))

            Button(
                onClick = {
                    if (email.isNotBlank() && password.length >= 6) {
                        auth.createUserWithEmailAndPassword(email.trim(), password.trim())
                            .addOnSuccessListener { navController.navigate("main") }
                            .addOnFailureListener {
                                Toast.makeText(context, context.getString(R.string.signup_failed), Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(context, context.getString(R.string.signup_invalid_data), Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(StandardButtonHeight),
                shape = RoundedCornerShape(TextFieldCornerRadius),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryViolet)
            ) {
                Text(stringResource(R.string.btn_continue), style = MaterialTheme.typography.ButtonText, color = White)
            }
        }

        Row(modifier = Modifier.padding(bottom = BottomPadding)) {
            Text(stringResource(R.string.login_no_account), style = MaterialTheme.typography.CaptionText)
            Text(
                text = stringResource(R.string.login_signin),
                color = PrimaryViolet,
                style = MaterialTheme.typography.CaptionText,
                modifier = Modifier.clickable { navController.navigate("login") }
            )
        }
    }
}

@Composable
private fun textFieldColors(): TextFieldColors {
    return OutlinedTextFieldDefaults.colors(
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
}