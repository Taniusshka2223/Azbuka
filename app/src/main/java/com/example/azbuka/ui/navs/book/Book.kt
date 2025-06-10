package com.example.azbuka.ui.navs.book

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.example.azbuka.R
import com.example.azbuka.ui.theme.*
import androidx.compose.ui.unit.sp

@Composable
fun Book() {
    val letters = listOf(
        "А", "Б", "В", "Г", "Д", "Е", "Ё", "Ж", "З", "И", "Й", "К", "Л", "М",
        "Н", "О", "П", "Р", "С", "Т", "У", "Ф", "Х", "Ц", "Ч", "Ш", "Щ","Ъ", "Ы",
        "Ь", "Э", "Ю", "Я"
    )
    val imageNames = listOf(
        "lettera", "letterb", "letterv", "letterg", "letterd", "lettere", "letterye", "letterzh",
        "letterz", "letteri", "lettery", "letterk", "letterl", "letterm", "lettern", "lettero",
        "letterp", "letterr", "letters", "lettert", "letteru", "letterf", "letterh", "letterc",
        "letterch", "lettersh", "lettershch", "letteree2", "lettery2", "letteree", "letterea",
        "letteryu", "letterya"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Spacer(modifier = Modifier.height(LargeSpacer))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = PageHorizontalPadding)
                .height(StandardButtonHeight)
                .background(PrimaryViolet, RoundedCornerShape(TextFieldCornerRadius)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.alphabet_title),
                fontSize = TitleFontSize,
                color = White,
                fontWeight = TitleFontWeight,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(MediumSpacer))

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = Modifier.padding(horizontal = PageHorizontalPadding),
            verticalArrangement = Arrangement.spacedBy(MediumSpacer),
            contentPadding = PaddingValues(top = SmallSpacer, bottom = BottomPadding)
        ) {
            items(letters.size) { index ->
                AlphabetCardRow(letter = letters[index], imageName = imageNames[index])
            }
        }
    }
}

@Composable
fun AlphabetCardRow(letter: String, imageName: String) {
    val navWidth = LocalConfiguration.current.screenWidthDp.dp
    val horizontalPadding = PageHorizontalPadding
    val spacingBetweenCards = SmallSpacer + 2.dp

    val cardWidth = (navWidth - horizontalPadding * 2 - spacingBetweenCards) / 2
    val cardHeight = cardWidth * 1.306f

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AlphabetCard(
            content = {
                Text(
                    text = letter,
                    fontSize = (cardWidth.value * AlphabetLetterFontScale).sp,
                    fontWeight = AlphabetLetterFontWeight,
                    color = Black
                )
            },
            width = cardWidth,
            height = cardHeight
        )

        Spacer(modifier = Modifier.width(spacingBetweenCards))

        AlphabetCard(
            content = {
                Image(
                    painter = painterResource(id = getDrawableId(imageName)),
                    contentDescription = stringResource(R.string.gesture_for_letter, letter),
                    modifier = Modifier.size(cardWidth * AlphabetImageScale)
                )
            },
            width = cardWidth,
            height = cardHeight
        )
    }
}


@Composable
fun AlphabetCard(content: @Composable BoxScope.() -> Unit, width: Dp, height: Dp) {
    Box(
        modifier = Modifier
            .size(width, height)
            .background(CardBackgroundColor.copy(alpha = 0.2f), RoundedCornerShape(TextFieldCornerRadius)),
        contentAlignment = Alignment.Center,
        content = content
    )
}

@Composable
fun getDrawableId(name: String): Int {
    val context = LocalContext.current
    return remember(name) {
        context.resources.getIdentifier(name, "drawable", context.packageName)
    }
}
