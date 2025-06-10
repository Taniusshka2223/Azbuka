package com.example.azbuka.ui.navs.cards

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.azbuka.R
import com.example.azbuka.ui.navs.cards.components.CardNavContent

enum class CardSortOption {
    FORWARD,
    BACKWARD,
    SHUFFLE
}

@Composable
fun CardNav(cardViewModel: CardViewModel = viewModel()) {
    val defaultCards = listOf(
        "А" to R.drawable.lettera, "Б" to R.drawable.letterb, "В" to R.drawable.letterv,
        "Г" to R.drawable.letterg, "Д" to R.drawable.letterd, "Е" to R.drawable.lettere,
        "Ё" to R.drawable.letterye, "Ж" to R.drawable.letterzh, "З" to R.drawable.letterz,
        "И" to R.drawable.letteri, "Й" to R.drawable.lettery, "К" to R.drawable.letterk,
        "Л" to R.drawable.letterl, "М" to R.drawable.letterm, "Н" to R.drawable.lettern,
        "О" to R.drawable.lettero, "П" to R.drawable.letterp, "Р" to R.drawable.letterr,
        "С" to R.drawable.letters, "Т" to R.drawable.lettert, "У" to R.drawable.letteru,
        "Ф" to R.drawable.letterf, "Х" to R.drawable.letterh, "Ц" to R.drawable.letterc,
        "Ч" to R.drawable.letterch, "Ш" to R.drawable.lettersh, "Щ" to R.drawable.lettershch,
        "Ъ" to R.drawable.letteree2, "Ы" to R.drawable.lettery2, "Ь" to R.drawable.letteree,
        "Э" to R.drawable.letterea, "Ю" to R.drawable.letteryu, "Я" to R.drawable.letterya
    )

    CardNavContent(defaultCards, cardViewModel)
}