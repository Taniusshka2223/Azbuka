package com.example.azbuka.ui.navs.cards.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.azbuka.ui.theme.*

@Composable
fun CounterBox(
    count: Int,
    color: androidx.compose.ui.graphics.Color,
    border: androidx.compose.ui.graphics.Color
) {
    Box(
        modifier = Modifier
            .padding(top = CounterBoxTopPadding, bottom = CounterBoxBottomPadding)
            .size(width = CounterBoxWidth, height = CounterBoxHeight)
            .background(color, shape = RoundedCornerShape(SmallCornerRadius))
            .border(width = BorderStrokeWidth, color = border, shape = RoundedCornerShape(SmallCornerRadius)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$count",
            style = CounterTextStyle,
            color = White,
            textAlign = TextAlign.Center
        )
    }
}
