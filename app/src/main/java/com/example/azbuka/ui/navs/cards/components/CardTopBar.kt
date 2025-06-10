package com.example.azbuka.ui.navs.cards.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import com.example.azbuka.R
import com.example.azbuka.ui.theme.*

@Composable
fun Header(
    showReset: () -> Unit,
    showSort: () -> Unit,
    currentIndex: Int,
    total: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = PageHorizontalPadding)
    ) {
        Image(
            painter = painterResource(id = R.drawable.cross),
            contentDescription = null,
            modifier = Modifier
                .size(MediumIconSize)
                .clickable(onClick = showReset)
        )
        Spacer(modifier = Modifier.width(MediumSpacer))
        Box(
            modifier = Modifier
                .weight(1f)
                .height(ProgressHeight)
                .clip(RoundedCornerShape(SmallCornerRadius))
                .background(ProgressBackgroundColor)
        ) {
            val progress = (currentIndex + 1f) / total.coerceAtLeast(1)
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress)
                    .background(PrimaryViolet)
            )
        }
        Spacer(modifier = Modifier.width(MediumSpacer))
        Image(
            painter = painterResource(id = R.drawable.filled),
            contentDescription = null,
            modifier = Modifier
                .size(MediumIconSize)
                .clickable(onClick = showSort)
        )
    }
}
