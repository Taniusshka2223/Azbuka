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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.azbuka.R
import com.example.azbuka.ui.theme.*
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SignUpNav(navController: NavController) {
    val context = LocalContext.current
    val auth = remember { FirebaseAuth.getInstance() }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    fun signUp() {
        val trimmedEmail = email.trim()
        val trimmedPassword = password.trim()

        when {
            trimmedEmail.isEmpty() || trimmedPassword.isEmpty() -> {
                Toast.makeText(context, R.string.signup_invalid_data, Toast.LENGTH_SHORT).show()
            }

            !Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches() -> {
                Toast.makeText(context, R.string.signup_invalid_email, Toast.LENGTH_SHORT).show()
            }

            trimmedPassword.length < 6 -> {
                Toast.makeText(context, R.string.signup_short_password, Toast.LENGTH_SHORT).show()
            }

            else -> {
                auth.createUserWithEmailAndPassword(trimmedEmail, trimmedPassword)
                    .addOnSuccessListener { navController.navigate("main") }
                    .addOnFailureListener {
                        Toast.makeText(context, R.string.signup_failed, Toast.LENGTH_SHORT).show()
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
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(TextFieldCornerRadius),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = textFieldColors()
            )

            Spacer(modifier = Modifier.height(MediumSpacer))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.password_rule_length),
                    style = PasswordRuleTextStyle
                )
                Text(
                    text = stringResource(R.string.password_rule_special),
                    style = PasswordRuleTextStyle
                )
                Text(
                    text = stringResource(R.string.password_rule_digit),
                    style = PasswordRuleTextStyle
                )
            }


            Spacer(modifier = Modifier.height(LargeSpacer))
            Button(
                onClick = { signUp() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(StandardButtonHeight),
                shape = RoundedCornerShape(TextFieldCornerRadius),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryViolet)
            ) {
                Text(
                    text = stringResource(R.string.btn_continue),
                    style = MaterialTheme.typography.ButtonText,
                    color = White
                )
            }
        }

        Row(modifier = Modifier.padding(bottom = BottomPadding)) {
            Text(stringResource(R.string.login_no_account), style = MaterialTheme.typography.CaptionText)
            Spacer(modifier = Modifier.width(4.dp))
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
