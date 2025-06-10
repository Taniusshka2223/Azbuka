package com.example.azbuka.ui.navs.auth

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import com.example.azbuka.R
import com.example.azbuka.ui.theme.*
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ResetPasswordEmailNav(navController: NavController) {
    val context = LocalContext.current
    val auth = remember { FirebaseAuth.getInstance() }

    var email by remember { mutableStateOf("") }
    var isSent by remember { mutableStateOf(false) }

    val isValidEmail: (String) -> Boolean = {
        android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = PageHorizontalPadding, vertical = PageVerticalPadding),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = stringResource(R.string.reset_password_title),
                style = Typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                color = TextDark
            )

            Spacer(modifier = Modifier.height(MediumSpacer))

            Text(
                text = stringResource(R.string.reset_password_subtitle),
                style = Typography.titleMedium,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(LargeSpacer))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    isSent = false
                },
                label = { Text(stringResource(R.string.reset_password_email_label), color = TextDark) },
                placeholder = { Text(stringResource(R.string.reset_password_email_placeholder)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(TextFieldCornerRadius),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = customTextFieldColors(),
                textStyle = Typography.bodyLarge.copy(color = TextDark)
            )

            Spacer(modifier = Modifier.height(LargeSpacer))

            Button(
                onClick = {
                    val trimmedEmail = email.trim()
                    when {
                        trimmedEmail.isEmpty() -> {
                            context.showToast(R.string.reset_password_toast_enter_email)
                        }

                        !isValidEmail(trimmedEmail) -> {
                            context.showToast(R.string.reset_password_toast_invalid_email)
                        }

                        else -> {
                            auth.sendPasswordResetEmail(trimmedEmail)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        isSent = true
                                        context.showToast(R.string.reset_password_toast_sent, Toast.LENGTH_LONG)
                                    } else {
                                        val errorMessage = task.exception?.message
                                            ?: context.getString(R.string.reset_password_toast_unknown_error)
                                        context.showToast(errorMessage, Toast.LENGTH_LONG)
                                    }
                                }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(StandardButtonHeight),
                shape = RoundedCornerShape(TextFieldCornerRadius),
                enabled = !isSent,
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryViolet)
            ) {
                Text(
                    text = stringResource(R.string.reset_password_send_button),
                    style = Typography.ButtonText,
                    color = White
                )
            }
        }

        Text(
            text = stringResource(R.string.reset_password_back),
            style = Typography.CaptionText,
            color = PrimaryViolet,
            modifier = Modifier
                .padding(top = MediumSpacer)
                .clickable { navController.popBackStack() }
        )
    }
}

private fun android.content.Context.showToast(resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, getString(resId), duration).show()
}

private fun android.content.Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
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
