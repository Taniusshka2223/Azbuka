package com.example.azbuka.ui.navs.cards.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.azbuka.R
import com.example.azbuka.ui.navs.cards.CardSortOption
import com.example.azbuka.ui.theme.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortBottomSheet(
    selectedCardFace: String,
    onCardFaceSelected: (String) -> Unit,
    selectedSortOption: CardSortOption,
    onSortOptionSelected: (CardSortOption) -> Unit,
    onRestart: () -> Unit,
    onDismiss: () -> Unit,
    sheetState: SheetState
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(SmallCornerRadius + 14.dp),
        containerColor = White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = PageHorizontalPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.settingscards),
                contentDescription = stringResource(R.string.sort_settings_image_description),
                modifier = Modifier
                    .size(SortIconSize)
            )

            Text(
                text = stringResource(R.string.sort_title),
                style = Typography.titleMedium.copy(color = TextDark),
                modifier = Modifier.padding(top = ExtraLargeSpacer)
            )

            SectionTitle(
                text = stringResource(R.string.sort_section_format),
                topPadding = LargeSpacer
            )

            Text(
                text = stringResource(R.string.sort_face_label),
                style = Typography.ButtonText.copy(
                    color = SecondaryTextColor,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.Start)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MediumSpacer),
                horizontalArrangement = Arrangement.spacedBy(MediumSpacer)
            ) {
                listOf(
                    stringResource(R.string.sort_option_letter),
                    stringResource(R.string.sort_option_gesture)
                ).forEach { option ->
                    val isSelected = option == selectedCardFace
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(42.dp)
                            .clip(RoundedCornerShape(TextFieldCornerRadius))
                            .background(if (isSelected) PrimaryViolet else Transparent)
                            .border(BORDER_STROKE_WIDTH, PrimaryViolet, RoundedCornerShape(TextFieldCornerRadius))
                            .clickable { onCardFaceSelected(option) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = option,
                            color = if (isSelected) White else PrimaryViolet,
                            style = Typography.ButtonText.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }


            SectionTitle(
                text = stringResource(R.string.sort_section_sorting),
                topPadding = LargeSpacer
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                val items = listOf(
                    stringResource(R.string.sort_option_forward) to CardSortOption.FORWARD,
                    stringResource(R.string.sort_option_backward) to CardSortOption.BACKWARD,
                    stringResource(R.string.sort_option_shuffle) to CardSortOption.SHUFFLE
                )

            items.forEachIndexed { index, (text, option) ->
                SortRadioItem(
                    text = text,
                    selected = selectedSortOption == option,
                    onClick = { onSortOptionSelected(option) },
                    modifier = if (index == items.lastIndex) Modifier.padding(bottom = InfoPanelBottomPadding) else Modifier
                    )
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(
    text: String,
    topPadding: Dp = LargeSpacer,
    style: androidx.compose.ui.text.TextStyle = Typography.titleMedium.copy(color = TextDark)
) {
    Text(
        text = text,
        style = style,
        modifier = Modifier
            .padding(top = topPadding, start = ExtraSmallSpacer)
            .fillMaxWidth()
            .wrapContentWidth(Alignment.Start)
    )
}