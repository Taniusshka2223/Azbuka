package com.example.azbuka.ui.navs.rating.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.azbuka.R
import com.example.azbuka.domain.model.DayStat
import com.example.azbuka.ui.theme.*

@Composable
fun SelectedDayInfo(
    day: Int,
    weeklyActivity: Map<Int, DayStat>,
    primaryColor: Color = PrimaryViolet,
    modifier: Modifier = Modifier
) {
    val learned = weeklyActivity[day]?.learned ?: 0
    val dayName = listOf(
        "Понедельник", "Вторник", "Среда",
        "Четверг", "Пятница", "Суббота", "Воскресенье"
    ).getOrNull(day - 1) ?: ""

    Text(
        text = stringResource(R.string.selected_day_info_text, dayName, learned),
        fontSize = Dimens.Body16,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
        color = primaryColor,
        textAlign = TextAlign.Center,
        modifier = modifier.fillMaxWidth(),
        fontFamily = SfProText
    )
}
