package com.example.azbuka.ui.navs.rating.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import com.example.azbuka.R
import com.example.azbuka.ui.theme.*

@Composable
fun DayProgressSection(
    progressPercent: Float,
    learnedPortion: Float,
    learnedColor: androidx.compose.ui.graphics.Color
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.day_progress_title),
            style = RatingNavWeeklySummaryTitleTextStyle,
            color = TextPrimary,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(MediumSpacer))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = PageHorizontalPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val strokeWidth = FinalProgressThickness
            val progressSize = FinalProgressSize

            val learned = learnedPortion.coerceIn(0f, 1f)
            val repeated = (progressPercent - learned).coerceIn(0f, 1f)
            val percentCorrect = (learned * 100).toInt().coerceIn(0, 100)

            Spacer(modifier = Modifier.height(DiagramSpacerVertical))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(progressSize)
            ) {
                Canvas(modifier = Modifier.size(progressSize)) {
                    val strokePx = strokeWidth.toPx()

                    drawArc(
                        color = ProgressBackgroundColor,
                        startAngle = 0f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(width = strokePx)
                    )

                    drawArc(
                        color = learnedColor,
                        startAngle = -90f + repeated * 360f,
                        sweepAngle = learned * 360f,
                        useCenter = false,
                        style = Stroke(width = strokePx)
                    )
                }

                Text(
                    text = "$percentCorrect%",
                    style = FinalNavProgressTextStyle,
                    color = TextDark
                )
            }

            Spacer(modifier = Modifier.height(DiagramSpacerVertical))
        }
    }
}