package com.example.azbuka.ui.navs.rating.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import com.example.azbuka.R
import com.example.azbuka.ui.theme.*


@Composable
fun RatingHeader(
    primaryColor: Color = PrimaryViolet
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(ProfileHeaderHeight)
            .background(color = primaryColor, shape = RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.rating_header_title),
            color = Color.White,
            fontSize = TitleFontSize,
            fontWeight = FontWeight.SemiBold,
            fontFamily = SfProText
        )
    }
}
