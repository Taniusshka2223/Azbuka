package com.example.azbuka.ui.navs.cards.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.azbuka.R
import com.example.azbuka.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetBottomSheet(
    onRestart: () -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(SmallCornerRadius),
        containerColor = White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = PageHorizontalPadding, vertical = BottomPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.sorting),
                contentDescription = stringResource(R.string.sort_settings_image_description),
                modifier = Modifier
                    .size(MediumIconSize + 8.dp)
                    .padding(top = ExtraSmallSpacer)
            )

            Spacer(modifier = Modifier.height(MediumSpacer + 4.dp))

            Text(
                text = stringResource(id = R.string.reset_title),
                style = Typography.titleMedium.copy(color = TextDark)
            )

            Spacer(modifier = Modifier.height(MediumSpacer + 4.dp))

            Text(
                text = stringResource(id = R.string.reset_description),
                style = Typography.CaptionText.copy(
                    color = SecondaryTextColor
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(ExtraLargeSpacer))

            Button(
                onClick = {
                    onRestart()
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryViolet),
                shape = RoundedCornerShape(FinalButtonCornerRadius),
                modifier = Modifier
                    .width(ResetButtonWidth)
                    .height(ResetButtonHeight)
            ) {
                Text(
                    text = stringResource(id = R.string.reset_button_restart),
                    style = Typography.ButtonText.copy(
                        color = White,
                        fontSize = ResetButtonFontSize
                    )
                )

            }

            Spacer(modifier = Modifier.height(ExtraLargeSpacer))
        }
    }
}
