package com.example.azbuka.ui.navs.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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

@Composable
fun WelcomeNav(
    onStartClicked: () -> Unit,
    onLoginClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = PageHorizontalPadding, vertical = ExtraLargeSpacer),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    modifier = Modifier.size(MediumIconSize)
                )

                Spacer(modifier = Modifier.width(SmallSpacer))
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = WelcomeAppNameTextStyle,
                    color = TextDark
                )
            }

            Spacer(modifier = Modifier.height(ExtraLargeSpacer))

            Image(
                painter = painterResource(id = R.drawable.welcome_illustration),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(394.dp)
            )

            Spacer(modifier = Modifier.height(LargeSpacer))

            Text(
                text = stringResource(id = R.string.welcome_title),
                style = WelcomeTitleTextStyle,
                color = TextDark,
                textAlign = TextAlign.Center
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                onClick = onStartClicked,
                modifier = Modifier
                    .width(CARD_WIDTH + 41.dp)
                    .height(StandardButtonHeight),
                shape = RoundedCornerShape(TextFieldCornerRadius),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryViolet)
            ) {
                Text(
                    text = stringResource(id = R.string.start_learning),
                    style = WelcomeButtonTextStyle,
                    color = White
                )
            }

            Spacer(modifier = Modifier.height(BottomPadding))

            Row {
                Text(
                    text = stringResource(id = R.string.already_have_account),
                    style = WelcomeLoginPromptTextStyle,
                    color = TextDark
                )
                Text(
                    text = stringResource(id = R.string.login),
                    style = WelcomeLoginPromptTextStyle,
                    color = PrimaryViolet,
                    modifier = Modifier.clickable { onLoginClicked() }
                )
            }
        }
    }
}
