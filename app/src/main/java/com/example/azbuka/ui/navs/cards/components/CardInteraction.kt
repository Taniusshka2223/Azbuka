package com.example.azbuka.ui.navs.cards.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.azbuka.domain.model.Flashcard
import com.example.azbuka.ui.navs.cards.CardSortOption
import com.example.azbuka.ui.navs.cards.CardViewModel
import com.example.azbuka.ui.theme.*

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun CardNavContent(
    defaultCards: List<Pair<String, Int>>,
    cardViewModel: CardViewModel = viewModel()
) {
    var selectedCardFace by remember { mutableStateOf("Буква") }
    var selectedSortOption by remember { mutableStateOf(CardSortOption.FORWARD) }

    var cardList by remember { mutableStateOf(defaultCards) }

    val currentIndex by cardViewModel.currentIndex.collectAsState()
    val learnedCount by cardViewModel.learnedCount.collectAsState()
    val repeatCount by cardViewModel.repeatCount.collectAsState()

    var showResetSheet by remember { mutableStateOf(false) }
    var showSortSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        cardViewModel.updateTotal(defaultCards.size)
    }

    fun restartSession() {
        cardList = when (selectedSortOption) {
            CardSortOption.FORWARD -> defaultCards
            CardSortOption.BACKWARD -> defaultCards.reversed()
            CardSortOption.SHUFFLE -> defaultCards.shuffled()
        }
        cardViewModel.updateIndex(0)
        cardViewModel.updateLearned(0)
        cardViewModel.updateRepeat(0)
    }

    if (showResetSheet) {
        ResetBottomSheet(
            onRestart = ::restartSession,
            onDismiss = { showResetSheet = false }
        )
    }

    if (showSortSheet) {
        SortBottomSheet(
            selectedCardFace = selectedCardFace,
            onCardFaceSelected = { selectedCardFace = it },
            selectedSortOption = selectedSortOption,
            onSortOptionSelected = {
                selectedSortOption = it
                restartSession()
            },
            onRestart = ::restartSession,
            onDismiss = { showSortSheet = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = LargeSpacer)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header(
            showReset = { showResetSheet = true },
            showSort = { showSortSheet = true },
            currentIndex = currentIndex,
            total = cardList.size
        )

        Spacer(modifier = Modifier.height(SmallSpacer))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = CardNavHorizontalPadding),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CounterBox(count = repeatCount, color = RepeatColor, border = RepeatBorderColor)
            CounterBox(count = learnedCount, color = LearnedColor, border = LearnedBorderColor)
        }

        Spacer(modifier = Modifier.height(MediumSpacer))

        if (currentIndex < cardList.size) {
            val (letter, imageRes) = cardList[currentIndex]
            Flashcard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = CardNavHorizontalPadding)
                    .height(CardNavFlashcardHeight),
                frontText = if (selectedCardFace == "Буква") letter else "",
                backText = if (selectedCardFace == "Жест") letter else "",
                frontImage = if (selectedCardFace == "Жест") painterResource(id = imageRes) else null,
                backImage = if (selectedCardFace == "Буква") painterResource(id = imageRes) else null,
                backgroundColor = CardBackgroundLight,
                textColor = TextDark,
                onSwipeLeft = {
                    cardViewModel.updateRepeat(repeatCount + 1)
                    cardViewModel.updateIndex(currentIndex + 1)
                },
                onSwipeRight = {
                    cardViewModel.updateLearned(learnedCount + 1)
                    cardViewModel.updateIndex(currentIndex + 1)
                }
            )
        } else {
            LaunchedEffect(Unit) { cardViewModel.finishSession() }
            FinalNav(learnedCount, repeatCount, cardList.size, onRestart = ::restartSession)
        }
    }
}