package com.example.azbuka.ui.screens.rating

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.azbuka.R
import com.example.azbuka.ui.screens.cards.CardViewModel
import com.example.azbuka.ui.screens.rating.components.*
import com.example.azbuka.ui.theme.*

@Composable
fun RatingScreen(cardViewModel: CardViewModel = viewModel()) {
    val finalLearned by cardViewModel.finalLearned.collectAsState()
    val finalTotal by cardViewModel.finalTotal.collectAsState()
    val weeklyActivity by cardViewModel.weeklyActivity.collectAsState()

    val activeDays = weeklyActivity.values.count { it.active }

    var selectedDay by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(horizontal = CardScreenHorizontalPadding, vertical = CardScreenHorizontalPadding)
    ) {
        RatingHeader(primaryColor = PrimaryViolet)

        Spacer(Modifier.height(LargeSpacer))

        val progressPercent = if (finalTotal > 0) finalLearned.toFloat() / finalTotal else 0f

        DayProgressSection(
            progressPercent = progressPercent,
            learnedPortion = progressPercent,
            learnedColor = LearnedColor,
            repeatedColor = RepeatColor
        )

        Spacer(Modifier.height(MediumSpacer))
        Divider(color = ProgressBackgroundColor, thickness = 2.dp)
        Spacer(Modifier.height(MediumSpacer))

        Text(
            text = stringResource(id = R.string.study_days),
            style = RatingScreenWeeklySummaryTitleTextStyle,
            color = TextPrimary,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(MediumSpacer))

        StudyDays(
            weeklyActivity = weeklyActivity,
            primaryColor = PrimaryViolet,
            backgroundLight = ProgressBackgroundColor,
            textPrimary = TextPrimary,
            selectedDay = selectedDay,
            onDaySelected = { selectedDay = it }
        )

        Spacer(Modifier.height(MediumSpacer))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.week),
                contentDescription = null,
                modifier = Modifier.size(StudyDayIconSize)
            )
            Spacer(Modifier.width(SmallSpacer))
            Text(
                text = "$activeDays / 7",
                style = RatingScreenBodyBoldTextStyle,
                color = TextPrimary
            )
        }

        selectedDay?.let { day ->
            Spacer(Modifier.height(LargeSpacer))
            SelectedDayInfo(
                day = day,
                weeklyActivity = weeklyActivity,
                primaryColor = PrimaryViolet
            )
        }

        Spacer(Modifier.height(LargeSpacer))
        Divider(color = ProgressBackgroundColor, thickness = 2.dp, modifier = Modifier.fillMaxWidth())

        Spacer(Modifier.height(LargeSpacer))
        Text(
            text = stringResource(id = R.string.weekly_summary),
            style = RatingScreenWeeklySummaryTitleTextStyle,
            color = TextPrimary,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(MediumSpacer))
        WeeklySummary(
            weeklyActivity = weeklyActivity,
            primaryColor = PrimaryViolet,
            backgroundLight = ProgressBackgroundColor
        )
    }
}
