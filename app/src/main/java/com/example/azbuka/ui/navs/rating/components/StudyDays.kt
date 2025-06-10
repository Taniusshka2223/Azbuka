package com.example.azbuka.ui.navs.rating.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.azbuka.domain.model.DayStat
import com.example.azbuka.R

import com.example.azbuka.ui.theme.*

@Composable
fun StudyDays(
    weeklyActivity: Map<Int, DayStat>,
    primaryColor: Color,
    backgroundLight: Color,
    textPrimary: Color,
    onDaySelected: (Int) -> Unit
) {
    val daysShort = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (i in 1..7) {
            val dayData = weeklyActivity[i]
            val active = dayData?.active == true

            val bgColor = if (active) primaryColor else backgroundLight
            val iconTint = if (active) White else InactiveIconTint

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { onDaySelected(i) }
            ) {
                Box(
                    modifier = Modifier
                        .size(StudyDayBoxSize)
                        .background(bgColor, shape = RoundedCornerShape(StudyDayCornerRadius)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = null,
                        modifier = Modifier.size(StudyDayIconSize),
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(iconTint)
                    )
                }
                Spacer(Modifier.height(ExtraSmallSpacer))
                Text(
                    text = daysShort[i - 1],
                    style = StudyDayLabelTextStyle,
                    color = textPrimary
                )
            }
        }
    }
}
