package com.example.azbuka.ui.navs.rating.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

import com.example.azbuka.domain.model.DayStat

import com.example.azbuka.ui.theme.*

@Composable
fun WeeklySummary(
    weeklyActivity: Map<Int, DayStat>,
    backgroundLight: Color
) {
    val daysShort = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = WeeklySummaryHorizontalPadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        for (i in 1..7) {
            val dayStat = weeklyActivity[i] ?: DayStat(active = false, learned = 0, remaining = 0)
            val learned = dayStat.learned
            val remaining = dayStat.remaining
            val total = learned + remaining
            val percentage = if (total > 0) learned.toFloat() / total else 0f
            val learnedHeight = (percentage * WeeklySummaryMaxHeight.value).dp
            val remainingHeight = (WeeklySummaryMaxHeight - learnedHeight)

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.width(WeeklySummaryColumnWidth)
            ) {
                Text(
                    text = "${(percentage * 100).toInt()}%",
                    style = WeeklySummaryPercentageTextStyle,
                    modifier = Modifier.padding(bottom = WeeklySummaryTextPaddingBottom)
                )

                if (remainingHeight > 0.dp) {
                    Box(
                        modifier = Modifier
                            .width(WeeklySummaryBarWidth)
                            .height(remainingHeight)
                            .clip(RoundedCornerShape(topStart = WeeklySummaryBarCornerRadius, topEnd = WeeklySummaryBarCornerRadius))
                            .background(backgroundLight)
                    )
                }

                if (learnedHeight > 0.dp) {
                    Box(
                        modifier = Modifier
                            .width(WeeklySummaryBarWidth)
                            .height(learnedHeight)
                            .clip(RoundedCornerShape(bottomStart = WeeklySummaryBarCornerRadius, bottomEnd = WeeklySummaryBarCornerRadius))
                            .background(LearnedColor)
                    )
                }

                Text(
                    text = daysShort[i - 1],
                    style = WeeklySummaryDayLabelTextStyle,
                    modifier = Modifier.padding(top = WeeklySummaryTextPaddingTop)
                )
            }
        }
    }
}
