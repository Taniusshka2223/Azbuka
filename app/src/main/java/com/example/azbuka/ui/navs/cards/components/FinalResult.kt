package com.example.azbuka.ui.navs.cards.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.azbuka.ui.theme.*


@Composable
fun FinalCounterBox(text: String, color: Color) {
    Box(
        modifier = Modifier
            .height(FinalCounterBoxHeight)
            .width(FinalCounterBoxWidth)
            .background(color, RoundedCornerShape(FinalCounterBoxCornerRadius)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = FinalCounterTextStyle,
            color = White,
            modifier = Modifier.padding(horizontal = FinalCounterBoxTextPadding)
        )
    }
}