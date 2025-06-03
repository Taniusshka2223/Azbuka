package com.example.azbuka.ui.screens.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.ui.unit.dp
import com.example.azbuka.R
import com.example.azbuka.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreenContent(
    navController: NavController,
    onSignOut: () -> Unit
) {
    val viewModel: ProfileViewModel = viewModel()
    val email by viewModel.email.collectAsState()
    val passwordResult by viewModel.passwordUpdateResult.collectAsState()
    val context = LocalContext.current

    val regular = FontFamily(Font(R.font.sf_pro_text_regular))
    val semibold = FontFamily(Font(R.font.sf_pro_text_semibold))
    val bold = FontFamily(Font(R.font.sf_pro_text_bold))

    var isEditing by remember { mutableStateOf(false) }
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val successPasswordUpdatedText = stringResource(R.string.success_password_updated)
    val errorFillAllFieldsText = stringResource(R.string.error_fill_all_fields)
    val errorPasswordsMismatchText = stringResource(R.string.error_passwords_mismatch)

    LaunchedEffect(passwordResult) {
        when (passwordResult) {
            is ProfileViewModel.ResultState.Success -> {
                Toast.makeText(context, successPasswordUpdatedText, Toast.LENGTH_SHORT).show()
                currentPassword = ""
                newPassword = ""
                confirmPassword = ""
                isEditing = false
                viewModel.clearResult()
            }
            is ProfileViewModel.ResultState.Error -> {
                Toast.makeText(context, (passwordResult as ProfileViewModel.ResultState.Error).message, Toast.LENGTH_LONG).show()
                viewModel.clearResult()
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(horizontal = ProfileHorizontalPadding)
    ) {
        Header(text = stringResource(R.string.profile_screen_title), font = semibold)

        Spacer(modifier = Modifier.height(ProfileVerticalSpacing))

        Text(
            text = stringResource(R.string.profile_settings),
            fontSize = Title22,
            fontFamily = semibold,
            color = TextPrimary
        )

        Spacer(modifier = Modifier.height(ProfileVerticalSpacing))

        TextFieldSection(
            label = stringResource(R.string.profile_email_label),
            value = email,
            font = regular
        )

        if (!isEditing) {
            TextFieldSection(
                label = stringResource(R.string.profile_password_label),
                value = "********",
                font = regular
            )
        } else {
            PasswordInputField(
                label = stringResource(R.string.profile_current_password),
                value = currentPassword,
                font = regular,
                onValueChange = { currentPassword = it }
            )
            PasswordInputField(
                label = stringResource(R.string.profile_new_password),
                value = newPassword,
                font = regular,
                onValueChange = { newPassword = it }
            )
            PasswordInputField(
                label = stringResource(R.string.profile_confirm_password),
                value = confirmPassword,
                font = regular,
                onValueChange = { confirmPassword = it }
            )

            Spacer(modifier = Modifier.height(ProfileVerticalSpacing))

            Button(
                onClick = {
                    when {
                        currentPassword.isBlank() || newPassword.isBlank() || confirmPassword.isBlank() -> {
                            Toast.makeText(context, errorFillAllFieldsText, Toast.LENGTH_SHORT).show()
                        }
                        newPassword != confirmPassword -> {
                            Toast.makeText(context, errorPasswordsMismatchText, Toast.LENGTH_SHORT).show()
                        }
                        else -> viewModel.updatePassword(currentPassword, newPassword)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ProfileButtonHeight),
                shape = RoundedCornerShape(ProfileTextFieldCornerRadius),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor)
            ) {
                Text(
                    text = stringResource(R.string.profile_button_save),
                    fontSize = ButtonText16,
                    fontFamily = semibold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(ProfileVerticalSpacing))

            Text(
                text = stringResource(R.string.profile_button_back),
                fontSize = ButtonText16,
                fontFamily = semibold,
                color = PrimaryColor,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        isEditing = false
                        currentPassword = ""
                        newPassword = ""
                        confirmPassword = ""
                    }
            )
        }

        if (!isEditing) {
            Spacer(modifier = Modifier.height(ProfileVerticalSpacing))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.profile_change_password_question),
                    fontSize = Dimens.Body16,
                    fontFamily = regular,
                    color = TextPrimary
                )
                Text(
                    text = stringResource(R.string.profile_change_password_action),
                    fontSize = Dimens.Body16,
                    fontFamily = regular,
                    color = PrimaryColor,
                    modifier = Modifier.clickable {
                        isEditing = true
                    }
                )
            }
        }

        if (passwordResult is ProfileViewModel.ResultState.Loading) {
            Spacer(modifier = Modifier.height(ProfileVerticalSpacing))
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                color = PrimaryColor
            )
        }

        Spacer(modifier = Modifier.height(ProfileSectionBottomPadding))

        Text(
            text = stringResource(R.string.profile_logout),
            fontSize = LogoutText22,
            fontFamily = bold,
            color = PrimaryColor,
            modifier = Modifier.clickable { onSignOut() }
        )
    }
}

@Composable
private fun Header(text: String, font: FontFamily) {
    Box(
        modifier = Modifier
            .padding(top = ProfileTopPadding)
            .fillMaxWidth()
            .height(ProfileHeaderHeight)
            .background(PrimaryColor, RoundedCornerShape(ProfileTextFieldCornerRadius)),
        contentAlignment = Alignment.Center
    ) {
        Text(text, fontSize = Title20, fontFamily = font, color = Color.White)
    }
}

@Composable
private fun TextFieldSection(label: String, value: String, font: FontFamily) {
    Column(modifier = Modifier.padding(bottom = ProfileVerticalSpacing)) {
        Text(label, fontSize = Label12, fontFamily = font, color = TextPrimary)
        Box(
            modifier = Modifier
                .padding(top = ProfileTextFieldTopPadding)
                .fillMaxWidth()
                .height(ProfileTextFieldHeight)
                .background(TextFieldBackground, RoundedCornerShape(ProfileTextFieldCornerRadius))
                .border(1.dp, TextFieldBorder, RoundedCornerShape(ProfileTextFieldCornerRadius)),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = value,
                fontSize = Label12,
                fontFamily = font,
                color = TextSecondary,
                modifier = Modifier.padding(start = ProfileTextFieldTopPadding)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInputField(label: String, value: String, font: FontFamily, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(bottom = ProfileVerticalSpacing)) {
        Text(label, fontSize = Label12, fontFamily = font, color = TextPrimary)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = stringResource(R.string.profile_password_placeholder, label),
                    fontFamily = font,
                    fontSize = Label12,
                    color = TextPrimary
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryColor,
                unfocusedBorderColor = TextFieldBorder,
                focusedContainerColor = TextFieldBackground,
                unfocusedContainerColor = TextFieldBackground,
                cursorColor = PrimaryColor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = ProfileTextFieldTopPadding),
            shape = RoundedCornerShape(ProfileTextFieldCornerRadius),
            singleLine = true
        )
    }
}
