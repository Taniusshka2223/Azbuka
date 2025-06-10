package com.example.azbuka.ui.navs.profile

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.dp
import com.example.azbuka.R
import com.example.azbuka.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileNavContent(
    onSignOut: () -> Unit
) {
    val viewModel: ProfileViewModel = viewModel()
    val email by viewModel.email.collectAsState()

    val regular = FontFamily(Font(R.font.sf_pro_text_regular))
    val semibold = FontFamily(Font(R.font.sf_pro_text_semibold))
    val bold = FontFamily(Font(R.font.sf_pro_text_bold))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(horizontal = ProfileHorizontalPadding)
    ) {
        Header(text = stringResource(R.string.profile_Nav_title), font = semibold)

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

        TextFieldSection(
            label = stringResource(R.string.profile_password_label),
            value = "********",
            font = regular
        )

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