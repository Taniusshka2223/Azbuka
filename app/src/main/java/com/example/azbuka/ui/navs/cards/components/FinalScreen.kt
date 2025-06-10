package com.example.azbuka.ui.navs.cards.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke

import androidx.compose.ui.res.stringResource
import com.example.azbuka.R
import com.example.azbuka.ui.theme.*

@Composable
fun FinalNav(learned: Int, repeated: Int, total: Int, onRestart: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = FinalSidePadding),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(FinalSectionSpacing)
    ) {
        Text(
            text = stringResource(R.string.final_result),
            style = FinalNavTitleTextStyle,
            color = TextDark
        )

        Text(
            text = stringResource(R.string.final_congrats),
            style = FinalNavSubtitleTextStyle,
            color = TextDark
        )

        Text(
            text = stringResource(R.string.final_progress_label),
            style = FinalNavSubtitleTextStyle,
            color = TextDark
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = FinalProgressTopPadding),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FinalProgressCircle(
                learned = learned,
                repeated = repeated,
                total = total
            )

            Column(
                modifier = Modifier.padding(start = SmallSpacer),
                verticalArrangement = Arrangement.spacedBy(FinalCounterBoxSpacing)
            ) {
                FinalCounterBox(
                    text = stringResource(R.string.final_know, learned),
                    color = LearnedColor
                )
                FinalCounterBox(
                    text = stringResource(R.string.final_repeat, repeated),
                    color = RepeatColor
                )
            }
        }

        Button(
            onClick = onRestart,
            modifier = Modifier
                .fillMaxWidth()
                .height(FinalButtonHeight)
                .padding(top = FinalButtonTopPadding),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryViolet),
            shape = RoundedCornerShape(FinalButtonCornerRadius)
        ) {
            Text(
                text = stringResource(R.string.final_restart_button),
                style = FinalNavTitleTextStyle
            )
        }
    }
}

@Composable
fun FinalProgressCircle(
    learned: Int,
    repeated: Int,
    total: Int
) {
    val strokeWidth = FinalProgressThickness
    val diameter = FinalProgressSize
    val totalFloat = total.toFloat().coerceAtLeast(1f)

    val learnedAngle = (learned / totalFloat) * 360f
    val repeatedAngle = (repeated / totalFloat) * 360f

    val percentCorrect = (learned / totalFloat * 100).toInt().coerceAtMost(100)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(diameter)
    ) {
        Canvas(modifier = Modifier.size(diameter)) {
            drawArc(
                color = ProgressBackgroundColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx())
            )

            drawArc(
                color = RepeatColor,
                startAngle = 0f,
                sweepAngle = repeatedAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx())
            )

            drawArc(
                color = LearnedColor,
                startAngle = repeatedAngle,
                sweepAngle = learnedAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx())
            )
        }


        Text(
            text = "$percentCorrect%",
            style = FinalNavProgressTextStyle,
            color = TextDark
        )
    }
}