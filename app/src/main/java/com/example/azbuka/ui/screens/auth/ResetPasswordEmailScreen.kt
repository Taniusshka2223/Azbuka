package com.example.azbuka.ui.screens.auth

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.font.FontWeight
import com.google.firebase.auth.FirebaseAuth
import androidx.navigation.NavController
import com.example.azbuka.ui.theme.*
import com.example.azbuka.R

@Composable
fun ResetPasswordEmailScreen(navController: NavController) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    var email by remember { mutableStateOf("") }
    var isSent by remember { mutableStateOf(false) }

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
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
                text = stringResource(id = R.string.reset_password_title),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                color = TextDark
            )

            Spacer(modifier = Modifier.height(MediumSpacer))

            Text(
                text = stringResource(id = R.string.reset_password_subtitle),
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
                colors = OutlinedTextFieldDefaults.colors(
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
            )

            Spacer(modifier = Modifier.height(LargeSpacer))

            Button(
                onClick = {
                    when {
                        email.isBlank() -> {
                            Toast.makeText(context, context.getString(R.string.reset_password_toast_enter_email), Toast.LENGTH_SHORT).show()
                        }
                        !isValidEmail(email.trim()) -> {
                            Toast.makeText(context, context.getString(R.string.reset_password_toast_invalid_email), Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            auth.sendPasswordResetEmail(email.trim())
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        isSent = true
                                        Toast.makeText(
                                            context,
                                            context.getString(R.string.reset_password_toast_sent),
                                            Toast.LENGTH_LONG
                                        ).show()
                                    } else {
                                        Toast.makeText(
                                            context,
                                            context.getString(R.string.reset_password_toast_error, task.exception?.message ?: "Не удалось отправить ссылку"),
                                            Toast.LENGTH_LONG
                                        ).show()
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
                Text(stringResource(R.string.reset_password_send_button), style = MaterialTheme.typography.ButtonText, color = White)
            }
        }

        Text(
            text = stringResource(R.string.reset_password_back),
            style = MaterialTheme.typography.CaptionText,
            color = PrimaryViolet,
            modifier = Modifier
                .padding(top = MediumSpacer)
                .clickable { navController.popBackStack() }
        )
    }
}
